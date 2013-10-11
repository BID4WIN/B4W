package com.bid4win.commons.persistence.entity.foo.cached;

import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;

/**
 * Metamodel de la class FooCachedParent3<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(FooCachedParent3.class)
public abstract class FooCachedParent3_ extends FooCachedParent_
{
  /** D�finition de la map d'enfants inclus dans l'objet parent */
  public static volatile MapAttribute<FooCachedParent3, String, FooCachedChild3> childMapInternal;

  // D�finition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}