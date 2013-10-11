//package trash.working;
//
//import java.util.Map;
//
//import javax.persistence.Access;
//import javax.persistence.AccessType;
//import javax.persistence.AttributeOverride;
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.FetchType;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.MapKey;
//import javax.persistence.MappedSuperclass;
//import javax.persistence.OneToMany;
//import javax.persistence.Transient;
//
//import org.hibernate.annotations.Cache;
//import org.hibernate.annotations.CacheConcurrencyStrategy;
//
//import com.bid4win.commons.core.UtilNumber;
//import com.bid4win.commons.core.UtilObject;
//import com.bid4win.commons.core.collection.Bid4WinList;
//import com.bid4win.commons.core.collection.Bid4WinMap;
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.commons.persistence.entity.Bid4WinEntity;
//import com.bid4win.commons.persistence.entity.Bid4WinEntityGeneratedID;
//import com.bid4win.commons.persistence.entity.Bid4WinRelation;
//import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
//import com.bid4win.persistence.entity.account.BidRight_;
//import com.bid4win.persistence.entity.product.Product;
//
///**
// * Cette classe défini une base de vente aux enchères<BR>
// * <BR>
// * @param <CLASS> Doit définir la classe réellement instanciée<BR>
// * @param <BID> Définition des enchères associées à la vente<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
////Annotation pour la persistence
//@MappedSuperclass
//@Access(AccessType.FIELD)
//@AttributeOverride(name = "version", column = @Column(length = 5))
//public class Auction<CLASS extends Auction<CLASS, BID>, BID extends Bid<BID, CLASS>>
//       extends Bid4WinEntityGeneratedID<CLASS>
//{
//  static
//  {
//    /** Défini la profondeur du noeud de relation existant avec la map des dernières enchères */
//    Auction_.NODE_LAST_BID_MAP.addNode(Bid_.NODE_AUCTION_LINK);
//    Auction_.NODE_LAST_BID_MAP.addNode(BidRight_.NODE_ACCOUNT_LINK);
//  }
//
//  /** Produit vendu aux enchères */
//  @Transient
//  private Product product = null;
//  /** Nombre d'enchères placées sur la vente */
//  @Transient
//  private int bidNb = 0;
//  /** Taille maximale de la map des dernières enchères placées sur la vente */
//  @Transient
//  private int lastBidNbMax = 10;
//  /** Map des dernières enchères placées sur la vente */
//  @Transient
//  private Bid4WinMap<Integer, BID> lastBidMap = new Bid4WinMap<Integer, BID>();
//
//  /**
//   * Constructeur pour création par introspection
//   */
//  protected Auction()
//  {
//    super();
//  }
//
//  /**
//   * Constructeur complet de la vente aux enchères
//   * @param product Produit vendu aux enchères
//   * @throws ModelArgumentException TODO A COMMENTER
//   */
//  protected Auction(Product product) throws ModelArgumentException
//  {
//    super(null);
//    this.defineProduct(product);
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
//           this.getBidNb() == toBeCompared.getBidNb();
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
//    // Retourne le rendu
//    return buffer;
//  }
//  /**
//   * 
//   * TODO A COMMENTER
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getFullRelationNodeList()
//   */
//  @Override
//  protected Bid4WinList<Bid4WinRelationNode> getFullRelationNodeList()
//  {
//    Bid4WinList<Bid4WinRelationNode> nodeList = super.getFullRelationNodeList();
//    nodeList.add(Auction_.NODE_LAST_BID_MAP);
//    nodeList.add(Auction_.NODE_PRODUCT);
//    return nodeList;
//  }
//  /**
//   * 
//   * TODO A COMMENTER
//   * @param relation {@inheritDoc}
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getRelationSimple(com.bid4win.commons.persistence.entity.Bid4WinRelation)
//   */
//  @Override
//  protected Bid4WinEntity<?, ?> getRelationSimple(Bid4WinRelation relation)
//  {
//    if(relation.equals(Auction_.RELATION_PRODUCT))
//    {
//      return this.getProduct();
//    }
//    return super.getRelationSimple(relation);
//  }
//  /**
//   * 
//   * TODO A COMMENTER
//   * @param relation {@inheritDoc}
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getRelationMap(com.bid4win.commons.persistence.entity.Bid4WinRelation)
//   */
//  @Override
//  protected java.util.Map<?, ? extends Bid4WinEntity<?, ?>> getRelationMap(Bid4WinRelation relation)
//  {
//    if(relation.equals(Auction_.RELATION_LAST_BID_MAP))
//    {
//      return this.getLastBidMapInternal();
//    }
//    return super.getRelationMap(relation);
//  }
//  
//  /**
//   * Getter du nombre de dernières enchères accessibles sur la vente aux enchères
//   * courante
//   * @return Le nombre de dernières enchères accessibles sur la vente aux enchères
//   * courante
//   */
//  public int getLastBidNb()
//  {
//    return this.getLastBidMap().size();
//  }
//  /**
//   * Getter de la Taille maximale de la map des dernières enchères placées sur la
//   * vente
//   * @return La taille maximale de la map des dernières enchères placées sur la
//   * vente
//   */
//  public int getLastBidNbMax()
//  {
//    return this.lastBidNbMax;
//  }
//  /**
//   * Getter de l'enchère dont la position absolue est précisée en argument
//   * @param absolutePosition Position absolue de l'enchère à retourner
//   * @return L'enchère trouvée à la position absolue indiquée en argument
//   */
//  public BID getBid(int absolutePosition)
//  {
//    return this.getLastBidMap().get(absolutePosition);
//  }
//  /**
//   * Getter de l'enchère dont la position relative par rapport à la dernière enchère
//   * de la vente est précisée en argument
//   * @param relativePosition Position relative de l'enchère à retourner par rapport
//   * à la dernière enchère de la vente
//   * @return L'enchère trouvée à la position relative indiquée en argument
//   */
//  public BID getLastBid(int relativePosition)
//  {
//    return this.getBid(this.getBidNb() - relativePosition);
//  }
//  /**
//   * Getter de la dernière enchère de la vente
//   * @return La dernière enchère de la vente
//   */
//  public BID getLastBid()
//  {
//    return this.getLastBid(0);
//  }
//  /**
//   * Cette fonction permet d'ajouter l'enchère en argument à la vente courante
//   * @param bid Enchère à ajouter à la vente courante
//   * @return La position absolue de l'enchère dans la vente courante
//   * @throws ModelArgumentException Si on ajoute une enchère nulle
//   */
//  protected int addBid(BID bid) throws ModelArgumentException
//  {
//    UtilObject.checkNotNull("bid", bid);
//    // Calcul la position absolue de l'echère dans la vente
//    int position = this.getBidNb() + 1;
//    // Augmente le nombre d'enchères de la vente
//    this.defineBidNb(position);
//    // Ajoute l'enchère à la vente
//    this.getLastBidMap().put(position, bid);
//    // Retourne la position absolue de l'echère dans la vente
//    return position;
//  }
//  /**
//   * Cette fonction permet de retirer l'enchère dont la position absolue est précisée
//   * en argument
//   * @param absolutePosition Position absolue de l'enchère à retirer de la vente
//   * courante
//   * @throws ModelArgumentException Si l'enchère à retirer ne peut être trouvée
//   */
//  protected void removeBid(int absolutePosition) throws ModelArgumentException
//  {
//    UtilObject.checkNotNull("remove", this.getLastBidMap().remove(absolutePosition));
//  }
//  /**
//   * Getter de la map des dernières enchères placées sur la vente
//   * @return La map des dernières enchères placées sur la vente
//   */
//  private Bid4WinMap<Integer, BID> getLastBidMap()
//  {
//    return this.lastBidMap;
//  }
//  /**
//   * Setter de la map des dernières enchères placées sur la vente
//   * @param lastBidMap Map des dernières enchères placées sur la vente à positionner
//   */
//  private void setLastBidMap(Bid4WinMap<Integer, BID> lastBidMap)
//  {
//    this.lastBidMap = lastBidMap;
//  }
//
//  /**
//   * Cette méthode permet de définir le produit vendu aux enchères
//   * @param product Définition du produit vendu aux enchères
//   * @throws ModelArgumentException Si on définie un produit nul
//   */
//  private void defineProduct(Product product) throws ModelArgumentException
//  {
//    this.setProduct(UtilObject.checkNotNull("product", product));
//  }
//  
//  /**
//   * Cette méthode permet de définir le nombre d'enchères placées sur la vente
//   * @param bidNb Définition du nombre d'enchères placées sur la vente
//   * @throws ModelArgumentException Si on définie un nombre inférieur à zéro
//   */
//  private void defineBidNb(int bidNb) throws ModelArgumentException
//  {
//    this.setBidNb(UtilNumber.checkMinValue("bidNb", bidNb, 0, true));
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
//  @ManyToOne(fetch = FetchType.EAGER, cascade = {})
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
//   * Getter de la map interne des dernières enchères placées sur la vente
//   * @return La map interne des dernières enchères placées sur la vente
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @OneToMany(mappedBy = "auctionLink", fetch = FetchType.LAZY,
//             cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
//  @MapKey(name = "position")
//  @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
//  private Map<Integer, BID> getLastBidMapInternal()
//  {
//    return this.getLastBidMap().getInternal();
//  }
//  /**
//   * Setter de la map interne des dernières enchères placées sur la vente
//   * @param internalLastBidMap Map interne des dernières enchères à positionner
//   * sur la vente
//   */
//  @SuppressWarnings(value = "unused")
//  private void setLastBidMapInternal(Map<Integer, BID> internalLastBidMap)
//  {
//    this.setLastBidMap(new Bid4WinMap<Integer, BID>(internalLastBidMap, true));
//  }
//}
