package com.bid4win.persistence.dao.auction.prebooked;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.persistence.dao.auction.BidDao_;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedAuction;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedBid;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedBidHistory;

/**
 * DAO pour les entités de la classe PrebookedBid<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("PrebookedBidDao")
@Scope("singleton")
public class PrebookedBidDao
       extends BidDao_<PrebookedBid, PrebookedAuction, PrebookedBidHistory>
{
  /** Référence du DAO des ventes aux enchères avec pré-inscription */
  @Autowired
  @Qualifier("PrebookedAuctionDao")
  private PrebookedAuctionDao auctionDao;
  /** Référence du DAO des historiques d'enchères avec pré-inscription */
  @Autowired
  @Qualifier("PrebookedBidHistoryDao")
  private PrebookedBidHistoryDao historyDao;

  /**
   * Constructeur
   */
  protected PrebookedBidDao()
  {
    super(PrebookedBid.class);
  }

  /**
   * Cette fonction défini le DAO des ventes aux enchères avec pré-inscription
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.BidDao_#getAuctionDao()
   */
  @Override
  protected PrebookedAuctionDao getAuctionDao()
  {
    return this.auctionDao;
  }
  /**
   * Cette fonction défini le DAO des historiques de vente aux enchères avec pré-
   * inscription
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.BidAbstractDao_#getHistoryDao()
   */
  @Override
  protected PrebookedBidHistoryDao getHistoryDao()
  {
    return this.historyDao;
  }
}
