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
 * @author Emeric Fill�tre
 */
public interface IBotDaoStub<BOT extends Bot<BOT, AUCTION, BID>,
                             AUCTION extends Auction<AUCTION, BID, ?, ?, ?>,
                             BID extends Bid<BID, AUCTION, ?>>
       extends IAccountBasedEntityMultipleDaoStub<BOT, Long, Account>
{
  /**
   * Cette fonction permet de r�cup�rer la liste d'ench�res en fonction de leur
   * vente
   * @param auction Vente des ench�res � rechercher
   * @return La liste d'ench�res trouv�es
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  public Bid4WinList<BOT> findListByAuction(AUCTION auction) throws PersistenceException;
  /**
   * Cette fonction permet de r�cup�rer la liste d'ench�res en fonction de l'identifiant
   * de leur vente
   * @param auctionId Identifiant de la vente des ench�res � rechercher
   * @return La liste d'ench�res trouv�es
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  public Bid4WinList<BOT> findListByAuctionId(String auctionId) throws PersistenceException;

  /**
   * Cette fonction permet de r�cup�rer le robot d'ench�res en fonction de sa vente
   * et de son compte utilisateur
   * @param auction Vente du robot d'ench�res � rechercher
   * @param account Compte utilisateur du robot d'ench�res � rechercher
   * @return Le robot d'ench�res potentiellement trouv�
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  public BOT findOneByAuctionAndAccount(AUCTION auction, Account account) throws PersistenceException;
  /**
   * Cette fonction permet de r�cup�rer le robot d'ench�res en fonction des identifiants
   * de sa vente et de son compte utilisateur
   * @param auctionId Identifiant de la vente du robot d'ench�re � rechercher
   * @param accountId Identifiant du compte utilisateur du robot d'ench�res � rechercher
   * @return Le robot d'ench�res potentiellement trouv�
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  public BOT findOneByAuctionIdAndAccountId(String auctionId, String accountId) throws PersistenceException;

  /**
   * Cette fonction permet de r�cup�rer la liste des prochains robots d'ench�res
   * � utiliser sur la vente en argument
   * @param auction Vente des prochains robots d'ench�res � rechercher
   * @return La liste des prochains robot d'ench�res trouv�s
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  public Bid4WinList<BOT> findNextBotList(AUCTION auction) throws PersistenceException;
}
