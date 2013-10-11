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
 * DAO pour les entit�s de la classe AuctionAbstract<BR>
 * <BR>
 * @param <AUCTION> D�finition du type de vente aux ench�res g�r�e par le DAO<BR>
 * @param <BID> D�finition des ench�res de la vente g�r�e par le DAO<BR>
 * @param <SCHEDULE> D�finition des �l�ments de planification des ventes aux ench�res
 * g�r�es par le DAO<BR>
 * @param <TERMS> D�finition des conditions des ventes aux ench�res g�r�es par le DAO<BR>
 * @param <CANCEL_POLICY> D�finition des politiques d'annulation des ventes aux
 * ench�res g�r�es par le DAO<BR>
 * @param <INVOLVEMENT> D�finition des implications de cr�dits sur les ventes aux
 * ench�res g�r�es par le DAO<BR>
 * <BR>
 * @author Emeric Fill�tre
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
   * @param auctionClass Classe de la vente aux ench�res g�r�e par le DAO
   */
  protected AuctionAbstractDao_(Class<AUCTION> auctionClass)
  {
    super(auctionClass);
  }

  /**
   * Cette m�thode permet de r�cup�rer toutes les ventes aux ench�res dont le status
   * appartient � un de ceux en argument ou toutes les ventes si aucun status n'
   * est pr�cis�
   * @param statusArray Status des ventes aux ench�res � r�cup�rer
   * @return Les ventes aux ench�res sont le status appartient � un de ceux en
   * argument (c'est � dire que lui ou un de ses parents correspond � un des status
   * donn�s)
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
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
   * A d�finir car :
   * Suite � un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
   * en compte si d�fini dans le metamodel de la super class : bug HHH-5024
   * TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
   * @param root TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected abstract Path<AuctionStatus> getAuctionStatusPath(Root<AUCTION> root);
  /**
   * A d�finir car :
   * Suite � un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
   * en compte si d�fini dans le metamodel de la super class : bug HHH-5024
   * TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
   * @param root TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected abstract Path<Bid4WinDate> getStartDatePath(Root<AUCTION> root);

  /**
   * Cette fonction permet d'historiser les ench�res de la vente en argument. Toutes
   * les ench�res pointant dessus seront d�plac�es vers leur table d'historique
   * @param auction Vente des ench�res � historiser
   * @return La vente des ench�res historis�es
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si les ench�res de la vente sont d�j� historis�es
   */
  public AUCTION historizeBids(AUCTION auction) throws PersistenceException, UserException
  {
    // Historise toutes les ench�res pointant sur la vente
    this.getBidDao().historize(auction);
    // Historise la vente aux ench�res
    return this.update(auction.historizeBids());
  }
  /**
   * Cette fonction permet d'historiser les implications sur la vente aux ench�res
   * en argument. Toutes les implications pointant dessus seront d�plac�es vers
   * leur table d'historique et la valeur r�elle des ench�res sera calcul�es
   * @param auction Vente aux ench�res des implications � historiser
   * @return La vente aux ench�res des implications historis�es
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si les implications de la vente aux ench�res sont d�j�
   * historis�es ou si un probl�me intervient lors du calcul de leur valeur totale
   */
  public AUCTION historizeInvolvement(AUCTION auction) throws PersistenceException, UserException
  {
    // TODO d�corr�ler la cr�ation des historiques d'implication de l'historisation de la vente !!!
    // Historise toutes les implications pointant sur la vente
    Bid4WinList<INVOLVEMENT> involvementList = this.getInvolvementDao().historize(auction);
    Amount involvementValue = new Amount(0);
    for(INVOLVEMENT involvement : involvementList)
    {
      involvementValue = involvementValue.add(involvement.getTotalValue());
    }
    // Historise la vente aux ench�res
    return this.update(auction.historizeInvolvements(involvementValue));
  }

  /**
   * Cette fonction doit d�finir le DAO des ench�res
   * @return Le DAO des ench�res
   */
  protected abstract BidAbstractDao_<BID, AUCTION, ?> getBidDao();
  /**
   * Cette fonction doit d�finir le DAO des implications
   * @return Le DAO des implications
   */
  protected abstract CreditInvolvementAuctionDao_<INVOLVEMENT, ?, AUCTION, ?> getInvolvementDao();
}
