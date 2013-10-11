package com.bid4win.persistence.dao.auction;

import com.bid4win.persistence.entity.auction.Auction;
import com.bid4win.persistence.entity.auction.Bid;
import com.bid4win.persistence.entity.auction.BidHistory;
import com.bid4win.persistence.entity.auction.CancelPolicy;
import com.bid4win.persistence.entity.auction.Schedule;
import com.bid4win.persistence.entity.auction.Terms;

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
public abstract class BidDaoTester<BID extends Bid<BID, AUCTION, HISTORY>,
                                   AUCTION extends Auction<AUCTION, BID, SCHEDULE, TERMS, CANCEL_POLICY>,
                                   HISTORY extends BidHistory<HISTORY, AUCTION, BID>,
                                   SCHEDULE extends Schedule<SCHEDULE>,
                                   TERMS extends Terms<TERMS>,
                                   CANCEL_POLICY extends CancelPolicy<CANCEL_POLICY>>
       extends BidAbstractDaoTester<BID, AUCTION, HISTORY, SCHEDULE, TERMS, CANCEL_POLICY>
{
  /**
   * Permet de préciser le DAO à tester
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.BidAbstractDaoTester#getDao()
   */
  @Override
  protected abstract IBidDaoStub<BID, AUCTION, HISTORY> getDao();

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionBasedEntityDaoTester#isAuctionUpdatedAfterAdd()
   */
  @Override
  public boolean isAuctionUpdatedAfterAdd()
  {
    return true;
  }
}
