package com.bid4win.persistence.dao.auction;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.auction.Auction;
import com.bid4win.persistence.entity.auction.Bid;
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
 * @param <SCHEDULE> TODO A COMMENTER<BR>
 * @param <TERMS> TODO A COMMENTER<BR>
 * @param <CANCEL_POLICY> TODO A COMMENTER<BR>
 * @param <BOT> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class AuctionInitializer_<AUCTION extends Auction<AUCTION, BID, SCHEDULE, TERMS, CANCEL_POLICY>,
                                          BID extends Bid<BID, AUCTION, ?>,
                                          SCHEDULE extends Schedule<SCHEDULE>,
                                          TERMS extends Terms<TERMS>,
                                          CANCEL_POLICY extends CancelPolicy<CANCEL_POLICY>,
                                          BOT extends Bot<BOT, AUCTION, BID>>
       extends AuctionAbstractInitializer_<AUCTION, BID, SCHEDULE, TERMS, CANCEL_POLICY>
{
  /**
   *
   * TODO A COMMENTER
   * @param botId TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public BOT getBot(long botId)  throws Bid4WinException
  {
    return this.getBotDao().getById(botId);
  }
  /**
   *
   * TODO A COMMENTER
   * @param auctionIndex TODO A COMMENTER
   * @param account TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public BOT addBot(int auctionIndex, Account account) throws Bid4WinException
  {
    return this.getBotDao().add(this.createBot(this.getEntity(auctionIndex), account));
  }
  /**
   *
   * TODO A COMMENTER
   * @param auction TODO A COMMENTER
   * @param account TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  protected abstract BOT createBot(AUCTION auction, Account account) throws UserException;

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionAbstractInitializer_#getBidDao()
   */
  @Override
  protected abstract IBidDaoStub<BID, AUCTION, ?> getBidDao();
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected abstract IBotDaoStub<BOT, AUCTION, BID> getBotDao();
}
