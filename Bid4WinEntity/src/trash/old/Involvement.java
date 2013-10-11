package trash.old;
//package com.bid4win.persistence.entity.account.credit;
//
//import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
//
///**
// * Cette classe défini un type d'implication de crédits<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//public class Involvement extends Bid4WinObjectType<Involvement>
//{
//  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
//   * valeur doit être modifiée si une évolution de la classe la rend incompatible
//   * avec les versions précédentes */
//  private static final long serialVersionUID = -2848163166522916784L;
//
//  /** Implication de crédits sur une vente aux enchères */
//  public final static Involvement AUCTION = new Involvement("AUCTION");
//  /** Implication de crédits sur une vente aux enchères normale */
//  public final static Involvement AUCTION_NORMAL = new Involvement(AuctionType.NORMAL);
//  /** Implication de crédits sur une vente aux enchères avec pré-inscription */
//  public final static Involvement AUCTION_PREBOOKED = new Involvement(AuctionType.PREBOOKED);
//
//  /**
//   *
//   * TODO A COMMENTER
//   * @param auctionType TODO A COMMENTER
//   * @return TODO A COMMENTER
//   */
//  public static Involvement getInvolvement(AuctionType auctionType)
//  {
//    return AUCTION.getSubtype(auctionType.getCode());
//  }
//
//  /**
//   * Constructeur
//   * @param code Code du type d'image
//   */
//  private Involvement(String code)
//  {
//    super(code);
//  }
//  /**
//   * Constructeur d'une implication de crédits dans une ventre aux enchères
//   * @param auctionType Type de la vente aux enchères
//   */
//  private Involvement(AuctionType auctionType)
//  {
//    this(auctionType.getCode(), AUCTION);
//  }
//  /**
//   * Constructeur
//   * @param code Code du type d'image
//   * @param parent Parent du type d'image
//   */
//  private Involvement(String code, Involvement parent)
//  {
//    super(code, parent);
//  }
//}
