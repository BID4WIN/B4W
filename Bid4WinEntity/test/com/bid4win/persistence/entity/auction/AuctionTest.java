package com.bid4win.persistence.entity.auction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.collection.Bid4WinList;
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
public class AuctionTest extends Bid4WinEntityTester
{
  /**
   * Test of addBid(Account, Bid4WinDate), of class Auction.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testAddBid_Account_Bid4WinDate() throws Bid4WinException
  {
    Account account1 = this.getGenerator().createAccount();
    Account account2 = this.getGenerator().createAccount();
    Bid4WinList<BidStub> lastBidList = new Bid4WinList<BidStub>();

    AuctionStub auction = this.getGenerator().createAuction();
    Bid4WinDate endDate = auction.getSchedule().getStartDate().addTime(auction.getSchedule().getInitialCountdown() * 1000);
    assertEquals("Wrong bid nb", 0, auction.getBidNb());
    assertEquals("Wrong end date", endDate, auction.getSchedule().getEndDate());
    assertNull("Wrong last bid", auction.getLastBid());
    assertEquals("Wrong last List", lastBidList, auction.getLastBidList());
    try
    {
      auction.addBid(account1, new Bid4WinDate());
      fail("Should fail with wrong status");
    }
    catch(UserException ex)
    {
      assertEquals("Wrong bid nb", 0, auction.getBidNb());
      assertEquals("Wrong end date", endDate, auction.getSchedule().getEndDate());
      assertNull("Wrong last bid", auction.getLastBid());
      assertEquals("Wrong last List", lastBidList, auction.getLastBidList());
    }
    auction.validate(new CancelPolicyStub(), this.getGenerator().createExchangeRates()).start();

    Bid4WinDate bidDate = endDate.addTime(-auction.getSchedule().getAdditionalCountdown() * 1000);

    BidStub bid = auction.addBid(account1, bidDate);
    lastBidList.add(bid);
    assertEquals("Wrong bid nb", 1, auction.getBidNb());
    assertEquals("Wrong end date", endDate, auction.getSchedule().getEndDate());
    assertTrue("Wrong last bid", bid == auction.getLastBid());
    assertEquals("Wrong last List", lastBidList, auction.getLastBidList());
    assertTrue("Wrong auction", auction == bid.getAuction());
    assertTrue("Wrong auction link", auction == bid.getAuctionLink());
    assertTrue("Wrong account", account1 == bid.getAccount());

    bidDate = endDate.addTime(-auction.getSchedule().getAdditionalCountdown() * 1000 + 1);

    try
    {
      auction.addBid(account1, bidDate);
      fail("Should fail with same last bidder");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertEquals("Wrong bid nb", 1, auction.getBidNb());
      assertEquals("Wrong end date", endDate, auction.getSchedule().getEndDate());
      assertTrue("Wrong last bid", bid == auction.getLastBid());
      assertEquals("Wrong last List", lastBidList, auction.getLastBidList());
      assertTrue("Wrong auction", auction == bid.getAuction());
      assertTrue("Wrong auction link", auction == bid.getAuctionLink());
      assertTrue("Wrong account", account1 == bid.getAccount());
    }
    try
    {
      auction.addBid(null, bidDate);
      fail("Should fail with null bidder");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertEquals("Wrong bid nb", 1, auction.getBidNb());
      assertEquals("Wrong end date", endDate, auction.getSchedule().getEndDate());
      assertTrue("Wrong last bid", bid == auction.getLastBid());
      assertEquals("Wrong last List", lastBidList, auction.getLastBidList());
      assertTrue("Wrong auction", auction == bid.getAuction());
      assertTrue("Wrong auction link", auction == bid.getAuctionLink());
      assertTrue("Wrong account", account1 == bid.getAccount());
    }
    try
    {
      auction.addBid(account2, null);
      fail("Should fail with null bid date");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertEquals("Wrong bid nb", 1, auction.getBidNb());
      assertEquals("Wrong end date", endDate, auction.getSchedule().getEndDate());
      assertTrue("Wrong last bid", bid == auction.getLastBid());
      assertEquals("Wrong last List", lastBidList, auction.getLastBidList());
      assertTrue("Wrong auction", auction == bid.getAuction());
      assertTrue("Wrong auction link", auction == bid.getAuctionLink());
      assertTrue("Wrong account", account1 == bid.getAccount());
    }

    endDate = endDate.addTime(auction.getSchedule().getAdditionalCountdown() * 1000);
    bid = auction.addBid(account2, bidDate);
    lastBidList.add(0, bid);
    assertEquals("Wrong bid nb", 2, auction.getBidNb());
    assertEquals("Wrong end date", endDate, auction.getSchedule().getEndDate());
    assertTrue("Wrong last bid", bid == auction.getLastBid());
    assertEquals("Wrong last List", lastBidList, auction.getLastBidList());
    assertTrue("Wrong auction", auction == bid.getAuction());
    assertTrue("Wrong auction link", auction == bid.getAuctionLink());
    assertTrue("Wrong account", account2 == bid.getAccount());

    bidDate = endDate.addTime(-auction.getSchedule().getAdditionalCountdown() * 1000);
    bid = auction.addBid(account1, bidDate);
    lastBidList.add(0, bid);
    assertEquals("Wrong bid nb", 3, auction.getBidNb());
    assertEquals("Wrong end date", endDate, auction.getSchedule().getEndDate());
    assertTrue("Wrong last bid", bid == auction.getLastBid());
    assertEquals("Wrong last List", lastBidList, auction.getLastBidList());
    assertTrue("Wrong auction", auction == bid.getAuction());
    assertTrue("Wrong auction link", auction == bid.getAuctionLink());
    assertTrue("Wrong account", account1 == bid.getAccount());

    BidStub removed = lastBidList.remove(1);
    removed.unlinkFromAuction();
    assertEquals("Wrong bid nb", 3, auction.getBidNb());
    assertEquals("Wrong end date", endDate, auction.getSchedule().getEndDate());
    assertTrue("Wrong last bid", bid == auction.getLastBid());
    assertEquals("Wrong last List", lastBidList, auction.getLastBidList());
    assertTrue("Wrong auction", auction == bid.getAuction());
    assertTrue("Wrong auction link", auction == bid.getAuctionLink());
    assertTrue("Wrong account", account1 == bid.getAccount());

    bidDate = endDate.addTime(-auction.getSchedule().getAdditionalCountdown() * 1000 + 1);
    endDate = endDate.addTime(auction.getSchedule().getAdditionalCountdown() * 1000);
    bid = auction.addBid(account2, bidDate);
    lastBidList.add(0, bid);
    assertEquals("Wrong bid nb", 4, auction.getBidNb());
    assertEquals("Wrong end date", endDate, auction.getSchedule().getEndDate());
    assertTrue("Wrong last bid", bid == auction.getLastBid());
    assertEquals("Wrong last List", lastBidList, auction.getLastBidList());
    assertTrue("Wrong auction", auction == bid.getAuction());
    assertTrue("Wrong auction link", auction == bid.getAuctionLink());
    assertTrue("Wrong account", account2 == bid.getAccount());

    bidDate = endDate.addTime(-auction.getSchedule().getAdditionalCountdown() * 1000 + 1);
    try
    {
      auction.addBid(account2, bidDate);
      fail("Should fail with same last bidder");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertEquals("Wrong bid nb", 4, auction.getBidNb());
      assertEquals("Wrong end date", endDate, auction.getSchedule().getEndDate());
      assertTrue("Wrong last bid", bid == auction.getLastBid());
      assertEquals("Wrong last List", lastBidList, auction.getLastBidList());
      assertTrue("Wrong auction", auction == bid.getAuction());
      assertTrue("Wrong auction link", auction == bid.getAuctionLink());
      assertTrue("Wrong account", account2 == bid.getAccount());
    }

    bid.unlinkFromAuction();
    lastBidList.removeFirst();
    bid = lastBidList.getFirst();
    try
    {
      auction.addBid(account2, bidDate);
      fail("Should fail with same last bidder");
    }
    catch(RuntimeException ex)
    {
      System.out.println(ex.getMessage());
      assertEquals("Wrong bid nb", 4, auction.getBidNb());
      assertEquals("Wrong end date", endDate, auction.getSchedule().getEndDate());
      assertNull("Wrong last bid", auction.getLastBid());
      assertEquals("Wrong last List", lastBidList, auction.getLastBidList());
      assertTrue("Wrong auction", auction == bid.getAuction());
      assertTrue("Wrong auction link", auction == bid.getAuctionLink());
      assertTrue("Wrong account", account1 == bid.getAccount());
    }
  }
}
