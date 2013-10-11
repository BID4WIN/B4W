package trash.old;
//package com.bid4win.persistence.entity.auction.history;
//
//import com.bid4win.commons.core.UtilObject;
//import com.bid4win.commons.core.comparator.Bid4WinComparator;
//import com.bid4win.commons.core.exception.UserException;
//import com.bid4win.commons.core.reference.MessageRef.AuctionRef;
//import com.bid4win.persistence.entity.auction.BidAbstract;
//
///**
// * Cette classe défini un historique d'enchère<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//public class BidHistory extends BidAbstract<BidHistory, AuctionHistory>
//{
//  /**
//   * Constructeur pour création par introspection
//   */
//  @SuppressWarnings("unused")
//  private BidHistory()
//  {
//    super();
//  }
//
//  /**
//   * Constructeur complet
//   * @param bid Enchères dont on souhaite construire l'historique
//   * @param auction Historique de la vente aux enchères associée
//   * @throws UserException Si l'enchère ou l'historique de vente associée est nul
//   */
//  public BidHistory(BidAbstract<?, ?> bid, AuctionHistory auction) throws UserException
//  {
//    super(UtilObject.checkNotNull("bid", bid,
//                                  AuctionRef.AUCTION_MISSING_ERROR).getAccount(),
//          auction, bid.getPosition());
//  }
//
//  /**
//   * Cette méthode permet de savoir si l'historique courant est bien celui de l'
//   * enchère en argument
//   * @param bid Enchère dont on veut vérifier l'historique
//   * @return True si l'historique courant est bien celui de l'enchère en argument
//   */
//  public boolean isHistoryOf(BidAbstract<?, ?> bid)
//  {
//    return Bid4WinComparator.getInstance().equals(this.getAccount().getId(),
//                                                  bid.getAccount().getId()) &&
//           Bid4WinComparator.getInstance().equals(this.getAuction().getId(),
//                                                  bid.getAuction().getId()) &&
//           this.getPosition() == bid.getPosition();
//  }
//}
