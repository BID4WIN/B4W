package trash;
//package com.bid4win.persistence.dao.auction.history;
//
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//
//import com.bid4win.commons.core.exception.PersistenceException;
//import com.bid4win.commons.core.exception.UserException;
//import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
//import com.bid4win.persistence.dao.auction.AuctionAbstractDao_;
//import com.bid4win.persistence.entity.auction.AuctionAbstract;
//import com.bid4win.persistence.entity.auction.history.AuctionHistory;
//import com.bid4win.persistence.entity.auction.history.BidHistory;
//import com.bid4win.persistence.entity.auction.history.ScheduleHistory;
//import com.bid4win.persistence.entity.auction.history.TermsHistory;
//
///**
// * DAO pour les entit�s de la classe AuctionHistory<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
//@Component("AuctionHistoryDao")
//@Scope("singleton")
//public class AuctionHistoryDao
//       extends AuctionAbstractDao_<AuctionHistory, BidHistory, ScheduleHistory, TermsHistory>
//{
//  /**
//   * Constructeur
//   */
//  protected AuctionHistoryDao()
//  {
//    super(AuctionHistory.class);
//  }
//
//  /**
//   * Cette m�thode permet de r�cup�rer l'unique historique potentiellement nul de
//   * la vente aux ench�res en argument
//   * @param auction Vente aux ench�res dont il faut r�cup�rer l'historique
//   * @return L'unique historique potentiellement nul de la vente aux ench�res en
//   * argument
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   */
//  public AuctionHistory findByAuction(AuctionAbstract<?, ?, ?, ?> auction)
//         throws PersistenceException
//  {
//   return this.findById(auction.getId());
//  }
//  /**
//   * Cette m�thode permet de r�cup�rer l'unique historique de la vente aux ench�res
//   * en argument
//   * @param auction Vente aux ench�res dont il faut r�cup�rer l'historique
//   * @return L'unique historique de la vente aux ench�res en argument
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   * @throws NotFoundEntityException Si aucune historique n'a pu �tre r�cup�r� pour
//   * la vente aux ench�res en argument
//   */
//  public AuctionHistory getByAuction(AuctionAbstract<?, ?, ?, ?> auction)
//         throws PersistenceException, NotFoundEntityException
//  {
//   return this.getById(auction.getId());
//  }
//  /**
//   * Cette m�thode permet de cr�er l'historique correspondant � la vente aux ench�res
//   * en argument
//   * @param auction Vente aux ench�res dont il faut cr�er l'historique
//   * @return L'historique cr�� pour la vente aux ench�res en argument
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   * @throws UserException TODO A COMMENTER
//   */
//  public AuctionHistory create(AuctionAbstract<?, ?, ?, ?> auction)
//         throws PersistenceException, UserException
//  {
//    // TODO ajouter le test de mise � jour d�finitif de la vente aux ench�res ...
//    return this.add(new AuctionHistory(auction));
//  }
//
//  /**
//   * Aucun lien n'est n�cessaire avec le DAO de gestion des historiques d'ench�res
//   * @return {@inheritDoc}
//   * @see com.bid4win.persistence.dao.auction.AuctionAbstractDao_#getBidDao()
//   */
//  @Override
//  protected BidHistoryDao getBidDao()
//  {
//    return null;
//  }
//  /**
//   * Aucun lien n'est n�cessaire avec le DAO de gestion des historiques de ventes
//   * aux ench�res
//   * @return {@inheritDoc}
//   * @see com.bid4win.persistence.dao.auction.AuctionAbstractDao_#getHistoryDao()
//   */
//  @Override
//  protected AuctionHistoryDao getHistoryDao()
//  {
//    return null;
//  }
//}
