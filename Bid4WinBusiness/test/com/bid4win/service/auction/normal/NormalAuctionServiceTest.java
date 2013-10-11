package com.bid4win.service.auction.normal;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.dao.account.credit.auction.CreditInvolvementNormalDaoStub;
import com.bid4win.persistence.dao.auction.normal.NormalAuctionDaoStub;
import com.bid4win.persistence.dao.auction.normal.NormalAuctionInitializer;
import com.bid4win.persistence.dao.auction.normal.NormalBidDaoStub;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementNormal;
import com.bid4win.persistence.entity.account.credit.auction.CreditUsageNormal;
import com.bid4win.persistence.entity.auction.normal.NormalAuction;
import com.bid4win.persistence.entity.auction.normal.NormalBid;
import com.bid4win.persistence.entity.auction.normal.NormalBidHistory;
import com.bid4win.persistence.entity.auction.normal.NormalBot;
import com.bid4win.persistence.entity.auction.normal.NormalCancelPolicy;
import com.bid4win.persistence.entity.auction.normal.NormalSchedule;
import com.bid4win.persistence.entity.auction.normal.NormalTerms;
import com.bid4win.service.auction.AuctionServiceTester;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
public class NormalAuctionServiceTest
       extends AuctionServiceTester<NormalAuction, NormalBid, NormalBidHistory,
                                    NormalSchedule, NormalTerms, NormalCancelPolicy,
                                    CreditInvolvementNormal, CreditUsageNormal, NormalBot>
{
  /** Référence du service à tester */
  @Autowired
  @Qualifier("NormalAuctionService")
  private NormalAuctionService service;
  /** Référence du DAO de test des ventes aux enchères normales */
  @Autowired
  @Qualifier("NormalAuctionDaoStub")
  private NormalAuctionDaoStub auctionDao;
  /** Référence du DAO de test des enchères */
  @Autowired
  @Qualifier("NormalBidDaoStub")
  private NormalBidDaoStub bidDao;
  /** Référence du DAO de test des implications de crédits sur des ventes aux enchères normales */
  @Autowired
  @Qualifier("CreditInvolvementNormalDaoStub")
  private CreditInvolvementNormalDaoStub involvementDao;
  /** Référence TODO */
  @Autowired
  @Qualifier("NormalAuctionInitializer")
  private NormalAuctionInitializer auctionInitializer;

  /**
   *
   * TODO A COMMENTER
   * @param accountIndex {@inheritDoc}
   * @param auction {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.service.auction.AuctionAbstractServiceTester#getInvolvement(int, com.bid4win.persistence.entity.auction.AuctionAbstract)
   */
  @Override
  protected CreditInvolvementNormal getInvolvement(int accountIndex, NormalAuction auction)
            throws Bid4WinException
  {
    return this.getAccount(accountIndex).getInvolvementNormal(auction);
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.service.auction.AuctionServiceTester#getService()
   */
  @Override
  public NormalAuctionService getService()
  {
    return this.service;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.service.auction.AuctionServiceTester#getDao()
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
   * @see com.bid4win.service.auction.AuctionServiceTester#getBidDao()
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
   * @see com.bid4win.service.auction.AuctionAbstractServiceTester#getInvolvementDao()
   */
  @Override
  protected CreditInvolvementNormalDaoStub getInvolvementDao()
  {
    return this.involvementDao;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.service.auction.AuctionServiceTester#getAuctionInitializer()
   */
  @Override
  protected NormalAuctionInitializer getAuctionInitializer()
  {
    return this.auctionInitializer;
  }
}
