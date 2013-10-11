package com.bid4win.persistence.dao.auction;

import com.bid4win.persistence.entity.auction.AuctionAbstract;
import com.bid4win.persistence.entity.auction.BidHistory;

/**
 * DAO pour les entités de la classe BidHistory<BR>
 * <BR>
 * @param <HISTORY> Définition du type d'historique d'enchère géré par le DAO<BR>
 * @param <AUCTION> Définition du type de vente des historiques d'enchère gérés
 * par le DAO<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class BidHistoryDao_<HISTORY extends BidHistory<HISTORY, AUCTION, ?>,
                            AUCTION extends AuctionAbstract<AUCTION, ?, ?, ?, ?>>
       extends BidAbstractDao_<HISTORY, AUCTION, HISTORY>
{
  /**
   * Constructeur
   * @param bidClass Classe de l'historique d'enchère géré par le DAO
   */
  protected BidHistoryDao_(Class<HISTORY> bidClass)
  {
    super(bidClass);
  }

  /**
   * Getter du DAO de gestion des historiques d'enchères
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.BidAbstractDao_#getHistoryDao()
   */
  @Override
  protected BidHistoryDao_<HISTORY, AUCTION> getHistoryDao()
  {
    return this;
  }
}
