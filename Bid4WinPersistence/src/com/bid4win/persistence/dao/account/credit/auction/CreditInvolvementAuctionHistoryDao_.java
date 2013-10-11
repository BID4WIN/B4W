package com.bid4win.persistence.dao.account.credit.auction;

import com.bid4win.persistence.entity.account.credit.CreditBundleHistory;
import com.bid4win.persistence.entity.account.credit.CreditUsage;
import com.bid4win.persistence.entity.account.credit.CreditUsageHistory;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementAuction;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementAuctionHistory;
import com.bid4win.persistence.entity.auction.AuctionAbstract;

/**
 * DAO pour les entités de la classe CreditInvolvementAuction<BR>
 * <BR>
 * @param <INVOLVEMENT> Définition du type d'historique d'implication géré par le
 * DAO<BR>
 * @param <USAGE> Définition du type d'utilisation de crédits des historiques d'
 * implication gérés par le DAO<BR>
 * @param <AUCTION> Définition du type de vente aux enchères des historiques d'
 * implication gérés par le DAO<BR>
 * @param <HISTORIZED> Définition du type d'implications des historiques gérés par
 * le DAO<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class CreditInvolvementAuctionHistoryDao_<INVOLVEMENT extends CreditInvolvementAuctionHistory<INVOLVEMENT, USAGE, AUCTION, HISTORIZED>,
                                                          USAGE extends CreditUsageHistory<USAGE, INVOLVEMENT>,
                                                          AUCTION extends AuctionAbstract<AUCTION, ?, ?, ?, ?>,
                                                          HISTORIZED extends CreditInvolvementAuction<HISTORIZED, ? extends CreditUsage<?, ?>, AUCTION, INVOLVEMENT>>
       extends CreditInvolvementAuctionAbstractDao_<INVOLVEMENT, USAGE, CreditBundleHistory, AUCTION, INVOLVEMENT>
{
  /**
   * Constructeur
   * @param involvementClass Classe de l'implication de crédits gérée par le DAO
   */
  protected CreditInvolvementAuctionHistoryDao_(Class<INVOLVEMENT> involvementClass)
  {
    super(involvementClass);
  }
}
