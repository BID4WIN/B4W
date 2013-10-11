package com.bid4win.persistence.entity.locale.inner;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.persistence.entity.locale.LocalizedUsage_;
import com.bid4win.persistence.entity.product.Product;

/**
 * Metamodel de la classe InnerContentUsage<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(InnerContentUsage.class)
public class InnerContentUsage_ extends LocalizedUsage_
{
  /** Définition du type de contenu */
  public static volatile SingularAttribute<InnerContentUsage, InnerContentType> type;
  /** Definition du type de l'utilisation de contenu */
  public static volatile SingularAttribute<InnerContentUsage, String> usageType;
  /** Definition de la position de l'utilisation de contenu */
  public static volatile SingularAttribute<InnerContentUsage, Integer> position;
  /** Definition du produit de l'utilisation de contenu */
  public static volatile SingularAttribute<InnerContentUsage, Product> product;
  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
