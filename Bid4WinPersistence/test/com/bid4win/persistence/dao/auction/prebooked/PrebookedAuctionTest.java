package com.bid4win.persistence.dao.auction.prebooked;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.dao.auction.AuctionDaoTester;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedAuction;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedBid;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedBidHistory;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedCancelPolicy;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedSchedule;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedTerms;
import com.bid4win.persistence.entity.product.Product;

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
public class PrebookedAuctionTest
       extends AuctionDaoTester<PrebookedAuction, PrebookedBid, PrebookedBidHistory,
                                PrebookedSchedule, PrebookedTerms, PrebookedCancelPolicy>
{
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("PrebookedAuctionDaoStub")
  private PrebookedAuctionDaoStub dao;
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("PrebookedBidDaoStub")
  private PrebookedBidDaoStub bidDao;

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionDaoTester#getDao()
   */
  @Override
  protected PrebookedAuctionDaoStub getDao()
  {
    return this.dao;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionDaoTester#getBidDao()
   */
  @Override
  protected PrebookedBidDaoStub getBidDao()
  {
    return this.bidDao;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionAbstractDaoTester#getBidHistoryDao()
   */
  @Override
  protected PrebookedBidHistoryDaoStub getBidHistoryDao()
  {
    return this.getBidDao().getHistoryDao();
  }

  /**
   *
   * TODO A COMMENTER
   * @param product {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionAbstractDaoTester#createAuction(com.bid4win.persistence.entity.product.Product)
   */
  @Override
  protected PrebookedAuction createAuction(Product product) throws Bid4WinException
  {
    return this.getGenerator().createPrebookedAuction(product);
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionAbstractDaoTester#createCancelPolicy()
   */
  @Override
  protected PrebookedCancelPolicy createCancelPolicy() throws Bid4WinException
  {
    return this.getGenerator().createPrebookedCancelPolicy();
  }
}
