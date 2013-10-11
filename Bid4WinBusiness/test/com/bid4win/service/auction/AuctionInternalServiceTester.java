package com.bid4win.service.auction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.persistence.dao.auction.AuctionInitializer_;
import com.bid4win.persistence.dao.auction.IAuctionDaoStub;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.account.credit.CreditUsage;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementAuction;
import com.bid4win.persistence.entity.auction.Auction;
import com.bid4win.persistence.entity.auction.Bid;
import com.bid4win.persistence.entity.auction.BidHistory;
import com.bid4win.persistence.entity.auction.Bot;
import com.bid4win.persistence.entity.auction.CancelPolicy;
import com.bid4win.persistence.entity.auction.Schedule;
import com.bid4win.persistence.entity.auction.Terms;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <AUCTION> TODO A COMMENTER<BR>
 * @param <BID> TODO A COMMENTER<BR>
 * @param <BID_HISTORY> TODO A COMMENTER<BR>
 * @param <SCHEDULE> TODO A COMMENTER<BR>
 * @param <TERMS> TODO A COMMENTER<BR>
 * @param <CANCEL_POLICY> TODO A COMMENTER<BR>
 * @param <INVOLVEMENT> TODO A COMMENTER<BR>
 * @param <USAGE> TODO A COMMENTER<BR>
 * @param <BOT> TODO A COMMENTER<BR>
 * @param <HANDLER> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class AuctionInternalServiceTester<AUCTION extends Auction<AUCTION, BID, SCHEDULE, TERMS, CANCEL_POLICY>,
                                           BID extends Bid<BID, AUCTION, BID_HISTORY>,
                                           BID_HISTORY extends BidHistory<BID_HISTORY, AUCTION, BID>,
                                           SCHEDULE extends Schedule<SCHEDULE>,
                                           TERMS extends Terms<TERMS>,
                                           CANCEL_POLICY extends CancelPolicy<CANCEL_POLICY>,
                                           INVOLVEMENT extends CreditInvolvementAuction<INVOLVEMENT, USAGE, AUCTION, ?>,
                                           USAGE extends CreditUsage<USAGE, INVOLVEMENT>,
                                           BOT extends Bot<BOT, AUCTION, BID>,
                                           HANDLER extends AuctionInternalHandler_<HANDLER, AUCTION, SCHEDULE, ?>>
       extends AuctionAbstractInternalServiceTester<AUCTION, BID, BID_HISTORY, SCHEDULE, TERMS, CANCEL_POLICY, INVOLVEMENT, USAGE, HANDLER>
{
  /**
   * Test of end(String), of class AuctionManager_.
   * @throws Bid4WinException {@inheritDoc}
   * @throws InterruptedException {@inheritDoc}
   * @see com.bid4win.service.auction.AuctionAbstractInternalServiceTester#testEnd_String()
   */
  @Override
  @Test
  public void testEnd_String() throws Bid4WinException, InterruptedException
  {
    super.testEnd_String();
    AUCTION auction = this.getAuctionInitializer().startAuction(1);
    int creditNbPerBid = auction.getTerms().getCreditNbPerBid();
    this.getAccountInitializer().addBundle(0, creditNbPerBid * 3);
    Account account1 = this.getAccount(0);
    this.getAccountInitializer().addBundle(1, creditNbPerBid);
    Account account2 = this.getAccount(1);
    this.getAccountInitializer().addBundle(2, creditNbPerBid);
    Account account3 = this.getAccount(2);

    BOT bot1 = this.getAuctionInitializer().addBot(1, account1);
    BOT bot2 = this.getAuctionInitializer().addBot(1, account2);

    if(auction.getSchedule().getEndCountdown(new Bid4WinDate()) > 0)
    {
      // Termine avant la date de clôture
      AUCTION result = this.getService().end(auction.getId());
      assertEquals("Wrong version", auction.getVersion(), result.getVersion());
      assertFalse("Should not be ended", result.isEnded());
      Account account = this.getAccount(0);
      assertEquals("Wrong account version", account1.getVersion(), account.getVersion());
      account = this.getAccount(1);
      assertEquals("Wrong account version", account2.getVersion(), account.getVersion());
      account = this.getAccount(2);
      assertEquals("Wrong account version", account3.getVersion(), account.getVersion());

      synchronized(auction)
      {
        auction.wait(auction.getSchedule().getEndCountdown(new Bid4WinDate()) + 10);
      }
    }

    // Termine avec deux robots valides
    AUCTION result = this.getService().end(auction.getId());
    assertEquals("Wrong version", auction.getVersion() + 1, result.getVersion());
    assertFalse("Should not be ended", result.isEnded());
    auction = result;
    Account account = this.getAccount(0);
    assertEquals("Wrong account version", account1.getVersion() + 1, account.getVersion());
    assertEquals("Wrong account credit nb", account1.getCreditNb() - creditNbPerBid, account.getCreditNb());
    account1 = account;
    account = this.getAccount(1);
    assertEquals("Wrong account version", account2.getVersion(), account.getVersion());
    account = this.getAccount(2);
    assertEquals("Wrong account version", account3.getVersion(), account.getVersion());
    BOT bot = this.getAuctionInitializer().getBot(bot1.getId());
    assertEquals("Wrong bot version", bot1.getVersion() + 1, bot.getVersion());
    assertEquals("Wrong last bid position", auction.getBidNb(), bot.getLastBidPosition());
    assertEquals("Wrong used bid nb", bot1.getUsedBidNb() + 1, bot.getUsedBidNb());
    bot1 = bot;
    bot = this.getAuctionInitializer().getBot(bot2.getId());
    assertEquals("Wrong bot version", bot2.getVersion(), bot.getVersion());

    if(auction.getSchedule().getEndCountdown(new Bid4WinDate()) > 0)
    {
      // Termine avant la date de clôture
      result = this.getService().end(auction.getId());
      assertEquals("Wrong version", auction.getVersion(), result.getVersion());
      assertFalse("Should not be ended", result.isEnded());
      account = this.getAccount(0);
      assertEquals("Wrong account version", account1.getVersion(), account.getVersion());
      account = this.getAccount(1);
      assertEquals("Wrong account version", account2.getVersion(), account.getVersion());
      account = this.getAccount(2);
      assertEquals("Wrong account version", account3.getVersion(), account.getVersion());
      bot = this.getAuctionInitializer().getBot(bot1.getId());
      assertEquals("Wrong bot version", bot1.getVersion(), bot.getVersion());
      bot = this.getAuctionInitializer().getBot(bot2.getId());
      assertEquals("Wrong bot version", bot2.getVersion(), bot.getVersion());

      synchronized(auction)
      {
        auction.wait(auction.getSchedule().getEndCountdown(new Bid4WinDate()) + 10);
      }
    }

    // Termine avec un robot valide
    result = this.getService().end(auction.getId());
    assertEquals("Wrong version", auction.getVersion() + 1, result.getVersion());
    assertFalse("Should not be ended", result.isEnded());
    auction = result;
    account = this.getAccount(0);
    assertEquals("Wrong account version", account1.getVersion(), account.getVersion());
    assertEquals("Wrong account credit nb", account1.getCreditNb(), account.getCreditNb());
    account = this.getAccount(1);
    assertEquals("Wrong account version", account2.getVersion() + 1, account.getVersion());
    assertEquals("Wrong account credit nb", account2.getCreditNb() - creditNbPerBid, account.getCreditNb());
    account2 = account;
    account = this.getAccount(2);
    assertEquals("Wrong account version", account3.getVersion(), account.getVersion());
    bot = this.getAuctionInitializer().getBot(bot1.getId());
    assertEquals("Wrong bot version", bot1.getVersion(), bot.getVersion());
    bot = this.getAuctionInitializer().getBot(bot2.getId());
    assertEquals("Wrong bot version", bot2.getVersion() + 1, bot.getVersion());
    assertEquals("Wrong last bid position", auction.getBidNb(), bot.getLastBidPosition());
    assertEquals("Wrong used bid nb", bot2.getUsedBidNb() + 1, bot.getUsedBidNb());
    bot2 = bot;

    if(auction.getSchedule().getEndCountdown(new Bid4WinDate()) > 0)
    {
      // Termine avant la date de clôture
      result = this.getService().end(auction.getId());
      assertEquals("Wrong version", auction.getVersion(), result.getVersion());
      assertFalse("Should not be ended", result.isEnded());
      account = this.getAccount(0);
      assertEquals("Wrong account version", account1.getVersion(), account.getVersion());
      account = this.getAccount(1);
      assertEquals("Wrong account version", account2.getVersion(), account.getVersion());
      account = this.getAccount(2);
      assertEquals("Wrong account version", account3.getVersion(), account.getVersion());
      bot = this.getAuctionInitializer().getBot(bot1.getId());
      assertEquals("Wrong bot version", bot1.getVersion(), bot.getVersion());
      bot = this.getAuctionInitializer().getBot(bot2.getId());
      assertEquals("Wrong bot version", bot2.getVersion(), bot.getVersion());

      synchronized(auction)
      {
        auction.wait(auction.getSchedule().getEndCountdown(new Bid4WinDate()) + 10);
      }
    }

    // Termine avec un robot valide
    result = this.getService().end(auction.getId());
    assertEquals("Wrong version", auction.getVersion() + 1, result.getVersion());
    assertFalse("Should not be ended", result.isEnded());
    auction = result;
    account = this.getAccount(0);
    assertEquals("Wrong account version", account1.getVersion() + 1, account.getVersion());
    assertEquals("Wrong account credit nb", account1.getCreditNb() - creditNbPerBid, account.getCreditNb());
    account1 = account;
    account = this.getAccount(1);
    assertEquals("Wrong account version", account2.getVersion(), account.getVersion());
    account = this.getAccount(2);
    assertEquals("Wrong account version", account3.getVersion(), account.getVersion());
    bot = this.getAuctionInitializer().getBot(bot1.getId());
    assertEquals("Wrong bot version", bot1.getVersion() + 1, bot.getVersion());
    assertEquals("Wrong last bid position", auction.getBidNb(), bot.getLastBidPosition());
    assertEquals("Wrong used bid nb", bot1.getUsedBidNb() + 1, bot.getUsedBidNb());
    bot1 = bot;
    bot = this.getAuctionInitializer().getBot(bot2.getId());
    assertEquals("Wrong bot version", bot2.getVersion(), bot.getVersion());

    if(auction.getSchedule().getEndCountdown(new Bid4WinDate()) > 0)
    {
      // Termine avant la date de clôture
      result = this.getService().end(auction.getId());
      assertEquals("Wrong version", auction.getVersion(), result.getVersion());
      assertFalse("Should not be ended", result.isEnded());
      account = this.getAccount(0);
      assertEquals("Wrong account version", account1.getVersion(), account.getVersion());
      account = this.getAccount(1);
      assertEquals("Wrong account version", account2.getVersion(), account.getVersion());
      account = this.getAccount(2);
      assertEquals("Wrong account version", account3.getVersion(), account.getVersion());
      bot = this.getAuctionInitializer().getBot(bot1.getId());
      assertEquals("Wrong bot version", bot1.getVersion(), bot.getVersion());
      bot = this.getAuctionInitializer().getBot(bot2.getId());
      assertEquals("Wrong bot version", bot2.getVersion(), bot.getVersion());

      synchronized(auction)
      {
        auction.wait(auction.getSchedule().getEndCountdown(new Bid4WinDate()) + 100);
      }
    }

    // Plus aucun robot valide
    result = this.getService().end(auction.getId());
    assertEquals("Wrong version", auction.getVersion() + 1, result.getVersion());
    assertTrue("Should be ended", result.isEnded());
    auction = result;
    account = this.getAccount(0);
    assertEquals("Wrong account version", account1.getVersion(), account.getVersion());
    account = this.getAccount(1);
    assertEquals("Wrong account version", account2.getVersion(), account.getVersion());
    account = this.getAccount(2);
    assertEquals("Wrong account version", account3.getVersion(), account.getVersion());
    bot = this.getAuctionInitializer().getBot(bot1.getId());
    assertEquals("Wrong bot version", bot1.getVersion(), bot.getVersion());
    bot = this.getAuctionInitializer().getBot(bot2.getId());
    assertEquals("Wrong bot version", bot2.getVersion(), bot.getVersion());

    BOT bot3 = this.getAuctionInitializer().addBot(1, account3);

    try
    {
      this.getService().end(auction.getId());
      fail("Should fail with ended auction");
    }
    catch(UserException ex)
    {
      result = this.getAuction(1);
      assertEquals("Wrong version", auction.getVersion(), result.getVersion());
      assertTrue("Should be ended", result.isEnded());
      account = this.getAccount(0);
      assertEquals("Wrong account version", account1.getVersion(), account.getVersion());
      account = this.getAccount(1);
      assertEquals("Wrong account version", account2.getVersion(), account.getVersion());
      account = this.getAccount(2);
      assertEquals("Wrong account version", account3.getVersion(), account.getVersion());
      bot = this.getAuctionInitializer().getBot(bot1.getId());
      assertEquals("Wrong bot version", bot1.getVersion(), bot.getVersion());
      bot = this.getAuctionInitializer().getBot(bot2.getId());
      assertEquals("Wrong bot version", bot2.getVersion(), bot.getVersion());
      bot = this.getAuctionInitializer().getBot(bot3.getId());
      assertEquals("Wrong bot version", bot3.getVersion(), bot.getVersion());
    }
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.service.auction.AuctionAbstractServiceTester#getAuctionInitializer()
   */
  @Override
  protected abstract AuctionInitializer_<AUCTION, BID, SCHEDULE, TERMS, CANCEL_POLICY, BOT> getAuctionInitializer();
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.service.auction.AuctionAbstractServiceTester#getService()
   */
  @Override
  public abstract AuctionInternalService_<AUCTION, BID, BID_HISTORY, SCHEDULE, TERMS, CANCEL_POLICY, INVOLVEMENT, BOT, HANDLER, ?> getService();
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.service.auction.AuctionAbstractServiceTester#getDao()
   */
  @Override
  protected abstract IAuctionDaoStub<AUCTION, BID, SCHEDULE, TERMS, CANCEL_POLICY> getDao();
}
