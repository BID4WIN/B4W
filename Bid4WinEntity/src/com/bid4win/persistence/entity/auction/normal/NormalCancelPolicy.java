package com.bid4win.persistence.entity.auction.normal;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.persistence.entity.auction.CancelPolicy;

/**
*
* TODO A COMMENTER<BR>
* <BR>
* @author Emeric Fillâtre
*/
@Embeddable
@Access(AccessType.FIELD)
public class NormalCancelPolicy extends CancelPolicy<NormalCancelPolicy>
{
  /**
   * Constructeur pour création par introspection
   */
  protected NormalCancelPolicy()
  {
    super();
  }
  /**
   * Constructeur
   * @param creditNbThreshold Seuil du nombre de crédits utilisés sur la vente aux
   * enchères en dessous duquel elle sera annulée
   * @param creditNbPaidBonus Nombre de crédits bonus par crédit payé utilisé sur
   * une vente aux enchères annulée
   * @throws UserException Si le seuil ou le nombre de crédits bonus est négatif
   */
  public NormalCancelPolicy(double creditNbThreshold, int creditNbPaidBonus) throws UserException
  {
    super(creditNbThreshold, creditNbPaidBonus);
  }
}
