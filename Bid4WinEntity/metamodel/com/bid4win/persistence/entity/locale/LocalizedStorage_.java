package com.bid4win.persistence.entity.locale;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.commons.persistence.entity.resource.FileResourceStorageMultiPart_;

/**
 * Metamodel de la classe LocalizedStorage<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(LocalizedStorage.class)
public class LocalizedStorage_ extends FileResourceStorageMultiPart_
{
  /** Définition du type de l'image */
  public static volatile SingularAttribute<LocalizedStorage<?, ?, ?, ?>, Bid4WinSet<Language>> partTypeSet;
  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
