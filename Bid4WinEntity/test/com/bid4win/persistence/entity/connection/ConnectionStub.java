package com.bid4win.persistence.entity.connection;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.connection.ConnectionData;
import com.bid4win.persistence.entity.account.Account;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class ConnectionStub extends Connection
{
  /** TODO A COMMENTER */
  //private Bid4WinDate startDate = new Bid4WinDate();

  /**
   * Constructeur pour création par introspection
   */
  protected ConnectionStub()
  {
    super();
  }
  /**
   * Constructeur
   * @param sessionId Identifiant de la session de connexion
   * @param account Compte utilisateur de connexion
   * @param ipAddress Adresse IP de connexion
   * @param remanent Flag indiquant si la rémanence est activée sur la connexion
   * @throws UserException Si l'identifiant de session, le compte utilisateur ou
   * l'adresse IP de connexion en argument est nul
   */
  public ConnectionStub(ConnectionData data/*String sessionId*/, Account account/*, IpAddress ipAddress, boolean remanent*/)
         throws UserException
  {
    super(data/*sessionId*/, account/*, ipAddress, remanent*/);
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.connection.ConnectionAbstract#getStartDate()
   */
  /*@Override
  public Bid4WinDate getStartDate()
  {
    return this.startDate;
  }*/
}
