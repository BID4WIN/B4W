package com.bid4win.persistence.dao.auction.normal;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.persistence.dao.auction.BidHistoryDao_;
import com.bid4win.persistence.entity.auction.normal.NormalAuction;
import com.bid4win.persistence.entity.auction.normal.NormalBidHistory;

/**
 * DAO pour les entités de la classe NormalBidHistory<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("NormalBidHistoryDao")
@Scope("singleton")
public class NormalBidHistoryDao extends BidHistoryDao_<NormalBidHistory, NormalAuction>
{
  /**
   * Constructeur
   */
  protected NormalBidHistoryDao()
  {
    super(NormalBidHistory.class);
  }
}
