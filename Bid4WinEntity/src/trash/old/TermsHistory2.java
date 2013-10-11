package trash.old;
//package com.bid4win.persistence.entity.auction.history;
//
//import javax.persistence.Access;
//import javax.persistence.AccessType;
//import javax.persistence.Embeddable;
//
//import com.bid4win.commons.core.UtilObject;
//import com.bid4win.commons.core.exception.UserException;
//import com.bid4win.commons.core.reference.MessageRef.AuctionRef;
//import com.bid4win.persistence.entity.auction.TermsAbstract;
//import com.bid4win.persistence.entity.auction.normal.NormalTerms;
//import com.bid4win.persistence.entity.auction.prebooked.PrebookedTerms;
//
///**
// * Cette classe défini les conditions d'une vente aux enchères historisée<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//@Embeddable
//@Access(AccessType.FIELD)
//public class TermsHistory extends TermsAbstract<TermsHistory>
//{
//  /**
//   * Constructeur pour création par introspection
//   */
//  protected TermsHistory()
//  {
//    super();
//  }
//  /**
//   * Constructeur
//   * @param terms TODO A COMMENTER
//   * @throws UserException TODO A COMMENTER
//   */
//  public TermsHistory(NormalTerms terms) throws UserException
//  {
//    super(UtilObject.checkNotNull("terms", terms,
//                                  AuctionRef.AUCTION_TERMS_MISSING_ERROR).getCreditNb());
//  }
//  /**
//   * Constructeur
//   * @param terms TODO A COMMENTER
//   * @throws UserException TODO A COMMENTER
//   */
//  public TermsHistory(PrebookedTerms terms) throws UserException
//  {
//    super(UtilObject.checkNotNull("terms", terms,
//                                  AuctionRef.AUCTION_TERMS_MISSING_ERROR).getCreditNb());
//  }
//
//  /**
//   *
//   * TODO A COMMENTER
//   * @return {@inheritDoc}
//   * @see com.bid4win.persistence.entity.auction.TermsAbstract#toHistory()
//   */
//  @Override
//  public TermsHistory toHistory()
//  {
//    return this;
//  }
//}
