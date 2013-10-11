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
// * Manager interne de gestion des ventes aux ench�res avec pr�-inscription incluant
// * leur gestion m�tier<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
//@Component("PrebookedAuctionManagerInternal")
//@Scope("singleton")
//public class PrebookedAuctionManagerInternal
//       extends AuctionManagerInternal_<PrebookedAuction, PrebookedBid,
//                                      PrebookedSchedule, PrebookedTerms,
//                                      PrebookedInvolvedCredit, PrebookedInvolvedBundle>
//{
//  /** R�f�rence du DAO des ventes aux ench�res avec pr�-inscription */
//  @Autowired
//  @Qualifier("PrebookedAuctionDao")
//  private PrebookedAuctionDao auctionDao = null;
//  /** R�f�rence du DAO des ench�res avec pr�-inscription */
//  @Autowired
//  @Qualifier("PrebookedBidDao")
//  private PrebookedBidDao bidDao = null;
//  /** R�f�rence du DAO des utilisations de cr�dits sur les ventes aux ench�res avec pr�-inscription */
//  @Autowired
//  @Qualifier("PrebookedInvolvedCreditDao")
//  private PrebookedInvolvedCreditDao creditDao = null;
//  /** R�f�rence du DAO des utilisations de lots de cr�dits sur les ventes aux ench�res avec pr�-inscription */
//  @Autowired
//  @Qualifier("PrebookedInvolvedBundleDao")
//  private PrebookedInvolvedBundleDao bundleDao = null;
//
//  /**
//   * Cette m�thode permet de se pr�-inscrire sur une vente aux ench�res
//   * @param auctionId Identifiant de la vente sur laquelle se pr�-inscrire
//   * @param accountId Identifiant du compte utilisateur � pr�-inscrire
//   * @return Le compte utilisateur pr�-inscrit sur la vente aux ench�res choisie
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   * @throws NotFoundEntityException Si le compte utilisateur ou la vente aux ench�res
//   * n'a pu �tre trouv�
//   * @throws ModelArgumentException Si un probl�me intervient lors de la pr�-inscription
//   * @throws UserException Si le compte utilisateur n'a pas assez de cr�dits ou
//   * est d�j� pr�-inscrit
//   */
//  public Account prebook(String auctionId, String accountId)
//         throws PersistenceException, NotFoundEntityException, ModelArgumentException, UserException
//  {
//    // R�cup�re la vente aux ench�res en la bloquant
//    PrebookedAuction auction = this.getAuctionDao().lockById(auctionId);
//    // Utilise les cr�dits sur le compte utilisateur donn�
//    return this.useCreditForPrebook(auction, accountId).getAccount();
//  }
//
//  /**
//   * Cette m�thode permet d'utiliser le nombre n�cessaire de cr�dits du compte
//   * utilisateur dont l'identifiant est pr�cis� pour se pr�-inscrire sur la vente
//   * aux ench�res en argument
//   * @param auction Vente aux ench�res pour laquelle utiliser les cr�dits
//   * @param accountId Identifiant du compte utilisateur des cr�dits � utiliser
//   * @return Les cr�dits utilis�s utilis�s sur la vente aux ench�res en argument
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   * @throws NotFoundEntityException Si le compte utilisateur n'a pu �tre trouv�
//   * @throws ModelArgumentException Si le nombre de cr�dits � utiliser est inf�rieur
//   * � un
//   * @throws UserException Si le compte utilisateur n'a pas assez de cr�dits ou
//   * est d�j� pr�-inscrit
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
//   * Cette m�thode  est red�finie afin de retourner les cr�dits d�j� impliqu�s
//   * car aucun cr�dit n'est utilis� pour placer une ench�re, seulement pour s'
//   * inscrire � la vente
//   * @param auction {@inheritDoc}
//   * @param accountId {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @throws NotFoundEntityException {@inheritDoc}
//   * @throws ModelArgumentException {@inheritDoc}
//   * @throws UserException Si le compte utilisateur n'est pas pr�-inscrit sur la
//   * vente aux ench�res
//   * @see com.bid4win.manager.auction.AuctionManagerInternal_#useCreditForBid(com.bid4win.persistence.entity.auction.Auction, java.lang.String)
//   */
//  @Override
//  protected PrebookedInvolvedCredit useCreditForBid(PrebookedAuction auction,
//                                                    String accountId)
//            throws PersistenceException, NotFoundEntityException, ModelArgumentException, UserException
//  {
//    // R�cup�re le compte utilisateur
//    Account account = this.getAccountManager().getById(accountId);
//    // Recherche et v�rifie les cr�dits utilis�s impliqu�s dans la vente aux ench�res
//    PrebookedInvolvedCredit credit = account.getPrebookedInvolvement(auction.getId());
//    UtilObject.checkNotNull("credit", credit, MessageRef.ERROR_AUCTION_NOT_BOOKED);
//    // TODO voir si limitation dans le nombre d'ench�re
//    return credit;
//  }
//  /**
//   * Cette m�thode permet de construire une vente aux ench�res avec pr�-inscription
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
//   * Cette m�thode permet de construire une utilisation de cr�dits du compte
//   * utilisateur donn� impliqu�e sur la vente aux ench�res avec pr�-inscription
//   * pr�cis�e en argument ou de r�cup�rer celle d�j� existante
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
//    // Recherche une utilisation de cr�dit potentiellement d�j� existante
//    PrebookedInvolvedCredit credit = account.getPrebookedInvolvement(auction.getId());
//    if(credit == null)
//    {
//      credit = new PrebookedInvolvedCredit(account, auction);
//    }
//    return credit;
//  }
//
//  /**
//   * Getter de la r�f�rence du DAO des ventes aux ench�res avec pr�-inscription
//   * @return {@inheritDoc}
//   * @see com.bid4win.manager.auction.AuctionManagerInternal_#getAuctionDao()
//   */
//  @Override
//  protected PrebookedAuctionDao getAuctionDao()
//  {
//    return this.auctionDao;
//  }
//  /**
//   * Getter de la r�f�rence du DAO des ench�res avec pr�-inscription
//   * @return {@inheritDoc}
//   * @see com.bid4win.manager.auction.AuctionManagerInternal_#getBidDao()
//   */
//  @Override
//  protected PrebookedBidDao getBidDao()
//  {
//    return this.bidDao;
//  }
//  /**
//   * Getter de la r�f�rence du DAO des utilisations de cr�dits sur les ventes aux
//   * ench�res avec pr�-inscription
//   * @return {@inheritDoc}
//   * @see com.bid4win.manager.auction.AuctionManagerInternal_#getCreditDao()
//   */
//  @Override
//  protected PrebookedInvolvedCreditDao getCreditDao()
//  {
//    return this.creditDao;
//  }
//  /**
//   * Getter de la r�f�rence du DAO des utilisations de lots de cr�dits sur les
//   * ventes aux ench�res avec pr�-inscription
//   * @return {@inheritDoc}
//   * @see com.bid4win.manager.auction.AuctionAbstractManagerInternal_#getBundleDao()
//   */
//  @Override
//  protected PrebookedInvolvedBundleDao getBundleDao()
//  {
//    return this.bundleDao;
//  }
//}
