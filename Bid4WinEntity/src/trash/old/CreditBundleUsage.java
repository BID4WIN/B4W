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
// * Cette classe d�fini l'utilisation d'un certain nombre de cr�dits d'un lot<BR>
// * <BR>
// * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
// * @param <CREDIT_USAGE> Doit d�finir le type d'utilisation des cr�dits du lot r�f�renc�<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
////Annotation pour la persistence
//@MappedSuperclass
//@Access(AccessType.FIELD)
//public class CreditBundleUsage<CLASS extends CreditBundleUsage<CLASS, CREDIT_USAGE>,
//                               CREDIT_USAGE extends CreditUsage<CREDIT_USAGE, CLASS>>
//       extends Bid4WinEntityAutoID<CLASS>
//{
//  /** Identifiant du lot duquel sont issus les cr�dits utilis�s */
//  @Transient
//  private Long bundleId;
//  /** Utilisation des cr�dits du lot r�f�renc� */
//  @Transient
//  private CREDIT_USAGE creditUsage = null;
//  /** Nombre de cr�dits utilis�s */
//  @Transient
//  private int usedNb = 0;
//
//  /**
//   * Constructeur pour cr�ation par introspection
//   */
//  protected CreditBundleUsage()
//  {
//    super();
//  }
//  /**
//   * Constructeur complet
//   * @param bundleId Identifiant du lot duquel sont issus les cr�dits utilis�s
//   * @param creditUsage Utilisation des cr�dits du lot r�f�renc�
//   * @param usedNb Nombre de cr�dits utilis�s
//   * @throws ModelArgumentException Si l'identifiant du lot de cr�dits ou leur
//   * utilisation est nul ou si le nombre de cr�dits utilis�s est inf�rieur � un
//   */
//  protected CreditBundleUsage(Long bundleId, CREDIT_USAGE creditUsage, int usedNb)
//            throws ModelArgumentException
//  {
//    super();
//    // D�fini l'identifiant du lot duquel sont issus les cr�dits utilis�s
//    this.defineBundleId(bundleId);
//    // D�fini l'utilisation des cr�dits du lot r�f�renc�
//    this.defineCreditUsage(creditUsage);
//    // Ajoute le nombre cr�dits utilis�s
//    this.addUsedNb(usedNb);
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
//           this.getBundleId() == toBeCompared.getBundleId() &&
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
//    buffer.append(" BUNDLE_ID=" + this.getBundleId());
//    buffer.append(" USED_NB=" + this.getUsedNb());
//    // Retourne le rendu
//    return buffer;
//  }
//  /**
//   * Ajoute � la liste des noeuds de relations de l'utilisation de cr�dits d'un
//   * lot le lien vers le lot duquel sont issus les cr�dits utilis�s ainsi que le
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
//   * Permet de r�cup�rer le lot duquel sont issus les cr�dits utilis�s ou leur
//   * utilisation s'il correspond � la relation en argument.
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
//   * Cette m�thode permet de d�finir l'identifiant du lot duquel sont issus les
//   * cr�dits utilis�s
//   * @param bundleId D�finition de l'identifiant du lot duquel sont issus les
//   * cr�dits utilis�s
//   * @throws ProtectionException Si l'utilisation de lot de cr�dits courante est
//   * prot�g�e
//   * @throws ModelArgumentException Si on d�fini un identifiant nul
//   */
//  private void defineBundleId(Long bundleId) throws ProtectionException, ModelArgumentException
//  {
//    // V�rifie la protection de l'utilisation de cr�dits courante
//    this.checkProtection();
//    this.setBundleId(UtilObject.checkNotNull("bundleId", bundleId));
//  }
//  /**
//   * Cette m�thode permet de d�finir l'utilisation des cr�dits du lot r�f�renc�
//   * @param creditUsage D�finition de l'tilisation des cr�dits du lot r�f�renc�
//   * @throws ProtectionException Si l'utilisation de lot de cr�dits courante est
//   * prot�g�e
//   * @throws ModelArgumentException Si l'utilisation de cr�dits en argument est
//   * nulle
//   */
//  private void defineCreditUsage(CREDIT_USAGE creditUsage)
//          throws ProtectionException, ModelArgumentException
//  {
//    // V�rifie la protection de l'utilisation de lot de cr�dits courante
//    this.checkProtection();
//    // V�rifie que l'utilisation de cr�dits en argument n'est pas nulle
//    UtilObject.checkNotNull("creditUsage", creditUsage);
//    // Cr�e le lien de l'utilisation de lot de cr�dits vers son utilisation
//    this.setCreditUsage(creditUsage);
//  }
//  /**
//   * Cette m�thode permet d'augmenter le nombre de cr�dits utilis�s
//   * @param usedNb Nombre de cr�dits utilis�s suppl�mentaire
//   * @throws ProtectionException Si l'utilisation de lot de cr�dits courante est
//   * prot�g�e
//   * @throws ModelArgumentException Si on ajoute moins d'un cr�dit
//   */
//  protected void addUsedNb(int usedNb) throws ProtectionException, ModelArgumentException
//  {
//    // V�rifie la protection de l'utilisation de cr�dits courante
//    this.checkProtection();
//    this.setUsedNb(this.getUsedNb() + UtilNumber.checkMinValue("usedNb", usedNb, 1, true));
//  }
//
//  /** #################################################################### **/
//  /** ########################### PERSISTENCE ############################ **/
//  /** #################################################################### **/
//  /**
//   * Getter de l'identifiant du lot duquel sont issus les cr�dits utilis�s
//   * @return L'identifiant du lot duquel sont issus les cr�dits utilis�s
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @Column(name = "BUNDLE_ID", length = 10, nullable = false, unique = false)
//  public Long getBundleId()
//  {
//    return this.bundleId;
//  }
//  /**
//   * Setter de l'identifiant du lot duquel sont issus les cr�dits utilis�s
//   * @param bundleId Identifiant du lot duquel sont issus les cr�dits utilis�s �
//   * positionner
//   */
//  private void setBundleId(Long bundleId)
//  {
//    this.bundleId = bundleId;
//  }
//
//  /**
//   * Getter de l'utilisation des cr�dits du lot r�f�renc�
//   * @return L'utilisation des cr�dits du lot r�f�renc�
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
//   * Setter de l'utilisation des cr�dits du lot r�f�renc�
//   * @param creditUsage Utilisation des cr�dits du lot r�f�renc� � positionner
//   */
//  private void setCreditUsage(CREDIT_USAGE creditUsage)
//  {
//    this.creditUsage = creditUsage;
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
