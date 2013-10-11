package com.bid4win.persistence.entity.auction;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;

import com.bid4win.commons.core.exception.UserException;

/**
 * Cette classe d�fini les conditions d'ench�re d'une vente<BR>
 * <BR>
 * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class Terms<CLASS extends Terms<CLASS>> extends TermsAbstract<CLASS>
{
  /**
   * Constructeur pour cr�ation par introspection
   */
  protected Terms()
  {
    super();
  }
  /**
   * Constructeur
   * @param creditNbPerBid Nombre de cr�dits � utiliser par l'utilisateur � chaque
   * ench�re
   * @param bidIncrementValue Montant d'un incr�ment d'ench�re dans la monnaie par
   * d�faut
   * @throws UserException Si le nombre de cr�dits � utiliser est n�gatif ou le
   * montant d'un incr�ment d'ench�re inf�rieur ou �gal � z�ro
   */
  public Terms(int creditNbPerBid, double bidIncrementValue) throws UserException
  {
    super(creditNbPerBid, bidIncrementValue);
  }
}
