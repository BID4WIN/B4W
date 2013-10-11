package com.bid4win.commons.persistence.dao.connection;

import com.bid4win.commons.persistence.dao.account.IAccountBasedEntityMultipleDaoStub;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.connection.ConnectionHistoryAbstract;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <HISTORY> TODO A COMMENTER<BR>
 * @param <ACCOUNT> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public interface IConnectionHistoryAbstractDaoStub<HISTORY extends ConnectionHistoryAbstract<HISTORY, ACCOUNT>,
                                                   ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends IAccountBasedEntityMultipleDaoStub<HISTORY, Long, ACCOUNT>
{
  // TODO A COMMENTER
}
