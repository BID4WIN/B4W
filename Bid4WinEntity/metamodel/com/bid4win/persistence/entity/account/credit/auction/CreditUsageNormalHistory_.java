package com.bid4win.persistence.entity.account.credit.auction;

import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.persistence.entity.account.credit.CreditUsageHistory_;

/**
 * Metamodel de la classe CreditUsageNormalHistory<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(CreditUsageNormalHistory.class)
public class CreditUsageNormalHistory_ extends CreditUsageHistory_
{
  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
