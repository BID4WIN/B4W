package com.bid4win.commons.persistence.entity.foo.not_cached;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;

/**
 * Metamodel de la class FooChild1<BR>
 * <BR><BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(FooChild1.class)
public abstract class FooChild1_ extends FooChild_
{
  /** Définition du parent de l'objet */
  public static volatile SingularAttribute<FooChild1, FooParent1> parent;

  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}