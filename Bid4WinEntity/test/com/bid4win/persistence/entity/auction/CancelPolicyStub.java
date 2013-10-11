package com.bid4win.persistence.entity.auction;

import com.bid4win.commons.core.exception.UserException;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class CancelPolicyStub extends CancelPolicy<CancelPolicyStub>
{
  /**
   * Constructeur pour cr�ation par introspection
   */
  protected CancelPolicyStub()
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
  public CancelPolicyStub(int creditNbThreshold, int creditNbPaidBonus) throws UserException
  {
    super(creditNbThreshold, creditNbPaidBonus);
  }
}
