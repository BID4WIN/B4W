package com.bid4win.commons.core.io.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.core.Bid4WinObjectTypeStub1;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("Bid4WinFileResourceMultiPartValidatorStub")
@Scope("singleton")
public class Bid4WinFileResourceMultiPartValidatorStub
       extends Bid4WinFileResourceMultiPartValidator<Bid4WinFileResourceMultiPartStub, String,
                                                     Bid4WinObjectTypeStub1, Bid4WinFileResourcePartStub>
{
  /** Référence du magasin de ressources à valider */
  @Autowired
  @Qualifier("Bid4WinFileResourceMultiPartStoreStub")
  private Bid4WinFileResourceMultiPartStoreStub store;

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceValidator#getStore()
   */
  @Override
  public Bid4WinFileResourceMultiPartStoreStub getStore()
  {
    return this.store;
  }
}
