package trash.old;
//package com.bid4win.persistence.entity.account;
//
//import java.util.Map;
//import java.util.Map.Entry;
//
//import javax.persistence.Access;
//import javax.persistence.AccessType;
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.FetchType;
//import javax.persistence.MapKey;
//import javax.persistence.MappedSuperclass;
//import javax.persistence.OneToMany;
//import javax.persistence.Transient;
//
//import org.hibernate.annotations.Cache;
//import org.hibernate.annotations.CacheConcurrencyStrategy;
//
//import com.bid4win.commons.core.UtilObject;
//import com.bid4win.commons.core.collection.Bid4WinList;
//import com.bid4win.commons.core.collection.Bid4WinMap;
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.commons.core.security.exception.ProtectionException;
//import com.bid4win.commons.persistence.entity.Bid4WinEntity;
//import com.bid4win.commons.persistence.entity.Bid4WinRelation;
//import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
//import com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultipleAutoID;
//
///**
// * Cette classe défini l'utilisation d'un certain nombre de crédits<BR>
// * <BR>
// * @param <CLASS> Doit définir la classe réellement instanciée<BR>
// * @param <BUNDLE_USAGE> Doit définir le type d'utilisation de lot de crédits<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
////Annotation pour la persistence
//@MappedSuperclass
//@Access(AccessType.FIELD)
//public abstract class CreditUsage<CLASS extends CreditUsage<CLASS, BUNDLE_USAGE>,
//                                  BUNDLE_USAGE extends CreditBundleUsage<BUNDLE_USAGE, CLASS>>
//       extends AccountBasedEntityMultipleAutoID<CLASS, Account>
//{
//  /** Map des utilisations de lots de crédits desquels sont issus les crédits utilisés */
//  @Transient
//  private Bid4WinMap<Long, BUNDLE_USAGE> bundleUsageMap = new Bid4WinMap<Long, BUNDLE_USAGE>();
//  /** Nombre de crédits utilisés */
//  @Transient
//  private int usedNb = 0;
//
//  /**
//   * Constructeur pour création par introspection
//   */
//  protected CreditUsage()
//  {
//    super();
//  }
//  /**
//   * Constructeur complet
//   * @param account Compte utilisateur des crédits utilisés
//   * @throws ProtectionException Si le compte utilisateur en argument est protégé
//   * @throws ModelArgumentException Si le compte utilisateur en argument est nul
//   */
//  public CreditUsage(Account account) throws ProtectionException, ModelArgumentException
//  {
//    super(account);
//  }
//
//  /**
//   * Redéfini l'équivalence interne de deux utilisations de crédits sans prise en
//   * compte de leurs relations afin d'y ajouter le test de leurs données propres
//   * @param toBeCompared {@inheritDoc}
//   * @param identical {@inheritDoc}
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#sameRelationNoneInternal(com.bid4win.commons.persistence.entity.Bid4WinEntity, boolean)
//   */
//  @Override
//  protected boolean sameRelationNoneInternal(CLASS toBeCompared, boolean identical)
//  {
//    return super.sameRelationNoneInternal(toBeCompared, identical) &&
//           this.getUsedNb() == toBeCompared.getUsedNb();
//  }
//  /**
//   * Permet d'effectuer le rendu simple de l'utilisation de crédits courante sans
//   * prise en compte de ses relations
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#renderRelationNone()
//   */
//  @Override
//  protected StringBuffer renderRelationNone()
//  {
//    // Effectue le rendu de base sans lien d'une entité
//    StringBuffer buffer = super.renderRelationNone();
//    // Ajoute les éléments de l'utilisation de crédit
//    buffer.append(" USED_NB=" + this.getUsedNb());
//    // Retourne le rendu
//    return buffer;
//  }
//  /**
//   * Ajoute à la liste des noeuds de relations les utilisations de lots de crédits
//   * desquels sont issus les crédits utilisés
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getFullRelationNodeList()
//   */
//  @Override
//  protected Bid4WinList<Bid4WinRelationNode> getFullRelationNodeList()
//  {
//    Bid4WinList<Bid4WinRelationNode> nodeList = super.getFullRelationNodeList();
//    nodeList.add(CreditUsage_Relations.NODE_BUNDLE_USAGE_MAP);
//    return nodeList;
//  }
//  /**
//   * Permet de récupérer les utilisations de lots de crédits desquels sont issus
//   * les crédits utilisés si elle correspondent à la relation en argument.
//   * @param relation {@inheritDoc}
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getRelationMap(com.bid4win.commons.persistence.entity.Bid4WinRelation)
//   */
//  @Override
//  protected Map<?, ? extends Bid4WinEntity<?, ?>> getRelationMap(Bid4WinRelation relation)
//  {
//    if(relation.equals(CreditUsage_Relations.RELATION_BUNDLE_USAGE_MAP))
//    {
//      return this.getBundleUsageMapInternal();
//    }
//    return super.getRelationMap(relation);
//  }
//
//  /**
//   * Getter de la liste des utilisations de lots de crédits desquels sont issus
//   * les crédits utilisés
//   * @return La liste des utilisations de lots de crédits desquels sont issus les
//   * crédits utilisés
//   */
//  public Bid4WinList<BUNDLE_USAGE> getBundleUsageList()
//  {
//    return new Bid4WinList<BUNDLE_USAGE>(this.getBundleUsageMap().values());
//  }
//  /**
//   * Getter de l'utilisation du lot de crédits dont l'identifiant est en argument
//   * @param id Identifiant du lot de crédits dont il faut rechercher l'utilisation
//   * @return L'utilisation du lot de crédits dont l'identifiant est en argument
//   */
//  protected BUNDLE_USAGE getBundleUsage(long id)
//  {
//    return this.getBundleUsageMap().get(id);
//  }
//  /**
//   * Cette méthode permet d'ajouter à l'utilisation de crédits courante des crédits
//   * des lots en argument
//   * @param bundleUsageMap Map des lots de provenance des crédits à ajouter aux
//   * crédits utilisés avec le nombre associé
//   * @return La liste d'utilisations de tous le crédits des lots en argument
//   * @throws ProtectionException Si l'utilisation de crédits courante ou une de
//   * ses utilisations de lot de crédits est protégée
//   * @throws ModelArgumentException Si l'un des lots de crédits ou son identifiant
//   * est nul ou si un des nombres de crédits utilisés associé est inférieur à un
//   */
//  public Bid4WinList<BUNDLE_USAGE> addBundleUsage(Bid4WinMap<CreditBundle, Integer> bundleUsageMap)
//         throws ProtectionException, ModelArgumentException
//  {
//    Bid4WinMap<Long, BUNDLE_USAGE> resultMap = new Bid4WinMap<Long, BUNDLE_USAGE>();
//    for(Entry<CreditBundle, Integer> entry : bundleUsageMap.entrySet())
//    {
//      BUNDLE_USAGE bundleUsage = this.addBundleUsage(entry.getKey(), entry.getValue());
//      resultMap.put(bundleUsage.getBundleId(), bundleUsage);
//    }
//    return new Bid4WinList<BUNDLE_USAGE>(resultMap.values());
//  }
//  /**
//   * Cette méthode permet d'ajouter à l'utilisation de crédits courante des crédits
//   * du lot en argument
//   * @param creditBundle Lot de provenance des crédits à ajouter aux crédits utilisés
//   * @param creditNb Nombre de crédit à ajouter
//   * @return L'utilisation de tous le crédits du lot en argument
//   * @throws ProtectionException Si l'utilisation de crédits courante ou une de
//   * ses utilisations de lot de crédits est protégée
//   * @throws ModelArgumentException Si le lot de crédits ou son identifiant est
//   * nul ou si son compte utilisateur ne correspond pas au compte utilisateur de
//   * l'utilisation courante ou si le nombre de crédits utilisés est inférieur à un
//   */
//  public BUNDLE_USAGE addBundleUsage(CreditBundle creditBundle, int creditNb)
//         throws ProtectionException, ModelArgumentException
//  {
//    // Vérifie la provenance des crédits utilisés
//    UtilObject.checkEquals("accountId",
//                           UtilObject.checkNotNull("creditBundle",
//                                                   creditBundle).getAccount().getId(),
//                           this.getAccount().getId());
//    // Ajoute et retourne l'utilisation des crédits du lot en argument
//    return this.addBundleUsage(creditBundle.getId(), creditNb);
//  }
//  /**
//   * Cette méthode permet d'ajouter à l'utilisation de crédits courante des crédits
//   * du lot dont l'identifiant est en argument
//   * @param bundleId Identifiant du lot de provenance des crédits à ajouter aux
//   * crédits utilisés
//   * @param creditNb Nombre de crédit à ajouter
//   * @return L'utilisation de tous le crédits du lot en argument
//   * @throws ProtectionException Si l'utilisation de crédits courante ou une de
//   * ses utilisations de lot de crédits est protégée
//   * @throws ModelArgumentException Si l'identifiant du lot de crédits en argument
//   * est nul ou si le nombre de crédits utilisés est inférieur à un
//   */
//  protected BUNDLE_USAGE addBundleUsage(Long bundleId, int creditNb)
//            throws ProtectionException, ModelArgumentException
//  {
//    // Vérifie la protection de l'utilisation de crédits courante
//    this.checkProtection();
//    // Recherche une utilisation du même lot déjà référencée
//    BUNDLE_USAGE bundleUsage = this.getBundleUsage(UtilObject.checkNotNull("bundleId",
//                                                                           bundleId));
//    // Augmente l'utilisation du lot déjà référencé
//    if(bundleUsage != null)
//    {
//      bundleUsage.addUsedNb(creditNb);
//    }
//    // Ajoute une utilisation du lot en argument
//    else
//    {
//      bundleUsage = this.createBundleUsage(bundleId, creditNb);
//      this.getBundleUsageMap().put(bundleId, bundleUsage);
//    }
//    // Augmente le nombre de crédits utilisés
//    this.setUsedNb(this.getUsedNb() + creditNb);
//    // Retourne l'utilisation des crédits du lot en argument
//    return bundleUsage;
//  }
//  /**
//   * Cette méthode doit permettre de créer l'utilisation d'un certain nombre de
//   * crédits du lot en argument
//   * @param bundleId Identifiant du lot duquel sont issus les crédits utilisés
//   * @param nb Nombre de crédits utilisés
//   * @return L'utilisation d'un certain nombre de crédits du lot en argument
//   * @throws ModelArgumentException Si le lot de crédits ou son identifiant est
//   * nul ou si le nombre de crédits utilisés est inférieur à un
//   */
//  protected abstract BUNDLE_USAGE createBundleUsage(Long bundleId, int nb)
//            throws ModelArgumentException;
//  /**
//   * Getter de la map des utilisations de lots de crédits desquels sont issus les
//   * crédits utilisés
//   * @return La map des utilisations de lots de crédits desquels sont issus les
//   * crédits utilisés
//   */
//  private Bid4WinMap<Long, BUNDLE_USAGE> getBundleUsageMap()
//  {
//    return this.bundleUsageMap;
//  }
//  /**
//   * Setter de la map des utilisations de lots de crédits desquels sont issus les
//   * crédits utilisés
//   * @param bundleUsageMap Map des utilisations de lots de crédits desquels sont
//   * issus les crédits utilisés à positionner
//   */
//  private void setBundleUsageMap(Bid4WinMap<Long, BUNDLE_USAGE> bundleUsageMap)
//  {
//    this.bundleUsageMap = bundleUsageMap;
//  }
//
//  /** #################################################################### **/
//  /** ########################### PERSISTENCE ############################ **/
//  /** #################################################################### **/
//  /**
//   * Getter de la map interne des utilisations de lots de crédits desquels sont
//   * issus les crédits utilisés
//   * @return La map interne des utilisations de lots de crédits desquels sont issus
//   * les crédits utilisés
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @OneToMany(mappedBy = "creditUsage", fetch = FetchType.LAZY,
//             cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
//  @MapKey(name = "bundleId")
//  @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
//  private Map<Long, BUNDLE_USAGE> getBundleUsageMapInternal()
//  {
//    return this.getBundleUsageMap().getInternal();
//  }
//  /**
//   * Setter de la map interne des utilisations de lots de crédits desquels sont
//   * issus les crédits utilisés
//   * @param internalBundleUsageMap Map interne des utilisations de lots de crédits
//   * desquels sont issus les crédits utilisés à positionner
//   */
//  @SuppressWarnings(value = "unused")
//  private void setBundleUsageMapInternal(Bid4WinMap<Long, BUNDLE_USAGE> internalBundleUsageMap)
//  {
//    this.setBundleUsageMap(new Bid4WinMap<Long, BUNDLE_USAGE>(internalBundleUsageMap, true));
//  }
//
//  /**
//   * Getter du nombre de crédits utilisés
//   * @return Le nombre de crédits utilisés
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @Column(name = "USED_NB", length = 5, nullable = false, unique = false)
//  public int getUsedNb()
//  {
//    return this.usedNb;
//  }
//  /**
//   * Setter du nombre de crédits utilisés
//   * @param usedNb Nombre de crédits utilisés à positionner
//   */
//  private void setUsedNb(int usedNb)
//  {
//    this.usedNb = usedNb;
//  }
//}
