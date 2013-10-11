package com.bid4win.manager.auction.normal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.manager.auction.AuctionManager_;
import com.bid4win.persistence.dao.account.credit.auction.CreditInvolvementNormalDao;
import com.bid4win.persistence.dao.auction.normal.NormalAuctionDao;
import com.bid4win.persistence.dao.auction.normal.NormalBidDao;
import com.bid4win.persistence.dao.auction.normal.NormalBotDao;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementNormal;
import com.bid4win.persistence.entity.auction.normal.NormalAuction;
import com.bid4win.persistence.entity.auction.normal.NormalBid;
import com.bid4win.persistence.entity.auction.normal.NormalBidHistory;
import com.bid4win.persistence.entity.auction.normal.NormalBot;
import com.bid4win.persistence.entity.auction.normal.NormalCancelPolicy;
import com.bid4win.persistence.entity.auction.normal.NormalSchedule;
import com.bid4win.persistence.entity.auction.normal.NormalTerms;
import com.bid4win.persistence.entity.price.Amount;
import com.bid4win.persistence.entity.product.Product;

/**
 * Manager de gestion des ventes aux ench�res normales incluant leur gestion m�tier<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Component("NormalAuctionManager")
@Scope("singleton")
public class NormalAuctionManager
       extends AuctionManager_<NormalAuction, NormalBid, NormalBidHistory,
                               NormalSchedule, NormalTerms, NormalCancelPolicy,
                               CreditInvolvementNormal, NormalBot>
{
  /** R�f�rence du DAO des ventes aux ench�res normales */
  @Autowired
  @Qualifier("NormalAuctionDao")
  private NormalAuctionDao auctionDao = null;
  /** R�f�rence du DAO des ench�res normales */
  @Autowired
  @Qualifier("NormalBidDao")
  private NormalBidDao bidDao = null;
  /** R�f�rence du DAO des robots d'ench�res normales */
  @Autowired
  @Qualifier("NormalBotDao")
  private NormalBotDao botDao = null;
  /** R�f�rence du DAO des implications de cr�dits sur les ventes aux ench�res normales */
  @Autowired
  @Qualifier("CreditInvolvementNormalDao")
  private CreditInvolvementNormalDao involvementDao = null;

  /**
   * Cette m�thode permet de construire une vente aux ench�res normale pour le
   * produit en argument
   * @param product {@inheritDoc}
   * @param schedule {@inheritDoc}
   * @param terms {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.manager.auction.AuctionAbstractManager_#createAuctionEntity(com.bid4win.persistence.entity.product.Product, com.bid4win.persistence.entity.auction.ScheduleAbstract, com.bid4win.persistence.entity.auction.TermsAbstract)
   */
  @Override
  protected NormalAuction createAuctionEntity(Product product, NormalSchedule schedule, NormalTerms terms)
            throws UserException
  {
    return new NormalAuction(product, schedule, terms);
  }
  /**
   *
   * TODO A COMMENTER
   * @param auction {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.manager.auction.AuctionAbstractManager_#getCancelPolicy(com.bid4win.persistence.entity.auction.AuctionAbstract)
   */
  @Override
  protected NormalCancelPolicy getCancelPolicy(NormalAuction auction) throws UserException
  {
    // TODO CALCULER REELLEMENT EN FONCTION DES PROPRIETES
    double threshold = auction.getProductPrice().getValue() * 0.02;
    int paidBonus = 1;
    return new NormalCancelPolicy(threshold, paidBonus);
  }
  /**
   * Cette m�thode permet de construire une implication de cr�dits du compte utilisateur
   * donn� sur la vente aux ench�res normale pr�cis�e en argument ou de r�cup�rer
   * celle d�j� existante
   * @param account {@inheritDoc}
   * @param auction {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.manager.auction.AuctionAbstractManager_#retreiveInvolvementEntity(com.bid4win.persistence.entity.account.Account, com.bid4win.persistence.entity.auction.AuctionAbstract)
   */
  @Override
  protected CreditInvolvementNormal retreiveInvolvementEntity(Account account, NormalAuction auction)
            throws UserException
  {
    // Recherche une implication de cr�dit potentiellement d�j� existante
    CreditInvolvementNormal involvement = account.getInvolvementNormal(auction);
    if(involvement == null)
    {
      involvement = new CreditInvolvementNormal(account, auction);
    }
    return involvement;
  }
  /**
   *
   * TODO A COMMENTER
   * @param auction {@inheritDoc}
   * @param account {@inheritDoc}
   * @param minBid {@inheritDoc}
   * @param maxBid {@inheritDoc}
   * @param maxBidNb {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.manager.auction.AuctionManager_#createBotEntity(com.bid4win.persistence.entity.auction.Auction, com.bid4win.persistence.entity.account.Account, com.bid4win.persistence.entity.price.Amount, com.bid4win.persistence.entity.price.Amount, int)
   */
  @Override
  protected NormalBot createBotEntity(NormalAuction auction, Account account,
                                      Amount minBid, Amount maxBid, int maxBidNb)
            throws UserException
  {
    return new NormalBot(account, auction, minBid, maxBid, maxBidNb);
  }

  /**
   * Getter de la r�f�rence du DAO des ventes aux ench�res normales
   * @return {@inheritDoc}
   * @see com.bid4win.manager.auction.AuctionManager_#getAuctionDao()
   */
  @Override
  protected NormalAuctionDao getAuctionDao()
  {
    return this.auctionDao;
  }
  /**
   * Getter de la r�f�rence du DAO des ench�res normales
   * @return {@inheritDoc}
   * @see com.bid4win.manager.auction.AuctionManager_#getBidDao()
   */
  @Override
  protected NormalBidDao getBidDao()
  {
    return this.bidDao;
  }
  /**
   * Getter de la r�f�rence du DAO des robots d'ench�res normales
   * @return {@inheritDoc}
   * @see com.bid4win.manager.auction.AuctionManager_#getBotDao()
   */
  @Override
  protected NormalBotDao getBotDao()
  {
    return this.botDao;
  }
  /**
   * Getter de la r�f�rence du DAO des implications de cr�dits sur les ventes aux
   * ench�res normales
   * @return {@inheritDoc}
   * @see com.bid4win.manager.auction.AuctionAbstractManager_#getInvolvementDao()
   */
  @Override
  protected CreditInvolvementNormalDao getInvolvementDao()
  {
    return this.involvementDao;
  }
}
