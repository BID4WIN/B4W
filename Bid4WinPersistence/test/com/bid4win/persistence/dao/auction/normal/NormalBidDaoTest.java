package com.bid4win.persistence.dao.auction.normal;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.dao.auction.BidDaoTester;
import com.bid4win.persistence.entity.auction.normal.NormalAuction;
import com.bid4win.persistence.entity.auction.normal.NormalBid;
import com.bid4win.persistence.entity.auction.normal.NormalBidHistory;
import com.bid4win.persistence.entity.auction.normal.NormalCancelPolicy;
import com.bid4win.persistence.entity.auction.normal.NormalSchedule;
import com.bid4win.persistence.entity.auction.normal.NormalTerms;

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
public class NormalBidDaoTest
       extends BidDaoTester<NormalBid, NormalAuction, NormalBidHistory,
                            NormalSchedule, NormalTerms, NormalCancelPolicy>
{
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("NormalBidDaoStub")
  private NormalBidDaoStub dao;
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("NormalAuctionInitializer")
  private NormalAuctionInitializer auctionInitializer;

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.BidDaoTester#getDao()
   */
  @Override
  protected NormalBidDaoStub getDao()
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
  protected NormalAuctionInitializer getAuctionInitializer()
  {
    return this.auctionInitializer;
  }
}
