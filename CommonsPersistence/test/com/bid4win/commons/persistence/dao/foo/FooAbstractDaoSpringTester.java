package com.bid4win.commons.persistence.dao.foo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.persistence.dao.Bid4WinDaoTester;
import com.bid4win.commons.persistence.entity.EntityGenerator;
import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;
import com.bid4win.commons.persistence.entity.foo.FooAbstract;
import com.bid4win.commons.persistence.entity.foo.not_cached.Foo;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <FOO> Entité sujet du test<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
//@ContextConfiguration(locations = "classpath:META-INF/config-non-jta/spring-persistence-foo.xml")
@TransactionConfiguration(transactionManager = "Bid4WinTransactionManager")
public abstract class FooAbstractDaoSpringTester<FOO extends FooAbstract<FOO>>
       extends Bid4WinDaoTester<FOO, Integer, AccountAbstractStub, EntityGenerator<AccountAbstractStub>>
{
  /**
   * Permet de préciser le DAO à tester
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDaoTester#getDao()
   */
  @Override
  public abstract FooAbstractDaoSpring<FOO> getDao();

  /**
   * Test of findOneByValue(String), of class FooAbstractDaoSpring.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testFindOneByValue_String()throws Bid4WinException
  {
    FOO foo = this.createFoo();
    // Effectue les tests
    try
    {
      assertNull("No data should have been returned",
                 this.getDao().findOneByValue(foo.getValue()));
    }
    catch(Exception ex)
    {
      fail("Should not have failed");
    }
    // Ajoute une entité
    this.add(foo);
    // Récupère l'entité ajoutée
    FOO expected = this.getById(foo.getId());
    // Effectue les tests
    FOO result = this.getDao().findOneByValue(foo.getValue());
    this.checkIdentical(expected, result);
    try
    {
      assertNull("No data should have been returned",
                 this.getDao().findOneByValue(foo.getValue() + "1"));
    }
    catch(Exception ex)
    {
      fail("Should not have failed");
    }
    // Ajoute une entité
    foo = this.createFoo();
    this.add(foo);
    // Effectue les tests
    try
    {
      this.getDao().findOneByValue(foo.getValue());
      fail("Should have failed because more than one data exist");
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
  }
  /**
   * Test of findListByValue(String), of class FooAbstractDaoSpring.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testFindListByValue_String()throws Bid4WinException
  {
    FOO foo = this.createFoo();
    // Effectue les tests
    Bid4WinList<FOO> result = this.getDao().findListByValue(foo.getValue());
    assertEquals("Wrong result size", 0, result.size());
    // Ajoute une entité
    this.add(foo);
    // Récupère l'entité ajoutée
    FOO expected1 = this.getById(foo.getId());
    // Effectue les tests
    result = this.getDao().findListByValue(foo.getValue());
    assertEquals("Wrong result size", 1, result.size());
    this.checkIdentical(expected1, result.get(0));
    result = this.getDao().findListByValue(foo.getValue() + "1");
    assertEquals("Wrong result size", 0, result.size());
    // Ajoute une entité
    foo = this.createFoo();
    this.add(foo);
    // Récupère l'entité ajoutée
    FOO expected2 = this.getById(foo.getId());
    // Effectue les tests
    result = this.getDao().findListByValue(foo.getValue());
    assertEquals("Wrong result size", 2, result.size());
    this.checkIdentical(expected1, result.get(0));
    this.checkIdentical(expected2, result.get(1));
  }
  /**
   * Test of findListByDate(Date), of class FooAbstractDaoSpring.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testFindListByDate_Date()throws Bid4WinException
  {
    FOO foo = this.createFoo();
    // Effectue les tests
    List<FOO> result = null;
    try
    {
      result = this.getDao().findListByDate(foo.getDate());
    }
    catch(RuntimeException ex)
    {
      ex.printStackTrace();
      throw ex;
    }
    assertEquals("Wrong result size", 0, result.size());
    // Ajoute une entité
    this.add(foo);
    // Récupère l'entité ajoutée
    FOO expected1 = this.getById(foo.getId());
    // Effectue les tests
    try
    {
      result = this.getDao().findListByDate(foo.getDate());
    }
    catch(RuntimeException ex)
    {
      ex.printStackTrace();
      throw ex;
    }
    assertEquals("Wrong result size", 1, result.size());
    this.checkIdentical(expected1, result.get(0));

    foo = this.createFoo();
    // Ajoute une entité
    this.add(foo);
    // Récupère l'entité ajoutée
    FOO expected2 = this.getById(foo.getId());
    // Effectue les tests
    result = this.getDao().findListByDate(expected1.getDate());
    assertEquals("Wrong result size", 2, result.size());
    this.checkIdentical(expected1, result.get(0));
    this.checkIdentical(expected2, result.get(1));
    result = this.getDao().findListByDate(expected2.getDate());
    assertEquals("Wrong result size", 1, result.size());
    this.checkIdentical(expected2, result.get(0));
  }
  /**
   * Test of findListByEmbeddedDate(EmbeddedDate), of class FooAbstractDaoSpring.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testFindListByEmbeddedDate_EmbeddedDate()throws Bid4WinException
  {
    FOO foo = this.createFoo();
    // Effectue les tests
    List<FOO> result = null;
    result = this.getDao().findListByEmbeddedDate(foo.getDate());
    assertEquals("Wrong result size", 0, result.size());
    // Ajoute une entité
    this.add(foo);
    // Récupère l'entité ajoutée
    FOO expected1 = this.getById(foo.getId());
    // Effectue les tests
    result = this.getDao().findListByEmbeddedDate(foo.getDate());
    assertEquals("Wrong result size", 1, result.size());
    this.checkIdentical(expected1, result.get(0));

    result = this.getDao().findListByEmbeddedDate(expected1.getDate().addTime(1));
    assertEquals("Wrong result size", 0, result.size());

    // Ajoute une entité
    foo = this.createFoo();
    this.add(foo);
    // Récupère l'entité ajoutée
    FOO expected2 = this.getById(foo.getId());

    // Effectue les tests
    result = this.getDao().findListByEmbeddedDate(expected1.getDate());
    assertEquals("Wrong result size", 2, result.size());
    this.checkIdentical(expected1, result.get(0));
    this.checkIdentical(expected2, result.get(1));

    result = this.getDao().findListByEmbeddedDate(expected1.getDate().addTime(1));
    assertEquals("Wrong result size", 1, result.size());
    this.checkIdentical(expected2, result.get(0));
  }
  /**
   * Test of add(FooAbstract), of class FooAbstractDaoSpring.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testAdd_FooAbstract()throws Bid4WinException
  {
    FOO foo = this.createFoo();
    // Ajoute une entité
    this.add(foo);
    // Récupère l'entité ajoutée
    FOO result = this.getById(foo.getId());
    // Effectue les tests
    assertNotNull(result);
    assertTrue("Wrong ID", 0 < result.getId());
    assertEquals("Wrong version", 0, result.getVersion());
    this.checkIdentical(foo, result);

    try
    {
      foo = this.createFoo();
      foo.setValue("RollBack test");
      this.getDao().addWithRollback(foo);
      fail("Should fail");
    }
    catch(AssertionError ex)
    {
      throw ex;
    }
    catch(Throwable th)
    {
      th.printStackTrace();
      result = this.getDao().findOneByValue(foo.getValue());
      assertNull(result);
    }
  }

  /**
   * Test of update(FooAbstract), of class FooAbstractDaoSpring.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testUpdate_FooAbstract()throws Bid4WinException
  {
    FOO foo = this.createFoo();
    // Ajoute une entité
    this.add(foo);
    // Modifie l'entité ajoutée
    this.updateFoo(foo);
    FOO update = this.update(foo);
    // Récupère l'entité modifiée
    FOO result = this.getById(foo.getId());
    // Effectue les tests
    assertNotNull(result);
    assertEquals("Wrong ID", foo.getId(), result.getId());
    assertEquals("Wrong version", foo.getVersion() + 1, result.getVersion());
    this.checkSame(foo, result);
    this.checkIdentical(update, result);
    // Récupère deux fois l'entité
    FOO foo2 = this.getById(foo.getId());
    foo2.setValue("VALUE3");
    FOO foo3 = this.getById(foo.getId());
    foo3.setValue("VALUE3");
    // Modifie l'entité
    this.update(foo2);
    try
    {
      // Modifie l'entité avec une mauvaise version
      this.update(foo3);
      fail("Update of a modified version should have failed");
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
  }

  /**
   * Test of removeById(int), of class FooAbstractDaoSpring.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testRemoveById_int()throws Bid4WinException
  {
    FOO foo = this.createFoo();
    // Ajoute une entité
    this.add(foo);
    // Récupère l'entité ajoutée
    FOO result = this.getById(foo.getId());
    // Supprime l'entité
    this.removeById(result.getId());
    // Effectue les tests
    try
    {
      this.getById(result.getId());
      fail("Entity should have been removed");
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
    try
    {
      // Supprime l'entité
      this.removeById(result.getId());
      fail("Unexisting entity could not be removed");
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
  }

  /**
   * Test of performance of simple classe, of class FooAbstractDaoSpring.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void test_PerformanceSimpleClassTest() throws Bid4WinException
  {
    Bid4WinList<FOO> stubList = new Bid4WinList<FOO>(10);
    Bid4WinDate begin = new Bid4WinDate();
    for(int i = 0 ; i < 10 ; i ++)
    {
      stubList.add(this.add(this.createFoo()));
    }
    Bid4WinDate intermediate = new Bid4WinDate();
    for(int i = 0 ; i < 1000 ; i ++)
    {
      this.getById(stubList.get(i % 10).getId());
    }
    Bid4WinDate end = new Bid4WinDate();
    System.out.println("CREATE TIME=" + (intermediate.getTime() - begin.getTime()) +
                       " SELECT TIME=" + (end.getTime() - intermediate.getTime()));
  }

  /**
   * Permet de créer l'entité à utiliser pour les tests
   * @return L'entité à utiliser pour les tests
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  protected FOO createFoo()
  {
    return (FOO)new Foo("VALUE", new Bid4WinDate());
  }
  /**
   * Permet de modifier l'entité à utiliser pour les tests
   * @param foo Entité à modifier
   * @throws ModelArgumentException TODO A COMMENTER
   */
  protected void updateFoo(FOO foo) throws ModelArgumentException
  {
    foo.setValue("VALUE2");
    foo.defineDate(new Bid4WinDate());
  }
}
