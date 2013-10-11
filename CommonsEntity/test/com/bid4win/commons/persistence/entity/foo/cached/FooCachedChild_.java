package com.bid4win.commons.persistence.entity.foo.cached;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.persistence.entity.Bid4WinEntity_;

/**
 * Metamodel de la class FooCachedChild<BR>
 * <BR><BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(FooCachedChild.class)
public abstract class FooCachedChild_ extends Bid4WinEntity_
{
  /** D�finition de l'identifiant de l'objet */
  public static volatile SingularAttribute<FooCachedChild<?, ?>, Integer> id;
  /** D�finition de la valeur de l'objet */
  public static volatile SingularAttribute<FooCachedChild<?, ?>, String> value;
  /** D�finition de la date de l'objet */
  public static volatile SingularAttribute<FooCachedChild<?, ?>, Bid4WinDate> date;

  // D�finition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}