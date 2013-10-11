package com.bid4win.service.auction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.account.security.Role;
import com.bid4win.persistence.dao.auction.AuctionAbstractInitializer_;
import com.bid4win.persistence.dao.auction.IAuctionAbstractDaoStub;
import com.bid4win.persistence.entity.account.credit.CreditUsage;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementAuction;
import com.bid4win.persistence.entity.auction.AuctionAbstract;
import com.bid4win.persistence.entity.auction.BidAbstract;
import com.bid4win.persistence.entity.auction.BidHistory;
import com.bid4win.persistence.entity.auction.CancelPolicyAbstract;
import com.bid4win.persistence.entity.auction.ScheduleAbstract;
import com.bid4win.persistence.entity.auction.Status;
import com.bid4win.persistence.entity.auction.TermsAbstract;
import com.bid4win.service.Bid4WinServiceTester;

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
 * @param <HANDLER> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class AuctionAbstractInternalServiceTester<AUCTION extends AuctionAbstract<AUCTION, BID, SCHEDULE, TERMS, CANCEL_POLICY>,
                                                   BID extends BidAbstract<BID, AUCTION, BID_HISTORY>,
                                                   BID_HISTORY extends BidHistory<BID_HISTORY, AUCTION, BID>,
                                                   SCHEDULE extends ScheduleAbstract<SCHEDULE>,
                                                   TERMS extends TermsAbstract<TERMS>,
                                                   CANCEL_POLICY extends CancelPolicyAbstract<CANCEL_POLICY>,
                                                   INVOLVEMENT extends CreditInvolvementAuction<INVOLVEMENT, USAGE, AUCTION, ?>,
                                                   USAGE extends CreditUsage<USAGE, INVOLVEMENT>,
                                                   HANDLER extends AuctionAbstractInternalHandler_<HANDLER, AUCTION, SCHEDULE, ?>>
       extends Bid4WinServiceTester<AUCTION, String>
{
  /** TODO A COMMENTER */
  private Object timer = new Object();

  /**
   * Test of start(String), of class AuctionAbstractInternalManager_.
   * @throws Bid4WinException Issue not expected during this test
   * @throws InterruptedException Issue not expected during this test
   */
  @Test
  public void testStart_String() throws Bid4WinException, InterruptedException
  {
    AUCTION auction = this.getAuction(0);
    AUCTION result = this.getService().start(auction.getId());
    assertEquals("Wrong version", auction.getVersion(), result.getVersion());
    assertTrue("Auction should not be started", auction.identical(result));

    auction = this.getAuctionInitializer().validateAuction(0);
    result = this.getService().start(auction.getId());
    assertEquals("Wrong version", auction.getVersion(), result.getVersion());
    assertTrue("Auction should not be started", auction.identical(result));

    synchronized(auction)
    {
      auction.wait(auction.getSchedule().getStartCountdown(new Bid4WinDate()) + 50);
    }

    result = this.getService().start(auction.getId());
    assertEquals("Wrong version", auction.getVersion() + 1, result.getVersion());
    assertTrue("Auction should be started", result.isStarted());

    result = this.getService().start(auction.getId());
    assertEquals("Wrong version", auction.getVersion() + 1, result.getVersion());
    assertTrue("Auction should be started", result.isStarted());

    auction = this.getAuctionInitializer().cancelAuction(0);
    try
    {
      this.getService().start(auction.getId());
      fail("Should fail with wrong auction status");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertEquals("Wrong version", auction.getVersion(), this.getAuction(0).getVersion());
    }
  }

  /**
   * Test of end(String), of class AuctionAbstractInternalManager_.
   * @throws Bid4WinException Issue not expected during this test
   * @throws InterruptedException Issue not expected during this test
   */
  @Test
  public void testEnd_String() throws Bid4WinException, InterruptedException
  {
    AUCTION auction = this.getAuction(0);
    AUCTION result = this.getService().end(auction.getId());
    assertEquals("Wrong version", auction.getVersion(), this.getAuction(0).getVersion());
    assertFalse("Should not be ended", result.isEnded());

    auction = this.getAuctionInitializer().validateAuction(0);
    result = this.getService().end(auction.getId());
    assertEquals("Wrong version", auction.getVersion(), this.getAuction(0).getVersion());
    assertFalse("Should not be ended", result.isEnded());

    auction = this.getAuctionInitializer().startAuction(0);
    result = this.getService().end(auction.getId());
    assertEquals("Wrong version", auction.getVersion(), this.getAuction(0).getVersion());
    assertFalse("Should not be ended", result.isEnded());

    synchronized(auction)
    {
      auction.wait(auction.getSchedule().getEndCountdown(new Bid4WinDate()) + 100);
    }

    result = this.getService().end(auction.getId());
    assertEquals("Wrong version", auction.getVersion() + 1, this.getAuction(0).getVersion());
    assertTrue("Should be ended", result.isEnded());

    try
    {
      this.getService().end(auction.getId());
      fail("Should fail with wrong auction status");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertEquals("Wrong version", auction.getVersion() + 1, this.getAuction(0).getVersion());
    }
  }

  /**
   *
   * TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  @Test
  public void test_run_of_handler() throws Bid4WinException
  {
    AUCTION auction = this.getAuction(0);
    // Création d'un handler de vente aux enchères en construction
    HANDLER handler1 = this.createHandler(auction);
    this.waitFor(500);
    assertNull("Wrong handler", this.getService().getHandler(auction));
    assertTrue("Handler should be stopped", handler1.isEnded());
    // Tentative d'enchérissement sur une vente pas encore ouverte
    auction = this.getAuctionInitializer().validateAuction(0);
    this.connectAccount(0);
    this.updateRole(0, Role.USER);
    this.getAccountInitializer().addBundle(0, 10);
    try
    {
      this.getService().bid(auction.getId());
      fail("Should fail with waiting auction");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    // Création d'un handler de vente aux enchères validée
    handler1 = this.createHandler(auction);
    this.waitFor(100);
    assertTrue("Wrong handler", handler1 == this.getService().getHandler(auction));
    assertFalse("Handler should not be stopped", handler1.isEnded());
    // Création d'un second handler de vente aux enchères validée
    HANDLER handler2 = this.createHandler(auction);
    this.waitFor(100);
    assertTrue("Wrong handler", handler1 == this.getService().getHandler(auction));
    assertFalse("Handler should not be stopped", handler1.isEnded());
    assertFalse("Wrong handler", handler2 == this.getService().getHandler(auction));
    assertTrue("Handler should be stopped", handler2.isEnded());

    try
    {
      this.getService().bid(auction.getId());
      fail("Should fail with waiting auction");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }

    this.waitFor(auction.getSchedule().getStartCountdown(new Bid4WinDate()) + 1000);
    this.getService().bid(auction.getId());

    auction = this.getAuction(0);
    assertEquals("Wrong auction status", Status.STARTED, auction.getStatus());
    this.waitFor(auction.getSchedule().getEndCountdown(new Bid4WinDate()) + 1000);
    auction = this.getAuction(0);
    assertFalse("Wrong auction status", auction.getStatus().belongsTo(Status.OPENED));
    assertNull("Wrong handler", this.getService().getHandler(auction));
    assertTrue("Handler should be stopped", handler1.isEnded());
  }

  /**
   *
   * TODO A COMMENTER
   * @param milliseconds TODO A COMMENTER
   */
  protected void waitFor(long milliseconds)
  {
    if(milliseconds > 0)
    {
      try
      {
        synchronized(this.timer)
        {
          this.timer.wait(milliseconds);
        }
      }
      catch(InterruptedException ex)
      {
        ex.printStackTrace();
      }
    }
  }

  /**
   *
   * TODO A COMMENTER
   * @param auction TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected HANDLER createHandler(AUCTION auction)
  {
    return this.getService().createHandler(auction);
  }
  /**
   *
   * TODO A COMMENTER
   * @param index TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public String getAuctionId(int index)
  {
    return this.getAuctionInitializer().getId(index);
  }
  /**
   *
   * TODO A COMMENTER
   * @param index TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public AUCTION getAuction(int index) throws Bid4WinException
  {
    return this.getAuctionInitializer().getEntity(index);
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinServiceTester#getService()
   */
  @Override
  public abstract AuctionAbstractInternalService_<AUCTION, BID, BID_HISTORY, SCHEDULE, TERMS, CANCEL_POLICY, INVOLVEMENT, HANDLER, ?> getService();
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDaoTester#getDao()
   */
  @Override
  protected abstract IAuctionAbstractDaoStub<AUCTION, BID, SCHEDULE, TERMS, CANCEL_POLICY> getDao();
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected abstract AuctionAbstractInitializer_<AUCTION, BID, SCHEDULE, TERMS, CANCEL_POLICY> getAuctionInitializer();

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.Bid4WinManagerTester#getSetupAccountNb()
   */
  @Override
  public int getSetupAccountNb()
  {
    return 3;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public int getSetupAuctionNb()
  {
    return 2;
  }
  /**
   * Test setup method
   * @throws Exception {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityDaoTester#setUp()
   */
  @Override
  @Before
  public void setUp() throws Exception
  {
    super.setUp();
    // Ajoute les ventes aux enchères utilisées pour les tests
    this.getAuctionInitializer().setUp(this.getSetupAuctionNb());
  }
  /**
   * Test teardown method
   * @throws Exception {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityDaoTester#tearDown()
   */
  @Override
  @After
  public void tearDown() throws Exception
  {
    // Supprime les compte utilisateurs utilisés pour les tests
    this.getAccountInitializer().tearDown();
    // Supprime les ventes aux enchères utilisées pour les tests
    this.getAuctionInitializer().tearDown();
    super.tearDown();
  }
}
