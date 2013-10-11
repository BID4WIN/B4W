package trash.old;
//package com.bid4win.persistence.entity.auction.old;
//
//import javax.persistence.Access;
//import javax.persistence.AccessType;
//import javax.persistence.Embeddable;
//
//import com.bid4win.commons.core.UtilObject;
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.persistence.entity.auction.AuctionAbstract;
//import com.bid4win.persistence.entity.auction.TermsAbstract;
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
//   * @param auction TODO A COMMENTER
//   * @throws ModelArgumentException TODO A COMMENTER
//   */
//  public TermsHistory(AuctionAbstract<?, ?, ?, ?> auction) throws ModelArgumentException
//  {
//    super(UtilObject.checkNotNull("auction", auction).getTerms().getCreditNb());
//  }
//}
