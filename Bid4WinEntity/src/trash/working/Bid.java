//package trash.working;
//
//import javax.persistence.Access;
//import javax.persistence.AccessType;
//import javax.persistence.AttributeOverride;
//import javax.persistence.Column;
//import javax.persistence.FetchType;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.MappedSuperclass;
//import javax.persistence.Transient;
//
//import com.bid4win.commons.core.Bid4WinComparator;
//import com.bid4win.commons.core.UtilBoolean;
//import com.bid4win.commons.core.UtilNumber;
//import com.bid4win.commons.core.UtilObject;
//import com.bid4win.commons.core.UtilString;
//import com.bid4win.commons.core.collection.Bid4WinList;
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.commons.core.security.exception.ProtectionException;
//import com.bid4win.commons.persistence.entity.Bid4WinEntity;
//import com.bid4win.commons.persistence.entity.Bid4WinRelation;
//import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
//import com.bid4win.persistence.entity.account.credit.Credit;
//
///**
// * Cette classe défini une enchère placée sur une vente<BR>
// * <BR>
// * @param <CLASS> Doit définir la classe réellement instanciée<BR>
// * @param <AUCTION> Définition de la vente associée à l'enchère<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
////Annotation pour la persistence
//@MappedSuperclass
//@Access(AccessType.FIELD)
//@AttributeOverride(name = "nb", column = @Column(name = "CREDIT_NB"))
//public class Bid<CLASS extends Bid<CLASS, AUCTION>, AUCTION extends Auction<AUCTION, CLASS>>
//       extends Credit<CLASS>
//{
//  /** Position de l'enchère sur la vente */
//  @Transient
//  private int position = 0;
//  /** Vente sur laquelle est placée l'enchère */
//  @Transient
//  private AUCTION auction = null;
//  /** Lien vers la vente sur laquelle est placée l'enchère */
//  @Transient
//  private AUCTION auctionLink = null;
//
//  /**
//   * Constructeur pour création par introspection
//   */
//  @SuppressWarnings("unused")
//  protected Bid()
//  {
//    super();
//  }
//
//  /**
//   *
//   * TODO A COMMENTER
//   * @param credit Crédits utilisés pour placer l'enchère
//   * @param auction Vente sur laquelle est placée l'enchère
//   * @throws ModelArgumentException TODO A COMMENTER
//   * @throws ProtectionException TODO A COMMENTER
//   */
//  protected Bid(Credit<?> credit, AUCTION auction) throws ProtectionException, ModelArgumentException
//  {
//    super(credit);
//    // Défini la vente sur laquelle est placée l'enchère
//    this.defineAuction(auction);
//    // Lie l'enchère à sa vente
//    this.linkToAuction();
//  }
//
//  /**
//   * Redéfini l'équivalence interne de deux enchères sans prise en compte de leurs
//   * relations afin d'y ajouter le test de leurs données propres
//   * @param toBeCompared {@inheritDoc}
//   * @param identical {@inheritDoc}
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#sameRelationNoneInternal(com.bid4win.commons.persistence.entity.Bid4WinEntity, boolean)
//   */
//  @Override
//  protected boolean sameRelationNoneInternal(CLASS toBeCompared, boolean identical)
//  {
//    return super.sameRelationNoneInternal(toBeCompared, identical) &&
//           this.getPosition() == toBeCompared.getPosition();
//
//  }
//  /**
//   * Permet d'effectuer le rendu simple du lot de droits à enchérir courant sans
//   * prise en compte de ses relations
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#renderRelationNone()
//   */
//  @Override
//  protected StringBuffer renderRelationNone()
//  {
//    // Effectue le rendu de base sans lien d'un droit à enchérir
//    StringBuffer buffer = super.renderRelationNone();
//    // Ajoute les éléments de l'enchère
//    buffer.append(" POSITION=" + this.getPosition());
//    buffer.append(" CREDIT_NB=" + this.getCreditNb());
//    // Retourne le rendu
//    return buffer;
//  }
//  /**
//   * Ajoute à la liste des noeuds de relations de l'enchère le lien vers son vente
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.account.AccountBasedEntity#getFullRelationNodeList()
//   */
//  @Override
//  protected Bid4WinList<Bid4WinRelationNode> getFullRelationNodeList()
//  {
//    Bid4WinList<Bid4WinRelationNode> nodeList = super.getFullRelationNodeList();
//    nodeList.add(Bid_.NODE_AUCTION);
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
//    if(relation.equals(Bid_.RELATION_AUCTION))
//    {
//      return this.getAuction();
//    }
//    else if(relation.equals(Bid_.RELATION_AUCTION_LINK))
//    {
//      return this.getAuctionLink();
//    }
//    return super.getRelationSimple(relation);
//  }
//
//  /**
//   * Permet de savoir si l'enchère courante est liée à une vente
//   * @return True si l'enchère courante est liée à une vente, false sinon
//   */
//  public boolean isLinkedToAuction()
//  {
//    return this.getAuctionLink() != null;
//  }
//  /**
//   * Cette méthode permet de lier l'enchère à sa vente
//   * @throws ProtectionException Si l'enchère courante ou sa vente est protégée
//   * @throws ModelArgumentException Si l'enchère est déjà liée ou si sa vente est
//   * nulle ou la référence déjà
//   */
//  protected void linkToAuction() throws ProtectionException, ModelArgumentException
//  {
//    // Vérifie la protection de l'enchère
//    this.checkProtection();
//    // Vérifie que la vente n'est pas nulle
//    UtilObject.checkNotNull("auction", this.getAuction());
//    // Vérifie que l'enchère courante n'est pas déjà liée
//    UtilBoolean.checkFalse("isLinkedToAuction()", this.isLinkedToAuction());
//    // Crée le lien de la vente vers l'enchère
//    this.getAuction().addBid(this);
//    // Crée le lien de l'enchère vers sa vente
//    this.setAuctionLink(this.getAuction());
//  }
//  /**
//   * Cette méthode permet de délier l'enchère courante de sa vente et inversement
//   * @return La vente anciennement liée à l'enchère courante
//   * @throws ProtectionException Si l'enchère courante ou sa vente est protégée
//   * @throws ModelArgumentException Si l'enchère courante n'est liée à aucune vente
//   */
//  public Auction unlinkFromAuction() throws ProtectionException, ModelArgumentException
//  {
//    // Vérifie la protection de l'enchère courante
//    this.checkProtection();
//    // Vérifie que l'enchère courante est bien liée
//    Auction auction = UtilObject.checkNotNull("auctionLink", this.getAuctionLink());
//    // Supprime le lien de la vente vers l'enchère
//    auction.removeBid(this.getPosition());
//    // Supprime le lien de l'enchère vers sa vente
//    this.setAuctionLink(null);
//    return auction;
//  }
//
//  /**
//   * Cette méthode permet de définir la position de l'enchère dans la vente
//   * @param position Définition de la position de l'enchère dans la vente
//   * @throws ModelArgumentException Si on définie une position inférieure à un
//   */
//  private void definePosition(int position) throws ModelArgumentException
//  {
//    this.setPosition(UtilNumber.checkMinValue("order", position, 1, true));
//  }
//  /**
//   * Cette méthode permet de définir la vente sur laquelle est placée l'enchère
//   * @param auction Définition de la vente sur laquelle est placée l'enchère
//   * @throws ModelArgumentException Si on définie une vente nulle
//   */
//  private void defineAuction(AUCTION auction) throws ModelArgumentException
//  {
//    this.setAuction(UtilObject.checkNotNull("auction", auction));
//  }
//  /**
//   * Getter du nombre de crédits utilisés par cette enchère
//   * @return Le nombre de crédits utilisés par cette enchère
//   */
//  public int getCreditNb()
//  {
//    return this.getNb();
//  }
//
//  /** #################################################################### **/
//  /** ########################### PERSISTENCE ############################ **/
//  /** #################################################################### **/
//  /**
//   * Getter de la position de l'enchère dans la vente
//   * @return La position de l'enchère dans la vente
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @Column(name = "POSITION", length = 5, nullable = false, unique = false)
//  public int getPosition()
//  {
//    return this.position;
//  }
//  /**
//   * Setter de la position de l'enchère dans la vente
//   * @param position Position de l'enchère dans la vente à positionner
//   */
//  private void setPosition(int position)
//  {
//    this.position = position;
//  }
//
//  /**
//   * Getter de la vente sur laquelle est placée l'enchère
//   * @return La vente sur laquelle est placée l'enchère
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @Column(name = "AUCTION_ID", length = 12, nullable = false, unique = false)
//  public AUCTION getAuction()
//  {
//    return this.auction;
//  }
//  /**
//   * Setter de la vente sur laquelle est placée l'enchère
//   * @param auction Vente sur laquelle est placée l'enchère à positionner
//   */
//  private void setAuction(AUCTION auction)
//  {
//    this.auction = auction;
//  }
//
//  /**
//   * Getter du lien vers la vente sur laquelle est placée l'enchère
//   * @return Le lien vers la vente sur laquelle est placée l'enchère
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @ManyToOne(fetch = FetchType.EAGER, cascade = {})
//  @JoinColumn(name = "AUCTION_ID_LINK", nullable = true, unique = false)
//  public AUCTION getAuctionLink()
//  {
//    return this.auctionLink;
//  }
//  /**
//   * Setter du lien vers la vente sur laquelle est placée l'enchère
//   * @param auction Vente avec laquelle lier l'enchère
//   */
//  private void setAuctionLink(AUCTION auction)
//  {
//    this.auctionLink = auction;
//  }
//}
