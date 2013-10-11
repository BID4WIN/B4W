package com.bid4win.persistence.entity.auction.prebooked;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.persistence.entity.auction.CancelPolicy;

/**
*
* TODO A COMMENTER<BR>
* <BR>
* @author Emeric Fill�tre
*/
@Embeddable
@Access(AccessType.FIELD)
public class PrebookedCancelPolicy extends CancelPolicy<PrebookedCancelPolicy>
{
  /**
   * Constructeur pour cr�ation par introspection
   */
  protected PrebookedCancelPolicy()
  {
    super();
  }
  /**
   * Constructeur
   * @param creditNbThreshold Seuil du nombre de cr�dits utilis�s sur la vente aux
   * ench�res en dessous duquel elle sera annul�e
   * @param creditNbPaidBonus Nombre de cr�dits bonus par cr�dit pay� utilis� sur
   * une vente aux ench�res annul�e
   * @throws UserException Si le seuil ou le nombre de cr�dits bonus est n�gatif
   */
  public PrebookedCancelPolicy(double creditNbThreshold, int creditNbPaidBonus) throws UserException
  {
    super(creditNbThreshold, creditNbPaidBonus);
  }
}
