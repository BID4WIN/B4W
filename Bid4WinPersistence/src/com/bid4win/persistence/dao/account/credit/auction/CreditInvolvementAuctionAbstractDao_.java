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
 * DAO pour les entit�s de la classe CreditInvolvementAuctionAbstract<BR>
 * <BR>
 * @param <INVOLVEMENT> D�finition du type d'implication g�r�e par le DAO<BR>
 * @param <USAGE> D�finition du type d'utilisation de cr�dits des implications
 * g�r�es par le DAO<BR>
 * @param <BUNDLE> Definition du type de lot de cr�dits utilis�s par les implications
 * g�r�es par le DAO<BR>
 * @param <AUCTION> D�finition du type de vente aux ench�res des implications g�r�es
 * par le DAO<BR>
 * @param <HISTORY> D�finition du type d'historique des implications g�r�es par
 * le DAO<BR>
 * <BR>
 * @author Emeric Fill�tre
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
   * @param involvementClass Classe de l'implication de cr�dits g�r�e par le DAO
   */
  protected CreditInvolvementAuctionAbstractDao_(Class<INVOLVEMENT> involvementClass)
  {
    super(involvementClass);
  }

  /**
   * Cette fonction permet de r�cup�rer la liste d'implications en fonction de leur
   * vente aux ench�res
   * @param auction Vente aux ench�res des implications � rechercher
   * @return La liste d'implications trouv�es
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  public Bid4WinList<INVOLVEMENT> findListByAuction(AUCTION auction) throws PersistenceException
  {
    return super.findList(this.getCriteriaForAuction(auction));
  }
  /**
   * Cette fonction permet de r�cup�rer la liste d'implications en fonction de l'
   * identifiant de leur vente aux ench�res
   * @param auctionId Identifiant de la vente aux ench�res des implications � rechercher
   * @return La liste d'implications trouv�es
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  public Bid4WinList<INVOLVEMENT> findListByAuctionId(String auctionId) throws PersistenceException
  {
    return super.findList(this.getCriteriaForAuctionId(auctionId));
  }

  /**
   * Cette m�thode permet de construire les crit�res permettant de rechercher les
   * implications dont la vente aux ench�res est pr�cis�e en argument
   * @param auction Vente aux ench�res des implications � rechercher
   * @return Les crit�res permettant de rechercher les implications en fonction
   * de leur vente aux ench�res
   */
  protected CriteriaQuery<INVOLVEMENT> getCriteriaForAuction(AUCTION auction)
  {
    return this.getCriteriaForAuctionId(auction.getId());
  }
  /**
   * Cette m�thode permet de construire les crit�res permettant de rechercher les
   * implications dont l'identifiant de la vente aux ench�res est pr�cis� en argument
   * @param auctionId Identifiant de la vente aux ench�res des implications � rechercher
   * @return Les crit�res permettant de rechercher les implications en fonction
   * de l'identifiant de leur vente aux ench�res
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
