package com.bid4win.persistence.dao.account.credit.auction;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.persistence.dao.account.credit.CreditUsageHistoryDao_;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementPrebookedHistory;
import com.bid4win.persistence.entity.account.credit.auction.CreditUsagePrebookedHistory;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("CreditUsagePrebookedHistoryDao")
@Scope("singleton")
public class CreditUsagePrebookedHistoryDao
       extends CreditUsageHistoryDao_<CreditUsagePrebookedHistory, CreditInvolvementPrebookedHistory>
{
  /**
   * Constructeur
   */
  protected CreditUsagePrebookedHistoryDao()
  {
    super(CreditUsagePrebookedHistory.class);
  }
}
