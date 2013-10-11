package com.bid4win.persistence.entity.account.credit;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultipleAutoID_Relations;

/**
 * Définition des relations de la classe CreditBundleAbstract vers les autres entités<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class CreditBundleAbstract_Relations extends AccountBasedEntityMultipleAutoID_Relations
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
