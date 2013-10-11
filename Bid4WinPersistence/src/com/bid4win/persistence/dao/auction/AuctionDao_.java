package com.bid4win.persistence.dao.auction;

import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementAuction;
import com.bid4win.persistence.entity.auction.Auction;
import com.bid4win.persistence.entity.auction.Bid;
import com.bid4win.persistence.entity.auction.CancelPolicy;
import com.bid4win.persistence.entity.auction.Schedule;
import com.bid4win.persistence.entity.auction.Terms;

/**
 * DAO pour les entit�s de la classe Auction<BR>
 * <BR>
 * @param <AUCTION> D�finition du type de vente aux ench�res g�r�e par le DAO<BR>
 * @param <BID> D�finition des ench�res de la vente g�r�e par le DAO<BR>
 * @param <SCHEDULE> D�finition des �l�ments de planification des ventes aux ench�res
 * g�r�es par le DAO<BR>
 * @param <TERMS> D�finition des conditions des ventes aux ench�res g�r�es par le DAO<BR>
 * @param <CANCEL_POLICY> D�finition des politiques d'annulation des ventes aux
 * ench�res g�r�es par le DAO<BR>
 * @param <INVOLVEMENT> D�finition des implications de cr�dits sur les ventes aux
 * ench�res g�r�es par le DAO<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class AuctionDao_<AUCTION extends Auction<AUCTION, BID, SCHEDULE, TERMS, CANCEL_POLICY>,
                                  BID extends Bid<BID, AUCTION, ?>,
                                  SCHEDULE extends Schedule<SCHEDULE>,
                                  TERMS extends Terms<TERMS>,
                                  CANCEL_POLICY extends CancelPolicy<CANCEL_POLICY>,
                                  INVOLVEMENT extends CreditInvolvementAuction<INVOLVEMENT, ?, AUCTION, ?>>
       extends AuctionAbstractDao_<AUCTION, BID, SCHEDULE, TERMS, CANCEL_POLICY, INVOLVEMENT>
{
  /**
   * Constructeur
   * @param auctionClass Classe de la vente aux ench�res g�r�e par le DAO
   */
  protected AuctionDao_(Class<AUCTION> auctionClass)
  {
    super(auctionClass);
  }

  /**
   * Permet de pr�ciser le DAO des ench�res. Attention � injecter le DAO gr�ce �
   * un setter pour casser l'injection circulaire � la construction
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionAbstractDao_#getBidDao()
   */
  @Override
  protected abstract BidDao_<BID, AUCTION, ?> getBidDao();
}
