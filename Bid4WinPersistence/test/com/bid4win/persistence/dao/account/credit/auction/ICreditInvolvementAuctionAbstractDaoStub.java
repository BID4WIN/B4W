package com.bid4win.persistence.dao.account.credit.auction;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.dao.account.IAccountBasedEntityMultipleDaoStub;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.account.credit.CreditBundleAbstract;
import com.bid4win.persistence.entity.account.credit.CreditBundleHistory;
import com.bid4win.persistence.entity.account.credit.CreditUsageAbstract;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementAuctionAbstract;
import com.bid4win.persistence.entity.auction.AuctionAbstract;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <INVOLVEMENT> TODO A COMMENTER<BR>
 * @param <USAGE> TODO A COMMENTER<BR>
 * @param <BUNDLE> TODO A COMMENTER<BR>
 * @param <AUCTION> TODO A COMMENTER<BR>
 * @param <HISTORY> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public interface ICreditInvolvementAuctionAbstractDaoStub<INVOLVEMENT extends CreditInvolvementAuctionAbstract<INVOLVEMENT, USAGE, BUNDLE, AUCTION, HISTORY>,
                                                          USAGE extends CreditUsageAbstract<USAGE, BUNDLE, INVOLVEMENT>,
                                                          BUNDLE extends CreditBundleAbstract<BUNDLE>,
                                                          AUCTION extends AuctionAbstract<AUCTION, ?, ?, ?, ?>,
                                                          HISTORY extends CreditInvolvementAuctionAbstract<HISTORY, ?, CreditBundleHistory, AUCTION, ?>>
       extends IAccountBasedEntityMultipleDaoStub<INVOLVEMENT, Long, Account>
{
  /**
   *
   * TODO A COMMENTER
   * @param auctionId TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  public Bid4WinList<INVOLVEMENT> findListByAuctionId(String auctionId) throws PersistenceException;
  /**
   *
   * TODO A COMMENTER
   * @param auction TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  public Bid4WinList<INVOLVEMENT> findListByAuction(AUCTION auction) throws PersistenceException;
}
