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
// * Cette classe d�fini les conditions d'une vente aux ench�res historis�e<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
//@Embeddable
//@Access(AccessType.FIELD)
//public class TermsHistory extends TermsAbstract<TermsHistory>
//{
//  /**
//   * Constructeur pour cr�ation par introspection
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
