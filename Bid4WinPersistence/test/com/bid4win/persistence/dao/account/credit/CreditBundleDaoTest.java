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
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
@TransactionConfiguration(transactionManager = "Bid4WinTransactionManager")
public class CreditBundleDaoTest extends CreditBundleAbstractDaoTester<CreditBundle>
{
  /** Référence du DAO à tester */
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
    // Ajoute un lot de crédits sans compte utilisateur
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
      // Vérifie que le compte utilisateur est inchangé
      Account account2 = this.getAccount(2);
      assertEquals("Wrong account version", account1.getVersion(), account2.getVersion());
      assertNull("Bundle should not exist", this.getDao().findOneByAccount(account1));
    }
    // Ajoute un lot de crédits déjà historisé
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
   // Création de la situation de départ
   Account account = this.getAccount(0);
   CreditBundle bundle1 = this.add(this.create(account));
   account = this.getAccount(0);
   CreditBundle bundle2 = this.add(this.create(account));
   account = this.getAccount(0);
   Account test = this.getAccount(0);

   for(int i = 0 ; i < bundle1.getInitialNb() - 1 ; i++)
   {
     // Utilise un crédit du lot
     bundle1 = test.useCredit();
     bundle1 = this.update(bundle1);
     // Vérifie le lot de crédits utilisé
     assertTrue("Wrong ID", 0 < bundle1.getId());
     assertEquals("Wrong version", 1 + i, bundle1.getVersion());
     assertTrue("Should still be linked", bundle1.isLinkedToAccount());
     assertEquals("Wrong current nb", 10 - 1 - i, bundle1.getCurrentNb());
     // Récupère le compte utilisateur du lot de droit à enchérir ajouté
     test = this.getAccount(0);
     assertEquals("Wrong version", account.getVersion() + 1 + i, test.getVersion());
     assertEquals("Wrong credit nb", 20 - 1 - i, test.getCreditNb());
     assertEquals("Wrong used credit nb", 1 + i, test.getUsedCreditNb());
   }
   // Utilise le dernier crédit du lot
   bundle1 = test.useCredit();
   bundle1 = this.update(bundle1);
   // Vérifie le lot de crédits utilisé
   assertTrue("Wrong ID", 0 < bundle1.getId());
   assertEquals("Wrong version", 10, bundle1.getVersion());
   assertFalse("Should not be linked anymore", bundle1.isLinkedToAccount());
   // Récupère le compte utilisateur du lot de crédits utilisé
   test = this.getAccount(0);
   // Vérifie le compte utilisateur du lot de crédits utilisé
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
    // Création de la situation de départ
    Account account = this.getAccount(0);
    CreditBundle bundle = this.add(this.create(account));
    account = this.getAccount(0);
    bundle = this.update(account.useCredit(bundle.getCurrentNb() - 1).keySet().iterator().next());
    // Vérifie le lot de crédits utilisé
    assertEquals("Wrong version", 1, bundle.getVersion());
    assertTrue("Should still be linked", bundle.isLinkedToAccount());
    assertEquals("Wrong current nb", 1, bundle.getCurrentNb());
    assertFalse("Should not be historised", bundle.isHistorized());
    // Récupère le compte utilisateur du lot de crédits utilisé
    Account test = this.getAccount(0);
    assertEquals("Wrong version", account.getVersion() + 1, test.getVersion());
    assertEquals("Wrong credit nb", 1, test.getCreditNb());
    assertEquals("Wrong used credit nb", 9, test.getUsedCreditNb());

    this.getDao().historize(bundle);
    bundle = this.getById(bundle.getId());
    // Vérifie le lot de crédits utilisé
    assertEquals("Wrong version", 2, bundle.getVersion());
    assertTrue("Should still be linked", bundle.isLinkedToAccount());
    assertEquals("Wrong current nb", 1, bundle.getCurrentNb());
    assertTrue("Should be historised", bundle.isHistorized());
    // Récupère le compte utilisateur du lot de crédits utilisé
    test = this.getAccount(0);
    assertEquals("Wrong version", account.getVersion() + 1, test.getVersion());
    assertEquals("Wrong credit nb", 1, test.getCreditNb());
    assertEquals("Wrong used credit nb", 9, test.getUsedCreditNb());
    // Récupère l'historique du lot de crédits utilisé
    CreditBundleHistory history = this.getHistoryDao().findOneByAccount(test);
    assertEquals("Wrong version", 0, history.getVersion());
    assertTrue("Wrong credit history", history.isHistoryOf(bundle));
    assertTrue("Wrong credit history", history.identical(bundle.getHistory()));

    this.update(test.useCredit());
    bundle = this.getById(bundle.getId());
    // Vérifie le lot de crédits utilisé
    assertEquals("Wrong version", 3, bundle.getVersion());
    assertFalse("Should not be linked anymore", bundle.isLinkedToAccount());
    assertEquals("Wrong current nb", 0, bundle.getCurrentNb());
    assertTrue("Should be historised", bundle.isHistorized());
    // Récupère le compte utilisateur du lot de droit à enchérir utilisé
    test = this.getAccount(0);
    assertEquals("Wrong version", account.getVersion() + 2, test.getVersion());
    assertEquals("Wrong credit nb", 0, test.getCreditNb());
    assertEquals("Wrong used credit nb", 10, test.getUsedCreditNb());
    // Récupère l'historique du lot de crédits utilisé
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
      // Vérifie le lot de crédits utilisé
      bundle = this.getById(bundle.getId());
      assertEquals("Wrong version", 3, bundle.getVersion());
      assertFalse("Should not be linked anymore", bundle.isLinkedToAccount());
      assertEquals("Wrong current nb", 0, bundle.getCurrentNb());
      assertTrue("Should be historised", bundle.isHistorized());
      // Récupère l'historique du lot de crédits utilisé
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
   * Permet de préciser le DAO à tester
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
