package com.bid4win.commons.persistence.entity.connection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.comparator.Bid4WinComparator;
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
  /** Donn�es de la connexion historis�es */
  @Transient private ConnectionData data = null;

  /** Identifiant de la session li�e � la connexion */
//  @Transient private String sessionId = null;
  /** Flag indiquant la r�manence de la connexion */
//  @Transient private boolean remanent = false;
  /** Adresse IP de connexion */
//  @Transient private IpAddress ipAddress = null;
  /** Date de d�but de connexion */
//  @Transient private Bid4WinDate startDate = null;
  /** Raison de fin de connexion */
//  @Transient private DisconnectionReason disconnectionReason = null;

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
 /* protected ConnectionHistoryAbstract(ACCOUNT account, String sessionId, boolean remanent,
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
  }*/
  /**
   * Constructeur � partir d'une connexion � historiser
   * @param connection Connexion dont il faut construire l'historique
   * @throws UserException Si les param�tres sont invalides
   */
  protected ConnectionHistoryAbstract(//ConnectionAbstract<?, ?, ACCOUNT> connection)
      ConnectionData data, ACCOUNT account)
         throws UserException
  {
    super(account);
    this.defineData(data);
    /*this(connection.getAccount(), connection.getId(), connection.isRemanent(),
         connection.getIpAddress(), connection.getStartDate(),
         connection.getDisconnectionReason());*/
  }

  /**
   * Red�fini l'�quivalence interne de deux historiques de connexions sans prise
   * en compte de leurs relations afin d'y ajouter le test de leurs donn�es propres
   * @param toBeCompared {@inheritDoc}
   * @param identical {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#sameRelationNoneInternal(com.bid4win.commons.persistence.entity.Bid4WinEntity, boolean)
   */
  @Override
  protected boolean sameRelationNoneInternal(CLASS toBeCompared, boolean identical)
  {
    return super.sameRelationNoneInternal(toBeCompared, identical) &&
           Bid4WinComparator.getInstance().equals(this.getData(), toBeCompared.getData());

  }
  /**
   * Permet d'effectuer le rendu simple de l'historique de connexion courant sans
   * prise en compte de ses relations
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#renderRelationNone()
   */
  @Override
  protected StringBuffer renderRelationNone()
  {
    // Effectue le rendu de base sans lien d'une entit�
    StringBuffer buffer = super.renderRelationNone();
    // Ajoute les �l�ments de l'historique de connexion
    buffer.append(" ").append(this.getData().render());
    buffer.append(" END_DATE=").append(this.getEndDate().formatYYYYIMMIDD());
    // Retourne le rendu
    return buffer;
  }

  private void defineData(ConnectionData data) throws ProtectionException, UserException
  {
    this.checkProtection();
    UtilObject.checkNotNull("data", data, ConnectionRef.CONNECTION_INVALID_ERROR);
    UtilObject.checkNotNull("disconnectionReason", data.getDisconnectionReason(),
                            ConnectionRef.CONNECTION_INVALID_ERROR);
    this.setData(data);
  }

  /**
   * Cette m�thode permet de d�finir l'identifiant de la session li�e � la connexion
   * @param sessionId D�finition de l'identifiant de la session li�e � la connexion
   * @throws ProtectionException Si l'historique de connexion courant est prot�g�
   */
/*  private void defineSessionId(String sessionId) throws ProtectionException
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
/*  private void defineIpAddress(IpAddress ipAddress) throws ProtectionException, UserException
  {
    // V�rifie la protection de la connexion courante
    this.checkProtection();
    this.setIpAddress(UtilObject.checkNotNull("ipAddress", ipAddress,
                      ConnectionRef.IP_MISSING_ERROR));
  }
  /**
   * Cette m�thode permet de d�finir la raison de fin de connexion
   * @param disconnectionReason D�finition de la raison de fin de connexion
   * @throws ProtectionException Si l'historique de connexion courant est prot�g�
   * @throws UserException Si on d�fini une raison de fin de connexion nulle
   */
/*  private void defineDisconnectionReason(DisconnectionReason disconnectionReason)
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
/*  private void defineStart(Bid4WinDate startDate)
          throws ProtectionException, UserException
  {
    // V�rifie la protection de la connexion courante
    this.checkProtection();
    UtilObject.checkNotNull("startDate", startDate, ConnectionRef.CONNECTION_INVALID_ERROR);
    this.setStartDate(startDate);
  }*/

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/

  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Embedded
  public ConnectionData getData()
  {
    return this.data;
  }
  private void setData(ConnectionData data)
  {
    this.data = data;
  }


  /**
   * Getter de l'identifiant de la session li�e � la connexion
   * @return L'identifiant de la session li�e � la connexion
   */
  // Annotation pour la persistence
/*  @Access(AccessType.PROPERTY)
  @Column(name = "SESSION_ID", length = 32 , nullable = false, unique = false)
  public String getSessionId()
  {
    return this.sessionId;
  }
  /**
   * Setter de l'identifiant de la session li�e � la connexion
   * @param sessionId Identifiant de la session li�e � la connexion � positionner
   */
/*  private void setSessionId(String sessionId)
  {
    this.sessionId = sessionId;
  }*/

  /**
   * Getter du flag indiquant la r�manence de la connexion
   * @return Le flag indiquant la r�manence de la connexion
   */
/*  // Annotation pour la persistence
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
/*  private void setRemanent(boolean remanent)
  {
    this.remanent = remanent;
  }*/

  /**
   * Getter de l'adresse IP de connexion
   * @return L'adresse IP de connexion
   */
/*  // Annotation pour la persistence
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
/*  private void setIpAddress(IpAddress ipAddress)
  {
    this.ipAddress = ipAddress;
  }*/

  /**
   * Getter de la date de d�but de connexion
   * @return La date de d�but de connexion
   */
/*  // Annotation pour la persistence
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
/*  private void setStartDate(Bid4WinDate startDate)
  {
    this.startDate = startDate;
  }*/

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
/*  // Annotation pour la persistence
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
/*  private void setDisconnectionReason(DisconnectionReason disconnectionReason)
  {
    this.disconnectionReason = disconnectionReason;
  }*/

  /**
   * Getter du flag indiquant si une r�utilisation de la r�manence a d�j� �t� tent�e
   * @return Le flag indiquant si une r�utilisation de la r�manence a d�j� �t� tent�e
   */
/*  // Annotation pour la persistence
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
/*  private void setReuseAttempted(boolean reuseAttempted)
  {
    this.reuseAttempted = reuseAttempted;
  }*/
}
