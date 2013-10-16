package com.bid4win.commons.service.connection;

import com.bid4win.commons.core.reference.MessageRef.ConnectionRef;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthenticationException;
import com.bid4win.commons.persistence.entity.account.security.exception.SessionException;
import com.bid4win.commons.persistence.entity.connection.ConnectionAbstract;
import com.bid4win.commons.persistence.entity.connection.DisconnectionReason;
import com.bid4win.commons.persistence.entity.connection.IpAddress;

/**
 * Cette classe permet de g�rer les donn�es li�es � la session courante<BR>
 * <BR>
 * @param <DATA> D�finition de la classe de conteneur de donn�es de la session courante<BR>
 * @param <ACCOUNT> Definition du type de compte utilisateur utilis� par le projet<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class SessionHandlerAbstract<DATA extends SessionDataAbstract<ACCOUNT, CONNECTION>,
                                             ACCOUNT extends AccountAbstract<ACCOUNT>,
                                             CONNECTION extends ConnectionAbstract<CONNECTION, ?, ACCOUNT>>
{
  /** Cl� avec laquelle sont conserv�es les donn�es de la session courante */
  private final ThreadLocal<DATA> SESSION_DATA = new ThreadLocal<DATA>();

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public boolean isConnected()
  {
    DATA data = this.findSessionData();
    return data != null && data.getConnection() != null;
  }
  /**
   * Cette m�thode permet de r�cup�rer le potentiel conteneur de donn�es de la
   * session courante
   * @return Le conteneur de donn�es de la session courante potentiellement nul
   * si aucune n'est d�finie
   */
  @SuppressWarnings("unchecked")
  private DATA findSessionData()
  {
    return this.SESSION_DATA.get();
  }
  /**
   * Cette m�thode permet de r�cup�rer le conteneur de donn�es de la session courante
   * @return Le conteneur de donn�es de la session courante
   * @throws SessionException Si aucune session n'est d�finie
   */
  public DATA getSessionData() throws SessionException
  {
    DATA data = this.findSessionData();
    if(data == null)
    {
      throw new SessionException(ConnectionRef.SESSION_UNDEFINED_ERROR);
    }
    return data;
  }
  /**
   * Cette m�thode doit �tre d�finie afin de construire un conteneur de donn�es
   * @param sessionId Identifiant de la session du conteneur de donn�es � cr�er
   * @param ipAddress Adresse IP li�e � la session
   * @return Un nouveau conteneur de donn�es
   * @throws SessionException Si l'identifiant de session en argument est invalide
   */
  protected abstract DATA createSessionData(String sessionId, IpAddress ipAddress)
            throws SessionException;

  /**
   * Getter de l'identifiant d�fini pour la session courante
   * @return L'identifiant d�fini pour la session courante
   * @throws SessionException Si aucune session n'est d�finie
   */
  public String getSessionId() throws SessionException
  {
    return this.getSessionData().getSessionId();
  }
  /**
   * Getter de l'adresse IP de connexion li�e � la session courante
   * @return L'adresse IP de connexion li�e � la session courante
   * @throws SessionException Si aucune session n'est d�finie
   */
  public IpAddress getIpAddress() throws SessionException
  {
    return this.getSessionData().getIpAddress();
  }
  /**
   * Getter du compte utilisateur potentiellement d�fini comme connect� sur la
   * session courante
   * @return Le compte utilisateur potentiellement d�fini comme connect� sur la
   * session courante
   * @throws SessionException Si aucune session n'est d�finie
   */
  public ACCOUNT getConnectedAccount() throws SessionException
  {
    CONNECTION connection = this.getConnection();
    if(connection != null)
    {
      return connection.getAccount();
    }
    return null;
  }
  public CONNECTION getConnection() throws SessionException
  {
    return this.getSessionData().getConnection();
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public boolean isSessionStarted()
  {
    return this.findSessionData() != null;
  }
  /**
   * Cette m�thode permet de d�finir la session li�e au thread courant
   * @param sessionId Identifiant de la session li�e au thread courant
   * @param ipAddress Adresse IP de connexion li�e � la session courante
   * @return TODO A COMMENTER
   * @throws SessionException Si une session diff�rente est d�j� d�finie
   */
  public DATA startSession(String sessionId, IpAddress ipAddress) throws SessionException
  {
    DATA data = this.findSessionData();
    if(data == null)
    {
      data = this.createSessionData(sessionId, ipAddress);
      this.SESSION_DATA.set(data);
    }
    else if(!data.getSessionId().equals(sessionId) || !data.getIpAddress().equals(ipAddress))
    {
     throw new SessionException(ConnectionRef.SESSION_DEFINED_ERROR);
    }
    return data;
  }
  /**
   * Cette m�thode permet de retirer le lien potentiellement d�fini entre une session
   * et le thread courant
   */
  public void stopSession()
  {
    this.SESSION_DATA.remove();
  }

  /**
   * Permet de d�finir le compte utilisateur connect�
   * @param account Compte utilisateur connect�
   * @throws SessionException Si aucune session n'est d�finie
   * @throws AuthenticationException Si un compte utilisateur diff�rent est d�j�
   * d�fini ou si celui en param�tre est nul
   */
  protected void connect(CONNECTION connection) throws SessionException, AuthenticationException
  {
    if(connection == null)
    {
      throw new AuthenticationException(ConnectionRef.CONNECTION_INVALID_ERROR,
                                        DisconnectionReason.NONE);
    }
    DATA data = this.getSessionData();
    if(data.getConnection() != null && !data.getConnection().getId().equals(connection.getId()))
    {
      throw new AuthenticationException(ConnectionRef.CONNECTION_INVALID_ERROR,
                                        DisconnectionReason.NONE);
    }
    data.setConnection(connection);
  }
  /**
   * Permet de retirer le compte utilisateur connect�
   * @throws SessionException Si aucune session n'est d�finie
   */
  public void disconnect() throws SessionException
  {
    this.getSessionData().setConnection(null);
  }
}
