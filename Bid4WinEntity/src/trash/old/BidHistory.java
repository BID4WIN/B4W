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
//   * @throws ModelArgumentException Si l'ench�re ou l'historique de vente associ�e
//   * est nul
//   * @throws UserException TODO A COMMENTER
//   */
//  public BidHistory(Bid<?, ?> bid, AuctionHistory auction)
//         throws ModelArgumentException, UserException
//  {
//    super(UtilObject.checkNotNull("bid", bid).getAccount(), auction, bid.getPosition());
//  }
//}
