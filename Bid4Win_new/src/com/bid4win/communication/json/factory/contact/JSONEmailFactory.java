package com.bid4win.communication.json.factory.contact;

import org.json.JSONObject;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.ConnectionRef;
import com.bid4win.commons.persistence.entity.contact.Email;
import com.bid4win.communication.action.json.JSONAction;
import com.bid4win.communication.json.model.JSONMessage.Type;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class JSONEmailFactory
{
  /** Nom de l'attribut email dans la structure JSON */
  public final static String EMAIL = "email";

  /** C'est l'instance utilis�e comme singleton */
  private final static JSONEmailFactory instance = new JSONEmailFactory();
  /**
   * La fabrique est un singleton. On passe donc par cette m�thode pour r�cup�rer
   * l'unique objet en m�moire
   * @return L'instance de la fabrique
   */
  public static JSONEmailFactory getInstance()
  {
    return JSONEmailFactory.instance;
  }
  /**
   * Le constructeur est prot�g� car la fabrique doit �tre acc�d�e comme un singleton
   */
  protected JSONEmailFactory()
  {
    super();
  }

  /**
   * Retourne l'objet cr�� � partir de la structure JSON en param�tre
   * @param json Structure JSON � partir de laquelle construire l'objet
   * @return L'objet construit
   * @throws UserException Si la construction de l'objet �choue
   */
/*  public Email get(JSONObject json) throws UserException
  {
    return new Email(JSONFactory.getInstance().getString(json, JSONEmailFactory.EMAIL));
  }*/

  /**
   * Retourne la structure JSON cr��e � partir de l'objet en param�tre
   * @param object Objet � partir duquel construire la structure JSON
   * @return La structure JSON construite
   */
  public JSONObject get(Email object)
  {
    JSONObject json = new JSONObject();
    this.fill(json, object);
    return json;
  }
  /**
   * Remplit la structure JSON avec les donn�es de l'objet en param�tre
   * @param json Structure JSON � remplir � partir des donn�es de l'objet
   * @param object Objet avec lequel remplir la structure JSON en param�tre
   */
  public void fill(JSONObject json, Email object)
  {
    json.put(JSONEmailFactory.EMAIL, object.getAddress());
  }

  /**
   *
   * TODO A COMMENTER
   * @param action TODO A COMMENTER
   * @param type TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public Email get(JSONAction action, Type type)
  {
    // R�cup�re les champs et stocke les messages associ�s aux �checs
    String address = this.getAddress(action, type);
    if(address != null)
    {
      try
      {
        // Construit l'objet et le retourne
        return new Email(address);
      }
      catch(UserException ex)
      {
        // Stocke le message d'�chec de cr�ation de l'objet
        action.putMessage(ex, type);
      }
    }
    return null;
  }
  /**
   *
   * TODO A COMMENTER
   * @param action TODO A COMMENTER
   * @param type TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public String getAddress(JSONAction action, Type type)
  {
    return action.getParameter(JSONEmailFactory.EMAIL,
                               ConnectionRef.EMAIL_MISSING_ERROR,
                               type);
  }
}
