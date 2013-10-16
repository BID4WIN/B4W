package com.bid4win.commons.persistence.entity.connection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.comparator.Bid4WinComparator;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.ConnectionRef;
import com.bid4win.commons.core.security.IdProcess;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultipleGeneratedID;

/**
 * Cette classe d�fini la connexion d'un compte utilisateur � partir d'un point
 * du r�seau (symbolis� par l'adresse IP)<BR>
 * <BR>
 * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
 * @param <HISTORY> D�finition du type d'historique de connexion<BR>
 * @param <ACCOUNT> D�finition du type de compte utilisateur de la connexion<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@AttributeOverride(name = "id", column = @Column(length = 32))
public abstract class ConnectionAbstract<CLASS extends ConnectionAbstract<CLASS, HISTORY, ACCOUNT>,
                                         HISTORY extends ConnectionHistoryAbstract<HISTORY, ACCOUNT>,
                                         ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends AccountBasedEntityMultipleGeneratedID<CLASS, ACCOUNT>
{
  /** Format des identifiants de session */
//  public final static String SESSION_ID_PATTERN = "[0-9A-F]{32}";

  /** Adresse IP de connexion */
//  @Transient private IpAddress ipAddress = null;
  /** Identifiant du process � l'origine de la connexion */
  @Transient private String processId = null;
  /** Flag indiquant la r�manence de la connexion */
//  @Transient private boolean remanent = false;
  /** Flag indiquant si la connexion est active */
  @Transient private boolean active = true;
  /** Raison de fin de connexion */
//  @Transient private DisconnectionReason disconnectionReason = null;
  /** TODO A COMMENTER */
  @Transient private ConnectionData data = null;

  /** Historique de connexion � cr�er pour une connexion termin�e */
  @Transient private HISTORY history = null;

  /**
   * Constructeur pour cr�ation par introspection
   */
  protected ConnectionAbstract()
  {
    super();
  }
  /**
   * Constructeur
   * @param sessionId Identifiant de la session de connexion
   * @param account Compte utilisateur de connexion
   * @param ipAddress Adresse IP de connexion
   * @param remanent Flag indiquant si la r�manence est activ�e sur la connexion
   * @throws UserException Si l'identifiant de session, le compte utilisateur ou
   * l'adresse IP de connexion en argument est nul
   */
  public ConnectionAbstract(/*String sessionId,*/ ConnectionData data, ACCOUNT account/*,
                            IpAddress ipAddress, boolean remanent*/)
         throws UserException
  {
    super(/*UtilString.checkPattern("sessionId", UtilString.trimNotNull(sessionId).toUpperCase(),
                                  SESSION_ID_PATTERN, ConnectionRef.SESSION_UNDEFINED_ERROR),*/
          UtilObject.checkNotNull("data", data, ConnectionRef.CONNECTION_INVALID_ERROR).getSessionId(), account);
    /*this.defineIpAddress(ipAddress);*/
    this.defineProcessId();
    this.defineData(data);
    //this.setRemanent(remanent);
  }

  /**
   * Red�fini l'�quivalence interne de deux connexions sans prise en compte de
   * leurs relations afin d'y ajouter le test de leurs donn�es propres
   * @param toBeCompared {@inheritDoc}
   * @param identical {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#sameRelationNoneInternal(com.bid4win.commons.persistence.entity.Bid4WinEntity, boolean)
   */
  @Override
  protected boolean sameRelationNoneInternal(CLASS toBeCompared, boolean identical)
  {
    return super.sameRelationNoneInternal(toBeCompared, identical) &&
           /*Bid4WinObjectComparator.getInstanceObject().equals(this.getIpAddress(),
                                                              toBeCompared.getIpAddress()) &&*/
           Bid4WinComparator.getInstance().equals(this.getProcessId(),
                                                  toBeCompared.getProcessId()) &&
           /*this.isRemanent() == toBeCompared.isRemanent() &&*/
           this.isActive() == toBeCompared.isActive() &&
           Bid4WinComparator.getInstance().equals(this.getData(), toBeCompared.getData());

  }
  /**
   * Permet d'effectuer le rendu simple de la connexion courante sans prise en
   * compte de ses relations
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#renderRelationNone()
   */
  @Override
  protected StringBuffer renderRelationNone()
  {
    // Effectue le rendu de base sans lien d'une entit�
    StringBuffer buffer = super.renderRelationNone();
    // Ajoute les �l�ments de la connexion
//    buffer.append(" ").append(this.getIpAddress().render());
    buffer.append(" PROCESS_ID=").append(this.getProcessId());
/*    if(this.isRemanent())
    {
      buffer.append(" REMANENT");
    }*/
    if(this.isActive())
    {
      buffer.append(" ACTIVE");
    }
    else
    {
      buffer.append(" INACTIVE");
    }
    buffer.append(" ").append(this.getData().render());
    // Retourne le rendu
    return buffer;
  }

  /**
   * Cette fonction permet de terminer la connexion courante en conservant son �tat
   * de r�manence. Si la connexion �tait active, son historique est pr�par�
   * @param disconnectionReason Raison de fin de la connexion
   * @return La connexion termin�e
   * @throws ProtectionException Si la connexion courante est prot�g�e
   */
  @SuppressWarnings("unchecked")
  public CLASS endConnection(DisconnectionReason disconnectionReason) throws ProtectionException
  {
    if(this.isActive())
    {
      try
      {
        this.defineData(this.getData().endConnection(disconnectionReason));
        this.setHistory(this.createHistory());
        this.setActive(false);
      }
      catch(UserException ex)
      {
        // TODO
      }
    }
    return (CLASS)this;
  }
  /**
   * Cette fonction permet d'arr�ter la connexion courante en retirant son �tat
   * de r�manence. Si la connexion �tait active, son historique est pr�par�
   * @param disconnectionReason Raison d'arr�t de la connexion
   * @return La connexion arr�t�e
   * @throws ProtectionException Si la connexion courante est prot�g�e
   */
  @SuppressWarnings("unchecked")
  public CLASS stopConnection(DisconnectionReason disconnectionReason) throws ProtectionException
  {
    try
    {
      this.endConnection(disconnectionReason);
      this.defineData(this.getData().stopConnection(disconnectionReason));
    }
    catch(UserException ex)
    {
      // TODO
    }
    return (CLASS)this;
  }

  /**
   * Cette fonction doit �tre d�finie afin de construire l'historique de la connexion
   * courante
   * @return L'historique de la connexion courante
   * @throws UserException Si la construction de l'historique �choue
   */
  protected abstract HISTORY createHistory() throws UserException;
  /**
   * Getter de l'historique de connexion � cr�er pour une connexion termin�e
   * @return L'historique de connexion � cr�er pour une connexion termin�e
   */
  public HISTORY getHistory()
  {
    return this.history;
  }
  /**
   * Setter de l'historique de connexion � cr�er pour une connexion termin�e
   * @param history Historique de connexion � cr�er pour une connexion termin�e
   * � positionner
   */
  private void setHistory(HISTORY history)
  {
    this.history = history;
  }
  /**
   * Cette m�thode permet de d�finir l'adresse IP de connexion
   * @param ipAddress D�finition de l'adresse IP de connexion
   * @throws ProtectionException Si la connexion courante est prot�g�e
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
   * Cette m�thode permet de d�finir l'identifiant du process � l'origine de la
   * connexion
   * @throws ProtectionException Si la connexion courante est prot�g�e
   */
  private void defineProcessId() throws ProtectionException
  {
    this.setProcessId(IdProcess.ID);
  }
  /**
   * Cette m�thode permet de d�finir la raison de fin de connexion
   * @param disconnectionReason D�finition de la raison de fin de connexion
   * @throws ProtectionException Si la connexion courante est prot�g�e
   */
 /* private void defineDisconnectionReason(DisconnectionReason disconnectionReason)
          throws ProtectionException
  {
    // V�rifie la protection de la connexion courante
    this.checkProtection();
    if(disconnectionReason == null)
    {
      disconnectionReason = DisconnectionReason.AUTO;
    }
    this.setDisconnectionReason(disconnectionReason);
  }
  /**
   * Getter de l'empreinte unique � utiliser pour utiliser la r�manence de la connexion
   * @return L'empreinte unique � utiliser pour utiliser la r�manence de la connexion
   */
  public String getFingerPrint()
  {
    return this.getId();
  }
  private void defineData(ConnectionData data) throws ProtectionException, UserException
  {
    this.checkProtection();
    UtilObject.checkNotNull("data", data, ConnectionRef.CONNECTION_INVALID_ERROR);
    this.setData(data);
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
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
   * Getter de l'identifiant du process � l'origine de la connexion
   * @return L'identifiant du process � l'origine de la connexion
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "PROCESS_ID", length = 12, nullable = false, unique = false)
  public String getProcessId()
  {
    return this.processId;
  }
  /**
   * Setter de l'identifiant du process � l'origine de la connexion
   * @param processId Identifiant du process � l'origine de la connexion � positionner
   */
  private void setProcessId(String processId)
  {
    this.processId = processId;
  }

  /**
   * Getter du flag indiquant la r�manence de la connexion
   * @return Le flag indiquant la r�manence de la connexion
   */
  // Annotation pour la persistence
/*  @Access(AccessType.PROPERTY)
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
   * Getter du flag indiquant si la connexion est active
   * @return Le flag indiquant si la connexion est active
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "ACTIVE", nullable = false, unique = false)
  public boolean isActive()
  {
    return this.active;
  }
  /**
   * Setter du flag indiquant si la connexion est active
   * @param active Flag indiquant si la connexion est active � positionner
   */
  private void setActive(boolean active)
  {
    this.active = active;
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

  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Embedded
  @AttributeOverrides({@AttributeOverride(name = "sessionId",
                                          column = @Column(name = "ID",
                                                           // L'identifiant de session correspond � l'identifiant de la connexion
                                                           insertable = false, updatable = false)),
                       @AttributeOverride(name = "startDate",
                                          column = @Column(name = "CREATE_DATE",
                                                           // La date de d�but de la connexion correspond � sa date de cr�ation
                                                           insertable = false, updatable = false))})
  public ConnectionData getData()
  {
    return this.data;
  }
  private void setData(ConnectionData data)
  {
    this.data = data;
  }

  /**
   * Getter de la date de d�but de connexion qui se basera sur sa date de cr�ation
   * @return La date de d�but de connexion
   */
/*  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "CREATE_DATE", nullable = false, unique = false,
          // La date de d�but de la connexion correspond � sa date de cr�ation
          insertable = false, updatable = false)
  public Bid4WinDate getStartDate()
  {
    return this.getCreateDate();
  }
  /**
   * Setter inutilis� de la date de d�but de connexion
   * @param startDate Date de d�but de connexion
   */
/*  @SuppressWarnings("unused")
  private void setStartDate(Bid4WinDate startDate)
  {
    // Inutilis� car la date de d�but de connexion correspond � sa date de cr�ation
  }*/
}
