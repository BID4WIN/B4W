package com.bid4win.commons.persistence.entity.connection;

import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.commons.persistence.entity.account.AccountBasedKey_;

/**
 * Metamodel de la classe PasswordReinitAbstract<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(PasswordReinitAbstract.class)
public class PasswordReinitAbstract_ extends AccountBasedKey_
{
  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
