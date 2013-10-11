package com.bid4win.persistence.entity.locale;

import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.commons.persistence.entity.property.PropertyRootAbstract_;

/**
 * Metamodel de la classe I18nRoot<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(I18nRoot.class)
public abstract class I18nRoot_ extends PropertyRootAbstract_
{
  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
