package com.bid4win.service.auction;

import com.bid4win.manager.auction.AuctionManager_;
import com.bid4win.persistence.entity.account.credit.CreditUsage;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementAuction;
import com.bid4win.persistence.entity.auction.Auction;
import com.bid4win.persistence.entity.auction.Bid;
import com.bid4win.persistence.entity.auction.BidHistory;
import com.bid4win.persistence.entity.auction.Bot;
import com.bid4win.persistence.entity.auction.CancelPolicy;
import com.bid4win.persistence.entity.auction.Schedule;
import com.bid4win.persistence.entity.auction.Terms;

/**
 * Manager interne de gestion des ventes aux enchères incluant la gestion des transactions
 * ainsi que celle des habilitations<BR>
 * <BR>
 * @param <AUCTION>  Définition de type de vente aux enchères géré par le manager<BR>
 * @param <BID> Définition de type d'enchère des ventes gérées par le manager<BR>
 * @param <BID_HISTORY> Définition du type d'historique associée aux enchères<BR>
 * @param <SCHEDULE> Définition des éléments de planification des ventes aux enchères
 * gérées par le manager<BR>
 * @param <TERMS> Définition des conditions des ventes aux enchères gérées par le
 * manager<BR>
 * @param <CANCEL_POLICY> Définition de la politique d'annulation des ventes aux
 * enchères gérées par le manager<BR>
 * @param <INVOLVEMENT> Définition des implications de crédits sur les ventes aux
 * enchères gérées par le manager<BR>
 * @param <BOT> Définition des robots d'enchères sur les ventes gérées par le manager<BR>
 * @param <HANDLER> TODO A COMMENTER<BR>
 * @param <SERVICE> Définition du service implémenté afin de pouvoir s'autoréférencer<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class AuctionInternalService_<AUCTION extends Auction<AUCTION, BID, SCHEDULE, TERMS, CANCEL_POLICY>,
                                              BID extends Bid<BID, AUCTION, BID_HISTORY>,
                                              BID_HISTORY extends BidHistory<BID_HISTORY, AUCTION, BID>,
                                              SCHEDULE extends Schedule<SCHEDULE>,
                                              TERMS extends Terms<TERMS>,
                                              CANCEL_POLICY extends CancelPolicy<CANCEL_POLICY>,
                                              INVOLVEMENT extends CreditInvolvementAuction<INVOLVEMENT, ? extends CreditUsage<?, INVOLVEMENT>, AUCTION, ?>,
                                              BOT extends Bot<BOT, AUCTION, BID>,
                                              HANDLER extends AuctionInternalHandler_<HANDLER, AUCTION, SCHEDULE, ?>,
                                              SERVICE extends AuctionInternalService_<AUCTION, BID, BID_HISTORY, SCHEDULE, TERMS, CANCEL_POLICY, INVOLVEMENT, BOT, HANDLER, SERVICE>>
       extends AuctionAbstractInternalService_<AUCTION, BID, BID_HISTORY, SCHEDULE, TERMS, CANCEL_POLICY, INVOLVEMENT, HANDLER, SERVICE>
{
  /**
   * Permet de préciser la référence du manager de gestion des ventes aux enchères
   * @return {@inheritDoc}
   * @see com.bid4win.service.auction.AuctionAbstractService_#getManager()
   */
  @Override
  protected abstract AuctionManager_<AUCTION, BID, BID_HISTORY, SCHEDULE, TERMS, CANCEL_POLICY, INVOLVEMENT, BOT> getManager();
}
