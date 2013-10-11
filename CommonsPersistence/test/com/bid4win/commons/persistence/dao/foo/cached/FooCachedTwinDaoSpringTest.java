package com.bid4win.commons.persistence.dao.foo.cached;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.persistence.dao.foo.FooAbstractDaoSpringTester;
import com.bid4win.commons.persistence.entity.foo.cached.FooCachedTwin;

/**
 * Test du DAO de la classe FooCachedTwin<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class FooCachedTwinDaoSpringTest extends FooAbstractDaoSpringTester<FooCachedTwin>
{
  /** R�f�rence du DAO � tester */
  @Autowired
  @Qualifier("FooCachedTwinDao")
  private FooCachedTwinDaoSpring dao;

  /**
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testRelation() throws Bid4WinException
  {
    System.out.println("### Relation test");

    FooCachedTwin twin1 = this.createFoo();
    FooCachedTwin twin2 = this.createFoo();
    twin1.defineTwin(twin2);

    this.getDao().add(twin1);

    FooCachedTwin result1 = this.getDao().getById(twin1.getId());
    FooCachedTwin result2 = this.getDao().getById(twin2.getId());
    assertTrue("wrong result", result1.identical(twin1));
    assertTrue("wrong result", result2.identical(twin2));
    System.out.println("### Relation test");
  }

  /**
   * Permet de cr�er l'entit� � utiliser pour les tests
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.foo.FooAbstractDaoSpringTester#createFoo()
   */
  @Override
  protected FooCachedTwin createFoo()
  {
    return new FooCachedTwin("VALUE", new Bid4WinDate());
  }
  /**
   * Getter du DAO � tester
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.foo.FooAbstractDaoSpringTester#getDao()
   */
  @Override
  public FooCachedTwinDaoSpring getDao()
  {
    return this.dao;
  }
}
