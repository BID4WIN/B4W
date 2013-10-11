package com.bid4win.manager.auction.prebooked;
//package com.bid4win.manager.auction.prebooked;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//
//import com.bid4win.commons.core.UtilObject;
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.commons.core.exception.PersistenceException;
//import com.bid4win.commons.core.exception.UserException;
//import com.bid4win.commons.core.reference.MessageRef;
//import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
//import com.bid4win.manager.auction.AuctionManagerInternal_;
//import com.bid4win.persistence.dao.auction.prebooked.PrebookedAuctionDao;
//import com.bid4win.persistence.dao.auction.prebooked.PrebookedBidDao;
//import com.bid4win.persistence.dao.auction.prebooked.PrebookedInvolvedBundleDao;
//import com.bid4win.persistence.dao.auction.prebooked.PrebookedInvolvedCreditDao;
//import com.bid4win.persistence.entity.account.Account;
//import com.bid4win.persistence.entity.auction.prebooked.PrebookedAuction;
//import com.bid4win.persistence.entity.auction.prebooked.PrebookedBid;
//import com.bid4win.persistence.entity.auction.prebooked.PrebookedInvolvedBundle;
//import com.bid4win.persistence.entity.auction.prebooked.PrebookedInvolvedCredit;
//import com.bid4win.persistence.entity.auction.prebooked.PrebookedSchedule;
//import com.bid4win.persistence.entity.auction.prebooked.PrebookedTerms;
//import com.bid4win.persistence.entity.product.Product;
//
///**
// * Manager interne de gestion des ventes aux enchères avec pré-inscription incluant
// * leur gestion métier<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//@Component("PrebookedAuctionManagerInternal")
//@Scope("singleton")
//public class PrebookedAuctionManagerInternal
//       extends AuctionManagerInternal_<PrebookedAuction, PrebookedBid,
//                                      PrebookedSchedule, PrebookedTerms,
//                                      PrebookedInvolvedCredit, PrebookedInvolvedBundle>
//{
//  /** Référence du DAO des ventes aux enchères avec pré-inscription */
//  @Autowired
//  @Qualifier("PrebookedAuctionDao")
//  private PrebookedAuctionDao auctionDao = null;
//  /** Référence du DAO des enchères avec pré-inscription */
//  @Autowired
//  @Qualifier("PrebookedBidDao")
//  private PrebookedBidDao bidDao = null;
//  /** Référence du DAO des utilisations de crédits sur les ventes aux enchères avec pré-inscription */
//  @Autowired
//  @Qualifier("PrebookedInvolvedCreditDao")
//  private PrebookedInvolvedCreditDao creditDao = null;
//  /** Référence du DAO des utilisations de lots de crédits sur les ventes aux enchères avec pré-inscription */
//  @Autowired
//  @Qualifier("PrebookedInvolvedBundleDao")
//  private PrebookedInvolvedBundleDao bundleDao = null;
//
//  /**
//   * Cette méthode permet de se pré-inscrire sur une vente aux enchères
//   * @param auctionId Identifiant de la vente sur laquelle se pré-inscrire
//   * @param accountId Identifiant du compte utilisateur à pré-inscrire
//   * @return Le compte utilisateur pré-inscrit sur la vente aux enchères choisie
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante
//   * @throws NotFoundEntityException Si le compte utilisateur ou la vente aux enchères
//   * n'a pu être trouvé
//   * @throws ModelArgumentException Si un problème intervient lors de la pré-inscription
//   * @throws UserException Si le compte utilisateur n'a pas assez de crédits ou
//   * est déjà pré-inscrit
//   */
//  public Account prebook(String auctionId, String accountId)
//         throws PersistenceException, NotFoundEntityException, ModelArgumentException, UserException
//  {
//    // Récupère la vente aux enchères en la bloquant
//    PrebookedAuction auction = this.getAuctionDao().lockById(auctionId);
//    // Utilise les crédits sur le compte utilisateur donné
//    return this.useCreditForPrebook(auction, accountId).getAccount();
//  }
//
//  /**
//   * Cette méthode permet d'utiliser le nombre nécessaire de crédits du compte
//   * utilisateur dont l'identifiant est précisé pour se pré-inscrire sur la vente
//   * aux enchères en argument
//   * @param auction Vente aux enchères pour laquelle utiliser les crédits
//   * @param accountId Identifiant du compte utilisateur des crédits à utiliser
//   * @return Les crédits utilisés utilisés sur la vente aux enchères en argument
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante
//   * @throws NotFoundEntityException Si le compte utilisateur n'a pu être trouvé
//   * @throws ModelArgumentException Si le nombre de crédits à utiliser est inférieur
//   * à un
//   * @throws UserException Si le compte utilisateur n'a pas assez de crédits ou
//   * est déjà pré-inscrit
//   */
//  protected PrebookedInvolvedCredit useCreditForPrebook(PrebookedAuction auction,
//                                                        String accountId)
//      throws PersistenceException, NotFoundEntityException, ModelArgumentException, UserException
//  {
//    Account account = this.getAccountManager().getById(accountId);
//    UtilObject.checkNotNull("credit", account.getPrebookedInvolvement(auction.getId()),
//                            MessageRef.ERROR_AUCTION_ALREADY_BOOKED);
//    return super.useCreditForAction(auction, accountId);
//  }
//  /**
//   * Cette méthode  est redéfinie afin de retourner les crédits déjà impliqués
//   * car aucun crédit n'est utilisé pour placer une enchère, seulement pour s'
//   * inscrire à la vente
//   * @param auction {@inheritDoc}
//   * @param accountId {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @throws NotFoundEntityException {@inheritDoc}
//   * @throws ModelArgumentException {@inheritDoc}
//   * @throws UserException Si le compte utilisateur n'est pas pré-inscrit sur la
//   * vente aux enchères
//   * @see com.bid4win.manager.auction.AuctionManagerInternal_#useCreditForBid(com.bid4win.persistence.entity.auction.Auction, java.lang.String)
//   */
//  @Override
//  protected PrebookedInvolvedCredit useCreditForBid(PrebookedAuction auction,
//                                                    String accountId)
//            throws PersistenceException, NotFoundEntityException, ModelArgumentException, UserException
//  {
//    // Récupère le compte utilisateur
//    Account account = this.getAccountManager().getById(accountId);
//    // Recherche et vérifie les crédits utilisés impliqués dans la vente aux enchères
//    PrebookedInvolvedCredit credit = account.getPrebookedInvolvement(auction.getId());
//    UtilObject.checkNotNull("credit", credit, MessageRef.ERROR_AUCTION_NOT_BOOKED);
//    // TODO voir si limitation dans le nombre d'enchère
//    return credit;
//  }
//  /**
//   * Cette méthode permet de construire une vente aux enchères avec pré-inscription
//   * pour le produit en argument
//   * @param product {@inheritDoc}
//   * @param schedule {@inheritDoc}
//   * @param terms {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws ModelArgumentException {@inheritDoc}
//   * @see com.bid4win.manager.auction.AuctionAbstractManagerInternal_#createAuctionEntity(com.bid4win.persistence.entity.product.Product, com.bid4win.persistence.entity.auction.ScheduleAbstract, com.bid4win.persistence.entity.auction.TermsAbstract)
//   */
//  @Override
//  protected PrebookedAuction createAuctionEntity(Product product, PrebookedSchedule schedule, PrebookedTerms terms)
//            throws ModelArgumentException
//  {
//    return new PrebookedAuction(product, schedule, terms);
//  }
//  /**
//   * Cette méthode permet de construire une utilisation de crédits du compte
//   * utilisateur donné impliquée sur la vente aux enchères avec pré-inscription
//   * précisée en argument ou de récupérer celle déjà existante
//   * @param account {@inheritDoc}
//   * @param auction {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws ModelArgumentException {@inheritDoc}
//   * @see com.bid4win.manager.auction.AuctionAbstractManagerInternal_#retreiveCreditEntity(com.bid4win.persistence.entity.account.Account, com.bid4win.persistence.entity.auction.AuctionAbstract)
//   */
//  @Override
//  protected PrebookedInvolvedCredit retreiveCreditEntity(Account account, PrebookedAuction auction)
//            throws ModelArgumentException
//  {
//    // Recherche une utilisation de crédit potentiellement déjà existante
//    PrebookedInvolvedCredit credit = account.getPrebookedInvolvement(auction.getId());
//    if(credit == null)
//    {
//      credit = new PrebookedInvolvedCredit(account, auction);
//    }
//    return credit;
//  }
//
//  /**
//   * Getter de la référence du DAO des ventes aux enchères avec pré-inscription
//   * @return {@inheritDoc}
//   * @see com.bid4win.manager.auction.AuctionManagerInternal_#getAuctionDao()
//   */
//  @Override
//  protected PrebookedAuctionDao getAuctionDao()
//  {
//    return this.auctionDao;
//  }
//  /**
//   * Getter de la référence du DAO des enchères avec pré-inscription
//   * @return {@inheritDoc}
//   * @see com.bid4win.manager.auction.AuctionManagerInternal_#getBidDao()
//   */
//  @Override
//  protected PrebookedBidDao getBidDao()
//  {
//    return this.bidDao;
//  }
//  /**
//   * Getter de la référence du DAO des utilisations de crédits sur les ventes aux
//   * enchères avec pré-inscription
//   * @return {@inheritDoc}
//   * @see com.bid4win.manager.auction.AuctionManagerInternal_#getCreditDao()
//   */
//  @Override
//  protected PrebookedInvolvedCreditDao getCreditDao()
//  {
//    return this.creditDao;
//  }
//  /**
//   * Getter de la référence du DAO des utilisations de lots de crédits sur les
//   * ventes aux enchères avec pré-inscription
//   * @return {@inheritDoc}
//   * @see com.bid4win.manager.auction.AuctionAbstractManagerInternal_#getBundleDao()
//   */
//  @Override
//  protected PrebookedInvolvedBundleDao getBundleDao()
//  {
//    return this.bundleDao;
//  }
//}
