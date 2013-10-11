package com.bid4win.communication.json.factory;

import java.util.Collection;
import java.util.Iterator;

import org.json.JSONObject;

/**
 * Fabrique de structures JSON contenant un ou plusieurs Objet(s)<BR>
 * <BR>
 *
 * @param <OBJECT> Objet dont la transformation JSON doit être gérée<BR>
 * <BR>
 * @author Maxime Ollagnier
 */
public abstract class JSONFactory<OBJECT extends Object>
{
  /** Nom de l'attribut id dans la structure JSON */
  public final static String ID = "id";

  /**
   * @param object Objet à partir duquel construire la structure JSON
   * @return La structure JSON créée à partir de l'objet en paramètre
   */
  public JSONObject get(OBJECT object)
  {
    JSONObject json = new JSONObject();
    this.fill(json, object);
    return json;
  }

  /**
   * Remplit la structure JSON avec les données de l'objet en paramètre
   *
   * @param json Structure JSON à remplir à partir des données de l'objet
   * @param object Objet avec lequel remplir la structure JSON en paramètre
   */
  public abstract void fill(JSONObject json, OBJECT object);

  /**
   * @param objectCollection Collection d'objets à partir de laquelle construire
   *          la structure JSON
   * @return La structure JSON créée à partir de la collection d'objets en
   *         paramètre
   */
  public JSONObject get(Collection<OBJECT> objectCollection)
  {
    JSONObject json = new JSONObject();
    this.fill(json, objectCollection);
    return json;
  }

  /**
   * Remplit la structure JSON avec les données de la collection d'objets en
   * paramètre
   *
   * @param json Structure JSON à remplir
   * @param objectCollection Collection d'objets à partir de laquelle remplir la
   *          structure JSON
   */
  public void fill(JSONObject json, Collection<OBJECT> objectCollection)
  {
    for(Iterator<OBJECT> iterator = objectCollection.iterator() ; iterator.hasNext() ;)
    {
      OBJECT object = iterator.next();
      json.put(this.getId(object), this.get(object));
    }
  }

  /**
   * @param object L'objet dont on va retourner l'ID
   * @return Retourne un ID servant à mapper l'objet dans une structure JSON
   */
  public abstract String getId(OBJECT object);
}
