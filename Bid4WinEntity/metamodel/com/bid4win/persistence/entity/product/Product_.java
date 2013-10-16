package com.bid4win.persistence.entity.product;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntityGeneratedID_;
import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.persistence.entity.image.ImageUsage;
import com.bid4win.persistence.entity.image.ImageUsage_Relations;
import com.bid4win.persistence.entity.locale.I18nGroup;
import com.bid4win.persistence.entity.locale.inner.InnerContentUsage;
import com.bid4win.persistence.entity.locale.inner.InnerContentUsage_Relations;
import com.bid4win.persistence.entity.price.Price;

/**
 * Metamodel de la classe Product<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(Product.class)
public class Product_ extends Bid4WinEntityGeneratedID_
{
  /** D�finition du champ permettant le for�age de la modification du produit */
  public static volatile SingularAttribute<Product, Integer> updateForce;
  /** D�finition de la r�f�rence du produit */
  public static volatile SingularAttribute<Product, String> reference;
  /** D�finition du nom du produit dans diff�rentes langues dont celle d�finie par d�faut */
  public static volatile SingularAttribute<Product, I18nGroup> names;
  /** D�finition du r�sum� de la description du produit dans diff�rentes langues dont celle d�finie par d�faut */
  public static volatile SingularAttribute<Product, I18nGroup> summaries;
  /** D�finition du prix du produit dans diff�rentes monnaies dont celle d�finie par d�faut */
  public static volatile SingularAttribute<Product, Price> price;
  /** Definition de la liste des utilisations d'image du produit */
  public static volatile ListAttribute<Product, ImageUsage> imageUsageListInternal;
  /** Definition de la liste des utilisations de contenu du produit */
  public static volatile ListAttribute<Product, InnerContentUsage> innerContentUsageListInternal;

  // D�finition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    /** D�fini la profondeur du noeud de relation existant avec les utilisations d'image */
    Product_Relations.NODE_IMAGE_USAGE_LIST.addNode(ImageUsage_Relations.NODE_PRODUCT);
    /** D�fini la profondeur du noeud de relation existant avec les utilisations de contenu */
    Product_Relations.NODE_INNER_CONTENT_USAGE_LIST.addNode(InnerContentUsage_Relations.NODE_PRODUCT);
    Bid4WinEntity_.stopProtection();
  }
}
