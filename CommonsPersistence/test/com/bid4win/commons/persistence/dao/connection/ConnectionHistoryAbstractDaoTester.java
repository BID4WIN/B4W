package com.bid4win.commons.persistence.dao.connection;

import com.bid4win.commons.persistence.dao.account.AccountBasedEntityMultipleDaoTester;
import com.bid4win.commons.persistence.entity.EntityGenerator;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.connection.ConnectionHistoryAbstract;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <HISTORY> TODO A COMMENTER<BR>
 * @param <ACCOUNT> TODO A COMMENTER<BR>
 * @param <GENERATOR> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class ConnectionHistoryAbstractDaoTester<HISTORY extends ConnectionHistoryAbstract<HISTORY, ACCOUNT>,
                                                         ACCOUNT extends AccountAbstract<ACCOUNT>,
                                                         GENERATOR extends EntityGenerator<ACCOUNT>>
       extends AccountBasedEntityMultipleDaoTester<HISTORY, Long, ACCOUNT, GENERATOR>
{
  /**
   * Permet de préciser le DAO à tester
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityMultipleDaoTester#getDao()
   */
  @Override
  protected abstract IConnectionHistoryAbstractDaoStub<HISTORY, ACCOUNT> getDao();
}
