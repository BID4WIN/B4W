package com.bid4win.communication.cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;

/**
 * Classe manager de cookies.
 *
 * @author Maxime Ollagnier
 */
public class CookieManager
{
  /** Nom du cookie contenant l'identifiant du compte utilisateur */
  public static final String ACCOUNT_ID_COOKIE_NAME = "accountId";
  /** Nom du cookie contenant le login */
  public static final String LOGIN_COOKIE_NAME = "login";
  /** Nom du cookie contenant la fingerPrint */
  public static final String FINGERPRINT_COOKIE_NAME = "fingerPrint";
  /** Nom du cookie contenant le langage */
  public static final String LANGUAGE_COOKIE_NAME = "langage";

  /** Durée de vie, en jours, par défaut d'un cookie */
  private static final int DEFAULT_EXPIRY_IN_DAYS = 30;

  /** Requête d'où récupérer les cookies */
  private HttpServletRequest request = null;

  /** Réponse où ajouter les cookies */
  private HttpServletResponse response = null;

  /**
   * Constructeur
   * @param request Requête d'où récupérer les cookies
   * @param response Réponse où ajouter les cookies
   */
  public CookieManager(HttpServletRequest request, HttpServletResponse response)
  {
    this.request = request;
    this.response = response;
  }

  /**
   * Renvoi le cookie contenu dans la requête en fonction de son nom ou null si
   * le cookie n'existe pas
   * @param cookieName Nom du cookie à récupérer
   * @return Le cookie dont le nom est donné en paramètre
   */
  public Cookie findCookie(String cookieName)
  {
    Cookie resCookie = null;
    for(int i = 0 ; i < this.request.getCookies().length && resCookie == null ; i++)
    {
      Cookie cookie = this.request.getCookies()[i];
      if(cookie.getName().equals(cookieName))
      {
        resCookie = cookie;
      }
    }
    return resCookie;
  }

  /**
   * Renvoi la valeur String du cookie contenu dans la requête en fonction de
   * son nom
   * @param cookieName Nom du cookie dont on veut récupérer la valeur
   * @return La valeur du cookie dont le nom est donné en paramètre
   */
  public String findCookieValue(String cookieName)
  {
    Cookie cookie = this.findCookie(cookieName);
    if(cookie != null)
    {
      return cookie.getValue();
    }
    return null;
  }

  /**
   * Renvoi le cookie contenu dans la requête en fonction de son nom
   * @param cookieName Nom du cookie à récupérer
   * @param errorMessageRef Référence du message d'erreur à utiliser en cas d'
   * échec
   * @return Le cookie dont le nom est donné en paramètre
   * @throws UserException Si le cookie n'a pas été trouvé dans la requête
   */
  public Cookie getCookie(String cookieName, MessageRef errorMessageRef) throws UserException
  {
    return UtilObject.checkNotNull("Cookie " + cookieName, this.findCookie(cookieName), errorMessageRef);
  }

  /**
   * Renvoi la valeur String du cookie contenu dans la requête en fonction de
   * son nom
   * @param cookieName Nom du cookie dont on veut récupérer la valeur
   * @param errorMessageRef Référence du message d'erreur à utiliser en cas d'
   * échec
   * @return La valeur du cookie dont le nom est donné en paramètre
   * @throws UserException Si le paramètre n'a pas été trouvé dans la requête
   */
  public String getCookieValue(String cookieName, MessageRef errorMessageRef) throws UserException
  {
    return this.getCookie(cookieName, errorMessageRef).getValue();
  }

  /**
   * Renvoi la valeur String du cookie contenu dans la requête en fonction de
   * son nom
   * @param cookieName Nom du cookie dont on veut récupérer la valeur
   * @param errorMessageRef Référence du message d'erreur à utiliser en cas d'
   * échec
   * @return La valeur du cookie dont le nom est donné en paramètre
   * @throws UserException Si le paramètre n'a pas été trouvé dans la requête
   */
  public String getCookieValueString(String cookieName, MessageRef errorMessageRef) throws UserException
  {
    return this.getCookie(cookieName, errorMessageRef).getValue();
  }

  /**
   * Renvoi la valeur int du cookie contenu dans la requête en fonction de son
   * nom
   * @param cookieName Nom du cookie dont on veut récupérer la valeur
   * @param errorMessageRef Référence du message d'erreur à utiliser en cas d'
   * échec
   * @return La valeur du cookie dont le nom est donné en paramètre
   * @throws NumberFormatException Si le parse de la valeur String échoue
   * @throws UserException Si le paramètre n'a pas été trouvé dans la requête
   */
  public int getCookieValueInt(String cookieName, MessageRef errorMessageRef) throws NumberFormatException, UserException
  {
    return Integer.valueOf(this.getCookieValue(cookieName, errorMessageRef)).intValue();
  }

  /**
   * Renvoi la valeur Long du cookie contenu dans la requête en fonction de son
   * nom
   * @param cookieName Nom du cookie dont on veut récupérer la valeur
   * @param errorMessageRef Référence du message d'erreur à utiliser en cas d'
   * échec
   * @return La valeur du cookie dont le nom est donné en paramètre
   * @throws NumberFormatException Si le parse de la valeur String échoue
   * @throws UserException Si le paramètre n'a pas été trouvé dans la requête
   */
  public long getCookieValueLong(String cookieName, MessageRef errorMessageRef) throws NumberFormatException, UserException
  {
    return Long.valueOf(this.getCookieValue(cookieName, errorMessageRef));
  }

  /**
   * Renvoi la valeur Double du cookie contenu dans la requête en fonction de
   * son nom
   * @param cookieName Nom du cookie dont on veut récupérer la valeur
   * @param errorMessageRef Référence du message d'erreur à utiliser en cas d'
   * échec
   * @return La valeur du cookie dont le nom est donné en paramètre
   * @throws NumberFormatException Si le parse de la valeur String échoue
   * @throws UserException Si le paramètre n'a pas été trouvé dans la requête
   */
  public double getCookieValueDouble(String cookieName, MessageRef errorMessageRef) throws NumberFormatException, UserException
  {
    return Double.valueOf(this.getCookieValue(cookieName, errorMessageRef));
  }

  /**
   * Renvoi la valeur booléenne du cookie contenu dans la requête en fonction de
   * son nom
   * @param cookieName Nom du cookie dont on veut récupérer la valeur
   * @param errorMessageRef Référence du message d'erreur à utiliser en cas d'
   * échec
   * @return La valeur du cookie dont le nom est donné en paramètre
   * @throws UserException Si le paramètre n'a pas été trouvé dans la requête
   */
  public boolean getCookieValueBool(String cookieName, MessageRef errorMessageRef) throws UserException
  {
    return Boolean.valueOf(this.getCookieValue(cookieName, errorMessageRef));
  }

  /**
   * Ajoute un cookie dans la réponse HTTP de la requête. Le cookie ajouté aura
   * la durée de vie par défaut d'un cookie.
   * @param name Nom du cookie à ajouter
   * @param value Valeur du cookie à ajouter
   */
  public void putCookie(String name, String value)
  {
    this.putCookie(name, value, CookieManager.DEFAULT_EXPIRY_IN_DAYS);
  }

  /**
   * Ajoute un cookie dans la réponse HTTP de la requête
   * @param name Nom du cookie à ajouter
   * @param value Valeur du cookie à ajouter
   * @param expiryInDays Durée de vie en jours du cookie
   */
  public void putCookie(String name, String value, int expiryInDays)
  {
    Cookie cookie = new Cookie(name, value);
    cookie.setMaxAge(expiryInDays * 24 * 60 * 60);
    this.response.addCookie(cookie);
  }

  /**
   * Supprime un cookie directement dans le navigateur
   * @param name Nom du cookie à supprimer
   */
  public void removeCookie(String name)
  {
    this.putCookie(name, "", 0);
  }
}
