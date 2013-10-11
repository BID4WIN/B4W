package com.bid4win.persistence.entity.account.credit;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.exception.ProtectionException;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class CreditUsageHistoryStub extends CreditUsageHistory<CreditUsageHistoryStub, CreditInvolvementHistoryStub>
{
  /**
   * Constructeur complet
   * @param bundle Lot de provenance des crédits utilisés
   * @param involvement Implication des crédits utilisés
   * @param usedNb Nombre de crédits utilisés
   * @throws ProtectionException Si l'implication globale en argument est protégée
   * @throws UserException Si l'implication globale ou la provenance des crédits
   * est nulle ou si leur nombre est inférieur à un
   */
  protected CreditUsageHistoryStub(CreditBundleHistory bundle, CreditInvolvementHistoryStub involvement, int usedNb)
            throws ProtectionException, UserException
  {
    super(bundle, involvement, usedNb);
  }
}
