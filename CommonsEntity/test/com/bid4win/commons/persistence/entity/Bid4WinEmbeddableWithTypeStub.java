package com.bid4win.commons.persistence.entity;

import com.bid4win.commons.core.Bid4WinObjectTypeStub1;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.reference.MessageRef.CurrencyRef;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinEmbeddableWithTypeStub
       extends Bid4WinEmbeddableWithType<Bid4WinEmbeddableWithTypeStub, Bid4WinObjectTypeStub1>
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = 8527864276544360521L;

  /**
   * Constructeur pour cr�ation par introspection
   */
  protected Bid4WinEmbeddableWithTypeStub()
  {
    super();
  }
  /**
   * Constructeur
   * @param type Type associ� � l'objet
   * @throws UserException Si les param�tres entr�s sont invalides
   */
  public Bid4WinEmbeddableWithTypeStub(Bid4WinObjectTypeStub1 type) throws UserException
  {
    super(type);
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#getMessageRefBase()
   */
  @Override
  public MessageRef getMessageRefBase()
  {
    return CurrencyRef.CURRENCY;
  }
}
