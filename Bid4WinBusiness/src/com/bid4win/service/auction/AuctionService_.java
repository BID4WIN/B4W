package com.bid4win.service.auction;

import org.springframework.transaction.annotation.Transactional;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.Bid4WinEntityLoader;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthenticationException;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthorizationException;
import com.bid4win.commons.persistence.entity.account.security.exception.SessionException;
import com.bid4win.manager.auction.AuctionManager_;
import com.bid4win.persistence.entity.account.credit.CreditUsage;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementAuction;
import com.bid4win.persistence.entity.auction.Auction;
import com.bid4win.persistence.entity.auction.Bid;
import com.bid4win.persistence.entity.auction.BidHistory;
import com.bid4win.persistence.entity.auction.Bot;
import com.bid4win.persistence.entity.auction.CancelPolicy;
import com.bid4win.persistence.entity.auction.Schedule;
import com.bid4win.persistence.entity.auction.Terms;
import com.bid4win.persistence.entity.price.Amount;

/**
 * Manager de gestion des ventes aux enchères incluant la gestion des transactions
 * ainsi que celle des habilitations<BR>
 * <BR>
 * @param <AUCTION>  Définition de type de vente aux enchères géré par le manager<BR>
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
 * @param <SERVICE> Définition du service implémenté afin de pouvoir s'autoréférencer<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class AuctionService_<AUCTION extends Auction<AUCTION, BID, SCHEDULE, TERMS, CANCEL_POLICY>,
                                      BID extends Bid<BID, AUCTION, BID_HISTORY>,
                                      BID_HISTORY extends BidHistory<BID_HISTORY, AUCTION, BID>,
                                      SCHEDULE extends Schedule<SCHEDULE>,
                                      TERMS extends Terms<TERMS>,
                                      CANCEL_POLICY extends CancelPolicy<CANCEL_POLICY>,
                                      INVOLVEMENT extends CreditInvolvementAuction<INVOLVEMENT, ? extends CreditUsage<?, INVOLVEMENT>, AUCTION, ?>,
                                      BOT extends Bot<BOT, AUCTION, BID>,
                                      SERVICE extends AuctionService_<AUCTION, BID, BID_HISTORY, SCHEDULE, TERMS, CANCEL_POLICY, INVOLVEMENT, BOT, SERVICE>>
       extends AuctionAbstractService_<AUCTION, BID, BID_HISTORY, SCHEDULE, TERMS, CANCEL_POLICY, INVOLVEMENT, SERVICE>
{
  /**
   *
   * TODO A COMMENTER
   * @param auctionId TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException TODO A COMMENTER
   * @throws SessionException  TODO A COMMENTER
   * @throws AuthenticationException  TODO A COMMENTER
   * @throws AuthorizationException  TODO A COMMENTER
   */
  @Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
  public BOT findBot(String auctionId)
         throws PersistenceException, SessionException,
                AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkUserRole();
    // Recherche le robots d'enchères
    BOT bot = this.getManager().findBot(auctionId, this.getConnectedAccountId());
    return Bid4WinEntityLoader.getInstance().loadRelation(bot);
  }
  /**
   *
   * TODO A COMMENTER
   * @param auctionId TODO A COMMENTER
   * @param minBid TODO A COMMENTER
   * @param maxBid TODO A COMMENTER
   * @param maxBidNb TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException TODO A COMMENTER
   * @throws NotFoundEntityException TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   * @throws SessionException  TODO A COMMENTER
   * @throws AuthenticationException TODO A COMMENTER
   * @throws AuthorizationException TODO A COMMENTER
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public BOT createBot(String auctionId, Amount minBid, Amount maxBid, int maxBidNb)
         throws PersistenceException, NotFoundEntityException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkUserRole();
    // Crée le robots d'enchères
    return this.getManager().createBot(
        auctionId, this.getConnectedAccountId(), minBid, maxBid, maxBidNb);
  }
  /**
   *
   * TODO A COMMENTER
   * @param auctionId TODO A COMMENTER
   * @param minBid TODO A COMMENTER
   * @param maxBid TODO A COMMENTER
   * @param maxBidNb TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException TODO A COMMENTER
   * @throws NotFoundEntityException TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   * @throws SessionException  TODO A COMMENTER
   * @throws AuthenticationException TODO A COMMENTER
   * @throws AuthorizationException TODO A COMMENTER
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public BOT updateBot(String auctionId, Amount minBid, Amount maxBid, int maxBidNb)
         throws PersistenceException, NotFoundEntityException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkUserRole();
    // Modifie le robots d'enchères
    return this.getManager().updateBot(
        auctionId, this.getConnectedAccountId(), minBid, maxBid, maxBidNb);
  }
  /**
   *
   * TODO A COMMENTER
   * @param auctionId TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException TODO A COMMENTER
   * @throws NotFoundEntityException TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   * @throws SessionException  TODO A COMMENTER
   * @throws AuthenticationException TODO A COMMENTER
   * @throws AuthorizationException TODO A COMMENTER
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public BOT deleteBot(String auctionId)
         throws PersistenceException, NotFoundEntityException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkUserRole();
    // Supprime le robots d'enchères
    return this.getManager().deleteBot(auctionId, this.getConnectedAccountId());
  }

  /**
   * Permet de préciser la référence du manager de gestion des ventes aux enchères
   * @return {@inheritDoc}
   * @see com.bid4win.service.auction.AuctionAbstractService_#getManager()
   */
  @Override
  protected AuctionManager_<AUCTION, BID, BID_HISTORY, SCHEDULE, TERMS, CANCEL_POLICY, INVOLVEMENT, BOT> getManager()
  {
    return this.getInternal().getManager();
  }
  /**
   * Permet de préciser la référence du service interne de gestion des ventes aux
   * enchères
   * @return {@inheritDoc}
   * @see com.bid4win.service.auction.AuctionAbstractService_#getInternal()
   */
  @Override
  protected abstract AuctionInternalService_<AUCTION, BID, BID_HISTORY, SCHEDULE, TERMS, CANCEL_POLICY, INVOLVEMENT, BOT, ?, ?> getInternal();
}
