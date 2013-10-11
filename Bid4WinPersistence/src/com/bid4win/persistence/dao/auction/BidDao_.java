package com.bid4win.persistence.dao.auction;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.persistence.entity.auction.Auction;
import com.bid4win.persistence.entity.auction.Bid;
import com.bid4win.persistence.entity.auction.BidHistory;

/**
 * DAO pour les entités de la classe BidAbstract<BR>
 * <BR>
 * @param <BID> Définition du type d'enchère gérée par le DAO<BR>
 * @param <AUCTION> Définition du type de vente des enchères gérées par le DAO<BR>
 * @param <HISTORY> Définition de la classe d'historique associée aux enchères
 * gérées par le DAO<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class BidDao_<BID extends Bid<BID, AUCTION, HISTORY>,
                              AUCTION extends Auction<AUCTION, BID, ?, ?, ?>,
                              HISTORY extends BidHistory<HISTORY, AUCTION, BID>>
       extends BidAbstractDao_<BID, AUCTION, HISTORY>
{
  /**
   * Constructeur
   * @param bidClass Classe de l'enchère gérée par le DAO
   */
  protected BidDao_(Class<BID> bidClass)
  {
    super(bidClass);
  }

  /**
   * Cette fonction permet d'ajouter l'enchère en argument et de mettre à jour la
   * vente correspondante tout en limitant le nombre de liens vers ses dernières
   * enchères
   * @param bid {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}, si la vente de l'enchère n'a
   * jamais été persistée ou si le retrait du lien vers une de ses dernières
   * enchères échoue
   * @see com.bid4win.persistence.dao.auction.BidAbstractDao_#add(com.bid4win.persistence.entity.auction.BidAbstract)
   */
  @Override
  public BID add(BID bid) throws PersistenceException
  {
    // Ajoute l'enchère en paramètre
    bid = super.add(bid);
    // Met à jour la vente liée (il doit y en avoir forcément une) et lui retire
    // les dernières enchères en trop
    AUCTION auction = bid.getAuctionLink();
    // Ne garde qu'un nombre limité de lien vers les dernières enchères
    try
    {
      Bid4WinList<BID> lastBidList = auction.getLastBidList();
      while(lastBidList.size() > auction.getLastBidNbMax())
      {
        BID toUnlink = lastBidList.removeLast();
        toUnlink.unlinkFromAuction();
        this.update(toUnlink);
      }
    }
    catch(UserException ex)
    {
      throw new PersistenceException(ex);
    }
    // Met à jour la vente aux enchères
    this.getAuctionDao().update(auction);
    return bid;
  }
  /**
   * Cette fonction permet de supprimer l'enchère en argument et de mettre à jour
   * la vente correspondante
   * @param bid {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#remove(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  protected BID remove(BID bid) throws PersistenceException
  {
    try
    {
      if(bid.isLinkedToAuction())
      {
        this.getAuctionDao().update(bid.unlinkFromAuction());
      }
    }
    catch(UserException ex)
    {
      throw new PersistenceException(ex);
    }
    return super.remove(bid);
  }

  /**
   * Cette fonction doit définir le DAO des ventes des enchères
   * @return Le DAO des ventes des enchères
   */
  protected abstract AuctionDao_<AUCTION, BID, ?, ?, ?, ?> getAuctionDao();
}
