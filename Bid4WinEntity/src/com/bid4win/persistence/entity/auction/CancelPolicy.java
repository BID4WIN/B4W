package com.bid4win.persistence.entity.auction;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;

import com.bid4win.commons.core.exception.UserException;

/**
 * Cette classe d�fini la politique d'annulation d'une vente aux ench�res<BR>
 * <BR>
 * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class CancelPolicy<CLASS extends CancelPolicy<CLASS>>
       extends CancelPolicyAbstract<CLASS>
{
  /**
   * Constructeur pour cr�ation par introspection
   */
  protected CancelPolicy()
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
  public CancelPolicy(double creditNbThreshold, int creditNbPaidBonus) throws UserException
  {
    super(creditNbThreshold, creditNbPaidBonus);
  }
}
