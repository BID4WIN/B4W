package com.bid4win.persistence.dao.auction;

import org.junit.After;
import org.junit.Before;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultiple;
import com.bid4win.persistence.dao.account.AccountBasedEntityMultipleDaoTester;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.auction.AuctionAbstract;
import com.bid4win.persistence.entity.auction.BidAbstract;
import com.bid4win.persistence.entity.auction.CancelPolicyAbstract;
import com.bid4win.persistence.entity.auction.ScheduleAbstract;
import com.bid4win.persistence.entity.auction.TermsAbstract;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <ENTITY> TODO A COMMENTER<BR>
 * @param <ID> TODO A COMMENTER<BR>
 * @param <AUCTION> TODO A COMMENTER<BR>
 * @param <BID> TODO A COMMENTER<BR>
 * @param <SCHEDULE> TODO A COMMENTER<BR>
 * @param <TERMS> TODO A COMMENTER<BR>
 * @param <CANCEL_POLICY> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class AuctionBasedTester<ENTITY extends AccountBasedEntityMultiple<ENTITY, ID, Account>, ID,
                                         AUCTION extends AuctionAbstract<AUCTION, BID, SCHEDULE, TERMS, CANCEL_POLICY>,
                                         BID extends BidAbstract<BID, AUCTION, ?>,
                                         SCHEDULE extends ScheduleAbstract<SCHEDULE>,
                                         TERMS extends TermsAbstract<TERMS>,
                                         CANCEL_POLICY extends CancelPolicyAbstract<CANCEL_POLICY>>
       extends AccountBasedEntityMultipleDaoTester<ENTITY, ID>
{
  /**
   *
   * TODO A COMMENTER
   * @param account {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityDaoTester#create(com.bid4win.commons.persistence.entity.account.AccountAbstract)
   */
  @Override
  protected ENTITY create(Account account) throws Bid4WinException
  {
    return this.create(account, this.getAuction(0));
  }
  /**
   *
   * TODO A COMMENTER
   * @param account TODO A COMMENTER
   * @param auction TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  protected abstract ENTITY create(Account account, AUCTION auction) throws Bid4WinException;

  /**
   *
   * TODO A COMMENTER
   * @param index TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public AUCTION getAuction(int index) throws Bid4WinException
  {
    return this.getAuctionInitializer().getEntity(index);
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected abstract AuctionAbstractInitializer_<AUCTION, BID, SCHEDULE, TERMS, CANCEL_POLICY> getAuctionInitializer();
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public int getSetupAuctionNb()
  {
    return 1;
  }
  /**
   * Test setup method
   * @throws Exception {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityDaoTester#setUp()
   */
  @Override
  @Before
  public void setUp() throws Exception
  {
    super.setUp();
    // Ajoute les ventes aux enchères utilisées pour les tests
    this.getAuctionInitializer().setUp(this.getSetupAuctionNb());
  }
  /**
   * Test teardown method
   * @throws Exception {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityDaoTester#tearDown()
   */
  @Override
  @After
  public void tearDown() throws Exception
  {
    super.tearDown();
    // Supprime les ventes aux enchères utilisées pour les tests
    this.getAuctionInitializer().tearDown();
  }
}
