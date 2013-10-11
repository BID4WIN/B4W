package com.bid4win.persistence.dao.auction.prebooked;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.dao.auction.BidDaoTester;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedAuction;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedBid;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedBidHistory;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedCancelPolicy;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedSchedule;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedTerms;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR><BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
@TransactionConfiguration(transactionManager = "Bid4WinTransactionManager")
public class PrebookedBidDaoTest
       extends BidDaoTester<PrebookedBid, PrebookedAuction, PrebookedBidHistory,
                            PrebookedSchedule, PrebookedTerms, PrebookedCancelPolicy>
{
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("PrebookedBidDaoStub")
  private PrebookedBidDaoStub dao;
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("PrebookedAuctionInitializer")
  private PrebookedAuctionInitializer auctionInitializer;

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.BidDaoTester#getDao()
   */
  @Override
  protected PrebookedBidDaoStub getDao()
  {
    return this.dao;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionBasedTester#getAuctionInitializer()
   */
  @Override
  protected PrebookedAuctionInitializer getAuctionInitializer()
  {
    return this.auctionInitializer;
  }
}
