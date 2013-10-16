package com.bid4win.commons.service.connection;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.reference.MessageRef.ConnectionRef;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.security.exception.SessionException;
import com.bid4win.commons.persistence.entity.connection.ConnectionAbstract;
import com.bid4win.commons.persistence.entity.connection.IpAddress;

/**
 * Cette classe correspond au conteneur de donn�es d'une session courante<BR>
 * <BR>
 * @param <ACCOUNT> Definition de type de compte utilisateur utilis� par le projet<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class SessionDataAbstract<ACCOUNT extends AccountAbstract<ACCOUNT>,
                                 CONNECTION extends ConnectionAbstract<CONNECTION, ?, ACCOUNT>>
{
  /** Identifiant de la session du conteneur de donn�es */
  private String sessionId = null;
  /** Adresse IP li�e � la session */
  private IpAddress ipAddress = null;
  private CONNECTION connection = null;
  /** Compte utilisateur potentiellement connect� */
  //private ACCOUNT account = null;

  /**
   * Constructeur
   * @param sessionId Identifiant de la session du conteneur de donn�es
   * @param ipAddress Adresse IP li�e � la session
   * @throws SessionException Si l'identifiant de session en argument est invalide
   */
  public SessionDataAbstract(String sessionId, IpAddress ipAddress) throws SessionException
  {
    super();
    this.setSessionId(sessionId);
    this.setIpAddress(ipAddress);
  }

  /**
   * Getter de l'identifiant de la session du conteneur de donn�es
   * @return L'identifiant de la session du conteneur de donn�es
   */
  public String getSessionId()
  {
    return this.sessionId;
  }
  /**
   * Setter de l'identifiant de la session du conteneur de donn�es
   * @param sessionId Identifiant de la session du conteneur de donn�es � positionner
   * @throws SessionException Si l'identifiant de session en argument est invalide
   */
  private void setSessionId(String sessionId) throws SessionException
  {
    sessionId = UtilString.trimNotNull(sessionId);
    if(sessionId.equals(UtilString.EMPTY))
    {
      throw new SessionException(ConnectionRef.SESSION_UNDEFINED_ERROR);
    }
    this.sessionId = sessionId;
  }

  /**
   * Getter de l'adresse IP li�e � la session
   * @return L'adresse IP li�e � la session
   */
  public IpAddress getIpAddress()
  {
    return this.ipAddress;
  }
  /**
   * Setter de l'adresse IP li�e � la session
   * @param ipAddress Adresse IP li�e � la session � positionner
   * @throws SessionException Si l'adresse IP est nulle
   */
  private void setIpAddress(IpAddress ipAddress) throws SessionException
  {
    if(ipAddress == null)
    {
      throw new SessionException(ConnectionRef.IP_MISSING_ERROR);
    }
    this.ipAddress = ipAddress;
  }

  public CONNECTION getConnection()
  {
    return this.connection;
  }
  protected void setConnection(CONNECTION connection)
  {
    this.connection = connection;
  }
  /**
   * Getter du compte utilisateur potentiellement connect�
   * @return Le compte utilisateur potentiellement connect�
   */
 /* public ACCOUNT getAccount()
  {
    return this.account;
  }
  /**
   * Setter du compte utilisateur potentiellement connect�
   * @param account Compte utilisateur potentiellement connect� � positionner
   */
 /* protected void setAccount(ACCOUNT account)
  {
    this.account = account;
  }

  /**
   * Getter de l'identifiant du compte utilisateur potentiellement connect�
   * @return L'identifiant du compte utilisateur potentiellement connect�
   */
 /* protected String getAccountId()
  {
    ACCOUNT account = this.getAccount();
    if(account == null)
    {
      return null;
    }
    return account.getId();
  }*/
}
