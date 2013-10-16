package com.bid4win.commons.persistence.entity.connection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Entity
@Access(AccessType.FIELD)
public class ConnectionHistoryAbstractStub
       extends ConnectionHistoryAbstract<ConnectionHistoryAbstractStub, AccountAbstractStub>
{
  /**
   * Constructeur pour création par introspection
   */
  protected ConnectionHistoryAbstractStub()
  {
    super();
  }
  /**
   *
   * TODO A COMMENTER
   * @param account TODO A COMMENTER
   * @param sessionId Identifiant de la session liée à la connexion
   * @param remanent TODO A COMMENTER
   * @param ipAddress Adresse IP de connexion
   * @param startDate Date de début de connexion
   * @param disconnectionReason Raison de fin de connexion
   * @throws UserException TODO A COMMENTER
   */
  public ConnectionHistoryAbstractStub(ConnectionData data, AccountAbstractStub account/*, String sessionId, boolean remanent,
                                       IpAddress ipAddress, Bid4WinDate startDate,
                                       DisconnectionReason disconnectionReason*/)
         throws UserException
  {
    super(data, account/*, sessionId, remanent, ipAddress, startDate, disconnectionReason*/);
  }
  /**
   *
   * TODO A COMMENTER
   * @param connection TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  /*public ConnectionHistoryAbstractStub(ConnectionAbstractStub connection)
         throws UserException
  {
    super(connection);
  }*/
}
