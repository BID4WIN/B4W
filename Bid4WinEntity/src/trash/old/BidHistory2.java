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
// * Cette classe d�fini un historique d'ench�re<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
//public class BidHistory extends BidAbstract<BidHistory, AuctionHistory>
//{
//  /**
//   * Constructeur pour cr�ation par introspection
//   */
//  @SuppressWarnings("unused")
//  private BidHistory()
//  {
//    super();
//  }
//
//  /**
//   * Constructeur complet
//   * @param bid Ench�res dont on souhaite construire l'historique
//   * @param auction Historique de la vente aux ench�res associ�e
//   * @throws UserException Si l'ench�re ou l'historique de vente associ�e est nul
//   */
//  public BidHistory(BidAbstract<?, ?> bid, AuctionHistory auction) throws UserException
//  {
//    super(UtilObject.checkNotNull("bid", bid,
//                                  AuctionRef.AUCTION_MISSING_ERROR).getAccount(),
//          auction, bid.getPosition());
//  }
//
//  /**
//   * Cette m�thode permet de savoir si l'historique courant est bien celui de l'
//   * ench�re en argument
//   * @param bid Ench�re dont on veut v�rifier l'historique
//   * @return True si l'historique courant est bien celui de l'ench�re en argument
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
