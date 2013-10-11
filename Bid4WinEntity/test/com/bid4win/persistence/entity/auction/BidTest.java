package com.bid4win.persistence.entity.auction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.entity.Bid4WinEntityTester;
import com.bid4win.persistence.entity.account.Account;

/**
 * Classe de test d'une enchère<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
public class BidTest extends Bid4WinEntityTester
{
  /**
   * Test of Bid(Account, AUCTION, int) method, of class Bid.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testBid_Account_AUCTION_int() throws Bid4WinException
  {
    Account account = this.getGenerator().createAccount();
    AuctionStub auction = this.getGenerator().createAuction();
    try
    {
      BidStub bid = new BidStub(account, auction, 1);
      assertTrue("Bad account", account == bid.getAccount());
      assertTrue("Bad auction", auction == bid.getAuction());
      assertTrue("Bad auction link", auction == bid.getAuctionLink());
      assertEquals("Bad position", 1, bid.getPosition());
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Instanciation should not fail: " + ex.getMessage());
    }
  }
  /**
   * Test of isLinkedToAuction(), of class Bid.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testIsLinkedToAccount_0args() throws Bid4WinException
  {
    Account account = this.getGenerator().createAccount();
    AuctionStub auction = this.getGenerator().createAuction();
    BidStub bid = new BidStub(account, auction, 1);
    assertTrue("Is linked to auction", bid.isLinkedToAuction());
    bid.unlinkFromAuction();
    assertFalse("Is not linked to auction", bid.isLinkedToAuction());
  }
  /**
   * Test of unlinkFromAuction(), of class Bid.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testUnlinkFromAuction_0args() throws Bid4WinException
  {
    Account account = this.getGenerator().createAccount();
    AuctionStub auction = this.getGenerator().createAuction();
    BidStub bid = new BidStub(account, auction, 1);
    assertTrue("Wrong auction", auction == bid.getAuction());
    assertTrue("Is not linked to auction", bid.isLinkedToAuction());
    assertTrue("Wrong auction", auction == bid.getAuctionLink());
    assertTrue("Wrong auction bid", bid == auction.getBid(bid.getPosition()));

    AuctionStub result = bid.unlinkFromAuction();
    assertTrue("Wrong auction", auction == bid.getAuction());
    assertFalse("Is linked to auction", bid.isLinkedToAuction());
    assertNull("Wrong auction", bid.getAuctionLink());
    assertTrue("Wrong result", auction == result);
    assertNull("Wrong auction bid", auction.getBid(bid.getPosition()));

    try
    {
      bid.unlinkFromAuction();
      fail("Second time unlink should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }

}
