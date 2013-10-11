package com.bid4win.communication.json.factory.account.security;

import org.json.JSONObject;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.ConnectionRef;
import com.bid4win.commons.persistence.entity.account.security.Password;
import com.bid4win.communication.action.json.JSONAction;
import com.bid4win.communication.json.factory.JSONFactory;
import com.bid4win.communication.json.model.JSONMessage.Type;

/**
 * Fabrique de structures JSON<BR>
 * <BR>
 *
 * @author Maxime Ollagnier
 */
public class JSONPasswordFactory extends JSONFactory<Password>
{
  /** Nom de l'attribut password dans la structure JSON */
  public final static String PASSWORD = "password";

  /** C'est l'instance utilisée comme singleton */
  private final static JSONPasswordFactory instance = new JSONPasswordFactory();

  /**
   * @return L'unique instance de la fabrique
   */
  public static JSONPasswordFactory getInstance()
  {
    return JSONPasswordFactory.instance;
  }

  /**
   * Constructeur protégé car la fabrique doit être accédée comme un singleton
   */
  protected JSONPasswordFactory()
  {
    super();
  }

  /**
   * Remplit la structure JSON avec les données de l'objet en paramètre
   *
   * @param json Structure JSON à remplir à partir des données de l'objet
   * @param object Objet avec lequel remplir la structure JSON en paramètre
   */
  @Override
  public void fill(JSONObject json, Password object)
  {
    json.put(JSONPasswordFactory.PASSWORD, "");
  }

  /**
   * @param object {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.communication.json.factory.JSONFactory#getId(Object)
   */
  @Override
  public String getId(Password object)
  {
    return null;
  }

  /**
   * Construit l'objet à partir de l'action spécifiée et ajoute le bon message
   * avec le type précisé si une erreur survient lors de sa création.
   *
   * @param action Action concernée
   * @param type Type de message à ajouter en cas d'erreur
   * @return L'objet créé
   */
  public Password get(JSONAction action, Type type)
  {
    String value = this.getValue(action, type);
    if(value != null)
    {
      try
      {
        return new Password(value);
      }
      catch(UserException ex)
      {
        action.putMessage(ex, type);
      }
    }
    return null;
  }

  /**
   * Récupère le paramètre dans l'action spécifiée et ajoute le bon message avec
   * le type précisé s'il n'est pas trouvé.
   *
   * @param action Action concernée
   * @param type Type de message à ajouter si le paramètre n'est pas trouvé.
   * @return La valeur du paramètre désiré
   */
  public String getValue(JSONAction action, Type type)
  {
    return action.getParameter(JSONPasswordFactory.PASSWORD, ConnectionRef.CONNECTION_PASSWORD_MISSING_ERROR, type);
  }
}
