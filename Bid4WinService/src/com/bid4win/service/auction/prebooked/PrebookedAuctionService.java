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
// * @author Emeric Fill�tre
// */
//@Component("PrebookedAuctionService")
//@Scope("singleton")
//public class PrebookedAuctionService
//       extends AuctionService_<PrebookedAuction, PrebookedBid,
//                              PrebookedSchedule, PrebookedTerms,
//                              PrebookedInvolvedCredit, PrebookedInvolvedBundle>
//{
//  /** R�f�rence du manager de gestion des ventes aux ench�res avec pr�-inscription */
//  @Autowired
//  @Qualifier("PrebookedAuctionManager")
//  private PrebookedAuctionManager manager = null;
//
//  /**
//   * Cette m�thode permet de pr�-inscrire un compte utilisateur sur une vente aux
//   * ench�res
//   * @param auctionId Identifiant de la vente sur laquelle se pr�-inscrire
//   * @param accountId Identifiant du compte utilisateur � pr�-inscrire
//   * @return Le compte utilisateur pr�-inscrit sur la vente aux ench�res choisie
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   * @throws ModelArgumentException Si un probl�me intervient lors de la pr�-inscription
//   * @throws UserException Si le compte utilisateur n'a pas assez de cr�dits ou
//   * est d�j� pr�-inscrit
//   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
//   * @throws AuthorizationException Si le compte utilisateur connect� ne correspond
//   * pas � celui � modifier ou n'a pas le niveau d'autorisation suffisant
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
