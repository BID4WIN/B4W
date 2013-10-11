package com.bid4win.persistence.entity.locale.inner;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.persistence.entity.locale.LocalizedStorage_;

/**
 * Metamodel de la classe InnerContentStorage<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(InnerContentStorage.class)
public class InnerContentStorage_ extends LocalizedStorage_
{
  /** Définition du type de contenu */
  public static volatile SingularAttribute<InnerContentStorage, InnerContentType> type;
  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
