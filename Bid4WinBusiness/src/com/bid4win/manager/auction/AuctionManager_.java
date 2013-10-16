package com.bid4win.manager.auction;

import org.springframework.transaction.annotation.Transactional;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.UtilObjectType;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.AccountRef;
import com.bid4win.commons.core.reference.MessageRef.AuctionRef;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.persistence.dao.auction.AuctionDao_;
import com.bid4win.persistence.dao.auction.BidDao_;
import com.bid4win.persistence.dao.auction.BotDao_;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.account.credit.CreditUsage;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementAuction;
import com.bid4win.persistence.entity.auction.Auction;
import com.bid4win.persistence.entity.auction.Bid;
import com.bid4win.persistence.entity.auction.BidHistory;
import com.bid4win.persistence.entity.auction.Bot;
import com.bid4win.persistence.entity.auction.BotListOptimizer;
import com.bid4win.persistence.entity.auction.CancelPolicy;
import com.bid4win.persistence.entity.auction.Schedule;
import com.bid4win.persistence.entity.auction.Status;
import com.bid4win.persistence.entity.auction.Terms;
import com.bid4win.persistence.entity.price.Amount;

/**
 * Manager interne de gestion des ventes aux enchères incluant leur gestion métier<BR>
 * <BR>
 * @param <AUCTION> Définition de type de vente aux enchères géré par le manager<BR>
 * @param <BID> Définition de type d'enchère des ventes gérées par le manager<BR>
 * @param <BID_HISTORY> Définition du type d'historique associée aux enchères<BR>
 * @param <SCHEDULE> Définition des éléments de planification des ventes aux enchères
 * gérées par le manager<BR>
 * @param <TERMS> Définition des conditions des ventes aux enchères gérées par le
 * manager<BR>
 * @param <CANCEL_POLICY> Définition de la politique d'annulation des ventes aux
 * enchères gérées par le manager<BR>
 * @param <INVOLVEMENT> Définition des implications de crédits sur les ventes aux
 * enchères gérées par le manager<BR>
 * @param <BOT> Définition des robots d'enchères sur les ventes gérées par le manager<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class AuctionManager_<AUCTION extends Auction<AUCTION, BID, SCHEDULE, TERMS, CANCEL_POLICY>,
                                      BID extends Bid<BID, AUCTION, BID_HISTORY>,
                                      BID_HISTORY extends BidHistory<BID_HISTORY, AUCTION, BID>,
                                      SCHEDULE extends Schedule<SCHEDULE>,
                                      TERMS extends Terms<TERMS>,
                                      CANCEL_POLICY extends CancelPolicy<CANCEL_POLICY>,
                                      INVOLVEMENT extends CreditInvolvementAuction<INVOLVEMENT, ? extends CreditUsage<?, INVOLVEMENT>, AUCTION, ?>,
                                      BOT extends Bot<BOT, AUCTION, BID>>
       extends AuctionAbstractManager_<AUCTION, BID, BID_HISTORY, SCHEDULE, TERMS, CANCEL_POLICY, INVOLVEMENT>
{
  /** TODO A COMMENTER */
  private BotListOptimizer<BOT> botListOptimizer = new BotListOptimizer<BOT>();

  /**
   *
   * TODO A COMMENTER
   * @param auctionId TODO A COMMENTER
   * @param accountId TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  public BOT findBot(String auctionId, String accountId) throws PersistenceException
  {
    return this.getBotDao().findOneByAuctionIdAndAccountId(auctionId, accountId);
  }
  /**
   *
   * TODO A COMMENTER
   * @param auctionId TODO A COMMENTER
   * @param accountId TODO A COMMENTER
   * @param minBid TODO A COMMENTER
   * @param maxBid TODO A COMMENTER
   * @param maxBidNb TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   * @throws NotFoundEntityException TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  public BOT createBot(String auctionId, String accountId,
                       Amount minBid, Amount maxBid, int maxBidNb)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    AUCTION auction = this.getAuctionDao().lockById(auctionId);
    UtilObjectType.checkBelongsTo("status", auction.getStatus(), Status.OPENED,
                                  AuctionRef.STATUS_NOT_STARTED_ERROR);
    Account account = this.getAccountManager().getById(accountId);
    BOT bot = this.createBotEntity(auction, account, minBid, maxBid, maxBidNb);
    return this.getBotDao().add(bot);
  }
  /**
   *
   * TODO A COMMENTER
   * @param auctionId TODO A COMMENTER
   * @param accountId TODO A COMMENTER
   * @param minBid TODO A COMMENTER
   * @param maxBid TODO A COMMENTER
   * @param maxBidNb TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   * @throws NotFoundEntityException TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  public BOT updateBot(String auctionId, String accountId,
                       Amount minBid, Amount maxBid, int maxBidNb)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    AUCTION auction = this.getAuctionDao().lockById(auctionId);
    UtilObjectType.checkBelongsTo("status", auction.getStatus(), Status.OPENED,
                                  AuctionRef.STATUS_NOT_STARTED_ERROR);
    BOT bot = this.getBotDao().getOneByAuctionIdAndAccountId(auctionId, accountId);
    UtilObject.checkEquals("auctionId", bot.getAuction().getId(), auctionId,
                           AuctionRef.AUCTION_INVALID_ERROR);
    UtilObject.checkEquals("accountId", bot.getAccount().getId(), accountId,
                           AccountRef.ACCOUNT_INVALID_ERROR);
    bot.defineBidRange(minBid, maxBid);
    bot.defineMaxBidNb(maxBidNb);
    return this.getBotDao().update(bot);
  }
  /**
   *
   * TODO A COMMENTER
   * @param auctionId TODO A COMMENTER
   * @param accountId TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   * @throws NotFoundEntityException TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  public BOT deleteBot(String auctionId, String accountId)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    AUCTION auction = this.getAuctionDao().lockById(auctionId);
    UtilObjectType.checkBelongsTo("status", auction.getStatus(), Status.OPENED,
                                  AuctionRef.STATUS_NOT_STARTED_ERROR);
    BOT bot = this.getBotDao().getOneByAuctionIdAndAccountId(auctionId, accountId);
    return this.getBotDao().remove(bot);
  }
  /**
   *
   * TODO A COMMENTER
   * @param auction TODO A COMMENTER
   * @param account TODO A COMMENTER
   * @param minBid TODO A COMMENTER
   * @param maxBid TODO A COMMENTER
   * @param maxBidNb TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  protected abstract BOT createBotEntity(AUCTION auction, Account account,
                                         Amount minBid, Amount maxBid, int maxBidNb)
            throws UserException;
  /**
   *
   * TODO A COMMENTER
   * @param auction {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.manager.auction.AuctionAbstractManager_#end(com.bid4win.persistence.entity.auction.AuctionAbstract)
   */
  @Override
  protected AUCTION end(AUCTION auction) throws PersistenceException, UserException
  {
    // La vente aux enchères est toujours en cours
    if(auction.getStatus().equals(Status.STARTED))
    {
      // Récupère la liste des robots potentiellement disponible sur la vente aux enchères
      Bid4WinList<BOT> botList = this.getBotDao().findNextBotList(auction);
      // Optimise la liste des robots pour une utilisation maximum des crédits et
      // essaye de placer une enchère automatique
      Bid4WinDate bidDate = new Bid4WinDate();
      for(BOT bot : botList.sort(this.getBotListOptimizer()))
      {
        try
        {
          return this.bid(auction, bot, bidDate);
        }
        catch(UserException ex)
        {
          // TODO LOG
          ex.printStackTrace();
          System.out.println(ex.getMessage());
        }
      }
    }
    // Aucun robot n'a donné satisfaction, on termine donc la vente aux enchères
    return super.end(auction);
  }
  /**
   *
   * TODO A COMMENTER
   * @param auction TODO A COMMENTER
   * @param bot TODO A COMMENTER
   * @param bidDate Date de positionnement de l'enchère
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   * @throws NotFoundEntityException TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  protected AUCTION bid(AUCTION auction, BOT bot, Bid4WinDate bidDate)
            throws PersistenceException, NotFoundEntityException, UserException
  {
    BID bid = this.bid(auction, bot.getAccount().getId(), bidDate).getLastBid();
    this.getBotDao().update(bot.autoBid(bid));
    return auction;
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected BotListOptimizer<BOT> getBotListOptimizer()
  {
    return this.botListOptimizer;
  }

  /**
   * Permet de préciser la référence du DAO des ventes aux enchères
   * @see com.bid4win.manager.auction.AuctionAbstractManager_#getAuctionDao()
   */
  @Override
  protected abstract AuctionDao_<AUCTION, BID, SCHEDULE, TERMS, CANCEL_POLICY, INVOLVEMENT> getAuctionDao();
  /**
   * Permet de préciser la référence du DAO des enchères
   * @return {@inheritDoc}
   * @see com.bid4win.manager.auction.AuctionAbstractManager_#getBidDao()
   */
  @Override
  protected abstract BidDao_<BID, AUCTION, BID_HISTORY> getBidDao();
  /**
   * Getter de la référence du DAO des robots d'enchères
   * @return La référence du DAO des robots d'enchères
   */
  protected abstract BotDao_<BOT, AUCTION, BID> getBotDao();
}
