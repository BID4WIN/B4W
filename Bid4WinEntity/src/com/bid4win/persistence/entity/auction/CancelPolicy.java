package com.bid4win.persistence.entity.auction;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;

import com.bid4win.commons.core.exception.UserException;

/**
 * Cette classe défini la politique d'annulation d'une vente aux enchères<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class CancelPolicy<CLASS extends CancelPolicy<CLASS>>
       extends CancelPolicyAbstract<CLASS>
{
  /**
   * Constructeur pour création par introspection
   */
  protected CancelPolicy()
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
  public CancelPolicy(double creditNbThreshold, int creditNbPaidBonus) throws UserException
  {
    super(creditNbThreshold, creditNbPaidBonus);
  }
}
