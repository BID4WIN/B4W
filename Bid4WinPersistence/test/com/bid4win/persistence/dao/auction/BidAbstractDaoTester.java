package com.bid4win.persistence.dao.auction;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.auction.AuctionAbstract;
import com.bid4win.persistence.entity.auction.BidAbstract;
import com.bid4win.persistence.entity.auction.BidAbstract_Relations;
import com.bid4win.persistence.entity.auction.BidHistory;
import com.bid4win.persistence.entity.auction.CancelPolicyAbstract;
import com.bid4win.persistence.entity.auction.ScheduleAbstract;
import com.bid4win.persistence.entity.auction.TermsAbstract;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <BID> TODO A COMMENTER<BR>
 * @param <AUCTION> TODO A COMMENTER<BR>
 * @param <HISTORY> TODO A COMMENTER<BR>
 * @param <SCHEDULE> TODO A COMMENTER<BR>
 * @param <TERMS> TODO A COMMENTER<BR>
 * @param <CANCEL_POLICY> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class BidAbstractDaoTester<BID extends BidAbstract<BID, AUCTION, HISTORY>,
                                           AUCTION extends AuctionAbstract<AUCTION, BID, SCHEDULE, TERMS, CANCEL_POLICY>,
                                           HISTORY extends BidHistory<HISTORY, AUCTION, BID>,
                                           SCHEDULE extends ScheduleAbstract<SCHEDULE>,
                                           TERMS extends TermsAbstract<TERMS>,
                                           CANCEL_POLICY extends CancelPolicyAbstract<CANCEL_POLICY>>
       extends AuctionBasedEntityDaoTester<BID, Long, AUCTION, BID, HISTORY, SCHEDULE, TERMS, CANCEL_POLICY>
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
  protected Bid4WinSet<BID> findListByAuction(AUCTION auction) throws PersistenceException
  {
    return new Bid4WinSet<BID>(this.getDao().findListByAuction(auction));
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
  protected Bid4WinSet<BID> findListByAuctionId(String auctionId) throws PersistenceException
  {
    return new Bid4WinSet<BID>(this.getDao().findListByAuctionId(auctionId));
  }

  /**
   *
   * TODO A COMMENTER
   * @param account {@inheritDoc}
   * @param auction {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionBasedTester#create(com.bid4win.persistence.entity.account.Account, com.bid4win.persistence.entity.auction.AuctionAbstract)
   */
  @Override
  protected final BID create(Account account, AUCTION auction) throws Bid4WinException
  {
    if(!auction.isStarted())
    {
      auction.validate(this.getAuctionInitializer().createCancelPolicy(),
                       this.getGenerator().createExchangeRates()).start();
    }
    return auction.addBid(account, new Bid4WinDate());
  }
  /**
   *
   * TODO A COMMENTER
   * @param entity {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionBasedEntityDaoTester#getAuction(com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultiple)
   */
  @Override
  protected AUCTION getAuction(BID entity)
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
    return BidAbstract_Relations.NODE_AUCTION;
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityMultipleDaoTester#getDao()
   */
  @Override
  protected abstract IBidAbstractDaoStub<BID, AUCTION, HISTORY> getDao();
}
