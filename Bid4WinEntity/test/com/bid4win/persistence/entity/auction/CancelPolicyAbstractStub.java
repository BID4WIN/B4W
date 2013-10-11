package com.bid4win.persistence.entity.auction;

import com.bid4win.commons.core.exception.UserException;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class CancelPolicyAbstractStub extends CancelPolicyAbstract<CancelPolicyAbstractStub>
{
  /**
   * Constructeur pour création par introspection
   */
  protected CancelPolicyAbstractStub()
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
  public CancelPolicyAbstractStub(int creditNbThreshold, int creditNbPaidBonus) throws UserException
  {
    super(creditNbThreshold, creditNbPaidBonus);
  }
}
