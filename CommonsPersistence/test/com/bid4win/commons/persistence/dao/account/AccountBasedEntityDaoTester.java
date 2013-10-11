package com.bid4win.commons.persistence.dao.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.dao.Bid4WinDaoTester;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.EntityGenerator;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntity;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntity_Relations;

/**
 * Classe de test de base de tous les DAO utilisant des entités basées sur des
 * comptes utilisateur<BR>
 * <BR>
 * @param <ENTITY> Entité sujet du test<BR>
 * @param <ID> Type de l'identifiant de l'entité sujet du test<BR>
 * @param <ACCOUNT> Compte utilisateur lié à l'entité sujet du test<BR>
 * @param <GENERATOR> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class AccountBasedEntityDaoTester<ENTITY extends AccountBasedEntity<ENTITY, ID, ACCOUNT>,
                                                  ID,
                                                  ACCOUNT extends AccountAbstract<ACCOUNT>,
                                                  GENERATOR extends EntityGenerator<ACCOUNT>>
       extends Bid4WinDaoTester<ENTITY, ID, ACCOUNT, GENERATOR>
{
  /**
   * Permet de préciser le DAO à tester
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDaoTester#getDao()
   */
  @Override
  protected abstract IAccountBasedEntityDaoStub<ENTITY, ID, ACCOUNT> getDao();

  /**
   * Test of findOneByAccount(ACCOUNT), of class AccountBasedEntityDao.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testFindOneByAccount_ACCOUNT() throws Bid4WinException
  {
    this.testFindOneForAccount(false);
  }
  /**
   * Test of findOneByAccountId(String), of class AccountBasedEntityDao.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testFindOneByAccountId_String() throws Bid4WinException
  {
    this.testFindOneForAccount(true);
  }
  /**
   *
   * TODO A COMMENTER
   * @param byId TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  protected void testFindOneForAccount(boolean byId) throws Bid4WinException
  {
    ACCOUNT account1 = this.getAccount(0);
    ACCOUNT account2 = this.getAccount(1);
    try
    {
      ENTITY result = this.findOne(account1, byId);
      assertNull("Should be null", result);
      result = this.findOne(account2, byId);
      assertNull("Should be null", result);

      ENTITY stub1 = this.create(account1);
      this.add(stub1);
      result = this.findOne(account1, byId);
      assertNotNull("Should not be null", result);
      assertTrue("Wrong result", stub1.identical(result, new Bid4WinList<Bid4WinRelationNode>()));
      assertTrue("Wrong result", stub1.same(
          result, new Bid4WinList<Bid4WinRelationNode>(AccountBasedEntity_Relations.NODE_ACCOUNT)));
      result = this.findOne(account2, byId);
      assertNull("Should be null", result);

      ENTITY stub2 = this.create(account2);
      this.add(stub2);
      result = this.findOne(account1, byId);
      assertNotNull("Should not be null", result);
      assertTrue("Wrong result", stub1.identical(result, new Bid4WinList<Bid4WinRelationNode>()));
      assertTrue("Wrong result", stub1.same(
          result, new Bid4WinList<Bid4WinRelationNode>(AccountBasedEntity_Relations.NODE_ACCOUNT)));
      result = this.findOne(account2, byId);
      assertNotNull("Should not be null", result);
      assertTrue("Wrong result", stub2.identical(result, new Bid4WinList<Bid4WinRelationNode>()));
      assertTrue("Wrong result", stub2.same(
          result, new Bid4WinList<Bid4WinRelationNode>(AccountBasedEntity_Relations.NODE_ACCOUNT)));

      if(!this.isAccountUpdatedAfterAdd())
      {
        ACCOUNT account = this.getAccount(0);
        assertTrue("Wrong account", account.identical(account1));
        account = this.getAccount(1);
        assertTrue("Wrong account", account.identical(account2));
      }
      else
      {
        ACCOUNT account = this.getAccount(0);
        assertTrue("Wrong account", account.same(account1));
        assertEquals("Wrong account version", account1.getVersion() + 1, account.getVersion());
        account = this.getAccount(1);
        assertTrue("Wrong account", account.same(account2));
        assertEquals("Wrong account version", account2.getVersion() + 1, account.getVersion());
      }
    }
    catch(Bid4WinException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
  }
  /**
   *
   * TODO A COMMENTER
   * @param account TODO A COMMENTER
   * @param byId TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  protected ENTITY findOne(ACCOUNT account, boolean byId) throws PersistenceException
  {
    if(byId)
    {
      return this.findOneByAccountId(account.getId());
    }
    return this.findOneByAccount(account);
  }

  /**
   * Test of add(ENTITY), of class AccountBasedEntityDao.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testAdd_ENTITY() throws Bid4WinException
  {
    ACCOUNT account1 = this.getAccount(0);
    ACCOUNT account2 = this.getAccount(1);
    try
    {
      ENTITY stub1 = this.create(account1);
      this.add(stub1);
      ENTITY result = this.getById(stub1.getId());
      assertTrue("Wrong result", stub1.identical(result, new Bid4WinList<Bid4WinRelationNode>()));
      assertTrue("Wrong result", stub1.same(
          result, new Bid4WinList<Bid4WinRelationNode>(AccountBasedEntity_Relations.NODE_ACCOUNT)));

      ENTITY stub2 = this.create(account2);
      this.add(stub2);
      result = this.getById(stub2.getId());
      assertTrue("Wrong result", stub2.identical(result, new Bid4WinList<Bid4WinRelationNode>()));
      assertTrue("Wrong result", stub2.same(
          result, new Bid4WinList<Bid4WinRelationNode>(AccountBasedEntity_Relations.NODE_ACCOUNT)));

      ACCOUNT account = this.getAccount(0);
      assertEquals("Wrong account version",
                   account1.getVersion() + (this.isAccountUpdatedAfterAdd() ? 1 : 0),
                   account.getVersion());
      account = this.getAccount(1);
      assertEquals("Wrong account version",
                   account2.getVersion() + (this.isAccountUpdatedAfterAdd() ? 1 : 0),
                   account.getVersion());
    }
    catch(Bid4WinException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public boolean isAccountUpdatedAfterAdd()
  {
    return false;
  }

  /**
   *
   * TODO A COMMENTER
   * @param account TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  protected ENTITY findOneByAccount(ACCOUNT account) throws PersistenceException
  {
    return this.getDao().findOneByAccount(account);
  }
  /**
   *
   * TODO A COMMENTER
   * @param accountId TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  protected ENTITY findOneByAccountId(String accountId) throws PersistenceException
  {
    return this.getDao().findOneByAccountId(accountId);
  }
  /**
   *
   * TODO A COMMENTER
   * @param account TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  protected abstract ENTITY create(ACCOUNT account) throws Bid4WinException;

  /**
   *
   * TODO A COMMENTER
   * @param index TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public ACCOUNT getAccount(int index) throws Bid4WinException
  {
    return this.getAccountInitializer().getEntity(index);
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected abstract AccountInitializer_<ACCOUNT, GENERATOR> getAccountInitializer();
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public int getSetupAccountNb()
  {
    return 2;
  }
  /**
   * Test setup method
   * @throws Exception {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDaoTester#setUp()
   */
  @Override
  @Before
  public void setUp() throws Exception
  {
    super.setUp();
    // Ajoute les comptes utilisateurs utilisés pour les tests
    this.getAccountInitializer().setUp(this.getSetupAccountNb());
  }
  /**
   * Test teardown method
   * @see com.bid4win.commons.persistence.dao.Bid4WinDaoTester#tearDown()
   */
  @Override
  @After
  public void tearDown() throws Exception
  {
    super.tearDown();
    // Supprime les comptes utilisateurs utilisés pour les tests
    this.getAccountInitializer().tearDown();
  }
}
