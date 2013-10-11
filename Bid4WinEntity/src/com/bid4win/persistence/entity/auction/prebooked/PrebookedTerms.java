package com.bid4win.persistence.entity.auction.prebooked;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.persistence.entity.auction.Terms;

/**
 * Cette classe défini les conditions d'enchère d'une vente avec pré-inscription<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Embeddable
@Access(AccessType.FIELD)
public class PrebookedTerms extends Terms<PrebookedTerms>
{
  /**
   * Constructeur pour création par introspection
   */
  @SuppressWarnings("unused")
  private PrebookedTerms()
  {
    super();
  }
  /**
   * Constructeur
   * @param creditNbPerBid Nombre de crédits à utiliser par l'utilisateur à chaque
   * enchère
   * @param bidIncrementValue Montant d'un incrément d'enchère
   * @throws UserException Si le nombre de crédits à utiliser est négatif ou le
   * montant d'un incrément d'enchère inférieur ou égal à zéro
   */
  public PrebookedTerms(int creditNbPerBid, double bidIncrementValue) throws UserException
  {
    super(creditNbPerBid, bidIncrementValue);
  }
}
