package com.bid4win.persistence.dao.connection;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.persistence.dao.connection.ConnectionAbstractDaoTester;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.dao.account.AccountInitializer;
import com.bid4win.persistence.entity.EntityGenerator;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.connection.Connection;
import com.bid4win.persistence.entity.connection.ConnectionHistory;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
@TransactionConfiguration(transactionManager = "Bid4WinTransactionManager")
public class ConnectionDaoTest
       extends ConnectionAbstractDaoTester<Connection, ConnectionHistory, Account, EntityGenerator>
{
  /** Référence du DAO à tester */
  @Autowired
  @Qualifier("ConnectionDaoStub")
  private ConnectionDaoStub dao;
  /** Référence TODO */
  @Autowired
  @Qualifier("AccountInitializer")
  private AccountInitializer accountInitializer;

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDaoTester#getDao()
   */
  @Override
  protected ConnectionDaoStub getDao()
  {
    return this.dao;
  }
  /**
   * Précise TODO
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityDaoTester#getAccountInitializer()
   */
  @Override
  protected AccountInitializer getAccountInitializer()
  {
    return this.accountInitializer;
  }
  /**
   *
   * TODO A COMMENTER
   * @param account {@inheritDoc}
   * @param sessionId {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.connection.ConnectionAbstractDaoTester#create(com.bid4win.commons.persistence.entity.account.AccountAbstract, java.lang.String, boolean)
   */
  @Override
  protected Connection create(Account account, String sessionId, boolean remanent)
            throws Bid4WinException
  {
    return new Connection(sessionId, account, this.getGenerator().createIpAddress(true), remanent);
  }
}
