package trash.old;
//package com.bid4win.persistence.entity.auction.old;
//
//import javax.persistence.Access;
//import javax.persistence.AccessType;
//import javax.persistence.Cacheable;
//import javax.persistence.Entity;
//
//import org.hibernate.annotations.Cache;
//import org.hibernate.annotations.CacheConcurrencyStrategy;
//
//import com.bid4win.commons.core.UtilObject;
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.persistence.entity.auction.AuctionAbstract;
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
//public class AuctionHistory
//       extends AuctionAbstract<AuctionHistory, BidHistory, ScheduleHistory, TermsHistory>
//{
//  /**
//   * Constructeur pour création par introspection
//   */
//  @SuppressWarnings("unused")
//  private AuctionHistory()
//  {
//    super();
//  }
//
//  /**
//   * Constructeur de l'historique de la vente aux enchère en argument
//   * @param auction Vente aux enchères dont on souhaite construire l'historique
//   * @throws ModelArgumentException Si la vente aux enchères en argument est nulle
//   */
//  public AuctionHistory(AuctionAbstract<?, ?, ?, ?> auction) throws ModelArgumentException
//  {
//    super(UtilObject.checkNotNull("auction", auction).getId(),
//          auction.getProduct(), new ScheduleHistory(auction), new TermsHistory(auction));
//    this.defineBidNb(auction.getBidNb());
//  }
//}
