package com.bid4win.communication.json.model;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.ModelArgumentException;

/**
 * Class d'objet JSON resultant d'une action et �tant destin� � �tre renvoy�
 * dans la r�ponse d'une requ�te
 * 
 * @author Maxime Ollagnier
 */
public class JSONResult extends JSONObject
{
  /** Nom du flag de success du r�sultat */
  private static String SUCCESS = "success";
  /** Nom de la table d'objets JSON du r�sultat */
  private static String JSON_OBJECT_MAP = "jsonObjectMap";
  /** Nom du tableau de messages d'information du r�sultat */
  private static String JSON_INFO_MESSAGE_ARRAY = "jsonInfoMessageArray";
  /** Nom du tableau de messages de mise en garde du r�sultat */
  private static String JSON_WARN_MESSAGE_ARRAY = "jsonWarnMessageArray";
  /** Nom du tableau de messages d'erreur du r�sultat */
  private static String JSON_ERROR_MESSAGE_ARRAY = "jsonErrorMessageArray";

  /** Nom standard d'un objet JSON ins�r� */
  private static String OBJECT_STD = "object";

  /**
   * Constructeur
   */
  public JSONResult()
  {
    // Initialise le flag de success
    this.setSuccess(false);
    // Initialise la table d'objets JSON
    this.put(JSONResult.JSON_OBJECT_MAP, new JSONMap());
    // Initialise le tableau de messages JSON d'information
    this.put(JSONResult.JSON_INFO_MESSAGE_ARRAY, new JSONArray());
    // Initialise le tableau de messages JSON de mise en garde
    this.put(JSONResult.JSON_WARN_MESSAGE_ARRAY, new JSONArray());
    // Initialise le tableau de messages JSON d'erreur
    this.put(JSONResult.JSON_ERROR_MESSAGE_ARRAY, new JSONArray());
  }

  /**
   * Setter du flag de success
   * @param success Le nouveau flag de success
   */
  public void setSuccess(boolean success)
  {
    this.put(JSONResult.SUCCESS, success);
  }

  /**
   * Ins�re dans l'objet JSON r�sultant l'objet JSON en param�tre
   * @param objectName Le nom de l'object � ins�rer
   * @param jsonObject L'objet JSON � ins�rer
   * @throws ModelArgumentException Si le param�tre est nul
   */
  public void putJSONObject(String objectName, JSONObject jsonObject) throws ModelArgumentException
  {
    UtilString.checkNotEmpty("objectName", objectName);
    UtilObject.checkNotNull("jsonObject", jsonObject);
    if(this.getJSONObjectMap().has(objectName))
    {
      throw new ModelArgumentException("Error trying to add JSONObject in JSONResult because [" + objectName + " : JSONObject] already inserted.");
    }
    this.getJSONObjectMap().put(objectName, jsonObject);
  }

  /**
   * Ins�re dans l'objet JSON r�sultant l'objet JSON en param�tre
   * @param jsonObject L'objet JSON � ins�rer
   * @throws ModelArgumentException Si le param�tre est nul
   */
  public void putJSONObject(JSONObject jsonObject) throws ModelArgumentException
  {
    this.putJSONObject(JSONResult.OBJECT_STD, jsonObject);
  }

  /**
   * Ins�re dans l'objet JSON r�sultant le message JSON en param�tre
   * @param jsonMessage Le message JSON � ins�rer
   */
  public void putJSONMessage(JSONMessage jsonMessage)
  {
    switch(jsonMessage.getType())
    {
      case INFO:
        this.getJSONInfoMessageArray().put(jsonMessage);
        break;
      case WARN:
        this.getJSONWarnMessageArray().put(jsonMessage);
        break;
      case ERROR:
        this.setSuccess(false);
        this.getJSONErrorMessageArray().put(jsonMessage);
        break;
    }
  }

  /**
   * Renvoi true si un message d'information est d�j� ins�r� dans l'objet JSON �
   * renvoyer dans la requ�te, false sinon
   * @return true si un message d'information existe
   */
  public boolean hasInformations()
  {
    return this.getJSONInfoMessageArray().length() > 0;
  }

  /**
   * Renvoi true si un message de mise en garde est d�j� ins�r� dans l'objet
   * JSON � renvoyer dans la requ�te, false sinon
   * @return true si un message de mise en garde existe
   */
  public boolean hasWarnings()
  {
    return this.getJSONWarnMessageArray().length() > 0;
  }

  /**
   * Renvoi true si un message d'erreur est d�j� ins�r� dans l'objet JSON �
   * renvoyer dans la requ�te, false sinon
   * @return true si un message d'erreur existe
   */
  public boolean hasErrors()
  {
    return this.getJSONErrorMessageArray().length() > 0;
  }

  /**
   * Renvoi la table d'objet JSON
   * @return la table d'objet JSON
   */
  private JSONObject getJSONObjectMap()
  {
    JSONObject jsonObjectMap = this.getJSONObject(JSONResult.JSON_OBJECT_MAP);
    if(jsonObjectMap == null)
    {
      jsonObjectMap = new JSONObject();
      this.put(JSONResult.JSON_OBJECT_MAP, jsonObjectMap);
    }
    return jsonObjectMap;
  }

  /**
   * Renvoi le tableau de messages JSON d'information
   * @return le tableau de messages JSON d'information
   */
  private JSONArray getJSONInfoMessageArray()
  {
    JSONArray jsonMessageArray = this.getJSONArray(JSONResult.JSON_INFO_MESSAGE_ARRAY);
    if(jsonMessageArray == null)
    {
      jsonMessageArray = new JSONArray();
      this.put(JSONResult.JSON_INFO_MESSAGE_ARRAY, jsonMessageArray);
    }
    return jsonMessageArray;
  }

  /**
   * Renvoi le tableau de messages JSON de mise en garde
   * @return le tableau de messages JSON de mise en garde
   */
  private JSONArray getJSONWarnMessageArray()
  {
    JSONArray jsonMessageArray = this.getJSONArray(JSONResult.JSON_WARN_MESSAGE_ARRAY);
    if(jsonMessageArray == null)
    {
      jsonMessageArray = new JSONArray();
      this.put(JSONResult.JSON_WARN_MESSAGE_ARRAY, jsonMessageArray);
    }
    return jsonMessageArray;
  }

  /**
   * Renvoi le tableau de messages JSON d'erreur
   * @return le tableau de messages JSON d'erreur
   */
  private JSONArray getJSONErrorMessageArray()
  {
    JSONArray jsonMessageArray = this.getJSONArray(JSONResult.JSON_ERROR_MESSAGE_ARRAY);
    if(jsonMessageArray == null)
    {
      jsonMessageArray = new JSONArray();
      this.put(JSONResult.JSON_ERROR_MESSAGE_ARRAY, jsonMessageArray);
    }
    return jsonMessageArray;
  }
}
