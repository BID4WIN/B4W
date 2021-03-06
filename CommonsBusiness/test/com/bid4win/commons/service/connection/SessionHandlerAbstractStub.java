package com.bid4win.commons.service.connection;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;
import com.bid4win.commons.persistence.entity.account.security.exception.SessionException;
import com.bid4win.commons.persistence.entity.connection.IpAddress;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Component("SessionHandler")
@Scope("singleton")
public class SessionHandlerAbstractStub
       extends SessionHandlerAbstract<SessionDataAbstractStub, AccountAbstractStub>
{
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
  protected SessionDataAbstractStub createSessionData(String sessionId, IpAddress ipAddress)
            throws SessionException
  {
    return new SessionDataAbstractStub(sessionId, ipAddress);
  }
}
