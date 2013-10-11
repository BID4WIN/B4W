package com.bid4win.persistence.dao.auction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.dao.account.AccountBasedEntityMultipleDao_;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.auction.AuctionAbstract;
import com.bid4win.persistence.entity.auction.AuctionAbstract_;
import com.bid4win.persistence.entity.auction.BidAbstract;
import com.bid4win.persistence.entity.auction.BidAbstract_;
import com.bid4win.persistence.entity.auction.BidHistory;

/**
 * DAO pour les entités de la classe BidAbstract<BR>
 * <BR>
 * @param <BID> Définition du type d'enchère gérée par le DAO<BR>
 * @param <AUCTION> Définition du type de vente des enchères gérées par le DAO<BR>
 * @param <HISTORY> Définition de la classe d'historique associée aux enchères
 * gérées par le DAO<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class BidAbstractDao_<BID extends BidAbstract<BID, AUCTION, HISTORY>,
                                      AUCTION extends AuctionAbstract<AUCTION, ?, ?, ?, ?>,
                                      HISTORY extends BidHistory<HISTORY, AUCTION, ?>>
       extends AccountBasedEntityMultipleDao_<BID, Long, Account>
{
  /**
   * Constructeur
   * @param bidClass Classe de l'enchère gérée par le DAO
   */
  protected BidAbstractDao_(Class<BID> bidClass)
  {
    super(bidClass);
  }

  /**
   * Cette fonction permet de récupérer la liste d'enchères en fonction de leur
   * vente
   * @param auction Vente des enchères à rechercher
   * @return La liste d'enchères trouvées
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  public Bid4WinList<BID> findListByAuction(AUCTION auction) throws PersistenceException
  {
    return super.findList(this.getCriteriaForAuction(auction));
  }
  /**
   * Cette fonction permet de récupérer la liste d'enchères en fonction de l'identifiant
   * de leur vente
   * @param auctionId Identifiant de la vente des enchères à rechercher
   * @return La liste d'enchères trouvées
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  public Bid4WinList<BID> findListByAuctionId(String auctionId) throws PersistenceException
  {
    return super.findList(this.getCriteriaForAuctionId(auctionId));
  }

  /**
   * Cette méthode permet de construire les critères permettant de rechercher les
   * enchères dont la vente est précisée en argument
   * @param auction Vente des enchères à rechercher
   * @return Les critères permettant de rechercher les enchères en fonction de
   * leur vente
   */
  protected CriteriaQuery<BID> getCriteriaForAuction(AUCTION auction)
  {
    return this.getCriteriaForAuctionId(auction.getId());
  }
  /**
   * Cette méthode permet de construire les critères permettant de rechercher les
   * enchères dont l'identifiant de la vente est précisé en argument
   * @param auctionId Identifiant de la vente des enchères à rechercher
   * @return Les critères permettant de rechercher les enchères en fonction de l'
   * identifiant de leur vente
   */
  protected CriteriaQuery<BID> getCriteriaForAuctionId(String auctionId)
  {
    CriteriaBuilder builder = this.getCriteriaBuilder();

    CriteriaQuery<BID> criteria = this.createCriteria();
    Root<BID> bid_ = criteria.from(this.getEntityClass());
    Path<String> auctionId_ = bid_.get(BidAbstract_.auction).get(AuctionAbstract_.id);
    Predicate condition = builder.equal(auctionId_, auctionId);
    criteria.where(condition);
    return criteria;
  }

  /**
   * Cette fonction permet d'ajouter l'enchère en argument
   * @param bid {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityDao_#add(com.bid4win.commons.persistence.entity.account.AccountBasedEntity)
   */
  @Override
  public BID add(BID bid) throws PersistenceException
  {
    // Ajoute l'enchère en paramètre
    return super.add(bid);
  }
  /**
   * Cette méthode permet de supprimer toutes les enchères d'une vente et d'en créer
   * leur historique
   * @param auction Vente aux enchères à historiser
   * @return Les enchères historisées
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si la creation d'un des historiques échoue
   */
  protected Bid4WinList<BID> historize(AUCTION auction)
            throws PersistenceException, UserException
  {
    Bid4WinList<BID> list  = this.findListByAuction(auction);
    for(BID bid : list)
    {
      this.historize(bid);
    }
    return list;
  }
  /**
   * Cette méthode permet de supprimer l'enchère en argument et d'en créer son historique
   * @param bid Enchère à historiser
   * @return L'enchère historisée
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si la creation de l'historique échoue
   */
  protected BID historize(BID bid) throws PersistenceException, UserException
  {
    this.getHistoryDao().add(bid.toHistory());
    return this.remove(bid);
  }

  /**
   * Getter du DAO de gestion des historiques d'enchères
   * @return Le DAO de gestion des historiques d'enchères
   */
  protected abstract BidHistoryDao_<HISTORY, AUCTION> getHistoryDao();
}
