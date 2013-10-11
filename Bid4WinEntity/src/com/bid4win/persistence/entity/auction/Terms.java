package com.bid4win.persistence.entity.auction;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;

import com.bid4win.commons.core.exception.UserException;

/**
 * Cette classe défini les conditions d'enchère d'une vente<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class Terms<CLASS extends Terms<CLASS>> extends TermsAbstract<CLASS>
{
  /**
   * Constructeur pour création par introspection
   */
  protected Terms()
  {
    super();
  }
  /**
   * Constructeur
   * @param creditNbPerBid Nombre de crédits à utiliser par l'utilisateur à chaque
   * enchère
   * @param bidIncrementValue Montant d'un incrément d'enchère dans la monnaie par
   * défaut
   * @throws UserException Si le nombre de crédits à utiliser est négatif ou le
   * montant d'un incrément d'enchère inférieur ou égal à zéro
   */
  public Terms(int creditNbPerBid, double bidIncrementValue) throws UserException
  {
    super(creditNbPerBid, bidIncrementValue);
  }
}
