package com.bid4win.service.connection;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.service.connection.ConnectionAbstractServiceTester;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.dao.connection.ConnectionDaoStub;
import com.bid4win.persistence.dao.connection.ConnectionHistoryDaoStub;
import com.bid4win.persistence.dao.connection.PasswordReinitDaoStub;
import com.bid4win.persistence.entity.EntityGenerator;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.connection.Connection;
import com.bid4win.persistence.entity.connection.ConnectionHistory;
import com.bid4win.persistence.entity.connection.PasswordReinit;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
public class ConnectionServiceTest
       extends ConnectionAbstractServiceTester<Connection, ConnectionHistory,
                                               PasswordReinit, SessionData,
                                               Account, EntityGenerator>
{
  /** Référence du DAO de gestion des connexions */
  @Autowired
  @Qualifier("ConnectionDaoStub")
  private ConnectionDaoStub connectionDao;
  /** Référence du DAO de gestion des historiques de connexion */
  @Autowired
  @Qualifier("ConnectionHistoryDaoStub")
  private ConnectionHistoryDaoStub historyDao;
  /** Référence du DAO de gestion des historiques de connexion*/
  @Autowired
  @Qualifier("PasswordReinitDaoStub")
  private PasswordReinitDaoStub reinitDao;

  /**
   *
   * TODO A COMMENTER
   * @param account {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.service.connection.ConnectionAbstractServiceTester#createReinit(com.bid4win.commons.persistence.entity.account.AccountAbstract)
   */
  @Override
  protected PasswordReinit createReinit(Account account) throws Bid4WinException
  {
    return new PasswordReinit(account);
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.connection.ConnectionAbstractServiceTester#getConnectionDao()
   */
  @Override
  protected ConnectionDaoStub getConnectionDao()
  {
    return this.connectionDao;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.connection.ConnectionAbstractServiceTester#getHistoryDao()
   */
  @Override
  protected ConnectionHistoryDaoStub getHistoryDao()
  {
    return this.historyDao;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.connection.ConnectionAbstractServiceTester#getReinitDao()
   */
  @Override
  protected PasswordReinitDaoStub getReinitDao()
  {
    return this.reinitDao;
  }
}
