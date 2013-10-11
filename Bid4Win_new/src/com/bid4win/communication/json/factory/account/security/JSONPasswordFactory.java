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

  /** C'est l'instance utilis�e comme singleton */
  private final static JSONPasswordFactory instance = new JSONPasswordFactory();

  /**
   * @return L'unique instance de la fabrique
   */
  public static JSONPasswordFactory getInstance()
  {
    return JSONPasswordFactory.instance;
  }

  /**
   * Constructeur prot�g� car la fabrique doit �tre acc�d�e comme un singleton
   */
  protected JSONPasswordFactory()
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
   * Construit l'objet � partir de l'action sp�cifi�e et ajoute le bon message
   * avec le type pr�cis� si une erreur survient lors de sa cr�ation.
   *
   * @param action Action concern�e
   * @param type Type de message � ajouter en cas d'erreur
   * @return L'objet cr��
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
   * R�cup�re le param�tre dans l'action sp�cifi�e et ajoute le bon message avec
   * le type pr�cis� s'il n'est pas trouv�.
   *
   * @param action Action concern�e
   * @param type Type de message � ajouter si le param�tre n'est pas trouv�.
   * @return La valeur du param�tre d�sir�
   */
  public String getValue(JSONAction action, Type type)
  {
    return action.getParameter(JSONPasswordFactory.PASSWORD, ConnectionRef.CONNECTION_PASSWORD_MISSING_ERROR, type);
  }
}
