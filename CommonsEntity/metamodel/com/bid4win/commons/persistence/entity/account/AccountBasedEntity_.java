package com.bid4win.commons.persistence.entity.account;

import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;

/**
 * Metamodel de la classe AccountBasedEntity<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(AccountBasedEntity.class)
public abstract class AccountBasedEntity_ extends Bid4WinEntity_
{
  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
