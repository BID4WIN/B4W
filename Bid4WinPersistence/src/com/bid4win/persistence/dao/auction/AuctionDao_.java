package com.bid4win.persistence.dao.auction;

import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementAuction;
import com.bid4win.persistence.entity.auction.Auction;
import com.bid4win.persistence.entity.auction.Bid;
import com.bid4win.persistence.entity.auction.CancelPolicy;
import com.bid4win.persistence.entity.auction.Schedule;
import com.bid4win.persistence.entity.auction.Terms;

/**
 * DAO pour les entités de la classe Auction<BR>
 * <BR>
 * @param <AUCTION> Définition du type de vente aux enchères gérée par le DAO<BR>
 * @param <BID> Définition des enchères de la vente gérée par le DAO<BR>
 * @param <SCHEDULE> Définition des éléments de planification des ventes aux enchères
 * gérées par le DAO<BR>
 * @param <TERMS> Définition des conditions des ventes aux enchères gérées par le DAO<BR>
 * @param <CANCEL_POLICY> Définition des politiques d'annulation des ventes aux
 * enchères gérées par le DAO<BR>
 * @param <INVOLVEMENT> Définition des implications de crédits sur les ventes aux
 * enchères gérées par le DAO<BR>
 * <BR>
 * @author Emeric Fillâtre
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
   * @param auctionClass Classe de la vente aux enchères gérée par le DAO
   */
  protected AuctionDao_(Class<AUCTION> auctionClass)
  {
    super(auctionClass);
  }

  /**
   * Permet de préciser le DAO des enchères. Attention à injecter le DAO grâce à
   * un setter pour casser l'injection circulaire à la construction
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionAbstractDao_#getBidDao()
   */
  @Override
  protected abstract BidDao_<BID, AUCTION, ?> getBidDao();
}
