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
 * Cette classe défini un historique de connexion<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * @param <ACCOUNT> Définition du type de compte utilisateur de la connexion<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public class ConnectionHistoryAbstract<CLASS extends ConnectionHistoryAbstract<CLASS, ACCOUNT>,
                                       ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends AccountBasedEntityMultipleAutoID<CLASS, ACCOUNT>
{
  /** Données de la connexion historisées */
  @Transient private ConnectionData data = null;

  /** Identifiant de la session liée à la connexion */
//  @Transient private String sessionId = null;
  /** Flag indiquant la rémanence de la connexion */
//  @Transient private boolean remanent = false;
  /** Adresse IP de connexion */
//  @Transient private IpAddress ipAddress = null;
  /** Date de début de connexion */
//  @Transient private Bid4WinDate startDate = null;
  /** Raison de fin de connexion */
//  @Transient private DisconnectionReason disconnectionReason = null;

  /**
   * Constructeur pour création par introspection
   */
  protected ConnectionHistoryAbstract()
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
   * Constructeur à partir d'une connexion à historiser
   * @param connection Connexion dont il faut construire l'historique
   * @throws UserException Si les paramètres sont invalides
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
   * Redéfini l'équivalence interne de deux historiques de connexions sans prise
   * en compte de leurs relations afin d'y ajouter le test de leurs données propres
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
    // Effectue le rendu de base sans lien d'une entité
    StringBuffer buffer = super.renderRelationNone();
    // Ajoute les éléments de l'historique de connexion
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
   * Cette méthode permet de définir l'identifiant de la session liée à la connexion
   * @param sessionId Définition de l'identifiant de la session liée à la connexion
   * @throws ProtectionException Si l'historique de connexion courant est protégé
   */
/*  private void defineSessionId(String sessionId) throws ProtectionException
  {
    // Vérifie la protection de la connexion courante
    this.checkProtection();
    this.setSessionId(UtilString.trimNotNull(sessionId));
  }
  /**
   * Cette méthode permet de définir l'adresse IP de connexion
   * @param ipAddress Définition de l'adresse IP de connexion
   * @throws ProtectionException Si l'historique de connexion courant est protégé
   * @throws UserException Si on défini une adresse IP nulle
   */
/*  private void defineIpAddress(IpAddress ipAddress) throws ProtectionException, UserException
  {
    // Vérifie la protection de la connexion courante
    this.checkProtection();
    this.setIpAddress(UtilObject.checkNotNull("ipAddress", ipAddress,
                      ConnectionRef.IP_MISSING_ERROR));
  }
  /**
   * Cette méthode permet de définir la raison de fin de connexion
   * @param disconnectionReason Définition de la raison de fin de connexion
   * @throws ProtectionException Si l'historique de connexion courant est protégé
   * @throws UserException Si on défini une raison de fin de connexion nulle
   */
/*  private void defineDisconnectionReason(DisconnectionReason disconnectionReason)
          throws ProtectionException, UserException
  {
    // Vérifie la protection de la connexion courante
    this.checkProtection();
    this.setDisconnectionReason(UtilObject.checkNotNull("disconnectionReason", disconnectionReason,
                                                        ConnectionRef.CONNECTION_INVALID_ERROR));
  }
  /**
   * Cette méthode permet de définir la date de début de connexion
   * @param startDate Définition de la date de début de connexion
   * @throws ProtectionException Si l'historique de connexion courant est protégé
   * @throws UserException Si on défini une date de début de connexion nulle
   */
/*  private void defineStart(Bid4WinDate startDate)
          throws ProtectionException, UserException
  {
    // Vérifie la protection de la connexion courante
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
   * Getter de l'identifiant de la session liée à la connexion
   * @return L'identifiant de la session liée à la connexion
   */
  // Annotation pour la persistence
/*  @Access(AccessType.PROPERTY)
  @Column(name = "SESSION_ID", length = 32 , nullable = false, unique = false)
  public String getSessionId()
  {
    return this.sessionId;
  }
  /**
   * Setter de l'identifiant de la session liée à la connexion
   * @param sessionId Identifiant de la session liée à la connexion à positionner
   */
/*  private void setSessionId(String sessionId)
  {
    this.sessionId = sessionId;
  }*/

  /**
   * Getter du flag indiquant la rémanence de la connexion
   * @return Le flag indiquant la rémanence de la connexion
   */
/*  // Annotation pour la persistence
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
   * @param ipAddress Adresse IP de connexion à positionner
   */
/*  private void setIpAddress(IpAddress ipAddress)
  {
    this.ipAddress = ipAddress;
  }*/

  /**
   * Getter de la date de début de connexion
   * @return La date de début de connexion
   */
/*  // Annotation pour la persistence
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
/*  private void setStartDate(Bid4WinDate startDate)
  {
    this.startDate = startDate;
  }*/

  /**
   * Getter de la date de fin de connexion qui se basera sur la date de création
   * de l'historique
   * @return La date de fin de connexion
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "CREATE_DATE", nullable = false, unique = false,
          // La date de fin de la connexion correspond à la date de création de son historique
          insertable = false, updatable = false)
  public Bid4WinDate getEndDate()
  {
    return this.getCreateDate();
  }
  /**
   * Setter inutilisé de la date de fin de connexion
   * @param endDate Date de fin de connexion
   */
  @SuppressWarnings("unused")
  private void setEndDate(Bid4WinDate endDate)
  {
    // Inutilisé car la date de fin de connexion correspond à la date de création
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
   * @param disconnectionReason Raison de fin de connexion à positionner
   */
/*  private void setDisconnectionReason(DisconnectionReason disconnectionReason)
  {
    this.disconnectionReason = disconnectionReason;
  }*/

  /**
   * Getter du flag indiquant si une réutilisation de la rémanence a déjà été tentée
   * @return Le flag indiquant si une réutilisation de la rémanence a déjà été tentée
   */
/*  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "REUSE_ATTEMPTED", nullable = false, unique = false)
  public boolean isReuseAttempted()
  {
    return this.reuseAttempted;
  }
  /**
   * Setter du flag indiquant si une réutilisation de la rémanence a déjà été tentée
   * @param reuseAttempted Flag indiquant si une réutilisation de la rémanence a
   * déjà été tentée à positionner
   */
/*  private void setReuseAttempted(boolean reuseAttempted)
  {
    this.reuseAttempted = reuseAttempted;
  }*/
}
