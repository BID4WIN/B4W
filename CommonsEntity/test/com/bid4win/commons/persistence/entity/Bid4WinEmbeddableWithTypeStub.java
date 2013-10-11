package com.bid4win.commons.persistence.entity;

import com.bid4win.commons.core.Bid4WinObjectTypeStub1;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.reference.MessageRef.CurrencyRef;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinEmbeddableWithTypeStub
       extends Bid4WinEmbeddableWithType<Bid4WinEmbeddableWithTypeStub, Bid4WinObjectTypeStub1>
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = 8527864276544360521L;

  /**
   * Constructeur pour création par introspection
   */
  protected Bid4WinEmbeddableWithTypeStub()
  {
    super();
  }
  /**
   * Constructeur
   * @param type Type associé à l'objet
   * @throws UserException Si les paramètres entrés sont invalides
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
