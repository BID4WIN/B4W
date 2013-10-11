package com.bid4win.persistence.entity.sale;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultipleGeneratedID_;
import com.bid4win.persistence.entity.price.Price;
import com.bid4win.persistence.entity.product.Product;
import com.bid4win.persistence.entity.product.Product_Relations;

/**
 * Metamodel de la classe Sale<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(Sale.class)
public class Sale_ extends AccountBasedEntityMultipleGeneratedID_
{
  /** D�finition du produit vendu */
  public static volatile SingularAttribute<Sale<?>, Product> product;
  /** D�finition du prix du produit vendu */
  public static volatile SingularAttribute<Sale<?>, Price> productPrice;
  /** D�finition de l'�tape de la vente */
  public static volatile SingularAttribute<Sale<?>, SaleStep> saleStep;

  // D�finition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    /** D�fini la profondeur du noeud de relation existant avec le produit vendu */
    Sale_Relations.NODE_PRODUCT.addNode(Product_Relations.NODE_IMAGE_USAGE_LIST);
    Sale_Relations.NODE_PRODUCT.addNode(Product_Relations.NODE_INNER_CONTENT_USAGE_LIST);
    Bid4WinEntity_.stopProtection();
  }
}
