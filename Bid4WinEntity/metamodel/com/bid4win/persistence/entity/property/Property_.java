package com.bid4win.persistence.entity.property;

import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.commons.persistence.entity.property.PropertyAbstract_;

/**
 * Metamodel de la classe Property<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(Property.class)
public abstract class Property_ extends PropertyAbstract_
{
  // D�finition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
