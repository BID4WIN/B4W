//package trash;
//
//import java.util.Map;
//
//import javax.persistence.Access;
//import javax.persistence.AccessType;
//import javax.persistence.AttributeOverride;
//import javax.persistence.Cacheable;
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.Id;
//import javax.persistence.MapKey;
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
//import com.bid4win.commons.persistence.entity.Bid4WinRelation;
//import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
//import com.bid4win.persistence.entity.account.BidRight_;
//
///**
// * Cette classe d�fini une vente aux ench�res<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
////Annotation pour la persistence
//@Entity
//@Access(AccessType.FIELD)
//@AttributeOverride(name = "version", column = @Column(length = 5))
//@Cacheable
//@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
//public class Auction extends Bid4WinEntity<Auction, String>
//{
//  static
//  {
//    /** D�fini la profondeur du noeud de relation existant avec la map des derni�res ench�res */
//    Auction_.NODE_LAST_BID_MAP.addNode(Bid_.NODE_AUCTION_LINK);
//    Auction_.NODE_LAST_BID_MAP.addNode(BidRight_.NODE_ACCOUNT_LINK);
//  }
//
//  /** Nombre d'ench�res plac�es sur la vente */
//  @Transient
//  private int bidNb = 0;
//  /** Taille maximale de la map des derni�res ench�res plac�es sur la vente */
//  @Transient
//  private int lastBidNbMax = 10;
//  /** Map des derni�res ench�res plac�es sur la vente */
//  @Transient
//  private Bid4WinMap<Integer, Bid> lastBidMap = new Bid4WinMap<Integer, Bid>();
//
//  /**
//   * Constructeur pour cr�ation par introspection
//   */
//  @SuppressWarnings("unused")
//  private Auction()
//  {
//    super();
//  }
//
//  /**
//   * 
//   * TODO A COMMENTER
//   * @param id TODO A COMMENTER
//   * @throws ModelArgumentException TODO A COMMENTER
//   */
//  public Auction(String id) throws ModelArgumentException
//  {
//    super(id);
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
//  protected boolean sameRelationNoneInternal(Auction toBeCompared, boolean identical)
//  {
//    return super.sameRelationNoneInternal(toBeCompared, identical) &&
//           this.getBidNb() == toBeCompared.getBidNb();
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
//    return nodeList;
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
//   * Getter du nombre de derni�res ench�res accessibles sur la vente aux ench�res
//   * courante
//   * @return Le nombre de derni�res ench�res accessibles sur la vente aux ench�res
//   * courante
//   */
//  public int getLastBidNb()
//  {
//    return this.getLastBidMap().size();
//  }
//  /**
//   * Getter de la Taille maximale de la map des derni�res ench�res plac�es sur la
//   * vente
//   * @return La taille maximale de la map des derni�res ench�res plac�es sur la
//   * vente
//   */
//  public int getLastBidNbMax()
//  {
//    return this.lastBidNbMax;
//  }
//  /**
//   * Getter de l'ench�re dont la position absolue est pr�cis�e en argument
//   * @param absolutePosition Position absolue de l'ench�re � retourner
//   * @return L'ench�re trouv�e � la position absolue indiqu�e en argument
//   */
//  public Bid getBid(int absolutePosition)
//  {
//    return this.getLastBidMap().get(absolutePosition);
//  }
//  /**
//   * Getter de l'ench�re dont la position relative par rapport � la derni�re ench�re
//   * de la vente est pr�cis�e en argument
//   * @param relativePosition Position relative de l'ench�re � retourner par rapport
//   * � la derni�re ench�re de la vente
//   * @return L'ench�re trouv�e � la position relative indiqu�e en argument
//   */
//  public Bid getLastBid(int relativePosition)
//  {
//    return this.getBid(this.getBidNb() - relativePosition);
//  }
//  /**
//   * Getter de la derni�re ench�re de la vente
//   * @return La derni�re ench�re de la vente
//   */
//  public Bid getLastBid()
//  {
//    return this.getLastBid(0);
//  }
//  /**
//   * Cette fonction permet d'ajouter l'ench�re en argument � la vente courante
//   * @param bid Ench�re � ajouter � la vente courante
//   * @return La position absolue de l'ench�re dans la vente courante
//   * @throws ModelArgumentException Si on ajoute une ench�re nulle
//   */
//  protected int addBid(Bid bid) throws ModelArgumentException
//  {
//    UtilObject.checkNotNull("bid", bid);
//    // Calcul la position absolue de l'ech�re dans la vente
//    int position = this.getBidNb() + 1;
//    // Augmente le nombre d'ench�res de la vente
//    this.defineBidNb(position);
//    // Ajoute l'ench�re � la vente
//    this.getLastBidMap().put(position, bid);
//    // Retourne la position absolue de l'ech�re dans la vente
//    return position;
//  }
//  /**
//   * Cette fonction permet de retirer l'ench�re dont la position absolue est pr�cis�e
//   * en argument
//   * @param absolutePosition Position absolue de l'ench�re � retirer de la vente
//   * courante
//   * @throws ModelArgumentException Si l'ench�re � retirer ne peut �tre trouv�e
//   */
//  protected void removeBid(int absolutePosition) throws ModelArgumentException
//  {
//    UtilObject.checkNotNull("remove", this.getLastBidMap().remove(absolutePosition));
//  }
//  /**
//   * Getter de la map des derni�res ench�res plac�es sur la vente
//   * @return La map des derni�res ench�res plac�es sur la vente
//   */
//  private Bid4WinMap<Integer, Bid> getLastBidMap()
//  {
//    return this.lastBidMap;
//  }
//  /**
//   * Setter de la map des derni�res ench�res plac�es sur la vente
//   * @param lastBidMap Map des derni�res ench�res plac�es sur la vente � positionner
//   */
//  private void setLastBidMap(Bid4WinMap<Integer, Bid> lastBidMap)
//  {
//    this.lastBidMap = lastBidMap;
//  }
//
//  /**
//   * Cette m�thode permet de d�finir le nombre d'ench�res plac�es sur la vente
//   * @param bidNb D�finition du nombre d'ench�res plac�es sur la vente
//   * @throws ModelArgumentException Si on d�finie un nombre inf�rieur � z�ro
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
//   * Getter de l'identifiant de la vente aux ench�res
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getId()
//   */
//  @Override
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @Id()
//  @Column(name = "ID", length = 10, nullable = false, unique = true)
//  public String getId()
//  {
//    return super.getId();
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
//   * Getter de la map interne des derni�res ench�res plac�es sur la vente
//   * @return La map interne des derni�res ench�res plac�es sur la vente
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @OneToMany(mappedBy = "auctionLink", fetch = FetchType.LAZY,
//             cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
//  @MapKey(name = "position")
//  @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
//  private Map<Integer, Bid> getLastBidMapInternal()
//  {
//    return this.getLastBidMap().getInternal();
//  }
//  /**
//   * Setter de la map interne des derni�res ench�res plac�es sur la vente
//   * @param internalLastBidMap Map interne des derni�res ench�res � positionner
//   * sur la vente
//   */
//  @SuppressWarnings(value = "unused")
//  private void setLastBidMapInternal(Map<Integer, Bid> internalLastBidMap)
//  {
//    this.setLastBidMap(new Bid4WinMap<Integer, Bid>(internalLastBidMap, true));
//  }
//}
