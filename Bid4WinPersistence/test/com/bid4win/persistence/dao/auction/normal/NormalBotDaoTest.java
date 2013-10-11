package com.bid4win.persistence.dao.auction.normal;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.dao.auction.BotDaoTester;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.auction.normal.NormalAuction;
import com.bid4win.persistence.entity.auction.normal.NormalBid;
import com.bid4win.persistence.entity.auction.normal.NormalBidHistory;
import com.bid4win.persistence.entity.auction.normal.NormalBot;
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
public class NormalBotDaoTest
       extends BotDaoTester<NormalBot, NormalAuction, NormalBid, NormalBidHistory,
                            NormalSchedule, NormalTerms, NormalCancelPolicy>
{
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("NormalBotDaoStub")
  private NormalBotDaoStub dao;
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("NormalAuctionInitializer")
  private NormalAuctionInitializer auctionInitializer;

  /**
   *
   * TODO A COMMENTER
   * @param account {@inheritDoc}
   * @param auction {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionBasedTester#create(com.bid4win.persistence.entity.account.Account, com.bid4win.persistence.entity.auction.AuctionAbstract)
   */
  @Override
  protected NormalBot create(Account account, NormalAuction auction)
            throws Bid4WinException
  {
    return new NormalBot(account, auction, 1, 2, 3);
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
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.BotDaoTester#getDao()
   */
  @Override
  protected NormalBotDaoStub getDao()
  {
    return this.dao;
  }
}
