package com.bid4win.persistence.dao.account.credit.auction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementPrebooked;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementPrebookedHistory;
import com.bid4win.persistence.entity.account.credit.auction.CreditUsagePrebooked;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedAuction;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("CreditInvolvementPrebookedDao")
@Scope("singleton")
public class CreditInvolvementPrebookedDao
       extends CreditInvolvementAuctionDao_<CreditInvolvementPrebooked, CreditUsagePrebooked,
                                            PrebookedAuction, CreditInvolvementPrebookedHistory>
{
  /** Référence du DAO des utilisations de crédits */
  @Autowired
  @Qualifier("CreditUsagePrebookedDao")
  private CreditUsagePrebookedDao usageDao;
  /** Référence du DAO des historisations d'implications */
  @Autowired
  @Qualifier("CreditInvolvementPrebookedHistoryDao")
  private CreditInvolvementPrebookedHistoryDao historyDao;

  /**
   * Constructeur
   */
  protected CreditInvolvementPrebookedDao()
  {
    super(CreditInvolvementPrebooked.class);
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.account.credit.CreditInvolvementDao_#getUsageDao()
   */
  @Override
  protected CreditUsagePrebookedDao getUsageDao()
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
    return this.historyDao;
  }
}
