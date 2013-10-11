package trash.old;
//package com.bid4win.persistence.entity.account;
//
//import javax.persistence.Access;
//import javax.persistence.AccessType;
//import javax.persistence.Column;
//import javax.persistence.FetchType;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.MappedSuperclass;
//import javax.persistence.Transient;
//
//import com.bid4win.commons.core.UtilNumber;
//import com.bid4win.commons.core.UtilObject;
//import com.bid4win.commons.core.collection.Bid4WinList;
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.commons.core.security.exception.ProtectionException;
//import com.bid4win.commons.persistence.entity.Bid4WinEntity;
//import com.bid4win.commons.persistence.entity.Bid4WinEntityAutoID;
//import com.bid4win.commons.persistence.entity.Bid4WinRelation;
//import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
//
///**
// * Cette classe défini l'utilisation d'un certain nombre de crédits d'un lot<BR>
// * <BR>
// * @param <CLASS> Doit définir la classe réellement instanciée<BR>
// * @param <CREDIT_USAGE> Doit définir le type d'utilisation des crédits du lot référencé<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
////Annotation pour la persistence
//@MappedSuperclass
//@Access(AccessType.FIELD)
//public class CreditBundleUsage<CLASS extends CreditBundleUsage<CLASS, CREDIT_USAGE>,
//                               CREDIT_USAGE extends CreditUsage<CREDIT_USAGE, CLASS>>
//       extends Bid4WinEntityAutoID<CLASS>
//{
//  /** Identifiant du lot duquel sont issus les crédits utilisés */
//  @Transient
//  private Long bundleId;
//  /** Utilisation des crédits du lot référencé */
//  @Transient
//  private CREDIT_USAGE creditUsage = null;
//  /** Nombre de crédits utilisés */
//  @Transient
//  private int usedNb = 0;
//
//  /**
//   * Constructeur pour création par introspection
//   */
//  protected CreditBundleUsage()
//  {
//    super();
//  }
//  /**
//   * Constructeur complet
//   * @param bundleId Identifiant du lot duquel sont issus les crédits utilisés
//   * @param creditUsage Utilisation des crédits du lot référencé
//   * @param usedNb Nombre de crédits utilisés
//   * @throws ModelArgumentException Si l'identifiant du lot de crédits ou leur
//   * utilisation est nul ou si le nombre de crédits utilisés est inférieur à un
//   */
//  protected CreditBundleUsage(Long bundleId, CREDIT_USAGE creditUsage, int usedNb)
//            throws ModelArgumentException
//  {
//    super();
//    // Défini l'identifiant du lot duquel sont issus les crédits utilisés
//    this.defineBundleId(bundleId);
//    // Défini l'utilisation des crédits du lot référencé
//    this.defineCreditUsage(creditUsage);
//    // Ajoute le nombre crédits utilisés
//    this.addUsedNb(usedNb);
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
//           this.getBundleId() == toBeCompared.getBundleId() &&
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
//    buffer.append(" BUNDLE_ID=" + this.getBundleId());
//    buffer.append(" USED_NB=" + this.getUsedNb());
//    // Retourne le rendu
//    return buffer;
//  }
//  /**
//   * Ajoute à la liste des noeuds de relations de l'utilisation de crédits d'un
//   * lot le lien vers le lot duquel sont issus les crédits utilisés ainsi que le
//   * lien vers leur utilisation
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getFullRelationNodeList()
//   */
//  @Override
//  protected Bid4WinList<Bid4WinRelationNode> getFullRelationNodeList()
//  {
//    Bid4WinList<Bid4WinRelationNode> nodeList = super.getFullRelationNodeList();
//    nodeList.add(CreditBundleUsage_Relations.NODE_CREDIT_USAGE);
//    return nodeList;
//  }
//  /**
//   * Permet de récupérer le lot duquel sont issus les crédits utilisés ou leur
//   * utilisation s'il correspond à la relation en argument.
//   * @param relation {@inheritDoc}
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getRelationSimple(com.bid4win.commons.persistence.entity.Bid4WinRelation)
//   */
//  @Override
//  protected Bid4WinEntity<?, ?> getRelationSimple(Bid4WinRelation relation)
//  {
//    if(relation.equals(CreditBundleUsage_Relations.RELATION_CREDIT_USAGE))
//    {
//      return this.getCreditUsage();
//    }
//    return super.getRelationSimple(relation);
//  }
//
//  /**
//   * Cette méthode permet de définir l'identifiant du lot duquel sont issus les
//   * crédits utilisés
//   * @param bundleId Définition de l'identifiant du lot duquel sont issus les
//   * crédits utilisés
//   * @throws ProtectionException Si l'utilisation de lot de crédits courante est
//   * protégée
//   * @throws ModelArgumentException Si on défini un identifiant nul
//   */
//  private void defineBundleId(Long bundleId) throws ProtectionException, ModelArgumentException
//  {
//    // Vérifie la protection de l'utilisation de crédits courante
//    this.checkProtection();
//    this.setBundleId(UtilObject.checkNotNull("bundleId", bundleId));
//  }
//  /**
//   * Cette méthode permet de définir l'utilisation des crédits du lot référencé
//   * @param creditUsage Définition de l'tilisation des crédits du lot référencé
//   * @throws ProtectionException Si l'utilisation de lot de crédits courante est
//   * protégée
//   * @throws ModelArgumentException Si l'utilisation de crédits en argument est
//   * nulle
//   */
//  private void defineCreditUsage(CREDIT_USAGE creditUsage)
//          throws ProtectionException, ModelArgumentException
//  {
//    // Vérifie la protection de l'utilisation de lot de crédits courante
//    this.checkProtection();
//    // Vérifie que l'utilisation de crédits en argument n'est pas nulle
//    UtilObject.checkNotNull("creditUsage", creditUsage);
//    // Crée le lien de l'utilisation de lot de crédits vers son utilisation
//    this.setCreditUsage(creditUsage);
//  }
//  /**
//   * Cette méthode permet d'augmenter le nombre de crédits utilisés
//   * @param usedNb Nombre de crédits utilisés supplémentaire
//   * @throws ProtectionException Si l'utilisation de lot de crédits courante est
//   * protégée
//   * @throws ModelArgumentException Si on ajoute moins d'un crédit
//   */
//  protected void addUsedNb(int usedNb) throws ProtectionException, ModelArgumentException
//  {
//    // Vérifie la protection de l'utilisation de crédits courante
//    this.checkProtection();
//    this.setUsedNb(this.getUsedNb() + UtilNumber.checkMinValue("usedNb", usedNb, 1, true));
//  }
//
//  /** #################################################################### **/
//  /** ########################### PERSISTENCE ############################ **/
//  /** #################################################################### **/
//  /**
//   * Getter de l'identifiant du lot duquel sont issus les crédits utilisés
//   * @return L'identifiant du lot duquel sont issus les crédits utilisés
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @Column(name = "BUNDLE_ID", length = 10, nullable = false, unique = false)
//  public Long getBundleId()
//  {
//    return this.bundleId;
//  }
//  /**
//   * Setter de l'identifiant du lot duquel sont issus les crédits utilisés
//   * @param bundleId Identifiant du lot duquel sont issus les crédits utilisés à
//   * positionner
//   */
//  private void setBundleId(Long bundleId)
//  {
//    this.bundleId = bundleId;
//  }
//
//  /**
//   * Getter de l'utilisation des crédits du lot référencé
//   * @return L'utilisation des crédits du lot référencé
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @ManyToOne(fetch = FetchType.LAZY, cascade = {})
//  @JoinColumn(name = "CREDIT_USAGE_ID", nullable = false, unique = false)
//  public CREDIT_USAGE getCreditUsage()
//  {
//    return this.creditUsage;
//  }
//  /**
//   * Setter de l'utilisation des crédits du lot référencé
//   * @param creditUsage Utilisation des crédits du lot référencé à positionner
//   */
//  private void setCreditUsage(CREDIT_USAGE creditUsage)
//  {
//    this.creditUsage = creditUsage;
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
