package com.bid4win.commons.persistence.entity.foo.not_cached;

import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;

/**
 * Metamodel de la class FooParent2<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(FooParent2.class)
public abstract class FooParent2_ extends FooParent_
{
  /** Définition de la map d'enfants inclus dans l'objet parent */
  public static volatile MapAttribute<FooParent2, String, FooChild2> childMapInternal;

  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}