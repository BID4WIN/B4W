package com.bid4win.persistence.dao.auction.normal;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.dao.auction.AuctionDaoTester;
import com.bid4win.persistence.entity.auction.normal.NormalAuction;
import com.bid4win.persistence.entity.auction.normal.NormalBid;
import com.bid4win.persistence.entity.auction.normal.NormalBidHistory;
import com.bid4win.persistence.entity.auction.normal.NormalCancelPolicy;
import com.bid4win.persistence.entity.auction.normal.NormalSchedule;
import com.bid4win.persistence.entity.auction.normal.NormalTerms;
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
public class NormalAuctionTest
       extends AuctionDaoTester<NormalAuction, NormalBid, NormalBidHistory,
                                NormalSchedule, NormalTerms, NormalCancelPolicy>
{
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("NormalAuctionDaoStub")
  private NormalAuctionDaoStub dao;
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("NormalBidDaoStub")
  private NormalBidDaoStub bidDao;

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionDaoTester#getDao()
   */
  @Override
  protected NormalAuctionDaoStub getDao()
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
  protected NormalBidDaoStub getBidDao()
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
  protected NormalBidHistoryDaoStub getBidHistoryDao()
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
  protected NormalAuction createAuction(Product product) throws Bid4WinException
  {
    return this.getGenerator().createNormalAuction(product);
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionAbstractDaoTester#createCancelPolicy()
   */
  @Override
  protected NormalCancelPolicy createCancelPolicy() throws Bid4WinException
  {
    return this.getGenerator().createNormalCancelPolicy();
  }
}
