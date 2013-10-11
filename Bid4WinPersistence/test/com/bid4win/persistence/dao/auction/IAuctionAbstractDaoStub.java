package com.bid4win.persistence.dao.auction;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.persistence.dao.sale.ISaleDaoStub;
import com.bid4win.persistence.entity.auction.AuctionAbstract;
import com.bid4win.persistence.entity.auction.BidAbstract;
import com.bid4win.persistence.entity.auction.CancelPolicyAbstract;
import com.bid4win.persistence.entity.auction.ScheduleAbstract;
import com.bid4win.persistence.entity.auction.Status;
import com.bid4win.persistence.entity.auction.TermsAbstract;

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
public interface IAuctionAbstractDaoStub<AUCTION extends AuctionAbstract<AUCTION, BID, SCHEDULE, TERMS, CANCEL_POLICY>,
                                         BID extends BidAbstract<BID, AUCTION, ?>,
                                         SCHEDULE extends ScheduleAbstract<SCHEDULE>,
                                         TERMS extends TermsAbstract<TERMS>,
                                         CANCEL_POLICY extends CancelPolicyAbstract<CANCEL_POLICY>>
       extends ISaleDaoStub<AUCTION>
{
  /**
   *
   * TODO A COMMENTER
   * @param statusArray ... statusArray TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  public Bid4WinList<AUCTION> findListByStatus(Status ... statusArray) throws PersistenceException;
  /**
   *
   * TODO A COMMENTER
   * @param auction TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  public AUCTION historizeBids(AUCTION auction) throws PersistenceException, UserException;
}
