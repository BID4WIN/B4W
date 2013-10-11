package com.bid4win.service.auction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Map.Entry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.account.security.Role;
import com.bid4win.persistence.dao.account.credit.auction.ICreditInvolvementAuctionDaoStub;
import com.bid4win.persistence.dao.auction.AuctionAbstractInitializer_;
import com.bid4win.persistence.dao.auction.IAuctionAbstractDaoStub;
import com.bid4win.persistence.dao.auction.IBidAbstractDaoStub;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.account.credit.CreditBundle;
import com.bid4win.persistence.entity.account.credit.CreditOrigin;
import com.bid4win.persistence.entity.account.credit.CreditUsage;
import com.bid4win.persistence.entity.account.credit.Origin;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementAuction;
import com.bid4win.persistence.entity.account.credit.collection.CreditMap;
import com.bid4win.persistence.entity.auction.AuctionAbstract;
import com.bid4win.persistence.entity.auction.BidAbstract;
import com.bid4win.persistence.entity.auction.BidHistory;
import com.bid4win.persistence.entity.auction.CancelPolicyAbstract;
import com.bid4win.persistence.entity.auction.ScheduleAbstract;
import com.bid4win.persistence.entity.auction.Status;
import com.bid4win.persistence.entity.auction.TermsAbstract;
import com.bid4win.persistence.entity.price.Amount;
import com.bid4win.persistence.entity.product.Product;
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
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class AuctionAbstractServiceTester<AUCTION extends AuctionAbstract<AUCTION, BID, SCHEDULE, TERMS, CANCEL_POLICY>,
                                                   BID extends BidAbstract<BID, AUCTION, BID_HISTORY>,
                                                   BID_HISTORY extends BidHistory<BID_HISTORY, AUCTION, BID>,
                                                   SCHEDULE extends ScheduleAbstract<SCHEDULE>,
                                                   TERMS extends TermsAbstract<TERMS>,
                                                   CANCEL_POLICY extends CancelPolicyAbstract<CANCEL_POLICY>,
                                                   INVOLVEMENT extends CreditInvolvementAuction<INVOLVEMENT, USAGE, AUCTION, ?>,
                                                   USAGE extends CreditUsage<USAGE, INVOLVEMENT>>
       extends Bid4WinServiceTester<AUCTION, String>
{
  /** TODO A COMMENTER */
  private Object timer = new Object();

  /**
   * Test of bid(String), of class AuctionAbstractManager_.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testBid_String() throws Bid4WinException
  {
    this.updateRole(0, Role.USER);
    this.getAccountInitializer().addBundle(0, 1).getAccount();
    Account account0 = this.getAccountInitializer().addBundle(0, 5).getAccount();
    INVOLVEMENT involvement0 = this.getInvolvement(0, this.getAuction(0));

    this.updateRole(1, Role.USER);
    this.getAccountInitializer().addBundle(1, new CreditOrigin(Origin.SPONSORSHIP, "12345678910"), 0, 3).getAccount();
    Account account1 = this.getAccountInitializer().addBundle(1, 1).getAccount();
    INVOLVEMENT involvement1 = this.getInvolvement(1, this.getAuction(0));

    this.updateRole(2, Role.USER);

    int bidNb = 0;
    int involvedCreditNb = this.getAuction(0).getInvolvedCreditNb();
    Amount involvementValue = this.getAuction(0).getInvolvementValue();

    try
    {
      this.bid(0, 1);
      fail("Should fail with wrong auction status");
    }
    catch(UserException ex)
    {
      assertEquals("Wrong bid nb", 0, this.getAuction(0).getBidNb());
    }

    try
    {
      this.getAuctionInitializer().validateAuction(0);
      this.bid(0, 1);
      fail("Should fail with wrong auction status");
    }
    catch(UserException ex)
    {
      assertEquals("Wrong bid nb", 0, this.getAuction(0).getBidNb());
    }

    this.getAuctionInitializer().startAuction(0);
    this.checkRoleRestriction(Role.USER, "bid", String.class);

    AUCTION auction = this.bid(0, 0);
    CreditMap usedCredit = account0.useCredit(auction.getTerms().getCreditNbPerBid());
    bidNb++;
    involvedCreditNb += usedCredit.getTotalNb();
    involvementValue = involvementValue.add(usedCredit.getTotalValue());
    assertTrue("Wrong auction", auction.identical(this.getAuction(0), new Bid4WinList<Bid4WinRelationNode>()));
    assertEquals("Wrong bid nb", bidNb, auction.getBidNb());
    assertEquals("Wrong involved credit nb", involvedCreditNb, auction.getInvolvedCreditNb());
    assertEquals("Wrong involvement value", involvementValue, auction.getInvolvementValue());
    assertNull("Wrong account", auction.getAccount());

    Bid4WinList<BID> bidList = this.getBidDao().findListByAuction(auction);
    assertEquals("Wrong bid nb", bidNb, bidList.size());
    assertEquals("Wrong bid account", this.getAccountId(0), bidList.getLast().getAccount().getId());
    INVOLVEMENT involvement = this.getInvolvementDao().getById(this.getInvolvement(0, auction).getId());
    assertEquals("Wrong involvement auction", auction.getId(), involvement.getAuctionId());
    involvement0 = this.checkInvolvement(involvement0, involvement, usedCredit);

    auction = this.bid(1, 0);
    usedCredit = account1.useCredit(auction.getTerms().getCreditNbPerBid());
    bidNb++;
    involvedCreditNb += usedCredit.getTotalNb();
    involvementValue = involvementValue.add(usedCredit.getTotalValue());
    assertTrue("Wrong auction", auction.identical(this.getAuction(0), new Bid4WinList<Bid4WinRelationNode>()));
    assertEquals("Wrong bid nb", bidNb, auction.getBidNb());
    assertEquals("Wrong involved credit nb", involvedCreditNb, auction.getInvolvedCreditNb());
    assertEquals("Wrong involvement value", involvementValue, auction.getInvolvementValue());
    assertNull("Wrong account", auction.getAccount());

    bidList = this.getBidDao().findListByAuction(auction).sort();
    assertEquals("Wrong bid nb", bidNb, bidList.size());
    assertEquals("Wrong bid account", this.getAccountId(1), bidList.getFirst().getAccount().getId());
    involvement = this.getInvolvementDao().getById(this.getInvolvement(1, auction).getId());
    assertEquals("Wrong involvement auction", auction.getId(), involvement.getAuctionId());
    involvement1 = this.checkInvolvement(involvement1, involvement, usedCredit);

    auction = this.bid(0, 0);
    usedCredit = account0.useCredit(auction.getTerms().getCreditNbPerBid());
    bidNb++;
    involvedCreditNb += usedCredit.getTotalNb();
    involvementValue = involvementValue.add(usedCredit.getTotalValue());
    assertTrue("Wrong auction", auction.identical(this.getAuction(0), new Bid4WinList<Bid4WinRelationNode>()));
    assertEquals("Wrong bid nb", bidNb, auction.getBidNb());
    assertEquals("Wrong involved credit nb", involvedCreditNb, auction.getInvolvedCreditNb());
    assertEquals("Wrong involvement value", involvementValue, auction.getInvolvementValue());
    assertNull("Wrong account", auction.getAccount());

    bidList = this.getBidDao().findListByAuction(auction).sort();
    assertEquals("Wrong bid nb", bidNb, bidList.size());
    assertEquals("Wrong bid account", this.getAccountId(0), bidList.getFirst().getAccount().getId());
    involvement = this.getInvolvementDao().getById(this.getInvolvement(0, auction).getId());
    assertEquals("Wrong involvement auction", auction.getId(), involvement.getAuctionId());
    involvement0 = this.checkInvolvement(involvement0, involvement, usedCredit);

    auction = this.bid(1, 0);
    usedCredit = account1.useCredit(auction.getTerms().getCreditNbPerBid());
    bidNb++;
    involvedCreditNb += usedCredit.getTotalNb();
    involvementValue = involvementValue.add(usedCredit.getTotalValue());
    assertTrue("Wrong auction", auction.identical(this.getAuction(0), new Bid4WinList<Bid4WinRelationNode>()));
    assertEquals("Wrong bid nb", bidNb, auction.getBidNb());
    assertEquals("Wrong involved credit nb", involvedCreditNb, auction.getInvolvedCreditNb());
    assertEquals("Wrong involvement value", involvementValue, auction.getInvolvementValue());
    assertNull("Wrong account", auction.getAccount());

    bidList = this.getBidDao().findListByAuction(auction).sort();
    assertEquals("Wrong bid nb", bidNb, bidList.size());
    assertEquals("Wrong bid account", this.getAccountId(1), bidList.getFirst().getAccount().getId());
    involvement = this.getInvolvementDao().getById(this.getInvolvement(1, auction).getId());
    assertEquals("Wrong involvement auction", auction.getId(), involvement.getAuctionId());
    involvement1 = this.checkInvolvement(involvement1, involvement, usedCredit);

    try
    {
      this.bid(2, 0);
      fail("Should fail with not enought credit");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      auction = this.getAuction(0);
      assertTrue("Wrong auction", auction.identical(this.getAuction(0), new Bid4WinList<Bid4WinRelationNode>()));
      assertEquals("Wrong bid nb", bidNb, auction.getBidNb());
      assertEquals("Wrong involved credit nb", involvedCreditNb, auction.getInvolvedCreditNb());
      assertEquals("Wrong involvement value", involvementValue, auction.getInvolvementValue());
      assertNull("Wrong account", auction.getAccount());
    }

    auction = this.getAuctionInitializer().closeAuction(0);
    Account winner = auction.getAccount();
    try
    {
      this.bid(0, 0);
      fail("Should fail with wrong auction status");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      auction = this.getAuction(0);
      assertTrue("Wrong auction", auction.identical(this.getAuction(0), new Bid4WinList<Bid4WinRelationNode>()));
      assertEquals("Wrong bid nb", bidNb, auction.getBidNb());
      assertEquals("Wrong involved credit nb", involvedCreditNb, auction.getInvolvedCreditNb());
      assertEquals("Wrong involvement value", involvementValue, auction.getInvolvementValue());
      assertEquals("Wrong account", winner, auction.getAccount());
    }
  }
  /**
   * Test of validateAuction(String), of class AuctionAbstractManager_.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testValidateAuction_String() throws Bid4WinException
  {
    this.checkRoleRestriction(this.getService().getAdminRole(), "validateAuction", String.class);
    AUCTION auction = this.getAuction(0);
    assertEquals("Wrong version", 0, auction.getVersion());
    assertEquals("Wrong auction status", Status.WORKING, auction.getStatus());
    assertNull("Wrong cancel policy", auction.getCancelPolicy());
    AUCTION result = this.getService().validateAuction(auction.getId());
    assertEquals("Wrong version", auction.getVersion() + 1, result.getVersion());
    assertEquals("Wrong auction status", Status.WAITING, result.getStatus());
    assertNotNull("Wrong cancel policy", result.getCancelPolicy());
    assertEquals("Wrong product price", auction.getProductPrice(), result.getProductPrice());

    Product product = this.getAuctionInitializer().getProductInitializer().getEntity(0);
    product.definePrice(this.getGenerator().createPrice(product.getPrice().getValue() + 1));
    this.getAuctionInitializer().getProductInitializer().update(product);

    result = this.getService().validateAuction(auction.getId());
    assertEquals("Wrong version", auction.getVersion() + 2, result.getVersion());
    assertEquals("Wrong auction status", Status.WAITING, result.getStatus());
    assertNotNull("Wrong cancel policy", result.getCancelPolicy());
    assertFalse("Wrong product price", auction.getProductPrice().equals(result.getProductPrice()));
    assertEquals("Wrong product price", product.getPrice().getAmount(), result.getProductPrice().getAmount());

    this.getAuctionInitializer().startAuction(0);
    try
    {
      this.getService().validateAuction(auction.getId());
      fail("Should fail with wrong auction status");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }

  /**
   *
   * TODO A COMMENTER
   * @param accountIndex TODO A COMMENTER
   * @param auctionIndex TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  protected AUCTION bid(int accountIndex, int auctionIndex) throws Bid4WinException
  {
    this.disconnectAccount();
    this.connectAccount(accountIndex);
    try
    {
      return this.getService().bid(this.getAuctionId(auctionIndex));
    }
    finally
    {
      this.disconnectAccount();
    }
  }
  /**
   *
   * TODO A COMMENTER
   * @param oldInvolvement TODO A COMMENTER
   * @param newInvolvement TODO A COMMENTER
   * @param usedCredit TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  protected INVOLVEMENT checkInvolvement(INVOLVEMENT oldInvolvement, INVOLVEMENT newInvolvement, CreditMap usedCredit)
            throws Bid4WinException
  {
    int involvedCreditNb = usedCredit.getTotalNb();
    if(oldInvolvement != null)
    {
      involvedCreditNb += oldInvolvement.getUsedNb();
    }
    assertEquals("Wrong used credit nb", involvedCreditNb, newInvolvement.getUsedNb());
    for(Entry<CreditBundle, Integer> entry : usedCredit.entrySet())
    {
      USAGE usage = newInvolvement.getUsage(entry.getKey());
      assertNotNull("Wrong usage", usage);
      int usedCreditNb = entry.getValue();
      if(oldInvolvement != null)
      {
        USAGE oldUsage = oldInvolvement.getUsage(entry.getKey());
        if(oldUsage != null)
        {
          usedCreditNb += oldUsage.getUsedNb();
        }
      }
      assertEquals("Wrong used credit nb", usedCreditNb, usage.getUsedNb());
    }
    return newInvolvement;
  }

  /**
   *
   * TODO A COMMENTER
   * @param accountIndex TODO A COMMENTER
   * @param auction TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  protected abstract INVOLVEMENT getInvolvement(int accountIndex, AUCTION auction)
            throws Bid4WinException;

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
  public abstract AuctionAbstractService_<AUCTION, BID, BID_HISTORY, SCHEDULE, TERMS, CANCEL_POLICY, INVOLVEMENT, ?> getService();
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
  protected abstract IBidAbstractDaoStub<BID, AUCTION, BID_HISTORY> getBidDao();
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected abstract ICreditInvolvementAuctionDaoStub<INVOLVEMENT, USAGE, AUCTION, ?> getInvolvementDao();
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
