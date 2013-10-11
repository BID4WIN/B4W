package com.bid4win.persistence.entity.image;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.commons.persistence.entity.resource.FileResourceStorageMultiPart_;

/**
 * Metamodel de la classe ImageStorage<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(ImageStorage.class)
public class ImageStorage_ extends FileResourceStorageMultiPart_
{
  /** Définition du type de l'image */
  public static volatile SingularAttribute<ImageStorage, ImageType> type;
  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
