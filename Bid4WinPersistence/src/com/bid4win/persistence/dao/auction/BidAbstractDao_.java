package com.bid4win.persistence.dao.auction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.dao.account.AccountBasedEntityMultipleDao_;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.auction.AuctionAbstract;
import com.bid4win.persistence.entity.auction.AuctionAbstract_;
import com.bid4win.persistence.entity.auction.BidAbstract;
import com.bid4win.persistence.entity.auction.BidAbstract_;
import com.bid4win.persistence.entity.auction.BidHistory;

/**
 * DAO pour les entit�s de la classe BidAbstract<BR>
 * <BR>
 * @param <BID> D�finition du type d'ench�re g�r�e par le DAO<BR>
 * @param <AUCTION> D�finition du type de vente des ench�res g�r�es par le DAO<BR>
 * @param <HISTORY> D�finition de la classe d'historique associ�e aux ench�res
 * g�r�es par le DAO<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class BidAbstractDao_<BID extends BidAbstract<BID, AUCTION, HISTORY>,
                                      AUCTION extends AuctionAbstract<AUCTION, ?, ?, ?, ?>,
                                      HISTORY extends BidHistory<HISTORY, AUCTION, ?>>
       extends AccountBasedEntityMultipleDao_<BID, Long, Account>
{
  /**
   * Constructeur
   * @param bidClass Classe de l'ench�re g�r�e par le DAO
   */
  protected BidAbstractDao_(Class<BID> bidClass)
  {
    super(bidClass);
  }

  /**
   * Cette fonction permet de r�cup�rer la liste d'ench�res en fonction de leur
   * vente
   * @param auction Vente des ench�res � rechercher
   * @return La liste d'ench�res trouv�es
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  public Bid4WinList<BID> findListByAuction(AUCTION auction) throws PersistenceException
  {
    return super.findList(this.getCriteriaForAuction(auction));
  }
  /**
   * Cette fonction permet de r�cup�rer la liste d'ench�res en fonction de l'identifiant
   * de leur vente
   * @param auctionId Identifiant de la vente des ench�res � rechercher
   * @return La liste d'ench�res trouv�es
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  public Bid4WinList<BID> findListByAuctionId(String auctionId) throws PersistenceException
  {
    return super.findList(this.getCriteriaForAuctionId(auctionId));
  }

  /**
   * Cette m�thode permet de construire les crit�res permettant de rechercher les
   * ench�res dont la vente est pr�cis�e en argument
   * @param auction Vente des ench�res � rechercher
   * @return Les crit�res permettant de rechercher les ench�res en fonction de
   * leur vente
   */
  protected CriteriaQuery<BID> getCriteriaForAuction(AUCTION auction)
  {
    return this.getCriteriaForAuctionId(auction.getId());
  }
  /**
   * Cette m�thode permet de construire les crit�res permettant de rechercher les
   * ench�res dont l'identifiant de la vente est pr�cis� en argument
   * @param auctionId Identifiant de la vente des ench�res � rechercher
   * @return Les crit�res permettant de rechercher les ench�res en fonction de l'
   * identifiant de leur vente
   */
  protected CriteriaQuery<BID> getCriteriaForAuctionId(String auctionId)
  {
    CriteriaBuilder builder = this.getCriteriaBuilder();

    CriteriaQuery<BID> criteria = this.createCriteria();
    Root<BID> bid_ = criteria.from(this.getEntityClass());
    Path<String> auctionId_ = bid_.get(BidAbstract_.auction).get(AuctionAbstract_.id);
    Predicate condition = builder.equal(auctionId_, auctionId);
    criteria.where(condition);
    return criteria;
  }

  /**
   * Cette fonction permet d'ajouter l'ench�re en argument
   * @param bid {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityDao_#add(com.bid4win.commons.persistence.entity.account.AccountBasedEntity)
   */
  @Override
  public BID add(BID bid) throws PersistenceException
  {
    // Ajoute l'ench�re en param�tre
    return super.add(bid);
  }
  /**
   * Cette m�thode permet de supprimer toutes les ench�res d'une vente et d'en cr�er
   * leur historique
   * @param auction Vente aux ench�res � historiser
   * @return Les ench�res historis�es
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si la creation d'un des historiques �choue
   */
  protected Bid4WinList<BID> historize(AUCTION auction)
            throws PersistenceException, UserException
  {
    Bid4WinList<BID> list  = this.findListByAuction(auction);
    for(BID bid : list)
    {
      this.historize(bid);
    }
    return list;
  }
  /**
   * Cette m�thode permet de supprimer l'ench�re en argument et d'en cr�er son historique
   * @param bid Ench�re � historiser
   * @return L'ench�re historis�e
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si la creation de l'historique �choue
   */
  protected BID historize(BID bid) throws PersistenceException, UserException
  {
    this.getHistoryDao().add(bid.toHistory());
    return this.remove(bid);
  }

  /**
   * Getter du DAO de gestion des historiques d'ench�res
   * @return Le DAO de gestion des historiques d'ench�res
   */
  protected abstract BidHistoryDao_<HISTORY, AUCTION> getHistoryDao();
}
