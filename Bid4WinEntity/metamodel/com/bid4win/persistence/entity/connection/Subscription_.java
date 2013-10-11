package com.bid4win.persistence.entity.connection;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.commons.persistence.entity.account.AccountBasedKey_;

/**
 * Metamodel de la classe Subscription<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(Subscription.class)
public abstract class Subscription_ extends AccountBasedKey_
{
  /** Définition de la date de validation de l'inscription */
  public static volatile SingularAttribute<Subscription, Bid4WinDate> validationDate;

  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
