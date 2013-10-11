package com.bid4win.commons.persistence.entity.account;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;

/**
 * Metamodel de la classe AccountBasedEntityMultipleAutoID<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(AccountBasedEntityMultipleAutoID.class)
public abstract class AccountBasedEntityMultipleAutoID_ extends AccountBasedEntityMultiple_
{
  /** Définition de l'identifiant de l'entité*/
  public static volatile SingularAttribute<AccountBasedEntityMultipleAutoID<?, ?>, Long> id;

  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
