package com.bid4win.persistence.dao.auction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.dao.account.AccountBasedEntityMultipleDao_;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.account.Account_;
import com.bid4win.persistence.entity.auction.Auction;
import com.bid4win.persistence.entity.auction.Auction_;
import com.bid4win.persistence.entity.auction.Bid;
import com.bid4win.persistence.entity.auction.Bot;
import com.bid4win.persistence.entity.auction.Bot_;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <BOT> TODO A COMMENTER<BR>
 * @param <AUCTION> TODO A COMMENTER<BR>
 * @param <BID> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class BotDao_<BOT extends Bot<BOT, AUCTION, BID>,
                     AUCTION extends Auction<AUCTION, BID, ?, ?, ?>,
                     BID extends Bid<BID, AUCTION, ?>>
       extends AccountBasedEntityMultipleDao_<BOT, Long, Account>
{
  /**
   * Constructeur
   * @param botClass Classe du robot d'ench�res g�r� par le DAO
   */
  protected BotDao_(Class<BOT> botClass)
  {
    super(botClass);
  }

  /**
   *
   * TODO A COMMENTER
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws NotFoundEntityException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#getById(java.lang.Object)
   */
  @Override
  public BOT getById(Long id) throws PersistenceException, NotFoundEntityException
  {
    return super.getById(id);
  }

  /**
   * Cette fonction permet de r�cup�rer la liste de robots d'ench�res en fonction
   * de leur vente
   * @param auction Vente des robots d'ench�res � rechercher
   * @return La liste de robots d'ench�res trouv�s
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  public Bid4WinList<BOT> findListByAuction(AUCTION auction) throws PersistenceException
  {
    return super.findList(this.getCriteriaForAuction(auction));
  }
  /**
   * Cette fonction permet de r�cup�rer la liste de robots d'ench�res en fonction
   * de l'identifiant de leur vente
   * @param auctionId Identifiant de la vente des robots d'ench�res � rechercher
   * @return La liste de robots d'ench�res trouv�s
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  public Bid4WinList<BOT> findListByAuctionId(String auctionId) throws PersistenceException
  {
    return super.findList(this.getCriteriaForAuctionId(auctionId));
  }

  /**
   * Cette m�thode permet de construire les crit�res permettant de rechercher les
   * robots d'ench�res dont la vente est pr�cis�e en argument
   * @param auction Vente des robots d'ench�res � rechercher
   * @return Les crit�res permettant de rechercher les robots d'ench�res en fonction
   * de leur vente
   */
  protected CriteriaQuery<BOT> getCriteriaForAuction(AUCTION auction)
  {
    return this.getCriteriaForAuctionAndAccount(auction, null);
  }
  /**
   * Cette m�thode permet de construire les crit�res permettant de rechercher les
   * robots d'ench�res dont l'identifiant de la vente est pr�cis� en argument
   * @param auctionId Identifiant de la vente des robots d'ench�res � rechercher
   * @return Les crit�res permettant de rechercher les robots d'ench�res en fonction
   * de l'identifiant de leur vente
   */
  protected CriteriaQuery<BOT> getCriteriaForAuctionId(String auctionId)
  {
    return this.getCriteriaForAuctionIdAndAccountId(auctionId, null);
  }

  /**
   * Cette fonction permet de r�cup�rer le potentiel robot d'ench�res en fonction
   * de sa vente et de son compte utilisateur
   * @param auction Vente du robot d'ench�res � rechercher
   * @param account Compte utilisateur du robot d'ench�res � rechercher
   * @return Le robot d'ench�res potentiellement trouv�
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  public BOT findOneByAuctionAndAccount(AUCTION auction, Account account)
         throws PersistenceException
  {
    return super.findOne(this.getCriteriaForAuctionAndAccount(auction, account));
  }
  /**
   * Cette fonction permet de r�cup�rer le potentiel robot d'ench�res en fonction
   * des identifiants de sa vente et de son compte utilisateur
   * @param auctionId Identifiant de la vente du robot d'ench�re � rechercher
   * @param accountId Identifiant du compte utilisateur du robot d'ench�res � rechercher
   * @return Le robot d'ench�res potentiellement trouv�
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  public BOT findOneByAuctionIdAndAccountId(String auctionId, String accountId)
         throws PersistenceException
  {
    return super.findOne(this.getCriteriaForAuctionIdAndAccountId(auctionId, accountId));
  }
  /**
   * Cette fonction permet de r�cup�rer l'unique robot d'ench�res en fonction de
   * sa vente et de son compte utilisateur
   * @param auction Vente du robot d'ench�res � rechercher
   * @param account Compte utilisateur du robot d'ench�res � rechercher
   * @return L'unique robot d'ench�res trouv�
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun robot correspondant n'a �t� trouv�
   */
  public BOT getOneByAuctionAndAccount(AUCTION auction, Account account)
         throws PersistenceException, NotFoundEntityException
  {
    return super.getOne(this.getCriteriaForAuctionAndAccount(auction, account));
  }
  /**
   * Cette fonction permet de r�cup�rer l'unique robot d'ench�res en fonction des
   * identifiants de sa vente et de son compte utilisateur
   * @param auctionId Identifiant de la vente du robot d'ench�re � rechercher
   * @param accountId Identifiant du compte utilisateur du robot d'ench�res � rechercher
   * @return L'unique robot d'ench�res trouv�
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun robot correspondant n'a �t� trouv�
   */
  public BOT getOneByAuctionIdAndAccountId(String auctionId, String accountId)
         throws PersistenceException
  {
    return super.getOne(this.getCriteriaForAuctionIdAndAccountId(auctionId, accountId));
  }

  /**
   * Cette m�thode permet de construire les crit�res permettant de rechercher les
   * robots d'ench�res dont la vente et potentiellement le compte utilisateur sont
   * pr�cis�s en argument
   * @param auction Vente des robots d'ench�res � rechercher
   * @param account Compte utilisateur potentiel du robot d'ench�res � rechercher
   * @return Les crit�res permettant de rechercher les robots d'ench�res en fonction
   * de leur vente et potentiellement de leur compte utilisateur
   */
  protected CriteriaQuery<BOT> getCriteriaForAuctionAndAccount(AUCTION auction,
                                                               Account account)
  {
    String accountId = (account == null ? null : account.getId());
    return this.getCriteriaForAuctionIdAndAccountId(auction.getId(), accountId);
  }
  /**
   * Cette m�thode permet de construire les crit�res permettant de rechercher les
   * robots d'ench�res dont l'identifiant de la vente et potentiellement celui du
   * compte utilisateur sont pr�cis�s en argument
   * @param auctionId Identifiant de la vente des robots d'ench�res � rechercher
   * @param accountId Identifiant potentiel du compte utilisateur du robot d'ench�res
   * � rechercher
   * @return Les crit�res permettant de rechercher les robots d'ench�res en fonction
   * de l'identifiant de leur vente et potentiellement de leur compte utilisateur
   */
  protected CriteriaQuery<BOT> getCriteriaForAuctionIdAndAccountId(String auctionId,
                                                                   String accountId)
  {
    CriteriaQuery<BOT> criteria = this.createCriteria();
    Root<BOT> bot_ = criteria.from(this.getEntityClass());
    return criteria.where(this.getConditionForAuctionIdAndAccountId(
        bot_, auctionId, accountId, false));
  }

  /**
   *
   * TODO A COMMENTER
   * @param bot_ TODO A COMMENTER
   * @param auction TODO A COMMENTER
   * @param account TODO A COMMENTER
   * @param excludeAccount Boolean indiquant qu'on souhaite rechercher les robots
   * dont le compte utilisateur n'est pas celui d�fini en argument si a true, ou
   * l'unique robot pour cette vente et ce compte utilisateur si � false
   * @return TODO A COMMENTER
   */
  protected Predicate getConditionForAuctionAndAccount(Root<BOT> bot_,
                                                       AUCTION auction,
                                                       Account account,
                                                       boolean excludeAccount)
  {
    String accountId = (account == null ? null : account.getId());
    return this.getConditionForAuctionIdAndAccountId(
        bot_, auction.getId(), accountId, excludeAccount);

  }
  /**
   *
   * TODO A COMMENTER
   * @param bot_ TODO A COMMENTER
   * @param auctionId TODO A COMMENTER
   * @param accountId TODO A COMMENTER
   * @param excludeAccount Boolean indiquant qu'on souhaite rechercher les robots
   * dont le compte utilisateur n'est pas celui d�fini en argument si a true, ou
   * l'unique robot pour cette vente et ce compte utilisateur si � false
   * @return TODO A COMMENTER
   */
  protected Predicate getConditionForAuctionIdAndAccountId(Root<BOT> bot_,
                                                           String auctionId,
                                                           String accountId,
                                                           boolean excludeAccount)
  {
    CriteriaBuilder builder = this.getCriteriaBuilder();

    Path<String> auctionId_ = bot_.get(Bot_.auction).get(Auction_.id);
    Predicate condition = builder.equal(auctionId_, auctionId);
    if(accountId != null)
    {
      Path<String> accountId_ = bot_.get(Bot_.account).get(Account_.id);
      Predicate accountCondition = null;
      if(excludeAccount)
      {
        accountCondition = builder.notEqual(accountId_, accountId);
      }
      else
      {
        accountCondition = builder.equal(accountId_, accountId);
      }
      condition = builder.and(condition, accountCondition);
    }
    return condition;
  }

  /**
   * Cette fonction permet de r�cup�rer la liste des prochains robots d'ench�res
   * � utiliser sur la vente en argument
   * @param auction Vente des prochains robots d'ench�res � rechercher
   * @return La liste des prochains robot d'ench�res trouv�s
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  public Bid4WinList<BOT> findNextBotList(AUCTION auction) throws PersistenceException
  {
    return super.findList(this.getCriteriaForNextBot(auction));
  }
  /**
   * Cette m�thode permet de construire les crit�res permettant de rechercher les
   * prochains robots d'ench�res � utiliser sur la vente en argument
   * @param auction Vente des prochains robots d'ench�res � rechercher
   * @return Les crit�res permettant de rechercher les prochains robots d'ench�res
   * � utiliser sur la vente en argument
   */
  protected CriteriaQuery<BOT> getCriteriaForNextBot(AUCTION auction)
  {
    CriteriaBuilder builder = this.getCriteriaBuilder();

    CriteriaQuery<BOT> criteria = this.createCriteria();
    Root<BOT> bot_ = criteria.from(this.getEntityClass());
    // Conditions sur la vente aux ench�res et le potentielle compte utilisateur � ignorer
    Predicate condition1 = this.getConditionForAuctionAndAccount(
        bot_, auction, auction.getLastBidder(), true);
    // Lien avec la table des comptes utilisateurs
    bot_.fetch(Bot_.account);
    Join<BOT, Account> join_ = bot_.join(Bot_.accountLink);
    // Conditions sur le nombre de cr�dits disponibles
    Predicate condition2 = builder.greaterThanOrEqualTo(join_.get(Account_.creditNb),
                                                        auction.getTerms().getCreditNbPerBid());
    // Conditions sur les positions des ench�res � faire par le robot
    Predicate condition3 = builder.lessThanOrEqualTo(bot_.get(Bot_.minBidPosition),
                                                     auction.getBidNb() + 1);
    Predicate condition4 = builder.greaterThanOrEqualTo(bot_.get(Bot_.maxBidPosition),
                                                        auction.getBidNb() + 1);
    // Conditions sur le nombre d'ench�res � faire par le robot
    Predicate condition5 = builder.greaterThan(bot_.get(Bot_.maxBidNb),
                                               bot_.get(Bot_.usedBidNb));

    Predicate condition = builder.and(condition1, condition2, condition3, condition4, condition5);
    criteria.where(condition);
    return criteria;
  }


  /**
   * Cette fonction permet de modifier le robot d'ench�res en argument
   * @param bot {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#remove(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  public BOT update(BOT bot) throws PersistenceException
  {
    return super.update(bot);
  }
  /**
   * Cette fonction permet de supprimer le robot d'ench�res en argument
   * @param bot {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#remove(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  public BOT remove(BOT bot) throws PersistenceException
  {
    return super.remove(bot);
  }

}
