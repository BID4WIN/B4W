package com.bid4win.service.auction.normal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.manager.auction.normal.NormalAuctionManager;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementNormal;
import com.bid4win.persistence.entity.auction.normal.NormalAuction;
import com.bid4win.persistence.entity.auction.normal.NormalBid;
import com.bid4win.persistence.entity.auction.normal.NormalBidHistory;
import com.bid4win.persistence.entity.auction.normal.NormalBot;
import com.bid4win.persistence.entity.auction.normal.NormalCancelPolicy;
import com.bid4win.persistence.entity.auction.normal.NormalSchedule;
import com.bid4win.persistence.entity.auction.normal.NormalTerms;
import com.bid4win.service.auction.AuctionInternalService_;

/**
 * Manager de gestion des ventes aux enchères normales incluant la gestion des
 * transactions ainsi que celle des habilitations<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("NormalAuctionInternalService")
@Scope("singleton")
public class NormalAuctionInternalService
       extends AuctionInternalService_<NormalAuction, NormalBid, NormalBidHistory, NormalSchedule,
                                       NormalTerms, NormalCancelPolicy, CreditInvolvementNormal,
                                       NormalBot, NormalAuctionInternalHandler, NormalAuctionInternalService>
{
  /** Référence du manager de gestion des ventes aux enchères normales */
  @Autowired
  @Qualifier("NormalAuctionManager")
  private NormalAuctionManager manager = null;

  /**
   *
   * TODO A COMMENTER
   * @param auction {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.service.auction.AuctionAbstractInternalService_#createHandler(com.bid4win.persistence.entity.auction.AuctionAbstract)
   */
  @Override
  protected NormalAuctionInternalHandler createHandler(NormalAuction auction)
  {
    return new NormalAuctionInternalHandler(auction, this.self());
  }

  /**
   * Getter de la référence du manager de gestion des ventes aux enchères
   * normales
   * @return {@inheritDoc}
   * @see com.bid4win.service.auction.AuctionInternalService_#getManager()
   */
  @Override
  protected NormalAuctionManager getManager()
  {
    return this.manager;
  }
}
