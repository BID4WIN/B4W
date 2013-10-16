package com.bid4win.commons.persistence.dao.foo.not_cached;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.persistence.dao.foo.FooAbstractDaoSpringTester;
import com.bid4win.commons.persistence.entity.foo.not_cached.FooStub;

/**
 * Test du DAO de la classe Foo<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class FooStubDaoSpringTest extends FooAbstractDaoSpringTester<FooStub>
{
  /** Référence du DAO à tester */
  @Autowired
  @Qualifier("FooStubDao")
  private FooStubDaoSpring dao;
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("FooStubLockerThread")
  private FooStubLockerThread locker1 = null;
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("FooStubLockerThread")
  private FooStubLockerThread locker2 = null;

  /**
   *
   * TODO A COMMENTER
   * @throws Bid4WinException Issue not expected during this test
   */
  //@Test
  public void testLock_Long() throws Bid4WinException
  {
    FooStub foo = this.createFoo();
    this.getDao().add(foo);

    this.locker1.init(this, foo.getId());
    this.locker2.init(this, foo.getId());

    this.locker1.start();
    synchronized(foo)
    {
      try
      {
        foo.wait(2000);
      }
      catch(InterruptedException ex)
      {
        ex.printStackTrace();
      }
    }
    assertTrue("First lock not done", this.locker1.isLockDone());

    this.locker2.start();
    synchronized(foo)
    {
      try
      {
        foo.wait(2000);
      }
      catch(InterruptedException ex)
      {
        ex.printStackTrace();
      }
    }
    assertFalse("Second lock done", this.locker2.isLockDone());

    this.locker1.end();
    synchronized(foo)
    {
      try
      {
        foo.wait(2000);
      }
      catch(InterruptedException ex)
      {
        ex.printStackTrace();
      }
    }
    assertTrue("Second lock not done", this.locker2.isLockDone());
  }

  /**
   * Permet de créer l'entité à utiliser pour les tests
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.foo.FooAbstractDaoSpringTester#createFoo()
   */
  @Override
  protected FooStub createFoo()
  {
    return new FooStub("VALUE", new Bid4WinDate());
  }
  /**
   * Getter du DAO à tester
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.foo.FooAbstractDaoSpringTester#getDao()
   */
  @Override
  public FooStubDaoSpring getDao()
  {
    return this.dao;
  }
}
