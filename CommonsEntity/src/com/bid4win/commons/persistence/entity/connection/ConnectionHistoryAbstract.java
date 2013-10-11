package com.bid4win.commons.persistence.entity.connection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.ConnectionRef;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultipleAutoID;

/**
 * Cette classe d�fini un historique de connexion<BR>
 * <BR>
 * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
 * @param <ACCOUNT> D�finition du type de compte utilisateur de la connexion<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public class ConnectionHistoryAbstract<CLASS extends ConnectionHistoryAbstract<CLASS, ACCOUNT>,
                                       ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends AccountBasedEntityMultipleAutoID<CLASS, ACCOUNT>
{
  /** Identifiant de la session li�e � la connexion */
  @Transient private String sessionId = null;
  /** Flag indiquant la r�manence de la connexion */
  @Transient private boolean remanent = false;
  /** Adresse IP de connexion */
  @Transient private IpAddress ipAddress = null;
  /** Date de d�but de connexion */
  @Transient private Bid4WinDate startDate = null;
  /** Raison de fin de connexion */
  @Transient private DisconnectionReason disconnectionReason = null;
  /** Flag indiquant si une r�utilisation de la r�manence a d�j� �t� tent�e */
  @Transient private boolean reuseAttempted = false;

  /**
   * Constructeur pour cr�ation par introspection
   */
  protected ConnectionHistoryAbstract()
  {
    super();
  }
  /**
   * Constructeur � n'utiliser que pour les tests pour �viter d'avoir � d'abord
   * cr�er une connection
   * @param account Compte utilisateur de connexion
   * @param sessionId Identifiant de la session li�e � la connexion
   * @param remanent Flag indiquant si la r�manence �tait activ�e sur la connexion
   * @param ipAddress Adresse IP de connexion
   * @param startDate Date de d�but de connexion
   * @param disconnectionReason Raison de fin de connexion
   * @throws UserException Si les param�tres sont invalides
   */
  protected ConnectionHistoryAbstract(ACCOUNT account, String sessionId, boolean remanent,
                                      IpAddress ipAddress, Bid4WinDate startDate,
                                      DisconnectionReason disconnectionReason)
         throws UserException
  {
    super(account);
    this.defineSessionId(sessionId);
    this.setRemanent(remanent);
    this.defineIpAddress(ipAddress);
    this.defineStart(startDate);
    this.defineDisconnectionReason(disconnectionReason);
  }
  /**
   * Constructeur � partir d'une connexion � historiser
   * @param connection Connexion dont il faut construire l'historique
   * @throws UserException Si les param�tres sont invalides
   */
  protected ConnectionHistoryAbstract(ConnectionAbstract<?, ?, ACCOUNT> connection)
         throws UserException
  {
    this(connection.getAccount(), connection.getId(), connection.isRemanent(),
         connection.getIpAddress(), connection.getStartDate(),
         connection.getDisconnectionReason());
  }

  /**
   * Cette m�thode permet de d�finir l'identifiant de la session li�e � la connexion
   * @param sessionId D�finition de l'identifiant de la session li�e � la connexion
   * @throws ProtectionException Si l'historique de connexion courant est prot�g�
   */
  private void defineSessionId(String sessionId) throws ProtectionException
  {
    // V�rifie la protection de la connexion courante
    this.checkProtection();
    this.setSessionId(UtilString.trimNotNull(sessionId));
  }
  /**
   * Cette m�thode permet de d�finir l'adresse IP de connexion
   * @param ipAddress D�finition de l'adresse IP de connexion
   * @throws ProtectionException Si l'historique de connexion courant est prot�g�
   * @throws UserException Si on d�fini une adresse IP nulle
   */
  private void defineIpAddress(IpAddress ipAddress) throws ProtectionException, UserException
  {
    // V�rifie la protection de la connexion courante
    this.checkProtection();
    this.setIpAddress(UtilObject.checkNotNull("ipAddress", ipAddress,
                      ConnectionRef.CONNECTION_IP_MISSING_ERROR));
  }
  /**
   * Cette m�thode permet de d�finir la raison de fin de connexion
   * @param disconnectionReason D�finition de la raison de fin de connexion
   * @throws ProtectionException Si l'historique de connexion courant est prot�g�
   * @throws UserException Si on d�fini une raison de fin de connexion nulle
   */
  private void defineDisconnectionReason(DisconnectionReason disconnectionReason)
          throws ProtectionException, UserException
  {
    // V�rifie la protection de la connexion courante
    this.checkProtection();
    this.setDisconnectionReason(UtilObject.checkNotNull("disconnectionReason", disconnectionReason,
                                                        ConnectionRef.CONNECTION_INVALID_ERROR));
  }
  /**
   * Cette m�thode permet de d�finir la date de d�but de connexion
   * @param startDate D�finition de la date de d�but de connexion
   * @throws ProtectionException Si l'historique de connexion courant est prot�g�
   * @throws UserException Si on d�fini une date de d�but de connexion nulle
   */
  private void defineStart(Bid4WinDate startDate)
          throws ProtectionException, UserException
  {
    // V�rifie la protection de la connexion courante
    this.checkProtection();
    UtilObject.checkNotNull("startDate", startDate, ConnectionRef.CONNECTION_INVALID_ERROR);
    this.setStartDate(startDate);
  }
  /**
   * Cette m�thode permet de noter la r�manence de la connexion associ�e � l'historique
   * courante a tent� d'�tre utilis�e
   * @return L'historique de connexion notifi� de la tentative d'utilisation de
   * la r�manence de la connexion associ�e
   */
  @SuppressWarnings("unchecked")
  public CLASS reuse()
  {
    this.setReuseAttempted(true);
    return (CLASS)this;
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de l'identifiant de la session li�e � la connexion
   * @return L'identifiant de la session li�e � la connexion
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "SESSION_ID", length = 32 , nullable = false, unique = false)
  public String getSessionId()
  {
    return this.sessionId;
  }
  /**
   * Setter de l'identifiant de la session li�e � la connexion
   * @param sessionId Identifiant de la session li�e � la connexion � positionner
   */
  private void setSessionId(String sessionId)
  {
    this.sessionId = sessionId;
  }

  /**
   * Getter du flag indiquant la r�manence de la connexion
   * @return Le flag indiquant la r�manence de la connexion
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "REMAMENT", nullable = false, unique = false)
  public boolean isRemanent()
  {
    return this.remanent;
  }
  /**
   * Setter du flag indiquant la r�manence de la connexion
   * @param remanent Flag indiquant la r�manence de la connexion � positionner
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
   * @param ipAddress Adresse IP de connexion � positionner
   */
  private void setIpAddress(IpAddress ipAddress)
  {
    this.ipAddress = ipAddress;
  }

  /**
   * Getter de la date de d�but de connexion
   * @return La date de d�but de connexion
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "START_DATE", nullable = false, unique = false)
  public Bid4WinDate getStartDate()
  {
    return this.startDate;
  }
  /**
   * Setter de la date de d�but de connexion
   * @param startDate Date de d�but de connexion � positionner
   */
  private void setStartDate(Bid4WinDate startDate)
  {
    this.startDate = startDate;
  }

  /**
   * Getter de la date de fin de connexion qui se basera sur la date de cr�ation
   * de l'historique
   * @return La date de fin de connexion
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "CREATE_DATE", nullable = false, unique = false,
          // La date de fin de la connexion correspond � la date de cr�ation de son historique
          insertable = false, updatable = false)
  public Bid4WinDate getEndDate()
  {
    return this.getCreateDate();
  }
  /**
   * Setter inutilis� de la date de fin de connexion
   * @param endDate Date de fin de connexion
   */
  @SuppressWarnings("unused")
  private void setEndDate(Bid4WinDate endDate)
  {
    // Inutilis� car la date de fin de connexion correspond � la date de cr�ation
    // de son historique
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
   * @param disconnectionReason Raison de fin de connexion � positionner
   */
  private void setDisconnectionReason(DisconnectionReason disconnectionReason)
  {
    this.disconnectionReason = disconnectionReason;
  }

  /**
   * Getter du flag indiquant si une r�utilisation de la r�manence a d�j� �t� tent�e
   * @return Le flag indiquant si une r�utilisation de la r�manence a d�j� �t� tent�e
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "REUSE_ATTEMPTED", nullable = false, unique = false)
  public boolean isReuseAttempted()
  {
    return this.reuseAttempted;
  }
  /**
   * Setter du flag indiquant si une r�utilisation de la r�manence a d�j� �t� tent�e
   * @param reuseAttempted Flag indiquant si une r�utilisation de la r�manence a
   * d�j� �t� tent�e � positionner
   */
  private void setReuseAttempted(boolean reuseAttempted)
  {
    this.reuseAttempted = reuseAttempted;
  }
}
