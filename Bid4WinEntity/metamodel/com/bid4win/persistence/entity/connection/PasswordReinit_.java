package com.bid4win.persistence.entity.connection;

import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.commons.persistence.entity.connection.PasswordReinitAbstract_;

/**
 * Metamodel de la classe PasswordReinit<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(PasswordReinit.class)
public abstract class PasswordReinit_ extends PasswordReinitAbstract_
{
  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
