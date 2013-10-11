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
// * Cette classe d�fini un historique de vente aux ench�res<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
//@Entity
//@Access(AccessType.FIELD)
//@Cacheable
//@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
//@TypeDef(name = "AUCTION_TYPE", typeClass = AuctionTypeUserType.class)
//public class AuctionHistory
//       extends AuctionAbstract<AuctionHistory, BidHistory, ScheduleHistory, TermsHistory>
//{
//  /** Type de la vente aux ench�res historis�e */
//  @Transient
//  private AuctionType type = null;
//
//  /**
//   * Constructeur pour cr�ation par introspection
//   */
//  protected AuctionHistory()
//  {
//    super();
//  }
//  /**
//   * Constructeur de l'historique de la vente aux ench�re en argument
//   * @param id Identifiant de la vente aux ench�res historis�e
//   * @param product Produit vendu aux ench�res
//   * @param schedule �l�ments de planification de base de la vente aux ench�res
//   * historis�e
//   * @param terms Conditions de la vente aux ench�res historis�e
//   * @param type TODO A COMMENTER
//   * @param bidNb TODO A COMMENTER
//   * @throws UserException Si le produit, les �l�ments de planification, les conditions
//   * ou le type de vente en argument est nul ou si le nombre d'ench�res est inf�rieur
//   * � z�ro
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
//   * Cette m�thode permet de savoir si l'historique courant est bien celui de la
//   * vente aux ench�res en argument
//   * @param auction Vente aux ench�res dont on veut v�rifier l'historique
//   * @return True si l'historique courant est bien celui de la vente aux ench�res
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
//   * Cette m�thode permet de d�finir le type de la vente aux ench�res historis�e
//   * @param type D�finition du type de la vente aux ench�res historis�e
//   * @throws UserException Si on d�fini un type nul
//   */
//  private void defineType(AuctionType type) throws ProtectionException, UserException
//  {
//    // V�rifie la protection de la vente aux ench�res courante
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
