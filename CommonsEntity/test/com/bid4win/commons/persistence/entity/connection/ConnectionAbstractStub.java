package com.bid4win.commons.persistence.entity.connection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.bid4win.commons.core.Bid4WinDate;
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
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class ConnectionAbstractStub
       extends ConnectionAbstract<ConnectionAbstractStub, ConnectionHistoryAbstractStub, AccountAbstractStub>
{
  /** TODO A COMMENTER */
  @Transient private Bid4WinDate startDate = null;

  /**
   * Constructeur pour création par introspection
   */
  protected ConnectionAbstractStub()
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
  public ConnectionAbstractStub(String sessionId, AccountAbstractStub account,
                                IpAddress ipAddress, boolean remanent)
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
  protected ConnectionHistoryAbstractStub createHistory() throws UserException
  {
    return new ConnectionHistoryAbstractStub(this);
  }

  /**
   *
   * TODO A COMMENTER
   * @param disconnectionReason {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.connection.ConnectionAbstract#endConnection(com.bid4win.commons.persistence.entity.connection.DisconnectionReason)
   */
  @Override
  public ConnectionAbstractStub endConnection(DisconnectionReason disconnectionReason)
  {
    if(super.getStartDate() == null)
    {
      this.setStartDate(new Bid4WinDate());
    }
    return super.endConnection(disconnectionReason);
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.connection.ConnectionAbstract#getStartDate()
   */
  @Override
  public Bid4WinDate getStartDate()
  {
    if(super.getStartDate() != null)
    {
      return super.getStartDate();
    }
    return this.startDate;
  }
  /**
   *
   * TODO A COMMENTER
   * @param startDate TODO A COMMENTER
   */
  public void setStartDate(Bid4WinDate startDate)
  {
    this.startDate = startDate;
  }
}
