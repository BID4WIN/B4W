package com.bid4win.persistence.dao.auction.prebooked;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.persistence.dao.account.credit.auction.CreditInvolvementPrebookedDao;
import com.bid4win.persistence.dao.auction.AuctionDao_;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementPrebooked;
import com.bid4win.persistence.entity.auction.AuctionStatus;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedAuction;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedAuction_;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedBid;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedCancelPolicy;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedSchedule;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedSchedule_;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedTerms;
import com.bid4win.persistence.entity.sale.SaleStep;

/**
 * DAO pour les entités de la classe PrebookedAuction<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("PrebookedAuctionDao")
@Scope("singleton")
public class PrebookedAuctionDao
       extends AuctionDao_<PrebookedAuction, PrebookedBid, PrebookedSchedule,
                           PrebookedTerms, PrebookedCancelPolicy, CreditInvolvementPrebooked>
{
  /** DAO de gestion des enchères des ventes avec pré-inscription */
  private PrebookedBidDao bidDao;
  /** DAO de gestion des implication de crédits sur les ventes aux enchères avec pré-inscription */
  @Autowired
  @Qualifier("CreditInvolvementPrebookedDao")
  private CreditInvolvementPrebookedDao involvementDao;

  /**
   * Constructeur
   */
  protected PrebookedAuctionDao()
  {
    super(PrebookedAuction.class);
  }

  /**
   * A définir car :
   * Suite à un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
   * en compte si défini dans le metamodel de la super class : bug HHH-5024
   * TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
   * @param root {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.sale.SaleDao_#getSaleStepPath(javax.persistence.criteria.Root)
   */
  @Override
  protected Path<SaleStep> getSaleStepPath(Root<PrebookedAuction> root)
  {
    return root.get(PrebookedAuction_.saleStep);
  }
  /**
   * A définir car :
   * Suite à un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
   * en compte si défini dans le metamodel de la super class : bug HHH-5024
   * TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
   * @param root {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionAbstractDao_#getAuctionStatusPath(javax.persistence.criteria.Root)
   */
  @Override
  protected Path<AuctionStatus> getAuctionStatusPath(Root<PrebookedAuction> root)
  {
    return root.get(PrebookedAuction_.auctionStatus);
  }
  /**
   * A définir car :
   * Suite à un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
   * en compte si défini dans le metamodel de la super class : bug HHH-5024
   * TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
   * @param root {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionAbstractDao_#getStartDatePath(javax.persistence.criteria.Root)
   */
  @Override
  protected Path<Bid4WinDate> getStartDatePath(Root<PrebookedAuction> root)
  {
    return root.get(PrebookedAuction_.schedule).get(PrebookedSchedule_.startDate);
  }

  /**
   * Getter du DAO de gestion des enchères des ventes avec pré-inscription
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionDao_#getBidDao()
   */
  @Override
  protected PrebookedBidDao getBidDao()
  {
    return this.bidDao;
  }
  /**
   * Setter du DAO de gestion des enchères des ventes avec pré-inscription
   * @param bidDao DAO de gestion des enchères des ventes avec pré-inscription
   * @throws ModelArgumentException Si le DAO des enchères a déjà été positionné
   */
  @Autowired
  @Qualifier("PrebookedBidDao")
  protected void setBidDao(PrebookedBidDao bidDao) throws ModelArgumentException
  {
    UtilObject.checkNull("bidDao", this.getBidDao());
    this.bidDao = bidDao;
  }
  /**
   * Getter du DAO de gestion des implication de crédits sur les ventes aux enchères
   * avec pré-inscription
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionAbstractDao_#getInvolvementDao()
   */
  @Override
  protected CreditInvolvementPrebookedDao getInvolvementDao()
  {
    return this.involvementDao;
  }
}
