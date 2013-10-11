package trash.old;
//package com.bid4win.persistence.entity.auction.old;
//
//import javax.persistence.Access;
//import javax.persistence.AccessType;
//import javax.persistence.FetchType;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.MappedSuperclass;
//import javax.persistence.Transient;
//
//import com.bid4win.commons.core.UtilObject;
//import com.bid4win.commons.core.collection.Bid4WinList;
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.commons.core.exception.UserException;
//import com.bid4win.commons.core.security.exception.ProtectionException;
//import com.bid4win.commons.persistence.entity.Bid4WinEntity;
//import com.bid4win.commons.persistence.entity.Bid4WinRelation;
//import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
//import com.bid4win.persistence.entity.account.Account;
//import com.bid4win.persistence.entity.auction.Bid_Relations;
//
///**
// * Cette classe défini une enchère placée sur une vente<BR>
// * <BR>
// * @param <CLASS> Doit définir la classe réellement instanciée<BR>
// * @param <AUCTION> Définition de la vente aux enchères<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
////Annotation pour la persistence
//@MappedSuperclass
//@Access(AccessType.FIELD)
//public class Bid<CLASS extends Bid<CLASS, AUCTION>,
//                 AUCTION extends Auction<AUCTION, CLASS, ?, ?>>
//       extends BidAbstract<CLASS, AUCTION>
//{
//  /** Lien vers la vente sur laquelle est placée l'enchère */
//  @Transient
//  private AUCTION auctionLink = null;
//
//  /**
//   * Constructeur pour création par introspection
//   */
//  protected Bid()
//  {
//    super();
//  }
//  /**
//   * Constructeur complet
//   * @param account Compte utilisateur de l'enchère
//   * @auction Vente de l'enchère
//   * @position Position de l'enchère dans la vente
//   * @throws ProtectionException Si la vente aux enchères est protégée
//   * @throws ModelArgumentException Si le compte utilisateur ou la vente en argument
//   * est nul ou si la position est inférieure à un
//   */
//  protected Bid(Account account, AUCTION auction, int position)
//            throws ProtectionException, ModelArgumentException, UserException
//  {
//    super(account, auction, position);
//    this.linkToAuction();
//  }
//
//  /**
//   * Ajoute à la liste des noeuds de relations de l'enchère le lien vers sa vente
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.account.AccountBasedEntity#getFullRelationNodeList()
//   */
//  @Override
//  protected Bid4WinList<Bid4WinRelationNode> getFullRelationNodeList()
//  {
//    Bid4WinList<Bid4WinRelationNode> nodeList = super.getFullRelationNodeList();
//    nodeList.add(Bid_Relations.NODE_AUCTION_LINK);
//    return nodeList;
//  }
//  /**
//   * Permet de récupérer le lien vers la vente de l'enchère s'il correspond à la
//   * relation en argument.
//   * @param relation {@inheritDoc}
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getRelationSimple(com.bid4win.commons.persistence.entity.Bid4WinRelation)
//   */
//  @Override
//  protected Bid4WinEntity<?, ?> getRelationSimple(Bid4WinRelation relation)
//  {
//    if(relation.equals(Bid_Relations.RELATION_AUCTION_LINK))
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
//   * Cette méthode permet de lier l'enchère courante à sa vente
//   * @throws ProtectionException Si l'enchère courante ou sa vente est protégée
//   * @throws ModelArgumentException Si la vente de l'enchère courante est nulle
//   * ou si cette dernière est déjà liée
//   */
//  @SuppressWarnings("unchecked")
//  private void linkToAuction() throws ProtectionException, ModelArgumentException
//  {
//    // Vérifie la protection de l'enchère courante
//    this.checkProtection();
//    // Vérifie que la vente de l'enchère courante n'est pas nulle
//    AUCTION auction = UtilObject.checkNotNull("auction", this.getAuction());
//    // Vérifie que l'enchère courante n'est pas déjà liée
//    UtilObject.checkNull("auctionLink", this.getAuctionLink());
//    // Crée le lien de la vente vers l'enchère
//    auction.putBid((CLASS)this);
//    // Crée le lien de l'enchère vers sa vente
//    this.setAuctionLink(auction);
//  }
//  /**
//   * Cette méthode permet de délier l'enchère courante de sa vente et inversement
//   * @return La vente anciennement liée à l'enchère courante
//   * @throws ProtectionException Si l'enchère courante ou sa vente est protégée
//   * @throws ModelArgumentException Si l'enchère courante n'est liée à aucune vente
//   */
//  @SuppressWarnings("unchecked")
//  public AUCTION unlinkFromAuction() throws ModelArgumentException
//  {
//    // Vérifie la protection de l'enchère courante
//    this.checkProtection();
//    // Vérifie que l'enchère courante est bien liée
//    AUCTION auction = UtilObject.checkNotNull("auctionLink", this.getAuctionLink());
//    // Retire l'enchère de la vente
//    auction.removeBid((CLASS)this);
//    // Supprime le lien de l'enchère vers sa vente
//    this.setAuctionLink(null);
//    return auction;
//  }
//
//  /** #################################################################### **/
//  /** ########################### PERSISTENCE ############################ **/
//  /** #################################################################### **/
//  /**
//   * Getter du lien vers la vente sur laquelle est placée l'enchère
//   * @return Le lien vers la vente sur laquelle est placée l'enchère
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @ManyToOne(fetch = FetchType.LAZY, cascade = {})
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
