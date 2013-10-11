package com.bid4win.persistence.dao.auction;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.persistence.entity.account.Account;
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
 * @param <AUCTION> TODO A COMMENTER<BR>
 * @param <BID> TODO A COMMENTER<BR>
 * @param <HISTORY> TODO A COMMENTER<BR>
 * @param <SCHEDULE> TODO A COMMENTER<BR>
 * @param <TERMS> TODO A COMMENTER<BR>
 * @param <CANCEL_POLICY> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class AuctionDaoTester<AUCTION extends Auction<AUCTION, BID, SCHEDULE, TERMS, CANCEL_POLICY>,
                                       BID extends Bid<BID, AUCTION, HISTORY>,
                                       HISTORY extends BidHistory<HISTORY, AUCTION, BID>,
                                       SCHEDULE extends Schedule<SCHEDULE>,
                                       TERMS extends Terms<TERMS>,
                                       CANCEL_POLICY extends CancelPolicy<CANCEL_POLICY>>
       extends AuctionAbstractDaoTester<AUCTION, BID, HISTORY, SCHEDULE, TERMS, CANCEL_POLICY>
{

  /**
   *
   * TODO A COMMENTER
   * @param account {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionAbstractDaoTester#create(com.bid4win.persistence.entity.account.Account)
   */
  @Override
  protected AUCTION create(Account account) throws Bid4WinException
  {
    AUCTION auction = super.create(account);
    return auction.getWinningBid().unlinkFromAuction();
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionAbstractDaoTester#getDao()
   */
  @Override
  protected abstract IAuctionDaoStub<AUCTION, BID, SCHEDULE, TERMS, CANCEL_POLICY> getDao();
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionAbstractDaoTester#getBidDao()
   */
  @Override
  protected abstract IBidDaoStub<BID, AUCTION, HISTORY> getBidDao();
}
