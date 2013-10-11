package com.bid4win.persistence.dao.auction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.comparator.Bid4WinEntitySetComparator;
import com.bid4win.persistence.dao.account.AccountBasedEntityMultipleDaoTester;
import com.bid4win.persistence.dao.product.ProductInitializer;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.auction.AuctionAbstract;
import com.bid4win.persistence.entity.auction.BidAbstract;
import com.bid4win.persistence.entity.auction.BidHistory;
import com.bid4win.persistence.entity.auction.CancelPolicyAbstract;
import com.bid4win.persistence.entity.auction.ScheduleAbstract;
import com.bid4win.persistence.entity.auction.Status;
import com.bid4win.persistence.entity.auction.TermsAbstract;
import com.bid4win.persistence.entity.product.Product;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <AUCTION> TODO A COMMENTER<BR>
 * @param <BID> TODO A COMMENTER<BR>
 * @param <HISTORY> TODO A COMMENTER<BR>
 * @param <SCHEDULE> TODO A COMMENTER<BR>
 * @param <TERMS> TODO A COMMENTER<BR>
 * @param <CANCEL_POLICY> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class AuctionAbstractDaoTester<AUCTION extends AuctionAbstract<AUCTION, BID, SCHEDULE, TERMS, CANCEL_POLICY>,
                                               BID extends BidAbstract<BID, AUCTION, HISTORY>,
                                               HISTORY extends BidHistory<HISTORY, AUCTION, BID>,
                                               SCHEDULE extends ScheduleAbstract<SCHEDULE>,
                                               TERMS extends TermsAbstract<TERMS>,
                                               CANCEL_POLICY extends CancelPolicyAbstract<CANCEL_POLICY>>
       extends AccountBasedEntityMultipleDaoTester<AUCTION, String>
{
  /** Référence de TODO A COMMENTER */
  @Autowired
  @Qualifier("ProductInitializer")
  private ProductInitializer productInitializer;

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  protected abstract CANCEL_POLICY createCancelPolicy() throws Bid4WinException;

  /**
   * Test of findListByStatus(Status...), of class AuctionAbstractDao_.
   * @throws Bid4WinException Issue not expected during this test
   */
  @SuppressWarnings("unchecked")
  @Test
  public void testfindListByStatus_Status_etc() throws Bid4WinException
  {
    AUCTION auction1 = this.add(this.createAuction());
    AUCTION auction2 = this.add(this.createAuction());
    Bid4WinSet<AUCTION> expected = new Bid4WinSet<AUCTION>(auction1, auction2);

    Bid4WinSet<AUCTION> result = new Bid4WinSet<AUCTION>(this.getDao().findListByStatus());
    assertEquals("Wrong result size", 2, result.size());
    assertTrue("Wrong result",
               Bid4WinEntitySetComparator.getInstanceEntitySet().identical(expected, result));

    auction1 = this.update(auction1.validate(this.createCancelPolicy(),
                                             this.getGenerator().createExchangeRates()));
    expected = new Bid4WinSet<AUCTION>(auction1, auction2);

    result = new Bid4WinSet<AUCTION>(this.getDao().findListByStatus());
    assertEquals("Wrong result size", 2, result.size());
    assertTrue("Wrong result",
               Bid4WinEntitySetComparator.getInstanceEntitySet().identical(expected, result));

    result = new Bid4WinSet<AUCTION>(this.getDao().findListByStatus(auction1.getStatus(),
                                                                    auction2.getStatus()));
    assertEquals("Wrong result size", 2, result.size());
    assertTrue("Wrong result",
               Bid4WinEntitySetComparator.getInstanceEntitySet().identical(expected, result));

    result = new Bid4WinSet<AUCTION>(this.getDao().findListByStatus(auction1.getStatus().getParent()));
    assertEquals("Wrong result size", 1, result.size());
    assertTrue("Wrong result", auction1.identical(result.iterator().next()));

    result = new Bid4WinSet<AUCTION>(this.getDao().findListByStatus(auction1.getStatus()));
    assertEquals("Wrong result size", 1, result.size());
    assertTrue("Wrong result", auction1.identical(result.iterator().next()));

    result = new Bid4WinSet<AUCTION>(this.getDao().findListByStatus(auction2.getStatus()));
    assertEquals("Wrong result size", 1, result.size());
    assertTrue("Wrong result", auction2.identical(result.iterator().next()));

    result = new Bid4WinSet<AUCTION>(this.getDao().findListByStatus(Status.STARTED));
    assertEquals("Wrong result size", 0, result.size());


  }
  /**
   * Test of update(AUCTION), of class AuctionAbstractDao_.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testUpdate_AUCTION() throws Bid4WinException
  {
    // Création de la situation de départ
    AUCTION auction = this.add(this.createAuction());

    AUCTION result = this.getById(auction.getId());
    assertTrue("Wrong auction", auction.identical(result));
    assertEquals("Wrong version", 0, result.getVersion());

    auction = this.update(auction.validate(this.createCancelPolicy(),
                                           this.getGenerator().createExchangeRates()));
    result = this.getById(auction.getId());
    assertTrue("Wrong auction", auction.identical(result));
    assertEquals("Wrong version", 1, result.getVersion());
  }
  /**
   * Test of historizeBids(AUCTION), of class AuctionAbstractDao_.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testHistorizeBids_AUCTION() throws Bid4WinException
  {
    // Création de la situation de départ
    AUCTION auction1 = this.add(this.createAuction().validate(
        this.createCancelPolicy(), this.getGenerator().createExchangeRates()).start());
    AUCTION auction2 = this.add(this.createAuction().validate(
        this.createCancelPolicy(), this.getGenerator().createExchangeRates()).start());
    for(int i = 0 ; i < 10 ; i++)
    {
      this.getBidDao().add(auction1.addBid(this.getAccount(i % 2), new Bid4WinDate()));
      auction1 = this.getById(auction1.getId());
    }
    for(int i = 0 ; i < 10 ; i++)
    {
      this.getBidDao().add(auction2.addBid(this.getAccount(i % 2), new Bid4WinDate()));
      auction2 = this.getById(auction2.getId());
    }
    Bid4WinList<BID> bidList1 = this.getBidDao().findListByAuction(auction1);
    Bid4WinList<BID> bidList2 = this.getBidDao().findListByAuction(auction2);
    assertEquals("Wrong bid nb", auction1.getBidNb(), bidList1.size());
    assertEquals("Wrong bid nb", auction2.getBidNb(), bidList2.size());

    // Tentative d'historisation d'une vente aux enchères non terminée
    try
    {
      this.getDao().historizeBids(auction1);
      fail("Should fail with not ended auction");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }

    // Historisation de la vente aux enchères
    this.getDao().historizeBids(auction1.close());
    AUCTION auction = this.getById(auction1.getId());
    assertTrue("Wrong auction", auction.same(auction1, new Bid4WinList<Bid4WinRelationNode>()));
    assertEquals("Wrong version", auction1.getVersion() + 1, auction.getVersion());
    assertTrue("Wrong result", auction.isBidHistorized());
    auction = this.getById(auction2.getId());
    assertTrue("Wrong auction", auction.identical(auction2));

    Bid4WinList<BID> bidList = this.getBidDao().findListByAuction(auction1);
    assertTrue("Bids should be deleted", bidList.isEmpty());
    bidList = this.getBidDao().findListByAuction(auction2);
    assertEquals("Wrong bid list", bidList2, bidList);

    Bid4WinList<HISTORY> historyList = this.getBidHistoryDao().findListByAuction(auction1);
    assertEquals("Wrong bid history list", bidList1.size(), historyList.size());
    for(BID bid : bidList1)
    {
      for(int i = 0 ; i < historyList.size() ; i++)
      {
        HISTORY history = historyList.get(i);
        if(history.isHistoryOf(bid))
        {
          historyList.remove(i);
          break;
        }
      }
    }
    assertTrue("Wrong bid history list", historyList.isEmpty());

    // Tentative d'historisation d'une vente aux enchères déjà historisée
    try
    {
      this.getDao().historizeBids(auction1);
      fail("Should fail with historized auction");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }

    // Tentative d'historisation d'une vente aux enchères non terminée
    try
    {
      this.getDao().historizeBids(auction2);
      fail("Should fail with not ended auction");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }

    // Historisation de la vente aux enchères
    this.getDao().historizeBids(auction2.cancel());
    auction = this.getById(auction1.getId());
    assertTrue("Wrong auction", auction.same(auction1, new Bid4WinList<Bid4WinRelationNode>()));
    assertEquals("Wrong version", auction1.getVersion() + 1, auction.getVersion());
    assertTrue("Wrong result", auction.isBidHistorized());
    auction = this.getById(auction2.getId());
    assertTrue("Wrong auction", auction.same(auction2, new Bid4WinList<Bid4WinRelationNode>()));
    assertEquals("Wrong version", auction2.getVersion() + 1, auction.getVersion());
    assertTrue("Wrong result", auction.isBidHistorized());

    bidList = this.getBidDao().findListByAuction(auction1);
    assertTrue("Bids should be deleted", bidList.isEmpty());
    bidList = this.getBidDao().findListByAuction(auction2);
    assertTrue("Bids should be deleted", bidList.isEmpty());

    historyList = this.getBidHistoryDao().findListByAuction(auction1);
    assertEquals("Wrong bid history list", bidList1.size(), historyList.size());
    for(BID bid : bidList1)
    {
      for(int i = 0 ; i < historyList.size() ; i++)
      {
        HISTORY history = historyList.get(i);
        if(history.isHistoryOf(bid))
        {
          historyList.remove(i);
          break;
        }
      }
    }
    assertTrue("Wrong bid history list", historyList.isEmpty());

    historyList = this.getBidHistoryDao().findListByAuction(auction2);
    assertEquals("Wrong bid history list", bidList2.size(), historyList.size());
    for(BID bid : bidList2)
    {
      for(int i = 0 ; i < historyList.size() ; i++)
      {
        HISTORY history = historyList.get(i);
        if(history.isHistoryOf(bid))
        {
          historyList.remove(i);
          break;
        }
      }
    }
    assertTrue("Wrong bid history list", historyList.isEmpty());
  }
  /**
   * Test of historizeInvovlvements(AUCTION), of class AuctionAbstractDao_.
   * @throws Bid4WinException Issue not expected during this test
   */
 /* @Test
  public void testHistorizeInvovlvements_AUCTION() throws Bid4WinException
  {
    // Création de la situation de départ
    AUCTION auction1 = this.add(this.createAuction());
    AUCTION auction2 = this.add(this.createAuction());
    Amount amount = new Amount(1.23);

  }*/

  /**
   *
   * TODO A COMMENTER
   * @param account {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityDaoTester#create(com.bid4win.commons.persistence.entity.account.AccountAbstract)
   */
  @Override
  protected AUCTION create(Account account) throws Bid4WinException
  {
    AUCTION auction = this.createAuction();
    auction.validate(this.createCancelPolicy(), this.getGenerator().createExchangeRates());
    auction.start().addBid(account, new Bid4WinDate());
    return auction.close();
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  protected AUCTION createAuction() throws Bid4WinException
  {
    return this.createAuction(this.getProduct(0));
  }
  /**
   *
   * TODO A COMMENTER
   * @param product TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  protected abstract AUCTION createAuction(Product product) throws Bid4WinException;

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDaoTester#getDao()
   */
  @Override
  protected abstract IAuctionAbstractDaoStub<AUCTION, BID, SCHEDULE, TERMS, CANCEL_POLICY> getDao();
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected abstract IBidAbstractDaoStub<BID, AUCTION, HISTORY> getBidDao();
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected abstract IBidAbstractDaoStub<HISTORY, AUCTION, HISTORY> getBidHistoryDao();

  /**
   *
   * TODO A COMMENTER
   * @param index TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public Product getProduct(int index) throws Bid4WinException
  {
    return this.getProductInitializer().getEntity(index);
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected ProductInitializer getProductInitializer()
  {
    return this.productInitializer;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public int getSetupProductNb()
  {
    return 1;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public boolean isAuctionUpdatedByBid()
  {
    return true;
  }
  /**
   * Test setup method
   * @throws Exception {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDaoTester#setUp()
   */
  @Override
  @Before
  public void setUp() throws Exception
  {
    super.setUp();
    // Ajoute les ventes aux enchères utilisées pour les tests
    this.getProductInitializer().setUp(this.getSetupProductNb());
  }
  /**
   * Test teardown method
   * @throws Exception {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDaoTester#tearDown()
   */
  @Override
  @After
  public void tearDown() throws Exception
  {
    super.tearDown();
    // Supprime les ventes aux enchères utilisées pour les tests
    this.getProductInitializer().tearDown();
  }
}
