package com.bid4win.persistence.entity.property;

import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.commons.persistence.entity.property.PropertyAbstract_;

/**
 * Metamodel de la classe Property<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(Property.class)
public abstract class Property_ extends PropertyAbstract_
{
  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
