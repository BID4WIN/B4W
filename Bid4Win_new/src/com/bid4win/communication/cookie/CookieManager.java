package com.bid4win.communication.cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.persistence.entity.locale.Language;

/**
 * Classe manager de cookies.
 *
 * @author Maxime Ollagnier
 */
public class CookieManager
{

  /** Nom du cookie contenant la cl� de protection de la session */
  private static final String PROTECTION_KEY = "protectionKey";
  /** Nom du cookie contenant le login du compte utilisateur � se rappeler */
  private static final String LOGIN = "login";
  /** Nom du cookie contenant l'identifiant du compte utilisateur � reconnecter */
  private static final String ACCOUNT_ID = "accountId";
  /** Nom du cookie contenant l'empreinte unique de re-connexion */
  private static final String FINGER_PRINT = "fingerPrint";
  /** Nom du cookie contenant le langage utilis� pr�c�demment */
  private static final String LANGUAGE = "langage";

  /** Dur�e de vie, en jours, par d�faut d'un cookie */
  private static final int DEFAULT_EXPIRY_IN_DAYS = 30;
  /** Dur�e de vie li�e � la session du browser */
  private static final int SESSION_EXPIRY = -1;

  /** Requ�te d'o� r�cup�rer les cookies */
  private HttpServletRequest request = null;
  /** R�ponse o� ajouter les cookies */
  private HttpServletResponse response = null;

  /**
   * Constructeur
   * @param request Requ�te d'o� r�cup�rer les cookies
   * @param response R�ponse o� ajouter les cookies
   */
  public CookieManager(HttpServletRequest request, HttpServletResponse response)
  {
    this.request = request;
    this.response = response;
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public String findProtectionKey()
  {
    return this.findCookieValue(PROTECTION_KEY);
  }
  /**
   *
   * TODO A COMMENTER
   * @param protectionKey TODO A COMMENTER
   */
  public void putProtectionKey(String protectionKey)
  {
    this.putCookie(PROTECTION_KEY, protectionKey, SESSION_EXPIRY);
  }
  /**
   *
   * TODO A COMMENTER
   */
  public void removeProtectionKey()
  {
    this.removeCookie(PROTECTION_KEY);
  }

  /**
   *
   * TODO A COMMENTER
   * @param login TODO A COMMENTER
   */
  public void putLogin(String login)
  {
    this.putCookie(LOGIN, login);
  }
  /**
   *
   * TODO A COMMENTER
   */
  public void removeLogin()
  {
    this.removeCookie(LOGIN);
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public String findAccountId()
  {
    return this.findCookieValue(ACCOUNT_ID);
  }
  /**
   *
   * TODO A COMMENTER
   * @param accountId TODO A COMMENTER
   */
  public void putAccountId(String accountId)
  {
    this.putCookie(ACCOUNT_ID, accountId);
  }
  /**
   *
   * TODO A COMMENTER
   */
  public void removeAccountId()
  {
    this.removeCookie(ACCOUNT_ID);
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public String findFingerPrint()
  {
    return this.findCookieValue(FINGER_PRINT);
  }
  /**
   *
   * TODO A COMMENTER
   * @param fingerPrint TODO A COMMENTER
   */
  public void putFingerPrint(String fingerPrint)
  {
    this.putCookie(FINGER_PRINT, fingerPrint);
  }
  /**
   *
   * TODO A COMMENTER
   */
  public void removeFingerPrint()
  {
    this.removeCookie(FINGER_PRINT);
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public String findLanguage()
  {
    return this.findCookieValue(LANGUAGE);
  }
  /**
   *
   * TODO A COMMENTER
   * @param language TODO A COMMENTER
   */
  public void putLanguage(Language language)
  {
    if(language != null)
    {
      this.putCookie(LANGUAGE, language.getCode());
    }
  }

  /**
   * Renvoi le cookie contenu dans la requ�te en fonction de son nom ou null si
   * le cookie n'existe pas
   * @param name Nom du cookie � r�cup�rer
   * @return Le cookie dont le nom est donn� en param�tre
   */
  protected Cookie findCookie(String name)
  {
    Cookie[] cookies = this.request.getCookies();
    if(cookies == null)
    {
      return null;
    }
    for(Cookie cookie : cookies)
    {
      if(cookie.getName().equals(name))
      {
        return cookie;
      }
    }
    return null;
  }

  /**
   * Renvoi la valeur du cookie contenu dans la requ�te en fonction de son nom
   * @param name Nom du cookie dont on veut r�cup�rer la valeur
   * @return La valeur du cookie dont le nom est donn� en param�tre
   */
  protected String findCookieValue(String name)
  {
    Cookie cookie = this.findCookie(name);
    if(cookie != null)
    {
      return cookie.getValue();
    }
    return null;
  }

  /**
   * Renvoi le cookie contenu dans la requ�te en fonction de son nom
   * @param name Nom du cookie � r�cup�rer
   * @param errorMessageRef R�f�rence du message d'erreur � utiliser en cas d'
   * �chec
   * @return Le cookie dont le nom est donn� en param�tre
   * @throws UserException Si le cookie n'a pas �t� trouv� dans la requ�te
   */
  protected Cookie getCookie(String name, MessageRef errorMessageRef) throws UserException
  {
    return UtilObject.checkNotNull("Cookie " + name, this.findCookie(name), errorMessageRef);
  }

  /**
   * Renvoi la valeur du cookie contenu dans la requ�te en fonction de son nom
   * @param name Nom du cookie dont on veut r�cup�rer la valeur
   * @param errorMessageRef R�f�rence du message d'erreur � utiliser en cas d'�chec
   * @return La valeur du cookie dont le nom est donn� en param�tre
   * @throws UserException Si le param�tre n'a pas �t� trouv� dans la requ�te
   */
  protected String getCookieValue(String name, MessageRef errorMessageRef) throws UserException
  {
    return this.getCookie(name, errorMessageRef).getValue();
  }
  /**
   * Renvoi la valeur enti�re du cookie contenu dans la requ�te en fonction de son
   * nom
   * @param name Nom du cookie dont on veut r�cup�rer la valeur
   * @param errorMessageRef R�f�rence du message d'erreur � utiliser en cas d'�chec
   * @return La valeur du cookie dont le nom est donn� en param�tre
   * @throws NumberFormatException Si le parse de la valeur String �choue
   * @throws UserException Si le param�tre n'a pas �t� trouv� dans la requ�te
   */
  protected int getCookieValueInt(String name, MessageRef errorMessageRef) throws NumberFormatException, UserException
  {
    return Integer.valueOf(this.getCookieValue(name, errorMessageRef));
  }
  /**
   * Renvoi la valeur Long du cookie contenu dans la requ�te en fonction de son nom
   * @param name Nom du cookie dont on veut r�cup�rer la valeur
   * @param errorMessageRef R�f�rence du message d'erreur � utiliser en cas d'�chec
   * @return La valeur du cookie dont le nom est donn� en param�tre
   * @throws NumberFormatException Si le parse de la valeur String �choue
   * @throws UserException Si le param�tre n'a pas �t� trouv� dans la requ�te
   */
  protected long getCookieValueLong(String name, MessageRef errorMessageRef) throws NumberFormatException, UserException
  {
    return Long.valueOf(this.getCookieValue(name, errorMessageRef));
  }
  /**
   * Renvoi la valeur d�cimale du cookie contenu dans la requ�te en fonction de son
   * nom
   * @param name Nom du cookie dont on veut r�cup�rer la valeur
   * @param errorMessageRef R�f�rence du message d'erreur � utiliser en cas d'�chec
   * @return La valeur du cookie dont le nom est donn� en param�tre
   * @throws NumberFormatException Si le parse de la valeur String �choue
   * @throws UserException Si le param�tre n'a pas �t� trouv� dans la requ�te
   */
  protected double getCookieValueDouble(String name, MessageRef errorMessageRef) throws NumberFormatException, UserException
  {
    return Double.valueOf(this.getCookieValue(name, errorMessageRef));
  }
  /**
   * Renvoi la valeur bool�enne du cookie contenu dans la requ�te en fonction de
   * son nom
   * @param name Nom du cookie dont on veut r�cup�rer la valeur
   * @param errorMessageRef R�f�rence du message d'erreur � utiliser en cas d'�chec
   * @return La valeur du cookie dont le nom est donn� en param�tre
   * @throws UserException Si le param�tre n'a pas �t� trouv� dans la requ�te
   */
  protected boolean getCookieValueBool(String name, MessageRef errorMessageRef) throws UserException
  {
    return Boolean.valueOf(this.getCookieValue(name, errorMessageRef));
  }

  /**
   * Ajoute un cookie dans la r�ponse HTTP de la requ�te. Le cookie ajout� aura
   * la dur�e de vie par d�faut d'un cookie.
   * @param name Nom du cookie � ajouter
   * @param value Valeur du cookie � ajouter
   */
  protected void putCookie(String name, String value)
  {
    this.putCookie(name, value, CookieManager.DEFAULT_EXPIRY_IN_DAYS);
  }

  /**
   * Ajoute un cookie dans la r�ponse HTTP de la requ�te
   * @param name Nom du cookie � ajouter
   * @param value Valeur du cookie � ajouter
   * @param expiryInDays Dur�e de vie en jours du cookie
   */
  protected void putCookie(String name, String value, int expiryInDays)
  {
    Cookie cookie = new Cookie(name, value);
    cookie.setMaxAge(expiryInDays * 24 * 60 * 60);
    this.response.addCookie(cookie);
  }

  /**
   * Supprime un cookie directement dans le navigateur
   * @param name Nom du cookie � supprimer
   */
  protected void removeCookie(String name)
  {
    this.putCookie(name, "", 0);
  }
}
