package com.bid4win.persistence.dao.auction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.comparator.Bid4WinEntitySetComparator;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.auction.Auction;
import com.bid4win.persistence.entity.auction.Bid;
import com.bid4win.persistence.entity.auction.BidHistory;
import com.bid4win.persistence.entity.auction.Bot;
import com.bid4win.persistence.entity.auction.Bot_Relations;
import com.bid4win.persistence.entity.auction.CancelPolicy;
import com.bid4win.persistence.entity.auction.Schedule;
import com.bid4win.persistence.entity.auction.Terms;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <BOT> TODO A COMMENTER<BR>
 * @param <AUCTION> TODO A COMMENTER<BR>
 * @param <BID> TODO A COMMENTER<BR>
 * @param <HISTORY> TODO A COMMENTER<BR>
 * @param <SCHEDULE> TODO A COMMENTER<BR>
 * @param <TERMS> TODO A COMMENTER<BR>
 * @param <CANCEL_POLICY> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class BotDaoTester<BOT extends Bot<BOT, AUCTION, BID>,
                                   AUCTION extends Auction<AUCTION, BID, SCHEDULE, TERMS, CANCEL_POLICY>,
                                   BID extends Bid<BID, AUCTION, HISTORY>,
                                   HISTORY extends BidHistory<HISTORY, AUCTION, BID>,
                                   SCHEDULE extends Schedule<SCHEDULE>,
                                   TERMS extends Terms<TERMS>,
                                   CANCEL_POLICY extends CancelPolicy<CANCEL_POLICY>>
       extends AuctionBasedEntityDaoTester<BOT, Long, AUCTION, BID, HISTORY, SCHEDULE, TERMS, CANCEL_POLICY>
{
  /**
   * Test of add(ENTITY), of class BotDao.
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionBasedEntityDaoTester#testAdd_ENTITY()
   */
  @Override
  @Test
  public void testAdd_ENTITY() throws Bid4WinException
  {
    super.testAdd_ENTITY();
    Bid4WinList<BOT> botList = this.getDao().findAll();
    try
    {
      this.add(this.create(this.getAccount(0), this.getAuction(0)));
      fail("Should fail with already defined bot");
    }
    catch(Throwable th)
    {
      System.out.println(th.getMessage());
      assertEquals("Wrong bot nb", botList.size(), this.getDao().findAll().size());
    }
  }
  /**
   * Test of findNextBotList(AUCTION), of class BotDao.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testFindNextBotList_AUCTION() throws Bid4WinException
  {
    Account account1 = this.getAccount(0);
    Account account2 = this.getAccount(1);
    Account account3 = this.getAccount(2);
    AUCTION auction1 = this.getAuctionInitializer().startAuction(0);
    AUCTION auction2 = this.getAuctionInitializer().startAuction(1);

    Bid4WinSet<BOT> botSet = new Bid4WinSet<BOT>();

    // Aucun robot défini
    Bid4WinList<BOT> result = this.getDao().findNextBotList(auction1);
    assertTrue("Wrong result", result.isEmpty());
    result = this.getDao().findNextBotList(auction2);
    assertTrue("Wrong result", result.isEmpty());

    // Pas assez de crédits sur les comptes utilisateurs des robots
    BOT bot1_1 = this.add(this.create(account1, auction1));
    bot1_1.defineBidRange(Bot.NO_MIN, Bot.NO_MAX);
    bot1_1.defineMaxBidNb(Bot.NO_MAX);
    bot1_1 = this.update(bot1_1);
    BOT bot2_1 = this.add(this.create(account2, auction1));
    bot2_1.defineBidRange(Bot.NO_MIN, Bot.NO_MAX);
    bot2_1.defineMaxBidNb(Bot.NO_MAX);
    bot2_1 = this.update(bot2_1);
    this.getAccountInitializer().addBundle(0, auction1.getTerms().getCreditNbPerBid() - 1);
    this.getAccountInitializer().addBundle(1, auction1.getTerms().getCreditNbPerBid() - 1);

    result = this.getDao().findNextBotList(auction1);
    assertTrue("Wrong result", result.isEmpty());
    result = this.getDao().findNextBotList(auction2);
    assertTrue("Wrong result", result.isEmpty());

    // Pas assez de crédits sur l'un des comptes utilisateurs des robots
    this.getAccountInitializer().addBundle(0, 1);
    botSet.add(bot1_1);
    result = this.getDao().findNextBotList(auction1);
    assertTrue("Wrong result",
               Bid4WinEntitySetComparator.getInstanceEntitySet().identical(
                   botSet, new Bid4WinSet<BOT>(result), new Bid4WinList<Bid4WinRelationNode>()));
    result = this.getDao().findNextBotList(auction2);
    assertTrue("Wrong result", result.isEmpty());

    // Tout bon
    this.getAccountInitializer().addBundle(1, 2);
    botSet.add(bot2_1);
    result = this.getDao().findNextBotList(auction1);
    assertTrue("Wrong result",
               Bid4WinEntitySetComparator.getInstanceEntitySet().identical(
                   botSet, new Bid4WinSet<BOT>(result), new Bid4WinList<Bid4WinRelationNode>()));
    result = this.getDao().findNextBotList(auction2);
    assertTrue("Wrong result", result.isEmpty());

    // En dehors du range minimum pour un robot
    botSet.remove(bot2_1);
    bot2_1.defineBidRange(2, 4);
    bot2_1 = this.update(bot2_1);
    result = this.getDao().findNextBotList(auction1);
    assertTrue("Wrong result",
               Bid4WinEntitySetComparator.getInstanceEntitySet().identical(
                   botSet, new Bid4WinSet<BOT>(result), new Bid4WinList<Bid4WinRelationNode>()));
    result = this.getDao().findNextBotList(auction2);
    assertTrue("Wrong result", result.isEmpty());

    // Tout bon
    BID bid = this.getAuctionInitializer().addBid(0, account3);
    auction1 = bid.getAuction();
    botSet.add(bot2_1);
    result = this.getDao().findNextBotList(auction1);
    assertTrue("Wrong result",
               Bid4WinEntitySetComparator.getInstanceEntitySet().identical(
                   botSet, new Bid4WinSet<BOT>(result), new Bid4WinList<Bid4WinRelationNode>()));
    result = this.getDao().findNextBotList(auction2);
    assertTrue("Wrong result", result.isEmpty());

    // Dernier enchérisseur pour un des robots
    bid = this.getAuctionInitializer().addBid(0, account1);
    auction1 = bid.getAuction();
    botSet.remove(bot1_1);
    bot1_1.autoBid(bid);
    bot1_1 = this.update(bot1_1);
    result = this.getDao().findNextBotList(auction1);
    assertTrue("Wrong result",
               Bid4WinEntitySetComparator.getInstanceEntitySet().identical(
                   botSet, new Bid4WinSet<BOT>(result), new Bid4WinList<Bid4WinRelationNode>()));
    result = this.getDao().findNextBotList(auction2);
    assertTrue("Wrong result", result.isEmpty());

    // Tout bon
    bid = this.getAuctionInitializer().addBid(0, account3);
    auction1 = bid.getAuction();
    botSet.add(bot1_1);
    result = this.getDao().findNextBotList(auction1);
    assertTrue("Wrong result",
               Bid4WinEntitySetComparator.getInstanceEntitySet().identical(
                   botSet, new Bid4WinSet<BOT>(result), new Bid4WinList<Bid4WinRelationNode>()));
    result = this.getDao().findNextBotList(auction2);
    assertTrue("Wrong result", result.isEmpty());

    // Dernier enchérisseur pour un des robots et en dehors du range maximum pour l'autre
    bid = this.getAuctionInitializer().addBid(0, account1);
    auction1 = bid.getAuction();
    botSet.remove(bot1_1);
    botSet.remove(bot2_1);
    bot1_1.autoBid(bid);
    bot1_1 = this.update(bot1_1);
    result = this.getDao().findNextBotList(auction1);
    assertTrue("Wrong result", result.isEmpty());
    result = this.getDao().findNextBotList(auction2);
    assertTrue("Wrong result", result.isEmpty());

    // En dehors du range maximum pour un des robots
    bid = this.getAuctionInitializer().addBid(0, account3);
    auction1 = bid.getAuction();
    botSet.add(bot1_1);
    result = this.getDao().findNextBotList(auction1);
    assertTrue("Wrong result",
               Bid4WinEntitySetComparator.getInstanceEntitySet().identical(
                   botSet, new Bid4WinSet<BOT>(result), new Bid4WinList<Bid4WinRelationNode>()));
    result = this.getDao().findNextBotList(auction2);
    assertTrue("Wrong result", result.isEmpty());

    // En dehors du range maximum pour un des robots et nombre de crédits maximum utilisable pour l'autre
    botSet.remove(bot1_1);
    bot1_1.defineMaxBidNb(bot1_1.getUsedBidNb());
    bot1_1 = this.update(bot1_1);
    result = this.getDao().findNextBotList(auction1);
    assertTrue("Wrong result", result.isEmpty());
    result = this.getDao().findNextBotList(auction2);
    assertTrue("Wrong result", result.isEmpty());
  }


  /**
   *
   * TODO A COMMENTER
   * @param auction {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionBasedEntityDaoTester#findListByAuction(com.bid4win.persistence.entity.auction.AuctionAbstract)
   */
  @Override
  protected Bid4WinSet<BOT> findListByAuction(AUCTION auction) throws PersistenceException
  {
    return new Bid4WinSet<BOT>(this.getDao().findListByAuction(auction));
  }
  /**
   *
   * TODO A COMMENTER
   * @param auctionId {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionBasedEntityDaoTester#findListByAuctionId(java.lang.String)
   */
  @Override
  protected Bid4WinSet<BOT> findListByAuctionId(String auctionId) throws PersistenceException
  {
    return new Bid4WinSet<BOT>(this.getDao().findListByAuctionId(auctionId));
  }

  /**
   *
   * TODO A COMMENTER
   * @param account {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionBasedTester#create(com.bid4win.persistence.entity.account.Account)
   */
  @Override
  protected BOT create(Account account) throws Bid4WinException
  {
    if(this.getDao().findOneByAuctionIdAndAccountId(this.getAuctionInitializer().getId(0), account.getId()) != null)
    {
      return this.create(account, this.getAuction(1));
    }
    return super.create(account);
  }

  /**
   *
   * TODO A COMMENTER
   * @param entity {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionBasedEntityDaoTester#getAuction(com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultiple)
   */
  @Override
  protected AUCTION getAuction(BOT entity)
  {
    return entity.getAuction();
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionBasedEntityDaoTester#getAuctionRelation()
   */
  @Override
  protected Bid4WinRelationNode getAuctionRelation()
  {
    return Bot_Relations.NODE_AUCTION;
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityDaoTester#getSetupAccountNb()
   */
  @Override
  public int getSetupAccountNb()
  {
    return 3;
  }

 /**
  *
  * TODO A COMMENTER
  * @return {@inheritDoc}
  * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityMultipleDaoTester#getDao()
  */
 @Override
 protected abstract IBotDaoStub<BOT, AUCTION, BID> getDao();
}
