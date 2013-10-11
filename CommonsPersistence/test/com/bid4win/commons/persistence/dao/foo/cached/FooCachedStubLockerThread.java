package com.bid4win.commons.persistence.dao.foo.cached;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bid4win.commons.persistence.dao.foo.FooAbstractLockerThread;
import com.bid4win.commons.persistence.entity.foo.cached.FooCachedStub;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class FooCachedStubLockerThread extends FooAbstractLockerThread<FooCachedStub>
{
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("FooCachedStubLocker")
  private FooCachedStubLocker locker;

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.foo.FooAbstractLockerThread#getLocker()
   */
  @Override
  public FooCachedStubLocker getLocker()
  {
    return this.locker;
  }
}
