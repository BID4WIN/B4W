package com.bid4win.persistence.dao.auction.prebooked;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.persistence.dao.auction.BidHistoryDao_;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedAuction;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedBidHistory;

/**
 * DAO pour les entités de la classe NormalBidHistory<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("PrebookedBidHistoryDao")
@Scope("singleton")
public class PrebookedBidHistoryDao extends BidHistoryDao_<PrebookedBidHistory, PrebookedAuction>
{
  /**
   * Constructeur
   */
  protected PrebookedBidHistoryDao()
  {
    super(PrebookedBidHistory.class);
  }
}
