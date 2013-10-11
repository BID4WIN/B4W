package com.bid4win.persistence.dao.auction;

import com.bid4win.persistence.entity.auction.Auction;
import com.bid4win.persistence.entity.auction.Bid;
import com.bid4win.persistence.entity.auction.BidHistory;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <BID> TODO A COMMENTER<BR>
 * @param <AUCTION> TODO A COMMENTER<BR>
 * @param <HISTORY> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public interface IBidDaoStub<BID extends Bid<BID, AUCTION, HISTORY>,
                             AUCTION extends Auction<AUCTION, BID, ?, ?, ?>,
                             HISTORY extends BidHistory<HISTORY, AUCTION, BID>>
       extends IBidAbstractDaoStub<BID, AUCTION, HISTORY>
{
  // Pas de définition spécifique
}
