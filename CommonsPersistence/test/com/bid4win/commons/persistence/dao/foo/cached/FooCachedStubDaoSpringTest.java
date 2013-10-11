package com.bid4win.commons.persistence.dao.foo.cached;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.persistence.dao.foo.FooAbstractDaoSpringTester;
import com.bid4win.commons.persistence.entity.foo.cached.FooCachedStub;

/**
 * Test du DAO de la classe Foo<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class FooCachedStubDaoSpringTest extends FooAbstractDaoSpringTester<FooCachedStub>
{
  /** Référence du DAO à tester */
  @Autowired
  @Qualifier("FooCachedStubDao")
  private FooCachedStubDaoSpring dao;
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("FooCachedStubLockerThread")
  private FooCachedStubLockerThread locker1 = null;
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("FooCachedStubLockerThread")
  private FooCachedStubLockerThread locker2 = null;

  /**
   * Test of lock(ID), of class Bid4WinDao.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testLock_ID() throws Bid4WinException
  {
    System.out.println("### lock(ID)");
    FooCachedStub foo = this.createFoo();
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
    this.locker2.end();
    System.out.println("### lock(ID)");
  }

  /**
   * Permet de créer l'entité à utiliser pour les tests
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.foo.FooAbstractDaoSpringTester#createFoo()
   */
  @Override
  protected FooCachedStub createFoo()
  {
    return new FooCachedStub("VALUE", new Bid4WinDate());
  }
  /**
   * Getter du DAO à tester
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.foo.FooAbstractDaoSpringTester#getDao()
   */
  @Override
  public FooCachedStubDaoSpring getDao()
  {
    return this.dao;
  }
}
