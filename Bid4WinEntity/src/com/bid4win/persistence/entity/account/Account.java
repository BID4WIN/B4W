package com.bid4win.persistence.entity.account;

import java.util.List;
import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OptimisticLock;

import com.bid4win.commons.core.UtilNumber;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.comparator.Bid4WinObjectComparator;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.reference.MessageRef.AccountRef;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.security.Credential;
import com.bid4win.commons.persistence.entity.contact.Email;
import com.bid4win.persistence.entity.account.credit.CreditBundle;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementNormal;
import com.bid4win.persistence.entity.account.credit.collection.CreditMap;
import com.bid4win.persistence.entity.account.preference.PreferenceBundle;
import com.bid4win.persistence.entity.account.preference.PreferenceRoot;
import com.bid4win.persistence.entity.auction.normal.NormalAuction;

/**
 * Cette classe d�fini un compte utilisateur pour l'application BID4WIN<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
//Annotation pour la persistence
@Entity
@Access(AccessType.FIELD)
@AttributeOverride(name = "version", column = @Column(length = 5))
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Account extends AccountAbstract<Account>
{
  /** Informations sur l'utilisateur du compte */
  @Transient
  private User user = null;
  /** Jeu de pr�f�rences du compte utilisateur */
  @Transient
  private PreferenceBundle preferenceBundle = null;
  /** Compte utilisateur ayant parrain� le compte utilisateur courant */
  @Transient
  private Account sponsor = null;
  /** Nombre de cr�dits disponibles pour le compte utilisateur */
  @Transient
  private int creditNb = 0;
  /** Nombre de cr�dits utilis�s par le compte utilisateur */
  @Transient
  private int usedCreditNb = 0;
  /** Liste de lots de cr�dits du compte utilisateur */
  @Transient
  private Bid4WinList<CreditBundle> creditBundleList = new Bid4WinList<CreditBundle>();
  /** Map des implications de cr�dits sur des ventes aux ench�res normales */
  @Transient
  private Bid4WinMap<String, CreditInvolvementNormal> involvementNormalMap =
    new Bid4WinMap<String, CreditInvolvementNormal>();

  /**
   * Constructeur pour cr�ation par introspection ou en proxy pour chargement diff�r�
   */
  protected Account()
  {
    super();
  }
  /**
   * Constructeur
   * @param credential Certificat de connexion du compte utilisateur
   * @param email Email du compte utilisateur
   * @throws ModelArgumentException Si le certificat de connexion ou l'email du
   * compte utilisateur en param�tre est nul
   */
  public Account(Credential credential, Email email) throws ModelArgumentException
  {
    this(credential, email, null, new PreferenceBundle());
  }
  /**
   * Constructeur
   * @param credential Certificat de connexion du compte utilisateur
   * @param email Email du compte utilisateur
   * @param user Informations sur l'utilisateur du compte
   * @param bundle Jeu de pr�f�rences du compte utilisateur
   * @throws ModelArgumentException Si le certificat de connexion ou l'email du
   * compte utilisateur en param�tre est nul
   */
  public Account(Credential credential, Email email, User user, PreferenceBundle bundle)
         throws ModelArgumentException
  {
    super(credential, email);
    this.defineUser(user);
    this.definePreferenceBundle(bundle);
  }
  /**
   * Constructeur
   * @param credential Certificat de connexion du compte utilisateur
   * @param email Email du compte utilisateur
   * @param user Informations sur l'utilisateur du compte
   * @param preferenceRoot Racine de la liste de pr�f�rences du compte utilisateur
   * @throws ModelArgumentException Si le certificat de connexion ou l'email du
   * compte utilisateur en param�tre est nul
   * @throws UserException Si l'une des pr�f�rences en argument est invalide
   */
  public Account(Credential credential, Email email, User user, PreferenceRoot preferenceRoot)
         throws ModelArgumentException, UserException
  {
    this(credential, email, user, new PreferenceBundle(preferenceRoot));
  }

  /**
   * Red�fini l'�quivalence interne de deux comptes utilisateur sans prise en compte
   * de leurs relations afin d'y ajouter le test de leurs donn�es propres
   * @param toBeCompared {@inheritDoc}
   * @param identical {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#sameRelationNoneInternal(com.bid4win.commons.persistence.entity.Bid4WinEntity, boolean)
   */
  @Override
  protected boolean sameRelationNoneInternal(Account toBeCompared, boolean identical)
  {
    return super.sameRelationNoneInternal(toBeCompared, identical) &&
           Bid4WinObjectComparator.getInstanceObject().equals(this.getUser(),
                                                              toBeCompared.getUser()) &&
           Bid4WinObjectComparator.getInstanceObject().equals(this.getPreferenceBundle(),
                                                              toBeCompared.getPreferenceBundle()) &&
           this.getCreditNb() == toBeCompared.getCreditNb() &&
           this.getUsedCreditNb() == toBeCompared.getUsedCreditNb();
  }
  /**
   * Permet d'effectuer le rendu simple du compte utilisateur courant sans prise
   * en compte de ses relations
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#renderRelationNone()
   */
  @Override
  protected StringBuffer renderRelationNone()
  {
    // Effectue le rendu de base sans lien d'un compte utilisateur
    StringBuffer buffer = super.renderRelationNone();
    // Ajoute les �l�ments du compte utilisateur
    if(this.isUserDefined())
    {
      buffer.append(" ").append(this.getUser().render());
    }
    buffer.append(" ").append(this.getPreferenceBundle().render());
    buffer.append(" CREDIT_NB=").append(this.getCreditNb());
    buffer.append(" USED_CREDIT_NB=").append(this.getUsedCreditNb());
    // Retourne le rendu
    return buffer;
  }
  /**
   *
   * TODO A COMMENTER
   * @param relation {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getMessageRefBase(com.bid4win.commons.persistence.entity.Bid4WinRelation)
   */
  @Override
  protected MessageRef getMessageRefBase(Bid4WinRelation relation)
  {
    if(relation.equals(Account_Relations.RELATION_SPONSOR))
    {
      return AccountRef.ACCOUNT;
    }
    return super.getMessageRefBase(relation);
  }
  /**
   * Ajoute � la liste des noeuds de relations du compte utilisateur le lien vers
   * son parrain, ses cr�dits et leurs implications
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getFullRelationNodeList()
   */
  @Override
  protected Bid4WinList<Bid4WinRelationNode> getFullRelationNodeList()
  {
    Bid4WinList<Bid4WinRelationNode> nodeList = super.getFullRelationNodeList();
    nodeList.add(Account_Relations.NODE_SPONSOR);
    nodeList.add(Account_Relations.NODE_CREDIT_BUNDLE_LIST);
    nodeList.add(Account_Relations.NODE_INVOLVEMENT_NORMAL_MAP);
    return nodeList;
  }
  /**
   * Permet de r�cup�rer le parrain du compte utilisateur s'il correspondant � la
   * relation en argument. Elle doit �tre red�finie pour toute nouvelle relation
   * de type simple � remonter
   * @param relation {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getRelationSimple(com.bid4win.commons.persistence.entity.Bid4WinRelation)
   */
  @Override
  protected Bid4WinEntity<?, ?> getRelationSimple(Bid4WinRelation relation)
  {
    if(relation.equals(Account_Relations.RELATION_SPONSOR))
    {
      return this.getSponsor();
    }
    return super.getRelationSimple(relation);
  }
  /**
   * Permet de positionner le parrain du compte utilisateur s'il correspondant �
   * la relation en argument. Elle doit �tre red�finie pour toute nouvelle relation
   * de type simple � positionner
   * @param relation {@inheritDoc}
   * @param entity {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#setRelationSimple(com.bid4win.commons.persistence.entity.Bid4WinRelation, com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  protected void setRelationSimple(Bid4WinRelation relation, Bid4WinEntity<?, ?> entity)
  {
    if(relation.equals(Account_Relations.RELATION_SPONSOR))
    {
      this.setSponsor((Account)entity);
    }
    else
    {
      super.setRelationSimple(relation, entity);
    }
  }
  /**
   * Permet de r�cup�rer la liste de cr�dits du compte utilisateur si elle correspond
   * � la relation en argument.
   * @param relation {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getRelationList(com.bid4win.commons.persistence.entity.Bid4WinRelation)
   */
  @Override
  protected List<? extends Bid4WinEntity<?, ?>> getRelationList(Bid4WinRelation relation)
  {
    if(relation.equals(Account_Relations.RELATION_CREDIT_BUNDLE_LIST))
    {
      return this.getCreditBundleListInternal();
    }
    return super.getRelationList(relation);
  }
  /**
   * Permet de r�cup�rer la map d'implications de cr�dits qui correspond � la
   * relation en argument.
   * @param relation {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getRelationMap(com.bid4win.commons.persistence.entity.Bid4WinRelation)
   */
  @Override
  protected Map<?, ? extends Bid4WinEntity<?, ?>> getRelationMap(Bid4WinRelation relation)
  {
    if(relation.equals(Account_Relations.RELATION_INVOLVEMENT_NORMAL_MAP))
    {
      return this.getInvolvementNormalMapInternal();
    }
    return super.getRelationMap(relation);
  }
  /**
   *
   * TODO A COMMENTER
   * @param relation {@inheritDoc}
   * @param value {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getRelationMapKey(com.bid4win.commons.persistence.entity.Bid4WinRelation, com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  protected Object getRelationMapKey(Bid4WinRelation relation, Bid4WinEntity<?, ?> value)
  {
    if(relation.equals(Account_Relations.RELATION_INVOLVEMENT_NORMAL_MAP))
    {
      return ((CreditInvolvementNormal)value).getAuctionId();
    }
    return super.getRelationMapKey(relation, value);
  }

  /**
   * Cette fonction permet de savoir si le compte utilisateur poss�de encore des
   * cr�dits
   * @return True si le compte utilisateur poss�de des cr�dits, false sinon
   */
  public boolean hasCredit()
  {
    return this.getCreditNb() != 0;
  }
  /**
   * Cette m�thode permet d'utiliser un cr�dit. Si le lot utilis� est vide une
   * fois le cr�dit retir�, il sera alors retir� du compte utilisateur
   * @return Le lot de provenance du cr�dit utilis�, potentiellement retir� du
   * compte utilisateur apr�s utilisation
   * @throws ProtectionException Si le compte utilisateur courant ou le lot de
   * cr�dits utilis� est prot�g�
   * @throws UserException Si le compte utilisateur n'a aucun cr�dit
   */
  public CreditBundle useCredit() throws ProtectionException, UserException
  {
    return this.useCredit(1).keySet().iterator().next();
  }
  /**
   * Cette m�thode permet d'utiliser des cr�dits. Les lots utilis�s vides une fois
   * les cr�dits retir�s seront retir�s du compte utilisateur
   * @param nb Nombre de cr�dits � utiliser
   * @return La map des cr�dits utilis�s (potentiellement retir�s du compte utilisateur
   * apr�s utilisation) avec en cl� leurs lots de provenance et en valeur leur nombre,
   * @throws ProtectionException Si le compte utilisateur courant ou les lots de
   * cr�dits utilis�s est prot�g�
   * @throws UserException Si le compte utilisateur n'a pas assez de cr�dits
   */
  public CreditMap useCredit(int nb) throws ProtectionException, UserException
  {
    // V�rifie la pr�sence des cr�dits
    UtilNumber.checkMinValue("creditNb", this.getCreditNb(), nb, true,
                             AccountRef.ACCOUNT_CREDIT_NB_INSUFFICIENT_ERROR);
    // R�cup�re les lots de cr�dits et en utilise une partie
    CreditMap usedCreditMap = new CreditMap();
    while(nb > 0)
    {
      CreditBundle usedBundle = this.getCreditBundle();
      int usedNb = Math.min(nb, usedBundle.getCurrentNb());
      usedCreditMap.put(usedBundle.use(usedNb), usedNb);
      // Valide l'utilisation des cr�dits
      nb -= usedNb;
      this.setCreditNb(this.getCreditNb() - usedNb);
      this.setUsedCreditNb(this.getUsedCreditNb() + usedNb);
    }
    // Retourne les lot de cr�dits utilis�s
    return usedCreditMap;
  }
  /**
   * Getter du lot de cr�dit � utiliser
   * @return Le lot de cr�dits � utiliser
   */
  private CreditBundle getCreditBundle()
  {
    return this.getCreditBundleList().getFirst();
  }

  /**
   * Getter des implications de cr�dits sur la vente aux ench�res normale en argument
   * @param auction Vente aux ench�res normale sur laquelle les implications de
   * cr�dits doivent �tre r�cup�rer
   * @return Les implications de cr�dits sur la vente aux ench�res normale en argument
   */
  public CreditInvolvementNormal getInvolvementNormal(NormalAuction auction)
  {
    return this.getInvolvementNormalMap().get(auction.getId());
  }
  /**
   * Getter du set des implications de cr�dits sur des ventes aux ench�res normales
   * @return Le set des implications de cr�dits sur des ventes aux ench�res normales
   */
  public Bid4WinSet<CreditInvolvementNormal> getInvolvementNormalSet()
  {
    return new Bid4WinSet<CreditInvolvementNormal>(this.getInvolvementNormalMap().values());
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public boolean isUserDefined()
  {
    return this.getUser() != null;
  }
  /**
   * Cette m�thode permet de d�finir les informations sur l'utilisateur du compte
   * @param user D�finition des informations sur l'utilisateur du compte
   * @throws ProtectionException Si le compte utilisateur courant est prot�g�
   */
  public void defineUser(User user) throws ProtectionException
  {
    // V�rifie la protection du compte utilisateur courant
    this.checkProtection();
    if(this.isUserDefined())
    {
      this.getUser().merge(user);
    }
    else
    {
      this.setUser(user);
    }
  }
  /**
   * Cette m�thode permet de d�finir le jeu de pr�f�rences du compte utilisateur
   * @param bundle D�finition du jeu de pr�f�rences du compte utilisateur
   * @throws ProtectionException Si le compte utilisateur courant est prot�g�
   */
  private void definePreferenceBundle(PreferenceBundle bundle) throws ProtectionException
  {
    // V�rifie la protection du compte utilisateur courant
    this.checkProtection();
    // Utilisation des pr�f�rences par d�faut
    if(bundle == null)
    {
      bundle = new PreferenceBundle();
    }
    this.setPreferenceBundle(bundle);
  }

  /**
   * Cette m�thode permet de d�finir le parrain du compte utilisateur courant
   * @param sponsor D�finition du parrain du compte utilisateur courant
   * @throws ProtectionException Si le compte utilisateur courant est prot�g�
   * @throws UserException Si on d�fini un parrain nul ou d�j� d�fini
   */
  protected void linkToSponsor(Account sponsor) throws ProtectionException, UserException
  {
    this.linkTo(Account_Relations.RELATION_SPONSOR, sponsor);
  }
  /**
   * Cette m�thode permet de retirer le parrain du compte utilisateur courant
   * @return L'ancien parrain du compte utilisateur
   * @throws ProtectionException Si le compte utilisateur courant est prot�g�
   * @throws UserException Si aucun parrain n'est d�fini
   */
  protected Account unlinkFromSponsor() throws ProtectionException, UserException
  {
    return (Account)this.unlinkFrom(Account_Relations.RELATION_SPONSOR);
  }

  /**
   * Getter de la liste de lots de cr�dits du compte utilisateur
   * @return La liste de lots de cr�dits du compte utilisateur
   */
  private Bid4WinList<CreditBundle> getCreditBundleList()
  {
    return this.creditBundleList;
  }
  /**
   * Setter de la liste de lots de cr�dits du compte utilisateur
   * @param creditBundleList Liste de lots de cr�dits du compte utilisateur �
   * positionner
   */
  private void setCreditBundleList(Bid4WinList<CreditBundle> creditBundleList)
  {
    this.creditBundleList = creditBundleList;
  }
  /**
   * Getter de la map des implications de cr�dits sur des ventes aux ench�res
   * normales du compte utilisateur courant
   * @return La map des implications de cr�dits sur des ventes aux ench�res normales
   * du compte utilisateur courant
   */
  private Bid4WinMap<String, CreditInvolvementNormal> getInvolvementNormalMap()
  {
    return this.involvementNormalMap;
  }
  /**
   * Setter de la map des implications de cr�dits sur des ventes aux ench�res
   * normales du compte utilisateur courant
   * @param involvementMap Map des implications de cr�dits sur des ventes aux ench�res
   * normales du compte utilisateur courant � positionner
   */
  private void setInvolvementNormalMap(Bid4WinMap<String, CreditInvolvementNormal> involvementMap)
  {
    this.involvementNormalMap = involvementMap;
  }
  /**
   *
   * TODO A COMMENTER
   * @param link {@inheritDoc}
   * @param backLink {@inheritDoc}
   * @param toBeAdded {@inheritDoc}
   * @throws ProtectionException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#addRelationCollection(com.bid4win.commons.persistence.entity.Bid4WinRelation, com.bid4win.commons.persistence.entity.Bid4WinRelation, com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  protected void addRelationCollection(Bid4WinRelation link,
                                       Bid4WinRelation backLink,
                                       Bid4WinEntity<?, ?> toBeAdded)
            throws ProtectionException, UserException
  {
    super.addRelationCollection(link, backLink, toBeAdded);
    // A la liaison d'un lot de cr�dits, il faut augmenter les cr�dits du compte
    if(link.equals(Account_Relations.RELATION_CREDIT_BUNDLE_LIST))
    {
      this.setCreditNb(this.getCreditNb() + ((CreditBundle)toBeAdded).getNb());
    }
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter des informations sur l'utilisateur du compte
   * @return Les informations sur l'utilisateur du compte
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Embedded()
  public User getUser()
  {
    return this.user;
  }
  /**
   * Setter des informations sur l'utilisateur du compte
   * @param user Informations sur l'utilisateur du compte � positionner
   */
  private void setUser(User user)
  {
    this.user = user;
  }

  /**
   * Getter du jeu de pr�f�rences du compte utilisateur
   * @return Le jeu de pr�f�rences du compte utilisateur
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Embedded()
  public PreferenceBundle getPreferenceBundle()
  {
    return this.preferenceBundle;
  }
  /**
   * Setter du jeu de pr�f�rences du compte utilisateur
   * @param preferenceBundle Jeu de pr�f�rences du compte utilisateur � positionner
   */
  private void setPreferenceBundle(PreferenceBundle preferenceBundle)
  {
    this.preferenceBundle = preferenceBundle;
  }

  /**
   * Getter du compte utilisateur ayant parrain� le compte utilisateur courant
   * @return Le compte utilisateur ayant parrain� le compte utilisateur courant
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @ManyToOne(fetch = FetchType.LAZY, cascade = {})
  @JoinColumn(name = "SPONSOR_ID", nullable = true, unique = false)
  public Account getSponsor()
  {
    return this.sponsor;
  }
  /**
   * Setter du compte utilisateur ayant parrain� le compte utilisateur courant
   * @param sponsor Compte utilisateur ayant parrain� le compte utilisateur courant
   * � positionner
   */
  private void setSponsor(Account sponsor)
  {
    this.sponsor = sponsor;
  }

  /**
   * Getter du nombre de cr�dits disponibles pour le compte utilisateur
   * @return Le nombre de cr�dits disponibles pour le compte utilisateur
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "CREDIT_NB", length = 5, nullable = false, unique = false)
  public int getCreditNb()
  {
    return this.creditNb;
  }
  /**
   * Setter du nombre de cr�dits disponibles pour le compte utilisateur
   * @param creditNb Nombre de cr�dits disponibles pour le compte utilisateur
   * � positionner
   */
  private void setCreditNb(int creditNb)
  {
    this.creditNb = creditNb;
  }

  /**
   * Getter du nombre de cr�dits utilis�s par le compte utilisateur
   * @return Le nombre de cr�dits utilis�s par le compte utilisateur
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "CREDIT_USED_NB", length = 5, nullable = false, unique = false)
  public int getUsedCreditNb()
  {
    return this.usedCreditNb;
  }
  /**
   * Setter du nombre de cr�dits utilis�s par le compte utilisateur
   * @param usedCreditNb Nombre de cr�dits utilis�s par le compte utilisateur �
   * positionner
   */
  private void setUsedCreditNb(int usedCreditNb)
  {
    this.usedCreditNb = usedCreditNb;
  }

  /**
   * Getter de la liste interne de lots de cr�dits du compte utilisateur
   * @return La liste interne de lots de cr�dits du compte utilisateur
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @OneToMany(mappedBy = "accountLink", fetch = FetchType.LAZY, cascade = {})
  @OrderBy(value = "id")
  @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
  // A partir d'Hibernate 4.1.1, l'entit� parent n'est pas mise � jour par d�faut
  @OptimisticLock(excluded = false)
  private List<CreditBundle> getCreditBundleListInternal()
  {
    return this.getCreditBundleList().getInternal();
  }
  /**
   * Setter de la liste interne de lots de cr�dits du compte utilisateur
   * @param internalCreditBundleList Liste interne de lots de cr�dits du compte
   * utilisateur � positionner
   */
  @SuppressWarnings(value = "unused")
  private void setCreditBundleListInternal(List<CreditBundle> internalCreditBundleList)
  {
    this.setCreditBundleList(new Bid4WinList<CreditBundle>(internalCreditBundleList, true));
  }

  /**
   * Getter de la map interne des implications de cr�dits sur des ventes aux ench�res
   * normales du compte utilisateur courant
   * @return La map interne des implications de cr�dits sur des ventes aux ench�res
   * normales du compte utilisateur courant
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = {})
  @MapKey(name = "auctionId")
  @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
  // A partir d'Hibernate 4.1.1, l'entit� parent n'est pas mise � jour par d�faut
  @OptimisticLock(excluded = false)
  private Map<String, CreditInvolvementNormal> getInvolvementNormalMapInternal()
  {
    return this.getInvolvementNormalMap().getInternal();
  }
  /**
   * Setter de la map interne des implications de cr�dits sur des ventes aux ench�res
   * normales du compte utilisateur courant
   * @param involvementMap Set interne de la map des implications de cr�dits sur
   * des ventes aux ench�res normales du compte utilisateur courant � positionner
   */
  @SuppressWarnings("unused")
  private void setInvolvementNormalMapInternal(Map<String, CreditInvolvementNormal> involvementMap)
  {
    this.setInvolvementNormalMap(new Bid4WinMap<String, CreditInvolvementNormal>(involvementMap, true));
  }
}