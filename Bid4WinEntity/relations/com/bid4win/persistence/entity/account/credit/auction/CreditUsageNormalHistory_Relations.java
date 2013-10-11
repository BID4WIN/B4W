package com.bid4win.persistence.entity.account.credit.auction;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.persistence.entity.account.credit.CreditUsageHistory_Relations;

/**
 * D�finition des relations de la classe CreditUsageNormalHistory vers les autres
 * entit�s<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class CreditUsageNormalHistory_Relations extends CreditUsageHistory_Relations
{
  // D�marre la protection des relations
  static
  {
    Bid4WinEntity_Relations.startProtection();
  }
  // Arr�te la protection des relations
  static
  {
    Bid4WinEntity_Relations.stopProtection();
  }
}
