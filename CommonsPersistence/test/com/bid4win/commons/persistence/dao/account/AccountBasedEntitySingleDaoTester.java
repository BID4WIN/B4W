package com.bid4win.commons.persistence.dao.account;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.EntityGenerator;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntitySingle;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntity_Relations;

/**
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <ENTITY> TODO A COMMENTER<BR>
 * @param <ID> TODO A COMMENTER<BR>
 * @param <ACCOUNT> Compte utilisateur lié à l'entité sujet du test<BR>
 * @param <GENERATOR> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class AccountBasedEntitySingleDaoTester<ENTITY extends AccountBasedEntitySingle<ENTITY, ID, ACCOUNT>,
                                                        ID,
                                                        ACCOUNT extends AccountAbstract<ACCOUNT>,
                                                        GENERATOR extends EntityGenerator<ACCOUNT>>
       extends AccountBasedEntityDaoTester<ENTITY, ID, ACCOUNT, GENERATOR>
{
  /**
   * Permet de préciser le DAO à tester
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDaoTester#getDao()
   */
  @Override
  protected abstract IAccountBasedEntitySingleDaoStub<ENTITY, ID, ACCOUNT> getDao();

  /**
   * Test of getOneByAccount(ACCOUNT), of class AccountBasedEntitySingleDao.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testGetOneByAccount_ACCOUNT() throws Bid4WinException
  {
    this.testGetOneForAccount(false);
  }
  /**
   * Test of getOneByAccountId(String), of class AccountBasedEntitySingleDao.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testGetOneByAccountId_String() throws Bid4WinException
  {
    this.testGetOneForAccount(true);
  }

  /**
   *
   * TODO A COMMENTER
   * @param byId TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  protected void testGetOneForAccount(boolean byId) throws Bid4WinException
  {
    ACCOUNT account1 = this.getAccount(0);
    ACCOUNT account2 = this.getAccount(1);
    try
    {
      try
      {
        this.getOne(account1, byId);
        fail("Should fail with no entity");
      }
      catch(NotFoundEntityException ex)
      {
        System.out.println(ex.getMessage());
      }
      try
      {
        this.getOne(account2, byId);
        fail("Should fail with no entity");
      }
      catch(NotFoundEntityException ex)
      {
        System.out.println(ex.getMessage());
      }

      ENTITY stub1 = this.create(account1);
      this.add(stub1);
      ENTITY result = this.getOne(account1, byId);
      assertNotNull("Should not be null", result);
      assertTrue("Wrong result", stub1.identical(result, new Bid4WinList<Bid4WinRelationNode>()));
      assertTrue("Wrong result", stub1.same(
          result, new Bid4WinList<Bid4WinRelationNode>(AccountBasedEntity_Relations.NODE_ACCOUNT)));
      try
      {
        this.getOne(account2, byId);
        fail("Should fail with no entity");
      }
      catch(NotFoundEntityException ex)
      {
        System.out.println(ex.getMessage());
      }

      ENTITY stub2 = this.create(account2);
      this.add(stub2);
      result = this.getOne(account1, byId);
      assertNotNull("Should not be null", result);
      assertTrue("Wrong result", stub1.identical(result, new Bid4WinList<Bid4WinRelationNode>()));
      assertTrue("Wrong result", stub1.same(
          result, new Bid4WinList<Bid4WinRelationNode>(AccountBasedEntity_Relations.NODE_ACCOUNT)));
      result = this.getOne(account2, byId);
      assertNotNull("Should not be null", result);
      assertTrue("Wrong result", stub2.identical(result, new Bid4WinList<Bid4WinRelationNode>()));
      assertTrue("Wrong result", stub2.same(
          result, new Bid4WinList<Bid4WinRelationNode>(AccountBasedEntity_Relations.NODE_ACCOUNT)));
    }
    catch(Bid4WinException ex)
    {
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
  protected ENTITY getOne(ACCOUNT account, boolean byId) throws PersistenceException
  {
    if(byId)
    {
      return this.getOneByAccountId(account.getId());
    }
    return this.getOneByAccount(account);
  }

  /**
   * Test of add(ENTITY), of class AccountBasedEntityDao.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Override
  @Test
  public void testAdd_ENTITY() throws Bid4WinException
  {
    super.testAdd_ENTITY();
    ACCOUNT account1 = this.getAccount(0);
    ENTITY existing = this.findOneByAccount(account1);
    assertNotNull("One entity should already exist", existing);
    try
    {
      ENTITY stub = this.create(account1);
      this.getDao().add(stub);
      fail("Should fail with already referenced account");
    }
    catch(AssertionError ex)
    {
      throw ex;
    }
    catch(Throwable ex)
    {
      ENTITY result = this.findOneByAccount(account1);
      assertTrue("Wrong result", existing.identical(result));
    }
  }


  /**
   *
   * TODO A COMMENTER
   * @param account TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  protected ENTITY getOneByAccount(ACCOUNT account) throws PersistenceException
  {
    return this.getDao().getOneByAccount(account);
  }
  /**
   *
   * TODO A COMMENTER
   * @param accountId TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  protected ENTITY getOneByAccountId(String accountId) throws PersistenceException
  {
    return this.getDao().getOneByAccountId(accountId);
  }
}
