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
// * Cette classe défini l'abstraction d'une vente aux enchères<BR>
// * <BR>
// * @param <CLASS> Doit définir la classe réellement instanciée<BR>
// * @param <BID> Définition des enchères associées à la vente<BR>
// * @param <SCHEDULE> Définition les éléments de planification de base de la vente
// * aux enchères<BR>
// * @param <TERMS> Définition des conditions de base de la vente aux enchères<BR>
// * <BR>
// * @author Emeric Fillâtre
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
//  // TODO schedule avec opening date par défaut +
//  // prebookingDate pour les prebooked
//  // et closing date pour les scratch
//  // Terms avec creditNbPerAction +
//  // prebookNb et bidNb pour prebooked par exemple
//
//
//  /** Produit vendu aux enchères */
//  @Transient
//  private Product product = null;
//  /** Éléments de planification de la vente aux enchères */
//  @Transient
//  private SCHEDULE schedule = null;
//  /** Conditions de la vente aux enchères */
//  private TERMS terms = null;
//  /** Nombre d'enchères placées sur la vente */
//  @Transient
//  private int bidNb = 0;
//
//  /**
//   * Constructeur pour création par introspection
//   */
//  protected AuctionAbstract()
//  {
//    super();
//  }
//  /**
//   * Constructeur complet de la vente aux enchères
//   * @param product Produit vendu aux enchères
//   * @param schedule Éléments de planification de base de la vente aux enchères
//   * @param terms Conditions de base de la vente aux enchères
//   * @throws ModelArgumentException Si le produit, les éléments de planification
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
//   * Constructeur complet de la vente aux enchères avec précision de l'identifiant
//   * @param id Identifiant de la vente aux enchères
//   * @param product Produit vendu aux enchères
//   * @param schedule Éléments de planification de base de la vente aux enchères
//   * @param terms Conditions de base de la vente aux enchères
//   * @throws ModelArgumentException Si l'identifiant, le produit, les éléments de
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
//   * Redéfini l'équivalence interne de deux ventes aux enchères sans prise en compte
//   * de leurs relations afin d'y ajouter le test de leurs données propres
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
//   * Permet d'effectuer le rendu simple de la vente aux enchères courante sans
//   * prise en compte de ses relations
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#renderRelationNone()
//   */
//  @Override
//  protected StringBuffer renderRelationNone()
//  {
//    // Effectue le rendu de base sans lien d'une entité
//    StringBuffer buffer = super.renderRelationNone();
//    // Ajoute les éléments de la vente aux enchères
//    buffer.append(" BID_NB=" + this.getBidNb());
//    buffer.append(" " + this.getSchedule().render());
//    buffer.append(" " + this.getTerms().render());
//    // Retourne le rendu
//    return buffer;
//  }
//  /**
//   * Ajoute à la liste des noeuds de relations de la vente aux enchères le lien
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
//   * Permet de récupérer le produit de la vente aux enchère s'il correspond à la
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
//   * Cette méthode permet de définir le produit vendu aux enchères
//   * @param product Définition du produit vendu aux enchères
//   * @throws ProtectionException Si l'enchère courante est protégée
//   * @throws ModelArgumentException Si on défini un produit nul
//   */
//  private void defineProduct(Product product) throws ProtectionException, ModelArgumentException
//  {
//    // Vérifie la protection de la vente aux enchères courante
//    this.checkProtection();
//    this.setProduct(UtilObject.checkNotNull("product", product));
//  }
//  /**
//   * Cette méthode permet de définir le nombre d'enchères placées sur la vente
//   * @param bidNb Définition du nombre d'enchères placées sur la vente
//   * @throws ProtectionException Si l'enchère courante est protégée
//   * @throws ModelArgumentException Si on défini un nombre inférieur à zéro
//   */
//  protected void defineBidNb(int bidNb) throws ProtectionException, ModelArgumentException
//  {
//    // Vérifie la protection de la vente aux enchères courante
//    this.checkProtection();
//    this.setBidNb(UtilNumber.checkMinValue("bidNb", bidNb, 0, true));
//  }
//  /**
//   * Cette méthode permet de définir les éléments de planification de la vente aux
//   * enchères
//   * @param schedule Définition des éléments de planification de la vente aux enchères
//   * @throws ModelArgumentException Si on défini des éléments de planification nuls
//   */
//  private void defineSchedule(SCHEDULE schedule) throws ProtectionException, ModelArgumentException
//  {
//    // Vérifie la protection de la vente aux enchères courante
//    this.checkProtection();
//    this.setSchedule(UtilObject.checkNotNull("schedule", schedule));
//  }
//  /**
//   * Cette méthode permet de définir les conditions de la vente aux enchères
//   * @param terms Définition des conditions de la vente aux enchères
//   * @throws ModelArgumentException Si on défini des conditions nulles
//   */
//  private void defineTerms(TERMS terms) throws ProtectionException, ModelArgumentException
//  {
//    // Vérifie la protection de la vente aux enchères courante
//    this.checkProtection();
//    this.setTerms(UtilObject.checkNotNull("terms", terms));
//  }
//
//  /** #################################################################### **/
//  /** ########################### PERSISTENCE ############################ **/
//  /** #################################################################### **/
//  /**
//   * Getter du produit vendu aux enchères
//   * @return Le produit vendu aux enchères
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
//   * Setter du produit de la vente aux enchères
//   * @param product Produit de la vente aux enchères à positionner
//   */
//  private void  setProduct(Product product)
//  {
//    this.product = product;
//  }
//
//  /**
//   * Getter du nombre d'enchères placées sur la vente
//   * @return Le nombre d'enchères placées sur la vente
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @Column(name = "BID_NB", length = 5, nullable = false, unique = false)
//  public int getBidNb()
//  {
//    return this.bidNb;
//  }
//  /**
//   * Setter du nombre d'enchères placées sur la vente
//   * @param bidNb Nombre d'enchères placées sur la vente à positionner
//   */
//  private void setBidNb(int bidNb)
//  {
//    this.bidNb = bidNb;
//  }
//
//  /**
//   * Getter des éléments de planification de la vente aux enchères
//   * @return Les éléments de planification de la vente aux enchères
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @Embedded()
//  public SCHEDULE getSchedule()
//  {
//    return this.schedule;
//  }
//  /**
//   * Setter des éléments de planification de la vente aux enchères
//   * @param schedule Éléments de planification de la vente aux enchères à positionner
//   */
//  private void setSchedule(SCHEDULE schedule)
//  {
//    this.schedule = schedule;
//  }
//
//  /**
//   * Getter des conditions de la vente aux enchères
//   * @return Les conditions de la vente aux enchères
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @Embedded()
//  public TERMS getTerms()
//  {
//    return this.terms;
//  }
//  /**
//   * Setter des conditions de la vente aux enchères
//   * @param terms Conditions de la vente aux enchères à positionner
//   */
//  private void setTerms(TERMS terms)
//  {
//    this.terms = terms;
//  }
//}