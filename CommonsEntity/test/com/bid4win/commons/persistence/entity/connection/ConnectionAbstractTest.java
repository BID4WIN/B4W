package com.bid4win.commons.persistence.entity.connection;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.EntityGeneratorStub;
import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 * Classe de test d'une connexion d'un compte utilisateur<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class ConnectionAbstractTest
       extends ConnectionAbstractTester<ConnectionAbstractStub, ConnectionHistoryAbstractStub, AccountAbstractStub, EntityGeneratorStub>
{
  /**
   *
   * TODO A COMMENTER
   * @param data {@inheritDoc}
   * @param account {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.connection.ConnectionAbstractTester#createConnection(com.bid4win.commons.persistence.entity.connection.ConnectionData, com.bid4win.commons.persistence.entity.account.AccountAbstract)
   */
  @Override
  protected ConnectionAbstractStub createConnection(ConnectionData data, AccountAbstractStub account)
            throws UserException
  {
    return new ConnectionAbstractStub(data, account);
  }
}
