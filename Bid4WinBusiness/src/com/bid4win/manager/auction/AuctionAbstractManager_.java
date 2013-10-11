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
 * Manager de base de gestion des ventes aux ench�res incluant leur gestion m�tier<BR>
 * <BR>
 * @param <AUCTION> D�finition du type de vente aux ench�res g�r� par le manager<BR>
 * @param <BID> D�finition du type d'ench�re des ventes g�r�es par le manager<BR>
 * @param <BID_HISTORY> D�finition du type d'historique associ�e aux ench�res<BR>
 * @param <SCHEDULE> D�finition des �l�ments de planification des ventes aux ench�res
 * g�r�es par le manager<BR>
 * @param <TERMS> D�finition des conditions des ventes aux ench�res g�r�es par le
 * manager<BR>
 * @param <CANCEL_POLICY> D�finition de la politique d'annulation des ventes aux
 * ench�res g�r�es par le manager<BR>
 * @param <INVOLVEMENT> D�finition des implications de cr�dits sur les ventes aux
 * ench�res g�r�es par le manager<BR>
 * <BR>
 * @author Emeric Fill�tre
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
  /** R�f�rence du manager de gestion des produits vendus aux ench�res */
  @Autowired
  @Qualifier("ProductManager")
  private ProductManager productManager = null;
  /** R�f�rence du service de gestion des monnaies */
  @Autowired
  @Qualifier("CurrencyService")
  private CurrencyService currencyService = null;

  /**
   * Cette m�thode permet de r�cup�rer une vente aux ench�res en fonction de son
   * identifiant
   * @param auctionId Identifiant de la vente aux ench�res � r�cup�rer
   * @return La vente aux ench�res trouv�e correspondant � l'identifiant en argument
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si la vente aux ench�res n'a pu �tre trouv�e
   */
 /* public AUCTION getById(String auctionId) throws PersistenceException, NotFoundEntityException
  {
    return this.getAuctionDao().getById(auctionId);
  }*/
  /**
   * Cette m�thode permet de r�cup�rer toutes les ventes aux ench�res dont le status
   * appartient � un de ceux en argument ou toutes les ventes si aucun status n'
   * est pr�cis�
   * @param statusArray Status des ventes aux ench�res � r�cup�rer
   * @return Les ventes aux ench�res sont le status appartient � un de ceux en
   * argument (c'est � dire que lui ou un de ses parents correspond � un des status
   * donn�es)
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  public Bid4WinList<AUCTION> getAuctionListByStatus(Status ... statusArray)
         throws PersistenceException
  {
    return this.getAuctionDao().findListByStatus(statusArray);
  }

  /**
   * Cette m�thode permet de cr�er une vente aux ench�res pour le produit en
   * argument
   * @param productId Identifiant du produit vendu aux ench�res
   * @param schedule �l�ments de planification de la vente aux ench�res
   * @param terms Conditions de la vente aux ench�res
   * @return La vente aux ench�res du produit en argument
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun produit n'a pu �tre r�cup�r� avec
   * l'identifiant en argument
   * @throws UserException Si un probl�me intervient lors de la r�cup�ration du
   * produit ou de la cr�ation de la vente aux ench�res
   */
  public AUCTION createAuction(String productId, SCHEDULE schedule, TERMS terms)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    // R�cup�re le produit vendu aux ench�res
    Product product = this.getProductManager().loadProduct(productId);
    // Cr�e la vente aux ench�res et la retourne
    AUCTION auction = this.createAuctionEntity(product, schedule, terms);
    return this.getAuctionDao().add(auction);
  }
  /**
   * Cette m�thode permet de modifier la vente aux ench�res d�finie en argument
   * @param auctionId Identifiant de la vente aux ench�res � modifier
   * @param schedule �l�ments de planification de la vente aux ench�res
   * @param terms Conditions de la vente aux ench�res
   * @return La vente aux ench�res modifi�e
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si la vente aux ench�res n'a pu �tre trouv�e
   * @throws UserException TODO A COMMENTER
   */
  public AUCTION updateAuction(String auctionId, SCHEDULE schedule, TERMS terms)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    AUCTION auction = this.getAuctionDao().lockById(auctionId);
    return this.getAuctionDao().update(auction.unvalidate(schedule, terms));
  }
  /**
   * Cette m�thode permet de placer une ench�re sur une vente
   * @param auctionId Identifiant de la vente sur laquelle placer l'ench�re
   * @param accountId Identifiant du compte utilisateur pla�ant l'ench�re
   * @return La vente sur laquelle a �t� plac�e l'ench�re
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si le compte utilisateur ou la vente aux ench�res
   * n'a pu �tre trouv�
   * @throws UserException Si le compte utilisateur n'a pas assez de cr�dits ou
   * est d�j� le dernier ench�risseur ou si la vente aux ench�res n'a pas d�but�
   * ou est termin�e
   */
  public AUCTION bid(String auctionId, String accountId)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    //  Date de l'ench�re
    //Bid4WinDate date = new Bid4WinDate().removeMilliSecondInfo();
    // R�cup�re la vente aux ench�res en la bloquant
    AUCTION auction = this.getAuctionDao().lockById(auctionId);
    // Valide la date de fin des ench�res TODO VOIR LA NECESSITE DE VALIDER LA DATE ...
   /* UtilNumber.checkMaxValue("timer", auction.getSchedule().getEndDate().getTimeBetween(date),
                             0, true, AuctionRef.AUCTION_STATUS_ENDED_ERROR);*/
    return this.bid(auction, accountId, new Bid4WinDate());
  }
  /**
   * Cette m�thode permet de placer une ench�re sur une vente
   * @param auction Vente sur laquelle placer l'ench�re
   * @param accountId Identifiant du compte utilisateur pla�ant l'ench�re
   * @param bidDate Date de positionnement de l'ench�re
   * @return La vente sur laquelle a �t� plac�e l'ench�re
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si le compte utilisateur n'a pu �tre trouv�
   * @throws UserException Si le compte utilisateur n'a pas assez de cr�dits ou
   * est d�j� le dernier ench�risseur ou si la vente aux ench�res n'a pas d�but�
   * ou est termin�e
   */
  protected AUCTION bid(AUCTION auction, String accountId, Bid4WinDate bidDate)
            throws PersistenceException, NotFoundEntityException, UserException
  {
    // Utilise les cr�dits sur le compte utilisateur donn�
    UsedCredit usedCredit = this.useCreditForBid(auction, accountId);
    // Place l'ench�re sur la vente et retourne cette derni�re
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
    // R�cup�re la vente aux ench�res en la bloquant
    AUCTION auction = this.getAuctionDao().lockById(auctionId);
    // Valide le prix du produit vendu aux ench�res
    auction.unvalidate(auction.getSchedule(), auction.getTerms()).validateProductPrice();
    // Valide la vente aux ench�res
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
   * Cette m�thode permet de d�marrer la vente aux ench�res dont l'identifiant est
   * pass� en argument si la date de d�marrage est atteinte
   * @param auctionId Identifiant de la vente aux ench�res � d�marrer
   * @return La vente aux ench�res d�marr�e ou son dernier �tat si la date de d�marrage
   * n'est pas atteinte
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si la vente aux ench�res n'a pu �tre trouv�e
   * @throws UserException TODO A COMMENTER
   */
  public AUCTION start(String auctionId)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    // R�cup�re la vente aux ench�res en la bloquant
    AUCTION auction = this.getAuctionDao().lockById(auctionId);
    // Valide la date de d�marrage des ench�res
    if(auction.getSchedule().getStartCountdown(new Bid4WinDate()) == 0)
    {
      auction = this.getAuctionDao().update(auction.start());
    }
    return auction;
  }
  /**
   * Cette m�thode permet de terminer la vente aux ench�res d�finie par l'identifiant
   * en argument si la date de cl�ture est atteinte. Suivant ses conditions d'
   * annulation, celle-ci sera close ou annul�e
   * @param auctionId Identifiant de la vente aux ench�res � terminer
   * @return La vente aux ench�res termin�e ou son dernier �tat si la date de cl�ture
   * n'est pas atteinte
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si la vente aux ench�res n'a pu �tre trouv�
   * @throws UserException Si la vente aux ench�res n'est pas d�marr�e
   */
  public AUCTION end(String auctionId)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    // R�cup�re la vente aux ench�res en la bloquant
    AUCTION auction = this.getAuctionDao().lockById(auctionId);
    // Valide la date de cl�ture des ench�res
    if(auction.getSchedule().getEndCountdown(new Bid4WinDate()) == 0)
    {
      auction = this.end(auction);
    }
    return auction;
  }
  /**
   * Cette m�thode permet de terminer la vente aux ench�res en argument si la date
   * de cl�ture est atteinte. Suivant ses conditions d'annulation, celle-ci sera
   * close ou annul�e
   * @param auction Vente aux ench�res � terminer
   * @return La vente aux ench�res termin�e ou son dernier �tat si la date de cl�ture
   * n'est pas atteinte
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si la vente aux ench�res n'a pu �tre trouv�
   * @throws UserException Si la vente aux ench�res n'est pas d�marr�e
   */
  protected AUCTION end(AUCTION auction) throws PersistenceException, UserException
  {
    // Le seuil minimum de cr�dits n'est pas atteint, la vente est annul�e
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
   * Cette m�thode permet d'utiliser le nombre n�cessaire de cr�dits du compte
   * utilisateur dont l'identifiant est pr�cis� pour placer une ench�re sur la
   * vente argument. Le compte utilisateur sera bloqu� en base de donn�es
   * @param auction Vente aux ench�res pour laquelle utiliser les cr�dits
   * @param accountId Identifiant du compte utilisateur des cr�dits � utiliser
   * @return Les cr�dits utilis�s utilis�s sur la vente aux ench�res en argument
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si le compte utilisateur n'a pu �tre trouv�
   * @throws UserException Si le compte utilisateur n'a pas assez de cr�dits
   */
  protected UsedCredit useCreditForBid(AUCTION auction, String accountId)
            throws PersistenceException, NotFoundEntityException, UserException
  {
    return this.useCredit(auction, accountId, auction.getTerms().getCreditNbPerBid());
  }
  /**
   * Cette m�thode permet d'utiliser le nombre n�cessaire de cr�dits du compte
   * utilisateur dont l'identifiant est pr�cis� pour effectuer l'action li�e � la
   * vente aux ench�res en argument (placer une ench�re par d�faut). Le compte
   * utilisateur sera bloqu� en base de donn�es
   * @param auction Vente aux ench�res pour laquelle utiliser les cr�dits
   * @param accountId Identifiant du compte utilisateur des cr�dits � utiliser
   * @return Les cr�dits utilis�s sur la vente aux ench�res en argument
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si le compte utilisateur n'a pu �tre trouv�
   * @throws UserException Si le compte utilisateur n'a pas assez de cr�dits
   */
  protected UsedCredit useCredit(AUCTION auction, String accountId, int creditNb)
            throws PersistenceException, NotFoundEntityException, UserException
  {
    UsedCredit usedCredit = null;
    // Block et r�cup�re le compte utilisateur, utilise les cr�dits et calcule
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
    // R�cup�re l'implication de cr�dits sur la vente en cours
    INVOLVEMENT involvement = this.retreiveInvolvementEntity(usedCredit.getAccount(),
                                                             auction);
    involvement.addUsage(usedCredit.getUsedCreditMap());
    // Ce sont les premiers cr�dits utilis�s sur cette vente aux ench�res
    if(involvement.isNewEntity())
    {
      involvement = this.getInvolvementDao().add(involvement);
    }
    else
    {
      involvement = this.getInvolvementDao().update(involvement);
    }
    // Retourne les cr�dits utilis�s
    return usedCredit;
  }
  /**
   * Cette m�thode doit permettre de construire une vente aux ench�res pour le
   * produit en argument
   * @param product Produit vendu aux ench�res
   * @param schedule �l�ments de planification de la vente aux ench�res
   * @param terms Conditions de la vente aux ench�res
   * @return La vente aux ench�res du produit en argument
   * @throws UserException Si un probl�me intervient � la construction de la vente
   * aux ench�res
   */
  protected abstract AUCTION createAuctionEntity(Product product, SCHEDULE schedule, TERMS terms)
            throws UserException;
  /**
   * Cette m�thode doit permettre de construire une implication des cr�dits du
   * compte utilisateur donn� sur la vente aux ench�res pr�cis�e en argument ou
   * de r�cup�rer celle d�j� existante
   * @param account Compte utilisateur de l'implication de cr�dits � construire
   * @param auction Vente aux ench�res impliqu�e dans l'utilisation de cr�dits �
   * construire
   * @return L'implication de cr�dits construite
   * @throws UserException Si un probl�me intervient � la construction de l'implication
   * de cr�dits
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
   * Getter de la r�f�rence du DAO des ventes aux ench�res
   * @return La r�f�rence du DAO des ventes aux ench�res
   */
  protected abstract AuctionAbstractDao_<AUCTION, BID, SCHEDULE, TERMS, CANCEL_POLICY, INVOLVEMENT> getAuctionDao();
  /**
   * Getter de la r�f�rence du DAO des ench�res
   * @return La r�f�rence du DAO des ench�res
   */
  protected abstract BidAbstractDao_<BID, AUCTION, BID_HISTORY> getBidDao();
  /**
   * Getter de la r�f�rence du DAO des utilisations de cr�dits sur les ventes aux
   * ench�res
   * @return La r�f�rence du DAO des utilisations de cr�dits sur les ventes aux
   * ench�res
   */
  protected abstract CreditInvolvementAuctionDao_<INVOLVEMENT, ?, AUCTION, ?> getInvolvementDao();

  /**
   * Getter de la r�f�rence du manager de gestion des produits vendus aux ench�res
   * @return La r�f�rence du manager de gestion des produits vendus aux ench�res
   */
  protected ProductManager getProductManager()
  {
    return this.productManager;
  }
  /**
   * Getter de la r�f�rence du manager de gestion des comptes utilisateurs
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.Bid4WinManager_#getAccountManager()
   */
  @Override
  protected AccountManager getAccountManager()
  {
    return (AccountManager)super.getAccountManager();
  }
  /**
   * Getter de la r�f�rence du service de gestion des monnaies
   * @return Le service de gestion des monnaies
   */
  protected CurrencyService getCurrencyService()
  {
    return this.currencyService;
  }
}
