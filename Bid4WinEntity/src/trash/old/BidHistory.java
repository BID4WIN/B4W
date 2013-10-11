package trash.old;
//package com.bid4win.persistence.entity.auction.old;
//
//import com.bid4win.commons.core.UtilObject;
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.commons.core.exception.UserException;
//import com.bid4win.persistence.entity.auction.Bid;
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
//   * @throws ModelArgumentException Si l'enchère ou l'historique de vente associée
//   * est nul
//   * @throws UserException TODO A COMMENTER
//   */
//  public BidHistory(Bid<?, ?> bid, AuctionHistory auction)
//         throws ModelArgumentException, UserException
//  {
//    super(UtilObject.checkNotNull("bid", bid).getAccount(), auction, bid.getPosition());
//  }
//}
