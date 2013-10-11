package com.bid4win.persistence.dao.auction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultiple;
import com.bid4win.commons.persistence.entity.comparator.Bid4WinEntitySetComparator;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.auction.AuctionAbstract;
import com.bid4win.persistence.entity.auction.BidAbstract;
import com.bid4win.persistence.entity.auction.BidHistory;
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
 * @param <HISTORY> TODO A COMMENTER<BR>
 * @param <SCHEDULE> TODO A COMMENTER<BR>
 * @param <TERMS> TODO A COMMENTER<BR>
 * @param <CANCEL_POLICY> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class AuctionBasedEntityDaoTester<ENTITY extends AccountBasedEntityMultiple<ENTITY, ID, Account>, ID,
                                                  AUCTION extends AuctionAbstract<AUCTION, BID, SCHEDULE, TERMS, CANCEL_POLICY>,
                                                  BID extends BidAbstract<BID, AUCTION, HISTORY>,
                                                  HISTORY extends BidHistory<HISTORY, AUCTION, BID>,
                                                  SCHEDULE extends ScheduleAbstract<SCHEDULE>,
                                                  TERMS extends TermsAbstract<TERMS>,
                                                  CANCEL_POLICY extends CancelPolicyAbstract<CANCEL_POLICY>>
       extends AuctionBasedTester<ENTITY, ID, AUCTION, BID, SCHEDULE, TERMS, CANCEL_POLICY>
{
  /**
   * Test of findListByAuction(AUCTION), of class ???.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testFindListByAuction_AUCTION() throws Bid4WinException
  {
    this.testFindListForAuction(false);
  }
  /**
   * Test of findListByAuctionId(String), of class ???.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testFindListByAuctionId_String() throws Bid4WinException
  {
    this.testFindListForAuction(true);
  }
  /**
   *
   * TODO A COMMENTER
   * @param byId TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  protected void testFindListForAuction(boolean byId) throws Bid4WinException
  {
    Account account1 = this.getAccount(0);
    Account account2 = this.getAccount(1);
    AUCTION auction1 = this.getAuction(0);
    AUCTION auction2 = this.getAuction(1);
    try
    {
      Bid4WinSet<ENTITY> stubSet1 = new Bid4WinSet<ENTITY>();
      Bid4WinSet<ENTITY> stubSet2 = new Bid4WinSet<ENTITY>();
      Bid4WinSet<ENTITY> result = this.findList(auction1, byId);
      assertTrue("Should be empty", result.isEmpty());
      result = this.findList(auction2, byId);
      assertTrue("Should be empty", result.isEmpty());

      ENTITY stub1 = this.create(account1, auction1);
      this.add(stub1);
      stubSet1.add(stub1);
      result = this.findList(auction1, byId);
      assertTrue("Wrong result",
                 Bid4WinEntitySetComparator.getInstanceEntitySet().identical(
                      stubSet1, result, new Bid4WinList<Bid4WinRelationNode>()));
      assertTrue("Wrong result",
                 Bid4WinEntitySetComparator.getInstanceEntitySet().same(
                     stubSet1, result,
                     new Bid4WinList<Bid4WinRelationNode>(this.getAuctionRelation())));
      result = this.findList(auction2, byId);
      assertTrue("Should be empty", result.isEmpty());

      account1 = this.getAccount(0);
      ENTITY stub2 = this.create(account1, auction2);
      this.add(stub2);
      stubSet2.add(stub2);
      result = this.findList(auction1, byId);
      assertTrue("Wrong result",
                 Bid4WinEntitySetComparator.getInstanceEntitySet().identical(
                     stubSet1, result, new Bid4WinList<Bid4WinRelationNode>()));
      assertTrue("Wrong result",
                 Bid4WinEntitySetComparator.getInstanceEntitySet().same(
                     stubSet1, result,
                     new Bid4WinList<Bid4WinRelationNode>(this.getAuctionRelation())));
      result = this.findList(auction2, byId);
      assertTrue("Wrong result",
                 Bid4WinEntitySetComparator.getInstanceEntitySet().identical(
                     stubSet2, result, new Bid4WinList<Bid4WinRelationNode>()));
      assertTrue("Wrong result",
                 Bid4WinEntitySetComparator.getInstanceEntitySet().same(
                     stubSet2, result,
                     new Bid4WinList<Bid4WinRelationNode>(this.getAuctionRelation())));

      auction1 = this.getAuction(0);
      ENTITY stub3 = this.create(account2, auction1);
      this.add(stub3);
      stubSet1.add(stub3);
      result = this.findList(auction1, byId);
      assertTrue("Wrong result",
                 Bid4WinEntitySetComparator.getInstanceEntitySet().identical(
                     stubSet1, result, new Bid4WinList<Bid4WinRelationNode>()));
      if(!this.isAuctionUpdatedAfterAdd())
      {
        assertTrue("Wrong result",
                   Bid4WinEntitySetComparator.getInstanceEntitySet().same(
                       stubSet1, result,
                       new Bid4WinList<Bid4WinRelationNode>(this.getAuctionRelation())));
      }
      result = this.findList(auction2, byId);
      assertTrue("Wrong result",
                 Bid4WinEntitySetComparator.getInstanceEntitySet().identical(
                     stubSet2, result, new Bid4WinList<Bid4WinRelationNode>()));
      assertTrue("Wrong result",
                 Bid4WinEntitySetComparator.getInstanceEntitySet().same(
                     stubSet2, result,
                     new Bid4WinList<Bid4WinRelationNode>(this.getAuctionRelation())));

      if(!this.isAuctionUpdatedAfterAdd())
      {
        AUCTION auction = this.getAuction(0);
        assertTrue("Wrong account", auction.same(auction1));
        auction = this.getAuction(1);
        assertTrue("Wrong account", auction.same(auction2));
      }
      else
      {
        AUCTION auction = this.getAuction(0);
        assertTrue("Wrong auction", auction.same(auction1));
        assertEquals("Wrong account version", auction1.getVersion() + 1, auction.getVersion());
        auction = this.getAuction(1);
        assertTrue("Wrong account", auction.same(auction2));
        assertEquals("Wrong auction version", auction2.getVersion() + 1, auction.getVersion());
      }
    }
    catch(Bid4WinException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
  }
  /**
   * Test of add(ENTITY), of class AccountBasedEntityDao.
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityMultipleDaoTester#testAdd_ENTITY()
   */
  @Override
  @Test
  public void testAdd_ENTITY() throws Bid4WinException
  {
    super.testAdd_ENTITY();
    Bid4WinList<ENTITY> list = this.findAll();
    Bid4WinMap<String, Integer> map = new Bid4WinMap<String, Integer>();
    for(ENTITY entity : list)
    {
      String id = this.getAuction(entity).getId();
      Integer nb = map.get(id);
      if(nb == null)
      {
        map.put(id, 1);
      }
      else
      {
        map.put(id, nb + 1);
      }
    }
    if(map.size() == 1)
    {
      list.add(this.add(this.create(this.getAccount(0), this.getAuction(1))));
      map.put(this.getAuction(list.getLast()).getId(), 1);
    }
    AUCTION auction = this.getAuction(0);
    assertEquals("Wrong auction version",
                 (this.isAuctionUpdatedAfterAdd() ? map.get(auction.getId()) : 0),
                  auction.getVersion());
    auction = this.getAuction(1);
    assertEquals("Wrong auction version",
                 (this.isAuctionUpdatedAfterAdd() ? map.get(auction.getId()) : 0),
                  auction.getVersion());
  }

  /**
   *
   * TODO A COMMENTER
   * @param auction TODO A COMMENTER
   * @param byId TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  protected Bid4WinSet<ENTITY> findList(AUCTION auction, boolean byId) throws PersistenceException
   {
    if(byId)
    {
      return this.findListByAuctionId(auction.getId());
    }
    return this.findListByAuction(auction);
  }
  /**
   *
   * TODO A COMMENTER
   * @param auction TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  protected abstract Bid4WinSet<ENTITY> findListByAuction(AUCTION auction) throws PersistenceException;
  /**
   *
   * TODO A COMMENTER
   * @param auctionId TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  protected abstract Bid4WinSet<ENTITY> findListByAuctionId(String auctionId) throws PersistenceException;

  /**
   *
   * TODO A COMMENTER
   * @param entity TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected abstract AUCTION getAuction(ENTITY entity);
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected abstract Bid4WinRelationNode getAuctionRelation();
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public boolean isAuctionUpdatedAfterAdd()
  {
    return false;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionBasedTester#getSetupAuctionNb()
   */
  @Override
  public int getSetupAuctionNb()
  {
    return 2;
  }
}
