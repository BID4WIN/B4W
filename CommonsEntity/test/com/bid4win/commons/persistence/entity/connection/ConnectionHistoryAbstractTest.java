package com.bid4win.commons.persistence.entity.connection;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.EntityGeneratorStub;
import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 * Classe de test d'un historique de connexion d'un compte utilisateur<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class ConnectionHistoryAbstractTest
       extends ConnectionHistoryAbstractTester<ConnectionHistoryAbstractStub, ConnectionAbstractStub, AccountAbstractStub, EntityGeneratorStub>
{
  /**
   *
   * TODO A COMMENTER
   * @param sessionId {@inheritDoc}
   * @param account {@inheritDoc}
   * @param ipAddress {@inheritDoc}
   * @param remanent {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.connection.ConnectionHistoryAbstractTester#createConnection(java.lang.String, com.bid4win.commons.persistence.entity.account.AccountAbstract, com.bid4win.commons.persistence.entity.connection.IpAddress, boolean)
   */
  @Override
  protected ConnectionAbstractStub createConnection(String sessionId, AccountAbstractStub account,
                                                    IpAddress ipAddress, boolean remanent)
            throws UserException
  {
    return new ConnectionAbstractStub(sessionId, account, ipAddress, remanent);
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
  protected ConnectionHistoryAbstractStub createHistory(ConnectionAbstractStub connection) throws UserException
  {
    return new ConnectionHistoryAbstractStub(connection);
  }
}
