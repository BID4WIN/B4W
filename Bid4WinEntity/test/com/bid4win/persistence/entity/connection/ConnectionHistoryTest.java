package com.bid4win.persistence.entity.connection;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.connection.ConnectionData;
import com.bid4win.commons.persistence.entity.connection.ConnectionHistoryAbstractTester;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.entity.EntityGenerator;
import com.bid4win.persistence.entity.account.Account;

/**
 * Classe de test d'un historique de connexion d'un compte utilisateur<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
public class ConnectionHistoryTest
       extends ConnectionHistoryAbstractTester<ConnectionHistory, Connection, Account, EntityGenerator>

{
  /**
   *
   * TODO A COMMENTER
   * @param data {@inheritDoc}
   * @param account {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.connection.ConnectionHistoryAbstractTester#createConnection(com.bid4win.commons.persistence.entity.connection.ConnectionData, com.bid4win.commons.persistence.entity.account.AccountAbstract)
   */
  @Override
  protected Connection createConnection(ConnectionData data/*String sessionId*/, Account account/*, IpAddress ipAddress, boolean remanent*/) throws UserException
  {
    return new ConnectionStub(data/*sessionId*/, account/*, ipAddress, remanent*/);
  }
  /**
   *
   * TODO A COMMENTER
   * @param connection {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.connection.ConnectionHistoryAbstractTester#createHistory(com.bid4win.commons.persistence.entity.connection.ConnectionAbstract)
   */
  @Override
  protected ConnectionHistory createHistory(Connection connection) throws UserException
  {
    return new ConnectionHistory(connection);
  }
}
