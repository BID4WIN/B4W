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
 * @author Emeric Fill�tre
 */
//Annotation pour la persistence
@Entity
@Access(AccessType.FIELD)
public class CreditUsageNormalHistory
       extends CreditUsageHistory<CreditUsageNormalHistory, CreditInvolvementNormalHistory>
{
  /**
   * Constructeur pour cr�ation par introspection
   */
  protected CreditUsageNormalHistory()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param bundle Lot de provenance des cr�dits utilis�s
   * @param involvement Implication des cr�dits utilis�s
   * @param usedNb Nombre de cr�dits utilis�s
   * @throws ProtectionException Si l'implication globale en argument est prot�g�e
   * @throws UserException Si l'implication globale ou la provenance des cr�dits
   * est nulle ou si leur nombre est inf�rieur � un
   */
  protected CreditUsageNormalHistory(CreditBundleHistory bundle,
                                     CreditInvolvementNormalHistory involvement,
                                     int usedNb)
            throws ProtectionException, UserException
  {
    super(bundle, involvement, usedNb);
  }
}
