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
 * Manager interne de gestion des ventes aux ench�res incluant la gestion des transactions
 * ainsi que celle des habilitations<BR>
 * <BR>
 * @param <AUCTION>  D�finition de type de vente aux ench�res g�r� par le manager<BR>
 * @param <BID> D�finition de type d'ench�re des ventes g�r�es par le manager<BR>
 * @param <BID_HISTORY> D�finition du type d'historique associ�e aux ench�res<BR>
 * @param <SCHEDULE> D�finition des �l�ments de planification des ventes aux ench�res
 * g�r�es par le manager<BR>
 * @param <TERMS> D�finition des conditions des ventes aux ench�res g�r�es par le
 * manager<BR>
 * @param <CANCEL_POLICY> D�finition de la politique d'annulation des ventes aux
 * ench�res g�r�es par le manager<BR>
 * @param <INVOLVEMENT> D�finition des implications de cr�dits sur les ventes aux
 * ench�res g�r�es par le manager<BR>
 * @param <BOT> D�finition des robots d'ench�res sur les ventes g�r�es par le manager<BR>
 * @param <HANDLER> TODO A COMMENTER<BR>
 * @param <SERVICE> D�finition du service impl�ment� afin de pouvoir s'autor�f�rencer<BR>
 * <BR>
 * @author Emeric Fill�tre
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
   * Permet de pr�ciser la r�f�rence du manager de gestion des ventes aux ench�res
   * @return {@inheritDoc}
   * @see com.bid4win.service.auction.AuctionAbstractService_#getManager()
   */
  @Override
  protected abstract AuctionManager_<AUCTION, BID, BID_HISTORY, SCHEDULE, TERMS, CANCEL_POLICY, INVOLVEMENT, BOT> getManager();
}
