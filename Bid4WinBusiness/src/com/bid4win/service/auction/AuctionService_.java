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
 * Manager de gestion des ventes aux ench�res incluant la gestion des transactions
 * ainsi que celle des habilitations<BR>
 * <BR>
 * @param <AUCTION>  D�finition de type de vente aux ench�res g�r� par le manager<BR>
 * @param <BID> D�finition de type d'ench�re des ventes g�r�es par le manager<BR>
 * @param <BID_HISTORY> D�finition du type d'historique associ�e aux ench�res<BR>
 * @param <SCHEDULE> D�finition des �l�ments de planification des ventes aux ench�res
 * g�r�es par le manager<BR>
 * @param <TERMS> D�finition des conditions des ventes aux ench�res g�r�es par le
 * manager<BR>
 * @param <CANCEL_POLICY> D�finition de la politique d'annulation des ventes aux
 * ench�res g�r�es par le manager<BR>
 * @param <INVOLVEMENT> D�finition des implications de cr�dits sur les ventes aux
 * ench�res g�r�es par le manager<BR>
 * @param <BOT> D�finition des robots d'ench�res sur les ventes g�r�es par le manager<BR>
 * @param <SERVICE> D�finition du service impl�ment� afin de pouvoir s'autor�f�rencer<BR>
 * <BR>
 * @author Emeric Fill�tre
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
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
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
    // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
    this.checkUserRole();
    // Recherche le robots d'ench�res
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
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
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
    // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
    this.checkUserRole();
    // Cr�e le robots d'ench�res
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
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
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
    // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
    this.checkUserRole();
    // Modifie le robots d'ench�res
    return this.getManager().updateBot(
        auctionId, this.getConnectedAccountId(), minBid, maxBid, maxBidNb);
  }
  /**
   *
   * TODO A COMMENTER
   * @param auctionId TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
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
    // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
    this.checkUserRole();
    // Supprime le robots d'ench�res
    return this.getManager().deleteBot(auctionId, this.getConnectedAccountId());
  }

  /**
   * Permet de pr�ciser la r�f�rence du manager de gestion des ventes aux ench�res
   * @return {@inheritDoc}
   * @see com.bid4win.service.auction.AuctionAbstractService_#getManager()
   */
  @Override
  protected AuctionManager_<AUCTION, BID, BID_HISTORY, SCHEDULE, TERMS, CANCEL_POLICY, INVOLVEMENT, BOT> getManager()
  {
    return this.getInternal().getManager();
  }
  /**
   * Permet de pr�ciser la r�f�rence du service interne de gestion des ventes aux
   * ench�res
   * @return {@inheritDoc}
   * @see com.bid4win.service.auction.AuctionAbstractService_#getInternal()
   */
  @Override
  protected abstract AuctionInternalService_<AUCTION, BID, BID_HISTORY, SCHEDULE, TERMS, CANCEL_POLICY, INVOLVEMENT, BOT, ?, ?> getInternal();
}
