package com.bid4win.persistence.dao.auction;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.dao.account.IAccountBasedEntityMultipleDaoStub;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.auction.AuctionAbstract;
import com.bid4win.persistence.entity.auction.BidAbstract;
import com.bid4win.persistence.entity.auction.BidHistory;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <BID> TODO A COMMENTER<BR>
 * @param <AUCTION> TODO A COMMENTER<BR>
 * @param <HISTORY> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public interface IBidAbstractDaoStub<BID extends BidAbstract<BID, AUCTION, HISTORY>,
                                     AUCTION extends AuctionAbstract<AUCTION, ?, ?, ?, ?>,
                                     HISTORY extends BidHistory<HISTORY, AUCTION, ?>>
       extends IAccountBasedEntityMultipleDaoStub<BID, Long, Account>
{
  /**
   * Cette fonction permet de r�cup�rer la liste d'ench�res en fonction de leur
   * vente
   * @param auction Vente des ench�res � rechercher
   * @return La liste d'ench�res trouv�es
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  public Bid4WinList<BID> findListByAuction(AUCTION auction) throws PersistenceException;
  /**
   * Cette fonction permet de r�cup�rer la liste d'ench�res en fonction de l'identifiant
   * de leur vente
   * @param auctionId Identifiant de la vente des ench�res � rechercher
   * @return La liste d'ench�res trouv�es
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  public Bid4WinList<BID> findListByAuctionId(String auctionId) throws PersistenceException;
}
