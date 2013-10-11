package trash.old;
//package com.bid4win.persistence.entity.auction.history;
//
//import javax.persistence.Access;
//import javax.persistence.AccessType;
//import javax.persistence.Cacheable;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Transient;
//
//import org.hibernate.annotations.Cache;
//import org.hibernate.annotations.CacheConcurrencyStrategy;
//import org.hibernate.annotations.Type;
//import org.hibernate.annotations.TypeDef;
//
//import com.bid4win.commons.core.UtilObject;
//import com.bid4win.commons.core.comparator.Bid4WinComparator;
//import com.bid4win.commons.core.exception.UserException;
//import com.bid4win.commons.core.reference.MessageRef.AuctionRef;
//import com.bid4win.commons.core.security.exception.ProtectionException;
//import com.bid4win.commons.persistence.entity.comparator.Bid4WinEntityComparator;
//import com.bid4win.persistence.entity.account.Account;
//import com.bid4win.persistence.entity.auction.AuctionAbstract;
//import com.bid4win.persistence.entity.auction.AuctionType;
//import com.bid4win.persistence.entity.product.Product;
//import com.bid4win.persistence.usertype.auction.AuctionTypeUserType;
//
///**
// * Cette classe défini un historique de vente aux enchères<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//@Entity
//@Access(AccessType.FIELD)
//@Cacheable
//@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
//@TypeDef(name = "AUCTION_TYPE", typeClass = AuctionTypeUserType.class)
//public class AuctionHistory
//       extends AuctionAbstract<AuctionHistory, BidHistory, ScheduleHistory, TermsHistory>
//{
//  /** Type de la vente aux enchères historisée */
//  @Transient
//  private AuctionType type = null;
//
//  /**
//   * Constructeur pour création par introspection
//   */
//  protected AuctionHistory()
//  {
//    super();
//  }
//  /**
//   * Constructeur de l'historique de la vente aux enchère en argument
//   * @param id Identifiant de la vente aux enchères historisée
//   * @param product Produit vendu aux enchères
//   * @param schedule Éléments de planification de base de la vente aux enchères
//   * historisée
//   * @param terms Conditions de la vente aux enchères historisée
//   * @param type TODO A COMMENTER
//   * @param bidNb TODO A COMMENTER
//   * @throws UserException Si le produit, les éléments de planification, les conditions
//   * ou le type de vente en argument est nul ou si le nombre d'enchères est inférieur
//   * à zéro
//   */
//  public AuctionHistory(String id, Product product, ScheduleHistory schedule,
//                        TermsHistory terms, AuctionType type, int bidNb) throws UserException
//  {
//    super(id, product, schedule, terms);
//    this.defineType(type);
//    this.defineBidNb(bidNb);
//  }
//
//  /**
//   * Cette méthode permet de savoir si l'historique courant est bien celui de la
//   * vente aux enchères en argument
//   * @param auction Vente aux enchères dont on veut vérifier l'historique
//   * @return True si l'historique courant est bien celui de la vente aux enchères
//   * en argument
//   */
//  public boolean isHistoryOf(AuctionAbstract<?, ?, ?, ?> auction)
//  {
//    return Bid4WinComparator.getInstance().equals(this.getId(), auction.getId()) &&
//           Bid4WinComparator.getInstance().equals(this.getType(), auction.getType()) &&
//           Bid4WinEntityComparator.getInstanceEntity().equals(this.getProduct(),
//                                                              auction.getProduct()) &&
//           auction.getSchedule().isHistory(this.getSchedule()) &&
//           auction.getTerms().isHistory(this.getTerms()) &&
//           this.getBidNb() == auction.getBidNb();
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @param account {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws ProtectionException {@inheritDoc}
//   * @throws UserException {@inheritDoc}
//   * @see com.bid4win.persistence.entity.auction.AuctionAbstract#addBid(com.bid4win.persistence.entity.account.Account)
//   * @deprecated TODO A COMMENTER
//   */
//  @Override
//  @Deprecated
//  public BidHistory addBid(Account account) throws ProtectionException, UserException
//  {
//    throw new UserException(AuctionRef.AUCTION_BID_INVALID_ERROR);
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @param account {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws ProtectionException {@inheritDoc}
//   * @throws UserException {@inheritDoc}
//   * @see com.bid4win.persistence.entity.auction.AuctionAbstract#createBid(com.bid4win.persistence.entity.account.Account)
//   * @deprecated TODO A COMMENTER
//   */
//  @Override
//  @Deprecated
//  protected BidHistory createBid(Account account) throws ProtectionException, UserException
//  {
//    throw new UserException(AuctionRef.AUCTION_BID_INVALID_ERROR);
//  }
//
//  /**
//   * Cette méthode permet de définir le type de la vente aux enchères historisée
//   * @param type Définition du type de la vente aux enchères historisée
//   * @throws UserException Si on défini un type nul
//   */
//  private void defineType(AuctionType type) throws ProtectionException, UserException
//  {
//    // Vérifie la protection de la vente aux enchères courante
//    this.checkProtection();
//    this.setType(UtilObject.checkNotNull("type", type,
//                                         AuctionRef.AUCTION_UNDEFINED_ERROR));
//  }
//
//  /** #################################################################### **/
//  /** ########################### PERSISTENCE ############################ **/
//  /** #################################################################### **/
//  /**
//   *
//   * TODO A COMMENTER
//   * @return {@inheritDoc}
//   * @see com.bid4win.persistence.entity.auction.AuctionAbstract#getType()
//   */
//  @Override
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @Column(name = "AUCTION_TYPE", length = 10, nullable = false, unique = false)
//  @Type(type = "AUCTION_TYPE")
//  public AuctionType getType()
//  {
//    return this.type;
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @param type TODO A COMMENTER
//   */
//  private void setType(AuctionType type)
//  {
//    this.type = type;
//  }
//}
