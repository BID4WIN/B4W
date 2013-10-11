package com.bid4win.service.auction.normal;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.dao.auction.normal.NormalAuctionDaoStub;
import com.bid4win.persistence.dao.auction.normal.NormalAuctionInitializer;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementNormal;
import com.bid4win.persistence.entity.account.credit.auction.CreditUsageNormal;
import com.bid4win.persistence.entity.auction.normal.NormalAuction;
import com.bid4win.persistence.entity.auction.normal.NormalBid;
import com.bid4win.persistence.entity.auction.normal.NormalBidHistory;
import com.bid4win.persistence.entity.auction.normal.NormalBot;
import com.bid4win.persistence.entity.auction.normal.NormalCancelPolicy;
import com.bid4win.persistence.entity.auction.normal.NormalSchedule;
import com.bid4win.persistence.entity.auction.normal.NormalTerms;
import com.bid4win.service.auction.AuctionInternalServiceTester;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
public class NormalAuctionInternalServiceTest
       extends AuctionInternalServiceTester<NormalAuction, NormalBid, NormalBidHistory,
                                            NormalSchedule, NormalTerms, NormalCancelPolicy,
                                            CreditInvolvementNormal, CreditUsageNormal,
                                            NormalBot, NormalAuctionInternalHandler>
{
  /** Référence du service à tester */
  @Autowired
  @Qualifier("NormalAuctionInternalService")
  private NormalAuctionInternalService service;
  /** Référence du DAO de test des ventes aux enchères normales */
  @Autowired
  @Qualifier("NormalAuctionDaoStub")
  private NormalAuctionDaoStub auctionDao;
  /** Référence TODO */
  @Autowired
  @Qualifier("NormalAuctionInitializer")
  private NormalAuctionInitializer auctionInitializer;

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.service.auction.AuctionInternalServiceTester#getService()
   */
  @Override
  public NormalAuctionInternalService getService()
  {
    return this.service;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.service.auction.AuctionInternalServiceTester#getDao()
   */
  @Override
  protected NormalAuctionDaoStub getDao()
  {
    return this.auctionDao;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.service.auction.AuctionInternalServiceTester#getAuctionInitializer()
   */
  @Override
  protected NormalAuctionInitializer getAuctionInitializer()
  {
    return this.auctionInitializer;
  }
}
