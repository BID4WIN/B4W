package com.bid4win.communication.json.factory.property;

import java.util.Iterator;

import org.json.JSONObject;

import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.reference.MessageRef.PropertyRef;
import com.bid4win.commons.persistence.entity.property.PropertyAbstract;
import com.bid4win.communication.action.json.JSONAction;
import com.bid4win.communication.json.model.JSONMessage.Type;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 *
 * @author Emeric Fillâtre
 */
public class JSONPropertyFactory
{
  /** Nom de l'attribut key dans la structure JSON */
  public final static String KEY = "key";
  /** Nom de l'attribut value dans la structure JSON */
  public final static String VALUE = "value";
  /** Nom de la liste de sous propriétés dans la structure JSON */
  public final static String PROPERTIES = "properties";
  /** Nom de la clé complète de la propriété parent dans la structure JSON */
  public final static String FULL_PARENT_KEY = "fullParentKey";

  /** C'est l'instance utilisée comme singleton */
  private final static JSONPropertyFactory instance = new JSONPropertyFactory();

  /**
   * La fabrique est un singleton. On passe donc par cette méthode pour
   * récupérer l'unique objet en mémoire
   *
   * @return L'instance de la fabrique
   */
  public static JSONPropertyFactory getInstance()
  {
    return JSONPropertyFactory.instance;
  }

  /**
   * Le constructeur est protégé car la fabrique doit être accédée comme un
   * singleton
   */
  protected JSONPropertyFactory()
  {
    super();
  }

  /**
   * Retourne la structure JSON créée à partir de l'objet en paramètre
   *
   * @param <TYPE> Type de la propriété
   * @param object Objet à partir duquel construire la structure JSON
   * @return La structure JSON construite
   */
  public <TYPE extends PropertyAbstract<TYPE, ?>> JSONObject get(TYPE object)
  {
    JSONObject json = new JSONObject();
    this.fill(json, object);
    return json;
  }

  /**
   * Remplit la structure JSON avec les données de l'objet en paramètre
   *
   * @param <TYPE> Type de la propriété
   * @param json Structure JSON à remplir à partir des données de l'objet
   * @param object Objet avec lequel remplir la structure JSON en paramètre
   */
  public <TYPE extends PropertyAbstract<TYPE, ?>> void fill(JSONObject json, TYPE object)
  {
    json.put(JSONPropertyFactory.KEY, object.getKey());
    json.put(JSONPropertyFactory.VALUE, object.getValue());
    json.put(JSONPropertyFactory.PROPERTIES, this.get(object.getPropertySet()));
    json.put(JSONPropertyFactory.FULL_PARENT_KEY, "");
    if(object.getProperty() != null)
    {
      json.put(JSONPropertyFactory.FULL_PARENT_KEY, object.getProperty().getFullKey());
    }
  }

  /**
   * Retourne la structure JSON créée à partir du set d'objets en paramètre
   *
   * @param <TYPE> Type de la propriété
   * @param objectSet Set d'objets à partir duquel construire la structure JSON
   * @return La structure JSON construite
   */
  public <TYPE extends PropertyAbstract<TYPE, ?>> JSONObject get(Bid4WinSet<TYPE> objectSet)
  {
    JSONObject json = new JSONObject();
    this.fill(json, objectSet);
    return json;
  }

  /**
   * Remplit la structure JSON avec les données du set d'objets en paramètre
   *
   * @param <TYPE> Type de la propriété
   * @param json Structure JSON à remplir à partir des données du set d'objets
   * @param objectSet Set d'objets avec lequel remplir la structure JSON en
   *          paramètre
   */
  public <TYPE extends PropertyAbstract<TYPE, ?>> void fill(JSONObject json, Bid4WinSet<TYPE> objectSet)
  {
    for(Iterator<TYPE> iterator = objectSet.iterator() ; iterator.hasNext() ;)
    {
      TYPE object = iterator.next();
      json.put(object.getKey(), this.get(object));
    }
  }

  /**
   *
   * TODO A COMMENTER
   *
   * @param action TODO A COMMENTER
   * @param type TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public String getKey(JSONAction action, Type type)
  {
    return action.getParameter(JSONPropertyFactory.KEY, PropertyRef.PROPERTY_KEY_MISSING_ERROR, type);
  }

  /**
   *
   * TODO A COMMENTER
   *
   * @param action TODO A COMMENTER
   * @param type TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public String getValue(JSONAction action, Type type)
  {
    return action.getParameter(JSONPropertyFactory.VALUE, PropertyRef.PROPERTY_VALUE_MISSING_ERROR, type);
  }
}
