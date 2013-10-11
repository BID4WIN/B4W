package com.bid4win.persistence.dao.account.credit.auction;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.persistence.dao.account.credit.CreditUsageDao_;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementPrebooked;
import com.bid4win.persistence.entity.account.credit.auction.CreditUsagePrebooked;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("CreditUsagePrebookedDao")
@Scope("singleton")
public class CreditUsagePrebookedDao
       extends CreditUsageDao_<CreditUsagePrebooked, CreditInvolvementPrebooked>
{
  /**
   * Constructeur
   */
  protected CreditUsagePrebookedDao()
  {
    super(CreditUsagePrebooked.class);
  }
}
