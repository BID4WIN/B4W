package com.bid4win.persistence.entity.account.credit;

import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.MapKey;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OptimisticLock;

import com.bid4win.commons.core.UtilBoolean;
import com.bid4win.commons.core.UtilNumber;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.AccountRef;
import com.bid4win.commons.core.reference.MessageRef.AuctionRef;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultipleAutoID;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.price.Amount;

/**
 * Cette classe défini l'implication globale de crédits en provenance de lots
 * potentiellement distincts<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * @param <USAGE> Définition des utilisations de crédits en provenance de différents
 * lots<BR>
 * @param <BUNDLE> Définition des lots de provenance des utilisations de crédits<BR>
 * @param <HISTORY> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
//Annotation pour la persistence
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class CreditInvolvement<CLASS extends CreditInvolvement<CLASS, USAGE, BUNDLE, HISTORY>,
                                        USAGE extends CreditUsageAbstract<USAGE, BUNDLE, CLASS>,
                                        BUNDLE extends CreditBundleAbstract<BUNDLE>,
                                        HISTORY extends CreditInvolvement<HISTORY, ?, CreditBundleHistory, ?>>
       extends AccountBasedEntityMultipleAutoID<CLASS, Account>
{
  /** Map des utilisations de crédits classées selon la référence de leur provenance */
  @Transient
  private Bid4WinMap<Long, USAGE> usageMap = new Bid4WinMap<Long, USAGE>();
  /** Nombre de crédits utilisés globalement */
  @Transient
  private int usedNb = 0;
  /** Identifiant d'historisation de l'implication de crédits */
  @Transient
  private Long historyId = null;
  /** Historisation de l'implication de crédits */
  @Transient
  private HISTORY history = null;

  /**
   * Constructeur pour création par introspection
   */
  protected CreditInvolvement()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param account Compte utilisateur des crédits utilisés
   * @throws UserException Si le compte utilisateur est nul
   */
  protected CreditInvolvement(Account account) throws UserException
  {
    super(account);
  }

  /**
   * Redéfini l'équivalence interne de deux implications de crédits sans prise en
   * compte de leurs relations afin d'y ajouter le test de leurs données propres
   * @param toBeCompared {@inheritDoc}
   * @param identical {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#sameRelationNoneInternal(com.bid4win.commons.persistence.entity.Bid4WinEntity, boolean)
   */
  @Override
  protected boolean sameRelationNoneInternal(CLASS toBeCompared, boolean identical)
  {
    return super.sameRelationNoneInternal(toBeCompared, identical) &&
           this.getUsedNb() == toBeCompared.getUsedNb();
  }
  /**
   * Permet d'effectuer le rendu simple de l'implication de crédits courante sans
   * prise en compte de ses relations
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#renderRelationNone()
   */
  @Override
  protected StringBuffer renderRelationNone()
  {
    // Effectue le rendu de base sans lien d'une entité
    StringBuffer buffer = super.renderRelationNone();
    // Ajoute les éléments de l'utilisation de crédit
    buffer.append(" USED_NB=" + this.getUsedNb());
    // Retourne le rendu
    return buffer;
  }
  /**
   * Ajoute à la liste des noeuds de relations de l'implication de crédits la map
   * des crédits utilisés
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.account.AccountBasedEntity#getFullRelationNodeList()
   */
  @Override
  protected Bid4WinList<Bid4WinRelationNode> getFullRelationNodeList()
  {
    Bid4WinList<Bid4WinRelationNode> nodeList = super.getFullRelationNodeList();
    nodeList.add(CreditInvolvement_Relations.NODE_USAGE_MAP);
    return nodeList;
  }
  /**
   * Permet de récupérer la map des crédits utilisés par l'implication globale si
   * elle correspond à la relation en argument. Elle doit être redéfinie pour toute
   * nouvelle relation de type map à remonter
   * @param relation {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getRelationMap(com.bid4win.commons.persistence.entity.Bid4WinRelation)
   */
  @Override
  protected Map<?, ? extends Bid4WinEntity<?, ?>> getRelationMap(Bid4WinRelation relation)
  {
    if(relation.equals(CreditInvolvement_Relations.RELATION_USAGE_MAP))
    {
      return this.getUsageMapInternal();
    }
    return super.getRelationMap(relation);
  }
  /**
   * Cette méthode permet de récupérer la clé associée à l'utilisation de crédits
   * en paramètre pour son classement dans la map correspondant suivant la relation
   * en argument
   * @param relation {@inheritDoc}
   * @param value {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getRelationMapKey(com.bid4win.commons.persistence.entity.Bid4WinRelation, com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @SuppressWarnings("unchecked")
  @Override
  protected Object getRelationMapKey(Bid4WinRelation relation, Bid4WinEntity<?, ?> value)
  {
    if(relation.equals(CreditInvolvement_Relations.RELATION_USAGE_MAP))
    {
      return ((USAGE)value).getBundleId();
    }
    return super.getRelationMapKey(relation, value);
  }

  /**
   * Getter de l'état d'historiation de l'implication de crédits
   * @return L'état d'historiation de l'implication de crédits
   */
  public boolean isHistorized()
  {
    if(this.getHistoryId() != null || this.getHistory() != null)
    {
      return true;
    }
    return false;
  }
  /**
   * Getter de l'identifiant d'historisation de l'implication de crédits
   * @return L'identifiant d'historisation de l'implication de crédits
   */
  public Long getHistoryId()
  {
    return this.historyId;
  }
  /**
   * Setter de l'identifiant d'historisation de l'implication de crédits
   * @param historyId Identifiant d'historisation de l'implication de crédits à
   * positionner
   */
  private void setHistoryId(Long historyId)
  {
    this.historyId = historyId;
  }
  /**
   * Getter de l'historisation de l'implication de crédits
   * @return L'historisation de l'implication de crédits à positionner
   */
  public HISTORY getHistory()
  {
    return this.history;
  }
  /**
   * Setter de l'historisation de l'implication de crédits
   * @param history Historisation de l'implication de crédits à positionner
   */
  private void setHistory(HISTORY history)
  {
    this.history = history;
  }
  /**
   * Cette méthode permet d'historiser l'implication de crédits courante
   * @return L'historisation de l'implication de crédits courante
   * @throws ProtectionException Si l'implication de crédit courante est protégée
   * @throws UserException Si l'implication de crédits courante est déjà historisée
   */
  public HISTORY historize() throws UserException
  {
    // Vérifie la protection de l'implication de crédits courante
    this.checkProtection();
    UtilBoolean.checkFalse("historized", this.isHistorized(),
                           AuctionRef.AUCTION_HISTORIZED_ERROR);
    this.setHistory(this.createHistory());
    return this.getHistory();
  }
  /**
   * Cette méthode doit être définie afin de créer l'historisation correspondant
   * à l'implication de crédits courante
   * @return L'historisation correspondant à l'implication de crédits courante
   * @throws UserException Si un problème intervient lors de la création de l'historisation
   */
  protected abstract HISTORY createHistory() throws UserException;
  /**
   * Cette méthode permet de positionner l'identifiant de l'historisation du lot
   * de crédits courant
   * @return Le lot de crédit courant
   * @throws UserException Si le lot de crédit courant n'est pas historisé ou si
   * l'identifiant de son historisation est nul
   */
  @SuppressWarnings("unchecked")
  public CLASS defineHistoryId() throws UserException
  {
    UtilObject.checkNotNull("history", this.getHistory(),
                            AccountRef.ACCOUNT_CREDIT_NOT_HISTORIZED_ERROR);
    this.setHistoryId(UtilObject.checkNotNull("historyId", this.getHistory().getId(),
                      AccountRef.ACCOUNT_CREDIT_NOT_HISTORIZED_ERROR));
    return (CLASS)this;
  }

  /**
   * Getter du set complet des utilisations de crédits pour toutes les provenances
   * @return Le set complet des utilisations de crédits selon toutes les provenances
   */
  public Bid4WinSet<USAGE> getUsageSet()
  {
    return new Bid4WinSet<USAGE>(this.getUsageMap().values());
  }
  /**
   * Getter de l'utilisation de crédits dont le lot de provenance est en argument
   * @param bundle Lot de provenance des crédits dont il faut rechercher l'utilisation
   * @return L'utilisation de crédits dont le lot de provenance est en argument
   * @throws UserException Si le lot de crédits en argument est nul
   */
  public USAGE getUsage(BUNDLE bundle) throws UserException
  {
    UtilObject.checkNotNull("bundle", bundle,
                            AccountRef.ACCOUNT_CREDIT_REFERENCE_MISSING_ERROR);
    return this.getUsageMap().get(bundle.getId());
  }
  /**
   * Cette méthode permet d'ajouter à l'implication de crédits courante des crédits
   * issus des lots définis en argument
   * @param usageMap Map des lots de provenance et nombres des crédits utilisés
   * @return Le set d'utilisations de tous le crédits des lots en argument par l'
   * implication  courante
   * @throws ProtectionException Si l'implication de crédits courante ou une des
   * utilisations de crédits est protégée
   * @throws UserException Si l'un des lots de crédits est nul ou si son compte
   * utilisateur ne correspond pas au compte utilisateur de l'implication courante
   * ou si un des nombres de crédits utilisés associé est inférieur à un
   */
  public Bid4WinSet<USAGE> addUsage(Bid4WinMap<BUNDLE, Integer> usageMap)
        throws ProtectionException, UserException
  {
    Bid4WinMap<Long, USAGE> resultMap = new Bid4WinMap<Long, USAGE>();
    for(Entry<BUNDLE, Integer> entry : usageMap.entrySet())
    {
      USAGE usage = this.addUsage(entry.getKey(), entry.getValue());
      resultMap.put(usage.getBundle().getId(), usage);
    }
    return new Bid4WinSet<USAGE>(resultMap.values());
  }
  /**
   * Cette méthode permet d'ajouter à l'implication de crédits courante des crédits
   * issus du lot défini en argument
   * @param bundle Lot de provenance des crédits utilisés
   * @param usedNb Nombre de crédit utilisés
   * @return L'utilisation de tous le crédits du lot en argument par l'implication
   * courante
   * @throws ProtectionException Si l'implication de crédits courante ou une des
   * utilisations de crédits est protégée
   * @throws UserException Si le lot de crédits est nul ou si son compte utilisateur
   * ne correspond pas au compte utilisateur de l'implication courante ou si le
   * nombre de crédits utilisés est inférieur à un
   */
  public USAGE addUsage(BUNDLE bundle, int usedNb) throws ProtectionException, UserException
  {
    // Vérifie la protection de l'utilisation de crédits courante
    this.checkProtection();
    // Vérifie la provenance des crédits utilisés
    UtilObject.checkNotNull("bundle", bundle, AccountRef.ACCOUNT_CREDIT_REFERENCE_MISSING_ERROR);
    UtilObject.checkEquals("accountId", bundle.getAccount().getId(),
                           this.getAccount().getId(), AccountRef.ACCOUNT_INVALID_ERROR);
    // Recherche une utilisation du même lot déjà référencée
    USAGE usage = this.getUsage(bundle);
    // Augmente l'utilisation du lot déjà référencé
    if(usage != null)
    {
      usage.addUsedNb(usedNb);
    }
    // Ajoute une utilisation du lot en argument
    else
    {
      usage = this.createCreditUsage(bundle, usedNb);
    }
    // Retourne l'utilisation des crédits du lot en argument
    return usage;
  }
  /**
   * Cette méthode doit être définie afin de créer l'utilisation de crédits correspondant
   * à l'implication courante
   * @param bundle Lot duquel sont issus les crédits utilisés
   * @param usedNb Nombre de crédit utilisés
   * @return L'utilisation de crédits correspondant à l'implication courante
   * @throws ProtectionException Si l'implication de crédits courante est protégée
   * @throws UserException TODO A COMMENTER
   */
  protected abstract USAGE createCreditUsage(BUNDLE bundle, int usedNb)
            throws ProtectionException, UserException;

  /**
   * Getter de la map des utilisations de crédits classées selon la référence de
   * leur provenance
   * @return La map des utilisations de crédits classées selon la référence de
   * leur provenance
   */
  private Bid4WinMap<Long, USAGE> getUsageMap()
  {
    return this.usageMap;
  }
  /**
   * Setter de la map des utilisations de crédits classées selon la référence de
   * leur provenance
   * @param usageMap Map des utilisations de crédits classées selon la référence
   * de leur provenance à positionner
   */
  private void setUsageMap(Bid4WinMap<Long, USAGE> usageMap)
  {
    this.usageMap = usageMap;
  }

  /**
   * Cette méthode permet d'augmenter le nombre de crédits utilisés
   * @param usedNb Nombre de crédits utilisés globalement en plus
   * @throws ProtectionException Si l'implication de crédits courante est protégée
   * @throws UserException Si on ajoute un nombre de crédits inférieur à un
   */
  protected void addUsedNb(int usedNb) throws ProtectionException, UserException
  {
    // Vérifie la protection de l'implication de crédits courante
    this.checkProtection();
    // Augmente le nombre de crédits utilisés globalement
    this.setUsedNb(this.getUsedNb() + UtilNumber.checkMinValue("usedNb", usedNb, 1, true,
                                                               AccountRef.ACCOUNT_CREDIT_NB_INVALID_ERROR));
  }
  /**
   * Cette méthode permet de récupérer la valeur totale des crédits utilisés par
   * l'implication courante
   * @return La valeur totale des crédits utilisés par l'implication courante
   * @throws UserException Si le calcul rencontre des problèmes
   */
  public Amount getTotalValue() throws UserException
  {
    Amount totalValue = new Amount(0);
    for(USAGE usage : this.getUsageMap().values())
    {
      totalValue = totalValue.add(usage.getBundle().getUnitValue());
    }
    return totalValue;
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de la map interne des utilisations de crédits classées selon la référence
   * de leur provenance
   * @return La map interne des utilisations de crédits classées selon la référence
   * de leur provenance
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @OneToMany(mappedBy = "involvement", fetch = FetchType.LAZY, cascade = {})
  @MapKey(name = "bundleId")
  @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
  // A partir d'Hibernate 4.1.1, l'entité parent n'est pas mise à jour par défaut
  @OptimisticLock(excluded = false)
  private Map<Long, USAGE> getUsageMapInternal()
  {
    return this.getUsageMap().getInternal();
  }
  /**
   * Setter de la map interne des utilisations de crédits classées selon la référence
   * de leur provenance
   * @param internalUsageMap Map interne des utilisations de crédits classées selon
   * la référence de leur provenance à positionner
   */
  @SuppressWarnings(value = "unused")
  private void setUsageMapInternal(Map<Long, USAGE> internalUsageMap)
  {
    this.setUsageMap(new Bid4WinMap<Long, USAGE>(internalUsageMap, true));
  }

  /**
   * Getter du nombre de crédits utilisés globalement
   * @return Le nombre de crédits utilisés globalement
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "USED_NB", length = 5, nullable = false, unique = false)
  public int getUsedNb()
  {
    return this.usedNb;
  }
  /**
   * Setter du nombre de crédits utilisés globalement
   * @param usedNb Nombre de crédits utilisés globalement à positionner
   */
  private void setUsedNb(int usedNb)
  {
    this.usedNb = usedNb;
  }
}
