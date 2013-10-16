package com.bid4win.commons.service.connection;

import com.bid4win.commons.core.reference.MessageRef.ConnectionRef;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthenticationException;
import com.bid4win.commons.persistence.entity.account.security.exception.SessionException;
import com.bid4win.commons.persistence.entity.connection.ConnectionAbstract;
import com.bid4win.commons.persistence.entity.connection.DisconnectionReason;
import com.bid4win.commons.persistence.entity.connection.IpAddress;

/**
 * Cette classe permet de gérer les données liées à la session courante<BR>
 * <BR>
 * @param <DATA> Définition de la classe de conteneur de données de la session courante<BR>
 * @param <ACCOUNT> Definition du type de compte utilisateur utilisé par le projet<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class SessionHandlerAbstract<DATA extends SessionDataAbstract<ACCOUNT, CONNECTION>,
                                             ACCOUNT extends AccountAbstract<ACCOUNT>,
                                             CONNECTION extends ConnectionAbstract<CONNECTION, ?, ACCOUNT>>
{
  /** Clé avec laquelle sont conservées les données de la session courante */
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
   * Cette méthode permet de récupérer le potentiel conteneur de données de la
   * session courante
   * @return Le conteneur de données de la session courante potentiellement nul
   * si aucune n'est définie
   */
  @SuppressWarnings("unchecked")
  private DATA findSessionData()
  {
    return this.SESSION_DATA.get();
  }
  /**
   * Cette méthode permet de récupérer le conteneur de données de la session courante
   * @return Le conteneur de données de la session courante
   * @throws SessionException Si aucune session n'est définie
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
   * Cette méthode doit être définie afin de construire un conteneur de données
   * @param sessionId Identifiant de la session du conteneur de données à créer
   * @param ipAddress Adresse IP liée à la session
   * @return Un nouveau conteneur de données
   * @throws SessionException Si l'identifiant de session en argument est invalide
   */
  protected abstract DATA createSessionData(String sessionId, IpAddress ipAddress)
            throws SessionException;

  /**
   * Getter de l'identifiant défini pour la session courante
   * @return L'identifiant défini pour la session courante
   * @throws SessionException Si aucune session n'est définie
   */
  public String getSessionId() throws SessionException
  {
    return this.getSessionData().getSessionId();
  }
  /**
   * Getter de l'adresse IP de connexion liée à la session courante
   * @return L'adresse IP de connexion liée à la session courante
   * @throws SessionException Si aucune session n'est définie
   */
  public IpAddress getIpAddress() throws SessionException
  {
    return this.getSessionData().getIpAddress();
  }
  /**
   * Getter du compte utilisateur potentiellement défini comme connecté sur la
   * session courante
   * @return Le compte utilisateur potentiellement défini comme connecté sur la
   * session courante
   * @throws SessionException Si aucune session n'est définie
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
   * Cette méthode permet de définir la session liée au thread courant
   * @param sessionId Identifiant de la session liée au thread courant
   * @param ipAddress Adresse IP de connexion liée à la session courante
   * @return TODO A COMMENTER
   * @throws SessionException Si une session différente est déjà définie
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
   * Cette méthode permet de retirer le lien potentiellement défini entre une session
   * et le thread courant
   */
  public void stopSession()
  {
    this.SESSION_DATA.remove();
  }

  /**
   * Permet de définir le compte utilisateur connecté
   * @param account Compte utilisateur connecté
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthenticationException Si un compte utilisateur différent est déjà
   * défini ou si celui en paramètre est nul
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
   * Permet de retirer le compte utilisateur connecté
   * @throws SessionException Si aucune session n'est définie
   */
  public void disconnect() throws SessionException
  {
    this.getSessionData().setConnection(null);
  }
}
