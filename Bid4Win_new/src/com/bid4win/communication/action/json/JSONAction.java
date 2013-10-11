package com.bid4win.communication.action.json;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.json.JSONObject;

import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.communication.action.BaseAction;
import com.bid4win.communication.json.model.JSONMessage;
import com.bid4win.communication.json.model.JSONResult;
import com.bid4win.communication.json.model.JSONMessage.Type;

/**
 * Classe d'action de renvoyant un objet de type JSONResult
 *
 * @author Maxime Ollagnier
 */
@InterceptorRefs({@InterceptorRef("jsonInterceptor"), @InterceptorRef("accessInterceptor")})
@Result(name = "success", type = "json", params = {"root", "jsonResult"})
public class JSONAction extends BaseAction
{
  /** Serial */
  private static final long serialVersionUID = -383629125218167927L;

  /** L'objet JSON retourné par la requête */
  public JSONResult jsonResult = new JSONResult();

  /**
   * Renvoi le paramètre de la requête dont le nom est donné en argument ou null
   * si celui-ci n'est pas présent tout en ajoutant dans l'objet JSON résultant
   * un message dans ce cas
   * @param parameterName Le nom du paramètre à retourner
   * @param messageRef Référence du message à utiliser en cas d'échec
   * @param type Type du message associé à l'échec de récupération du paramètre
   * @return Le paramètre de la requête dont le nom est donné en argument ou
   * null si non présent
   */
  public String getParameter(String parameterName, MessageRef messageRef, Type type)
  {
    try
    {
      return super.getParameter(parameterName, messageRef);
    }
    catch(UserException ex)
    {
      this.putMessage(ex, type);
      return null;
    }
  }

  /**
   * Renvoi la valeur du cookie de la requête dont le nom est donné en argument
   * ou null si celui-ci n'est pas présent tout en ajoutant dans l'objet JSON
   * résultant un message dans ce cas
   * @param cookieName Le nom du cookie dont la valeur est retournée
   * @param messageRef Référence du message à utiliser en cas d'échec
   * @param type Type du message associé à l'échec de récupération du cookie
   * @return La valeur du cookie de la requête dont le nom est donné en argument
   * ou null si non présent
   */
  public String getCookieValue(String cookieName, MessageRef messageRef, Type type)
  {
    try
    {
      return super.getCookieManager().getCookieValue(cookieName, messageRef);
    }
    catch(UserException ex)
    {
      this.putMessage(ex, type);
      return null;
    }
  }

  /**
   * Retourne l'objet JSON resultant de l'action
   * @return l'objet JSON resultant de l'action
   */
  public String getJsonResult()
  {
    return this.jsonResult.toString();
  }

  /**
   * Setter du flag de success
   * @param success Le nouveau flag de success
   */
  public void setSuccess(boolean success)
  {
    this.jsonResult.setSuccess(success);
  }

  /**
   * Insère dans l'objet JSON résultant l'objet JSON en paramètre
   * @param jsonObject L'objet JSON à insérer
   * @throws ModelArgumentException Si le paramètre est nul
   */
  public void putJSONObject(JSONObject jsonObject) throws ModelArgumentException
  {
    this.jsonResult.putJSONObject(jsonObject);
  }

  /**
   * Insère dans l'objet JSON résultant l'objet JSON en paramètre
   * @param objectName Le nom de l'object à insérer
   * @param jsonObject L'objet JSON à insérer
   * @throws ModelArgumentException Si le paramètre est nul
   */
  public void putJSONObject(String objectName, JSONObject jsonObject) throws ModelArgumentException
  {
    this.jsonResult.putJSONObject(objectName, jsonObject);
  }

  /**
   * Insère dans l'objet JSON résultant le message d'information en paramètre
   * @param message Le message d'information
   */
  public void putInfoMessage(String message)
  {
    this.putMessage(message, Type.INFO);
  }

  /**
   * Insère dans l'objet JSON résultant le message d'information en paramètre,
   * en précisant si l'i18n doit être utilisée
   * @param message Le message d'information
   * @param i18n L'indicateur d'utilisation de l'i18n
   */
  public void putInfoMessage(String message, boolean i18n)
  {
    this.putMessage(message, i18n, Type.INFO);
  }

  /**
   * Insère dans l'objet JSON résultant le message d'information
   * @param infoMessageRef référence du message d'information
   */
  public void putInfoMessage(MessageRef infoMessageRef)
  {
    this.putMessage(infoMessageRef, Type.INFO);
  }

  /**
   * Insère dans l'objet JSON résultant le message d'information
   * @param infoException Exception contenant la référence du message
   * d'information
   */
  public void putInfoMessage(UserException infoException)
  {
    this.putMessage(infoException, Type.INFO);
  }

  /**
   * Insère dans l'objet JSON résultant le message string en paramètre au niveau
   * de message de mise en garde
   * @param message Le message string
   */
  public void putWarnMessage(String message)
  {
    this.putMessage(message, Type.WARN);
  }

  /**
   * Insère dans l'objet JSON résultant le message string en paramètre au niveau
   * de message de mise en garde, en précisant si l'i18n doit être utilisée
   * @param message Le message string
   * @param i18n L'indicateur d'utilisation de l'i18n
   */
  public void putWarnMessage(String message, boolean i18n)
  {
    this.putMessage(message, i18n, Type.WARN);
  }

  /**
   * Insère dans l'objet JSON résultant le message de mise en garde
   * @param warnMessageRef référence du message de mise en garde
   */
  public void putWarnMessage(MessageRef warnMessageRef)
  {
    this.putMessage(warnMessageRef, Type.WARN);
  }

  /**
   * Insère dans l'objet JSON résultant le message de mise en garde
   * @param warnException Exception contenant la référence du message de mise en
   * garde
   */
  public void putWarnMessage(UserException warnException)
  {
    this.putMessage(warnException, Type.WARN);
  }

  /**
   * Insère dans l'objet JSON résultant le message string en paramètre au niveau
   * de message d'erreur
   * @param message Le message string
   */
  public void putErrorMessage(String message)
  {
    this.putMessage(message, Type.ERROR);
  }

  /**
   * Insère dans l'objet JSON résultant le message string en paramètre au niveau
   * de message d'ereur, en précisant si l'i18n doit être utilisée
   * @param message Le message string
   * @param i18n L'indicateur d'utilisation de l'i18n
   */
  public void putErrorMessage(String message, boolean i18n)
  {
    this.putMessage(message, i18n, Type.ERROR);
  }

  /**
   * Insère dans l'objet JSON résultant le message d'erreur
   * @param errorMessageRef référence du message d'erreur
   */
  public void putErrorMessage(MessageRef errorMessageRef)
  {
    this.putMessage(errorMessageRef, Type.ERROR);
  }

  /**
   * Insère dans l'objet JSON résultant le message d'erreur
   * @param errorException Exception contenant la référence du message d'erreur
   */
  public void putErrorMessage(UserException errorException)
  {
    this.putMessage(errorException, Type.ERROR);
  }

  /**
   * Insère dans l'objet JSON résultant le message de type choisi
   * @param message Message à ajouter
   * @param type Type du message à ajouter
   */
  public void putMessage(String message, Type type)
  {
    this.jsonResult.putJSONMessage(new JSONMessage(message, type));
  }

  /**
   * Insère dans l'objet JSON résultant le message de type choisi
   * @param message Message à ajouter
   * @param i18n L'indicateur d'utilisation de l'i18n
   * @param type Type du message à ajouter
   */
  public void putMessage(String message, boolean i18n, Type type)
  {
    this.jsonResult.putJSONMessage(new JSONMessage(message, type, i18n));
  }

  /**
   * Insère dans l'objet JSON résultant le message de type choisi
   * @param messageRef Référence du message à ajouter
   * @param type Type du message à ajouter
   */
  public void putMessage(MessageRef messageRef, Type type)
  {
    this.putMessage(messageRef.getCode(), true, type);
  }

  /**
   * Insère dans l'objet JSON résultant le message de type choisi
   * @param exception Exception contenant la référence du message à ajouter
   * @param type Type du message à ajouter
   */
  public void putMessage(UserException exception, Type type)
  {
    this.putMessage(exception.getMessageRef(), type);
  }

  /**
   * Renvoi true si un message d'information est déjà inséré dans l'objet JSON à
   * renvoyer dans la requête, false sinon
   * @return true si un message d'information existe
   */
  public boolean hasInformationMessage()
  {
    return this.jsonResult.hasInformations();
  }

  /**
   * Renvoi true si un message de mise en garde est déjà inséré dans l'objet
   * JSON à renvoyer dans la requête, false sinon
   * @return true si un message de mise en garde existe
   */
  public boolean hasWarningMessage()
  {
    return this.jsonResult.hasWarnings();
  }

  /**
   * Renvoi true si un message d'erreur est déjà inséré dans l'objet JSON à
   * renvoyer dans la requête, false sinon
   * @return true si un message d'erreur existe
   */
  public boolean hasErrorMessage()
  {
    return this.jsonResult.hasErrors();
  }
}
