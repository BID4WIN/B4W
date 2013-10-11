package com.bid4win.persistence.entity.connection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Cacheable;
import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.connection.ConnectionAbstract;
import com.bid4win.commons.persistence.entity.connection.IpAddress;
import com.bid4win.persistence.entity.account.Account;

/**
*
* TODO A COMMENTER<BR>
* <BR>
* @author Emeric Fillâtre
*/
@Entity
@Access(AccessType.FIELD)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Connection extends ConnectionAbstract<Connection, ConnectionHistory, Account>
{
  /**
   * Constructeur pour création par introspection
   */
  protected Connection()
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
  public Connection(String sessionId, Account account, IpAddress ipAddress, boolean remanent)
         throws UserException
  {
    super(sessionId, account, ipAddress, remanent);
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.connection.ConnectionAbstract#createHistory()
   */
  @Override
  protected ConnectionHistory createHistory() throws UserException
  {
    return new ConnectionHistory(this);
  }

}
