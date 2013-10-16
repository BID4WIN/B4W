package com.bid4win.commons.persistence.entity.connection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Transient;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.comparator.Bid4WinComparator;
import com.bid4win.commons.core.comparator.Bid4WinObjectComparator;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.ConnectionRef;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.Bid4WinEmbeddable;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class ConnectionData extends Bid4WinEmbeddable<ConnectionData>
{
  /** Format des identifiants de session */
  public final static String SESSION_ID_PATTERN = "[0-9A-F]{32}";

  // TODO voir pour redefinition des colonnes idsession et startdate dans connection

  /** Identifiant de la session liée à la connexion */
  @Transient private String sessionId = null;
  /** Flag indiquant la rémanence de la connexion */
  @Transient private boolean remanent = false;
  /** Adresse IP de connexion */
  @Transient private IpAddress ipAddress = null;
  /** Date de début de connexion */
  @Transient private Bid4WinDate startDate = null;
  /** Raison de fin de connexion */
  @Transient private DisconnectionReason disconnectionReason = null;

  /**
   * Constructeur pour création par introspection
   */
  protected ConnectionData()
  {
    super();
  }
  /**
   * Constructeur
   * @param sessionId Identifiant de la session de connexion
   * @param remanent Flag indiquant si la rémanence est activée sur la connexion
   * @param ipAddress Adresse IP de connexion
   * @throws UserException Si l'identifiant de session, ou l'adresse IP de connexion
   * en argument est nul
   */
  public ConnectionData(String sessionId, IpAddress ipAddress, boolean remanent)
            throws UserException
  {
    this.defineSessionId(sessionId);
    this.defineIpAddress(ipAddress);
    this.setRemanent(remanent);
  }
  /**
   *
   * TODO A COMMENTER
   * @param data TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  private ConnectionData(ConnectionData data) throws UserException
  {
    this(data.getSessionId(), data.getIpAddress(), data.isRemanent());
    this.defineDisconnectionReason(data.getDisconnectionReason());
    this.defineStartDate(data.getStartDate());
  }

  /**
   * Redéfini l'égalité interne de  TODO par l'égalité de leur contenu
   * @param toBeCompared {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#equalsInternal(com.bid4win.commons.persistence.entity.Bid4WinEmbeddable)
   */
  @Override
  protected boolean equalsInternal(ConnectionData toBeCompared)
  {
    return super.equalsInternal(toBeCompared) &&
           Bid4WinComparator.getInstance().equals(this.getSessionId(),
                                                  toBeCompared.getSessionId()) &&
           this.isRemanent() == toBeCompared.isRemanent() &&
           Bid4WinObjectComparator.getInstanceObject().equals(this.getIpAddress(),
                                                              toBeCompared.getIpAddress()) &&
           Bid4WinComparator.getInstance().equals(this.getStartDate(),
                                                  toBeCompared.getStartDate()) &&
           Bid4WinObjectComparator.getInstanceObject().equals(this.getDisconnectionReason(),
                                                              toBeCompared.getDisconnectionReason());
  }
  /**
   * Redéfini la transformation en chaîne de caractères de TODO lisiblement
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#render()
   */
  @Override
  public StringBuffer render()
  {
    // Effectue le rendu de base d'un objet
    StringBuffer buffer = super.render();
    // Ajoute les éléments de TODO
    buffer.append(this.getIpAddress().render());
    if(this.isRemanent())
    {
      buffer.append(" REMANENT");
    }
    buffer.append(" ").append(this.getSessionId());
    if(this.getStartDate() != null)
    {
      buffer.append(" START_DATE=").append(this.getStartDate().formatYYYYIMMIDD());
    }
    if(this.getDisconnectionReason() != null)
    {
      buffer.append(" ").append(this.getDisconnectionReason().render());
    }
    // Retourne le rendu
    return buffer;
  }

  /**
   * Cette fonction permet de créer les données correspondant à la fin d'une connexion
   * en conservant son état de rémanence
   * @param disconnectionReason Raison de fin de la connexion
   * @return Les données correspondant à la fin d'une connexion
   * @throws UserException
   */
  public ConnectionData endConnection(DisconnectionReason disconnectionReason)
         throws UserException
  {
    ConnectionData data = new ConnectionData(this);
    data.defineDisconnectionReason(disconnectionReason);
    return data;
  }
  /**
   * Cette fonction permet de créer les données correspondant à l'arrêt d'une connexion
   * en retirant son état de rémanence
   * @param disconnectionReason Raison d'arrêt de la connexion
   * @return Les données correspondant à l'arrêt d'une connexion
   * @throws UserException
   */
  public ConnectionData stopConnection(DisconnectionReason disconnectionReason)
         throws UserException
  {
    ConnectionData data = this.endConnection(disconnectionReason);
    data.setRemanent(false);
    return data;
  }

  /**
   * Cette méthode permet de définir l'identifiant de la session liée à la connexion
   * @param sessionId Définition de l'identifiant de la session liée à la connexion
   * @throws ProtectionException Si l'historique de connexion courant est protégé
   * @throws UserException Si on défini un identifiant de session invalide
   */
  private void defineSessionId(String sessionId) throws ProtectionException, UserException
  {
    // Vérifie la protection de la connexion courante
    this.checkProtection();
    sessionId = UtilString.trimNotNull(sessionId).toUpperCase();
    this.setSessionId(UtilString.checkPattern("sessionId", sessionId, SESSION_ID_PATTERN,
                                              ConnectionRef.SESSION_UNDEFINED_ERROR));
  }
  /**
   * Cette méthode permet de définir l'adresse IP de connexion
   * @param ipAddress Définition de l'adresse IP de connexion
   * @throws ProtectionException Si l'historique de connexion courant est protégé
   * @throws UserException Si on défini une adresse IP nulle
   */
  private void defineIpAddress(IpAddress ipAddress) throws ProtectionException, UserException
  {
    // Vérifie la protection de la connexion courante
    this.checkProtection();
    UtilObject.checkNotNull("ipAddress", ipAddress, ConnectionRef.IP_MISSING_ERROR);
    this.setIpAddress(ipAddress);
  }
  /**
   * Cette méthode permet de définir la date de début de connexion
   * @param startDate Définition de la date de début de connexion
   * @throws ProtectionException Si l'historique de connexion courant est protégé
   * @throws UserException Si on défini une date de début de connexion nulle
   */
  private void defineStartDate(Bid4WinDate startDate)
          throws ProtectionException, UserException
  {
    // Vérifie la protection de la connexion courante
    this.checkProtection();
    UtilObject.checkNotNull("startDate", startDate, ConnectionRef.CONNECTION_INVALID_ERROR);
    this.setStartDate(startDate);
  }
  /**
   * Cette méthode permet de définir la raison de fin de connexion
   * @param disconnectionReason Définition de la raison de fin de connexion
   * @throws ProtectionException Si l'historique de connexion courant est protégé
   */
  private void defineDisconnectionReason(DisconnectionReason disconnectionReason)
          throws ProtectionException
  {
    // Vérifie la protection de la connexion courante
    this.checkProtection();
    if(disconnectionReason == null)
    {
      disconnectionReason = DisconnectionReason.AUTO;
    }
    this.setDisconnectionReason(disconnectionReason);
  }


  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de l'identifiant de la session liée à la connexion
   * @return L'identifiant de la session liée à la connexion
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "SESSION_ID", length = 32 , nullable = false, unique = false)
  public String getSessionId()
  {
    return this.sessionId;
  }
  /**
   * Setter de l'identifiant de la session liée à la connexion
   * @param sessionId Identifiant de la session liée à la connexion à positionner
   */
  private void setSessionId(String sessionId)
  {
    this.sessionId = sessionId;
  }

  /**
   * Getter du flag indiquant la rémanence de la connexion
   * @return Le flag indiquant la rémanence de la connexion
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "REMAMENT", nullable = false, unique = false)
  public boolean isRemanent()
  {
    return this.remanent;
  }
  /**
   * Setter du flag indiquant la rémanence de la connexion
   * @param remanent Flag indiquant la rémanence de la connexion à positionner
   */
  private void setRemanent(boolean remanent)
  {
    this.remanent = remanent;
  }

  /**
   * Getter de l'adresse IP de connexion
   * @return L'adresse IP de connexion
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Embedded()
  public IpAddress getIpAddress()
  {
    return this.ipAddress;
  }
  /**
   * Setter de l'adresse IP de connexion
   * @param ipAddress Adresse IP de connexion à positionner
   */
  private void setIpAddress(IpAddress ipAddress)
  {
    this.ipAddress = ipAddress;
  }

  /**
   * Getter de la date de début de connexion
   * @return La date de début de connexion
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "START_DATE", nullable = false, unique = false)
  public Bid4WinDate getStartDate()
  {
    return this.startDate;
  }
  /**
   * Setter de la date de début de connexion
   * @param startDate Date de début de connexion à positionner
   */
  private void setStartDate(Bid4WinDate startDate)
  {
    this.startDate = startDate;
  }

  /**
   * Getter de la raison de fin de connexion
   * @return La raison de fin de connexion
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "REASON", nullable = false, unique = false)
  public DisconnectionReason getDisconnectionReason()
  {
    return this.disconnectionReason;
  }
  /**
   * Setter de la raison de fin de connexion
   * @param disconnectionReason Raison de fin de connexion à positionner
   */
  private void setDisconnectionReason(DisconnectionReason disconnectionReason)
  {
    this.disconnectionReason = disconnectionReason;
  }
}
