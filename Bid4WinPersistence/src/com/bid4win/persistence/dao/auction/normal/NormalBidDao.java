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
 * DAO pour les entit�s de la classe NormalBid<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Component("NormalBidDao")
@Scope("singleton")
public class NormalBidDao extends BidDao_<NormalBid, NormalAuction, NormalBidHistory>
{
  /** R�f�rence du DAO des ventes aux ench�res normales */
  @Autowired
  @Qualifier("NormalAuctionDao")
  private NormalAuctionDao auctionDao;
  /** R�f�rence du DAO des historiques d'ench�res normales */
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
   * Cette fonction d�fini le DAO des ventes aux ench�res normales
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.BidDao_#getAuctionDao()
   */
  @Override
  protected NormalAuctionDao getAuctionDao()
  {
    return this.auctionDao;
  }
  /**
   * Cette fonction d�fini le DAO des historiques de vente aux ench�res normales
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.BidAbstractDao_#getHistoryDao()
   */
  @Override
  protected NormalBidHistoryDao getHistoryDao()
  {
    return this.historyDao;
  }
}
