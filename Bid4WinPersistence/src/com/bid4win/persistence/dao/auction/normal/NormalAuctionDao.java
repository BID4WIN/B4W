package com.bid4win.persistence.dao.auction.normal;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.persistence.dao.account.credit.auction.CreditInvolvementNormalDao;
import com.bid4win.persistence.dao.auction.AuctionDao_;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementNormal;
import com.bid4win.persistence.entity.auction.AuctionStatus;
import com.bid4win.persistence.entity.auction.normal.NormalAuction;
import com.bid4win.persistence.entity.auction.normal.NormalAuction_;
import com.bid4win.persistence.entity.auction.normal.NormalBid;
import com.bid4win.persistence.entity.auction.normal.NormalCancelPolicy;
import com.bid4win.persistence.entity.auction.normal.NormalSchedule;
import com.bid4win.persistence.entity.auction.normal.NormalSchedule_;
import com.bid4win.persistence.entity.auction.normal.NormalTerms;
import com.bid4win.persistence.entity.sale.SaleStep;

/**
 * DAO pour les entités de la classe NormalAuction<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("NormalAuctionDao")
@Scope("singleton")
public class NormalAuctionDao
       extends AuctionDao_<NormalAuction, NormalBid, NormalSchedule,
                           NormalTerms, NormalCancelPolicy, CreditInvolvementNormal>
{
  /** DAO de gestion des enchères des ventes normales */
  private NormalBidDao bidDao;
  /** DAO de gestion des implication de crédits sur les ventes aux enchères normales */
  @Autowired
  @Qualifier("CreditInvolvementNormalDao")
  private CreditInvolvementNormalDao involvementDao;

  /**
   * Constructeur
   */
  protected NormalAuctionDao()
  {
    super(NormalAuction.class);
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
  protected Path<SaleStep> getSaleStepPath(Root<NormalAuction> root)
  {
    return root.get(NormalAuction_.saleStep);
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
  protected Path<AuctionStatus> getAuctionStatusPath(Root<NormalAuction> root)
  {
    return root.get(NormalAuction_.auctionStatus);
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
  protected Path<Bid4WinDate> getStartDatePath(Root<NormalAuction> root)
  {
    return root.get(NormalAuction_.schedule).get(NormalSchedule_.startDate);
  }

  /**
   * Getter du DAO de gestion des enchères des ventes normales
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionDao_#getBidDao()
   */
  @Override
  protected NormalBidDao getBidDao()
  {
    return this.bidDao;
  }
  /**
   * Setter du DAO de gestion des enchères des ventes normales
   * @param bidDao DAO de gestion des enchères des ventes normales
   * @throws ModelArgumentException Si le DAO des enchères a déjà été positionné
   */
  @Autowired
  @Qualifier("NormalBidDao")
  protected void setBidDao(NormalBidDao bidDao) throws ModelArgumentException
  {
    UtilObject.checkNull("bidDao", this.getBidDao());
    this.bidDao = bidDao;
  }
  /**
   * Getter du DAO de gestion des implication de crédits sur les ventes aux enchères
   * normales
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.dao.auction.AuctionAbstractDao_#getInvolvementDao()
   */
  @Override
  protected CreditInvolvementNormalDao getInvolvementDao()
  {
    return this.involvementDao;
  }
}
