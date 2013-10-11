//package trash;
//
//import javax.persistence.Access;
//import javax.persistence.AccessType;
//import javax.persistence.Cacheable;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Transient;
//
//import org.hibernate.annotations.Cache;
//import org.hibernate.annotations.CacheConcurrencyStrategy;
//
//import com.bid4win.commons.core.Bid4WinComparator;
//import com.bid4win.commons.core.UtilNumber;
//import com.bid4win.commons.core.UtilObject;
//import com.bid4win.commons.core.UtilString;
//import com.bid4win.commons.core.collection.Bid4WinList;
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.commons.persistence.entity.Bid4WinEntity;
//import com.bid4win.commons.persistence.entity.Bid4WinRelation;
//import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
//import com.bid4win.persistence.entity.account.BidRight;
//
///**
// * Cette classe d�fini une ench�re plac�e sur une vente<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
////Annotation pour la persistence
//@Entity
//@Access(AccessType.FIELD)
//@Cacheable
//@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
//public class Bid extends BidRight<Bid>
//{
//  /** Position de l'ench�re sur la vente */
//  @Transient
//  private int position = 0;
//  /** Identifiant de la vente sur laquelle est plac�e l'ench�re */
//  @Transient
//  private String auctionId = null;
//  /** Lien vers la vente sur laquelle est plac�e l'ench�re */
//  @Transient
//  private Auction auctionLink = null;
//
//  /**
//   * Constructeur pour cr�ation par introspection
//   */
//  @SuppressWarnings("unused")
//  private Bid()
//  {
//    super();
//  }
//
//  /**
//   *
//   * TODO A COMMENTER
//   * @param bidRight TODO A COMMENTER
//   * @param auctionId TODO A COMMENTER
//   * @throws ModelArgumentException TODO A COMMENTER
//   */
//  protected Bid(BidRight<?> bidRight, String auctionId) throws ModelArgumentException
//  {
//    super(bidRight);
//    this.defineAuctionId(auctionId);
//  }
//
//  /**
//   * Constructeur complet
//   * @param bidRight TODO A COMMENTER
//   * @param auction TODO A COMMENTER
//   * @throws ModelArgumentException TODO A COMMENTER
//   */
//  public Bid(BidRight<?> bidRight, Auction auction) throws ModelArgumentException
//  {
//    this(bidRight, UtilObject.checkNotNull("auction", auction).getId());
//    this.setAuctionLink(auction);
//    this.definePosition(auction.addBid(this));
//  }
//
//  /**
//   * Red�fini l'�quivalence interne de deux ench�res sans prise en compte de leurs
//   * relations afin d'y ajouter le test de leurs donn�es propres
//   * @param toBeCompared {@inheritDoc}
//   * @param identical {@inheritDoc}
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#sameRelationNoneInternal(com.bid4win.commons.persistence.entity.Bid4WinEntity, boolean)
//   */
//  @Override
//  protected boolean sameRelationNoneInternal(Bid toBeCompared, boolean identical)
//  {
//    return super.sameRelationNoneInternal(toBeCompared, identical) &&
//           this.getPosition() == toBeCompared.getPosition() &&
//           Bid4WinComparator.getInstance().equals(this.getAuctionId(),
//                                                  toBeCompared.getAuctionId());
//
//  }
//  /**
//   * Permet d'effectuer le rendu simple du lot de droits � ench�rir courant sans
//   * prise en compte de ses relations
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#renderRelationNone()
//   */
//  @Override
//  protected StringBuffer renderRelationNone()
//  {
//    // Effectue le rendu de base sans lien d'un droit � ench�rir
//    StringBuffer buffer = super.renderRelationNone();
//    // Ajoute les �l�ments de l'ench�re
//    buffer.append(" POSITION=" + this.getPosition());
//    buffer.append(" AUCTION_ID=" + this.getAuctionId());
//    // Retourne le rendu
//    return buffer;
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @return {@inheritDoc}
//   * @see com.bid4win.persistence.entity.account.BidRight#getFullRelationNodeList()
//   */
//  @Override
//  protected Bid4WinList<Bid4WinRelationNode> getFullRelationNodeList()
//  {
//    Bid4WinList<Bid4WinRelationNode> nodeList = super.getFullRelationNodeList();
//    nodeList.add(Bid_.NODE_AUCTION_LINK);
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
//    if(relation.equals(Bid_.RELATION_AUCTION_LINK))
//    {
//      return this.getAuctionLink();
//    }
//    return super.getRelationSimple(relation);
//  }
//
//  /**
//   * Permet de savoir si l'ench�re courante est li�e � une vente
//   * @return True si l'ench�re courante est li�e � une vente, false sinon
//   */
//  public boolean isLinkedToAuction()
//  {
//    return this.getAuctionLink() != null;
//  }
//  /**
//   * Cette m�thode permet de d�lier l'ench�re courante de sa vente et inversement
//   * @return La vente anciennement li�e � l'ench�re courante
//   * @throws ModelArgumentException Si l'ench�re courante n'est li�e � aucune vente
//   */
//  public Auction unlinkFromAuction() throws ModelArgumentException
//  {
//    Auction auction = UtilObject.checkNotNull("auctionLink", this.getAuctionLink());
//    this.setAuctionLink(null);
//    auction.removeBid(this.getPosition());
//    return auction;
//  }
//
//  /**
//   * Cette m�thode permet de d�finir la position de l'ench�re dans la vente
//   * @param position D�finition de la position de l'ench�re dans la vente
//   * @throws ModelArgumentException Si on d�finie une position inf�rieure � un
//   */
//  private void definePosition(int position) throws ModelArgumentException
//  {
//    this.setPosition(UtilNumber.checkMinValue("order", position, 1, true));
//  }
//  /**
//   * Cette m�thode permet de d�finir l'identifiant de la vente sur laquelle est
//   * plac�e l'ench�re
//   * @param auctionId D�finition de l'identifiant de la vente sur laquelle est
//   * plac�e l'ench�re
//   * @throws ModelArgumentException Si on d�finie un identifiant nul
//   */
//  private void defineAuctionId(String auctionId) throws ModelArgumentException
//  {
//    auctionId = UtilString.trimNotNull(auctionId);
//    this.setAuctionId(UtilString.checkNotEmpty("auctionId", auctionId));
//  }
//
//  /** #################################################################### **/
//  /** ########################### PERSISTENCE ############################ **/
//  /** #################################################################### **/
//  /**
//   * Getter de la position de l'ench�re dans la vente
//   * @return La position de l'ench�re dans la vente
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @Column(name = "POSITION", length = 5, nullable = false, unique = false)
//  public int getPosition()
//  {
//    return this.position;
//  }
//  /**
//   * Setter de la position de l'ench�re dans la vente
//   * @param position Position de l'ench�re dans la vente � positionner
//   */
//  private void setPosition(int position)
//  {
//    this.position = position;
//  }
//
//  /**
//   * Getter de l'identifiant de la vente sur laquelle est plac�e l'ench�re
//   * @return L'identifiant de la vente sur laquelle est plac�e l'ench�re
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @Column(name = "AUCTION_ID", length = 10, nullable = false, unique = false)
//  public String getAuctionId()
//  {
//    return this.auctionId;
//  }
//  /**
//   * Setter de l'identifiant de la vente sur laquelle est plac�e l'ench�re
//   * @param auctionId Identifiant de la vente sur laquelle est plac�e l'ench�re
//   * � positionner
//   */
//  private void setAuctionId(String auctionId)
//  {
//    this.auctionId = auctionId;
//  }
//
//  /**
//   * Getter du lien vers la vente sur laquelle est plac�e l'ench�re
//   * @return Le lien vers la vente sur laquelle est plac�e l'ench�re
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @ManyToOne(fetch = FetchType.EAGER, cascade = {})
//  @JoinColumn(name = "AUCTION_ID_LINK", nullable = true, unique = false)
//  public Auction getAuctionLink()
//  {
//    return this.auctionLink;
//  }
//  /**
//   * Setter du lien vers la vente sur laquelle est plac�e l'ench�re
//   * @param auction Vente avec laquelle lier l'ench�re
//   */
//  private void setAuctionLink(Auction auction)
//  {
//    this.auctionLink = auction;
//  }
//}
