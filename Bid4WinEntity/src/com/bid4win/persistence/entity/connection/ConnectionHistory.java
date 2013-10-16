package com.bid4win.persistence.entity.connection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.connection.ConnectionData;
import com.bid4win.commons.persistence.entity.connection.ConnectionHistoryAbstract;
import com.bid4win.persistence.entity.account.Account;

/**
*
* TODO A COMMENTER<BR>
* <BR>
* @author Emeric Fillâtre
*/
@Entity
@Access(AccessType.FIELD)
public class ConnectionHistory
       extends ConnectionHistoryAbstract<ConnectionHistory, Account>
{
  /**
   * Constructeur pour création par introspection
   */
  protected ConnectionHistory()
  {
    super();
  }
  /**
   * Constructeur à n'utiliser que pour les tests pour éviter d'avoir à d'abord
   * créer une connection
   * @param account Compte utilisateur de connexion
   * @param sessionId Identifiant de la session liée à la connexion
   * @param remanent Flag indiquant si la rémanence était activée sur la connexion
   * @param ipAddress Adresse IP de connexion
   * @param startDate Date de début de connexion
   * @param disconnectionReason Raison de fin de connexion
   * @throws UserException Si les paramètres sont invalides
   */
  public ConnectionHistory(ConnectionData data, Account account/*, String sessionId, boolean remanent,
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
  public ConnectionHistory(Connection connection)
         throws UserException
  {
    super(connection.getData(), connection.getAccount());
  }

}
