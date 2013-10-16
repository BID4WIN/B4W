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

  /** C'est l'instance utilis�e comme singleton */
  private final static JSONLoginFactory instance = new JSONLoginFactory();

  /**
   * @return L'unique instance de la fabrique
   */
  public static JSONLoginFactory getInstance()
  {
    return JSONLoginFactory.instance;
  }

  /**
   * Constructeur prot�g� car la fabrique doit �tre acc�d�e comme un singleton
   */
  protected JSONLoginFactory()
  {
    super();
  }

  /**
   * Remplit la structure JSON avec les donn�es de l'objet en param�tre
   *
   * @param json Structure JSON � remplir � partir des donn�es de l'objet
   * @param object Objet avec lequel remplir la structure JSON en param�tre
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
   * Construit l'objet � partir de l'action sp�cifi�e et ajoute le bon message
   * avec le type pr�cis� si une erreur survient lors de sa cr�ation.
   *
   * @param action Action concern�e
   * @param type Type de message � ajouter en cas d'erreur
   * @return L'objet cr��
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
   * R�cup�re le param�tre dans l'action sp�cifi�e et ajoute le bon message avec
   * le type pr�cis� s'il n'est pas trouv�.
   *
   * @param action Action concern�e
   * @param type Type de message � ajouter si le param�tre n'est pas trouv�.
   * @return La valeur du param�tre d�sir�
   */
  public String getValue(JSONAction action, Type type)
  {
    return action.getParameter(JSONLoginFactory.LOGIN, ConnectionRef.LOGIN_MISSING_ERROR, type);
  }
}
