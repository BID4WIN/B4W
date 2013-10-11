package com.bid4win.persistence.dao.connection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.persistence.dao.account.AccountBasedKeyDaoTester;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.dao.account.AccountInitializer;
import com.bid4win.persistence.entity.EntityGenerator;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.connection.Subscription;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
@TransactionConfiguration(transactionManager = "Bid4WinTransactionManager")
public class SubscriptionDaoTest
       extends AccountBasedKeyDaoTester<Subscription, Account, EntityGenerator>
{
  /** Référence du DAO à tester */
  @Autowired
  @Qualifier("SubscriptionDaoStub")
  private SubscriptionDaoStub dao;
  /** Référence TODO */
  @Autowired
  @Qualifier("AccountInitializer")
  private AccountInitializer accountInitializer;

  /**
   * Test of update(Subscription), of class SubscriptionDao.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testUpdate_Subscription() throws Bid4WinException
  {
    Account account1 = this.getAccount(0);
    Subscription subscription = this.create(account1);
    this.getDao().add(subscription);
    Subscription result = this.getDao().getById(subscription.getId());
    assertTrue("Wrong subscription", subscription.identical(result));
    subscription.defineValidationDate();
    this.getDao().update(subscription);
    result = this.getDao().getById(subscription.getId());
    assertEquals("Wrong version", 1, result.getVersion());
    assertTrue("Wrong result", subscription.same(result));
    assertFalse("Wrong result", subscription.identical(result));
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
  protected Subscription create(Account account) throws Bid4WinException
  {
    return new Subscription(account);
  }

  /**
   * Getter du DAO à tester
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedKeyDaoTester#getDao()
   */
  @Override
  public SubscriptionDaoStub getDao()
  {
    return this.dao;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityDaoTester#getAccountInitializer()
   */
  @Override
  protected AccountInitializer getAccountInitializer()
  {
    return this.accountInitializer;
  }
}
