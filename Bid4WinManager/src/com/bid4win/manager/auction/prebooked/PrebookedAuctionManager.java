//package com.bid4win.manager.auction.prebooked;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.bid4win.commons.core.exception.Bid4WinException;
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.commons.core.exception.PersistenceException;
//import com.bid4win.commons.core.exception.UserException;
//import com.bid4win.commons.persistence.entity.account.security.Role;
//import com.bid4win.commons.security.exception.AuthenticationException;
//import com.bid4win.commons.security.exception.AuthorizationException;
//import com.bid4win.manager.auction.AuctionManager_;
//import com.bid4win.persistence.entity.account.Account;
//import com.bid4win.persistence.entity.auction.prebooked.PrebookedAuction;
//import com.bid4win.persistence.entity.auction.prebooked.PrebookedBid;
//import com.bid4win.persistence.entity.auction.prebooked.PrebookedInvolvedBundle;
//import com.bid4win.persistence.entity.auction.prebooked.PrebookedInvolvedCredit;
//import com.bid4win.persistence.entity.auction.prebooked.PrebookedSchedule;
//import com.bid4win.persistence.entity.auction.prebooked.PrebookedTerms;
//
///**
// * Manager de gestion des ventes aux enchères avec pré-inscription incluant la
// * gestion des transactions ainsi que celle des habilitations<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//@Component("PrebookedAuctionManager")
//@Scope("singleton")
//public class PrebookedAuctionManager
//       extends AuctionManager_<PrebookedAuction, PrebookedBid,
//                              PrebookedSchedule, PrebookedTerms,
//                              PrebookedInvolvedCredit, PrebookedInvolvedBundle>
//{
//  /** Référence du manager interne de gestion des ventes aux enchères avec pré-inscription */
//  @Autowired
//  @Qualifier("PrebookedAuctionManagerInternal")
//  private PrebookedAuctionManagerInternal internal = null;
//
//  /**
//   * Cette méthode permet de pré-inscrire un compte utilisateur sur une vente aux
//   * enchères
//   * @param auctionId Identifiant de la vente sur laquelle se pré-inscrire
//   * @param accountId Identifiant du compte utilisateur à pré-inscrire
//   * @return Le compte utilisateur pré-inscrit sur la vente aux enchères choisie
//   * @throws RuntimeException Si un problème intervient lors de la validation de
//   * la transaction
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante
//   * @throws ModelArgumentException Si un problème intervient lors de la pré-inscription
//   * @throws UserException Si le compte utilisateur n'a pas assez de crédits ou
//   * est déjà pré-inscrit
//   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
//   * @throws AuthorizationException Si le compte utilisateur connecté ne correspond
//   * pas à celui à modifier ou n'a pas le niveau d'autorisation suffisant
//   */
//  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
//  public Account prebook(String auctionId, String accountId)
//         throws PersistenceException, ModelArgumentException, UserException, AuthenticationException, AuthorizationException
//  {
//    // @ Vérifie le compte utilisateur connecté
//    Role role = this.checkAccount(accountId);
//    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
//    this.checkRole(role, Role.USER);
//    this.checkNotRole(role, Role.WAIT);
//    // Pré-inscrit le compte utilisateur sur la vente aux enchères
//    return this.getInternal().prebook(auctionId, accountId);
//  }
//
//  /**
//   *
//   * TODO A COMMENTER
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.manager.Bid4WinManager_#getAdminRole()
//   */
//  @Override
//  public Role getAdminRole()
//  {
//    return null;
//  }
//  /**
//   * Getter de la référence du manager interne de gestion des ventes aux enchères
//   * avec pré-inscription
//   * @return {@inheritDoc}
//   * @see com.bid4win.manager.auction.AuctionManager_#getInternal()
//   */
//  @Override
//  protected PrebookedAuctionManagerInternal getInternal()
//  {
//    return this.internal;
//  }
//}
