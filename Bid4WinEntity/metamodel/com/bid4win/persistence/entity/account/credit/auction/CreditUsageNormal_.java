package com.bid4win.persistence.entity.account.credit.auction;

import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.persistence.entity.account.credit.CreditUsage_;

/**
 * Metamodel de la classe CreditUsageNormal<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(CreditUsageNormal.class)
public class CreditUsageNormal_ extends CreditUsage_
{
  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
