package com.bid4win.persistence.dao.account.credit.auction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementNormal;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementNormalHistory;
import com.bid4win.persistence.entity.account.credit.auction.CreditUsageNormal;
import com.bid4win.persistence.entity.auction.normal.NormalAuction;

/**
 * DAO pour les entités de la classe CreditInvolvementNormal<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("CreditInvolvementNormalDao")
@Scope("singleton")
public class CreditInvolvementNormalDao
       extends CreditInvolvementAuctionDao_<CreditInvolvementNormal, CreditUsageNormal,
                                            NormalAuction, CreditInvolvementNormalHistory>
{
  /** Référence du DAO des utilisations de crédits */
  @Autowired
  @Qualifier("CreditUsageNormalDao")
  private CreditUsageNormalDao usageDao;
  /** Référence du DAO des historisations d'implications */
  @Autowired
  @Qualifier("CreditInvolvementNormalHistoryDao")
  private CreditInvolvementNormalHistoryDao historyDao;

  /**
   * Constructeur
   */
  protected CreditInvolvementNormalDao()
  {
    super(CreditInvolvementNormal.class);
  }

  /**
   * Getter du DAO de gestion des utilisations de crédits des implications
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.account.credit.CreditInvolvementDao_#getUsageDao()
   */
  @Override
  protected CreditUsageNormalDao getUsageDao()
  {
    return this.usageDao;
  }
  /**
   * Getter du DAO de gestion des historisations d'implication de crédits
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.account.credit.CreditInvolvementDao_#getHistoryDao()
   */
  @Override
  protected CreditInvolvementNormalHistoryDao getHistoryDao()
  {
    return this.historyDao;
  }
}
