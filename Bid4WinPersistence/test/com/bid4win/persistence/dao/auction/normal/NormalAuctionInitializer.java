package com.bid4win.persistence.dao.auction.normal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.persistence.dao.auction.AuctionInitializer_;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.auction.Bot;
import com.bid4win.persistence.entity.auction.normal.NormalAuction;
import com.bid4win.persistence.entity.auction.normal.NormalBid;
import com.bid4win.persistence.entity.auction.normal.NormalBot;
import com.bid4win.persistence.entity.auction.normal.NormalCancelPolicy;
import com.bid4win.persistence.entity.auction.normal.NormalSchedule;
import com.bid4win.persistence.entity.auction.normal.NormalTerms;
import com.bid4win.persistence.entity.product.Product;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("NormalAuctionInitializer")
@Scope("singleton")
public class NormalAuctionInitializer
       extends AuctionInitializer_<NormalAuction, NormalBid, NormalSchedule,
                                   NormalTerms, NormalCancelPolicy, NormalBot>
{
  /** Référence du DAO des ventes aux enchères normales */
  @Autowired
  @Qualifier("NormalAuctionDaoStub")
  private NormalAuctionDaoStub dao;
  /** Référence du DAO des enchères normales */
  @Autowired
  @Qualifier("NormalBidDaoStub")
  private NormalBidDaoStub bidDao;
  /** Référence du DAO des robots d'enchères normales */
  @Autowired
  @Qualifier("NormalBotDaoStub")
  private NormalBotDaoStub botDao;

  /**
   *
   * TODO A COMMENTER
   * @param product {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionInitializer_#createAuction(com.bid4win.persistence.entity.product.Product)
   */
  @Override
  protected NormalAuction createAuction(Product product) throws Bid4WinException
  {
    return this.getGenerator().createNormalAuction(product);
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
  protected NormalAuction createAuction(Product product, NormalSchedule schedule, NormalTerms terms) throws Bid4WinException
  {
    return new NormalAuction(product, schedule, terms);
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionInitializer_#createCancelPolicy()
   */
  @Override
  protected NormalCancelPolicy createCancelPolicy() throws Bid4WinException
  {
    return this.getGenerator().createNormalCancelPolicy();
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
  protected NormalBot createBot(NormalAuction auction, Account account)
            throws UserException
  {
    return new NormalBot(account, auction, Bot.NO_MIN, Bot.NO_MAX, Bot.NO_MAX);
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4winTestInitializer_#getDao()
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
   * @see com.bid4win.persistence.dao.auction.AuctionInitializer_#getBidDao()
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
   * @see com.bid4win.persistence.dao.auction.AuctionInitializer_#getBotDao()
   */
  @Override
  protected NormalBotDaoStub getBotDao()
  {
    return this.botDao;
  }
}
