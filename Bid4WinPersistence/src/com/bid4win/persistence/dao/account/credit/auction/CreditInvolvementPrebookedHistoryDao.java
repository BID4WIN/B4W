package com.bid4win.persistence.dao.account.credit.auction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementPrebooked;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementPrebookedHistory;
import com.bid4win.persistence.entity.account.credit.auction.CreditUsagePrebookedHistory;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedAuction;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("CreditInvolvementPrebookedHistoryDao")
@Scope("singleton")
public class CreditInvolvementPrebookedHistoryDao
       extends CreditInvolvementAuctionHistoryDao_<CreditInvolvementPrebookedHistory,
                                                   CreditUsagePrebookedHistory,
                                                   PrebookedAuction,
                                                   CreditInvolvementPrebooked>
{
  /** Référence du DAO des utilisations de crédits */
  @Autowired
  @Qualifier("CreditUsagePrebookedHistoryDao")
  private CreditUsagePrebookedHistoryDao usageDao;

  /**
   * Constructeur
   */
  protected CreditInvolvementPrebookedHistoryDao()
  {
    super(CreditInvolvementPrebookedHistory.class);
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.account.credit.CreditInvolvementDao_#getUsageDao()
   */
  @Override
  protected CreditUsagePrebookedHistoryDao getUsageDao()
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
  protected CreditInvolvementPrebookedHistoryDao getHistoryDao()
  {
    return this;
  }
}
