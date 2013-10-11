package com.bid4win.commons.persistence.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Metamodel de la classe Bid4WinEntityAutoID<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(Bid4WinEntityAutoID.class)
public class Bid4WinEntityAutoID_ extends Bid4WinEntity_
{
  /** D�finition de l'identifiant de l'entit� */
  public static volatile SingularAttribute<Bid4WinEntityAutoID<?>, Long> id;

  // D�finition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
