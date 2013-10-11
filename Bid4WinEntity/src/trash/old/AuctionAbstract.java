package trash.old;
//package com.bid4win.persistence.entity.auction.old;
//
//import javax.persistence.Access;
//import javax.persistence.AccessType;
//import javax.persistence.Column;
//import javax.persistence.Embedded;
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
//import com.bid4win.commons.core.security.IdPattern;
//import com.bid4win.commons.core.security.exception.ProtectionException;
//import com.bid4win.commons.persistence.entity.Bid4WinEntity;
//import com.bid4win.commons.persistence.entity.Bid4WinEntityGeneratedID;
//import com.bid4win.commons.persistence.entity.Bid4WinRelation;
//import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
//import com.bid4win.persistence.entity.auction.AuctionAbstract_Relations;
//import com.bid4win.persistence.entity.product.Product;
//
///**
// * Cette classe d�fini l'abstraction d'une vente aux ench�res<BR>
// * <BR>
// * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
// * @param <BID> D�finition des ench�res associ�es � la vente<BR>
// * @param <SCHEDULE> D�finition les �l�ments de planification de base de la vente
// * aux ench�res<BR>
// * @param <TERMS> D�finition des conditions de base de la vente aux ench�res<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
////Annotation pour la persistence
//@MappedSuperclass
//@Access(AccessType.FIELD)
//public abstract class AuctionAbstract<CLASS extends AuctionAbstract<CLASS, BID, SCHEDULE, TERMS>,
//                                      BID extends BidAbstract<BID, CLASS>,
//                                      SCHEDULE extends ScheduleAbstract<SCHEDULE>,
//                                      TERMS extends TermsAbstract<TERMS>>
//       extends Bid4WinEntityGeneratedID<CLASS>
//{
//  // TODO schedule avec opening date par d�faut +
//  // prebookingDate pour les prebooked
//  // et closing date pour les scratch
//  // Terms avec creditNbPerAction +
//  // prebookNb et bidNb pour prebooked par exemple
//
//
//  /** Produit vendu aux ench�res */
//  @Transient
//  private Product product = null;
//  /** �l�ments de planification de la vente aux ench�res */
//  @Transient
//  private SCHEDULE schedule = null;
//  /** Conditions de la vente aux ench�res */
//  private TERMS terms = null;
//  /** Nombre d'ench�res plac�es sur la vente */
//  @Transient
//  private int bidNb = 0;
//
//  /**
//   * Constructeur pour cr�ation par introspection
//   */
//  protected AuctionAbstract()
//  {
//    super();
//  }
//  /**
//   * Constructeur complet de la vente aux ench�res
//   * @param product Produit vendu aux ench�res
//   * @param schedule �l�ments de planification de base de la vente aux ench�res
//   * @param terms Conditions de base de la vente aux ench�res
//   * @throws ModelArgumentException Si le produit, les �l�ments de planification
//   * ou les conditions en argument sont nuls
//   */
//  protected AuctionAbstract(Product product, SCHEDULE schedule, TERMS terms)
//            throws ModelArgumentException
//  {
//    super((IdPattern)null);
//    this.defineProduct(product);
//    this.defineSchedule(schedule);
//    this.defineTerms(terms);
//  }
//  /**
//   * Constructeur complet de la vente aux ench�res avec pr�cision de l'identifiant
//   * @param id Identifiant de la vente aux ench�res
//   * @param product Produit vendu aux ench�res
//   * @param schedule �l�ments de planification de base de la vente aux ench�res
//   * @param terms Conditions de base de la vente aux ench�res
//   * @throws ModelArgumentException Si l'identifiant, le produit, les �l�ments de
//   * planification ou les conditions en argument sont nuls nul
//   */
//  protected AuctionAbstract(String id, Product product, SCHEDULE schedule, TERMS terms)
//            throws ModelArgumentException
//  {
//    super(id);
//    this.defineProduct(product);
//    this.defineSchedule(schedule);
//    this.defineTerms(terms);
//  }
//
//  /**
//   * Red�fini l'�quivalence interne de deux ventes aux ench�res sans prise en compte
//   * de leurs relations afin d'y ajouter le test de leurs donn�es propres
//   * @param toBeCompared {@inheritDoc}
//   * @param identical {@inheritDoc}
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#sameRelationNoneInternal(com.bid4win.commons.persistence.entity.Bid4WinEntity, boolean)
//   */
//  @Override
//  protected boolean sameRelationNoneInternal(CLASS toBeCompared, boolean identical)
//  {
//    return super.sameRelationNoneInternal(toBeCompared, identical) &&
//           this.getBidNb() == toBeCompared.getBidNb() &&
//           this.getSchedule().equals(toBeCompared.getSchedule()) &&
//           this.getTerms().equals(toBeCompared.getTerms());
//  }
//  /**
//   * Permet d'effectuer le rendu simple de la vente aux ench�res courante sans
//   * prise en compte de ses relations
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#renderRelationNone()
//   */
//  @Override
//  protected StringBuffer renderRelationNone()
//  {
//    // Effectue le rendu de base sans lien d'une entit�
//    StringBuffer buffer = super.renderRelationNone();
//    // Ajoute les �l�ments de la vente aux ench�res
//    buffer.append(" BID_NB=" + this.getBidNb());
//    buffer.append(" " + this.getSchedule().render());
//    buffer.append(" " + this.getTerms().render());
//    // Retourne le rendu
//    return buffer;
//  }
//  /**
//   * Ajoute � la liste des noeuds de relations de la vente aux ench�res le lien
//   * vers son produit
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getFullRelationNodeList()
//   */
//  @Override
//  protected Bid4WinList<Bid4WinRelationNode> getFullRelationNodeList()
//  {
//    Bid4WinList<Bid4WinRelationNode> nodeList = super.getFullRelationNodeList();
//    nodeList.add(AuctionAbstract_Relations.NODE_PRODUCT);
//    return nodeList;
//  }
//  /**
//   * Permet de r�cup�rer le produit de la vente aux ench�re s'il correspond � la
//   * relation en argument.
//   * @param relation {@inheritDoc}
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getRelationSimple(com.bid4win.commons.persistence.entity.Bid4WinRelation)
//   */
//  @Override
//  protected Bid4WinEntity<?, ?> getRelationSimple(Bid4WinRelation relation)
//  {
//    if(relation.equals(AuctionAbstract_Relations.RELATION_PRODUCT))
//    {
//      return this.getProduct();
//    }
//    return super.getRelationSimple(relation);
//  }
//
//  /**
//   * Cette m�thode permet de d�finir le produit vendu aux ench�res
//   * @param product D�finition du produit vendu aux ench�res
//   * @throws ProtectionException Si l'ench�re courante est prot�g�e
//   * @throws ModelArgumentException Si on d�fini un produit nul
//   */
//  private void defineProduct(Product product) throws ProtectionException, ModelArgumentException
//  {
//    // V�rifie la protection de la vente aux ench�res courante
//    this.checkProtection();
//    this.setProduct(UtilObject.checkNotNull("product", product));
//  }
//  /**
//   * Cette m�thode permet de d�finir le nombre d'ench�res plac�es sur la vente
//   * @param bidNb D�finition du nombre d'ench�res plac�es sur la vente
//   * @throws ProtectionException Si l'ench�re courante est prot�g�e
//   * @throws ModelArgumentException Si on d�fini un nombre inf�rieur � z�ro
//   */
//  protected void defineBidNb(int bidNb) throws ProtectionException, ModelArgumentException
//  {
//    // V�rifie la protection de la vente aux ench�res courante
//    this.checkProtection();
//    this.setBidNb(UtilNumber.checkMinValue("bidNb", bidNb, 0, true));
//  }
//  /**
//   * Cette m�thode permet de d�finir les �l�ments de planification de la vente aux
//   * ench�res
//   * @param schedule D�finition des �l�ments de planification de la vente aux ench�res
//   * @throws ModelArgumentException Si on d�fini des �l�ments de planification nuls
//   */
//  private void defineSchedule(SCHEDULE schedule) throws ProtectionException, ModelArgumentException
//  {
//    // V�rifie la protection de la vente aux ench�res courante
//    this.checkProtection();
//    this.setSchedule(UtilObject.checkNotNull("schedule", schedule));
//  }
//  /**
//   * Cette m�thode permet de d�finir les conditions de la vente aux ench�res
//   * @param terms D�finition des conditions de la vente aux ench�res
//   * @throws ModelArgumentException Si on d�fini des conditions nulles
//   */
//  private void defineTerms(TERMS terms) throws ProtectionException, ModelArgumentException
//  {
//    // V�rifie la protection de la vente aux ench�res courante
//    this.checkProtection();
//    this.setTerms(UtilObject.checkNotNull("terms", terms));
//  }
//
//  /** #################################################################### **/
//  /** ########################### PERSISTENCE ############################ **/
//  /** #################################################################### **/
//  /**
//   * Getter du produit vendu aux ench�res
//   * @return Le produit vendu aux ench�res
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @ManyToOne(fetch = FetchType.LAZY, cascade = {})
//  @JoinColumn(name = "PRODUCT_ID", nullable = false, unique = false)
//  public Product getProduct()
//  {
//    return this.product;
//  }
//  /**
//   * Setter du produit de la vente aux ench�res
//   * @param product Produit de la vente aux ench�res � positionner
//   */
//  private void  setProduct(Product product)
//  {
//    this.product = product;
//  }
//
//  /**
//   * Getter du nombre d'ench�res plac�es sur la vente
//   * @return Le nombre d'ench�res plac�es sur la vente
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @Column(name = "BID_NB", length = 5, nullable = false, unique = false)
//  public int getBidNb()
//  {
//    return this.bidNb;
//  }
//  /**
//   * Setter du nombre d'ench�res plac�es sur la vente
//   * @param bidNb Nombre d'ench�res plac�es sur la vente � positionner
//   */
//  private void setBidNb(int bidNb)
//  {
//    this.bidNb = bidNb;
//  }
//
//  /**
//   * Getter des �l�ments de planification de la vente aux ench�res
//   * @return Les �l�ments de planification de la vente aux ench�res
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @Embedded()
//  public SCHEDULE getSchedule()
//  {
//    return this.schedule;
//  }
//  /**
//   * Setter des �l�ments de planification de la vente aux ench�res
//   * @param schedule �l�ments de planification de la vente aux ench�res � positionner
//   */
//  private void setSchedule(SCHEDULE schedule)
//  {
//    this.schedule = schedule;
//  }
//
//  /**
//   * Getter des conditions de la vente aux ench�res
//   * @return Les conditions de la vente aux ench�res
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @Embedded()
//  public TERMS getTerms()
//  {
//    return this.terms;
//  }
//  /**
//   * Setter des conditions de la vente aux ench�res
//   * @param terms Conditions de la vente aux ench�res � positionner
//   */
//  private void setTerms(TERMS terms)
//  {
//    this.terms = terms;
//  }
//}