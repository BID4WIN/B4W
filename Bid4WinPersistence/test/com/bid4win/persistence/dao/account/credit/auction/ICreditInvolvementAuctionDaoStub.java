package com.bid4win.persistence.dao.account.credit.auction;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.persistence.entity.account.credit.CreditBundle;
import com.bid4win.persistence.entity.account.credit.CreditUsage;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementAuction;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementAuctionHistory;
import com.bid4win.persistence.entity.auction.AuctionAbstract;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <INVOLVEMENT> TODO A COMMENTER<BR>
 * @param <USAGE> TODO A COMMENTER<BR>
 * @param <AUCTION> TODO A COMMENTER<BR>
 * @param <HISTORY> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public interface ICreditInvolvementAuctionDaoStub<INVOLVEMENT extends CreditInvolvementAuction<INVOLVEMENT, USAGE, AUCTION, HISTORY>,
                                                  USAGE extends CreditUsage<USAGE, INVOLVEMENT>,
                                                  AUCTION extends AuctionAbstract<AUCTION, ?, ?, ?, ?>,
                                                  HISTORY extends CreditInvolvementAuctionHistory<HISTORY, ?, AUCTION, INVOLVEMENT>>
       extends ICreditInvolvementAuctionAbstractDaoStub<INVOLVEMENT, USAGE, CreditBundle, AUCTION, HISTORY>
{
  /**
   *
   * TODO A COMMENTER
   * @param auction TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  public Bid4WinList<INVOLVEMENT> historize(AUCTION auction) throws PersistenceException, UserException;
}
