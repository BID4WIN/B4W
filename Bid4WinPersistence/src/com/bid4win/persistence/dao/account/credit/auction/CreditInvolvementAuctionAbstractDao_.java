package com.bid4win.persistence.dao.account.credit.auction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.persistence.dao.account.credit.CreditInvolvementDao_;
import com.bid4win.persistence.entity.account.credit.CreditBundleAbstract;
import com.bid4win.persistence.entity.account.credit.CreditBundleHistory;
import com.bid4win.persistence.entity.account.credit.CreditUsageAbstract;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementAuctionAbstract;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementAuction_;
import com.bid4win.persistence.entity.auction.AuctionAbstract;
import com.bid4win.persistence.entity.auction.AuctionAbstract_;

/**
 * DAO pour les entités de la classe CreditInvolvementAuctionAbstract<BR>
 * <BR>
 * @param <INVOLVEMENT> Définition du type d'implication gérée par le DAO<BR>
 * @param <USAGE> Définition du type d'utilisation de crédits des implications
 * gérées par le DAO<BR>
 * @param <BUNDLE> Definition du type de lot de crédits utilisés par les implications
 * gérées par le DAO<BR>
 * @param <AUCTION> Définition du type de vente aux enchères des implications gérées
 * par le DAO<BR>
 * @param <HISTORY> Définition du type d'historique des implications gérées par
 * le DAO<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class CreditInvolvementAuctionAbstractDao_<INVOLVEMENT extends CreditInvolvementAuctionAbstract<INVOLVEMENT, USAGE, BUNDLE, AUCTION, HISTORY>,
                                                           USAGE extends CreditUsageAbstract<USAGE, BUNDLE, INVOLVEMENT>,
                                                           BUNDLE extends CreditBundleAbstract<BUNDLE>,
                                                           AUCTION extends AuctionAbstract<AUCTION, ?, ?, ?, ?>,
                                                           HISTORY extends CreditInvolvementAuctionAbstract<HISTORY, ?, CreditBundleHistory, AUCTION, ?>>
       extends CreditInvolvementDao_<INVOLVEMENT, USAGE, BUNDLE, HISTORY>
{
  /**
   * Constructeur
   * @param involvementClass Classe de l'implication de crédits gérée par le DAO
   */
  protected CreditInvolvementAuctionAbstractDao_(Class<INVOLVEMENT> involvementClass)
  {
    super(involvementClass);
  }

  /**
   * Cette fonction permet de récupérer la liste d'implications en fonction de leur
   * vente aux enchères
   * @param auction Vente aux enchères des implications à rechercher
   * @return La liste d'implications trouvées
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  public Bid4WinList<INVOLVEMENT> findListByAuction(AUCTION auction) throws PersistenceException
  {
    return super.findList(this.getCriteriaForAuction(auction));
  }
  /**
   * Cette fonction permet de récupérer la liste d'implications en fonction de l'
   * identifiant de leur vente aux enchères
   * @param auctionId Identifiant de la vente aux enchères des implications à rechercher
   * @return La liste d'implications trouvées
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  public Bid4WinList<INVOLVEMENT> findListByAuctionId(String auctionId) throws PersistenceException
  {
    return super.findList(this.getCriteriaForAuctionId(auctionId));
  }

  /**
   * Cette méthode permet de construire les critères permettant de rechercher les
   * implications dont la vente aux enchères est précisée en argument
   * @param auction Vente aux enchères des implications à rechercher
   * @return Les critères permettant de rechercher les implications en fonction
   * de leur vente aux enchères
   */
  protected CriteriaQuery<INVOLVEMENT> getCriteriaForAuction(AUCTION auction)
  {
    return this.getCriteriaForAuctionId(auction.getId());
  }
  /**
   * Cette méthode permet de construire les critères permettant de rechercher les
   * implications dont l'identifiant de la vente aux enchères est précisé en argument
   * @param auctionId Identifiant de la vente aux enchères des implications à rechercher
   * @return Les critères permettant de rechercher les implications en fonction
   * de l'identifiant de leur vente aux enchères
   */
  protected CriteriaQuery<INVOLVEMENT> getCriteriaForAuctionId(String auctionId)
  {
    CriteriaBuilder builder = this.getCriteriaBuilder();

    CriteriaQuery<INVOLVEMENT> criteria = this.createCriteria();
    Root<INVOLVEMENT> bid_ = criteria.from(this.getEntityClass());
    Path<String> auctionId_ = bid_.get(CreditInvolvementAuction_.auction).get(AuctionAbstract_.id);
    Predicate condition = builder.equal(auctionId_, auctionId);
    criteria.where(condition);
    return criteria;
  }
}
