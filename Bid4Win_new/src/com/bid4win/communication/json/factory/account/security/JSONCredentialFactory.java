package com.bid4win.communication.json.factory.account.security;

import org.json.JSONObject;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.persistence.entity.account.security.Credential;
import com.bid4win.commons.persistence.entity.account.security.Login;
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
public class JSONCredentialFactory extends JSONFactory<Credential>
{
  /** C'est l'instance utilis�e comme singleton */
  private final static JSONCredentialFactory instance = new JSONCredentialFactory();

  /**
   * @return L'unique instance de la fabrique
   */
  public static JSONCredentialFactory getInstance()
  {
    return JSONCredentialFactory.instance;
  }

  /**
   * Constructeur prot�g� car la fabrique doit �tre acc�d�e comme un singleton
   */
  protected JSONCredentialFactory()
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
  public void fill(JSONObject json, Credential object)
  {
    JSONLoginFactory.getInstance().fill(json, object.getLogin());
    JSONPasswordFactory.getInstance().fill(json, object.getPassword());
  }

  /**
   * @param object {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.communication.json.factory.JSONFactory#getId(Object)
   */
  @Override
  public String getId(Credential object)
  {
    return object.getLogin().getValue();
  }

  /**
   * Construit l'objet � partir de l'action sp�cifi�e et ajoute le bon message
   * avec le type pr�cis� si une erreur survient lors de sa cr�ation.
   *
   * @param action Action concern�e
   * @param type Type de message � ajouter en cas d'erreur
   * @return L'objet cr��
   */
  public Credential get(JSONAction action, Type type)
  {
    Login login = JSONLoginFactory.getInstance().get(action, type);
    Password password = JSONPasswordFactory.getInstance().get(action, type);
    if(login != null && password != null)
    {
      try
      {
        return new Credential(login, password);
      }
      catch(UserException ex)
      {
        ex.printStackTrace();
        action.putMessage(MessageRef.UNKNOWN_ERROR, type);
      }
    }
    return null;
  }
}
