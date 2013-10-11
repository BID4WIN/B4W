package com.bid4win.commons.service.connection;

import org.apache.log4j.MDC;

import com.bid4win.commons.core.reference.MessageRef.AccountRef;
import com.bid4win.commons.core.reference.MessageRef.ConnectionRef;
import com.bid4win.commons.core.security.IdGenerator;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthenticationException;
import com.bid4win.commons.persistence.entity.account.security.exception.SessionException;
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
public abstract class SessionHandlerAbstract<DATA extends SessionDataAbstract<ACCOUNT>,
                                             ACCOUNT extends AccountAbstract<ACCOUNT>>
{
  /** Clé avec laquelle sont conservées les données de la session courante */
  private final static String SESSION_DATA_ID = IdGenerator.generateId(16);

  /**
   * Cette méthode permet de récupérer le potentiel conteneur de données de la
   * session courante
   * @return Le conteneur de données de la session courante potentiellement nul
   * si aucune n'est définie
   */
  @SuppressWarnings("unchecked")
  private DATA findSessionData()
  {
    return (DATA)MDC.get(SessionHandlerAbstract.SESSION_DATA_ID);
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
      throw new SessionException(ConnectionRef.CONNECTION_SESSION_UNDEFINED_ERROR);
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
    return this.getSessionData().getAccount();
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
      MDC.put(SessionHandlerAbstract.SESSION_DATA_ID, data);
    }
    else if(!data.getSessionId().equals(sessionId) || !data.getIpAddress().equals(ipAddress))
    {
     throw new SessionException(ConnectionRef.CONNECTION_SESSION_DEFINED_ERROR);
    }
    return data;
  }
  /**
   * Cette méthode permet de retirer le lien potentiellement défini entre une session
   * et le thread courant
   */
  public void stopSession()
  {
    MDC.remove(SessionHandlerAbstract.SESSION_DATA_ID);
  }

  /**
   * Permet de définir le compte utilisateur connecté
   * @param account Compte utilisateur connecté
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthenticationException Si un compte utilisateur différent est déjà
   * défini ou si celui en paramètre est nul
   */
  protected void connect(ACCOUNT account) throws SessionException, AuthenticationException
  {
    if(account == null)
    {
      throw new AuthenticationException(AccountRef.ACCOUNT_MISSING_ERROR,
                                        DisconnectionReason.NONE);
    }
    DATA data = this.getSessionData();
    if(data.getAccount() != null && !data.getAccount().getId().equals(account.getId()))
    {
      throw new AuthenticationException(AccountRef.ACCOUNT_INVALID_ERROR,
                                        DisconnectionReason.NONE);
    }
    data.setAccount(account);
  }
  /**
   * Permet de retirer le compte utilisateur connecté
   * @throws SessionException Si aucune session n'est définie
   */
  public void disconnect() throws SessionException
  {
    this.getSessionData().setAccount(null);
  }
}
