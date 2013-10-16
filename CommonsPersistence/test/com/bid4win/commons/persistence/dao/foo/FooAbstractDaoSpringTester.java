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
import com.bid4win.commons.persistence.dao.Bid4WinDaoTester;
import com.bid4win.commons.persistence.entity.EntityGenerator;
import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;
import com.bid4win.commons.persistence.entity.account.security.Role;
import com.bid4win.commons.persistence.entity.foo.FooAbstract;
import com.bid4win.commons.persistence.entity.foo.FooAbstract_Fields;
import com.bid4win.commons.persistence.entity.foo.not_cached.Foo;
import com.bid4win.commons.persistence.request.Bid4WinPagination;
import com.bid4win.commons.persistence.request.Bid4WinRange;
import com.bid4win.commons.persistence.request.Bid4WinResult;
import com.bid4win.commons.persistence.request.data.Bid4WinData;
import com.bid4win.commons.persistence.request.data.Bid4WinDataNullable;
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
       extends Bid4WinDaoTester<FOO, Long, AccountAbstractStub, EntityGenerator<AccountAbstractStub>>
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
    Bid4WinList<FOO> result = this.getDao().findListByValue(foo.getValue(), null);
    assertEquals("Wrong result size", 0, result.size());
    // Ajoute une entité
    this.add(foo);
    // Récupère l'entité ajoutée
    FOO expected1 = this.getById(foo.getId());
    // Effectue les tests
    result = this.getDao().findListByValue(foo.getValue(), null);
    assertEquals("Wrong result size", 1, result.size());
    this.checkIdentical(expected1, result.get(0));
    result = this.getDao().findListByValue(foo.getValue() + "1", null);
    assertEquals("Wrong result size", 0, result.size());
    // Ajoute une entité
    foo = this.createFoo();
    this.add(foo);
    // Récupère l'entité ajoutée
    FOO expected2 = this.getById(foo.getId());
    // Effectue les tests
    result = this.getDao().findListByValue(foo.getValue(), null);
    assertEquals("Wrong result size", 2, result.size());
    this.checkIdentical(expected1, result.get(0));
    this.checkIdentical(expected2, result.get(1));
  }

  /**
   * Test of findList(Bid4WinCriteria, Bid4WinPagination), of class Bid4WinDao_.
   * @throws Bid4WinException Issue not expected during this test
   */
  @SuppressWarnings("unchecked")
  @Test
  public void testFindList_Bid4WinCriteria_Bid4WinPagination() throws Bid4WinException
  {
    FOO foo = this.createFoo();
    Bid4WinPagination<FOO> pagination1 = new Bid4WinPagination<FOO>();
    Bid4WinPagination<FOO> pagination2 = new Bid4WinPagination<FOO>(new Bid4WinRange(2));
    Bid4WinPagination<FOO> pagination3 = new Bid4WinPagination<FOO>(new Bid4WinRange(1, 2));
    Bid4WinPagination<FOO> pagination4 = new Bid4WinPagination<FOO>(new Bid4WinRange(1, 0));
    Bid4WinData<FOO, String> data = new Bid4WinData<FOO, String>(FooAbstract_Fields.VALUE,
                                                                 foo.getValue());

    Bid4WinResult<FOO> result = this.getDao().findList(data, null);
    assertEquals("Wrong result size", 0, result.size());
    assertEquals("Wrong result totalCount", 0, result.getTotalCount());
    result = this.getDao().findList(data, pagination1);
    assertEquals("Wrong result size", 0, result.size());
    assertEquals("Wrong result totalCount", 0, result.getTotalCount());
    result = this.getDao().findList(data, pagination2);
    assertEquals("Wrong result size", 0, result.size());
    assertEquals("Wrong result totalCount", 0, result.getTotalCount());
    result = this.getDao().findList(data, pagination3);
    assertEquals("Wrong result size", 0, result.size());
    assertEquals("Wrong result totalCount", 0, result.getTotalCount());
    result = this.getDao().findList(data, pagination4);
    assertEquals("Wrong result size", 0, result.size());
    assertEquals("Wrong result totalCount", 0, result.getTotalCount());

    this.add(foo);
    FOO expected1 = this.getById(foo.getId());

    result = this.getDao().findList(data, null);
    assertEquals("Wrong result size", 1, result.size());
    this.checkIdentical(expected1, result.get(0));
    assertEquals("Wrong result totalCount", 1, result.getTotalCount());
    result = this.getDao().findList(data, pagination1);
    assertEquals("Wrong result size", 1, result.size());
    this.checkIdentical(expected1, result.get(0));
    assertEquals("Wrong result totalCount", 1, result.getTotalCount());
    result = this.getDao().findList(data, pagination2);
    assertEquals("Wrong result size", 1, result.size());
    this.checkIdentical(expected1, result.get(0));
    assertEquals("Wrong result totalCount", 1, result.getTotalCount());
    result = this.getDao().findList(data, pagination3);
    assertEquals("Wrong result size", 0, result.size());
    assertEquals("Wrong result totalCount", 1, result.getTotalCount());
    result = this.getDao().findList(data, pagination4);
    assertEquals("Wrong result size", 0, result.size());
    assertEquals("Wrong result totalCount", 1, result.getTotalCount());

    foo = this.createFoo();
    this.add(foo);
    FOO expected2 = this.getById(foo.getId());

    result = this.getDao().findList(data, null);
    assertEquals("Wrong result size", 2, result.size());
    this.checkIdentical(expected1, result.get(0));
    this.checkIdentical(expected2, result.get(1));
    assertEquals("Wrong result totalCount", 2, result.getTotalCount());
    result = this.getDao().findList(data, pagination1);
    assertEquals("Wrong result size", 2, result.size());
    this.checkIdentical(expected1, result.get(0));
    this.checkIdentical(expected2, result.get(1));
    assertEquals("Wrong result totalCount", 2, result.getTotalCount());
    result = this.getDao().findList(data, pagination2);
    assertEquals("Wrong result size", 2, result.size());
    this.checkIdentical(expected1, result.get(0));
    this.checkIdentical(expected2, result.get(1));
    assertEquals("Wrong result totalCount", 2, result.getTotalCount());
    result = this.getDao().findList(data, pagination3);
    assertEquals("Wrong result size", 1, result.size());
    this.checkIdentical(expected2, result.get(0));
    assertEquals("Wrong result totalCount", 2, result.getTotalCount());
    result = this.getDao().findList(data, pagination4);
    assertEquals("Wrong result size", 1, result.size());
    this.checkIdentical(expected2, result.get(0));
    assertEquals("Wrong result totalCount", 2, result.getTotalCount());

    foo = this.createFoo();
    this.add(foo);
    FOO expected3 = this.getById(foo.getId());

    result = this.getDao().findList(data, null);
    assertEquals("Wrong result size", 3, result.size());
    this.checkIdentical(expected1, result.get(0));
    this.checkIdentical(expected2, result.get(1));
    this.checkIdentical(expected3, result.get(2));
    assertEquals("Wrong result totalCount", 3, result.getTotalCount());
    result = this.getDao().findList(data, pagination1);
    assertEquals("Wrong result size", 3, result.size());
    this.checkIdentical(expected1, result.get(0));
    this.checkIdentical(expected2, result.get(1));
    this.checkIdentical(expected3, result.get(2));
    assertEquals("Wrong result totalCount", 3, result.getTotalCount());
    result = this.getDao().findList(data, pagination2);
    assertEquals("Wrong result size", 2, result.size());
    this.checkIdentical(expected1, result.get(0));
    this.checkIdentical(expected2, result.get(1));
    assertEquals("Wrong result totalCount", 3, result.getTotalCount());
    result = this.getDao().findList(data, pagination3);
    assertEquals("Wrong result size", 2, result.size());
    this.checkIdentical(expected2, result.get(0));
    this.checkIdentical(expected3, result.get(1));
    assertEquals("Wrong result totalCount", 3, result.getTotalCount());
    result = this.getDao().findList(data, pagination4);
    assertEquals("Wrong result size", 2, result.size());
    this.checkIdentical(expected2, result.get(0));
    this.checkIdentical(expected3, result.get(1));
    assertEquals("Wrong result totalCount", 3, result.getTotalCount());

    foo = this.createFoo();
    this.add(foo);
    FOO expected4 = this.getById(foo.getId());

    result = this.getDao().findList(data, null);
    assertEquals("Wrong result size", 4, result.size());
    this.checkIdentical(expected1, result.get(0));
    this.checkIdentical(expected2, result.get(1));
    this.checkIdentical(expected3, result.get(2));
    this.checkIdentical(expected4, result.get(3));
    assertEquals("Wrong result totalCount", 4, result.getTotalCount());
    result = this.getDao().findList(data, pagination1);
    assertEquals("Wrong result size", 4, result.size());
    this.checkIdentical(expected1, result.get(0));
    this.checkIdentical(expected2, result.get(1));
    this.checkIdentical(expected3, result.get(2));
    this.checkIdentical(expected4, result.get(3));
    assertEquals("Wrong result totalCount", 4, result.getTotalCount());
    result = this.getDao().findList(data, pagination2);
    assertEquals("Wrong result size", 2, result.size());
    this.checkIdentical(expected1, result.get(0));
    this.checkIdentical(expected2, result.get(1));
    assertEquals("Wrong result totalCount", 4, result.getTotalCount());
    result = this.getDao().findList(data, pagination3);
    assertEquals("Wrong result size", 2, result.size());
    this.checkIdentical(expected2, result.get(0));
    this.checkIdentical(expected3, result.get(1));
    assertEquals("Wrong result totalCount", 4, result.getTotalCount());
    result = this.getDao().findList(data, pagination4);
    assertEquals("Wrong result size", 3, result.size());
    this.checkIdentical(expected2, result.get(0));
    this.checkIdentical(expected3, result.get(1));
    this.checkIdentical(expected4, result.get(2));
    assertEquals("Wrong result totalCount", 4, result.getTotalCount());

    expected3.defineRole(Role.ADMIN);
    this.update(expected3);
    Bid4WinDataNullable<FOO, Role> data2 = new Bid4WinDataNullable<FOO, Role>(FooAbstract_Fields.ROLE);

    result = this.getDao().findList(data2, null);
    assertEquals("Wrong result size", 3, result.size());
    this.checkIdentical(expected1, result.get(0));
    this.checkIdentical(expected2, result.get(1));
    this.checkIdentical(expected4, result.get(2));
    assertEquals("Wrong result totalCount", 3, result.getTotalCount());
    result = this.getDao().findList(data2, pagination1);
    assertEquals("Wrong result size", 3, result.size());
    this.checkIdentical(expected1, result.get(0));
    this.checkIdentical(expected2, result.get(1));
    this.checkIdentical(expected4, result.get(2));
    assertEquals("Wrong result totalCount", 3, result.getTotalCount());
    result = this.getDao().findList(data2, pagination2);
    assertEquals("Wrong result size", 2, result.size());
    this.checkIdentical(expected1, result.get(0));
    this.checkIdentical(expected2, result.get(1));
    assertEquals("Wrong result totalCount", 3, result.getTotalCount());
    result = this.getDao().findList(data2, pagination3);
    assertEquals("Wrong result size", 2, result.size());
    this.checkIdentical(expected2, result.get(0));
    this.checkIdentical(expected4, result.get(1));
    assertEquals("Wrong result totalCount", 3, result.getTotalCount());
    result = this.getDao().findList(data2, pagination4);
    assertEquals("Wrong result size", 2, result.size());
    this.checkIdentical(expected2, result.get(0));
    this.checkIdentical(expected4, result.get(1));
    assertEquals("Wrong result totalCount", 3, result.getTotalCount());
  }
  /**
   * Test of findListByRole(Role), of class FooAbstractDaoSpring.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testFindListByRole_Role() throws Bid4WinException
  {
    FOO foo = this.createFoo();
    // Effectue les tests
    Bid4WinList<FOO> result = this.getDao().findListByRole(foo.getRole());
    assertEquals("Wrong result size", 0, result.size());
    // Ajoute une entité
    this.add(foo);
    // Récupère l'entité ajoutée
    FOO expected1 = this.getById(foo.getId());

    // Effectue les tests
    result = this.getDao().findListByRole(expected1.getRole());
    assertEquals("Wrong result size", 1, result.size());
    this.checkIdentical(expected1, result.get(0));
    result = this.getDao().findListByRole(Role.ADMIN);
    assertEquals("Wrong result size", 0, result.size());

    // Ajoute une entité
    foo = this.createFoo();
    this.add(foo);
    // Récupère l'entité ajoutée
    FOO expected2 = this.getById(foo.getId());

    // Effectue les tests
    result = this.getDao().findListByRole(expected1.getRole());
    assertEquals("Wrong result size", 2, result.size());
    this.checkIdentical(expected1, result.get(0));
    this.checkIdentical(expected2, result.get(1));
    result = this.getDao().findListByRole(Role.ADMIN);
    assertEquals("Wrong result size", 0, result.size());

    // Modifie un rôle
    expected1.defineRole(Role.ADMIN);
    this.update(expected1);
    // Récupère l'entité modifiée
    expected1 = this.getById(expected1.getId());

    // Effectue les tests
    result = this.getDao().findListByRole(expected1.getRole());
    assertEquals("Wrong result size", 1, result.size());
    this.checkIdentical(expected1, result.get(0));
    result = this.getDao().findListByRole(expected2.getRole());
    assertEquals("Wrong result size", 1, result.size());
    this.checkIdentical(expected2, result.get(0));
    result = this.getDao().findListByRole(Role.BASIC);
    assertEquals("Wrong result size", 0, result.size());
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
    List<FOO> result1 = this.getDao().findListByUpdateDate(foo.getUpdateDate());
    List<FOO> result2 = this.getDao().findListByDate(foo.getDate());
    assertEquals("Wrong result size", 0, result1.size());
    assertEquals("Wrong result size", 0, result2.size());

    // Ajoute une entité
    this.add(foo);
    // Récupère l'entité ajoutée
    FOO expected1 = this.getById(foo.getId());

    // Effectue les tests
    result1 = this.getDao().findListByUpdateDate(expected1.getUpdateDate());
    result2 = this.getDao().findListByDate(expected1.getDate());
    assertEquals("Wrong result size", 0, result1.size());
    assertEquals("Wrong result size", 1, result2.size());
    this.checkIdentical(expected1, result2.get(0));

    foo = this.createFoo();
    // Ajoute une entité
    this.add(foo);
    // Récupère l'entité ajoutée
    FOO expected2 = this.getById(foo.getId());

    // Effectue les tests
    result1 = this.getDao().findListByUpdateDate(expected1.getUpdateDate());
    result2 = this.getDao().findListByDate(expected1.getDate());
    assertEquals("Wrong result size", 0, result1.size());
    assertEquals("Wrong result size", 2, result2.size());
    this.checkIdentical(expected1, result2.get(0));
    this.checkIdentical(expected2, result2.get(1));

    result1 = this.getDao().findListByUpdateDate(expected2.getUpdateDate());
    result2 = this.getDao().findListByDate(expected2.getDate());
    assertEquals("Wrong result size", 0, result1.size());
    assertEquals("Wrong result size", 1, result2.size());
    this.checkIdentical(expected2, result2.get(0));
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
      foo.defineValue("RollBack test");
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
    foo2.defineValue("VALUE3");
    FOO foo3 = this.getById(foo.getId());
    foo3.defineValue("VALUE3");
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
   * Test of removeById(Long), of class FooAbstractDaoSpring.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testRemoveById_Long()throws Bid4WinException
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
   */
  protected void updateFoo(FOO foo)
  {
    foo.defineValue("VALUE2");
    foo.defineDate(new Bid4WinDate());
  }
}
