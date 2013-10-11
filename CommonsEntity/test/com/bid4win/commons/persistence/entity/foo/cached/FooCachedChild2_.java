package com.bid4win.commons.persistence.entity.foo.cached;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;

/**
 * Metamodel de la class FooCachedChild2<BR>
 * <BR><BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(FooCachedChild2.class)
public abstract class FooCachedChild2_ extends FooCachedChild_
{
  /** Définition du parent de l'objet */
  public static volatile SingularAttribute<FooCachedChild2, FooCachedParent2> parent;

  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}