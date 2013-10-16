package com.bid4win.service.connection;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.persistence.entity.account.security.exception.AuthenticationException;
import com.bid4win.commons.persistence.entity.account.security.exception.SessionException;
import com.bid4win.commons.persistence.entity.connection.IpAddress;
import com.bid4win.commons.service.connection.SessionHandlerAbstract;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.connection.Connection;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("SessionHandler")
@Scope("singleton")
public class SessionHandler extends SessionHandlerAbstract<SessionData, Account, Connection>
{
  private static SessionHandler instance = null;
  public static SessionHandler getInstance()
  {
    return SessionHandler.instance;
  }

  /**
   *
   * TODO A COMMENTER
   */
  protected SessionHandler()
  {
    SessionHandler.instance = this;
  }
  /**
   *
   * TODO A COMMENTER
   * @param sessionId {@inheritDoc}
   * @param ipAddress {@inheritDoc}
   * @return {@inheritDoc}
   * @throws SessionException {@inheritDoc}
   * @see com.bid4win.commons.service.connection.SessionHandlerAbstract#createSessionData(java.lang.String, com.bid4win.commons.persistence.entity.connection.IpAddress)
   */
  @Override
  protected SessionData createSessionData(String sessionId, IpAddress ipAddress)
            throws SessionException
  {
    return new SessionData(sessionId, ipAddress);
  }
  @Override
  protected void connect(Connection connection) throws SessionException, AuthenticationException
  {
    super.connect(connection);
  }

}
