package com.bid4win.persistence.dao.account.credit;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.persistence.entity.account.credit.CreditBundleHistory;

/**
 * DAO pour les entit�s de la classe CreditBundleHistory<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Component("CreditBundleHistoryDao")
@Scope("singleton")
public class CreditBundleHistoryDao extends CreditBundleAbstractDao_<CreditBundleHistory>
{
  /**
   * Constructeur
   */
  protected CreditBundleHistoryDao()
  {
    super(CreditBundleHistory.class);
  }
}
