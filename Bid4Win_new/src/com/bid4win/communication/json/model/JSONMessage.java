package com.bid4win.communication.json.model;

import org.json.JSONObject;

/**
 * Classe de message JSON. Utilisée pour définir un message destiné à transiter
 * via une requête JSON
 *
 * @author Maxime Ollagnier
 */
public class JSONMessage extends JSONObject
{
  /** Nom du champ message */
  private static final String MESSAGE = "message";
  /** Nom du champ type du message */
  private static final String TYPE = "type";
  /** Nom du champ indicateur d'i18n */
  private static final String I18N = "i18n";

  /** Message par défaut */
  private static final String DEFAULT_MESSAGE = "Empty message";
  /** Niveau d'un message par défaut */
  private static final Type DEFAULT_TYPE = Type.INFO;
  /** Utilisation de l'i18n par défaut */
  private static final boolean DEFAULT_I18N = false;

  /** Énumération des types de message */
  public static enum Type
  {
    /** Niveau d'un message d'information */
    INFO,
    /** Niveau d'un message de mise en garde */
    WARN,
    /** Niveau d'un message d'erreur */
    ERROR
  }

  /**
   * Constructeur par défaut
   */
  @SuppressWarnings("unused")
  private JSONMessage()
  {
    this(JSONMessage.DEFAULT_MESSAGE, JSONMessage.DEFAULT_TYPE, JSONMessage.DEFAULT_I18N);
  }

  /**
   * Constructeur
   * @param message Le message string
   */
  public JSONMessage(String message)
  {
    this(message, JSONMessage.DEFAULT_TYPE, JSONMessage.DEFAULT_I18N);
  }

  /**
   * Constructeur
   * @param message Le message string
   * @param type Le type du message
   */
  public JSONMessage(String message, Type type)
  {
    this(message, type, JSONMessage.DEFAULT_I18N);
  }

  /**
   * Constructeur
   * @param message Le message string
   * @param i18n L'indicateur d'utilisation de l'i18n
   */
  public JSONMessage(String message, boolean i18n)
  {
    this(message, JSONMessage.DEFAULT_TYPE, i18n);
  }

  /**
   * Constructeur
   * @param message Le message string
   * @param type Le type du message
   * @param i18n L'indicateur d'utilisation de l'i18n
   */
  public JSONMessage(String message, Type type, boolean i18n)
  {
    super();
    this.setMessage(message);
    this.setType(type);
    this.setI18n(i18n);
  }

  /**
   * Guetter du message string
   * @return Le message string
   */
  public String getMessage()
  {
    return this.getString(JSONMessage.MESSAGE);
  }

  /**
   * Guetter du type du message
   * @return type du message
   */
  public Type getType()
  {
    return Type.valueOf(this.getString(JSONMessage.TYPE));
  }

  /**
   * Guetter de l'indicateur d'i18n
   * @return l'indicateur d'i18n
   */
  public boolean isI18n()
  {
    return this.getBoolean(JSONMessage.I18N);
  }

  /**
   * Setter du message string
   * @param message Le nouveau message string
   */
  protected void setMessage(String message)
  {
    this.put(JSONMessage.MESSAGE, message);
  }

  /**
   * Setter du type du message
   * @param type Le nouveau type du message
   */
  protected void setType(Type type)
  {
    this.put(JSONMessage.TYPE, type.toString());
  }

  /**
   * Setter de l'indicateur d'i18n
   * @param i18n Le nouvel indicateur d'i18n
   */
  protected void setI18n(boolean i18n)
  {
    this.put(JSONMessage.I18N, i18n);
  }
}
