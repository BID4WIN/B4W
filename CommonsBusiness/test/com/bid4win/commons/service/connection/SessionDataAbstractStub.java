package com.bid4win.commons.service.connection;

import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;
import com.bid4win.commons.persistence.entity.account.security.exception.SessionException;
import com.bid4win.commons.persistence.entity.connection.ConnectionAbstractStub;
import com.bid4win.commons.persistence.entity.connection.IpAddress;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class SessionDataAbstractStub extends SessionDataAbstract<AccountAbstractStub, ConnectionAbstractStub>
{
  /**
   * Constructeur
   * @param sessionId Identifiant de la session du conteneur de donn�es
   * @param ipAddress Adresse IP li�e � la session
   * @throws SessionException Si l'identifiant de session en argument est invalide
   */
  public SessionDataAbstractStub(String sessionId, IpAddress ipAddress) throws SessionException
  {
    super(sessionId, ipAddress);
  }
}
