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
 * Cette classe d�fini l'implication globale de cr�dits en provenance de lots
 * potentiellement distincts<BR>
 * <BR>
 * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
 * @param <USAGE> D�finition des utilisations de cr�dits en provenance de diff�rents
 * lots<BR>
 * @param <BUNDLE> D�finition des lots de provenance des utilisations de cr�dits<BR>
 * @param <HISTORY> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
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
  /** Map des utilisations de cr�dits class�es selon la r�f�rence de leur provenance */
  @Transient
  private Bid4WinMap<Long, USAGE> usageMap = new Bid4WinMap<Long, USAGE>();
  /** Nombre de cr�dits utilis�s globalement */
  @Transient
  private int usedNb = 0;
  /** Identifiant d'historisation de l'implication de cr�dits */
  @Transient
  private Long historyId = null;
  /** Historisation de l'implication de cr�dits */
  @Transient
  private HISTORY history = null;

  /**
   * Constructeur pour cr�ation par introspection
   */
  protected CreditInvolvement()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param account Compte utilisateur des cr�dits utilis�s
   * @throws UserException Si le compte utilisateur est nul
   */
  protected CreditInvolvement(Account account) throws UserException
  {
    super(account);
  }

  /**
   * Red�fini l'�quivalence interne de deux implications de cr�dits sans prise en
   * compte de leurs relations afin d'y ajouter le test de leurs donn�es propres
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
   * Permet d'effectuer le rendu simple de l'implication de cr�dits courante sans
   * prise en compte de ses relations
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#renderRelationNone()
   */
  @Override
  protected StringBuffer renderRelationNone()
  {
    // Effectue le rendu de base sans lien d'une entit�
    StringBuffer buffer = super.renderRelationNone();
    // Ajoute les �l�ments de l'utilisation de cr�dit
    buffer.append(" USED_NB=" + this.getUsedNb());
    // Retourne le rendu
    return buffer;
  }
  /**
   * Ajoute � la liste des noeuds de relations de l'implication de cr�dits la map
   * des cr�dits utilis�s
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
   * Permet de r�cup�rer la map des cr�dits utilis�s par l'implication globale si
   * elle correspond � la relation en argument. Elle doit �tre red�finie pour toute
   * nouvelle relation de type map � remonter
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
   * Cette m�thode permet de r�cup�rer la cl� associ�e � l'utilisation de cr�dits
   * en param�tre pour son classement dans la map correspondant suivant la relation
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
   * Getter de l'�tat d'historiation de l'implication de cr�dits
   * @return L'�tat d'historiation de l'implication de cr�dits
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
   * Getter de l'identifiant d'historisation de l'implication de cr�dits
   * @return L'identifiant d'historisation de l'implication de cr�dits
   */
  public Long getHistoryId()
  {
    return this.historyId;
  }
  /**
   * Setter de l'identifiant d'historisation de l'implication de cr�dits
   * @param historyId Identifiant d'historisation de l'implication de cr�dits �
   * positionner
   */
  private void setHistoryId(Long historyId)
  {
    this.historyId = historyId;
  }
  /**
   * Getter de l'historisation de l'implication de cr�dits
   * @return L'historisation de l'implication de cr�dits � positionner
   */
  public HISTORY getHistory()
  {
    return this.history;
  }
  /**
   * Setter de l'historisation de l'implication de cr�dits
   * @param history Historisation de l'implication de cr�dits � positionner
   */
  private void setHistory(HISTORY history)
  {
    this.history = history;
  }
  /**
   * Cette m�thode permet d'historiser l'implication de cr�dits courante
   * @return L'historisation de l'implication de cr�dits courante
   * @throws ProtectionException Si l'implication de cr�dit courante est prot�g�e
   * @throws UserException Si l'implication de cr�dits courante est d�j� historis�e
   */
  public HISTORY historize() throws UserException
  {
    // V�rifie la protection de l'implication de cr�dits courante
    this.checkProtection();
    UtilBoolean.checkFalse("historized", this.isHistorized(),
                           AuctionRef.AUCTION_HISTORIZED_ERROR);
    this.setHistory(this.createHistory());
    return this.getHistory();
  }
  /**
   * Cette m�thode doit �tre d�finie afin de cr�er l'historisation correspondant
   * � l'implication de cr�dits courante
   * @return L'historisation correspondant � l'implication de cr�dits courante
   * @throws UserException Si un probl�me intervient lors de la cr�ation de l'historisation
   */
  protected abstract HISTORY createHistory() throws UserException;
  /**
   * Cette m�thode permet de positionner l'identifiant de l'historisation du lot
   * de cr�dits courant
   * @return Le lot de cr�dit courant
   * @throws UserException Si le lot de cr�dit courant n'est pas historis� ou si
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
   * Getter du set complet des utilisations de cr�dits pour toutes les provenances
   * @return Le set complet des utilisations de cr�dits selon toutes les provenances
   */
  public Bid4WinSet<USAGE> getUsageSet()
  {
    return new Bid4WinSet<USAGE>(this.getUsageMap().values());
  }
  /**
   * Getter de l'utilisation de cr�dits dont le lot de provenance est en argument
   * @param bundle Lot de provenance des cr�dits dont il faut rechercher l'utilisation
   * @return L'utilisation de cr�dits dont le lot de provenance est en argument
   * @throws UserException Si le lot de cr�dits en argument est nul
   */
  public USAGE getUsage(BUNDLE bundle) throws UserException
  {
    UtilObject.checkNotNull("bundle", bundle,
                            AccountRef.ACCOUNT_CREDIT_REFERENCE_MISSING_ERROR);
    return this.getUsageMap().get(bundle.getId());
  }
  /**
   * Cette m�thode permet d'ajouter � l'implication de cr�dits courante des cr�dits
   * issus des lots d�finis en argument
   * @param usageMap Map des lots de provenance et nombres des cr�dits utilis�s
   * @return Le set d'utilisations de tous le cr�dits des lots en argument par l'
   * implication  courante
   * @throws ProtectionException Si l'implication de cr�dits courante ou une des
   * utilisations de cr�dits est prot�g�e
   * @throws UserException Si l'un des lots de cr�dits est nul ou si son compte
   * utilisateur ne correspond pas au compte utilisateur de l'implication courante
   * ou si un des nombres de cr�dits utilis�s associ� est inf�rieur � un
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
   * Cette m�thode permet d'ajouter � l'implication de cr�dits courante des cr�dits
   * issus du lot d�fini en argument
   * @param bundle Lot de provenance des cr�dits utilis�s
   * @param usedNb Nombre de cr�dit utilis�s
   * @return L'utilisation de tous le cr�dits du lot en argument par l'implication
   * courante
   * @throws ProtectionException Si l'implication de cr�dits courante ou une des
   * utilisations de cr�dits est prot�g�e
   * @throws UserException Si le lot de cr�dits est nul ou si son compte utilisateur
   * ne correspond pas au compte utilisateur de l'implication courante ou si le
   * nombre de cr�dits utilis�s est inf�rieur � un
   */
  public USAGE addUsage(BUNDLE bundle, int usedNb) throws ProtectionException, UserException
  {
    // V�rifie la protection de l'utilisation de cr�dits courante
    this.checkProtection();
    // V�rifie la provenance des cr�dits utilis�s
    UtilObject.checkNotNull("bundle", bundle, AccountRef.ACCOUNT_CREDIT_REFERENCE_MISSING_ERROR);
    UtilObject.checkEquals("accountId", bundle.getAccount().getId(),
                           this.getAccount().getId(), AccountRef.ACCOUNT_INVALID_ERROR);
    // Recherche une utilisation du m�me lot d�j� r�f�renc�e
    USAGE usage = this.getUsage(bundle);
    // Augmente l'utilisation du lot d�j� r�f�renc�
    if(usage != null)
    {
      usage.addUsedNb(usedNb);
    }
    // Ajoute une utilisation du lot en argument
    else
    {
      usage = this.createCreditUsage(bundle, usedNb);
    }
    // Retourne l'utilisation des cr�dits du lot en argument
    return usage;
  }
  /**
   * Cette m�thode doit �tre d�finie afin de cr�er l'utilisation de cr�dits correspondant
   * � l'implication courante
   * @param bundle Lot duquel sont issus les cr�dits utilis�s
   * @param usedNb Nombre de cr�dit utilis�s
   * @return L'utilisation de cr�dits correspondant � l'implication courante
   * @throws ProtectionException Si l'implication de cr�dits courante est prot�g�e
   * @throws UserException TODO A COMMENTER
   */
  protected abstract USAGE createCreditUsage(BUNDLE bundle, int usedNb)
            throws ProtectionException, UserException;

  /**
   * Getter de la map des utilisations de cr�dits class�es selon la r�f�rence de
   * leur provenance
   * @return La map des utilisations de cr�dits class�es selon la r�f�rence de
   * leur provenance
   */
  private Bid4WinMap<Long, USAGE> getUsageMap()
  {
    return this.usageMap;
  }
  /**
   * Setter de la map des utilisations de cr�dits class�es selon la r�f�rence de
   * leur provenance
   * @param usageMap Map des utilisations de cr�dits class�es selon la r�f�rence
   * de leur provenance � positionner
   */
  private void setUsageMap(Bid4WinMap<Long, USAGE> usageMap)
  {
    this.usageMap = usageMap;
  }

  /**
   * Cette m�thode permet d'augmenter le nombre de cr�dits utilis�s
   * @param usedNb Nombre de cr�dits utilis�s globalement en plus
   * @throws ProtectionException Si l'implication de cr�dits courante est prot�g�e
   * @throws UserException Si on ajoute un nombre de cr�dits inf�rieur � un
   */
  protected void addUsedNb(int usedNb) throws ProtectionException, UserException
  {
    // V�rifie la protection de l'implication de cr�dits courante
    this.checkProtection();
    // Augmente le nombre de cr�dits utilis�s globalement
    this.setUsedNb(this.getUsedNb() + UtilNumber.checkMinValue("usedNb", usedNb, 1, true,
                                                               AccountRef.ACCOUNT_CREDIT_NB_INVALID_ERROR));
  }
  /**
   * Cette m�thode permet de r�cup�rer la valeur totale des cr�dits utilis�s par
   * l'implication courante
   * @return La valeur totale des cr�dits utilis�s par l'implication courante
   * @throws UserException Si le calcul rencontre des probl�mes
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
   * Getter de la map interne des utilisations de cr�dits class�es selon la r�f�rence
   * de leur provenance
   * @return La map interne des utilisations de cr�dits class�es selon la r�f�rence
   * de leur provenance
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @OneToMany(mappedBy = "involvement", fetch = FetchType.LAZY, cascade = {})
  @MapKey(name = "bundleId")
  @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
  // A partir d'Hibernate 4.1.1, l'entit� parent n'est pas mise � jour par d�faut
  @OptimisticLock(excluded = false)
  private Map<Long, USAGE> getUsageMapInternal()
  {
    return this.getUsageMap().getInternal();
  }
  /**
   * Setter de la map interne des utilisations de cr�dits class�es selon la r�f�rence
   * de leur provenance
   * @param internalUsageMap Map interne des utilisations de cr�dits class�es selon
   * la r�f�rence de leur provenance � positionner
   */
  @SuppressWarnings(value = "unused")
  private void setUsageMapInternal(Map<Long, USAGE> internalUsageMap)
  {
    this.setUsageMap(new Bid4WinMap<Long, USAGE>(internalUsageMap, true));
  }

  /**
   * Getter du nombre de cr�dits utilis�s globalement
   * @return Le nombre de cr�dits utilis�s globalement
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "USED_NB", length = 5, nullable = false, unique = false)
  public int getUsedNb()
  {
    return this.usedNb;
  }
  /**
   * Setter du nombre de cr�dits utilis�s globalement
   * @param usedNb Nombre de cr�dits utilis�s globalement � positionner
   */
  private void setUsedNb(int usedNb)
  {
    this.usedNb = usedNb;
  }
}
