package com.bid4win.service.auction.normal;

import com.bid4win.persistence.entity.auction.normal.NormalAuction;
import com.bid4win.persistence.entity.auction.normal.NormalSchedule;
import com.bid4win.service.auction.AuctionInternalHandler_;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class NormalAuctionInternalHandler
       extends AuctionInternalHandler_<NormalAuctionInternalHandler, NormalAuction,
                                       NormalSchedule, NormalAuctionInternalService>
{
  /**
   *
   * TODO A COMMENTER
   * @param auction TODO A COMMENTER
   * @param service TODO A COMMENTER
   */
  protected NormalAuctionInternalHandler(NormalAuction auction, NormalAuctionInternalService service)
  {
    super(auction, service);
  }
}
