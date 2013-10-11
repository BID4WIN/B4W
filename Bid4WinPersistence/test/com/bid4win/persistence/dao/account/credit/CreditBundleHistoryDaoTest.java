package com.bid4win.persistence.dao.account.credit;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.entity.account.Account;
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
public class CreditBundleHistoryDaoTest extends CreditBundleAbstractDaoTester<CreditBundleHistory>
{
  /**
   *
   * TODO A COMMENTER
   * @param account {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityDaoTester#create(com.bid4win.commons.persistence.entity.account.AccountAbstract)
   */
  @Override
  protected CreditBundleHistory create(Account account) throws Bid4WinException
  {
    return this.getGenerator().createCreditBundleHistory(account, 1, 10);
  }

  /**
   * Permet de préciser le DAO à tester
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityMultipleDaoTester#getDao()
   */
  @Override
  protected CreditBundleHistoryDaoStub getDao()
  {
    return this.getHistoryDao();
  }
}
