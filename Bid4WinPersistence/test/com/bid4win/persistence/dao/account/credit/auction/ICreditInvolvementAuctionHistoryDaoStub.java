package com.bid4win.persistence.dao.account.credit.auction;

import com.bid4win.persistence.entity.account.credit.CreditBundleHistory;
import com.bid4win.persistence.entity.account.credit.CreditUsageHistory;
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
 * @param <HISTORED> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public interface ICreditInvolvementAuctionHistoryDaoStub<INVOLVEMENT extends CreditInvolvementAuctionHistory<INVOLVEMENT, USAGE, AUCTION, HISTORED>,
                                                         USAGE extends CreditUsageHistory<USAGE, INVOLVEMENT>,
                                                         AUCTION extends AuctionAbstract<AUCTION, ?, ?, ?, ?>,
                                                         HISTORED extends CreditInvolvementAuction<HISTORED, ?, AUCTION, INVOLVEMENT>>
       extends ICreditInvolvementAuctionAbstractDaoStub<INVOLVEMENT, USAGE, CreditBundleHistory, AUCTION, INVOLVEMENT>
{
  //
}
