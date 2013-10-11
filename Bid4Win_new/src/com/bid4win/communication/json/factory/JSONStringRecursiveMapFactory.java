package com.bid4win.communication.json.factory;

import java.util.Map;

import org.json.JSONObject;

import com.bid4win.commons.core.collection.Bid4WinStringRecursiveMap;

/**
 * Fabrique de structures JSON<BR>
 * <BR>
 *
 * @author Maxime Ollagnier
 */
public class JSONStringRecursiveMapFactory extends JSONFactory<Bid4WinStringRecursiveMap>
{
  /** C'est l'instance utilis�e comme singleton */
  private final static JSONStringRecursiveMapFactory instance = new JSONStringRecursiveMapFactory();

  /**
   * @return L'unique instance de la fabrique
   */
  public static JSONStringRecursiveMapFactory getInstance()
  {
    return JSONStringRecursiveMapFactory.instance;
  }

  /**
   * Constructeur prot�g� car la fabrique doit �tre acc�d�e comme un singleton
   */
  protected JSONStringRecursiveMapFactory()
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
  public void fill(JSONObject json, Bid4WinStringRecursiveMap object)
  {
    for(Map.Entry<String, Bid4WinStringRecursiveMap> entry : object.entrySet())
    {
      json.put(entry.getKey(), this.get(entry.getValue()));
    }
  }

  /**
   * @param object {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.communication.json.factory.JSONFactory#getId(Object)
   */
  @Override
  public String getId(Bid4WinStringRecursiveMap object)
  {
    return null;
  }
}
