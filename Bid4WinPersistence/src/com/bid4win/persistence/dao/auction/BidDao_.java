package com.bid4win.persistence.dao.auction;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.persistence.entity.auction.Auction;
import com.bid4win.persistence.entity.auction.Bid;
import com.bid4win.persistence.entity.auction.BidHistory;

/**
 * DAO pour les entit�s de la classe BidAbstract<BR>
 * <BR>
 * @param <BID> D�finition du type d'ench�re g�r�e par le DAO<BR>
 * @param <AUCTION> D�finition du type de vente des ench�res g�r�es par le DAO<BR>
 * @param <HISTORY> D�finition de la classe d'historique associ�e aux ench�res
 * g�r�es par le DAO<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class BidDao_<BID extends Bid<BID, AUCTION, HISTORY>,
                              AUCTION extends Auction<AUCTION, BID, ?, ?, ?>,
                              HISTORY extends BidHistory<HISTORY, AUCTION, BID>>
       extends BidAbstractDao_<BID, AUCTION, HISTORY>
{
  /**
   * Constructeur
   * @param bidClass Classe de l'ench�re g�r�e par le DAO
   */
  protected BidDao_(Class<BID> bidClass)
  {
    super(bidClass);
  }

  /**
   * Cette fonction permet d'ajouter l'ench�re en argument et de mettre � jour la
   * vente correspondante tout en limitant le nombre de liens vers ses derni�res
   * ench�res
   * @param bid {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}, si la vente de l'ench�re n'a
   * jamais �t� persist�e ou si le retrait du lien vers une de ses derni�res
   * ench�res �choue
   * @see com.bid4win.persistence.dao.auction.BidAbstractDao_#add(com.bid4win.persistence.entity.auction.BidAbstract)
   */
  @Override
  public BID add(BID bid) throws PersistenceException
  {
    // Ajoute l'ench�re en param�tre
    bid = super.add(bid);
    // Met � jour la vente li�e (il doit y en avoir forc�ment une) et lui retire
    // les derni�res ench�res en trop
    AUCTION auction = bid.getAuctionLink();
    // Ne garde qu'un nombre limit� de lien vers les derni�res ench�res
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
    // Met � jour la vente aux ench�res
    this.getAuctionDao().update(auction);
    return bid;
  }
  /**
   * Cette fonction permet de supprimer l'ench�re en argument et de mettre � jour
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
   * Cette fonction doit d�finir le DAO des ventes des ench�res
   * @return Le DAO des ventes des ench�res
   */
  protected abstract AuctionDao_<AUCTION, BID, ?, ?, ?, ?> getAuctionDao();
}
