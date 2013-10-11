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
 * @author Emeric Fill�tre
 */
//Annotation pour la persistence
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class CreditUsageHistory<CLASS extends CreditUsageAbstract<CLASS, CreditBundleHistory, INVOLVEMENT>,
                                         INVOLVEMENT extends CreditInvolvement<INVOLVEMENT, CLASS, CreditBundleHistory, ?>>
       extends CreditUsageAbstract<CLASS, CreditBundleHistory, INVOLVEMENT>
{
  /**
   * Constructeur pour cr�ation par introspection
   */
  protected CreditUsageHistory()
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
  protected CreditUsageHistory(CreditBundleHistory bundle, INVOLVEMENT involvement, int usedNb)
            throws ProtectionException, UserException
  {
    super(bundle, involvement, usedNb);
  }

}
