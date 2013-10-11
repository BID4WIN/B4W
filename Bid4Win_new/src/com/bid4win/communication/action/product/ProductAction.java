package com.bid4win.communication.action.product;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.communication.action.json.JSONAction;
import com.bid4win.communication.json.factory.product.JSONProductFactory;
import com.bid4win.persistence.entity.locale.I18nElement;
import com.bid4win.persistence.entity.locale.I18nGroup;
import com.bid4win.persistence.entity.locale.Language;
import com.bid4win.persistence.entity.price.Currency;
import com.bid4win.persistence.entity.price.Price;
import com.bid4win.persistence.entity.product.Product;
import com.bid4win.service.product.ProductService;

/**
 * Common Product action class
 *
 * @author Maxime Ollagnier
 */
public class ProductAction extends JSONAction
{
  /** Serial number */
  private static final long serialVersionUID = 896193118231482318L;

  /** Product service reference */
  @Autowired
  @Qualifier("ProductService")
  private ProductService productService = null;

  /**
   * Renvoie la map d'objet image du répertoire dont le path est spécifié
   * TODO
   *
   * @return SUCCESS
   * @throws UserException si une erreur utilisateur se produit.
   * @throws Throwable si une erreur technique se produit.
   */
  @Action("GetProductAction")
  public String GetProductAction() throws Throwable, UserException
  {
    String id = this.findParameter("id");
    String searchString = UtilString.trimNotNull(this.findParameter("searchString"));
    if(!UtilString.trimNotNull(id).equals(""))
    {
      Bid4WinList<Product> productList = new Bid4WinList<Product>();
      productList.add(this.getProductService().getProduct(id));
      this.putJSONObject(JSONProductFactory.getInstance().getComplete(productList));
    }
    else if(!UtilString.trimNotNull(searchString).equals(""))
    {
      // TODO get with filter
    }
    else
    {
      Bid4WinList<Product> productList = this.getProductService().getProductList();
      this.putJSONObject(JSONProductFactory.getInstance().get(productList));
    }
    this.setSuccess(true);
    return SUCCESS;
  }

  /**
   * TODO
   *
   * @return SUCCESS
   * @throws UserException si une erreur utilisateur se produit.
   * @throws Throwable si une erreur technique se produit.
   */
  @Action("SaveProductAction")
  public String SaveProductAction() throws Throwable, UserException
  {
    String id = UtilString.trimNotNull(this.findParameter("id"));
    String reference = UtilString.trimNotNull(this.findParameter("reference"));
    String defaultName = UtilString.trimNotNull(this.findParameter("name" + Language.DEFAULT.getCode()));
    if(defaultName.equals(""))
    {
      defaultName = "Name";
    }
    I18nGroup names = new I18nGroup(defaultName);
    String defaultSummary = UtilString.trimNotNull(this.findParameter("summary" + Language.DEFAULT.getCode()));
    if(defaultSummary.equals(""))
    {
      defaultSummary = "Summary";
    }
    I18nGroup summaries = new I18nGroup(defaultSummary);
    for(Language language : Language.getLanguageSet())
    {
      if(language != Language.DEFAULT)
      {
        String name = UtilString.trimNotNull(this.findParameter("name" + language.getCode()));
        if(!name.equals(""))
        {
          names.addEmbedded(new I18nElement(language, name));
        }
        String summary = UtilString.trimNotNull(this.findParameter("summary" + language.getCode()));
        if(!summary.equals(""))
        {
          summaries.addEmbedded(new I18nElement(language, summary));
        }
      }
    }
    String defaultPriceString = UtilString.trimNotNull(this.findParameter("price" + Currency.DEFAULT.getCode()));
    if(defaultPriceString.equals(""))
    {
      defaultPriceString = "0";
    }
    double defaultPrice = 0;
    try
    {
      defaultPrice = Double.parseDouble(defaultPriceString);
    }
    catch(NumberFormatException e)
    {
      this.putWarnMessage(MessageRef.CurrencyRef.CURRENCY_AMOUNT_INVALID_ERROR);
    }
    Price price = new Price(defaultPrice);
    for(Currency currency : Currency.getCurrencySet())
    {
      if(currency != Currency.DEFAULT)
      {
        String currentPriceString = UtilString.trimNotNull(this.findParameter("price" + currency.getCode()));
        if(!currentPriceString.equals(""))
        {
          double currentPrice = 0;
          try
          {
            currentPrice = Double.parseDouble(currentPriceString);
          }
          catch(NumberFormatException e)
          {
            this.putWarnMessage(MessageRef.CurrencyRef.CURRENCY_AMOUNT_INVALID_ERROR);
          }
          price.addAmout(currency, currentPrice);
        }
      }
    }
    Bid4WinList<Product> productList = new Bid4WinList<Product>();
    if(!UtilString.trimNotNull(id).equals(""))
    {
      productList.add(this.getProductService().updateProduct(id, reference, names, summaries, price));
    }
    else
    {
      productList.add(this.getProductService().createProduct(reference, names, summaries, price));
    }
    this.putJSONObject(JSONProductFactory.getInstance().getComplete(productList));
    this.setSuccess(true);
    return SUCCESS;
  }

  /**
   * TODO
   *
   * @return SUCCESS
   * @throws UserException si une erreur utilisateur se produit.
   * @throws Throwable si une erreur technique se produit.
   */
  @Action("AddProductImageAction")
  public String AddProductImageAction() throws Throwable, UserException
  {
    String id = UtilString.trimNotNull(this.findParameter("id"));
    String imageStorageId = UtilString.trimNotNull(this.findParameter("imageStorageId"));
    Bid4WinList<Product> productList = new Bid4WinList<Product>();
    productList.add(this.getProductService().addImage(id, Long.parseLong(imageStorageId)));
    this.putJSONObject(JSONProductFactory.getInstance().getComplete(productList));
    this.setSuccess(true);
    return SUCCESS;
  }

  /**
   * TODO
   *
   * @return SUCCESS
   * @throws UserException si une erreur utilisateur se produit.
   * @throws Throwable si une erreur technique se produit.
   */
  @Action("RemoveProductImageAction")
  public String RemoveProductImageAction() throws Throwable, UserException
  {
    String id = UtilString.trimNotNull(this.findParameter("id"));
    String imageUsageId = UtilString.trimNotNull(this.findParameter("imageUsageId"));
    Bid4WinList<Product> productList = new Bid4WinList<Product>();
    productList.add(this.getProductService().removeImage(id, Long.parseLong(imageUsageId)));
    this.putJSONObject(JSONProductFactory.getInstance().getComplete(productList));
    this.setSuccess(true);
    return SUCCESS;
  }

  /**
   * TODO
   *
   * @return SUCCESS
   * @throws UserException si une erreur utilisateur se produit.
   * @throws Throwable si une erreur technique se produit.
   */
  @Action("MoveProductImageAction")
  public String MoveProductImageAction() throws Throwable, UserException
  {
    String id = UtilString.trimNotNull(this.findParameter("id"));
    String oldPosition = UtilString.trimNotNull(this.findParameter("oldPosition"));
    String newPosition = UtilString.trimNotNull(this.findParameter("newPosition"));
    Bid4WinList<Product> productList = new Bid4WinList<Product>();
    productList.add(this.getProductService().moveImage(id, Integer.parseInt(oldPosition), Integer.parseInt(newPosition)));
    this.putJSONObject(JSONProductFactory.getInstance().getComplete(productList));
    this.setSuccess(true);
    return SUCCESS;
  }

  /**
   * Returns the ProductService
   *
   * @return the ProductService
   */
  protected ProductService getProductService()
  {
    return this.productService;
  }
}
