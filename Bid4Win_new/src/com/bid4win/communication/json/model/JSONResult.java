package com.bid4win.communication.json.model;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.ModelArgumentException;

/**
 * Class d'objet JSON resultant d'une action et étant destiné à être renvoyé
 * dans la réponse d'une requête
 * 
 * @author Maxime Ollagnier
 */
public class JSONResult extends JSONObject
{
  /** Nom du flag de success du résultat */
  private static String SUCCESS = "success";
  /** Nom de la table d'objets JSON du résultat */
  private static String JSON_OBJECT_MAP = "jsonObjectMap";
  /** Nom du tableau de messages d'information du résultat */
  private static String JSON_INFO_MESSAGE_ARRAY = "jsonInfoMessageArray";
  /** Nom du tableau de messages de mise en garde du résultat */
  private static String JSON_WARN_MESSAGE_ARRAY = "jsonWarnMessageArray";
  /** Nom du tableau de messages d'erreur du résultat */
  private static String JSON_ERROR_MESSAGE_ARRAY = "jsonErrorMessageArray";

  /** Nom standard d'un objet JSON inséré */
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
   * Insère dans l'objet JSON résultant l'objet JSON en paramètre
   * @param objectName Le nom de l'object à insérer
   * @param jsonObject L'objet JSON à insérer
   * @throws ModelArgumentException Si le paramètre est nul
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
   * Insère dans l'objet JSON résultant l'objet JSON en paramètre
   * @param jsonObject L'objet JSON à insérer
   * @throws ModelArgumentException Si le paramètre est nul
   */
  public void putJSONObject(JSONObject jsonObject) throws ModelArgumentException
  {
    this.putJSONObject(JSONResult.OBJECT_STD, jsonObject);
  }

  /**
   * Insère dans l'objet JSON résultant le message JSON en paramètre
   * @param jsonMessage Le message JSON à insérer
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
   * Renvoi true si un message d'information est déjà inséré dans l'objet JSON à
   * renvoyer dans la requête, false sinon
   * @return true si un message d'information existe
   */
  public boolean hasInformations()
  {
    return this.getJSONInfoMessageArray().length() > 0;
  }

  /**
   * Renvoi true si un message de mise en garde est déjà inséré dans l'objet
   * JSON à renvoyer dans la requête, false sinon
   * @return true si un message de mise en garde existe
   */
  public boolean hasWarnings()
  {
    return this.getJSONWarnMessageArray().length() > 0;
  }

  /**
   * Renvoi true si un message d'erreur est déjà inséré dans l'objet JSON à
   * renvoyer dans la requête, false sinon
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
