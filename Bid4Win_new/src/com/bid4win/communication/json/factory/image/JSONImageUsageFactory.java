package com.bid4win.communication.json.factory.image;

import org.json.JSONObject;

import com.bid4win.commons.core.exception.Bid4WinRuntimeException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.communication.json.factory.JSONFactory;
import com.bid4win.persistence.entity.image.ImageUsage;
import com.bid4win.persistence.entity.image.resource.Format;

/**
 * Fabrique de structures JSON<BR>
 * <BR>
 *
 * @author Maxime Ollagnier
 */
public class JSONImageUsageFactory extends JSONFactory<ImageUsage>
{
  /** Nom des attributs dans la structure JSON */
  public final static String FULL_PATH = "fullPath";

  /** C'est l'instance utilis�e comme singleton */
  private final static JSONImageUsageFactory instance = new JSONImageUsageFactory();

  /**
   * @return L'unique instance de la fabrique
   */
  public static JSONImageUsageFactory getInstance()
  {
    return JSONImageUsageFactory.instance;
  }

  /**
   * Constructeur prot�g� car la fabrique doit �tre acc�d�e comme un singleton
   */
  protected JSONImageUsageFactory()
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
  public void fill(JSONObject json, ImageUsage object)
  {
    try
    {
      json.put(JSONFactory.ID, object.getId());
      json.put(JSONImageUsageFactory.FULL_PATH, object.getPart(Format.SMALL).getFullPath());
    }
    catch(UserException e)
    {
      throw new Bid4WinRuntimeException(e);
    }
  }

  /**
   * @param object {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.communication.json.factory.JSONFactory#getId(Object)
   */
  @Override
  public String getId(ImageUsage object)
  {
    return String.valueOf(object.getId());
  }
}
