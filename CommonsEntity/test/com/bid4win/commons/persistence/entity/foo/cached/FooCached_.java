package com.bid4win.commons.persistence.entity.foo.cached;

import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.commons.persistence.entity.foo.FooAbstract_;

/**
 * Metamodel de la class FooCached<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(FooCached.class)
public abstract class FooCached_ extends FooAbstract_
{
  // D�finition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
