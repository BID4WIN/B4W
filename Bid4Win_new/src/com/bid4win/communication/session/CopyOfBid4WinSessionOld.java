//package com.bid4win.communication.session;
//
//
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.apache.catalina.session.StandardSessionFacade;
//import org.apache.struts2.ServletActionContext;
//
//import com.bid4win.commons.core.UtilObject;
//import com.bid4win.commons.core.UtilString;
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.commons.core.exception.UserException;
//import com.bid4win.commons.core.reference.MessageRef.ConnectionRef;
//import com.bid4win.commons.persistence.entity.connection.IpAddress;
//import com.bid4win.persistence.entity.locale.Language;
//
///**
// * Class enrichissant la StandardSessionFacade de fonctionnalités spécifiques.
// *
// * @author Maxime Ollagnier
// */
//public class CopyOfBid4WinSessionOld extends StandardSessionFacade
//{
//  /** TODO */
//  private final static String PROTECTION_KEY = "protectionKey";
//  /** TODO */
//  private final static String COOKIE_ACCEPTED = "cookieAccepted";
//  /** TODO */
//  private final static String CONNECTED = "connected";
//  /** TODO */
//  private final static String IP_ADDRESS = "ipAddress";
//  /** Nom de l'attribut language de la session */
//  private final static String LANGUAGE = "language";
//
//  /**
//   * Constructeur
//   * @param session La session HTTP à encapsuler
//   */
//  public CopyOfBid4WinSessionOld()//HttpSession session)
//  {
//    this(ServletActionContext.getRequest());
//  }
//  public CopyOfBid4WinSessionOld(HttpServletRequest request)//HttpSession session)
//  {
//    this(ServletActionContext.getRequest().getSession());
//  }
//  public CopyOfBid4WinSessionOld(HttpSession session)
//  {
//    super(session);
//  }
//  public void invalide()
//  {
//    super.invalidate();
//    this.getSessionContext().
//  }
//
//  /**
//   * Getter de la clé de protection de la session
//   * @return La clé de protection de la session
//   */
//  public String getProtectionKey()
//  {
//    return UtilString.trimNotNull((String)this.getAttribute(CopyOfBid4WinSessionOld.PROTECTION_KEY));
//  }
//  /**
//   * Setter de la clé de protection de la session
//   * @param key Clé de protection de la session
//   */
//  public void setProtectionKey(String key)
//  {
//    this.setAttribute(CopyOfBid4WinSessionOld.PROTECTION_KEY, key);
//  }
//
//  /**
//   * Getter du status de connexion d'un compte utilisateur sur la session
//   * @return True si un compte utilisateur est connecte sur la session, false sinon
//   */
//  public boolean isConnected()
//  {
//    Boolean connected = (Boolean)this.getAttribute(CopyOfBid4WinSessionOld.CONNECTED);
//    if(connected == null)
//    {
//      return false;
//    }
//    return connected;
//  }
//  /**
//   * Cette méthode permet de définir qu'un compte utilisateur est connecté sur la
//   * session courante
//   */
//  public void connect()
//  {
//    this.setAttribute(CopyOfBid4WinSessionOld.CONNECTED, true);
//  }
//  /**
//   * Cette méthode permet de définir qu'aucun compte utilisateur n'est connecté
//   * sur la session courante
//   */
//  public void disconnect()
//  {
//    this.setAttribute(CopyOfBid4WinSessionOld.CONNECTED, false);
//  }
//
//  /**
//   * Getter de l'adresse IP de connexion à la session
//   * @return L'adresse IP de connexion à la session
//   */
//  public IpAddress getIpAddress()
//  {
//    return (IpAddress)this.getAttribute(CopyOfBid4WinSessionOld.IP_ADDRESS);
//  }
//  /**
//   * Setter de l'adresse IP de connexion à la session
//   * @param ipAddress Adresse IP de connexion à la session
//   * @throws UserException Si l'adresse IP en argument est nulle
//   */
//  public void setIpAddress(IpAddress ipAddress) throws UserException
//  {
//    UtilObject.checkNotNull(CopyOfBid4WinSessionOld.IP_ADDRESS, ipAddress,
//                            ConnectionRef.IP_MISSING_ERROR);
//    this.setAttribute(CopyOfBid4WinSessionOld.IP_ADDRESS, ipAddress);
//  }
//
//  /**
//   * Guetter du language de la session
//   * @return Le language de la session
//   */
//  public Language getLanguage()
//  {
//    return (Language)(this.getAttribute(CopyOfBid4WinSessionOld.LANGUAGE));
//  }
//  /**
//   * Setter du language de la session
//   * @param language Language à setter
//   * @throws ModelArgumentException Si le language en paramètre est nul
//   */
//  public void setLanguage(Language language) throws ModelArgumentException
//  {
//    UtilObject.checkNotNull(CopyOfBid4WinSessionOld.LANGUAGE, language);
//    this.setAttribute(CopyOfBid4WinSessionOld.LANGUAGE, language);
//  }
//  /**
//   * Renvoi true si un language est enregistré dans la session
//   * @return true si un language est enregistré dans la session
//   */
//  public boolean hasLanguage()
//  {
//    return this.getAttribute(CopyOfBid4WinSessionOld.LANGUAGE) != null;
//  }
//  /**
//   * Renvoi true si le langage en paramètre est le même que celui de la session
//   * @param language Le langage à tester
//   * @return true si le langage en paramètre est le même que celui de la session
//   */
//  public boolean hasLanguage(Language language)
//  {
//    if(language == null)
//    {
//      if(!this.hasLanguage())
//      {
//        return true;
//      }
//      return false;
//    }
//    return language.equals(this.getAttribute(CopyOfBid4WinSessionOld.LANGUAGE));
//  }
//}
