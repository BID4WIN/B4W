package com.bid4win.commons.persistence.entity.account;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;

/**
 * Metamodel de la classe AccountBasedKey<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(AccountBasedKey.class)
public abstract class AccountBasedKey_ extends AccountBasedEntitySingle_
{
  /** Définition de l'identifiant de la clé */
  public static volatile SingularAttribute<AccountBasedKey<?, ?>, String> id;

  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
