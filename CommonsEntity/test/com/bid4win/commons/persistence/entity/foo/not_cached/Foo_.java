package com.bid4win.commons.persistence.entity.foo.not_cached;

import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.commons.persistence.entity.foo.FooAbstract_;

/**
 * Metamodel de la class Foo<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(Foo.class)
public abstract class Foo_ extends FooAbstract_
{
  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
