package com.bid4win.service.auction;

import org.springframework.transaction.annotation.Transactional;

import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.account.security.Role;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthenticationException;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthorizationException;
import com.bid4win.commons.persistence.entity.account.security.exception.SessionException;
import com.bid4win.commons.service.Bid4WinService_;
import com.bid4win.manager.auction.AuctionAbstractManager_;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.account.credit.CreditUsage;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementAuction;
import com.bid4win.persistence.entity.auction.AuctionAbstract;
import com.bid4win.persistence.entity.auction.BidAbstract;
import com.bid4win.persistence.entity.auction.BidHistory;
import com.bid4win.persistence.entity.auction.CancelPolicyAbstract;
import com.bid4win.persistence.entity.auction.ScheduleAbstract;
import com.bid4win.persistence.entity.auction.TermsAbstract;
import com.bid4win.service.connection.SessionData;

/**
 * Manager interne de base de gestion des ventes aux enchères incluant la gestion
 * des transactions ainsi que celle des habilitations. Ses méthodes ne doivent pas
 * être exposées mais être utilisées au travers de processus internes<BR>
 * <BR>
 * @param <AUCTION> Définition du type de vente aux enchères géré par le manager<BR>
 * @param <BID> Définition du type d'enchère des ventes gérées par le manager<BR>
 * @param <BID_HISTORY> Définition du type d'historique associée aux enchères<BR>
 * @param <SCHEDULE> Définition des éléments de planification des ventes aux enchères
 * gérées par le manager<BR>
 * @param <TERMS> Définition des conditions des ventes aux enchères gérées par le
 * manager<BR>
 * @param <CANCEL_POLICY> Définition de la politique d'annulation des ventes aux
 * enchères gérées par le manager<BR>
 * @param <INVOLVEMENT> Définition des implications de crédits sur les ventes aux
 * enchères gérées par le manager<BR>
 * @param <HANDLER> TODO A COMMENTER<BR>
 * @param <SERVICE> Définition du service implémenté afin de pouvoir s'autoréférencer<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class AuctionAbstractInternalService_<AUCTION extends AuctionAbstract<AUCTION, BID, SCHEDULE, TERMS, CANCEL_POLICY>,
                                                      BID extends BidAbstract<BID, AUCTION, BID_HISTORY>,
                                                      BID_HISTORY extends BidHistory<BID_HISTORY, AUCTION, BID>,
                                                      SCHEDULE extends ScheduleAbstract<SCHEDULE>,
                                                      TERMS extends TermsAbstract<TERMS>,
                                                      CANCEL_POLICY extends CancelPolicyAbstract<CANCEL_POLICY>,
                                                      INVOLVEMENT extends CreditInvolvementAuction<INVOLVEMENT, ? extends CreditUsage<?, INVOLVEMENT>, AUCTION, ?>,
                                                      HANDLER extends AuctionAbstractInternalHandler_<HANDLER, AUCTION, SCHEDULE, ?>,
                                                      SERVICE extends AuctionAbstractInternalService_<AUCTION, BID, BID_HISTORY, SCHEDULE, TERMS, CANCEL_POLICY, INVOLVEMENT, HANDLER, SERVICE>>
       extends Bid4WinService_<SessionData, Account, SERVICE>
{
  /** Map des handlers de ventes aux enchères */
  private Bid4WinMap<String, HANDLER> handlerMap = new Bid4WinMap<String, HANDLER>();

  /**
   * Cette méthode permet de rechercher la vente aux enchères dont l'identifiant
   * est précisé en argument tout en bénéficiant du cache applicatif
   * @param id Identifiant de la vente aux enchères à rechercher
   * @return La vente aux enchères trouvée avec ses relations
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si la vente aux enchères n'a pu être trouvée
   */
  @Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
  public AUCTION getAuction(String id) throws PersistenceException, NotFoundEntityException
  {
    try
    {
      return this.load(this.getAuctionClass(), id);
    }
    catch(UserException ex)
    {
      // Ne devrais pas arriver car on ne demande aucune validation d'habilitation
      throw new PersistenceException(ex);
    }
  }
  /**
   * Cette méthode permet de rechercher les ventes aux enchères dont les identifiants
   * sont précisés en argument tout en bénéficiant du cache applicatif
   * @param idSet Identifiants des ventes aux enchères à rechercher
   * @return La map des ventes aux enchères classées selon leur identifiant avec
   * leurs relations
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si un des identifiants ne correspond pas à
   * une vente aux enchères existantes
   */
  @Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
  public Bid4WinMap<String, AUCTION> getAuctionList(Bid4WinSet<String> idSet)
         throws PersistenceException, NotFoundEntityException
  {
    try
    {
      return this.loadAll(this.getAuctionClass(), idSet);
    }
    catch(UserException ex)
    {
      // Ne devrais pas arrive car on ne demande aucune validation d'habilitation
      throw new PersistenceException(ex);
    }
  }

  /**
   * Cette méthode permet de placer une enchère sur une vente pour le compte utilisateur
   * connecté. Elle est dite interne car elle ne doit pas être présentée par le
   * service de gestion des ventes aux enchères mais être utilisée au travers de
   * la méthode bid() du service qui informera le handler d'une modification sur
   * la vente aux enchères dont il a la charge
   * @param auctionId Identifiant de la vente sur laquelle placer l'enchère
   * @return La vente sur laquelle a été placée l'enchère
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si le compte utilisateur ou la vente aux enchères
   * n'a pu être trouvé
   * @throws UserException Si le compte utilisateur n'a pas assez de crédits ou
   * est déjà le dernier enchérisseur ou si la vente aux enchères n'a pas débuté
   * ou est terminée
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public AUCTION bid(String auctionId)
         throws PersistenceException, NotFoundEntityException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkUserRole();
    return this.getManager().bid(auctionId, this.getConnectedAccountId()).loadRelation();
  }

  /**
   * Cette méthode permet de valider la vente aux enchères dont l'identifiant est
   * passé en argument. Elle est dite interne car elle ne doit pas être présentée
   * par le service de gestion des ventes aux enchères mais être utilisée au travers
   * de la méthode validateAuction() du service qui affectera alors un handler à
   * la vente aux enchères
   * @param auctionId Identifiant de la vente aux enchères à valider
   * @return La vente aux enchères validée
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si la vente aux enchères n'a pu être trouvée
   * @throws UserException TODO A COMMENTER
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public AUCTION validate(String auctionId)
         throws PersistenceException, NotFoundEntityException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    //Valide la vente aux enchères
    return this.getManager().validateAuction(auctionId).loadRelation();
  }
  /**
   * Cette méthode permet de démarrer la vente aux enchères dont l'identifiant est
   * passé en argument si la date de démarrage est atteinte. Cette méthode est dite
   * interne car elle ne doit pas être présentée par le service de gestion des ventes
   * aux enchères mais être utilisée au travers de leurs handlers
   * @param auctionId Identifiant de la vente aux enchères à démarrer
   * @return La vente aux enchères démarrée ou son dernier état si la date de démarrage
   * n'est pas atteinte
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si la vente aux enchères n'a pu être trouvée
   * @throws UserException TODO A COMMENTER
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public AUCTION start(String auctionId)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    // Démarre la vente aux enchères
    return this.getManager().start(auctionId).loadRelation();
  }
  /**
   * Cette méthode permet de terminer la vente aux enchères définie par l'identifiant
   * en argument si la date de clôture est atteinte. Suivant ses conditions d'
   * annulation, celle-ci sera close ou annulée. Cette méthode est dite interne
   * car elle ne doit pas être présentée par le service de gestion des ventes aux
   * enchères mais être utilisée au travers de leurs handlers (pas de vérification
   * d'habilitations
   * @param auctionId Identifiant de la vente aux enchères à clôturer
   * @return La vente aux enchères terminée ou son dernier état si la date de clôture
   * n'est pas atteinte
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si la vente aux enchères n'a pu être trouvée
   * @throws UserException Si la vente aux enchères n'est pas démarrée
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public AUCTION end(String auctionId)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    // Termine la vente aux enchères
    return this.getManager().end(auctionId).loadRelation();
  }

  /**
   * Cette méthode permet de récupérer le handler de la vente aux enchères en argument
   * @param auction Vente aux enchères dont il faut récupérer le handler
   * @return Le handler de la vente aux enchères en argument
   */
  protected HANDLER getHandler(AUCTION auction)
  {
    return this.handlerMap.get(auction.getId());
  }
  /**
   * Cette méthode permet de d'affecter (si ce n'est déjà fait ou dans le cas contraire
   * de notifier d'une modification) un handler sur la vente aux enchères en argument
   * En s'assurant du positionnement de celle-ci dans le cache applicatif. Le handler
   * aura la charge de suivre son cycle de vie afin de l'ouvrir, la clôturer, l'annuler
   * suivant sa planification. Il validera aussi la présence des ressources liées au
   * produit vendu. Attention donc à n'affecter un handler qu'à des ventes aux enchères
   * protégées contre les modifications
   */
  protected AUCTION affectHandler(AUCTION auction)
  {
    HANDLER handler = this.getHandler(auction);
    if(handler == null)
    {
      // L'ajout d'un handler unique dans le service prendra en charge le positionnement
      // dans le cache applicatif
      this.createHandler(auction);
    }
    else if(handler.updateAuction(auction))
    {
      return this.cache(this.getAuctionClass(), auction);
    }
    return auction;
  }
  /**
   * Cette méthode doit être définie afin de créer le handler pour la vente aux
   * enchères en argument
   * @param auction Vente aux enchères pour laquelle il faut créer un handler
   * @return Le handler créer pour la vente aux enchères en argument
   */
  protected abstract HANDLER createHandler(AUCTION auction);
  /**
   * Cette méthode permet d'ajouter le handler de vente aux enchères en argument
   * s'il n'existe pas déjà et dans ce cas de positionner la vente aux enchères
   * concernée dans le cache applicatif. Elle ne doit être appelé que par le handler
   * au moment de son lancement ce qui évite d'avoir des blocages de synchronisation
   * @param handler Handler de vente aux enchères à ajouter
   * @return True si le handler n'existait pas déjà et a été ajouté, false sinon
   */
  protected boolean addHandler(HANDLER handler)
  {
    if(this.getHandler(handler.getAuction()) == null)
    {
      synchronized(this.handlerMap)
      {
        if(this.getHandler(handler.getAuction()) == null)
        {
          this.handlerMap.put(handler.getAuctionId(), handler);
          this.cache(this.getAuctionClass(), handler.getAuction());
          return true;
        }
      }
    }
    return false;
  }
  /**
   * Cette méthode permet de retirer le handler de vente aux enchères en argument
   * s'il est bien arrêté et référencé par le service et alors de retirer du cache
   * applicatif la vente aux enchères concernée. Elle ne doit être appelé que par
   * le handler au moment de son arrêt ce qui évite d'avoir des blocages de synchronisation
   * @param handler Handler de vente aux enchères à retirer
   */
  protected void removeHandler(HANDLER handler)
  {
    if(this.getHandler(handler.getAuction()) != null)
    {
      synchronized(this.handlerMap)
      {
        if(handler == this.getHandler(handler.getAuction()) &&
           handler.isEnded())
        {
          this.handlerMap.remove(handler.getAuction().getId());
          this.uncache(this.getAuctionClass(), handler.getAuction());
        }
      }
    }
  }

  /**
   *
   * TODO A COMMENTER {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinService_#clearCache()
   */
  @Override
  protected void clearCache()
  {
    super.clearCache();
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinService_#getAdminRole()
   */
  @Override
  public Role getAdminRole()
  {
    return Role.BIZ;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public Class<AUCTION> getAuctionClass()
  {
    return this.getManager().getAuctionClass();
  }

  /**
   * Permet de préciser la référence du manager de gestion des ventes aux enchères
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinService_#getManager()
   */
  @Override
  protected abstract AuctionAbstractManager_<AUCTION, BID, BID_HISTORY, SCHEDULE, TERMS, CANCEL_POLICY, INVOLVEMENT> getManager();
}
