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
// * DAO pour les entités de la classe AuctionHistory<BR>
// * <BR>
// * @author Emeric Fillâtre
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
//   * Cette méthode permet de récupérer l'unique historique potentiellement nul de
//   * la vente aux enchères en argument
//   * @param auction Vente aux enchères dont il faut récupérer l'historique
//   * @return L'unique historique potentiellement nul de la vente aux enchères en
//   * argument
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante
//   */
//  public AuctionHistory findByAuction(AuctionAbstract<?, ?, ?, ?> auction)
//         throws PersistenceException
//  {
//   return this.findById(auction.getId());
//  }
//  /**
//   * Cette méthode permet de récupérer l'unique historique de la vente aux enchères
//   * en argument
//   * @param auction Vente aux enchères dont il faut récupérer l'historique
//   * @return L'unique historique de la vente aux enchères en argument
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante
//   * @throws NotFoundEntityException Si aucune historique n'a pu être récupéré pour
//   * la vente aux enchères en argument
//   */
//  public AuctionHistory getByAuction(AuctionAbstract<?, ?, ?, ?> auction)
//         throws PersistenceException, NotFoundEntityException
//  {
//   return this.getById(auction.getId());
//  }
//  /**
//   * Cette méthode permet de créer l'historique correspondant à la vente aux enchères
//   * en argument
//   * @param auction Vente aux enchères dont il faut créer l'historique
//   * @return L'historique créé pour la vente aux enchères en argument
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante
//   * @throws UserException TODO A COMMENTER
//   */
//  public AuctionHistory create(AuctionAbstract<?, ?, ?, ?> auction)
//         throws PersistenceException, UserException
//  {
//    // TODO ajouter le test de mise à jour définitif de la vente aux enchères ...
//    return this.add(new AuctionHistory(auction));
//  }
//
//  /**
//   * Aucun lien n'est nécessaire avec le DAO de gestion des historiques d'enchères
//   * @return {@inheritDoc}
//   * @see com.bid4win.persistence.dao.auction.AuctionAbstractDao_#getBidDao()
//   */
//  @Override
//  protected BidHistoryDao getBidDao()
//  {
//    return null;
//  }
//  /**
//   * Aucun lien n'est nécessaire avec le DAO de gestion des historiques de ventes
//   * aux enchères
//   * @return {@inheritDoc}
//   * @see com.bid4win.persistence.dao.auction.AuctionAbstractDao_#getHistoryDao()
//   */
//  @Override
//  protected AuctionHistoryDao getHistoryDao()
//  {
//    return null;
//  }
//}
