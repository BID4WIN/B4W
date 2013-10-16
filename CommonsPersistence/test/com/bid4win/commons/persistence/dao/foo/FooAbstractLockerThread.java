package com.bid4win.commons.persistence.dao.foo;

import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.entity.foo.FooAbstract;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <FOO> Entité sujet du test<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class FooAbstractLockerThread<FOO extends FooAbstract<FOO>>
       extends Thread
{
  /** TODO A COMMENTER */
  private FooAbstractDaoSpringTester<FOO> test = null;
  /** TODO A COMMENTER */
  private long id = -1;
  /** TODO A COMMENTER */
  private boolean lockDone = false;
  /** TODO A COMMENTER */
  private Object wait = new Object();

  /**
   *
   * TODO A COMMENTER
   */
  public FooAbstractLockerThread()
  {
    super();
  }

  /**
   *
   * TODO A COMMENTER
   * @param test TODO A COMMENTER
   * @param id TODO A COMMENTER
   */
  public void init(FooAbstractDaoSpringTester<FOO> test, long id)
  {
    this.test = test;
    this.id = id;
  }
  /**
   *
   * TODO A COMMENTER {@inheritDoc}
   * @see java.lang.Thread#run()
   */
  @Override
  public void run()
  {
    try
    {
      this.getLocker().lock(this, this.id, this.wait);
    }
    catch(PersistenceException ex)
    {
      ex.printStackTrace();
    }
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public FooAbstractDaoSpringTester<FOO> getTest()
  {
    return this.test;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public abstract FooAbstractLocker<FOO> getLocker();
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public boolean isLockDone()
  {
    return this.lockDone;
  }
  /**
   *
   * TODO A COMMENTER
   */
  public void setLockDone()
  {
    this.lockDone = true;
  }
  /**
   *
   * TODO A COMMENTER
   */
  public void end()
  {
    this.getLocker().end(this.wait);
  }
}