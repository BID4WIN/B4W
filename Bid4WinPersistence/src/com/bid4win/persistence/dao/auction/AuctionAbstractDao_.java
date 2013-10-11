package com.bid4win.persistence.dao.auction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.persistence.dao.account.credit.auction.CreditInvolvementAuctionDao_;
import com.bid4win.persistence.dao.sale.SaleDao_;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementAuction;
import com.bid4win.persistence.entity.auction.AuctionAbstract;
import com.bid4win.persistence.entity.auction.AuctionStatus;
import com.bid4win.persistence.entity.auction.BidAbstract;
import com.bid4win.persistence.entity.auction.CancelPolicyAbstract;
import com.bid4win.persistence.entity.auction.ScheduleAbstract;
import com.bid4win.persistence.entity.auction.Status;
import com.bid4win.persistence.entity.auction.TermsAbstract;
import com.bid4win.persistence.entity.price.Amount;

/**
 * DAO pour les entités de la classe AuctionAbstract<BR>
 * <BR>
 * @param <AUCTION> Définition du type de vente aux enchères gérée par le DAO<BR>
 * @param <BID> Définition des enchères de la vente gérée par le DAO<BR>
 * @param <SCHEDULE> Définition des éléments de planification des ventes aux enchères
 * gérées par le DAO<BR>
 * @param <TERMS> Définition des conditions des ventes aux enchères gérées par le DAO<BR>
 * @param <CANCEL_POLICY> Définition des politiques d'annulation des ventes aux
 * enchères gérées par le DAO<BR>
 * @param <INVOLVEMENT> Définition des implications de crédits sur les ventes aux
 * enchères gérées par le DAO<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class AuctionAbstractDao_<AUCTION extends AuctionAbstract<AUCTION, BID, SCHEDULE, TERMS, CANCEL_POLICY>,
                                          BID extends BidAbstract<BID, AUCTION, ?>,
                                          SCHEDULE extends ScheduleAbstract<SCHEDULE>,
                                          TERMS extends TermsAbstract<TERMS>,
                                          CANCEL_POLICY extends CancelPolicyAbstract<CANCEL_POLICY>,
                                          INVOLVEMENT extends CreditInvolvementAuction<INVOLVEMENT, ?, AUCTION, ?>>
       extends SaleDao_<AUCTION>
{
  /**
   * Constructeur
   * @param auctionClass Classe de la vente aux enchères gérée par le DAO
   */
  protected AuctionAbstractDao_(Class<AUCTION> auctionClass)
  {
    super(auctionClass);
  }

  /**
   * Cette méthode permet de récupérer toutes les ventes aux enchères dont le status
   * appartient à un de ceux en argument ou toutes les ventes si aucun status n'
   * est précisé
   * @param statusArray Status des ventes aux enchères à récupérer
   * @return Les ventes aux enchères sont le status appartient à un de ceux en
   * argument (c'est à dire que lui ou un de ses parents correspond à un des status
   * donnés)
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  public Bid4WinList<AUCTION> findListByStatus(Status ... statusArray)
         throws PersistenceException
  {
    return this.findList(this.getCriteriaForStatus(statusArray));
  }
  /**
   *
   * TODO A COMMENTER
   * @param statusArray TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  protected CriteriaQuery<AUCTION> getCriteriaForStatus(Status ... statusArray)
            throws PersistenceException
  {
    CriteriaBuilder builder = this.getCriteriaBuilder();

    CriteriaQuery<AUCTION> criteria = this.createCriteria();
    Root<AUCTION> auction_ = criteria.from(this.getEntityClass());
    if(statusArray.length != 0)
    {
      Path<AuctionStatus> auctionStatus_ = this.getAuctionStatusPath(auction_);
      Bid4WinSet<Status> statusSet = new Bid4WinSet<Status>();
      for(Status status : statusArray)
      {
        statusSet.add(status);
        statusSet.addAll(status.getRecursiveSubtypeSet());
      }
      Predicate[] predicates = new Predicate[statusSet.size()];
      int i = 0;
      try
      {
        for(Status status : statusSet)
        {
          predicates[i++] = builder.equal(auctionStatus_, new AuctionStatus(status));
        }
      }
      catch(UserException ex)
      {
        throw new PersistenceException(ex);
      }
      Predicate condition = builder.or(predicates);
      criteria.where(condition);
    }
    Order order = builder.asc(this.getStartDatePath(auction_));
    criteria.orderBy(order);
    return criteria;
  }
  /**
   * A définir car :
   * Suite à un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
   * en compte si défini dans le metamodel de la super class : bug HHH-5024
   * TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
   * @param root TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected abstract Path<AuctionStatus> getAuctionStatusPath(Root<AUCTION> root);
  /**
   * A définir car :
   * Suite à un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
   * en compte si défini dans le metamodel de la super class : bug HHH-5024
   * TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
   * @param root TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected abstract Path<Bid4WinDate> getStartDatePath(Root<AUCTION> root);

  /**
   * Cette fonction permet d'historiser les enchères de la vente en argument. Toutes
   * les enchères pointant dessus seront déplacées vers leur table d'historique
   * @param auction Vente des enchères à historiser
   * @return La vente des enchères historisées
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si les enchères de la vente sont déjà historisées
   */
  public AUCTION historizeBids(AUCTION auction) throws PersistenceException, UserException
  {
    // Historise toutes les enchères pointant sur la vente
    this.getBidDao().historize(auction);
    // Historise la vente aux enchères
    return this.update(auction.historizeBids());
  }
  /**
   * Cette fonction permet d'historiser les implications sur la vente aux enchères
   * en argument. Toutes les implications pointant dessus seront déplacées vers
   * leur table d'historique et la valeur réelle des enchères sera calculées
   * @param auction Vente aux enchères des implications à historiser
   * @return La vente aux enchères des implications historisées
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si les implications de la vente aux enchères sont déjà
   * historisées ou si un problème intervient lors du calcul de leur valeur totale
   */
  public AUCTION historizeInvolvement(AUCTION auction) throws PersistenceException, UserException
  {
    // TODO décorréler la création des historiques d'implication de l'historisation de la vente !!!
    // Historise toutes les implications pointant sur la vente
    Bid4WinList<INVOLVEMENT> involvementList = this.getInvolvementDao().historize(auction);
    Amount involvementValue = new Amount(0);
    for(INVOLVEMENT involvement : involvementList)
    {
      involvementValue = involvementValue.add(involvement.getTotalValue());
    }
    // Historise la vente aux enchères
    return this.update(auction.historizeInvolvements(involvementValue));
  }

  /**
   * Cette fonction doit définir le DAO des enchères
   * @return Le DAO des enchères
   */
  protected abstract BidAbstractDao_<BID, AUCTION, ?> getBidDao();
  /**
   * Cette fonction doit définir le DAO des implications
   * @return Le DAO des implications
   */
  protected abstract CreditInvolvementAuctionDao_<INVOLVEMENT, ?, AUCTION, ?> getInvolvementDao();
}
