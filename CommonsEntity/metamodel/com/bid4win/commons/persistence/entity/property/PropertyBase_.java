package com.bid4win.commons.persistence.entity.property;

import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;

/**
 * Metamodel de la classe PropertyBase<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(PropertyBase.class)
public abstract class PropertyBase_ extends Bid4WinEntity_
{
  // D�fini la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    /** D�fini la profondeur du noeud de relation existant avec les sous-propri�t�s */
    PropertyBase_Relations.NODE_PROPERTY_MAP.addNode(PropertyAbstract_Relations.NODE_ROOT);
    PropertyBase_Relations.NODE_PROPERTY_MAP.addNode(PropertyAbstract_Relations.NODE_PROPERTY);
    PropertyBase_Relations.NODE_PROPERTY_MAP.addNode(PropertyBase_Relations.NODE_PROPERTY_MAP);
    Bid4WinEntity_.stopProtection();
  }
}
