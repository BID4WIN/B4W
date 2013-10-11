package com.bid4win.persistence.dao.account.credit.auction;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.persistence.dao.account.credit.CreditUsageHistoryDao_;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementNormalHistory;
import com.bid4win.persistence.entity.account.credit.auction.CreditUsageNormalHistory;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("CreditUsageNormalHistoryDao")
@Scope("singleton")
public class CreditUsageNormalHistoryDao
       extends CreditUsageHistoryDao_<CreditUsageNormalHistory, CreditInvolvementNormalHistory>
{
  /**
   * Constructeur
   */
  protected CreditUsageNormalHistoryDao()
  {
    super(CreditUsageNormalHistory.class);
  }
}
