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
@Component("Bid4WinFileResourceMultiPartStoreStub")
@Scope("singleton")
public class Bid4WinFileResourceMultiPartStoreStub
       extends Bid4WinFileResourceMultiPartStore<Bid4WinFileResourceMultiPartStub,
                                                 String, Bid4WinObjectTypeStub1,
                                                 Bid4WinFileResourcePartStub>
{
  /** Référence de la classe de validation des ressources */
  @Autowired
  @Qualifier("Bid4WinFileResourcePartStoreStub")
  private Bid4WinFileResourcePartStoreStub partStore;

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceMultiPartStore#getPartTypeDefault()
   */
  @Override
  public Bid4WinObjectTypeStub1 getPartTypeDefault()
  {
    return Bid4WinObjectTypeStub1.TYPE1;
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinFileResourceMultiPartStore#getPartStore()
   */
  @Override
  public Bid4WinFileResourcePartStoreStub getPartStore()
  {
    return this.partStore;
  }
}
