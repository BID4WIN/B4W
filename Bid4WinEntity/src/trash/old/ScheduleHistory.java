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
//import com.bid4win.persistence.entity.auction.ScheduleAbstract;
//
///**
// * Cette classe d�fini les �l�ments de planification d'une vente aux ench�res historis�e<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
//@Embeddable
//@Access(AccessType.FIELD)
//public class ScheduleHistory extends ScheduleAbstract<ScheduleHistory>
//{
//  /**
//   * Constructeur pour cr�ation par introspection
//   */
//  protected ScheduleHistory()
//  {
//    super();
//  }
//  /**
//   * Constructeur
//   * @param auction TODO A COMMENTER
//   * @throws ModelArgumentException TODO A COMMENTER
//   */
//  public ScheduleHistory(AuctionAbstract<?, ?, ?, ?> auction) throws ModelArgumentException
//  {
//    super(UtilObject.checkNotNull("auction", auction).getSchedule().getOpeningDate());
//  }
//}
