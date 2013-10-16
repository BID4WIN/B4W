package com.bid4win.communication.interceptor;

import javax.servlet.http.HttpServletRequest;

import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.ConnectionRef;
import com.bid4win.commons.core.security.IdGenerator;
import com.bid4win.commons.core.security.IdPattern;
import com.bid4win.commons.persistence.entity.account.security.Password;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthenticationException;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthorizationException;
import com.bid4win.commons.persistence.entity.account.security.exception.SessionException;
import com.bid4win.commons.persistence.entity.connection.DisconnectionReason;
import com.bid4win.commons.persistence.entity.connection.IpAddress;
import com.bid4win.communication.action.BaseAction;
import com.bid4win.communication.action.connection.ConnectionAction;
import com.bid4win.communication.cookie.CookieManager;
import com.bid4win.communication.session.Bid4WinSession;
import com.bid4win.persistence.entity.connection.Connection;
import com.bid4win.service.connection.ConnectionService;
import com.bid4win.service.connection.SessionHandler;
import com.opensymphony.xwork2.ActionInvocation;

/**
 * TODO A COMMENTER
 *
 * @author Maxime Ollagnier
 */
public class AccessInterceptor extends Interceptor
{
  /** Serial */
  private final static long serialVersionUID = -5089660090669925284L;
  /** TODO A COMMENTER */
  private final static IdPattern PROTECTION_KEY_PATTERN = IdPattern.createNoCheck("BBBBBBBBBBBB");

 /* @Override
  public String intercept(ActionInvocation actionInvocation) throws Exception
  {
    BaseAction action = this.getAction(actionInvocation);
    boolean connectionAction = action instanceof ConnectionAction;
    Bid4WinSession session = action.getSession();
    CookieManager cookieManager = action.getCookieManager();
    HttpServletRequest request = action.getRequest();
    IpAddress ipAddress = new IpAddress(request.getRemoteAddr());

    // On démarre la session métier
    SessionHandler.getInstance().startSession(session.getId(), ipAddress);
    // On récupère l'état de connexion de la session
    boolean connected = session.isConnected();
    try
    {
      // On teste une possible re-connexion
      connected = this.reconnect(session, ipAddress, cookieManager);
      try
      {
        String result = BaseAction.REFRESH;
        // On effectue l'action demandée sauf s'il s'agit d'une action de connexion
        // et que les cookies ne semblent pas acceptés côté client
        // TODO lancer une exception à la place qui sera traitée par le JsonInterceptor
        if(!connectionAction || this.isCookieAccepted(session, cookieManager))
        {
          result = actionInvocation.invoke();
        }
        // Un compte est connecté après l'appel à l'action et ne l'était pas avant
        if(!connected && session.isConnected())
        {
          // Il faut protéger la session nouvellement connectée contre le vol
          this.protectSession(session, ipAddress, cookieManager);
        }
        return result;
      }
      catch(AuthenticationException ex)
      {
        // Le compte utilisateur n'est plus considéré connecté
        session.disconnect();
        throw ex;
      }
    }
    finally
    {
      // Un compte était connecté avant l'appel à l'action et ne l'est plus
      if(connected && !session.isConnected())
      {
        // La connexion a été interrompue, il faut invalider la session
        this.invalidateSession(session, cookieManager, true);
      }
      // On stoppe la session métier
      SessionHandler.getInstance().stopSession();
    }
  }*/

  /**
   * Cette méthode gère la sécurité des accès aux actions en prenant en charge la
   * gestion des connexions et en essayant d'empêcher le vol de session ou d'identité
   * {@inheritDoc}
   * @see com.opensymphony.xwork2.interceptor.Interceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
   */
  @Override
  public String intercept(ActionInvocation invocation) //throws Exception
  {
    BaseAction action = this.getAction(invocation);
    Bid4WinSession session = action.getSession();
    CookieManager cookieManager = action.getCookieManager();
    HttpServletRequest request = action.getRequest();
    IpAddress ipAddress = new IpAddress(request.getRemoteAddr());

    // On démarre la session métier
    SessionHandler.getInstance().startSession(session.getId(), ipAddress);
    try
    {
      // On ne peut pas demander à effectuer d'action sur la connexion du compte
      // utilisateur au premier appel car il faut valider que les cookies soient
      // acceptés par le client
      if(!session.isNew())
      {
//        actionName = this.getActionName(invocation);
//        if(actionName.equals(InitAction.INIT))
        this.handleConnection(session, ipAddress, cookieManager,
                              this.getActionName(invocation), request);
      }
      try
      {
        // Execute l'action demandée
        return invocation.invoke();
      }
      catch(AuthenticationException ex)
      {
        // Le compte utilisateur n'est plus considéré connecté
        if(session.isConnected())
        {
          this.invalidateSession(session, ipAddress, cookieManager, false);
        }
        throw ex;
      }
    }
    finally
    {
      // On stoppe la session métier
      SessionHandler.getInstance().stopSession();
    }
  }

  protected void handleConnection(Bid4WinSession session, IpAddress ipAddress,
                                 CookieManager cookieManager, String actionName,
                                 HttpServletRequest request)
  {
      try
      {
        if(session.isConnected())
        {
          // Si la session est considérée connectée, il faut vérifier sa protection
          this.checkProtection(session, ipAddress, cookieManager);
          // La déconnexion est demandée
          if(actionName.equals(ConnectionAction.DISCONNECT))
          {
            this.disconnect(session, ipAddress, cookieManager);
          }
        }
        else
        {
          if(actionName.equals(ConnectionAction.CONNECT))
          {
            this.connect(session, ipAddress, cookieManager, request);
          }
          else
          {
            this.reconnect(session, ipAddress, cookieManager);
          }
        }
      }
      catch(SessionException ex)
      {
        this.invalidateSession(session, ipAddress, cookieManager, false);
        this.handleConnection(session, ipAddress, cookieManager, actionName, request);
      }
  }
  /**
   * Cette fonction permet protéger la session donnée. Il faut lui attribuer une
   * clé ainsi que l'adresse IP de connexion
   * @param session TODO A COMMENTER
   * @param ipAddress TODO A COMMENTER
   * @param cookieManager TODO A COMMENTER
   * @param connection TODO A COMMENTER
   */
  protected void startProtection(Bid4WinSession session, IpAddress ipAddress,
                                 CookieManager cookieManager, Connection connection)
  {
    // Définition de l'adresse IP de connexion
    session.setIpAddress(ipAddress);
    // Définition d'une clé unique de connexion à la session
    String key = IdGenerator.generateId(AccessInterceptor.PROTECTION_KEY_PATTERN);
    // Positionnement de la clé unique chez le client
    cookieManager.putProtectionKey(key);
    // Positionnement de la clé unique dans la session
    session.setProtectionKey(key);
    // On positionne les cookies nécessaire à une future re-connexion
    if(connection.isRemanent())
    {
      cookieManager.putAccountId(connection.getAccount().getId());
      cookieManager.putFingerPrint(connection.getFingerPrint());
    }
    else
    {
      cookieManager.removeAccountId();
      cookieManager.removeFingerPrint();
    }
  }
  /**
   * Cette fonction permet vérifier la protection de la session donnée. On valide
   * d'abord la présence de la clé chez le client : si elle est présente et que
   * l'adresse IP de connexion est différente, on invalide la session courante,
   * si l'adresse IP de connexion est identique, la session appartient bien au
   * client et si elle n'est pas présente, le client tente d'accéder à une session
   * ne lui appartenant pas ou a un problème avec ses cookies
   * @param session TODO A COMMENTER
   * @param ipAddress TODO A COMMENTER
   * @param cookieManager TODO A COMMENTER
   * @throws SessionException TODO A COMMENTER
   * @throws AuthenticationException TODO A COMMENTER
   */
  protected void checkProtection(Bid4WinSession session, IpAddress ipAddress, CookieManager cookieManager)
            throws SessionException, AuthenticationException
  {
    // La clé chez le client n'est pas la même que celle de la session, la session
    // ne lui appartient pas
    if(!session.getProtectionKey().equals(cookieManager.findProtectionKey()))
    {
      cookieManager.removeProtectionKey();
      throw new AuthenticationException(ConnectionRef.SESSION_INVALID_ERROR,
                                        DisconnectionReason.SECURITY);
    }
    // Connexion à partir d'un point différent du réseau, on doit démarrer une
    // nouvelle session
    if(!session.getIpAddress().equals(ipAddress))
    {
      this.invalidateSession(session, ipAddress, cookieManager, false);
    }
  }

  /**
   *
   * TODO A COMMENTER
   * @param session TODO A COMMENTER
   * @param ipAddress TODO A COMMENTER
   * @param cookieManager TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   * @throws SessionException TODO A COMMENTER
   */
  protected void disconnect(Bid4WinSession session, IpAddress ipAddress, CookieManager cookieManager)
            throws PersistenceException, SessionException
  {
    ConnectionService.getInstance().disconnect();
    session.disconnect();
    this.invalidateSession(session, ipAddress, cookieManager, true);
  }
  /**
   *
   * TODO A COMMENTER
   * @param session TODO A COMMENTER
   * @param ipAddress TODO A COMMENTER
   * @param cookieManager TODO A COMMENTER
   * @param request TODO A COMMENTER
   */
  protected void connect(Bid4WinSession session, IpAddress ipAddress,
                         CookieManager cookieManager, HttpServletRequest request)
            throws PersistenceException, UserException, SessionException,
                   AuthenticationException, AuthorizationException
  {
    String loginOrEmail = UtilObject.checkNotNull("loginOrEmail",
                          request.getParameter("loginOrEmail"),
                          ConnectionRef.EMAIL_MISSING_ERROR);
    Password password = new Password(UtilObject.checkNotNull("password",
                                                             request.getParameter("password"),
                                                             ConnectionRef.PASSWORD_MISSING_ERROR));
    boolean reconnect = Boolean.parseBoolean(request.getParameter("reconnect"));

    // Connexion
    Connection connection = ConnectionService.getInstance().connect(loginOrEmail, password, reconnect);

    session.connect();
    this.startProtection(session, ipAddress, cookieManager, connection);
  }
  /**
   *
   * TODO A COMMENTER
   * @param session TODO A COMMENTER
   * @param ipAddress TODO A COMMENTER
   * @param cookieManager TODO A COMMENTER
   */
  protected void reconnect(Bid4WinSession session, IpAddress ipAddress, CookieManager cookieManager)
            throws PersistenceException, UserException, SessionException,
                   AuthenticationException, AuthorizationException
  {
    String accountId = cookieManager.findAccountId();
    String fingerPrint = cookieManager.findFingerPrint();
    if(accountId != null)
    {
      // Les informations nécessaire à une re-connexion ne sont pas présentes
      if(fingerPrint != null)
      {
        Connection connection = ConnectionService.getInstance().reconnect(fingerPrint,
                                                                          accountId);
        session.connect();
        this.startProtection(session, ipAddress, cookieManager, connection);
      }
      else
      {
        cookieManager.removeAccountId();
      }
    }
    else
    {
      if(fingerPrint != null)
      {
        cookieManager.removeFingerPrint();
      }
    }
  }
  /**
   *
   * TODO A COMMENTER
   * @param session TODO A COMMENTER
   * @param ipAddress TODO A COMMENTER
   * @param cookieManager TODO A COMMENTER
   * @param removeRemanence TODO A COMMENTER
   * @throws SessionException TODO A COMMENTER
   */
  protected void invalidateSession(Bid4WinSession session, IpAddress ipAddress,
                                   CookieManager cookieManager, boolean removeRemanence)
            throws SessionException
  {
    // Les informations de rémanence de connexion doivent être supprimées
    if(removeRemanence)
    {
      cookieManager.removeAccountId();
      cookieManager.removeFingerPrint();
    }
    // La clé de protection de la session doit être supprimée
    cookieManager.removeProtectionKey();
    // La session est invalidée
    session.invalidate();
    // Si une session métier était démarrée, il faut l'arrêter et en démarrer une
    // nouvelle
    if(SessionHandler.getInstance().isSessionStarted())
    {
      SessionHandler.getInstance().stopSession();
      SessionHandler.getInstance().startSession(session.getId(), ipAddress);
    }
  }

/*  @Override
  public void beforeResult(ActionInvocation invocation, String resultCode)
  {
    // TODO Auto-generated method stub
    System.out.println("preresult");
  }*/
  /**
  *
  * TODO A COMMENTER
  * @param session TODO A COMMENTER
  * @param cookieManager TODO A COMMENTER
  * @return TODO A COMMENTER
  */
 /*protected boolean isCookieAccepted(Bid4WinSession session, CookieManager cookieManager)
 {
   if(session.isNew())
   {
     // On positionne un nouveau cookie de validation
     cookieManager.putCookie(CookieManager.COOKIE_ACCEPTED_COOKIE_NAME, "true");
     // On vérifie la présence d'un ancien cookie de validation
     if(cookieManager.findCookie(CookieManager.COOKIE_ACCEPTED_COOKIE_NAME) == null)
     {
       return false;
     }
   }
   else
   {
     if(cookieManager.findCookie(CookieManager.COOKIE_ACCEPTED_COOKIE_NAME) == null)
     {
       // On positionne un nouveau cookie de validation
       cookieManager.putCookie(CookieManager.COOKIE_ACCEPTED_COOKIE_NAME, "true");
       return false;
     }
   }
   return true;
 }*/

 /**
  * Cette fonction permet protéger la session donnée. Si elle est nouvellement
  * créée, il faut lui attribuer une clé ainsi que l'adresse IP de connexion.
  * Sinon, on valide d'abord la présence de la clé chez le client : si elle est
  * présente et que l'adresse IP de connexion est différente, on invalide la
  * session courante, si l'adresse IP de connexion est identique, la session
  * appartient bien au client et si elle n'est pas présente, le client tente d'
  * accéder à une session ne lui appartenant pas ou a un problème avec ses cookies
  * @param session TODO A COMMENTER
  * @param ipAddress TODO A COMMENTER
  * @param cookieManager TODO A COMMENTER
  * @throws UserException TODO A COMMENTER
  * @throws SessionException TODO A COMMENTER
  */
/* protected void protectSession(Bid4WinSession session, IpAddress ipAddress, CookieManager cookieManager)
           throws UserException, SessionException
 {
   // La session est nouvellement connectée, on essaye de la protéger
   if(session.getProtectionKey().equals(""))
   {
     // Définition de l'adresse IP de connexion
     session.setIpAddress(ipAddress);
     // Définition d'une clé unique de connexion à la session
     String key = IdGenerator.generateId(AccessInterceptor.PROTECTION_KEY_PATTERN);
     // Positionnement de la clé unique chez le client
     cookieManager.putCookie(CookieManager.PROTECTION_KEY_COOKIE_NAME, key);
     // Positionnement de la clé unique dans la session
     session.setProtectionKey(key);
     // On positionne les cookies nécessaire à une future re-connexion
     Connection connection = SessionHandler.getInstance().getConnection();
     if(connection.isRemanent())
     {
       // TODO date d'expiration
       cookieManager.putCookie(CookieManager.ACCOUNT_ID_COOKIE_NAME,
                               connection.getAccount().getId());
       cookieManager.putCookie(CookieManager.FINGERPRINT_COOKIE_NAME,
                               connection.getFingerPrint());
     }
     else
     {
       cookieManager.removeCookie(CookieManager.ACCOUNT_ID_COOKIE_NAME);
       cookieManager.removeCookie(CookieManager.FINGERPRINT_COOKIE_NAME);
     }
   }
   else
   {
     // La clé chez le client n'est pas la même que celle de la session, la session
     // ne lui appartient pas
     if(!session.getProtectionKey().equals(cookieManager.findCookieValue(CookieManager.PROTECTION_KEY_COOKIE_NAME)))
     {
       cookieManager.removeCookie(CookieManager.PROTECTION_KEY_COOKIE_NAME);
       throw new SessionException();
     }
     // Connexion à partir d'un point différent du réseau, on doit démarrer une
     // nouvelle session
     if(!session.getIpAddress().equals(ipAddress))
     {
       this.invalidateSession(session, cookieManager, false);
     }
   }
 }*/
}
