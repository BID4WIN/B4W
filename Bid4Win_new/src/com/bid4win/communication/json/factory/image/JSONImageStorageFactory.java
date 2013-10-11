package com.bid4win.communication.json.factory.image;

import org.json.JSONObject;

import com.bid4win.communication.json.factory.JSONFactory;
import com.bid4win.persistence.entity.image.ImageStorage;

/**
 * Fabrique de structures JSON<BR>
 * <BR>
 *
 * @author Maxime Ollagnier
 */
public class JSONImageStorageFactory extends JSONFactory<ImageStorage>
{
  /** Nom des attributs dans la structure JSON */
  public final static String NAME = "name";

  /** C'est l'instance utilisée comme singleton */
  private final static JSONImageStorageFactory instance = new JSONImageStorageFactory();

  /**
   * @return L'unique instance de la fabrique
   */
  public static JSONImageStorageFactory getInstance()
  {
    return JSONImageStorageFactory.instance;
  }

  /**
   * Constructeur protégé car la fabrique doit être accédée comme un singleton
   */
  protected JSONImageStorageFactory()
  {
    super();
  }

  /**
   * Remplit la structure JSON avec les données de l'objet en paramètre
   *
   * @param json Structure JSON à remplir à partir des données de l'objet
   * @param object Objet avec lequel remplir la structure JSON en paramètre
   */
  @Override
  public void fill(JSONObject json, ImageStorage object)
  {
    json.put(JSONFactory.ID, this.getId(object));
    json.put(JSONImageStorageFactory.NAME, object.getName());
  }

  /**
   * @param object {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.communication.json.factory.JSONFactory#getId(Object)
   */
  @Override
  public String getId(ImageStorage object)
  {
    return String.valueOf(object.getId());
  }
}
