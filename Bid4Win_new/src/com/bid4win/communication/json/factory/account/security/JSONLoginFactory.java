package com.bid4win.communication.json.factory.account.security;

import org.json.JSONObject;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.ConnectionRef;
import com.bid4win.commons.persistence.entity.account.security.Login;
import com.bid4win.communication.action.json.JSONAction;
import com.bid4win.communication.json.factory.JSONFactory;
import com.bid4win.communication.json.model.JSONMessage.Type;

/**
 * Fabrique de structures JSON<BR>
 * <BR>
 *
 * @author Maxime Ollagnier
 */
public class JSONLoginFactory extends JSONFactory<Login>
{
  /** Nom de l'attribut login dans la structure JSON */
  public final static String LOGIN = "login";

  /** C'est l'instance utilisée comme singleton */
  private final static JSONLoginFactory instance = new JSONLoginFactory();

  /**
   * @return L'unique instance de la fabrique
   */
  public static JSONLoginFactory getInstance()
  {
    return JSONLoginFactory.instance;
  }

  /**
   * Constructeur protégé car la fabrique doit être accédée comme un singleton
   */
  protected JSONLoginFactory()
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
  public void fill(JSONObject json, Login object)
  {
    json.put(JSONLoginFactory.LOGIN, object.getValue());
  }

  /**
   * @param object {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.communication.json.factory.JSONFactory#getId(Object)
   */
  @Override
  public String getId(Login object)
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
  public Login get(JSONAction action, Type type)
  {
    String value = this.getValue(action, type);
    if(value != null)
    {
      try
      {
        return new Login(value);
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
    return action.getParameter(JSONLoginFactory.LOGIN, ConnectionRef.LOGIN_MISSING_ERROR, type);
  }
}
