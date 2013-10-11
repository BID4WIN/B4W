package com.bid4win.communication.json.factory.product;

import java.util.Collection;
import java.util.Iterator;

import org.json.JSONObject;

import com.bid4win.commons.core.exception.Bid4WinRuntimeException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.communication.json.factory.JSONFactory;
import com.bid4win.persistence.entity.image.ImageUsage;
import com.bid4win.persistence.entity.image.resource.Format;
import com.bid4win.persistence.entity.locale.I18nElement;
import com.bid4win.persistence.entity.price.Amount;
import com.bid4win.persistence.entity.product.Product;

/**
 * Fabrique de structures JSON<BR>
 * <BR>
 *
 * @author Maxime Ollagnier
 */
public class JSONProductFactory extends JSONFactory<Product>
{
  /** Nom des attributs dans la structure JSON */
  public final static String REFERENCE = "reference";
  public final static String NAME = "name";
  public final static String NAME_MAP = "nameMap";
  public final static String SUMMARY_MAP = "summaryMap";
  public final static String AMOUNT_MAP = "amountMap";
  public final static String IMAGE_MAP = "imageMap";
  public final static String IMAGE_USAGE_LIST = "imageUsageList";
  public final static String HTML_PAGE_USAGE_LIST = "htmlPageUsageList";


  /** C'est l'instance utilisée comme singleton */
  private final static JSONProductFactory instance = new JSONProductFactory();

  /**
   * @return L'unique instance de la fabrique
   */
  public static JSONProductFactory getInstance()
  {
    return JSONProductFactory.instance;
  }

  /**
   * Constructeur protégé car la fabrique doit être accédée comme un singleton
   */
  protected JSONProductFactory()
  {
    super();
  }

  /**
   * Remplit la structure JSON avec les données light de l'objet en paramètre
   *
   * @param json Structure JSON à remplir à partir des données light de l'objet
   * @param object Objet avec lequel remplir la structure JSON en paramètre
   */
  @Override
  public void fill(JSONObject json, Product object)
  {
    json.put(JSONFactory.ID, this.getId(object));
    json.put(JSONProductFactory.REFERENCE, object.getReference());
    json.put(JSONProductFactory.NAME, object.getName());
  }

  /**
   * Remplit la structure JSON avec les données de l'objet en paramètre
   *
   * @param json Structure JSON à remplir à partir des données de l'objet
   * @param object Objet avec lequel remplir la structure JSON en paramètre
   */
  public void fillComplete(JSONObject json, Product object)
  {
    try
    {
      this.fill(json, object);
      JSONObject namesJSON = new JSONObject();
      for(I18nElement nameI18nElement : object.getNames().getEmbeddedSet())
      {
        namesJSON.put(nameI18nElement.getLanguage().getCode(), nameI18nElement.getValue());
      }
      json.put(JSONProductFactory.NAME_MAP, namesJSON);
      JSONObject summariesJSON = new JSONObject();
      for(I18nElement summaryI18nElement : object.getSummaries().getEmbeddedSet())
      {
        summariesJSON.put(summaryI18nElement.getLanguage().getCode(), summaryI18nElement.getValue());
      }
      json.put(JSONProductFactory.SUMMARY_MAP, summariesJSON);
      JSONObject amountsJSON = new JSONObject();
      for(Amount amount : object.getPrice().getEmbeddedSet())
      {
        amountsJSON.put(amount.getCurrency().getCode(), amount.getValue());
      }
      json.put(JSONProductFactory.AMOUNT_MAP, amountsJSON);
      JSONObject imagesJSON = new JSONObject();
      for(ImageUsage imageUsage : object.getImageList())
      {
        imagesJSON.put(String.valueOf(imageUsage.getId()), "WEB-SUP/" + imageUsage.getPart(Format.SMALL).getFullPath());
      }
      json.put(JSONProductFactory.IMAGE_MAP, imagesJSON);
    }
    catch(UserException e)
    {
      throw new Bid4WinRuntimeException(e);
    }
    //json.put(JSONProductFactory.IMAGE_USAGE_LIST, JSONImageUsageFactory.getInstance().get(object.getImageList()));
  }

  /**
   * @param object Objet à partir duquel construire la structure JSON
   * @return La structure JSON créée à partir de l'objet en paramètre
   */
  public JSONObject getComplete(Product object)
  {
    JSONObject json = new JSONObject();
    this.fillComplete(json, object);
    return json;
  }

  /**
   * @param objectCollection Collection d'objets à partir de laquelle construire
   *          la structure JSON
   * @return La structure JSON créée à partir de la collection d'objets en
   *         paramètre
   */
  public JSONObject getComplete(Collection<Product> objectCollection)
  {
    JSONObject json = new JSONObject();
    for(Iterator<Product> iterator = objectCollection.iterator() ; iterator.hasNext() ;)
    {
      Product object = iterator.next();
      json.put(this.getId(object), this.getComplete(object));
    }
    return json;
  }

  /**
   * @param object {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.communication.json.factory.JSONFactory#getId(Object)
   */
  @Override
  public String getId(Product object)
  {
    return String.valueOf(object.getId());
  }
}
