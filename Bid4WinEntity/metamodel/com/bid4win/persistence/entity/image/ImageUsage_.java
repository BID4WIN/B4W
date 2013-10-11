package com.bid4win.persistence.entity.image;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.commons.persistence.entity.resource.FileResourceUsageMultiPart_;
import com.bid4win.persistence.entity.product.Product;

/**
 * Metamodel de la classe ImageUsage<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(ImageUsage.class)
public class ImageUsage_ extends FileResourceUsageMultiPart_
{
  /** Définition du type de l'image */
  public static volatile SingularAttribute<ImageUsage, ImageType> type;
  /** Definition du type de l'utilisation d'image */
  public static volatile SingularAttribute<ImageUsage, String> usageType;
  /** Definition de la position de l'utilisation d'image */
  public static volatile SingularAttribute<ImageUsage, Integer> position;
  /** Definition du produit de l'utilisation d'image */
  public static volatile SingularAttribute<ImageUsage, Product> product;

  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
