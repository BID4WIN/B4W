//package com.bid4win.service.auction.prebooked;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.commons.core.exception.PersistenceException;
//import com.bid4win.commons.core.exception.UserException;
//import com.bid4win.commons.security.exception.AuthenticationException;
//import com.bid4win.commons.security.exception.AuthorizationException;
//import com.bid4win.manager.auction.prebooked.PrebookedAuctionManager;
//import com.bid4win.persistence.entity.account.Account;
//import com.bid4win.persistence.entity.auction.prebooked.PrebookedAuction;
//import com.bid4win.persistence.entity.auction.prebooked.PrebookedBid;
//import com.bid4win.persistence.entity.auction.prebooked.PrebookedInvolvedBundle;
//import com.bid4win.persistence.entity.auction.prebooked.PrebookedInvolvedCredit;
//import com.bid4win.persistence.entity.auction.prebooked.PrebookedSchedule;
//import com.bid4win.persistence.entity.auction.prebooked.PrebookedTerms;
//import com.bid4win.service.auction.AuctionService_;
//
///**
// *
// * TODO A COMMENTER<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//@Component("PrebookedAuctionService")
//@Scope("singleton")
//public class PrebookedAuctionService
//       extends AuctionService_<PrebookedAuction, PrebookedBid,
//                              PrebookedSchedule, PrebookedTerms,
//                              PrebookedInvolvedCredit, PrebookedInvolvedBundle>
//{
//  /** Référence du manager de gestion des ventes aux enchères avec pré-inscription */
//  @Autowired
//  @Qualifier("PrebookedAuctionManager")
//  private PrebookedAuctionManager manager = null;
//
//  /**
//   * Cette méthode permet de pré-inscrire un compte utilisateur sur une vente aux
//   * enchères
//   * @param auctionId Identifiant de la vente sur laquelle se pré-inscrire
//   * @param accountId Identifiant du compte utilisateur à pré-inscrire
//   * @return Le compte utilisateur pré-inscrit sur la vente aux enchères choisie
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante
//   * @throws ModelArgumentException Si un problème intervient lors de la pré-inscription
//   * @throws UserException Si le compte utilisateur n'a pas assez de crédits ou
//   * est déjà pré-inscrit
//   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
//   * @throws AuthorizationException Si le compte utilisateur connecté ne correspond
//   * pas à celui à modifier ou n'a pas le niveau d'autorisation suffisant
//   */
//  public Account prebook(String auctionId, String accountId)
//         throws PersistenceException, ModelArgumentException, UserException, AuthenticationException, AuthorizationException
//  {
//    try
//    {
//      return this.getManager().prebook(auctionId, accountId);
//    }
//    catch(RuntimeException ex)
//    {
//      throw new PersistenceException();
//    }
//  }
//
//  /**
//   *
//   * TODO A COMMENTER
//   * @return {@inheritDoc}
//   * @see com.bid4win.service.auction.AuctionService_#getManager()
//   */
//  @Override
//  protected PrebookedAuctionManager getManager()
//  {
//    return this.manager;
//  }
//}
