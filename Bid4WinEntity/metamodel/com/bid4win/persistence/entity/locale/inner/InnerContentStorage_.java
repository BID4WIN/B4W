package com.bid4win.persistence.entity.locale.inner;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.persistence.entity.locale.LocalizedStorage_;

/**
 * Metamodel de la classe InnerContentStorage<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(InnerContentStorage.class)
public class InnerContentStorage_ extends LocalizedStorage_
{
  /** D�finition du type de contenu */
  public static volatile SingularAttribute<InnerContentStorage, InnerContentType> type;
  // D�finition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
