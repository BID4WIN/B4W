package com.bid4win.commons.persistence.entity.foo.cached;

import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;

/**
 * Metamodel de la class FooCachedParent1<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(FooCachedParent1.class)
public abstract class FooCachedParent1_ extends FooCachedParent_
{
  /** Définition de la map d'enfants inclus dans l'objet parent */
  public static volatile MapAttribute<FooCachedParent1, String, FooCachedChild1> childMap;

  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}