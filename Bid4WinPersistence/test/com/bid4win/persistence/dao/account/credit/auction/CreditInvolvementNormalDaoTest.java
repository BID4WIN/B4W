package com.bid4win.persistence.dao.account.credit.auction;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.dao.auction.normal.NormalAuctionInitializer;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementNormal;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementNormalHistory;
import com.bid4win.persistence.entity.account.credit.auction.CreditUsageNormal;
import com.bid4win.persistence.entity.auction.normal.NormalAuction;
import com.bid4win.persistence.entity.auction.normal.NormalBid;
import com.bid4win.persistence.entity.auction.normal.NormalBidHistory;
import com.bid4win.persistence.entity.auction.normal.NormalCancelPolicy;
import com.bid4win.persistence.entity.auction.normal.NormalSchedule;
import com.bid4win.persistence.entity.auction.normal.NormalTerms;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
@TransactionConfiguration(transactionManager = "Bid4WinTransactionManager")
public class CreditInvolvementNormalDaoTest
       extends CreditInvolvementAuctionDaoTester<CreditInvolvementNormal, CreditUsageNormal,
                                                 CreditInvolvementNormalHistory,
                                                 NormalAuction, NormalBid, NormalBidHistory,
                                                 NormalSchedule, NormalTerms, NormalCancelPolicy>
{
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("CreditInvolvementNormalDaoStub")
  private CreditInvolvementNormalDaoStub dao;
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
  protected CreditInvolvementNormal create(Account account, NormalAuction auction) throws Bid4WinException
  {
    return new CreditInvolvementNormal(account, auction);
  }
  /**
   *
   * TODO A COMMENTER
   * @param account {@inheritDoc}
   * @param auction {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.account.credit.auction.CreditInvolvementAuctionDaoTester#getInvolvement(com.bid4win.persistence.entity.account.Account, com.bid4win.persistence.entity.auction.AuctionAbstract)
   */
  @Override
  protected CreditInvolvementNormal getInvolvement(Account account, NormalAuction auction)
  {
    return account.getInvolvementNormal(auction);
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.account.credit.auction.CreditInvolvementAuctionDaoTester#getDao()
   */
  @Override
  protected CreditInvolvementNormalDaoStub getDao()
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
