package trash;
//package com.bid4win.persistence.dao.account;
//
//import com.bid4win.commons.core.exception.PersistenceException;
//import com.bid4win.commons.persistence.dao.account.AccountBasedEntityMultipleDao_;
//import com.bid4win.commons.persistence.dao.exception.NotPersistedEntityException;
//import com.bid4win.persistence.entity.account.Account;
//import com.bid4win.persistence.entity.account.CreditBundleUsage;
//import com.bid4win.persistence.entity.account.CreditUsage;
//
///**
// * DAO pour les entités de la classe CreditUsage<BR>
// * <BR>
// * @param <CREDIT> TODO A COMMENTER<BR>
// * @param <BUNDLE> TODO A COMMENTER<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//public class CreditUsageDao_<CREDIT extends CreditUsage<CREDIT, BUNDLE>,
//                            BUNDLE extends CreditBundleUsage<BUNDLE, CREDIT>>
//       extends AccountBasedEntityMultipleDao_<CREDIT, Long, Account>
//{
//  /**
//   * Constructeur
//   * @param creditClass Classe de l'utilisation de crédits gérée par le DAO
//   */
//  protected CreditUsageDao_(Class<CREDIT> creditClass)
//  {
//    super(creditClass);
//  }
//
//  /**
//   * Cette fonction permet d'ajouter l'utilisation de crédits en argument
//   * @param credit {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityDao_#add(com.bid4win.commons.persistence.entity.account.AccountBasedEntity)
//   */
//  @Override
//  public CREDIT add(CREDIT credit) throws PersistenceException
//  {
//    // L'ajout sera propagé aux potentielles utilisations de lot de crédits automatiquement
//    return super.add(credit);
//  }
//  /**
//   * Cette fonction permet de modifier l'utilisation de crédits en argument
//   * @param credit {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @throws NotPersistedEntityException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#update(com.bid4win.commons.persistence.entity.Bid4WinEntity)
//   */
//  @Override
//  public CREDIT update(CREDIT credit) throws PersistenceException, NotPersistedEntityException
//  {
//    return super.update(credit);
//  }
//}
