package com.bid4win.persistence.entity.account.credit.auction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.collection.Bid4WinCollection;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.account.credit.CreditBundle;
import com.bid4win.persistence.entity.account.credit.CreditBundleStub;
import com.bid4win.persistence.entity.account.credit.CreditInvolvementTester;
import com.bid4win.persistence.entity.account.credit.CreditOrigin;
import com.bid4win.persistence.entity.auction.normal.NormalAuction;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
public class CreditInvolvementNormalTest
       extends CreditInvolvementTester<CreditInvolvementNormal, CreditUsageNormal, CreditBundle, CreditInvolvementNormalHistory>
{
  /** TODO A COMMENTER */
  private NormalAuction auction = null;
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  private NormalAuction getAuction()
  {
    if(this.auction == null)
    {
      try
      {
        this.auction = this.getGenerator().createNormalAuction();
      }
      catch(Bid4WinException ex)
      {
        ex.printStackTrace();
      }
    }
    return this.auction;
  }
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
  protected CreditUsageNormal createUsage(CreditBundle bundle, CreditInvolvementNormal involvement) throws UserException
  {
    return new CreditUsageNormal(bundle, involvement, 1);
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
  protected CreditInvolvementNormal createEntity(Account account) throws UserException
  {
    return new CreditInvolvementNormal(account, this.getAuction());
  }

  /**
   * Test of  CreditInvolvementNormal(...) method, of class CreditInvolvementNormal.
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.account.AccountBasedEntityTester#testConstructor_etc()
   */
  @Override
  @Test
  public void testConstructor_etc() throws Bid4WinException
  {
    super.testConstructor_etc();


    Account account = this.getGenerator().createAccount("123");
    NormalAuction auction = this.getGenerator().createNormalAuction();
    try
    {
      CreditInvolvementNormal involvement = new CreditInvolvementNormal(account, auction);
      assertTrue("Bad account", account == involvement.getAccount());
      assertTrue("Bad auction", auction == involvement.getAuction());
      assertTrue("Bad auction id", auction.getId() == involvement.getAuctionId());
      assertTrue("Should be referenced by account",
                 involvement == account.getInvolvementNormal(auction));
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Instanciation should not fail: " + ex.getMessage());
    }
    try
    {
      new CreditInvolvementNormal(account, null);
      fail("Instanciation with null auction should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertEquals("Account involvement map should be not be modified",
                   1, account.getInvolvementNormals().size());
    }
  }
  /**
   * Test of createHistory() method, of class CreditInvolvementNormal.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testCreateHistory_0args() throws Bid4WinException
  {
    Account account = this.getGenerator().createAccount("123");
    NormalAuction auction = this.getGenerator().createNormalAuction();

    CreditInvolvementNormal involvement = new CreditInvolvementNormal(account, auction);

    CreditBundleStub bundle1 = new CreditBundleStub(account, this.getGenerator().createCreditOrigin(), 1, 1);
    involvement.addUsage(bundle1, 1);
    CreditBundleStub bundle2 = new CreditBundleStub(account, this.getGenerator().createCreditOrigin(), 1, 1);
    involvement.addUsage(bundle2, 2);
    CreditBundleStub bundle3 = new CreditBundleStub(account, this.getGenerator().createCreditOrigin(), 1, 1);
    involvement.addUsage(bundle3, 3);

    try
    {
      involvement.createHistory();
      fail("Should fail with not historized bundle");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    bundle1.historize();
    bundle2.historize();
    try
    {
      involvement.createHistory();
      fail("Should fail with not historized bundle");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    bundle3.historize();

    CreditInvolvementNormalHistory history = involvement.createHistory();
    assertTrue("Wrong account", account == history.getAccount());
    assertTrue("Wrong auction", auction == history.getAuction());
    assertEquals("Wrong used credit nb", involvement.getUsedNb(), history.getUsedNb());
    Bid4WinCollection<CreditUsageNormal> usages = involvement.getUsages();
    Bid4WinCollection<CreditUsageNormalHistory> histories = history.getUsages();
    assertEquals("Wrong usage nb", usages.size(), histories.size());
    for(CreditUsageNormal usage : usages)
    {
      CreditUsageNormalHistory usageHistory = history.getUsage(usage.getBundle().getHistory());
      assertTrue("Wrong credit bundle", usageHistory.getBundle() == usage.getBundle().getHistory());
      assertEquals("Wrong used credit nb", usage.getUsedNb(), usageHistory.getUsedNb());
    }
  }
}
