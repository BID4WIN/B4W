//package com.bid4win.service.auction;
//
//import com.bid4win.commons.core.exception.Bid4WinException;
//import com.bid4win.manager.auction.AuctionAbstractManager_;
//import com.bid4win.persistence.dao.auction.AuctionInitializer_;
//import com.bid4win.persistence.entity.account.credit.CreditUsage;
//import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementAuction;
//import com.bid4win.persistence.entity.auction.AuctionAbstract;
//import com.bid4win.persistence.entity.auction.BidAbstract;
//import com.bid4win.persistence.entity.auction.BidHistory;
//import com.bid4win.persistence.entity.auction.CancelPolicyAbstract;
//import com.bid4win.persistence.entity.auction.ScheduleAbstract;
//import com.bid4win.persistence.entity.auction.TermsAbstract;
//
///**
// *
// * TODO A COMMENTER<BR>
// * <BR>
// * @param <AUCTION> TODO A COMMENTER
// * @param <BID> TODO A COMMENTER
// * @param <BID_HISTORY> TODO A COMMENTER
// * @param <SCHEDULE> TODO A COMMENTER
// * @param <TERMS> TODO A COMMENTER
// * @param <CANCEL_POLICY> TODO A COMMENTER
// * @param <INVOLVEMENT> TODO A COMMENTER
// * @param <USAGE> TODO A COMMENTER
// * @param <HANDLER> TODO A COMMENTER
// * @param <SERVICE> TODO A COMMENTER
// * <BR>
// * @author Emeric Fillâtre
// */
//public abstract class AuctionAbstractHandlerTester<AUCTION extends AuctionAbstract<AUCTION, BID, SCHEDULE, TERMS, CANCEL_POLICY>,
//                                                   BID extends BidAbstract<BID, AUCTION, BID_HISTORY>,
//                                                   BID_HISTORY extends BidHistory<BID_HISTORY, AUCTION, BID>,
//                                                   SCHEDULE extends ScheduleAbstract<SCHEDULE>,
//                                                   TERMS extends TermsAbstract<TERMS>,
//                                                   CANCEL_POLICY extends CancelPolicyAbstract<CANCEL_POLICY>,
//                                                   INVOLVEMENT extends CreditInvolvementAuction<INVOLVEMENT, USAGE, AUCTION, ?>,
//                                                   USAGE extends CreditUsage<USAGE, INVOLVEMENT>,
//                                                   HANDLER extends AuctionAbstractHandler<HANDLER, AUCTION, BID, BID_HISTORY, SCHEDULE,TERMS, INVOLVEMENT, SERVICE>,
//                                                   SERVICE extends AuctionAbstractService_<AUCTION, BID, BID_HISTORY, SCHEDULE, TERMS, INVOLVEMENT, HANDLER>>
//       extends Bid4WinServiceTest<AUCTION, String>
//{
//  /**
//   *
//   * TODO A COMMENTER
//   * @param auction TODO A COMMENTER
//   * @param service TODO A COMMENTER
//   * @return TODO A COMMENTER
//   */
//  protected abstract HANDLER createHandler(AUCTION auction, SERVICE service);
//
//  /**
//   *
//   * TODO A COMMENTER
//   * @param index TODO A COMMENTER
//   * @return TODO A COMMENTER
//   */
//  public String getAuctionId(int index)
//  {
//    return this.getAuctionInitializer().getId(index);
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @param index TODO A COMMENTER
//   * @return TODO A COMMENTER
//   * @throws Bid4WinException TODO A COMMENTER
//   */
//  public AUCTION getAuction(int index) throws Bid4WinException
//  {
//    return this.getAuctionInitializer().getEntity(index);
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @return TODO A COMMENTER
//   */
//  protected abstract AuctionInitializer_<AUCTION, BID, SCHEDULE, TERMS> getAuctionInitializer();
//  /**
//   *
//   * TODO A COMMENTER
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.service.Bid4WinServiceTest#getManager()
//   */
//  @Override
//  public abstract AuctionAbstractManager_<AUCTION, BID, BID_HISTORY, SCHEDULE, TERMS, INVOLVEMENT> getManager();
//  /**
//   *
//   * TODO A COMMENTER
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.service.Bid4WinServiceTest#getService()
//   */
//  @Override
//  public abstract SERVICE getService();
//
//  /**
//   *
//   * TODO A COMMENTER
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.manager.Bid4WinManagerTester#getSetupAccountNb()
//   */
//  @Override
//  public int getSetupAccountNb()
//  {
//    return 2;
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @return TODO A COMMENTER
//   */
//  public int getSetupAuctionNb()
//  {
//    return 2;
//  }
//}
