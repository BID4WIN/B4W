package com.bid4win.manager.auction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.manager.Bid4WinManager_;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.manager.account.AccountManager;
import com.bid4win.manager.account.UsedCredit;
import com.bid4win.manager.product.ProductManager;
import com.bid4win.persistence.dao.account.credit.auction.CreditInvolvementAuctionDao_;
import com.bid4win.persistence.dao.auction.AuctionAbstractDao_;
import com.bid4win.persistence.dao.auction.BidAbstractDao_;
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
import com.bid4win.persistence.entity.price.ExchangeRates;
import com.bid4win.persistence.entity.product.Product;
import com.bid4win.service.price.CurrencyService;

/**
 * Manager de base de gestion des ventes aux enchères incluant leur gestion métier<BR>
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
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class AuctionAbstractManager_<AUCTION extends AuctionAbstract<AUCTION, BID, SCHEDULE, TERMS, CANCEL_POLICY>,
                                              BID extends BidAbstract<BID, AUCTION, BID_HISTORY>,
                                              BID_HISTORY extends BidHistory<BID_HISTORY, AUCTION, BID>,
                                              SCHEDULE extends ScheduleAbstract<SCHEDULE>,
                                              TERMS extends TermsAbstract<TERMS>,
                                              CANCEL_POLICY extends CancelPolicyAbstract<CANCEL_POLICY>,
                                              INVOLVEMENT extends CreditInvolvementAuction<INVOLVEMENT, ? extends CreditUsage<?, INVOLVEMENT>, AUCTION, ?>>
       extends Bid4WinManager_<Account, AuctionAbstractManager_<AUCTION, BID, BID_HISTORY, SCHEDULE, TERMS, CANCEL_POLICY, INVOLVEMENT>>
{
  /** Référence du manager de gestion des produits vendus aux enchères */
  @Autowired
  @Qualifier("ProductManager")
  private ProductManager productManager = null;
  /** Référence du service de gestion des monnaies */
  @Autowired
  @Qualifier("CurrencyService")
  private CurrencyService currencyService = null;

  /**
   * Cette méthode permet de récupérer une vente aux enchères en fonction de son
   * identifiant
   * @param auctionId Identifiant de la vente aux enchères à récupérer
   * @return La vente aux enchères trouvée correspondant à l'identifiant en argument
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si la vente aux enchères n'a pu être trouvée
   */
 /* public AUCTION getById(String auctionId) throws PersistenceException, NotFoundEntityException
  {
    return this.getAuctionDao().getById(auctionId);
  }*/
  /**
   * Cette méthode permet de récupérer toutes les ventes aux enchères dont le status
   * appartient à un de ceux en argument ou toutes les ventes si aucun status n'
   * est précisé
   * @param statusArray Status des ventes aux enchères à récupérer
   * @return Les ventes aux enchères sont le status appartient à un de ceux en
   * argument (c'est à dire que lui ou un de ses parents correspond à un des status
   * données)
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  public Bid4WinList<AUCTION> getAuctionListByStatus(Status ... statusArray)
         throws PersistenceException
  {
    return this.getAuctionDao().findListByStatus(statusArray);
  }

  /**
   * Cette méthode permet de créer une vente aux enchères pour le produit en
   * argument
   * @param productId Identifiant du produit vendu aux enchères
   * @param schedule Éléments de planification de la vente aux enchères
   * @param terms Conditions de la vente aux enchères
   * @return La vente aux enchères du produit en argument
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun produit n'a pu être récupéré avec
   * l'identifiant en argument
   * @throws UserException Si un problème intervient lors de la récupération du
   * produit ou de la création de la vente aux enchères
   */
  public AUCTION createAuction(String productId, SCHEDULE schedule, TERMS terms)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    // Récupère le produit vendu aux enchères
    Product product = this.getProductManager().loadProduct(productId);
    // Crée la vente aux enchères et la retourne
    AUCTION auction = this.createAuctionEntity(product, schedule, terms);
    return this.getAuctionDao().add(auction);
  }
  /**
   * Cette méthode permet de modifier la vente aux enchères définie en argument
   * @param auctionId Identifiant de la vente aux enchères à modifier
   * @param schedule Éléments de planification de la vente aux enchères
   * @param terms Conditions de la vente aux enchères
   * @return La vente aux enchères modifiée
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si la vente aux enchères n'a pu être trouvée
   * @throws UserException TODO A COMMENTER
   */
  public AUCTION updateAuction(String auctionId, SCHEDULE schedule, TERMS terms)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    AUCTION auction = this.getAuctionDao().lockById(auctionId);
    return this.getAuctionDao().update(auction.unvalidate(schedule, terms));
  }
  /**
   * Cette méthode permet de placer une enchère sur une vente
   * @param auctionId Identifiant de la vente sur laquelle placer l'enchère
   * @param accountId Identifiant du compte utilisateur plaçant l'enchère
   * @return La vente sur laquelle a été placée l'enchère
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si le compte utilisateur ou la vente aux enchères
   * n'a pu être trouvé
   * @throws UserException Si le compte utilisateur n'a pas assez de crédits ou
   * est déjà le dernier enchérisseur ou si la vente aux enchères n'a pas débuté
   * ou est terminée
   */
  public AUCTION bid(String auctionId, String accountId)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    //  Date de l'enchére
    //Bid4WinDate date = new Bid4WinDate().removeMilliSecondInfo();
    // Récupère la vente aux enchères en la bloquant
    AUCTION auction = this.getAuctionDao().lockById(auctionId);
    // Valide la date de fin des enchères TODO VOIR LA NECESSITE DE VALIDER LA DATE ...
   /* UtilNumber.checkMaxValue("timer", auction.getSchedule().getEndDate().getTimeBetween(date),
                             0, true, AuctionRef.AUCTION_STATUS_ENDED_ERROR);*/
    return this.bid(auction, accountId, new Bid4WinDate());
  }
  /**
   * Cette méthode permet de placer une enchère sur une vente
   * @param auction Vente sur laquelle placer l'enchère
   * @param accountId Identifiant du compte utilisateur plaçant l'enchère
   * @param bidDate Date de positionnement de l'enchère
   * @return La vente sur laquelle a été placée l'enchère
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si le compte utilisateur n'a pu être trouvé
   * @throws UserException Si le compte utilisateur n'a pas assez de crédits ou
   * est déjà le dernier enchérisseur ou si la vente aux enchères n'a pas débuté
   * ou est terminée
   */
  protected AUCTION bid(AUCTION auction, String accountId, Bid4WinDate bidDate)
            throws PersistenceException, NotFoundEntityException, UserException
  {
    // Utilise les crédits sur le compte utilisateur donné
    UsedCredit usedCredit = this.useCreditForBid(auction, accountId);
    // Place l'enchère sur la vente et retourne cette dernière
    BID bid = this.getBidDao().add(auction.addBid(usedCredit.getAccount(), bidDate));
    return bid.getAuction();
  }
  /**
   *
   * TODO A COMMENTER
   * @param auctionId TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   * @throws NotFoundEntityException TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  public AUCTION validateAuction(String auctionId)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    // Récupère la vente aux enchères en la bloquant
    AUCTION auction = this.getAuctionDao().lockById(auctionId);
    // Valide le prix du produit vendu aux enchères
    auction.unvalidate(auction.getSchedule(), auction.getTerms()).validateProductPrice();
    // Valide la vente aux enchères
    return this.getAuctionDao().update(auction.validate(this.getCancelPolicy(auction),
                                                        this.getExchangeRates()));
  }
  /**
   *
   * TODO A COMMENTER
   * @param auction TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  protected abstract CANCEL_POLICY getCancelPolicy(AUCTION auction) throws UserException;
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  protected ExchangeRates getExchangeRates() throws PersistenceException
  {
    return this.getCurrencyService().getExchangeRates().copy();
  }
  /**
   * Cette méthode permet de démarrer la vente aux enchères dont l'identifiant est
   * passé en argument si la date de démarrage est atteinte
   * @param auctionId Identifiant de la vente aux enchères à démarrer
   * @return La vente aux enchères démarrée ou son dernier état si la date de démarrage
   * n'est pas atteinte
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si la vente aux enchères n'a pu être trouvée
   * @throws UserException TODO A COMMENTER
   */
  public AUCTION start(String auctionId)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    // Récupère la vente aux enchères en la bloquant
    AUCTION auction = this.getAuctionDao().lockById(auctionId);
    // Valide la date de démarrage des enchères
    if(auction.getSchedule().getStartCountdown(new Bid4WinDate()) == 0)
    {
      auction = this.getAuctionDao().update(auction.start());
    }
    return auction;
  }
  /**
   * Cette méthode permet de terminer la vente aux enchères définie par l'identifiant
   * en argument si la date de clôture est atteinte. Suivant ses conditions d'
   * annulation, celle-ci sera close ou annulée
   * @param auctionId Identifiant de la vente aux enchères à terminer
   * @return La vente aux enchères terminée ou son dernier état si la date de clôture
   * n'est pas atteinte
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si la vente aux enchères n'a pu être trouvé
   * @throws UserException Si la vente aux enchères n'est pas démarrée
   */
  public AUCTION end(String auctionId)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    // Récupère la vente aux enchères en la bloquant
    AUCTION auction = this.getAuctionDao().lockById(auctionId);
    // Valide la date de clôture des enchères
    if(auction.getSchedule().getEndCountdown(new Bid4WinDate()) == 0)
    {
      auction = this.end(auction);
    }
    return auction;
  }
  /**
   * Cette méthode permet de terminer la vente aux enchères en argument si la date
   * de clôture est atteinte. Suivant ses conditions d'annulation, celle-ci sera
   * close ou annulée
   * @param auction Vente aux enchères à terminer
   * @return La vente aux enchères terminée ou son dernier état si la date de clôture
   * n'est pas atteinte
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si la vente aux enchères n'a pu être trouvé
   * @throws UserException Si la vente aux enchères n'est pas démarrée
   */
  protected AUCTION end(AUCTION auction) throws PersistenceException, UserException
  {
    // Le seuil minimum de crédits n'est pas atteint, la vente est annulée
    if(auction.getInvolvedCreditNb() < auction.getCancelPolicy().getCreditNbThreshold())
    {
      auction.cancel();
    }
    // La vente est close
    else
    {
      auction.close();
    }
    return this.getAuctionDao().update(auction);
  }

  /**
   * Cette méthode permet d'utiliser le nombre nécessaire de crédits du compte
   * utilisateur dont l'identifiant est précisé pour placer une enchère sur la
   * vente argument. Le compte utilisateur sera bloqué en base de données
   * @param auction Vente aux enchères pour laquelle utiliser les crédits
   * @param accountId Identifiant du compte utilisateur des crédits à utiliser
   * @return Les crédits utilisés utilisés sur la vente aux enchères en argument
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si le compte utilisateur n'a pu être trouvé
   * @throws UserException Si le compte utilisateur n'a pas assez de crédits
   */
  protected UsedCredit useCreditForBid(AUCTION auction, String accountId)
            throws PersistenceException, NotFoundEntityException, UserException
  {
    return this.useCredit(auction, accountId, auction.getTerms().getCreditNbPerBid());
  }
  /**
   * Cette méthode permet d'utiliser le nombre nécessaire de crédits du compte
   * utilisateur dont l'identifiant est précisé pour effectuer l'action liée à la
   * vente aux enchères en argument (placer une enchère par défaut). Le compte
   * utilisateur sera bloqué en base de données
   * @param auction Vente aux enchères pour laquelle utiliser les crédits
   * @param accountId Identifiant du compte utilisateur des crédits à utiliser
   * @return Les crédits utilisés sur la vente aux enchères en argument
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si le compte utilisateur n'a pu être trouvé
   * @throws UserException Si le compte utilisateur n'a pas assez de crédits
   */
  protected UsedCredit useCredit(AUCTION auction, String accountId, int creditNb)
            throws PersistenceException, NotFoundEntityException, UserException
  {
    UsedCredit usedCredit = null;
    // Block et récupère le compte utilisateur, utilise les crédits et calcule
    // leur valeur
    if(creditNb != 0)
    {
      usedCredit = this.getAccountManager().useCredit(accountId, creditNb);
      auction.addInvolvedCredit(usedCredit.getUsedCreditMap());
    }
    else
    {
      usedCredit = new UsedCredit(this.getAccountManager().lockById(accountId));
    }
    // Récupère l'implication de crédits sur la vente en cours
    INVOLVEMENT involvement = this.retreiveInvolvementEntity(usedCredit.getAccount(),
                                                             auction);
    involvement.addUsage(usedCredit.getUsedCreditMap());
    // Ce sont les premiers crédits utilisés sur cette vente aux enchères
    if(involvement.isNewEntity())
    {
      involvement = this.getInvolvementDao().add(involvement);
    }
    else
    {
      involvement = this.getInvolvementDao().update(involvement);
    }
    // Retourne les crédits utilisés
    return usedCredit;
  }
  /**
   * Cette méthode doit permettre de construire une vente aux enchères pour le
   * produit en argument
   * @param product Produit vendu aux enchères
   * @param schedule Éléments de planification de la vente aux enchères
   * @param terms Conditions de la vente aux enchères
   * @return La vente aux enchères du produit en argument
   * @throws UserException Si un problème intervient à la construction de la vente
   * aux enchères
   */
  protected abstract AUCTION createAuctionEntity(Product product, SCHEDULE schedule, TERMS terms)
            throws UserException;
  /**
   * Cette méthode doit permettre de construire une implication des crédits du
   * compte utilisateur donné sur la vente aux enchères précisée en argument ou
   * de récupérer celle déjà existante
   * @param account Compte utilisateur de l'implication de crédits à construire
   * @param auction Vente aux enchères impliquée dans l'utilisation de crédits à
   * construire
   * @return L'implication de crédits construite
   * @throws UserException Si un problème intervient à la construction de l'implication
   * de crédits
   */
  protected abstract INVOLVEMENT retreiveInvolvementEntity(Account account, AUCTION auction)
            throws UserException;

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public Class<AUCTION> getAuctionClass()
  {
    return this.getAuctionDao().getEntityClass();
  }

  /**
   * Getter de la référence du DAO des ventes aux enchères
   * @return La référence du DAO des ventes aux enchères
   */
  protected abstract AuctionAbstractDao_<AUCTION, BID, SCHEDULE, TERMS, CANCEL_POLICY, INVOLVEMENT> getAuctionDao();
  /**
   * Getter de la référence du DAO des enchères
   * @return La référence du DAO des enchères
   */
  protected abstract BidAbstractDao_<BID, AUCTION, BID_HISTORY> getBidDao();
  /**
   * Getter de la référence du DAO des utilisations de crédits sur les ventes aux
   * enchères
   * @return La référence du DAO des utilisations de crédits sur les ventes aux
   * enchères
   */
  protected abstract CreditInvolvementAuctionDao_<INVOLVEMENT, ?, AUCTION, ?> getInvolvementDao();

  /**
   * Getter de la référence du manager de gestion des produits vendus aux enchères
   * @return La référence du manager de gestion des produits vendus aux enchères
   */
  protected ProductManager getProductManager()
  {
    return this.productManager;
  }
  /**
   * Getter de la référence du manager de gestion des comptes utilisateurs
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.Bid4WinManager_#getAccountManager()
   */
  @Override
  protected AccountManager getAccountManager()
  {
    return (AccountManager)super.getAccountManager();
  }
  /**
   * Getter de la référence du service de gestion des monnaies
   * @return Le service de gestion des monnaies
   */
  protected CurrencyService getCurrencyService()
  {
    return this.currencyService;
  }
}
