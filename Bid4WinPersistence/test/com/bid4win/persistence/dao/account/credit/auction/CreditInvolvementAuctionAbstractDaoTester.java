package com.bid4win.persistence.dao.account.credit.auction;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.persistence.dao.auction.AuctionBasedEntityDaoTester;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.account.credit.CreditBundleAbstract;
import com.bid4win.persistence.entity.account.credit.CreditBundleHistory;
import com.bid4win.persistence.entity.account.credit.CreditUsageAbstract;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementAuctionAbstract;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementAuctionAbstract_Relations;
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
 * @param <BUNDLE> TODO A COMMENTER<BR>
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
public abstract class CreditInvolvementAuctionAbstractDaoTester<INVOLVEMENT extends CreditInvolvementAuctionAbstract<INVOLVEMENT, USAGE, BUNDLE, AUCTION, HISTORY>,
                                                                USAGE extends CreditUsageAbstract<USAGE, BUNDLE, INVOLVEMENT>,
                                                                BUNDLE extends CreditBundleAbstract<BUNDLE>,
                                                                HISTORY extends CreditInvolvementAuctionAbstract<HISTORY, ?, CreditBundleHistory, AUCTION, ?>,
                                                                AUCTION extends AuctionAbstract<AUCTION, BID, SCHEDULE, TERMS, CANCEL_POLICY>,
                                                                BID extends BidAbstract<BID, AUCTION, BID_HISTORY>,
                                                                BID_HISTORY extends BidHistory<BID_HISTORY, AUCTION, BID>,
                                                                SCHEDULE extends ScheduleAbstract<SCHEDULE>,
                                                                TERMS extends TermsAbstract<TERMS>,
                                                                CANCEL_POLICY extends CancelPolicyAbstract<CANCEL_POLICY>>
       extends AuctionBasedEntityDaoTester<INVOLVEMENT, Long, AUCTION, BID, BID_HISTORY, SCHEDULE, TERMS, CANCEL_POLICY>
{
  /**
   *
   * TODO A COMMENTER
   * @param auction {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionBasedEntityDaoTester#findListByAuction(com.bid4win.persistence.entity.auction.AuctionAbstract)
   */
  @Override
  protected Bid4WinSet<INVOLVEMENT> findListByAuction(AUCTION auction) throws PersistenceException
  {
    return new Bid4WinSet<INVOLVEMENT>(this.getDao().findListByAuction(auction));
  }
  /**
   *
   * TODO A COMMENTER
   * @param auctionId {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionBasedEntityDaoTester#findListByAuctionId(java.lang.String)
   */
  @Override
  protected Bid4WinSet<INVOLVEMENT> findListByAuctionId(String auctionId) throws PersistenceException
  {
    return new Bid4WinSet<INVOLVEMENT>(this.getDao().findListByAuctionId(auctionId));
  }

  /**
   * Test of add(ENTITY), of class CreditInvolvementAuctionAbstractDao.
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionBasedEntityDaoTester#testAdd_ENTITY()
   */
  @Override
  @Test
  public void testAdd_ENTITY() throws Bid4WinException
  {
    super.testAdd_ENTITY();
    Bid4WinMap<BUNDLE, Integer> usageMap = new Bid4WinMap<BUNDLE, Integer>();
    usageMap.put(this.addBundle(1, 10), 1);
    usageMap.put(this.addBundle(1, 10), 2);

    Account account = this.getAccount(1);
    INVOLVEMENT involvement = this.create(account);
    Bid4WinSet<USAGE> usageSet = involvement.addUsage(usageMap);

    this.add(involvement);

    INVOLVEMENT result = this.getById(involvement.getId());
    assertTrue("Wrong result", involvement.identical(result, new Bid4WinList<Bid4WinRelationNode>()));
    assertTrue("Wrong result", involvement.same(result));
    for(USAGE usage : usageSet)
    {
      assertTrue("Wrong usage", usage.same(result.getUsage(usage.getBundle())));
    }
  }

  /**
   *
   * TODO A COMMENTER
   * @param entity {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionBasedEntityDaoTester#getAuction(com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultiple)
   */
  @Override
  protected AUCTION getAuction(INVOLVEMENT entity)
  {
    return entity.getAuction();
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionBasedEntityDaoTester#getAuctionRelation()
   */
  @Override
  protected Bid4WinRelationNode getAuctionRelation()
  {
    return CreditInvolvementAuctionAbstract_Relations.NODE_AUCTION;
  }
  /**
   *
   * TODO A COMMENTER
   * @param account {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionBasedTester#create(com.bid4win.persistence.entity.account.Account)
   */
  @Override
  protected INVOLVEMENT create(Account account) throws Bid4WinException
  {
    AUCTION auction = this.getAuction(0);
    if(this.getInvolvement(account, auction) != null)
    {
      auction = this.getAuction(1);
    }
    return this.create(account, auction);
  }
  /**
   *
   * TODO A COMMENTER
   * @param account TODO A COMMENTER
   * @param auction TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected abstract INVOLVEMENT getInvolvement(Account account, AUCTION auction);
  /**
   *
   * TODO A COMMENTER
   * @param accountIndex TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  protected abstract BUNDLE addBundle(int accountIndex, int nb) throws Bid4WinException;

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityMultipleDaoTester#getDao()
   */
  @Override
  protected abstract ICreditInvolvementAuctionAbstractDaoStub<INVOLVEMENT, USAGE, BUNDLE, AUCTION, HISTORY> getDao();
}
