package com.bid4win.persistence.dao.account.credit.auction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.persistence.dao.account.credit.CreditBundleDaoStub;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.account.credit.CreditBundle;
import com.bid4win.persistence.entity.account.credit.CreditUsage;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementAuction;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementAuctionHistory;
import com.bid4win.persistence.entity.account.credit.collection.CreditMap;
import com.bid4win.persistence.entity.auction.AuctionAbstract;
import com.bid4win.persistence.entity.auction.BidAbstract;
import com.bid4win.persistence.entity.auction.BidHistory;
import com.bid4win.persistence.entity.auction.CancelPolicyAbstract;
import com.bid4win.persistence.entity.auction.ScheduleAbstract;
import com.bid4win.persistence.entity.auction.TermsAbstract;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <INVOLVEMENT> TODO A COMMENTER<BR>
 * @param <USAGE> TODO A COMMENTER<BR>
 * @param <HISTORY> TODO A COMMENTER<BR>
 * @param <AUCTION> TODO A COMMENTER<BR>
 * @param <BID> TODO A COMMENTER<BR>
 * @param <BID_HISTORY> TODO A COMMENTER<BR>
 * @param <SCHEDULE> TODO A COMMENTER<BR>
 * @param <TERMS> TODO A COMMENTER<BR>
 * @param <CANCEL_POLICY> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class CreditInvolvementAuctionDaoTester<INVOLVEMENT extends CreditInvolvementAuction<INVOLVEMENT, USAGE, AUCTION, HISTORY>,
                                                        USAGE extends CreditUsage<USAGE, INVOLVEMENT>,
                                                        HISTORY extends CreditInvolvementAuctionHistory<HISTORY, ?, AUCTION, INVOLVEMENT>,
                                                        AUCTION extends AuctionAbstract<AUCTION, BID, SCHEDULE, TERMS, CANCEL_POLICY>,
                                                        BID extends BidAbstract<BID, AUCTION, BID_HISTORY>,
                                                        BID_HISTORY extends BidHistory<BID_HISTORY, AUCTION, BID>,
                                                        SCHEDULE extends ScheduleAbstract<SCHEDULE>,
                                                        TERMS extends TermsAbstract<TERMS>,
                                                        CANCEL_POLICY extends CancelPolicyAbstract<CANCEL_POLICY>>
       extends CreditInvolvementAuctionAbstractDaoTester<INVOLVEMENT, USAGE, CreditBundle, HISTORY, AUCTION, BID, BID_HISTORY, SCHEDULE, TERMS, CANCEL_POLICY>
{
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("CreditBundleDaoStub")
  private CreditBundleDaoStub bundleDao;


  /**
   * Test of add(ENTITY), of class CreditInvolvementAuctionDao.
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionBasedEntityDaoTester#testAdd_ENTITY()
   */
  @Override
  @Test
  public void testAdd_ENTITY() throws Bid4WinException
  {
    super.testAdd_ENTITY();
    Account account1 = this.getAccount(0);
    Bid4WinSet<INVOLVEMENT> set1 = this.findListByAccount(account1);
    for(INVOLVEMENT involvement : set1)
    {
      assertTrue("Wrong involvement",
                 involvement.identical(this.getInvolvement(account1, involvement.getAuction()),
                                       new Bid4WinList<Bid4WinRelationNode>()));
    }

    Account account2 = this.getAccount(1);
    Bid4WinSet<INVOLVEMENT> set2 = this.findListByAccount(account2);
    for(INVOLVEMENT involvement : set2)
    {
      assertTrue("Wrong involvement",
                 involvement.identical(this.getInvolvement(account2, involvement.getAuction()),
                                       new Bid4WinList<Bid4WinRelationNode>()));
    }
  }
  /**
   * Test of historize(AUCTION), of class CreditInvolvementAuctionDao.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testHistorize_AUCTION() throws Bid4WinException
  {
    // Création de la situation de départ
    CreditMap usageMap1 = new CreditMap();
    usageMap1.put(this.addBundle(0, 10), 1);
    usageMap1.put(this.addBundle(0, 10), 2);
    CreditMap usageMap2 = new CreditMap();
    usageMap2.put(this.addBundle(1, 10), 1);
    usageMap2.put(this.addBundle(1, 10), 2);

    Account account1 = this.getAccount(0);
    INVOLVEMENT involvement1 = this.create(account1);
    involvement1.addUsage(usageMap1);
    this.add(involvement1);
    account1 = this.getAccount(0);
    INVOLVEMENT involvement2 = this.create(account1);
    involvement2.addUsage(usageMap1);
    this.add(involvement2);

    Account account2 = this.getAccount(1);
    INVOLVEMENT involvement3 = this.create(account2);
    involvement3.addUsage(usageMap2);
    this.add(involvement3);

    try
    {
      this.getDao().historize(this.getAuction(0));
      fail("Should fail with unhistorized bundle");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      INVOLVEMENT result = this.getDao().getById(involvement1.getId());
      assertTrue("Wrong involvement",
                 involvement1.identical(result, new Bid4WinList<Bid4WinRelationNode>()));
      assertFalse("Should not be historized", result.isHistorized());
      result = this.getDao().getById(involvement2.getId());
      assertTrue("Wrong involvement",
                 involvement2.identical(result, new Bid4WinList<Bid4WinRelationNode>()));
      assertFalse("Should not be historized", result.isHistorized());
      result = this.getDao().getById(involvement3.getId());
      assertTrue("Wrong involvement",
                 involvement3.identical(result, new Bid4WinList<Bid4WinRelationNode>()));
      assertFalse("Should not be historized", result.isHistorized());
    }
    // Historise les lots du premier compte utilisateur
    for(CreditBundle bundle : usageMap1.keySet())
    {
      this.getBundleDao().historize(bundle);
    }

    this.getDao().historize(this.getAuction(1));
    INVOLVEMENT result = this.getDao().getById(involvement1.getId());
    assertTrue("Wrong involvement",
               involvement1.identical(result, new Bid4WinList<Bid4WinRelationNode>()));
    assertFalse("Should not be historized", result.isHistorized());
    result = this.getDao().getById(involvement2.getId());
    assertTrue("Should be historized", result.isHistorized());
    HISTORY history = result.getHistory();
    assertNotNull("Wrong involvement history", history);
    assertEquals("Wrong account id", result.getAccount().getId(), history.getAccount().getId());
    assertEquals("Wrong auction id", result.getAuctionId(), history.getAuctionId());
    assertEquals("Wrong credit nb", result.getUsedNb(), history.getUsedNb());
    for(CreditBundle bundle : usageMap1.keySet())
    {
      assertNotNull("Wrong usage history", history.getUsage(bundle.getHistory()));
      assertEquals("Wrong credit nb",
                   result.getUsage(bundle).getUsedNb(), history.getUsage(bundle.getHistory()).getUsedNb());
    }
    result = this.getDao().getById(involvement3.getId());
    assertTrue("Wrong involvement",
               involvement3.identical(result, new Bid4WinList<Bid4WinRelationNode>()));
    assertFalse("Should not be historized", result.isHistorized());

    try
    {
      this.getDao().historize(this.getAuction(0));
      fail("Should fail with unhistorized bundle");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      result = this.getDao().getById(involvement1.getId());
      assertTrue("Wrong involvement",
                 involvement1.identical(result, new Bid4WinList<Bid4WinRelationNode>()));
      assertFalse("Should not be historized", result.isHistorized());
      result = this.getDao().getById(involvement3.getId());
      assertTrue("Wrong involvement",
                 involvement3.identical(result, new Bid4WinList<Bid4WinRelationNode>()));
      assertFalse("Should not be historized", result.isHistorized());
    }
    // Historise les lots du deuxième compte utilisateur
    for(CreditBundle bundle : usageMap2.keySet())
    {
      this.getBundleDao().historize(bundle);
    }

    this.getDao().historize(this.getAuction(0));

    result = this.getDao().getById(involvement1.getId());
    assertTrue("Should be historized", result.isHistorized());
    history = result.getHistory();
    assertNotNull("Wrong involvement history", history);
    assertEquals("Wrong account id", result.getAccount().getId(), history.getAccount().getId());
    assertEquals("Wrong auction id", result.getAuctionId(), history.getAuctionId());
    assertEquals("Wrong credit nb", result.getUsedNb(), history.getUsedNb());
    for(CreditBundle bundle : usageMap1.keySet())
    {
      assertNotNull("Wrong usage history", history.getUsage(bundle.getHistory()));
      assertEquals("Wrong credit nb",
                   result.getUsage(bundle).getUsedNb(), history.getUsage(bundle.getHistory()).getUsedNb());
    }
    result = this.getDao().getById(involvement2.getId());
    assertTrue("Should be historized", result.isHistorized());
    history = result.getHistory();
    assertNotNull("Wrong involvement history", history);
    assertEquals("Wrong account id", result.getAccount().getId(), history.getAccount().getId());
    assertEquals("Wrong auction id", result.getAuctionId(), history.getAuctionId());
    assertEquals("Wrong credit nb", result.getUsedNb(), history.getUsedNb());
    for(CreditBundle bundle : usageMap1.keySet())
    {
      assertNotNull("Wrong usage history", history.getUsage(bundle.getHistory()));
      assertEquals("Wrong credit nb",
                   result.getUsage(bundle).getUsedNb(), history.getUsage(bundle.getHistory()).getUsedNb());
    }
    result = this.getDao().getById(involvement3.getId());
    assertTrue("Should be historized", result.isHistorized());
    history = result.getHistory();
    assertNotNull("Wrong involvement history", history);
    assertEquals("Wrong account id", result.getAccount().getId(), history.getAccount().getId());
    assertEquals("Wrong auction id", result.getAuctionId(), history.getAuctionId());
    assertEquals("Wrong credit nb", result.getUsedNb(), history.getUsedNb());
    for(CreditBundle bundle : usageMap2.keySet())
    {
      assertNotNull("Wrong usage history", history.getUsage(bundle.getHistory()));
      assertEquals("Wrong credit nb",
                   result.getUsage(bundle).getUsedNb(), history.getUsage(bundle.getHistory()).getUsedNb());
    }
  }
  /**
   *
   * TODO A COMMENTER
   * @param accountIndex {@inheritDoc}
   * @param nb {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.persistence.dao.account.credit.auction.CreditInvolvementAuctionAbstractDaoTester#addBundle(int, int)
   */
  @Override
  protected CreditBundle addBundle(int accountIndex, int nb) throws Bid4WinException
  {
    return this.getAccountInitializer().addBundle(accountIndex, nb);
  }


  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityDaoTester#isAccountUpdatedAfterAdd()
   */
  @Override
  public boolean isAccountUpdatedAfterAdd()
  {
    return true;
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityMultipleDaoTester#getDao()
   */
  @Override
  protected abstract ICreditInvolvementAuctionDaoStub<INVOLVEMENT, USAGE, AUCTION, HISTORY> getDao();
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected CreditBundleDaoStub getBundleDao()
  {
    return this.bundleDao;
  }
}
