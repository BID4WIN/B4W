package com.bid4win.persistence.entity.account.credit;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.entity.account.Account;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
public class CreditInvolvementTest
       extends CreditInvolvementTester<CreditInvolvementStub, CreditUsageStub, CreditBundle, CreditInvolvementHistoryStub>
{
  /**
   *
   * TODO A COMMENTER
   * @param account {@inheritDoc}
   * @param origin {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.persistence.entity.account.credit.CreditInvolvementTester#createBundle(com.bid4win.persistence.entity.account.Account, com.bid4win.persistence.entity.account.credit.CreditOrigin)
   */
  @Override
  protected CreditBundle createBundle(Account account, CreditOrigin origin) throws UserException
  {
    return new CreditBundleStub(account, origin, 1.23, 1);
  }
  /**
   *
   * TODO A COMMENTER
   * @param bundle {@inheritDoc}
   * @param involvement {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.persistence.entity.account.credit.CreditInvolvementTester#createUsage(com.bid4win.persistence.entity.account.credit.CreditBundleAbstract, com.bid4win.persistence.entity.account.credit.CreditInvolvement)
   */
  @Override
  protected CreditUsageStub createUsage(CreditBundle bundle, CreditInvolvementStub involvement) throws UserException
  {
    return new CreditUsageStub(bundle, involvement, 1);
  }
  /**
   *
   * TODO A COMMENTER
   * @param account {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.account.AccountBasedEntityTester#createEntity(com.bid4win.commons.persistence.entity.account.AccountAbstract)
   */
  @Override
  protected CreditInvolvementStub createEntity(Account account) throws UserException
  {
    return new CreditInvolvementStub(account);
  }
}
