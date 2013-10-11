package com.bid4win.persistence.dao.account.credit.auction;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.persistence.dao.account.credit.CreditUsageDao_;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementNormal;
import com.bid4win.persistence.entity.account.credit.auction.CreditUsageNormal;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("CreditUsageNormalDao")
@Scope("singleton")
public class CreditUsageNormalDao
       extends CreditUsageDao_<CreditUsageNormal, CreditInvolvementNormal>
{
  /**
   * Constructeur
   */
  protected CreditUsageNormalDao()
  {
    super(CreditUsageNormal.class);
  }

}
