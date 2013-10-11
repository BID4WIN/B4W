package com.bid4win.commons.persistence.dao.foo.not_cached;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bid4win.commons.persistence.dao.foo.FooAbstractLockerThread;
import com.bid4win.commons.persistence.entity.foo.not_cached.FooStub;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class FooStubLockerThread extends FooAbstractLockerThread<FooStub>
{
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("FooStubLocker")
  private FooStubLocker locker;

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.foo.FooAbstractLockerThread#getLocker()
   */
  @Override
  public FooStubLocker getLocker()
  {
    return this.locker;
  }
}
