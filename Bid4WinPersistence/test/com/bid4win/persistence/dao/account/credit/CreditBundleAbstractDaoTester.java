package com.bid4win.persistence.dao.account.credit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bid4win.persistence.dao.account.AccountBasedEntityMultipleDaoTester;
import com.bid4win.persistence.entity.account.credit.CreditBundleAbstract;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <BUNDLE> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class CreditBundleAbstractDaoTester<BUNDLE extends CreditBundleAbstract<BUNDLE>>
       extends AccountBasedEntityMultipleDaoTester<BUNDLE, Long>
{
  /** R�f�rence du DAO des historiques de lots de cr�dits */
  @Autowired
  @Qualifier("CreditBundleHistoryDaoStub")
  private CreditBundleHistoryDaoStub historyDao;

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected CreditBundleHistoryDaoStub getHistoryDao()
  {
    return this.historyDao;
  }
}
