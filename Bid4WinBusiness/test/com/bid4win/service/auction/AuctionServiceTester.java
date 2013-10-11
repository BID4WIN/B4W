package com.bid4win.service.auction;

import com.bid4win.persistence.dao.auction.AuctionInitializer_;
import com.bid4win.persistence.dao.auction.IAuctionDaoStub;
import com.bid4win.persistence.dao.auction.IBidDaoStub;
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
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class AuctionServiceTester<AUCTION extends Auction<AUCTION, BID, SCHEDULE, TERMS, CANCEL_POLICY>,
                                           BID extends Bid<BID, AUCTION, BID_HISTORY>,
                                           BID_HISTORY extends BidHistory<BID_HISTORY, AUCTION, BID>,
                                           SCHEDULE extends Schedule<SCHEDULE>,
                                           TERMS extends Terms<TERMS>,
                                           CANCEL_POLICY extends CancelPolicy<CANCEL_POLICY>,
                                           INVOLVEMENT extends CreditInvolvementAuction<INVOLVEMENT, USAGE, AUCTION, ?>,
                                           USAGE extends CreditUsage<USAGE, INVOLVEMENT>,
                                           BOT extends Bot<BOT, AUCTION, BID>>
       extends AuctionAbstractServiceTester<AUCTION, BID, BID_HISTORY, SCHEDULE, TERMS, CANCEL_POLICY, INVOLVEMENT, USAGE>
{
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
  public abstract AuctionService_<AUCTION, BID, BID_HISTORY, SCHEDULE, TERMS, CANCEL_POLICY, INVOLVEMENT, BOT, ?> getService();
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.service.auction.AuctionAbstractServiceTester#getDao()
   */
  @Override
  protected abstract IAuctionDaoStub<AUCTION, BID, SCHEDULE, TERMS, CANCEL_POLICY> getDao();
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.service.auction.AuctionAbstractServiceTester#getBidDao()
   */
  @Override
  protected abstract IBidDaoStub<BID, AUCTION, BID_HISTORY> getBidDao();
}
