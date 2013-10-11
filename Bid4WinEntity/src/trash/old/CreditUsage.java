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
// * Cette classe d�fini l'utilisation d'un certain nombre de cr�dits<BR>
// * <BR>
// * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
// * @param <BUNDLE_USAGE> Doit d�finir le type d'utilisation de lot de cr�dits<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
////Annotation pour la persistence
//@MappedSuperclass
//@Access(AccessType.FIELD)
//public abstract class CreditUsage<CLASS extends CreditUsage<CLASS, BUNDLE_USAGE>,
//                                  BUNDLE_USAGE extends CreditBundleUsage<BUNDLE_USAGE, CLASS>>
//       extends AccountBasedEntityMultipleAutoID<CLASS, Account>
//{
//  /** Map des utilisations de lots de cr�dits desquels sont issus les cr�dits utilis�s */
//  @Transient
//  private Bid4WinMap<Long, BUNDLE_USAGE> bundleUsageMap = new Bid4WinMap<Long, BUNDLE_USAGE>();
//  /** Nombre de cr�dits utilis�s */
//  @Transient
//  private int usedNb = 0;
//
//  /**
//   * Constructeur pour cr�ation par introspection
//   */
//  protected CreditUsage()
//  {
//    super();
//  }
//  /**
//   * Constructeur complet
//   * @param account Compte utilisateur des cr�dits utilis�s
//   * @throws ProtectionException Si le compte utilisateur en argument est prot�g�
//   * @throws ModelArgumentException Si le compte utilisateur en argument est nul
//   */
//  public CreditUsage(Account account) throws ProtectionException, ModelArgumentException
//  {
//    super(account);
//  }
//
//  /**
//   * Red�fini l'�quivalence interne de deux utilisations de cr�dits sans prise en
//   * compte de leurs relations afin d'y ajouter le test de leurs donn�es propres
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
//   * Permet d'effectuer le rendu simple de l'utilisation de cr�dits courante sans
//   * prise en compte de ses relations
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#renderRelationNone()
//   */
//  @Override
//  protected StringBuffer renderRelationNone()
//  {
//    // Effectue le rendu de base sans lien d'une entit�
//    StringBuffer buffer = super.renderRelationNone();
//    // Ajoute les �l�ments de l'utilisation de cr�dit
//    buffer.append(" USED_NB=" + this.getUsedNb());
//    // Retourne le rendu
//    return buffer;
//  }
//  /**
//   * Ajoute � la liste des noeuds de relations les utilisations de lots de cr�dits
//   * desquels sont issus les cr�dits utilis�s
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
//   * Permet de r�cup�rer les utilisations de lots de cr�dits desquels sont issus
//   * les cr�dits utilis�s si elle correspondent � la relation en argument.
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
//   * Getter de la liste des utilisations de lots de cr�dits desquels sont issus
//   * les cr�dits utilis�s
//   * @return La liste des utilisations de lots de cr�dits desquels sont issus les
//   * cr�dits utilis�s
//   */
//  public Bid4WinList<BUNDLE_USAGE> getBundleUsageList()
//  {
//    return new Bid4WinList<BUNDLE_USAGE>(this.getBundleUsageMap().values());
//  }
//  /**
//   * Getter de l'utilisation du lot de cr�dits dont l'identifiant est en argument
//   * @param id Identifiant du lot de cr�dits dont il faut rechercher l'utilisation
//   * @return L'utilisation du lot de cr�dits dont l'identifiant est en argument
//   */
//  protected BUNDLE_USAGE getBundleUsage(long id)
//  {
//    return this.getBundleUsageMap().get(id);
//  }
//  /**
//   * Cette m�thode permet d'ajouter � l'utilisation de cr�dits courante des cr�dits
//   * des lots en argument
//   * @param bundleUsageMap Map des lots de provenance des cr�dits � ajouter aux
//   * cr�dits utilis�s avec le nombre associ�
//   * @return La liste d'utilisations de tous le cr�dits des lots en argument
//   * @throws ProtectionException Si l'utilisation de cr�dits courante ou une de
//   * ses utilisations de lot de cr�dits est prot�g�e
//   * @throws ModelArgumentException Si l'un des lots de cr�dits ou son identifiant
//   * est nul ou si un des nombres de cr�dits utilis�s associ� est inf�rieur � un
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
//   * Cette m�thode permet d'ajouter � l'utilisation de cr�dits courante des cr�dits
//   * du lot en argument
//   * @param creditBundle Lot de provenance des cr�dits � ajouter aux cr�dits utilis�s
//   * @param creditNb Nombre de cr�dit � ajouter
//   * @return L'utilisation de tous le cr�dits du lot en argument
//   * @throws ProtectionException Si l'utilisation de cr�dits courante ou une de
//   * ses utilisations de lot de cr�dits est prot�g�e
//   * @throws ModelArgumentException Si le lot de cr�dits ou son identifiant est
//   * nul ou si son compte utilisateur ne correspond pas au compte utilisateur de
//   * l'utilisation courante ou si le nombre de cr�dits utilis�s est inf�rieur � un
//   */
//  public BUNDLE_USAGE addBundleUsage(CreditBundle creditBundle, int creditNb)
//         throws ProtectionException, ModelArgumentException
//  {
//    // V�rifie la provenance des cr�dits utilis�s
//    UtilObject.checkEquals("accountId",
//                           UtilObject.checkNotNull("creditBundle",
//                                                   creditBundle).getAccount().getId(),
//                           this.getAccount().getId());
//    // Ajoute et retourne l'utilisation des cr�dits du lot en argument
//    return this.addBundleUsage(creditBundle.getId(), creditNb);
//  }
//  /**
//   * Cette m�thode permet d'ajouter � l'utilisation de cr�dits courante des cr�dits
//   * du lot dont l'identifiant est en argument
//   * @param bundleId Identifiant du lot de provenance des cr�dits � ajouter aux
//   * cr�dits utilis�s
//   * @param creditNb Nombre de cr�dit � ajouter
//   * @return L'utilisation de tous le cr�dits du lot en argument
//   * @throws ProtectionException Si l'utilisation de cr�dits courante ou une de
//   * ses utilisations de lot de cr�dits est prot�g�e
//   * @throws ModelArgumentException Si l'identifiant du lot de cr�dits en argument
//   * est nul ou si le nombre de cr�dits utilis�s est inf�rieur � un
//   */
//  protected BUNDLE_USAGE addBundleUsage(Long bundleId, int creditNb)
//            throws ProtectionException, ModelArgumentException
//  {
//    // V�rifie la protection de l'utilisation de cr�dits courante
//    this.checkProtection();
//    // Recherche une utilisation du m�me lot d�j� r�f�renc�e
//    BUNDLE_USAGE bundleUsage = this.getBundleUsage(UtilObject.checkNotNull("bundleId",
//                                                                           bundleId));
//    // Augmente l'utilisation du lot d�j� r�f�renc�
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
//    // Augmente le nombre de cr�dits utilis�s
//    this.setUsedNb(this.getUsedNb() + creditNb);
//    // Retourne l'utilisation des cr�dits du lot en argument
//    return bundleUsage;
//  }
//  /**
//   * Cette m�thode doit permettre de cr�er l'utilisation d'un certain nombre de
//   * cr�dits du lot en argument
//   * @param bundleId Identifiant du lot duquel sont issus les cr�dits utilis�s
//   * @param nb Nombre de cr�dits utilis�s
//   * @return L'utilisation d'un certain nombre de cr�dits du lot en argument
//   * @throws ModelArgumentException Si le lot de cr�dits ou son identifiant est
//   * nul ou si le nombre de cr�dits utilis�s est inf�rieur � un
//   */
//  protected abstract BUNDLE_USAGE createBundleUsage(Long bundleId, int nb)
//            throws ModelArgumentException;
//  /**
//   * Getter de la map des utilisations de lots de cr�dits desquels sont issus les
//   * cr�dits utilis�s
//   * @return La map des utilisations de lots de cr�dits desquels sont issus les
//   * cr�dits utilis�s
//   */
//  private Bid4WinMap<Long, BUNDLE_USAGE> getBundleUsageMap()
//  {
//    return this.bundleUsageMap;
//  }
//  /**
//   * Setter de la map des utilisations de lots de cr�dits desquels sont issus les
//   * cr�dits utilis�s
//   * @param bundleUsageMap Map des utilisations de lots de cr�dits desquels sont
//   * issus les cr�dits utilis�s � positionner
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
//   * Getter de la map interne des utilisations de lots de cr�dits desquels sont
//   * issus les cr�dits utilis�s
//   * @return La map interne des utilisations de lots de cr�dits desquels sont issus
//   * les cr�dits utilis�s
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
//   * Setter de la map interne des utilisations de lots de cr�dits desquels sont
//   * issus les cr�dits utilis�s
//   * @param internalBundleUsageMap Map interne des utilisations de lots de cr�dits
//   * desquels sont issus les cr�dits utilis�s � positionner
//   */
//  @SuppressWarnings(value = "unused")
//  private void setBundleUsageMapInternal(Bid4WinMap<Long, BUNDLE_USAGE> internalBundleUsageMap)
//  {
//    this.setBundleUsageMap(new Bid4WinMap<Long, BUNDLE_USAGE>(internalBundleUsageMap, true));
//  }
//
//  /**
//   * Getter du nombre de cr�dits utilis�s
//   * @return Le nombre de cr�dits utilis�s
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @Column(name = "USED_NB", length = 5, nullable = false, unique = false)
//  public int getUsedNb()
//  {
//    return this.usedNb;
//  }
//  /**
//   * Setter du nombre de cr�dits utilis�s
//   * @param usedNb Nombre de cr�dits utilis�s � positionner
//   */
//  private void setUsedNb(int usedNb)
//  {
//    this.usedNb = usedNb;
//  }
//}
