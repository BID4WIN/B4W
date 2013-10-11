package com.bid4win.persistence.entity.auction.prebooked;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.persistence.entity.auction.Terms;

/**
 * Cette classe d�fini les conditions d'ench�re d'une vente avec pr�-inscription<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Embeddable
@Access(AccessType.FIELD)
public class PrebookedTerms extends Terms<PrebookedTerms>
{
  /**
   * Constructeur pour cr�ation par introspection
   */
  @SuppressWarnings("unused")
  private PrebookedTerms()
  {
    super();
  }
  /**
   * Constructeur
   * @param creditNbPerBid Nombre de cr�dits � utiliser par l'utilisateur � chaque
   * ench�re
   * @param bidIncrementValue Montant d'un incr�ment d'ench�re
   * @throws UserException Si le nombre de cr�dits � utiliser est n�gatif ou le
   * montant d'un incr�ment d'ench�re inf�rieur ou �gal � z�ro
   */
  public PrebookedTerms(int creditNbPerBid, double bidIncrementValue) throws UserException
  {
    super(creditNbPerBid, bidIncrementValue);
  }
}
