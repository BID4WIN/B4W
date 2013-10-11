package com.bid4win.service.connection;

import com.bid4win.commons.persistence.entity.account.security.exception.SessionException;
import com.bid4win.commons.persistence.entity.connection.IpAddress;
import com.bid4win.commons.service.connection.SessionDataAbstract;
import com.bid4win.persistence.entity.account.Account;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class SessionData extends SessionDataAbstract<Account>
{
  /**
   *
   * TODO A COMMENTER
   * @param sessionId TODO A COMMENTER
   * @param ipAddress TODO A COMMENTER
   * @throws SessionException TODO A COMMENTER
   */
  protected SessionData(String sessionId, IpAddress ipAddress) throws SessionException
  {
    super(sessionId, ipAddress);
  }
}
