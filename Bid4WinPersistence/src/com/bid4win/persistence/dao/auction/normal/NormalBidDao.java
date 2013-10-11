package com.bid4win.persistence.dao.auction.normal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.persistence.dao.auction.BidDao_;
import com.bid4win.persistence.entity.auction.normal.NormalAuction;
import com.bid4win.persistence.entity.auction.normal.NormalBid;
import com.bid4win.persistence.entity.auction.normal.NormalBidHistory;

/**
 * DAO pour les entités de la classe NormalBid<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("NormalBidDao")
@Scope("singleton")
public class NormalBidDao extends BidDao_<NormalBid, NormalAuction, NormalBidHistory>
{
  /** Référence du DAO des ventes aux enchères normales */
  @Autowired
  @Qualifier("NormalAuctionDao")
  private NormalAuctionDao auctionDao;
  /** Référence du DAO des historiques d'enchères normales */
  @Autowired
  @Qualifier("NormalBidHistoryDao")
  private NormalBidHistoryDao historyDao;

  /**
   * Constructeur
   */
  protected NormalBidDao()
  {
    super(NormalBid.class);
  }

  /**
   * Cette fonction défini le DAO des ventes aux enchères normales
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.BidDao_#getAuctionDao()
   */
  @Override
  protected NormalAuctionDao getAuctionDao()
  {
    return this.auctionDao;
  }
  /**
   * Cette fonction défini le DAO des historiques de vente aux enchères normales
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.BidAbstractDao_#getHistoryDao()
   */
  @Override
  protected NormalBidHistoryDao getHistoryDao()
  {
    return this.historyDao;
  }
}
