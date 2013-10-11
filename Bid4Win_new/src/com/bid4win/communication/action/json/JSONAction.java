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

  /** L'objet JSON retourn� par la requ�te */
  public JSONResult jsonResult = new JSONResult();

  /**
   * Renvoi le param�tre de la requ�te dont le nom est donn� en argument ou null
   * si celui-ci n'est pas pr�sent tout en ajoutant dans l'objet JSON r�sultant
   * un message dans ce cas
   * @param parameterName Le nom du param�tre � retourner
   * @param messageRef R�f�rence du message � utiliser en cas d'�chec
   * @param type Type du message associ� � l'�chec de r�cup�ration du param�tre
   * @return Le param�tre de la requ�te dont le nom est donn� en argument ou
   * null si non pr�sent
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
   * Renvoi la valeur du cookie de la requ�te dont le nom est donn� en argument
   * ou null si celui-ci n'est pas pr�sent tout en ajoutant dans l'objet JSON
   * r�sultant un message dans ce cas
   * @param cookieName Le nom du cookie dont la valeur est retourn�e
   * @param messageRef R�f�rence du message � utiliser en cas d'�chec
   * @param type Type du message associ� � l'�chec de r�cup�ration du cookie
   * @return La valeur du cookie de la requ�te dont le nom est donn� en argument
   * ou null si non pr�sent
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
   * Ins�re dans l'objet JSON r�sultant l'objet JSON en param�tre
   * @param jsonObject L'objet JSON � ins�rer
   * @throws ModelArgumentException Si le param�tre est nul
   */
  public void putJSONObject(JSONObject jsonObject) throws ModelArgumentException
  {
    this.jsonResult.putJSONObject(jsonObject);
  }

  /**
   * Ins�re dans l'objet JSON r�sultant l'objet JSON en param�tre
   * @param objectName Le nom de l'object � ins�rer
   * @param jsonObject L'objet JSON � ins�rer
   * @throws ModelArgumentException Si le param�tre est nul
   */
  public void putJSONObject(String objectName, JSONObject jsonObject) throws ModelArgumentException
  {
    this.jsonResult.putJSONObject(objectName, jsonObject);
  }

  /**
   * Ins�re dans l'objet JSON r�sultant le message d'information en param�tre
   * @param message Le message d'information
   */
  public void putInfoMessage(String message)
  {
    this.putMessage(message, Type.INFO);
  }

  /**
   * Ins�re dans l'objet JSON r�sultant le message d'information en param�tre,
   * en pr�cisant si l'i18n doit �tre utilis�e
   * @param message Le message d'information
   * @param i18n L'indicateur d'utilisation de l'i18n
   */
  public void putInfoMessage(String message, boolean i18n)
  {
    this.putMessage(message, i18n, Type.INFO);
  }

  /**
   * Ins�re dans l'objet JSON r�sultant le message d'information
   * @param infoMessageRef r�f�rence du message d'information
   */
  public void putInfoMessage(MessageRef infoMessageRef)
  {
    this.putMessage(infoMessageRef, Type.INFO);
  }

  /**
   * Ins�re dans l'objet JSON r�sultant le message d'information
   * @param infoException Exception contenant la r�f�rence du message
   * d'information
   */
  public void putInfoMessage(UserException infoException)
  {
    this.putMessage(infoException, Type.INFO);
  }

  /**
   * Ins�re dans l'objet JSON r�sultant le message string en param�tre au niveau
   * de message de mise en garde
   * @param message Le message string
   */
  public void putWarnMessage(String message)
  {
    this.putMessage(message, Type.WARN);
  }

  /**
   * Ins�re dans l'objet JSON r�sultant le message string en param�tre au niveau
   * de message de mise en garde, en pr�cisant si l'i18n doit �tre utilis�e
   * @param message Le message string
   * @param i18n L'indicateur d'utilisation de l'i18n
   */
  public void putWarnMessage(String message, boolean i18n)
  {
    this.putMessage(message, i18n, Type.WARN);
  }

  /**
   * Ins�re dans l'objet JSON r�sultant le message de mise en garde
   * @param warnMessageRef r�f�rence du message de mise en garde
   */
  public void putWarnMessage(MessageRef warnMessageRef)
  {
    this.putMessage(warnMessageRef, Type.WARN);
  }

  /**
   * Ins�re dans l'objet JSON r�sultant le message de mise en garde
   * @param warnException Exception contenant la r�f�rence du message de mise en
   * garde
   */
  public void putWarnMessage(UserException warnException)
  {
    this.putMessage(warnException, Type.WARN);
  }

  /**
   * Ins�re dans l'objet JSON r�sultant le message string en param�tre au niveau
   * de message d'erreur
   * @param message Le message string
   */
  public void putErrorMessage(String message)
  {
    this.putMessage(message, Type.ERROR);
  }

  /**
   * Ins�re dans l'objet JSON r�sultant le message string en param�tre au niveau
   * de message d'ereur, en pr�cisant si l'i18n doit �tre utilis�e
   * @param message Le message string
   * @param i18n L'indicateur d'utilisation de l'i18n
   */
  public void putErrorMessage(String message, boolean i18n)
  {
    this.putMessage(message, i18n, Type.ERROR);
  }

  /**
   * Ins�re dans l'objet JSON r�sultant le message d'erreur
   * @param errorMessageRef r�f�rence du message d'erreur
   */
  public void putErrorMessage(MessageRef errorMessageRef)
  {
    this.putMessage(errorMessageRef, Type.ERROR);
  }

  /**
   * Ins�re dans l'objet JSON r�sultant le message d'erreur
   * @param errorException Exception contenant la r�f�rence du message d'erreur
   */
  public void putErrorMessage(UserException errorException)
  {
    this.putMessage(errorException, Type.ERROR);
  }

  /**
   * Ins�re dans l'objet JSON r�sultant le message de type choisi
   * @param message Message � ajouter
   * @param type Type du message � ajouter
   */
  public void putMessage(String message, Type type)
  {
    this.jsonResult.putJSONMessage(new JSONMessage(message, type));
  }

  /**
   * Ins�re dans l'objet JSON r�sultant le message de type choisi
   * @param message Message � ajouter
   * @param i18n L'indicateur d'utilisation de l'i18n
   * @param type Type du message � ajouter
   */
  public void putMessage(String message, boolean i18n, Type type)
  {
    this.jsonResult.putJSONMessage(new JSONMessage(message, type, i18n));
  }

  /**
   * Ins�re dans l'objet JSON r�sultant le message de type choisi
   * @param messageRef R�f�rence du message � ajouter
   * @param type Type du message � ajouter
   */
  public void putMessage(MessageRef messageRef, Type type)
  {
    this.putMessage(messageRef.getCode(), true, type);
  }

  /**
   * Ins�re dans l'objet JSON r�sultant le message de type choisi
   * @param exception Exception contenant la r�f�rence du message � ajouter
   * @param type Type du message � ajouter
   */
  public void putMessage(UserException exception, Type type)
  {
    this.putMessage(exception.getMessageRef(), type);
  }

  /**
   * Renvoi true si un message d'information est d�j� ins�r� dans l'objet JSON �
   * renvoyer dans la requ�te, false sinon
   * @return true si un message d'information existe
   */
  public boolean hasInformationMessage()
  {
    return this.jsonResult.hasInformations();
  }

  /**
   * Renvoi true si un message de mise en garde est d�j� ins�r� dans l'objet
   * JSON � renvoyer dans la requ�te, false sinon
   * @return true si un message de mise en garde existe
   */
  public boolean hasWarningMessage()
  {
    return this.jsonResult.hasWarnings();
  }

  /**
   * Renvoi true si un message d'erreur est d�j� ins�r� dans l'objet JSON �
   * renvoyer dans la requ�te, false sinon
   * @return true si un message d'erreur existe
   */
  public boolean hasErrorMessage()
  {
    return this.jsonResult.hasErrors();
  }
}
