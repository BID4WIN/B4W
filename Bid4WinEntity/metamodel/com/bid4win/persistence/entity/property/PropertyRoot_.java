package com.bid4win.persistence.entity.property;

import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.commons.persistence.entity.property.PropertyRootAbstract_;

/**
 * Metamodel de la classe PropertyRoot<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(PropertyRoot.class)
public abstract class PropertyRoot_ extends PropertyRootAbstract_
{
  // D�finition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
