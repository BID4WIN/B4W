package com.bid4win.commons.persistence.dao.connection;

import com.bid4win.commons.persistence.dao.account.IAccountBasedEntityMultipleDaoStub;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.connection.ConnectionAbstract;
import com.bid4win.commons.persistence.entity.connection.ConnectionHistoryAbstract;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <CONNECTION> TODO A COMMENTER<BR>
 * @param <HISTORY> TODO A COMMENTER<BR>
 * @param <ACCOUNT> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public interface IConnectionAbstractDaoStub<CONNECTION extends ConnectionAbstract<CONNECTION, HISTORY, ACCOUNT>,
                                            HISTORY extends ConnectionHistoryAbstract<HISTORY, ACCOUNT>,
                                            ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends IAccountBasedEntityMultipleDaoStub<CONNECTION, String, ACCOUNT>
{
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public IConnectionHistoryAbstractDaoStub<HISTORY, ACCOUNT> getHistoryDaoStub();
}
