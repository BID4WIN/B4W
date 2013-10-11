package com.bid4win.commons.service.connection;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.persistence.dao.connection.ConnectionAbstractDaoStub;
import com.bid4win.commons.persistence.dao.connection.ConnectionHistoryAbstractDaoStub;
import com.bid4win.commons.persistence.dao.connection.PasswordReinitAbstractDaoStub;
import com.bid4win.commons.persistence.entity.EntityGeneratorStub;
import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;
import com.bid4win.commons.persistence.entity.connection.ConnectionAbstractStub;
import com.bid4win.commons.persistence.entity.connection.ConnectionHistoryAbstractStub;
import com.bid4win.commons.persistence.entity.connection.PasswordReinitAbstractStub;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class ConnectionAbstractServiceTest
       extends ConnectionAbstractServiceTester<ConnectionAbstractStub,
                                               ConnectionHistoryAbstractStub,
                                               PasswordReinitAbstractStub,
                                               SessionDataAbstractStub,
                                               AccountAbstractStub,
                                               EntityGeneratorStub>
{
  /** Référence du DAO de gestion des connexions */
  @Autowired
  @Qualifier("ConnectionDao")
  private ConnectionAbstractDaoStub connectionDao;
  /** Référence du DAO de gestion des historiques de connexion */
  @Autowired
  @Qualifier("ConnectionHistoryDao")
  private ConnectionHistoryAbstractDaoStub historyDao;
  /** Référence du DAO de gestion des historiques de connexion*/
  @Autowired
  @Qualifier("PasswordReinitDao")
  private PasswordReinitAbstractDaoStub reinitDao;

  /**
   *
   * TODO A COMMENTER
   * @param account {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.service.connection.ConnectionAbstractServiceTester#createReinit(com.bid4win.commons.persistence.entity.account.AccountAbstract)
   */
  @Override
  protected PasswordReinitAbstractStub createReinit(AccountAbstractStub account)
            throws Bid4WinException
  {
    return new PasswordReinitAbstractStub(account);
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.connection.ConnectionAbstractServiceTester#getConnectionDao()
   */
  @Override
  protected ConnectionAbstractDaoStub getConnectionDao()
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
  protected ConnectionHistoryAbstractDaoStub getHistoryDao()
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
  protected PasswordReinitAbstractDaoStub getReinitDao()
  {
    return this.reinitDao;
  }
}
