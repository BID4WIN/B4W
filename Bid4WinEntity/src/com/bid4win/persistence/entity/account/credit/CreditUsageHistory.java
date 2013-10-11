package com.bid4win.persistence.entity.account.credit;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.exception.ProtectionException;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <CLASS> TODO A COMMENTER<BR>
 * @param <INVOLVEMENT> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
//Annotation pour la persistence
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class CreditUsageHistory<CLASS extends CreditUsageAbstract<CLASS, CreditBundleHistory, INVOLVEMENT>,
                                         INVOLVEMENT extends CreditInvolvement<INVOLVEMENT, CLASS, CreditBundleHistory, ?>>
       extends CreditUsageAbstract<CLASS, CreditBundleHistory, INVOLVEMENT>
{
  /**
   * Constructeur pour création par introspection
   */
  protected CreditUsageHistory()
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
  protected CreditUsageHistory(CreditBundleHistory bundle, INVOLVEMENT involvement, int usedNb)
            throws ProtectionException, UserException
  {
    super(bundle, involvement, usedNb);
  }

}
