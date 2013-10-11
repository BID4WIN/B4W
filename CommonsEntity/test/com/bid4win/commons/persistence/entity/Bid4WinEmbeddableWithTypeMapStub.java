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
public class Bid4WinEmbeddableWithTypeMapStub
       extends Bid4WinEmbeddableWithTypeMap<Bid4WinEmbeddableWithTypeMapStub, Bid4WinEmbeddableWithTypeStub, Bid4WinObjectTypeStub1>
{
  /**
   *
   * TODO A COMMENTER
   */
  protected Bid4WinEmbeddableWithTypeMapStub()
  {
    super();
  }
  /**
   * Constructeur
   * @param embeddeds Objets associ�s � des types d�finis � r�f�rencer
   * @throws UserException Si les param�tres entr�s sont invalides
   */
  public Bid4WinEmbeddableWithTypeMapStub(Bid4WinEmbeddableWithTypeStub ... embeddeds) throws UserException
  {
    super(embeddeds);
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
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddableWithTypeMap#getTypeClass()
   */
  @Override
  public Class<Bid4WinObjectTypeStub1> getTypeClass()
  {
    return Bid4WinObjectTypeStub1.class;
  }
}
