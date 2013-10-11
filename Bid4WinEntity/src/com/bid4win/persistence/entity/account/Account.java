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
 * Cette classe défini un compte utilisateur pour l'application BID4WIN<BR>
 * <BR>
 * @author Emeric Fillâtre
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
  /** Jeu de préférences du compte utilisateur */
  @Transient
  private PreferenceBundle preferenceBundle = null;
  /** Compte utilisateur ayant parrainé le compte utilisateur courant */
  @Transient
  private Account sponsor = null;
  /** Nombre de crédits disponibles pour le compte utilisateur */
  @Transient
  private int creditNb = 0;
  /** Nombre de crédits utilisés par le compte utilisateur */
  @Transient
  private int usedCreditNb = 0;
  /** Liste de lots de crédits du compte utilisateur */
  @Transient
  private Bid4WinList<CreditBundle> creditBundleList = new Bid4WinList<CreditBundle>();
  /** Map des implications de crédits sur des ventes aux enchères normales */
  @Transient
  private Bid4WinMap<String, CreditInvolvementNormal> involvementNormalMap =
    new Bid4WinMap<String, CreditInvolvementNormal>();

  /**
   * Constructeur pour création par introspection ou en proxy pour chargement différé
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
   * compte utilisateur en paramètre est nul
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
   * @param bundle Jeu de préférences du compte utilisateur
   * @throws ModelArgumentException Si le certificat de connexion ou l'email du
   * compte utilisateur en paramètre est nul
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
   * @param preferenceRoot Racine de la liste de préférences du compte utilisateur
   * @throws ModelArgumentException Si le certificat de connexion ou l'email du
   * compte utilisateur en paramètre est nul
   * @throws UserException Si l'une des préférences en argument est invalide
   */
  public Account(Credential credential, Email email, User user, PreferenceRoot preferenceRoot)
         throws ModelArgumentException, UserException
  {
    this(credential, email, user, new PreferenceBundle(preferenceRoot));
  }

  /**
   * Redéfini l'équivalence interne de deux comptes utilisateur sans prise en compte
   * de leurs relations afin d'y ajouter le test de leurs données propres
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
    // Ajoute les éléments du compte utilisateur
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
   * Ajoute à la liste des noeuds de relations du compte utilisateur le lien vers
   * son parrain, ses crédits et leurs implications
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
   * Permet de récupérer le parrain du compte utilisateur s'il correspondant à la
   * relation en argument. Elle doit être redéfinie pour toute nouvelle relation
   * de type simple à remonter
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
   * Permet de positionner le parrain du compte utilisateur s'il correspondant à
   * la relation en argument. Elle doit être redéfinie pour toute nouvelle relation
   * de type simple à positionner
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
   * Permet de récupérer la liste de crédits du compte utilisateur si elle correspond
   * à la relation en argument.
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
   * Permet de récupérer la map d'implications de crédits qui correspond à la
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
   * Cette fonction permet de savoir si le compte utilisateur possède encore des
   * crédits
   * @return True si le compte utilisateur possède des crédits, false sinon
   */
  public boolean hasCredit()
  {
    return this.getCreditNb() != 0;
  }
  /**
   * Cette méthode permet d'utiliser un crédit. Si le lot utilisé est vide une
   * fois le crédit retiré, il sera alors retiré du compte utilisateur
   * @return Le lot de provenance du crédit utilisé, potentiellement retiré du
   * compte utilisateur après utilisation
   * @throws ProtectionException Si le compte utilisateur courant ou le lot de
   * crédits utilisé est protégé
   * @throws UserException Si le compte utilisateur n'a aucun crédit
   */
  public CreditBundle useCredit() throws ProtectionException, UserException
  {
    return this.useCredit(1).keySet().iterator().next();
  }
  /**
   * Cette méthode permet d'utiliser des crédits. Les lots utilisés vides une fois
   * les crédits retirés seront retirés du compte utilisateur
   * @param nb Nombre de crédits à utiliser
   * @return La map des crédits utilisés (potentiellement retirés du compte utilisateur
   * après utilisation) avec en clé leurs lots de provenance et en valeur leur nombre,
   * @throws ProtectionException Si le compte utilisateur courant ou les lots de
   * crédits utilisés est protégé
   * @throws UserException Si le compte utilisateur n'a pas assez de crédits
   */
  public CreditMap useCredit(int nb) throws ProtectionException, UserException
  {
    // Vérifie la présence des crédits
    UtilNumber.checkMinValue("creditNb", this.getCreditNb(), nb, true,
                             AccountRef.ACCOUNT_CREDIT_NB_INSUFFICIENT_ERROR);
    // Récupère les lots de crédits et en utilise une partie
    CreditMap usedCreditMap = new CreditMap();
    while(nb > 0)
    {
      CreditBundle usedBundle = this.getCreditBundle();
      int usedNb = Math.min(nb, usedBundle.getCurrentNb());
      usedCreditMap.put(usedBundle.use(usedNb), usedNb);
      // Valide l'utilisation des crédits
      nb -= usedNb;
      this.setCreditNb(this.getCreditNb() - usedNb);
      this.setUsedCreditNb(this.getUsedCreditNb() + usedNb);
    }
    // Retourne les lot de crédits utilisés
    return usedCreditMap;
  }
  /**
   * Getter du lot de crédit à utiliser
   * @return Le lot de crédits à utiliser
   */
  private CreditBundle getCreditBundle()
  {
    return this.getCreditBundleList().getFirst();
  }

  /**
   * Getter des implications de crédits sur la vente aux enchères normale en argument
   * @param auction Vente aux enchères normale sur laquelle les implications de
   * crédits doivent être récupérer
   * @return Les implications de crédits sur la vente aux enchères normale en argument
   */
  public CreditInvolvementNormal getInvolvementNormal(NormalAuction auction)
  {
    return this.getInvolvementNormalMap().get(auction.getId());
  }
  /**
   * Getter du set des implications de crédits sur des ventes aux enchères normales
   * @return Le set des implications de crédits sur des ventes aux enchères normales
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
   * Cette méthode permet de définir les informations sur l'utilisateur du compte
   * @param user Définition des informations sur l'utilisateur du compte
   * @throws ProtectionException Si le compte utilisateur courant est protégé
   */
  public void defineUser(User user) throws ProtectionException
  {
    // Vérifie la protection du compte utilisateur courant
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
   * Cette méthode permet de définir le jeu de préférences du compte utilisateur
   * @param bundle Définition du jeu de préférences du compte utilisateur
   * @throws ProtectionException Si le compte utilisateur courant est protégé
   */
  private void definePreferenceBundle(PreferenceBundle bundle) throws ProtectionException
  {
    // Vérifie la protection du compte utilisateur courant
    this.checkProtection();
    // Utilisation des préférences par défaut
    if(bundle == null)
    {
      bundle = new PreferenceBundle();
    }
    this.setPreferenceBundle(bundle);
  }

  /**
   * Cette méthode permet de définir le parrain du compte utilisateur courant
   * @param sponsor Définition du parrain du compte utilisateur courant
   * @throws ProtectionException Si le compte utilisateur courant est protégé
   * @throws UserException Si on défini un parrain nul ou déjà défini
   */
  protected void linkToSponsor(Account sponsor) throws ProtectionException, UserException
  {
    this.linkTo(Account_Relations.RELATION_SPONSOR, sponsor);
  }
  /**
   * Cette méthode permet de retirer le parrain du compte utilisateur courant
   * @return L'ancien parrain du compte utilisateur
   * @throws ProtectionException Si le compte utilisateur courant est protégé
   * @throws UserException Si aucun parrain n'est défini
   */
  protected Account unlinkFromSponsor() throws ProtectionException, UserException
  {
    return (Account)this.unlinkFrom(Account_Relations.RELATION_SPONSOR);
  }

  /**
   * Getter de la liste de lots de crédits du compte utilisateur
   * @return La liste de lots de crédits du compte utilisateur
   */
  private Bid4WinList<CreditBundle> getCreditBundleList()
  {
    return this.creditBundleList;
  }
  /**
   * Setter de la liste de lots de crédits du compte utilisateur
   * @param creditBundleList Liste de lots de crédits du compte utilisateur à
   * positionner
   */
  private void setCreditBundleList(Bid4WinList<CreditBundle> creditBundleList)
  {
    this.creditBundleList = creditBundleList;
  }
  /**
   * Getter de la map des implications de crédits sur des ventes aux enchères
   * normales du compte utilisateur courant
   * @return La map des implications de crédits sur des ventes aux enchères normales
   * du compte utilisateur courant
   */
  private Bid4WinMap<String, CreditInvolvementNormal> getInvolvementNormalMap()
  {
    return this.involvementNormalMap;
  }
  /**
   * Setter de la map des implications de crédits sur des ventes aux enchères
   * normales du compte utilisateur courant
   * @param involvementMap Map des implications de crédits sur des ventes aux enchères
   * normales du compte utilisateur courant à positionner
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
    // A la liaison d'un lot de crédits, il faut augmenter les crédits du compte
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
   * @param user Informations sur l'utilisateur du compte à positionner
   */
  private void setUser(User user)
  {
    this.user = user;
  }

  /**
   * Getter du jeu de préférences du compte utilisateur
   * @return Le jeu de préférences du compte utilisateur
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Embedded()
  public PreferenceBundle getPreferenceBundle()
  {
    return this.preferenceBundle;
  }
  /**
   * Setter du jeu de préférences du compte utilisateur
   * @param preferenceBundle Jeu de préférences du compte utilisateur à positionner
   */
  private void setPreferenceBundle(PreferenceBundle preferenceBundle)
  {
    this.preferenceBundle = preferenceBundle;
  }

  /**
   * Getter du compte utilisateur ayant parrainé le compte utilisateur courant
   * @return Le compte utilisateur ayant parrainé le compte utilisateur courant
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
   * Setter du compte utilisateur ayant parrainé le compte utilisateur courant
   * @param sponsor Compte utilisateur ayant parrainé le compte utilisateur courant
   * à positionner
   */
  private void setSponsor(Account sponsor)
  {
    this.sponsor = sponsor;
  }

  /**
   * Getter du nombre de crédits disponibles pour le compte utilisateur
   * @return Le nombre de crédits disponibles pour le compte utilisateur
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "CREDIT_NB", length = 5, nullable = false, unique = false)
  public int getCreditNb()
  {
    return this.creditNb;
  }
  /**
   * Setter du nombre de crédits disponibles pour le compte utilisateur
   * @param creditNb Nombre de crédits disponibles pour le compte utilisateur
   * à positionner
   */
  private void setCreditNb(int creditNb)
  {
    this.creditNb = creditNb;
  }

  /**
   * Getter du nombre de crédits utilisés par le compte utilisateur
   * @return Le nombre de crédits utilisés par le compte utilisateur
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "CREDIT_USED_NB", length = 5, nullable = false, unique = false)
  public int getUsedCreditNb()
  {
    return this.usedCreditNb;
  }
  /**
   * Setter du nombre de crédits utilisés par le compte utilisateur
   * @param usedCreditNb Nombre de crédits utilisés par le compte utilisateur à
   * positionner
   */
  private void setUsedCreditNb(int usedCreditNb)
  {
    this.usedCreditNb = usedCreditNb;
  }

  /**
   * Getter de la liste interne de lots de crédits du compte utilisateur
   * @return La liste interne de lots de crédits du compte utilisateur
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @OneToMany(mappedBy = "accountLink", fetch = FetchType.LAZY, cascade = {})
  @OrderBy(value = "id")
  @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
  // A partir d'Hibernate 4.1.1, l'entité parent n'est pas mise à jour par défaut
  @OptimisticLock(excluded = false)
  private List<CreditBundle> getCreditBundleListInternal()
  {
    return this.getCreditBundleList().getInternal();
  }
  /**
   * Setter de la liste interne de lots de crédits du compte utilisateur
   * @param internalCreditBundleList Liste interne de lots de crédits du compte
   * utilisateur à positionner
   */
  @SuppressWarnings(value = "unused")
  private void setCreditBundleListInternal(List<CreditBundle> internalCreditBundleList)
  {
    this.setCreditBundleList(new Bid4WinList<CreditBundle>(internalCreditBundleList, true));
  }

  /**
   * Getter de la map interne des implications de crédits sur des ventes aux enchères
   * normales du compte utilisateur courant
   * @return La map interne des implications de crédits sur des ventes aux enchères
   * normales du compte utilisateur courant
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = {})
  @MapKey(name = "auctionId")
  @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
  // A partir d'Hibernate 4.1.1, l'entité parent n'est pas mise à jour par défaut
  @OptimisticLock(excluded = false)
  private Map<String, CreditInvolvementNormal> getInvolvementNormalMapInternal()
  {
    return this.getInvolvementNormalMap().getInternal();
  }
  /**
   * Setter de la map interne des implications de crédits sur des ventes aux enchères
   * normales du compte utilisateur courant
   * @param involvementMap Set interne de la map des implications de crédits sur
   * des ventes aux enchères normales du compte utilisateur courant à positionner
   */
  @SuppressWarnings("unused")
  private void setInvolvementNormalMapInternal(Map<String, CreditInvolvementNormal> involvementMap)
  {
    this.setInvolvementNormalMap(new Bid4WinMap<String, CreditInvolvementNormal>(involvementMap, true));
  }
}