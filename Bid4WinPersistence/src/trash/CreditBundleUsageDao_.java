package trash;
//package com.bid4win.persistence.dao.account;
//
//import com.bid4win.commons.core.exception.PersistenceException;
//import com.bid4win.commons.persistence.dao.Bid4WinDao_;
//import com.bid4win.commons.persistence.dao.exception.NotPersistedEntityException;
//import com.bid4win.persistence.entity.account.CreditBundleUsage;
//import com.bid4win.persistence.entity.account.CreditUsage;
//
///**
// * DAO pour les entit�s de la classe CreditBundleUsage<BR>
// * <BR>
// * @param <BUNDLE> TODO A COMMENTER<BR>
// * @param <CREDIT> TODO A COMMENTER<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
//public abstract class CreditBundleUsageDao_<BUNDLE extends CreditBundleUsage<BUNDLE, CREDIT>,
//                                           CREDIT extends CreditUsage<CREDIT, BUNDLE>>
//       extends Bid4WinDao_<BUNDLE, Long>
//{
//  /**
//   * Constructeur
//   * @param bundleClass Classe de l'utilisation de lot de cr�dits g�r�e par le DAO
//   */
//  protected CreditBundleUsageDao_(Class<BUNDLE> bundleClass)
//  {
//    super(bundleClass);
//  }
//
//  /**
//   * Cette fonction permet d'ajouter l'utilisation de lot de cr�dits en argument
//   * et de mettre � jour l'utilisation de cr�dits correspondante
//   * @param bundle {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#add(com.bid4win.commons.persistence.entity.Bid4WinEntity)
//   */
//  @Override
//  public BUNDLE add(BUNDLE bundle) throws PersistenceException
//  {
//    // Ajoute l'utilisation de lot de cr�dits en param�tre
//    bundle = super.add(bundle);
//    // Met � jour l'utilisation de cr�dits correspondante
//    this.getCreditUsageDao().update(bundle.getCreditUsage());
//    return bundle;
//  }
//  /**
//   * Cette fonction permet de modifier l'utilisation de lot de cr�dits en argument
//   * et de mettre � jour l'utilisation de cr�dits correspondante
//   * @param bundle {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @throws NotPersistedEntityException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#update(com.bid4win.commons.persistence.entity.Bid4WinEntity)
//   */
//  @Override
//  public BUNDLE update(BUNDLE bundle) throws PersistenceException, NotPersistedEntityException
//  {
//    // Modifie l'utilisation de lot de cr�dits en param�tre
//    bundle = super.update(bundle);
//    // Met � jour l'utilisation de cr�dits correspondante
//    this.getCreditUsageDao().update(bundle.getCreditUsage());
//    return bundle;
//  }
//
//  /**
//   *
//   * TODO A COMMENTER
//   * @return TODO A COMMENTER
//   */
//  protected abstract CreditUsageDao_<CREDIT, BUNDLE> getCreditUsageDao();
//}
