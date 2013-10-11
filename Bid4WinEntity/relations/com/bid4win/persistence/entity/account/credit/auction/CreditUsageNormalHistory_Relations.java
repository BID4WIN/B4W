package com.bid4win.persistence.entity.account.credit.auction;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.persistence.entity.account.credit.CreditUsageHistory_Relations;

/**
 * Définition des relations de la classe CreditUsageNormalHistory vers les autres
 * entités<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class CreditUsageNormalHistory_Relations extends CreditUsageHistory_Relations
{
  // Démarre la protection des relations
  static
  {
    Bid4WinEntity_Relations.startProtection();
  }
  // Arrête la protection des relations
  static
  {
    Bid4WinEntity_Relations.stopProtection();
  }
}
