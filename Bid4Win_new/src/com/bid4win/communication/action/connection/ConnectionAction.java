package com.bid4win.communication.action.connection;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bid4win.commons.core.exception.BusinessException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.ConnectionRef;
import com.bid4win.commons.mail.Bid4WinMailManager;
import com.bid4win.commons.persistence.entity.account.security.Credential;
import com.bid4win.commons.persistence.entity.account.security.Password;
import com.bid4win.commons.persistence.entity.contact.Email;
import com.bid4win.communication.action.json.JSONAction;
import com.bid4win.communication.json.factory.JSONAccountFactory;
import com.bid4win.communication.json.factory.account.security.JSONCredentialFactory;
import com.bid4win.communication.json.factory.contact.JSONEmailFactory;
import com.bid4win.communication.json.model.JSONMessage.Type;
import com.bid4win.manager.account.AccountManager;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.connection.Connection;
import com.bid4win.persistence.entity.connection.PasswordReinit;
import com.bid4win.persistence.entity.connection.Subscription;
import com.bid4win.persistence.entity.locale.Language;
import com.bid4win.service.connection.ConnectionService;
import com.bid4win.service.property.PropertyService;

/**
 * Classe d'action liées à la problématique de la connexion
 *
 * @author Maxime Ollagnier
 */
public class ConnectionAction extends JSONAction
{
  public static final String CONNECT = "ConnectAction";
  public static final String DISCONNECT = "DisconnectAction";

  /** Serial */
  private static final long serialVersionUID = -6566305749815622031L;

  /** Référence du ConnectionService */
  @Autowired
  @Qualifier("ConnectionService")
  private ConnectionService connectionService = null;

  /** Référence du PropertyService */
  @Autowired
  @Qualifier("PropertyService")
  private PropertyService propertyService = null;

  /** Référence du AccountManager */
  @Autowired
  @Qualifier("AccountManager")
  private AccountManager accountManager = null;

  /** Référence du MailManager */
  @Autowired
  @Qualifier("Bid4WinMailManager")
  private Bid4WinMailManager mailManager = null;

  /**
   * Effectue la connexion d'un compte utilisateur. Vérification d'authenticité,
   * mise à jour de la base de données, prise en compte des options de rémanence
   * et ajout de du compte à la session.
   *
   * @return refresh si la page doit être rechargée. Sinon, SUCCESS
   * @throws UserException si une erreur utilisateur se produit.
   * @throws Throwable si une erreur technique se produit.
   */
  @Action(value = CONNECT, results = {@Result(name = REFRESH, type = TYPE_TILE, params = {"location", "baseLayout.tile"}),
                                      @Result(name = SUCCESS, type = TYPE_JSON, params = {"root", "jsonResult"})})
  public String ConnectAction() throws UserException, Throwable
  {
  /*  if(this.getSession().isConnected())
    {
      throw new BusinessException("Account already registered in session");
    }*/
    String loginOrEmail = this.getParameter("loginOrEmail", ConnectionRef.EMAIL_MISSING_ERROR);
    Password password = new Password(this.getParameter("password", ConnectionRef.PASSWORD_MISSING_ERROR));
    boolean rememberMe = this.getParameterBool("rememberMe");
    boolean reconnect = this.getParameterBool("reconnect");

    // Connexion
    Connection connection = this.getConnectionService().connect(loginOrEmail, password, reconnect);

    // Gestion des cookies de connexion
    if(rememberMe)
    {
      this.getCookieManager().putLogin(
          connection.getAccount().getCredential().getLogin().getValue());
    }
    else
    {
      this.getCookieManager().removeLogin();
    }
    this.getSession().connect();
    this.putJSONObject(JSONAccountFactory.getInstance().getJSONAccount(connection.getAccount()));
    this.setSuccess(true);

    // Le langage du compte est sauvegardé en session. Si le langage de
    // session a été mis à jour, la page est rafraîchie.
    // TODO Gestion des préférences
    // if(this.saveLanguageInSession(account.getPreference().getLanguage()))
    // A transférer dans ConnectionService.connect()
    if(this.saveLanguageInSession(Language.FRENCH))
    {
      return "refresh";
    }
    return SUCCESS;
  }
  /**
   * Effectue la déconnexion d'un éventuel compte utilisateur connecté. Mise à
   * jour de la base de données ainsi que déréférencement du compte de la
   * session et invalidation de cette dernière.
   *
   * @return SUCCESS
   * @throws Throwable si une erreur technique se produit.
   */
  @Action(DISCONNECT)
  public String DisconnectAction() throws Throwable
  {

    this.setSuccess(true);
    return SUCCESS;
  }

  /**
   * Reconnecte l'utilisateur au compte utilisateur en session. Si aucun compte
   * utilisateur n'est présent dans la session alors une tentative de
   * reconnection par IP est effectuée avec le login et la fingerPrint
   * (identifiant unique égal à l'ID de la session lors de la dernière
   * connexion) récupérés dans les cookies.
   *
   * @return refresh si la page doit être rechargée. Sinon, SUCCESS
   * @throws Throwable si une erreur technique se produit.
   */
  @Action(value = "ReconnectAction", results = {@Result(name = REFRESH, type = TYPE_TILE, params = {"location", "baseLayout.tile"}), @Result(name = "success", type = "json", params = {"root", "jsonResult"})})
  public String ReconnectAction() throws Throwable
  {
      Account account = this.getConnectionService().getConnectedAccount();
      /*if(this.getSession().hasAccountId())
      {
        String accountId = this.getSession().getAccountId();
        account = this.getAccountManager().getById(accountId);
      }
      else
      {
        // Aucun compte n'a encore été connecté, on positionne le time out de
        // session
        if(this.getPropertyService().hasProperty(PropertyRef.SERVER_SESSION_TIMEOUT.getCode()))
        {
          this.getSession().setMaxInactiveInterval(this.getPropertyService().getProperty(
              PropertyRef.SERVER_SESSION_TIMEOUT.getCode()).getIntValue());
        }

        String accountId = this.getCookieManager().findCookieValue(CookieManager.ACCOUNT_ID_COOKIE_NAME);
        String fingerPrint = this.getCookieManager().findCookieValue(CookieManager.FINGERPRINT_COOKIE_NAME);

        if(accountId != null && fingerPrint != null)
        {
          IpAddress ipAddress = new IpAddress(this.getRequest().getRemoteAddr());
          String sessionId = this.getSession().getId();
          account = this.getConnectionService().reconnect(accountId, ipAddress, sessionId, fingerPrint);
        }
      }*/
      if(account != null)
      {
        // Le langage du compte est sauvegardé en session. Si le langage de
        // session a été mis à jour, la page est rafraîchie.
        // TODO Gestion des préférences
        // if(this.saveLanguageInSession(account.getPreference().getLanguage()))
        if(this.saveLanguageInSession(Language.FRENCH))
        {
          return "refresh";
        }
        this.putJSONObject(JSONAccountFactory.getInstance().getJSONAccount(account));
        this.setSuccess(true);
      }
    return SUCCESS;
  }

  /**
   * C'est l'action de réinitialisation du mot de passe en elle-même. La requête
   * contient l'identifiant du compte utilisateur concerné ainsi qu'une clé
   * unique utilisés afin d'authentifier le remplacement de l'ancien mot de
   * passe par le nouveau également présent dans la requête.
   *
   * @return SUCCESS
   * @throws UserException si une erreur utilisateur se produit.
   * @throws Throwable si une erreur technique se produit.
   */
  @Action("PasswordReinitAction")
  public String PasswordReinitAction() throws UserException, Throwable
  {
    if(this.getSession().isConnected())
    {
      throw new BusinessException("Account already registered in session");
    }
    String reinitKey = this.getParameter("reinitKey");
    String accountId = this.getParameter("accountId", ConnectionRef.LOGIN_MISSING_ERROR);
    Password password = new Password(this.getParameter("password", ConnectionRef.PASSWORD_MISSING_ERROR));

    Account account = this.getConnectionService().reinitPassword(reinitKey, accountId, password);
    this.putJSONObject(JSONAccountFactory.getInstance().getJSONAccount(account));
    this.setSuccess(true);
    return SUCCESS;
  }

  /**
   * Envoi un e-mail à l'utilisateur, dont le compte est référencé par le
   * loginOrEmail de la requête, contenant un lien vers la page de
   * réinitialisation du mot de passe. Le lien contient l'identifiant du compte
   * utilisateur ainsi qu'une clé unique qui seront utilisés lors de l'action de
   * réinitialisation du mot de passe.
   *
   * @return SUCCESS
   * @throws UserException si une erreur utilisateur se produit.
   * @throws Throwable si une erreur technique se produit.
   */
  @Action("PasswordReinitMailAction")
  public String PasswordReinitMailAction() throws UserException, Throwable
  {
    if(this.getSession().isConnected())
    {
      throw new BusinessException("Account already registered in session");
    }
    String loginOrEmail = this.getParameter("loginOrEmail", ConnectionRef.EMAIL_MISSING_ERROR);
    PasswordReinit passwordReinit = this.getConnectionService().reinitPassword(loginOrEmail);
    this.getMailManager().sendPasswordReinitMail(passwordReinit);
    this.setSuccess(true);
    return SUCCESS;
  }

  /**
   * Récupère une ID de compte utilisateur ainsi qu'une clé unique de
   * ré-initialisation de mot de passe dans la requête, les vérifie, puis
   * redirige la requête avec ces attributs vers la page de ré-initialisation de
   * mot de passe de la webApp.
   *
   * @return refresh
   * @throws Throwable si une erreur technique se produit.
   */
  @Action(value = "PasswordReinitPromptAction", results = {@Result(name = REFRESH, type = TYPE_TILE, params = {"location", "baseLayout.tile"})})
  public String PasswordReinitPromptAction() throws Throwable
  {
    if(!this.getSession().isConnected())
    {
      String reinitKey = this.getParameter("reinitKey");
      String accountId = this.getParameter("accountId", ConnectionRef.LOGIN_MISSING_ERROR);
      this.getConnectionService().getPasswordReinit(reinitKey, accountId);
    }
    return "refresh";
  }

  /**
   * TODO
   *
   * @return SUCCESS
   * @throws UserException si une erreur utilisateur se produit.
   * @throws Throwable si une erreur technique se produit.
   */
  @Action("SubscribeAction")
  public String SubscribeAction() throws UserException, Throwable
  {
    // Essaye de récupérer le certificat de connexion du compte utilisateur à créer
    Credential credential = JSONCredentialFactory.getInstance().get(this, Type.WARN);
    // Essaye de récupérer l'email du compte utilisateur à créer
    Email email = JSONEmailFactory.getInstance().get(this, Type.WARN);

    Subscription subscription = this.getConnectionService().subscribe(credential, email);
    Account account = subscription.getAccount();
    this.getMailManager().sendActivationMail(account);

    this.putJSONObject(JSONAccountFactory.getInstance().getJSONAccount(subscription.getAccount()));
    this.setSuccess(true);
    return SUCCESS;
  }

  /**
   * Renvoi le ConnectionService
   *
   * @return Le ConnectionService
   */
  protected ConnectionService getConnectionService()
  {
    return this.connectionService;
  }

  /**
   * Renvoi le PropertyService
   *
   * @return Le PropertyService
   */
  protected PropertyService getPropertyService()
  {
    return this.propertyService;
  }

  /**
   * Renvoi le AccountManager
   *
   * @return Le AccountManager
   */
  protected AccountManager getAccountManager()
  {
    return this.accountManager;
  }

  /**
   * Renvoi le MailManager
   *
   * @return Le MailManager
   */
  protected Bid4WinMailManager getMailManager()
  {
    return this.mailManager;
  }
}
