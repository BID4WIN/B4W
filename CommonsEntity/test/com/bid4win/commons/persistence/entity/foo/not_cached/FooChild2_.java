package com.bid4win.commons.persistence.entity.foo.not_cached;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;

/**
 * Metamodel de la class FooChild2<BR>
 * <BR><BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(FooChild2.class)
public abstract class FooChild2_ extends FooChild_
{
  /** D�finition du parent de l'objet */
  public static volatile SingularAttribute<FooChild2, FooParent2> parent;

  // D�finition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}