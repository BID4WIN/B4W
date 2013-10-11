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
 * @author Emeric Fillâtre
 */
public interface IBidAbstractDaoStub<BID extends BidAbstract<BID, AUCTION, HISTORY>,
                                     AUCTION extends AuctionAbstract<AUCTION, ?, ?, ?, ?>,
                                     HISTORY extends BidHistory<HISTORY, AUCTION, ?>>
       extends IAccountBasedEntityMultipleDaoStub<BID, Long, Account>
{
  /**
   * Cette fonction permet de récupérer la liste d'enchères en fonction de leur
   * vente
   * @param auction Vente des enchères à rechercher
   * @return La liste d'enchères trouvées
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  public Bid4WinList<BID> findListByAuction(AUCTION auction) throws PersistenceException;
  /**
   * Cette fonction permet de récupérer la liste d'enchères en fonction de l'identifiant
   * de leur vente
   * @param auctionId Identifiant de la vente des enchères à rechercher
   * @return La liste d'enchères trouvées
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  public Bid4WinList<BID> findListByAuctionId(String auctionId) throws PersistenceException;
}
