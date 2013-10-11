package com.bid4win.commons.persistence.dao.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.EntityGenerator;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultiple;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntity_Relations;
import com.bid4win.commons.persistence.entity.comparator.Bid4WinEntitySetComparator;

/**
 * Classe de test de base de tous les DAO utilisant des entités basées à plusieurs
 * sur des comptes utilisateur<BR>
 * <BR>
 * @param <ENTITY> Entité sujet du test<BR>
 * @param <ID> Type de l'identifiant de l'entité sujet du test<BR>
 * @param <ACCOUNT> Compte utilisateur lié à l'entité sujet du test<BR>
 * @param <GENERATOR> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class AccountBasedEntityMultipleDaoTester<ENTITY extends AccountBasedEntityMultiple<ENTITY, ID, ACCOUNT>,
                                                          ID,
                                                          ACCOUNT extends AccountAbstract<ACCOUNT>,
                                                          GENERATOR extends EntityGenerator<ACCOUNT>>
       extends AccountBasedEntityDaoTester<ENTITY, ID, ACCOUNT, GENERATOR>
{
  /**
   * Permet de préciser le DAO à tester
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityDaoTester#getDao()
   */
  @Override
  protected abstract IAccountBasedEntityMultipleDaoStub<ENTITY, ID, ACCOUNT> getDao();

  /**
   * Test of findListByAccount(ACCOUNT), of class AccountBasedEntityMultipleDao.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testFindListByAccount_ACCOUNT() throws Bid4WinException
  {
    this.testFindListForAccount(false);
  }
  /**
   * Test of findListByAccountId(String), of class AccountBasedEntityMultipleDao.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testFindListByAccountId_String() throws Bid4WinException
  {
    this.testFindListForAccount(true);
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
    try
    {
      ENTITY stub = this.create(account1);
      this.getDao().add(stub);
      ENTITY result = this.getById(stub.getId());
      assertTrue("Wrong result", stub.identical(result, new Bid4WinList<Bid4WinRelationNode>()));
      assertTrue("Wrong result", stub.same(
          result, new Bid4WinList<Bid4WinRelationNode>(AccountBasedEntity_Relations.NODE_ACCOUNT)));

      ACCOUNT account = this.getAccount(0);
      assertEquals("Wrong account version",
          account1.getVersion() + (this.isAccountUpdatedAfterAdd() ? 1 : 0),
          account.getVersion());
      assertEquals("Wrong entity nb", 2, this.findListByAccount(account1).size());
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
   * @param byId TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  protected void testFindListForAccount(boolean byId) throws Bid4WinException
  {
    ACCOUNT account1 = this.getAccount(0);
    ACCOUNT account2 = this.getAccount(1);
    try
    {
      Bid4WinSet<ENTITY> stubSet1 = new Bid4WinSet<ENTITY>();
      Bid4WinSet<ENTITY> stubSet2 = new Bid4WinSet<ENTITY>();
      Bid4WinSet<ENTITY> result = this.findList(account1, byId);
      assertTrue("Should be empty", result.isEmpty());
      result = this.findList(account2, byId);
      assertTrue("Should be empty", result.isEmpty());

      this.testFindOneForAccount(byId);
      stubSet1.add(this.findOne(account1, byId));
      stubSet2.add(this.findOne(account2, byId));
      result = this.findList(account1, byId);
      assertTrue("Wrong result",
                 Bid4WinEntitySetComparator.getInstanceEntitySet().identical(
                     stubSet1, result, new Bid4WinList<Bid4WinRelationNode>()));
      assertTrue("Wrong result",
                 Bid4WinEntitySetComparator.getInstanceEntitySet().same(
                     stubSet1, result,
                     new Bid4WinList<Bid4WinRelationNode>(AccountBasedEntity_Relations.NODE_ACCOUNT)));
      result = this.findList(account2, byId);
      assertTrue("Wrong result",
                 Bid4WinEntitySetComparator.getInstanceEntitySet().identical(
                     stubSet2, result, new Bid4WinList<Bid4WinRelationNode>()));
      assertTrue("Wrong result",
                 Bid4WinEntitySetComparator.getInstanceEntitySet().same(
                     stubSet2, result,
                     new Bid4WinList<Bid4WinRelationNode>(AccountBasedEntity_Relations.NODE_ACCOUNT)));

      account1 = this.getAccount(0);
      account2 = this.getAccount(1);
      ENTITY stub3 = this.create(account1);
      this.add(stub3);
      stubSet1.add(stub3);
      result = this.findList(account1, byId);
      assertTrue("Wrong result",
                 Bid4WinEntitySetComparator.getInstanceEntitySet().identical(
                     stubSet1, result, new Bid4WinList<Bid4WinRelationNode>()));
      if(!this.isAccountUpdatedAfterAdd())
      {
        assertTrue("Wrong result",
                   Bid4WinEntitySetComparator.getInstanceEntitySet().same(
                       stubSet1, result,
                       new Bid4WinList<Bid4WinRelationNode>(AccountBasedEntity_Relations.NODE_ACCOUNT)));
      }
      result = this.findList(account2, byId);
      assertTrue("Wrong result",
                 Bid4WinEntitySetComparator.getInstanceEntitySet().identical(
                     stubSet2, result, new Bid4WinList<Bid4WinRelationNode>()));
      assertTrue("Wrong result",
                 Bid4WinEntitySetComparator.getInstanceEntitySet().same(
                     stubSet2, result,
                     new Bid4WinList<Bid4WinRelationNode>(AccountBasedEntity_Relations.NODE_ACCOUNT)));

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
        assertEquals("Wrong account version", account2.getVersion(), account.getVersion());
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
  protected Bid4WinSet<ENTITY> findList(ACCOUNT account, boolean byId) throws PersistenceException
  {
    if(byId)
    {
      return this.findListByAccountId(account.getId());
    }
    return this.findListByAccount(account);
  }
  /**
   *
   * TODO A COMMENTER
   * @param account TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  protected Bid4WinSet<ENTITY> findListByAccount(ACCOUNT account) throws PersistenceException
  {
    return new Bid4WinSet<ENTITY>(this.getDao().findListByAccount(account));
  }
  /**
   *
   * TODO A COMMENTER
   * @param accountId TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  protected Bid4WinSet<ENTITY> findListByAccountId(String accountId) throws PersistenceException
  {
    return new Bid4WinSet<ENTITY>(this.getDao().findListByAccountId(accountId));
  }
}
