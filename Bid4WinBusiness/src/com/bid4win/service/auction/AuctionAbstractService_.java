package com.bid4win.service.auction;

import org.springframework.transaction.annotation.Transactional;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.ObjectProtector;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.Bid4WinEntityLoader;
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
import com.bid4win.persistence.entity.auction.Status;
import com.bid4win.persistence.entity.auction.TermsAbstract;
import com.bid4win.service.connection.SessionData;

/**
 * Manager de base de gestion des ventes aux enchères incluant la gestion des
 * transactions ainsi que celle des habilitations<BR>
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
 * @param <SERVICE> Définition du service implémenté afin de pouvoir s'autoréférencer<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class AuctionAbstractService_<AUCTION extends AuctionAbstract<AUCTION, BID, SCHEDULE, TERMS, CANCEL_POLICY>,
                                              BID extends BidAbstract<BID, AUCTION, BID_HISTORY>,
                                              BID_HISTORY extends BidHistory<BID_HISTORY, AUCTION, BID>,
                                              SCHEDULE extends ScheduleAbstract<SCHEDULE>,
                                              TERMS extends TermsAbstract<TERMS>,
                                              CANCEL_POLICY extends CancelPolicyAbstract<CANCEL_POLICY>,
                                              INVOLVEMENT extends CreditInvolvementAuction<INVOLVEMENT, ? extends CreditUsage<?, INVOLVEMENT>, AUCTION, ?>,
                                              SERVICE extends AuctionAbstractService_<AUCTION, BID, BID_HISTORY, SCHEDULE, TERMS, CANCEL_POLICY, INVOLVEMENT, SERVICE>>
       extends Bid4WinService_<SessionData, Account, SERVICE>
{
  /**
   * Cette méthode permet de récupérer une vente aux enchères en fonction de son
   * identifiant
   * @param auctionId Identifiant de la vente aux enchères à récupérer
   * @return La vente aux enchères trouvée correspondant à l'identifiant en argument
   * sans ces relations chargée
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si la vente aux enchères n'a pu être trouvée
   */
/*  @Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
  public AUCTION getById(String auctionId) throws PersistenceException, NotFoundEntityException
  {// TODO voir pour le chargement des relations (seulement produit ?)
    return this.getInternal().getById(auctionId).loadRelation();
  }*/

  /**
   * Cette méthode permet de récupérer toutes les ventes aux enchères dont le status
   * appartient à un de ceux en argument ou toutes les ventes si aucun status n'
   * est précisé
   * @param loadRelation Permet d'indiquer si on souhaite ou non le chargement des
   * relations des ventes aux enchères remontées
   * @param statusArray Status des ventes aux enchères à récupérer
   * @return Les ventes aux enchères sont le status appartient à un de ceux en
   * argument (c'est à dire que lui ou un de ses parents correspond à un des status
   * données) avec leurs relations si demandé expressément
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  @Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
  public Bid4WinList<AUCTION> getAuctionListByStatus(boolean loadRelation,
                                                     Status ... statusArray)
         throws PersistenceException, SessionException,
                AuthenticationException, AuthorizationException
  {
    if(statusArray.length == 0)
    {
      // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
      this.checkAdminRole();
    }
    else
    {
      for(Status status : statusArray)
      {
        if(!status.belongsTo(Status.VALID) && !status.belongsTo(Status.ENDED))
        {
          // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
          this.checkAdminRole();
          break;
        }
      }
    }
    Bid4WinList<AUCTION> result = this.getManager().getAuctionListByStatus(statusArray);
    if(loadRelation)
    {
      Bid4WinEntityLoader.getInstance().loadRelation(result);
    }
    return result;
  }

  /**
   * Cette méthode permet de récupérer toutes les ventes aux enchères valides, c'
   * est à dire celles sur lesquelles l'utilisateur peut être amené à effectuer
   * une action. La remontée de ces ventes leur affectera un handler si ce n'est
   * déjà fait qui aura la charge de suivre le cycle de vie de celles-ci afin de
   * les ouvrir, les clôturer, les annuler suivant leur planification. L'affectation
   * d'un handler à une vente aux enchères validera aussi la présence des ressources
   * liées au produit vendu et positionnera la vente aux enchères dans le cache
   * applicatif
   * @return La liste de toutes les ventes aux enchères valides avec leurs relations
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  public Bid4WinList<AUCTION> getValidAuctionList() throws PersistenceException
  {
    Bid4WinList<AUCTION> result = null;
    // Récupère les ventes aux enchères en les protégeant contre toute modification
    // ultérieure
    String protectionId = ObjectProtector.startProtection();
    try
    {
      result = this.self().getAuctionListByStatus(true, Status.VALID);
    }
    catch(UserException ex)
    {
      throw new PersistenceException();
    }
    finally
    {
      ObjectProtector.stopProtection(protectionId);
    }
    // Affecte un handler à chaque vente aux enchères si ce n'est déjà fait
    for(AUCTION auction : result)
    {
      this.getInternal().affectHandler(auction);
    }
    return result;
  }
  /**
   * Cette méthode permet de rechercher la vente aux enchères dont l'identifiant
   * est précisé en argument tout en bénéficiant du cache applicatif
   * @param id Identifiant de la vente aux enchères à rechercher
   * @return La vente aux enchères trouvée avec ses relations
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si la vente aux enchères n'a pu être trouvée
   */
  public AUCTION getAuction(String id) throws PersistenceException, NotFoundEntityException
  {
    return this.getInternal().getAuction(id);
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
  public Bid4WinMap<String, AUCTION> getAuctionList(Bid4WinSet<String> idSet)
         throws PersistenceException, NotFoundEntityException
  {
    return this.getInternal().getAuctionList(idSet);
  }

  /**
   * Cette méthode permet de créer une vente aux enchères pour le produit en argument
   * @param productId Identifiant du produit vendu aux enchères
   * @param schedule Éléments de planification de la vente aux enchères
   * @param terms Conditions de la vente aux enchères
   * @return La vente aux enchères du produit en argument
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun produit n'a pu être récupéré avec
   * l'identifiant en argument
   * @throws UserException Si un problème intervient lors de la récupération du
   * produit ou de la création de la vente aux enchères
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public AUCTION createAuction(String productId, SCHEDULE schedule, TERMS terms)
         throws PersistenceException, NotFoundEntityException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    // Crée la vente aux enchères
    return this.getManager().createAuction(productId, schedule, terms).loadRelation();
  }
  /**
   * Cette méthode permet de modifier la vente aux enchères définie en argument
   * @param auctionId Identifiant de la vente aux enchères à modifier
   * @param schedule Éléments de planification de la vente aux enchères
   * @param terms Conditions de la vente aux enchères
   * @return La vente aux enchères modifiée
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
  public AUCTION updateAuction(String auctionId, SCHEDULE schedule, TERMS terms)
         throws PersistenceException, NotFoundEntityException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    // Crée la vente aux enchères
    return this.getManager().updateAuction(auctionId, schedule, terms).loadRelation();
  }
  /**
   * Cette méthode permet de valider la vente aux enchères dont l'identifiant est
   * passé en argument
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
  public AUCTION validateAuction(String auctionId)
         throws PersistenceException, NotFoundEntityException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    AUCTION auction = null;
    // Valide la vente aux enchères en la protégeant contre toute modification
    // ultérieure
    String protectionId = ObjectProtector.startProtection();
    try
    {
      auction = this.getInternal().validate(auctionId);
    }
    finally
    {
      ObjectProtector.stopProtection(protectionId);
    }
    // Affecte ou notifie le handler concerné
    return this.getInternal().affectHandler(auction);
  }

  /**
   * Cette méthode permet de placer une enchère sur une vente pour le compte utilisateur
   * connecté
   * @param auctionId Identifiant de la vente sur laquelle placer l'enchère
   * @return La vente sur laquelle a été placée l'enchère
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
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
  public AUCTION bid(String auctionId)
         throws PersistenceException, NotFoundEntityException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    AUCTION auction = null;
    // Place l'enchère sur la vente en la protégeant contre toute modification
    // ultérieure
    String protectionId = ObjectProtector.startProtection();
    try
    {
      auction = this.getInternal().bid(auctionId);
    }
    finally
    {
      ObjectProtector.stopProtection(protectionId);
    }
    // Affecte ou notifie le handler concerné
    return this.getInternal().affectHandler(auction);
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
    this.getInternal().clearCache();
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinService_#getAdminRole()
   */
  @Override
  public final Role getAdminRole()
  {
    return this.getInternal().getAdminRole();
  }
  /**
   * Permet de préciser la référence du manager de gestion des ventes aux enchères
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinService_#getManager()
   */
  @Override
  protected AuctionAbstractManager_<AUCTION, BID, BID_HISTORY, SCHEDULE, TERMS, CANCEL_POLICY, INVOLVEMENT> getManager()
  {
    return this.getInternal().getManager();
  }
  /**
   * Permet de préciser la référence du service interne de gestion des ventes aux
   * enchères
   * @return La référence du service interne de gestions des ventes aux enchères
   */
  protected abstract AuctionAbstractInternalService_<AUCTION, BID, BID_HISTORY, SCHEDULE, TERMS, CANCEL_POLICY, INVOLVEMENT, ?, ?> getInternal();
}
