package com.bid4win.persistence.entity.auction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.entity.Bid4WinEntityTester;
import com.bid4win.persistence.entity.account.Account;

/**
*
* TODO A COMMENTER<BR>
* <BR>
* @author Emeric Fillâtre
*/
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
public class BidHistoryTest extends Bid4WinEntityTester
{
  /**
   * Test of BidHistory(BID) method, of class BidHistory.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testBidHistory_BID() throws Bid4WinException
  {
    Account account = this.getGenerator().createAccount();
    AuctionAbstractStub auction = this.getGenerator().createAuctionAbstract();
    auction.validate(new CancelPolicyAbstractStub(), this.getGenerator().createExchangeRates()).start();
    BidAbstractStub bid = auction.addBid(account, new Bid4WinDate());
    try
    {
      BidAbstractStubHistory history = new BidAbstractStubHistory(bid);
      assertTrue("Bad account", account == bid.getAccount());
      assertTrue("Bad auction", auction == bid.getAuction());
      assertEquals("Bad position", bid.getPosition(), history.getPosition());
      assertEquals("Bad bid date", bid.getCreateDate(), history.getBidDate());
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Instanciation should not fail: " + ex.getMessage());
    }
    try
    {
      new BidAbstractStubHistory(null);
      fail("Should should fail with null bid");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of isHistoryOf(BID) method, of class BidHistory.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testIsHistoryOf_BID() throws Bid4WinException
  {
    Account account = this.getGenerator().createAccount();
    AuctionAbstractStub auction = this.getGenerator().createAuctionAbstract();
    auction.validate(new CancelPolicyAbstractStub(), this.getGenerator().createExchangeRates()).start();

    BidAbstractStub bid1 = auction.addBid(account, new Bid4WinDate());
    BidAbstractStubHistory history1 = bid1.toHistory();
    assertTrue("Wrong result", history1.isHistoryOf(bid1));

    BidAbstractStub bid2 = auction.addBid(this.getGenerator().createAccount(), new Bid4WinDate());
    BidAbstractStubHistory history2 = bid2.toHistory();
    assertTrue("Wrong result", history1.isHistoryOf(bid1));
    assertTrue("Wrong result", history2.isHistoryOf(bid2));

    assertFalse("Wrong result", history1.isHistoryOf(bid2));
    assertFalse("Wrong result", history2.isHistoryOf(bid1));
  }
}
