package com.bid4win.persistence.entity.account.credit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.entity.account.Account;

/**
 * Classe de test d'un historique de lot de crédit<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
public class CreditBundleHistoryTest
       extends CreditBundleAbstractTester<CreditBundleHistory>
{
  /**
   *
   * TODO A COMMENTER
   * @param account {@inheritDoc}
   * @param origin {@inheritDoc}
   * @param totalValue {@inheritDoc}
   * @param nb {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.persistence.entity.account.credit.CreditBundleAbstractTester#createEntity(com.bid4win.persistence.entity.account.Account, com.bid4win.persistence.entity.account.credit.CreditOrigin, double, int)
   */
  @Override
  protected CreditBundleHistory createEntity(Account account, CreditOrigin origin, double totalValue, int nb) throws UserException
  {
    return new CreditBundleHistory(account, origin, totalValue, nb);
  }

  /**
   * Test of  CreditBundleHistory(...) method, of class CreditBundleHistory.
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.account.AccountBasedEntityTester#testConstructor_etc()
   */
  @Override
  @Test
  public void testConstructor_etc() throws Bid4WinException
  {
    super.testConstructor_etc();

    Account account = this.getGenerator().createAccount("123");
    CreditOrigin origin = this.getGenerator().createCreditOrigin();
    CreditBundle bundle = new CreditBundle(account, origin, 1.23, 10);
    try
    {
      CreditBundleHistory history = new CreditBundleHistory(bundle);
      assertEquals("Bad origin", bundle.getOrigin(), history.getOrigin());
      assertEquals("Bad unit value", bundle.getUnitValue(), history.getUnitValue());
      assertEquals("Bad credit nb", bundle.getNb(), history.getNb());
      assertTrue("Bad account", account == bundle.getAccount());
    }
    catch(Bid4WinException ex)
    {
      ex.printStackTrace();
      fail("Instanciation should not fail: " + ex.getMessage());
    }
    try
    {
      new CreditBundleHistory(null);
      fail("Instanciation with null bundle should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
}
