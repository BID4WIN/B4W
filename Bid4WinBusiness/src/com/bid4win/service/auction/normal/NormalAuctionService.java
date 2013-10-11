package com.bid4win.service.auction.normal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementNormal;
import com.bid4win.persistence.entity.auction.normal.NormalAuction;
import com.bid4win.persistence.entity.auction.normal.NormalBid;
import com.bid4win.persistence.entity.auction.normal.NormalBidHistory;
import com.bid4win.persistence.entity.auction.normal.NormalBot;
import com.bid4win.persistence.entity.auction.normal.NormalCancelPolicy;
import com.bid4win.persistence.entity.auction.normal.NormalSchedule;
import com.bid4win.persistence.entity.auction.normal.NormalTerms;
import com.bid4win.service.auction.AuctionService_;

/**
 * Manager de gestion des ventes aux ench�res normales incluant la gestion des
 * transactions ainsi que celle des habilitations<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Component("NormalAuctionService")
@Scope("singleton")
public class NormalAuctionService
       extends AuctionService_<NormalAuction, NormalBid, NormalBidHistory, NormalSchedule,
                               NormalTerms, NormalCancelPolicy, CreditInvolvementNormal,
                               NormalBot, NormalAuctionService>
{
  /** R�f�rence du service interne de gestion des ventes aux ench�res normales */
  @Autowired
  @Qualifier("NormalAuctionInternalService")
  private NormalAuctionInternalService internal = null;

  /**
   * Getter de la r�f�rence du service interne de gestion des ventes aux ench�res
   * normales
   * @return {@inheritDoc}
   * @see com.bid4win.service.auction.AuctionService_#getInternal()
   */
  @Override
  protected NormalAuctionInternalService getInternal()
  {
    return this.internal;
  }
}
