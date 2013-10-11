package com.bid4win.persistence.dao.auction;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.dao.account.IAccountBasedEntityMultipleDaoStub;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.auction.Auction;
import com.bid4win.persistence.entity.auction.Bid;
import com.bid4win.persistence.entity.auction.Bot;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <BOT> TODO A COMMENTER<BR>
 * @param <AUCTION> TODO A COMMENTER<BR>
 * @param <BID> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public interface IBotDaoStub<BOT extends Bot<BOT, AUCTION, BID>,
                             AUCTION extends Auction<AUCTION, BID, ?, ?, ?>,
                             BID extends Bid<BID, AUCTION, ?>>
       extends IAccountBasedEntityMultipleDaoStub<BOT, Long, Account>
{
  /**
   * Cette fonction permet de récupérer la liste d'enchères en fonction de leur
   * vente
   * @param auction Vente des enchères à rechercher
   * @return La liste d'enchères trouvées
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  public Bid4WinList<BOT> findListByAuction(AUCTION auction) throws PersistenceException;
  /**
   * Cette fonction permet de récupérer la liste d'enchères en fonction de l'identifiant
   * de leur vente
   * @param auctionId Identifiant de la vente des enchères à rechercher
   * @return La liste d'enchères trouvées
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  public Bid4WinList<BOT> findListByAuctionId(String auctionId) throws PersistenceException;

  /**
   * Cette fonction permet de récupérer le robot d'enchères en fonction de sa vente
   * et de son compte utilisateur
   * @param auction Vente du robot d'enchères à rechercher
   * @param account Compte utilisateur du robot d'enchères à rechercher
   * @return Le robot d'enchères potentiellement trouvé
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  public BOT findOneByAuctionAndAccount(AUCTION auction, Account account) throws PersistenceException;
  /**
   * Cette fonction permet de récupérer le robot d'enchères en fonction des identifiants
   * de sa vente et de son compte utilisateur
   * @param auctionId Identifiant de la vente du robot d'enchère à rechercher
   * @param accountId Identifiant du compte utilisateur du robot d'enchères à rechercher
   * @return Le robot d'enchères potentiellement trouvé
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  public BOT findOneByAuctionIdAndAccountId(String auctionId, String accountId) throws PersistenceException;

  /**
   * Cette fonction permet de récupérer la liste des prochains robots d'enchères
   * à utiliser sur la vente en argument
   * @param auction Vente des prochains robots d'enchères à rechercher
   * @return La liste des prochains robot d'enchères trouvés
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  public Bid4WinList<BOT> findNextBotList(AUCTION auction) throws PersistenceException;
}
