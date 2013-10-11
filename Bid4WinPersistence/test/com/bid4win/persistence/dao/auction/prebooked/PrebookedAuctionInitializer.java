package com.bid4win.persistence.dao.auction.prebooked;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.persistence.dao.auction.AuctionInitializer_;
import com.bid4win.persistence.dao.auction.IBotDaoStub;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.auction.Bot;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedAuction;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedBid;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedBot;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedCancelPolicy;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedSchedule;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedTerms;
import com.bid4win.persistence.entity.product.Product;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("PrebookedAuctionInitializer")
@Scope("singleton")
public class PrebookedAuctionInitializer
       extends AuctionInitializer_<PrebookedAuction, PrebookedBid, PrebookedSchedule,
                                   PrebookedTerms, PrebookedCancelPolicy, PrebookedBot>
{
  /** Référence du DAO des ventes aux enchères avec pré-inscription */
  @Autowired
  @Qualifier("PrebookedAuctionDaoStub")
  private PrebookedAuctionDaoStub dao;
  /** Référence du DAO des enchères avec pré-inscription */
  @Autowired
  @Qualifier("PrebookedBidDaoStub")
  private PrebookedBidDaoStub bidDao;

  /**
   *
   * TODO A COMMENTER
   * @param product {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionInitializer_#createAuction(com.bid4win.persistence.entity.product.Product)
   */
  @Override
  protected PrebookedAuction createAuction(Product product) throws Bid4WinException
  {
    return this.getGenerator().createPrebookedAuction(product);
  }
  /**
   *
   * TODO A COMMENTER
   * @param product {@inheritDoc}
   * @param schedule {@inheritDoc}
   * @param terms {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionInitializer_#createAuction(com.bid4win.persistence.entity.product.Product, com.bid4win.persistence.entity.auction.ScheduleAbstract, com.bid4win.persistence.entity.auction.TermsAbstract)
   */
  @Override
  protected PrebookedAuction createAuction(Product product, PrebookedSchedule schedule, PrebookedTerms terms) throws Bid4WinException
  {
    return new PrebookedAuction(product, schedule, terms);
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionInitializer_#createCancelPolicy()
   */
  @Override
  protected PrebookedCancelPolicy createCancelPolicy() throws Bid4WinException
  {
    return this.getGenerator().createPrebookedCancelPolicy();
  }
  /**
   *
   * TODO A COMMENTER
   * @param auction {@inheritDoc}
   * @param account {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionInitializer_#createBot(com.bid4win.persistence.entity.auction.Auction, com.bid4win.persistence.entity.account.Account)
   */
  @Override
  protected PrebookedBot createBot(PrebookedAuction auction, Account account)
            throws UserException
  {
    return new PrebookedBot(account, auction, Bot.NO_MIN, Bot.NO_MAX, Bot.NO_MAX);
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4winTestInitializer_#getDao()
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
   * @see com.bid4win.persistence.dao.auction.AuctionInitializer_#getBidDao()
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
   * @see com.bid4win.persistence.dao.auction.AuctionInitializer_#getBotDao()
   */
  @Override
  protected IBotDaoStub<PrebookedBot, PrebookedAuction, PrebookedBid> getBotDao()
  {
    // TODO Auto-generated method stub
    return null;
  }
}
