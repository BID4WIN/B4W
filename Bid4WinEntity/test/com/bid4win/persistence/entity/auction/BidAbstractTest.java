package com.bid4win.persistence.entity.auction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
public class BidAbstractTest extends Bid4WinEntityTester
{
  /**
   * Test of BidAbstract(Account, AUCTION, int) method, of class BidAbstract.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testBidAbstract_Account_AUCTION_int() throws Bid4WinException
  {
    Account account = this.getGenerator().createAccount();
    AuctionAbstractStub auction = this.getGenerator().createAuctionAbstract();
    try
    {
      BidAbstractStub bid = new BidAbstractStub(account, auction, 1);
      assertTrue("Bad account", account == bid.getAccount());
      assertTrue("Bad auction", auction == bid.getAuction());
      assertEquals("Bad position", 1, bid.getPosition());
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Instanciation should not fail: " + ex.getMessage());
    }
    try
    {
      new BidAbstractStub(null, auction, 1);
      fail("Should should fail with null account");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      new BidAbstractStub(account, null, 1);
      fail("Should should fail with null auction");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      new BidAbstractStub(account, auction, 0);
      fail("Should should fail with zero or negative position");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of sameInternal(BidAbstract, boolean), of class BidAbstract.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testSameInternal_BidAbstract_boolean() throws Bid4WinException
  {
    Account account1 = this.getGenerator().createAccount("1");
    Account account2 = this.getGenerator().createAccount("2");
    AuctionAbstractStub auction1 = this.getGenerator().createAuctionAbstract();
    AuctionAbstractStub auction2 = this.getGenerator().createAuctionAbstract();

    BidAbstractStub bid1 = new BidAbstractStub(account1, auction1, 1);
    BidAbstractStub bid2 = new BidAbstractStub(account1, auction1, 1);
    assertTrue(bid1.same(bid2));
    assertTrue(bid2.same(bid1));
    assertTrue(bid1.identical(bid2));
    assertTrue(bid2.identical(bid1));

    bid2 = new BidAbstractStub(account1, auction1, 2);
    assertFalse(bid1.same(bid2));
    assertFalse(bid2.same(bid1));
    assertFalse(bid1.identical(bid2));
    assertFalse(bid2.identical(bid1));

    bid2 = new BidAbstractStub(account2, auction1, 1);
    assertTrue(bid1.same(bid2));
    assertTrue(bid2.same(bid1));
    assertFalse(bid1.identical(bid2));
    assertFalse(bid2.identical(bid1));

    bid2 = new BidAbstractStub(account1, auction2, 1);
    assertTrue(bid1.same(bid2));
    assertTrue(bid2.same(bid1));
    assertTrue(bid1.identical(bid2));
    assertTrue(bid2.identical(bid1));

    auction1.defineBidNb(2);
    assertFalse(bid1.same(bid2));
    assertFalse(bid2.same(bid1));
    assertFalse(bid1.identical(bid2));
    assertFalse(bid2.identical(bid1));

    auction2.defineBidNb(2);
    assertTrue(bid1.same(bid2));
    assertTrue(bid2.same(bid1));
    assertTrue(bid1.identical(bid2));
    assertTrue(bid2.identical(bid1));
  }
}
