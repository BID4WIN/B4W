package com.bid4win.persistence.dao.auction;

import com.bid4win.persistence.entity.auction.AuctionAbstract;
import com.bid4win.persistence.entity.auction.BidHistory;

/**
 * DAO pour les entit�s de la classe BidHistory<BR>
 * <BR>
 * @param <HISTORY> D�finition du type d'historique d'ench�re g�r� par le DAO<BR>
 * @param <AUCTION> D�finition du type de vente des historiques d'ench�re g�r�s
 * par le DAO<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class BidHistoryDao_<HISTORY extends BidHistory<HISTORY, AUCTION, ?>,
                            AUCTION extends AuctionAbstract<AUCTION, ?, ?, ?, ?>>
       extends BidAbstractDao_<HISTORY, AUCTION, HISTORY>
{
  /**
   * Constructeur
   * @param bidClass Classe de l'historique d'ench�re g�r� par le DAO
   */
  protected BidHistoryDao_(Class<HISTORY> bidClass)
  {
    super(bidClass);
  }

  /**
   * Getter du DAO de gestion des historiques d'ench�res
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.BidAbstractDao_#getHistoryDao()
   */
  @Override
  protected BidHistoryDao_<HISTORY, AUCTION> getHistoryDao()
  {
    return this;
  }
}
