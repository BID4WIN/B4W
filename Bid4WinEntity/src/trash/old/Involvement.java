package trash.old;
//package com.bid4win.persistence.entity.account.credit;
//
//import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
//
///**
// * Cette classe d�fini un type d'implication de cr�dits<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
//public class Involvement extends Bid4WinObjectType<Involvement>
//{
//  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
//   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
//   * avec les versions pr�c�dentes */
//  private static final long serialVersionUID = -2848163166522916784L;
//
//  /** Implication de cr�dits sur une vente aux ench�res */
//  public final static Involvement AUCTION = new Involvement("AUCTION");
//  /** Implication de cr�dits sur une vente aux ench�res normale */
//  public final static Involvement AUCTION_NORMAL = new Involvement(AuctionType.NORMAL);
//  /** Implication de cr�dits sur une vente aux ench�res avec pr�-inscription */
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
//   * Constructeur d'une implication de cr�dits dans une ventre aux ench�res
//   * @param auctionType Type de la vente aux ench�res
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
