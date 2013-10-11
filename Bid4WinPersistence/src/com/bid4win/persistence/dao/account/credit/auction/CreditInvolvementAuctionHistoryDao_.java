package com.bid4win.persistence.dao.account.credit.auction;

import com.bid4win.persistence.entity.account.credit.CreditBundleHistory;
import com.bid4win.persistence.entity.account.credit.CreditUsage;
import com.bid4win.persistence.entity.account.credit.CreditUsageHistory;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementAuction;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementAuctionHistory;
import com.bid4win.persistence.entity.auction.AuctionAbstract;

/**
 * DAO pour les entit�s de la classe CreditInvolvementAuction<BR>
 * <BR>
 * @param <INVOLVEMENT> D�finition du type d'historique d'implication g�r� par le
 * DAO<BR>
 * @param <USAGE> D�finition du type d'utilisation de cr�dits des historiques d'
 * implication g�r�s par le DAO<BR>
 * @param <AUCTION> D�finition du type de vente aux ench�res des historiques d'
 * implication g�r�s par le DAO<BR>
 * @param <HISTORIZED> D�finition du type d'implications des historiques g�r�s par
 * le DAO<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class CreditInvolvementAuctionHistoryDao_<INVOLVEMENT extends CreditInvolvementAuctionHistory<INVOLVEMENT, USAGE, AUCTION, HISTORIZED>,
                                                          USAGE extends CreditUsageHistory<USAGE, INVOLVEMENT>,
                                                          AUCTION extends AuctionAbstract<AUCTION, ?, ?, ?, ?>,
                                                          HISTORIZED extends CreditInvolvementAuction<HISTORIZED, ? extends CreditUsage<?, ?>, AUCTION, INVOLVEMENT>>
       extends CreditInvolvementAuctionAbstractDao_<INVOLVEMENT, USAGE, CreditBundleHistory, AUCTION, INVOLVEMENT>
{
  /**
   * Constructeur
   * @param involvementClass Classe de l'implication de cr�dits g�r�e par le DAO
   */
  protected CreditInvolvementAuctionHistoryDao_(Class<INVOLVEMENT> involvementClass)
  {
    super(involvementClass);
  }
}
