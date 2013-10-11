package com.bid4win.persistence.dao.auction.prebooked;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.persistence.dao.auction.BidHistoryDao_;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedAuction;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedBidHistory;

/**
 * DAO pour les entit�s de la classe NormalBidHistory<BR>
 * <BR>
 * @author Emeric Fill�tre
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
