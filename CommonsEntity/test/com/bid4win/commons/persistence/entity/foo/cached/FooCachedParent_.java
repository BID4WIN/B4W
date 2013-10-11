package com.bid4win.commons.persistence.entity.foo.cached;

import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;

/**
 * Metamodel de la class FooCachedParent<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(FooCachedParent.class)
public abstract class FooCachedParent_ extends FooCached_
{
  // D�finition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    FooCachedParent_Relations.NODE_CHILD.addNode(FooCachedChild_Relations.NODE_PARENT);
    Bid4WinEntity_.stopProtection();
  }
}