package com.bid4win.communication.json.factory.property;

import java.util.Iterator;

import org.json.JSONObject;

import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.persistence.entity.property.PropertyAbstract;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 *
 * @author Maxime Ollagnier
 */
public class JSONI18nFactory extends JSONPropertyFactory
{
  /** C'est l'instance utilisée comme singleton */
  private final static JSONI18nFactory instance = new JSONI18nFactory();

  /**
   * La fabrique est un singleton. On passe donc par cette méthode pour
   * récupérer l'unique objet en mémoire
   *
   * @return L'instance de la fabrique
   */
  public static JSONI18nFactory getInstance()
  {
    return JSONI18nFactory.instance;
  }

  /**
   * Le constructeur est protégé car la fabrique doit être accédée comme un
   * singleton
   */
  protected JSONI18nFactory()
  {
    super();
  }

  /**
   * Retourne la structure JSON créée à partir du set d'objets en paramètre
   *
   * @param <TYPE> Type de la propriété
   * @param objectSet Set d'objets à partir duquel construire la structure JSON
   * @return La structure JSON construite
   */
  public <TYPE extends PropertyAbstract<TYPE, ?>> JSONObject getFullKeyMap(Bid4WinSet<TYPE> objectSet)
  {
    JSONObject json = new JSONObject();
    this.fillFullKeyMap(json, objectSet);
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
  public <TYPE extends PropertyAbstract<TYPE, ?>> void fillFullKeyMap(JSONObject json, Bid4WinSet<TYPE> objectSet)
  {
    for(Iterator<TYPE> iterator = objectSet.iterator() ; iterator.hasNext() ;)
    {
      TYPE object = iterator.next();
      json.put(object.getFullKey(), this.get(object));
    }
  }
}
