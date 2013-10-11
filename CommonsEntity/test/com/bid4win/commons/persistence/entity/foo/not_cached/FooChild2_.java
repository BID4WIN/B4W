package com.bid4win.commons.persistence.entity.foo.not_cached;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;

/**
 * Metamodel de la class FooChild2<BR>
 * <BR><BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(FooChild2.class)
public abstract class FooChild2_ extends FooChild_
{
  /** Définition du parent de l'objet */
  public static volatile SingularAttribute<FooChild2, FooParent2> parent;

  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}