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
// * Manager de gestion des ventes aux ench�res avec pr�-inscription incluant la
// * gestion des transactions ainsi que celle des habilitations<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
//@Component("PrebookedAuctionManager")
//@Scope("singleton")
//public class PrebookedAuctionManager
//       extends AuctionManager_<PrebookedAuction, PrebookedBid,
//                              PrebookedSchedule, PrebookedTerms,
//                              PrebookedInvolvedCredit, PrebookedInvolvedBundle>
//{
//  /** R�f�rence du manager interne de gestion des ventes aux ench�res avec pr�-inscription */
//  @Autowired
//  @Qualifier("PrebookedAuctionManagerInternal")
//  private PrebookedAuctionManagerInternal internal = null;
//
//  /**
//   * Cette m�thode permet de pr�-inscrire un compte utilisateur sur une vente aux
//   * ench�res
//   * @param auctionId Identifiant de la vente sur laquelle se pr�-inscrire
//   * @param accountId Identifiant du compte utilisateur � pr�-inscrire
//   * @return Le compte utilisateur pr�-inscrit sur la vente aux ench�res choisie
//   * @throws RuntimeException Si un probl�me intervient lors de la validation de
//   * la transaction
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   * @throws ModelArgumentException Si un probl�me intervient lors de la pr�-inscription
//   * @throws UserException Si le compte utilisateur n'a pas assez de cr�dits ou
//   * est d�j� pr�-inscrit
//   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
//   * @throws AuthorizationException Si le compte utilisateur connect� ne correspond
//   * pas � celui � modifier ou n'a pas le niveau d'autorisation suffisant
//   */
//  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
//  public Account prebook(String auctionId, String accountId)
//         throws PersistenceException, ModelArgumentException, UserException, AuthenticationException, AuthorizationException
//  {
//    // @ V�rifie le compte utilisateur connect�
//    Role role = this.checkAccount(accountId);
//    // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
//    this.checkRole(role, Role.USER);
//    this.checkNotRole(role, Role.WAIT);
//    // Pr�-inscrit le compte utilisateur sur la vente aux ench�res
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
//   * Getter de la r�f�rence du manager interne de gestion des ventes aux ench�res
//   * avec pr�-inscription
//   * @return {@inheritDoc}
//   * @see com.bid4win.manager.auction.AuctionManager_#getInternal()
//   */
//  @Override
//  protected PrebookedAuctionManagerInternal getInternal()
//  {
//    return this.internal;
//  }
//}
