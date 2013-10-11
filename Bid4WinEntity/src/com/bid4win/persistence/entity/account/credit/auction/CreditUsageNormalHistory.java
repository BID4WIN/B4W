package com.bid4win.persistence.entity.account.credit.auction;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.persistence.entity.account.credit.CreditBundleHistory;
import com.bid4win.persistence.entity.account.credit.CreditUsageHistory;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
//Annotation pour la persistence
@Entity
@Access(AccessType.FIELD)
public class CreditUsageNormalHistory
       extends CreditUsageHistory<CreditUsageNormalHistory, CreditInvolvementNormalHistory>
{
  /**
   * Constructeur pour création par introspection
   */
  protected CreditUsageNormalHistory()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param bundle Lot de provenance des crédits utilisés
   * @param involvement Implication des crédits utilisés
   * @param usedNb Nombre de crédits utilisés
   * @throws ProtectionException Si l'implication globale en argument est protégée
   * @throws UserException Si l'implication globale ou la provenance des crédits
   * est nulle ou si leur nombre est inférieur à un
   */
  protected CreditUsageNormalHistory(CreditBundleHistory bundle,
                                     CreditInvolvementNormalHistory involvement,
                                     int usedNb)
            throws ProtectionException, UserException
  {
    super(bundle, involvement, usedNb);
  }
}
