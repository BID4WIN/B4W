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
// * Cette classe d�fini une ench�re plac�e sur une vente<BR>
// * <BR>
// * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
// * @param <AUCTION> D�finition de la vente associ�e � l'ench�re<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
////Annotation pour la persistence
//@MappedSuperclass
//@Access(AccessType.FIELD)
//@AttributeOverride(name = "nb", column = @Column(name = "CREDIT_NB"))
//public class Bid<CLASS extends Bid<CLASS, AUCTION>, AUCTION extends Auction<AUCTION, CLASS>>
//       extends Credit<CLASS>
//{
//  /** Position de l'ench�re sur la vente */
//  @Transient
//  private int position = 0;
//  /** Vente sur laquelle est plac�e l'ench�re */
//  @Transient
//  private AUCTION auction = null;
//  /** Lien vers la vente sur laquelle est plac�e l'ench�re */
//  @Transient
//  private AUCTION auctionLink = null;
//
//  /**
//   * Constructeur pour cr�ation par introspection
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
//   * @param credit Cr�dits utilis�s pour placer l'ench�re
//   * @param auction Vente sur laquelle est plac�e l'ench�re
//   * @throws ModelArgumentException TODO A COMMENTER
//   * @throws ProtectionException TODO A COMMENTER
//   */
//  protected Bid(Credit<?> credit, AUCTION auction) throws ProtectionException, ModelArgumentException
//  {
//    super(credit);
//    // D�fini la vente sur laquelle est plac�e l'ench�re
//    this.defineAuction(auction);
//    // Lie l'ench�re � sa vente
//    this.linkToAuction();
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
//  protected boolean sameRelationNoneInternal(CLASS toBeCompared, boolean identical)
//  {
//    return super.sameRelationNoneInternal(toBeCompared, identical) &&
//           this.getPosition() == toBeCompared.getPosition();
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
//    buffer.append(" CREDIT_NB=" + this.getCreditNb());
//    // Retourne le rendu
//    return buffer;
//  }
//  /**
//   * Ajoute � la liste des noeuds de relations de l'ench�re le lien vers son vente
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
//   * Permet de savoir si l'ench�re courante est li�e � une vente
//   * @return True si l'ench�re courante est li�e � une vente, false sinon
//   */
//  public boolean isLinkedToAuction()
//  {
//    return this.getAuctionLink() != null;
//  }
//  /**
//   * Cette m�thode permet de lier l'ench�re � sa vente
//   * @throws ProtectionException Si l'ench�re courante ou sa vente est prot�g�e
//   * @throws ModelArgumentException Si l'ench�re est d�j� li�e ou si sa vente est
//   * nulle ou la r�f�rence d�j�
//   */
//  protected void linkToAuction() throws ProtectionException, ModelArgumentException
//  {
//    // V�rifie la protection de l'ench�re
//    this.checkProtection();
//    // V�rifie que la vente n'est pas nulle
//    UtilObject.checkNotNull("auction", this.getAuction());
//    // V�rifie que l'ench�re courante n'est pas d�j� li�e
//    UtilBoolean.checkFalse("isLinkedToAuction()", this.isLinkedToAuction());
//    // Cr�e le lien de la vente vers l'ench�re
//    this.getAuction().addBid(this);
//    // Cr�e le lien de l'ench�re vers sa vente
//    this.setAuctionLink(this.getAuction());
//  }
//  /**
//   * Cette m�thode permet de d�lier l'ench�re courante de sa vente et inversement
//   * @return La vente anciennement li�e � l'ench�re courante
//   * @throws ProtectionException Si l'ench�re courante ou sa vente est prot�g�e
//   * @throws ModelArgumentException Si l'ench�re courante n'est li�e � aucune vente
//   */
//  public Auction unlinkFromAuction() throws ProtectionException, ModelArgumentException
//  {
//    // V�rifie la protection de l'ench�re courante
//    this.checkProtection();
//    // V�rifie que l'ench�re courante est bien li�e
//    Auction auction = UtilObject.checkNotNull("auctionLink", this.getAuctionLink());
//    // Supprime le lien de la vente vers l'ench�re
//    auction.removeBid(this.getPosition());
//    // Supprime le lien de l'ench�re vers sa vente
//    this.setAuctionLink(null);
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
//   * Cette m�thode permet de d�finir la vente sur laquelle est plac�e l'ench�re
//   * @param auction D�finition de la vente sur laquelle est plac�e l'ench�re
//   * @throws ModelArgumentException Si on d�finie une vente nulle
//   */
//  private void defineAuction(AUCTION auction) throws ModelArgumentException
//  {
//    this.setAuction(UtilObject.checkNotNull("auction", auction));
//  }
//  /**
//   * Getter du nombre de cr�dits utilis�s par cette ench�re
//   * @return Le nombre de cr�dits utilis�s par cette ench�re
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
//   * Getter de la vente sur laquelle est plac�e l'ench�re
//   * @return La vente sur laquelle est plac�e l'ench�re
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @Column(name = "AUCTION_ID", length = 12, nullable = false, unique = false)
//  public AUCTION getAuction()
//  {
//    return this.auction;
//  }
//  /**
//   * Setter de la vente sur laquelle est plac�e l'ench�re
//   * @param auction Vente sur laquelle est plac�e l'ench�re � positionner
//   */
//  private void setAuction(AUCTION auction)
//  {
//    this.auction = auction;
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
//  public AUCTION getAuctionLink()
//  {
//    return this.auctionLink;
//  }
//  /**
//   * Setter du lien vers la vente sur laquelle est plac�e l'ench�re
//   * @param auction Vente avec laquelle lier l'ench�re
//   */
//  private void setAuctionLink(AUCTION auction)
//  {
//    this.auctionLink = auction;
//  }
//}
