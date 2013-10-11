package com.bid4win.persistence.entity.property;

import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.commons.persistence.entity.property.PropertyRootAbstract_;

/**
 * Metamodel de la classe PropertyRoot<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(PropertyRoot.class)
public abstract class PropertyRoot_ extends PropertyRootAbstract_
{
  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
