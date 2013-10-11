package com.bid4win.commons.persistence.entity.foo.not_cached;

import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;

/**
 * Metamodel de la class FooParent<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(FooParent.class)
public abstract class FooParent_ extends Foo_
{
  // D�finition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    FooParent_Relations.NODE_CHILD.addNode(FooChild_Relations.NODE_PARENT);
    Bid4WinEntity_.stopProtection();
  }
}