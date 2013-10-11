package com.bid4win.commons.persistence.entity.foo.cached;

import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;

/**
 * Metamodel de la class FooCachedParent2<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(FooCachedParent2.class)
public abstract class FooCachedParent2_ extends FooCachedParent_
{
  /** Définition de la map d'enfants inclus dans l'objet parent */
  public static volatile MapAttribute<FooCachedParent2, String, FooCachedChild2> childMapInternal;

  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}