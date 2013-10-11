package com.bid4win.persistence.dao.account.credit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.account.credit.CreditBundle;
import com.bid4win.persistence.entity.account.credit.CreditBundleAbstract;
import com.bid4win.persistence.entity.account.credit.CreditBundleHistory;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
@TransactionConfiguration(transactionManager = "Bid4WinTransactionManager")
public class CreditBundleDaoTest extends CreditBundleAbstractDaoTester<CreditBundle>
{
  /** R�f�rence du DAO � tester */
  @Autowired
  @Qualifier("CreditBundleDaoStub")
  private CreditBundleDaoStub dao;

  /**
   * Test of add(ENTITY), of class CreditBundleDao.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Override
  @Test
  public void testAdd_ENTITY() throws Bid4WinException
  {
    super.testAdd_ENTITY();

    for(int i = 0 ; i < 2 ; i++)
    {
      Account account = this.getAccount(i);
      Bid4WinList<CreditBundle> list = this.getDao().findListByAccount(account);
      assertEquals("Wrong account version", list.size(), account.getVersion());
      int nb = 0;
      for(CreditBundle bundle : list)
      {
        nb += bundle.getCurrentNb();
      }
      assertEquals("Wrong bid right nb", nb, account.getCreditNb());
    }
    // Ajoute un lot de cr�dits sans compte utilisateur
    Account account1 = this.getAccount(2);
    CreditBundle bundle = this.create(account1);
    account1.useCredit(bundle.getCurrentNb());
    try
    {
      this.getDao().add(bundle);
      fail("Should fail with unlinked credit bundle");
    }
    catch(PersistenceException ex)
    {
      System.out.println(ex.getMessage());
      // V�rifie que le compte utilisateur est inchang�
      Account account2 = this.getAccount(2);
      assertEquals("Wrong account version", account1.getVersion(), account2.getVersion());
      assertNull("Bundle should not exist", this.getDao().findOneByAccount(account1));
    }
    // Ajoute un lot de cr�dits d�j� historis�
    account1 = this.getAccount(2);
    Bid4WinList<CreditBundle> list1 = this.getDao().findListByAccount(account1);
    bundle = this.create(account1);
    CreditBundleHistory history = bundle.historize();
    try
    {
      this.getDao().add(bundle);
      Bid4WinList<CreditBundle> list2 = this.getDao().findListByAccount(account1);
      assertEquals("Should be added", list1.size() + 1, list2.size());
      Account account2 = this.getAccount(2);
      assertEquals("Wrong account version", list2.size(), account2.getVersion());
      int nb = 0;
      for(CreditBundle creditBundle : list2)
      {
        nb += creditBundle.getCurrentNb();
      }
      assertEquals("Wrong bid right nb", nb, account2.getCreditNb());
      CreditBundleAbstract<?> result = this.getDao().getById(bundle.getId());
      assertTrue("Wrong result", bundle.identical(result, new Bid4WinList<Bid4WinRelationNode>()));
      assertTrue("Wrong result", bundle.same(result));
      result = this.getHistoryDao().findOneByAccount(account1);
      assertTrue("Wrong history", history.identical(result, new Bid4WinList<Bid4WinRelationNode>()));
      assertTrue("Wrong history", history.same(result));
    }
    catch(PersistenceException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
  }
  /**
   * Test of update(CreditBundle), of class CreditBundleDao.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testUpdate_CreditBundle() throws Bid4WinException
  {
   // Cr�ation de la situation de d�part
   Account account = this.getAccount(0);
   CreditBundle bundle1 = this.add(this.create(account));
   account = this.getAccount(0);
   CreditBundle bundle2 = this.add(this.create(account));
   account = this.getAccount(0);
   Account test = this.getAccount(0);

   for(int i = 0 ; i < bundle1.getInitialNb() - 1 ; i++)
   {
     // Utilise un cr�dit du lot
     bundle1 = test.useCredit();
     bundle1 = this.update(bundle1);
     // V�rifie le lot de cr�dits utilis�
     assertTrue("Wrong ID", 0 < bundle1.getId());
     assertEquals("Wrong version", 1 + i, bundle1.getVersion());
     assertTrue("Should still be linked", bundle1.isLinkedToAccount());
     assertEquals("Wrong current nb", 10 - 1 - i, bundle1.getCurrentNb());
     // R�cup�re le compte utilisateur du lot de droit � ench�rir ajout�
     test = this.getAccount(0);
     assertEquals("Wrong version", account.getVersion() + 1 + i, test.getVersion());
     assertEquals("Wrong credit nb", 20 - 1 - i, test.getCreditNb());
     assertEquals("Wrong used credit nb", 1 + i, test.getUsedCreditNb());
   }
   // Utilise le dernier cr�dit du lot
   bundle1 = test.useCredit();
   bundle1 = this.update(bundle1);
   // V�rifie le lot de cr�dits utilis�
   assertTrue("Wrong ID", 0 < bundle1.getId());
   assertEquals("Wrong version", 10, bundle1.getVersion());
   assertFalse("Should not be linked anymore", bundle1.isLinkedToAccount());
   // R�cup�re le compte utilisateur du lot de cr�dits utilis�
   test = this.getAccount(0);
   // V�rifie le compte utilisateur du lot de cr�dits utilis�
   assertEquals("Wrong version", account.getVersion() + 10, test.getVersion());
   assertEquals("Wrong bid rights nb", 10, test.getCreditNb());
   assertEquals("Wrong used credit nb", 10, test.getUsedCreditNb());
   assertEquals("Wrong credit bundle", bundle2.getId(), test.useCredit().getId());
  }
  /**
   * Test of historize(CreditBundle), of class CreditBundleDao.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testHistorize_CreditBundle() throws Bid4WinException
  {
    // Cr�ation de la situation de d�part
    Account account = this.getAccount(0);
    CreditBundle bundle = this.add(this.create(account));
    account = this.getAccount(0);
    bundle = this.update(account.useCredit(bundle.getCurrentNb() - 1).keySet().iterator().next());
    // V�rifie le lot de cr�dits utilis�
    assertEquals("Wrong version", 1, bundle.getVersion());
    assertTrue("Should still be linked", bundle.isLinkedToAccount());
    assertEquals("Wrong current nb", 1, bundle.getCurrentNb());
    assertFalse("Should not be historised", bundle.isHistorized());
    // R�cup�re le compte utilisateur du lot de cr�dits utilis�
    Account test = this.getAccount(0);
    assertEquals("Wrong version", account.getVersion() + 1, test.getVersion());
    assertEquals("Wrong credit nb", 1, test.getCreditNb());
    assertEquals("Wrong used credit nb", 9, test.getUsedCreditNb());

    this.getDao().historize(bundle);
    bundle = this.getById(bundle.getId());
    // V�rifie le lot de cr�dits utilis�
    assertEquals("Wrong version", 2, bundle.getVersion());
    assertTrue("Should still be linked", bundle.isLinkedToAccount());
    assertEquals("Wrong current nb", 1, bundle.getCurrentNb());
    assertTrue("Should be historised", bundle.isHistorized());
    // R�cup�re le compte utilisateur du lot de cr�dits utilis�
    test = this.getAccount(0);
    assertEquals("Wrong version", account.getVersion() + 1, test.getVersion());
    assertEquals("Wrong credit nb", 1, test.getCreditNb());
    assertEquals("Wrong used credit nb", 9, test.getUsedCreditNb());
    // R�cup�re l'historique du lot de cr�dits utilis�
    CreditBundleHistory history = this.getHistoryDao().findOneByAccount(test);
    assertEquals("Wrong version", 0, history.getVersion());
    assertTrue("Wrong credit history", history.isHistoryOf(bundle));
    assertTrue("Wrong credit history", history.identical(bundle.getHistory()));

    this.update(test.useCredit());
    bundle = this.getById(bundle.getId());
    // V�rifie le lot de cr�dits utilis�
    assertEquals("Wrong version", 3, bundle.getVersion());
    assertFalse("Should not be linked anymore", bundle.isLinkedToAccount());
    assertEquals("Wrong current nb", 0, bundle.getCurrentNb());
    assertTrue("Should be historised", bundle.isHistorized());
    // R�cup�re le compte utilisateur du lot de droit � ench�rir utilis�
    test = this.getAccount(0);
    assertEquals("Wrong version", account.getVersion() + 2, test.getVersion());
    assertEquals("Wrong credit nb", 0, test.getCreditNb());
    assertEquals("Wrong used credit nb", 10, test.getUsedCreditNb());
    // R�cup�re l'historique du lot de cr�dits utilis�
    history = this.getHistoryDao().findOneByAccount(test);
    assertEquals("Wrong version", 0, history.getVersion());
    assertTrue("Wrong credit history", history.isHistoryOf(bundle));
    assertTrue("Wrong credit history", history.identical(bundle.getHistory()));

    try
    {
      this.getDao().historize(bundle);
      fail("Should fail with historized bunble");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      // V�rifie le lot de cr�dits utilis�
      bundle = this.getById(bundle.getId());
      assertEquals("Wrong version", 3, bundle.getVersion());
      assertFalse("Should not be linked anymore", bundle.isLinkedToAccount());
      assertEquals("Wrong current nb", 0, bundle.getCurrentNb());
      assertTrue("Should be historised", bundle.isHistorized());
      // R�cup�re l'historique du lot de cr�dits utilis�
      history = this.getHistoryDao().findOneByAccount(test);
      assertEquals("Wrong version", 0, history.getVersion());
      assertTrue("Wrong credit history", history.isHistoryOf(bundle));
      assertTrue("Wrong credit history", history.identical(bundle.getHistory()));
    }
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityMultipleDaoTester#isAccountUpdatedAfterAdd()
   */
  @Override
  public boolean isAccountUpdatedAfterAdd()
  {
    return true;
  }
  /**
   *
   * TODO A COMMENTER
   * @param account {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityDaoTester#create(com.bid4win.commons.persistence.entity.account.AccountAbstract)
   */
  @Override
  protected CreditBundle create(Account account) throws Bid4WinException
  {
    return new CreditBundle(account, this.getGenerator().createCreditOrigin(), 1.23, 10);
  }

  /**
   * Permet de pr�ciser le DAO � tester
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityDaoTester#getDao()
   */
  @Override
  protected CreditBundleDaoStub getDao()
  {
    return this.dao;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityDaoTester#getSetupAccountNb()
   */
  @Override
  public int getSetupAccountNb()
  {
    return 3;
  }
}
