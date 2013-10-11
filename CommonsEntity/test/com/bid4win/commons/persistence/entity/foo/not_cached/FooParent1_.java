package com.bid4win.commons.persistence.entity.foo.not_cached;

import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;

/**
 * Metamodel de la class FooParent1<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(FooParent1.class)
public abstract class FooParent1_ extends FooParent_
{
  /** Définition de la map d'enfants inclus dans l'objet parent */
  public static volatile MapAttribute<FooParent1, String, FooChild1> childMapInternal;

  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}