package com.bid4win.persistence.dao.auction;

import com.bid4win.persistence.entity.auction.Auction;
import com.bid4win.persistence.entity.auction.Bid;
import com.bid4win.persistence.entity.auction.CancelPolicy;
import com.bid4win.persistence.entity.auction.Schedule;
import com.bid4win.persistence.entity.auction.Terms;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <AUCTION> TODO A COMMENTER<BR>
 * @param <BID> TODO A COMMENTER<BR>
 * @param <SCHEDULE> TODO A COMMENTER<BR>
 * @param <TERMS> TODO A COMMENTER<BR>
 * @param <CANCEL_POLICY> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public interface IAuctionDaoStub<AUCTION extends Auction<AUCTION, BID, SCHEDULE, TERMS, CANCEL_POLICY>,
                                 BID extends Bid<BID, AUCTION, ?>,
                                 SCHEDULE extends Schedule<SCHEDULE>,
                                 TERMS extends Terms<TERMS>,
                                 CANCEL_POLICY extends CancelPolicy<CANCEL_POLICY>>
       extends IAuctionAbstractDaoStub<AUCTION, BID, SCHEDULE, TERMS, CANCEL_POLICY>
{
  // Pas de définition spécifique
}
