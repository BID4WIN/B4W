package com.bid4win.persistence.entity.auction;

import com.bid4win.commons.core.exception.UserException;

/**
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class TermsAbstractStub extends TermsAbstract<TermsAbstractStub>
{
  /**
   * Constructeur pour cr�ation par introspection
   */
  protected TermsAbstractStub()
  {
    super();
  }
  /**
   * Constructeur
   * @param creditNb Nombre de cr�dits � utiliser par l'ench�risseur
   * @param bidIncrementValue Montant d'un incr�ment d'ench�re
   * @throws UserException Si le nombre de cr�dits � utiliser est n�gatif ou le
   * montant d'un incr�ment d'ench�re inf�rieur ou �gal � z�ro
   */
  public TermsAbstractStub(int creditNb, double bidIncrementValue) throws UserException
  {
    super(creditNb, bidIncrementValue);
  }
}
