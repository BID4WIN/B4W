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
 * @author Emeric Fillâtre
 */
public class JSONEmailFactory
{
  /** Nom de l'attribut email dans la structure JSON */
  public final static String EMAIL = "email";

  /** C'est l'instance utilisée comme singleton */
  private final static JSONEmailFactory instance = new JSONEmailFactory();
  /**
   * La fabrique est un singleton. On passe donc par cette méthode pour récupérer
   * l'unique objet en mémoire
   * @return L'instance de la fabrique
   */
  public static JSONEmailFactory getInstance()
  {
    return JSONEmailFactory.instance;
  }
  /**
   * Le constructeur est protégé car la fabrique doit être accédée comme un singleton
   */
  protected JSONEmailFactory()
  {
    super();
  }

  /**
   * Retourne l'objet créé à partir de la structure JSON en paramètre
   * @param json Structure JSON à partir de laquelle construire l'objet
   * @return L'objet construit
   * @throws UserException Si la construction de l'objet échoue
   */
/*  public Email get(JSONObject json) throws UserException
  {
    return new Email(JSONFactory.getInstance().getString(json, JSONEmailFactory.EMAIL));
  }*/

  /**
   * Retourne la structure JSON créée à partir de l'objet en paramètre
   * @param object Objet à partir duquel construire la structure JSON
   * @return La structure JSON construite
   */
  public JSONObject get(Email object)
  {
    JSONObject json = new JSONObject();
    this.fill(json, object);
    return json;
  }
  /**
   * Remplit la structure JSON avec les données de l'objet en paramètre
   * @param json Structure JSON à remplir à partir des données de l'objet
   * @param object Objet avec lequel remplir la structure JSON en paramètre
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
    // Récupère les champs et stocke les messages associés aux échecs
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
        // Stocke le message d'échec de création de l'objet
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
