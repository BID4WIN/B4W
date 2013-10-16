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
 * @author Emeric Fillâtre
 */
@StaticMetamodel(Product.class)
public class Product_ extends Bid4WinEntityGeneratedID_
{
  /** Définition du champ permettant le forçage de la modification du produit */
  public static volatile SingularAttribute<Product, Integer> updateForce;
  /** Définition de la référence du produit */
  public static volatile SingularAttribute<Product, String> reference;
  /** Définition du nom du produit dans différentes langues dont celle définie par défaut */
  public static volatile SingularAttribute<Product, I18nGroup> names;
  /** Définition du résumé de la description du produit dans différentes langues dont celle définie par défaut */
  public static volatile SingularAttribute<Product, I18nGroup> summaries;
  /** Définition du prix du produit dans différentes monnaies dont celle définie par défaut */
  public static volatile SingularAttribute<Product, Price> price;
  /** Definition de la liste des utilisations d'image du produit */
  public static volatile ListAttribute<Product, ImageUsage> imageUsageListInternal;
  /** Definition de la liste des utilisations de contenu du produit */
  public static volatile ListAttribute<Product, InnerContentUsage> innerContentUsageListInternal;

  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    /** Défini la profondeur du noeud de relation existant avec les utilisations d'image */
    Product_Relations.NODE_IMAGE_USAGE_LIST.addNode(ImageUsage_Relations.NODE_PRODUCT);
    /** Défini la profondeur du noeud de relation existant avec les utilisations de contenu */
    Product_Relations.NODE_INNER_CONTENT_USAGE_LIST.addNode(InnerContentUsage_Relations.NODE_PRODUCT);
    Bid4WinEntity_.stopProtection();
  }
}
