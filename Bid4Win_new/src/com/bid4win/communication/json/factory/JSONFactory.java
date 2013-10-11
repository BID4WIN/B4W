package com.bid4win.communication.json.factory;

import java.util.Collection;
import java.util.Iterator;

import org.json.JSONObject;

/**
 * Fabrique de structures JSON contenant un ou plusieurs Objet(s)<BR>
 * <BR>
 *
 * @param <OBJECT> Objet dont la transformation JSON doit �tre g�r�e<BR>
 * <BR>
 * @author Maxime Ollagnier
 */
public abstract class JSONFactory<OBJECT extends Object>
{
  /** Nom de l'attribut id dans la structure JSON */
  public final static String ID = "id";

  /**
   * @param object Objet � partir duquel construire la structure JSON
   * @return La structure JSON cr��e � partir de l'objet en param�tre
   */
  public JSONObject get(OBJECT object)
  {
    JSONObject json = new JSONObject();
    this.fill(json, object);
    return json;
  }

  /**
   * Remplit la structure JSON avec les donn�es de l'objet en param�tre
   *
   * @param json Structure JSON � remplir � partir des donn�es de l'objet
   * @param object Objet avec lequel remplir la structure JSON en param�tre
   */
  public abstract void fill(JSONObject json, OBJECT object);

  /**
   * @param objectCollection Collection d'objets � partir de laquelle construire
   *          la structure JSON
   * @return La structure JSON cr��e � partir de la collection d'objets en
   *         param�tre
   */
  public JSONObject get(Collection<OBJECT> objectCollection)
  {
    JSONObject json = new JSONObject();
    this.fill(json, objectCollection);
    return json;
  }

  /**
   * Remplit la structure JSON avec les donn�es de la collection d'objets en
   * param�tre
   *
   * @param json Structure JSON � remplir
   * @param objectCollection Collection d'objets � partir de laquelle remplir la
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
   * @return Retourne un ID servant � mapper l'objet dans une structure JSON
   */
  public abstract String getId(OBJECT object);
}
