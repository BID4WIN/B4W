package com.bid4win.persistence.dao.account.credit.auction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementNormal;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementNormalHistory;
import com.bid4win.persistence.entity.account.credit.auction.CreditUsageNormalHistory;
import com.bid4win.persistence.entity.auction.normal.NormalAuction;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("CreditInvolvementNormalHistoryDao")
@Scope("singleton")
public class CreditInvolvementNormalHistoryDao
       extends CreditInvolvementAuctionHistoryDao_<CreditInvolvementNormalHistory,
                                                   CreditUsageNormalHistory,
                                                   NormalAuction,
                                                   CreditInvolvementNormal>
{
  /** Référence du DAO des utilisations de crédits */
  @Autowired
  @Qualifier("CreditUsageNormalHistoryDao")
  private CreditUsageNormalHistoryDao usageDao;

  /**
   * Constructeur
   */
  protected CreditInvolvementNormalHistoryDao()
  {
    super(CreditInvolvementNormalHistory.class);
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.account.credit.CreditInvolvementDao_#getUsageDao()
   */
  @Override
  protected CreditUsageNormalHistoryDao getUsageDao()
  {
    return this.usageDao;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.account.credit.CreditInvolvementDao_#getHistoryDao()
   */
  @Override
  protected CreditInvolvementNormalHistoryDao getHistoryDao()
  {
    return this;
  }
}
