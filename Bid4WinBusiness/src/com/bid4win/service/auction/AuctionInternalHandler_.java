package com.bid4win.service.auction;

import com.bid4win.persistence.entity.auction.Auction;
import com.bid4win.persistence.entity.auction.Schedule;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <CLASS> TODO A COMMENTER<BR>
 * @param <AUCTION> TODO A COMMENTER<BR>
 * @param <SCHEDULE> TODO A COMMENTER<BR>
 * @param <SERVICE> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class AuctionInternalHandler_<CLASS extends AuctionInternalHandler_<CLASS, AUCTION, SCHEDULE, SERVICE>,
                                              AUCTION extends Auction<AUCTION, ?, SCHEDULE, ?, ?>,
                                              SCHEDULE extends Schedule<SCHEDULE>,
                                              SERVICE extends AuctionInternalService_<AUCTION, ?, ?, SCHEDULE, ?, ?, ?, ?, CLASS, ?>>
       extends AuctionAbstractInternalHandler_<CLASS, AUCTION, SCHEDULE, SERVICE>
{
  /**
   * Constructeur du handler qui le d�marrera
   * @param auction Vente aux ench�res du handler
   * @param service Service de gestion de la vente aux ench�res du handler
   */
  protected AuctionInternalHandler_(AUCTION auction, SERVICE service)
  {
    super(auction, service);
  }
}
