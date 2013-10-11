package com.bid4win.persistence.entity.auction;

import com.bid4win.commons.core.exception.UserException;

/**
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class TermsAbstractStub extends TermsAbstract<TermsAbstractStub>
{
  /**
   * Constructeur pour création par introspection
   */
  protected TermsAbstractStub()
  {
    super();
  }
  /**
   * Constructeur
   * @param creditNb Nombre de crédits à utiliser par l'enchérisseur
   * @param bidIncrementValue Montant d'un incrément d'enchère
   * @throws UserException Si le nombre de crédits à utiliser est négatif ou le
   * montant d'un incrément d'enchère inférieur ou égal à zéro
   */
  public TermsAbstractStub(int creditNb, double bidIncrementValue) throws UserException
  {
    super(creditNb, bidIncrementValue);
  }
}
