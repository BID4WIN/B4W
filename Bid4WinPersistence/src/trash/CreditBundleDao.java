package trash;
//package com.bid4win.persistence.dao.account;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.commons.core.exception.PersistenceException;
//import com.bid4win.commons.persistence.dao.account.AccountBasedEntityMultipleDao_;
//import com.bid4win.persistence.entity.account.Account;
//import com.bid4win.persistence.entity.account.CreditBundle;
//
///**
// * DAO pour les entités de la classe CreditBundle<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//@Component("CreditBundleDao")
//@Scope("singleton")
//public class CreditBundleDao extends AccountBasedEntityMultipleDao_<CreditBundle, Long, Account>
//{
//  /** Référence du DAO des comptes utilisateur */
//  @Autowired
//  @Qualifier("AccountDao")
//  private AccountDao accountDao;
//
//  /**
//   * Constructeur
//   */
//  protected CreditBundleDao()
//  {
//    super(CreditBundle.class);
//  }
//
//  /**
//   * Cette fonction permet d'ajouter le lot de crédits en argument et de mettre
//   * à jour le compte utilisateur correspondant
//   * @param bundle {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc} ou si le compte utilisateur du lot
//   * de crédits n'a jamais été persisté
//   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityDao_#add(com.bid4win.commons.persistence.entity.account.AccountBasedEntity)
//   */
//  @Override
//  public CreditBundle add(CreditBundle bundle) throws PersistenceException
//  {
//    // Ajoute le lot de crédits en paramètre
//    bundle = super.add(bundle);
//    // Met à jour le compte utilisateur lié s'il y en a un
//    if(bundle.isLinkedToAccount())
//    {
//      this.getAccountDao().update(bundle.getAccountLink());
//    }
//    return bundle;
//  }
//  /**
//   * Cette fonction permet de modifier le lot de crédits en argument et de mettre
//   * à jour le compte utilisateur correspondant et d'en supprimer toute référence
//   * si le lot est vide
//   * @param bundle {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#update(com.bid4win.commons.persistence.entity.Bid4WinEntity)
//   */
//  @Override
//  public CreditBundle update(CreditBundle bundle) throws PersistenceException
//  {
//    // Si le lot de crédits est vide et lié à un compte utilisateur il faut retirer
//    // le lien
//    if(bundle.isLinkedToAccount() && bundle.getCurrentNb() == 0)
//    {
//      try
//      {
//        bundle.unlinkFromAccount();
//      }
//      catch(ModelArgumentException ex)
//      {
//        throw new PersistenceException(ex);
//      }
//    }
//    // Met à jour le compte utilisateur du lot de crédits (pour les nombre de crédits)
//    this.getAccountDao().update(bundle.getAccount());
//    // Modifie le lot de crédits en paramètre
//    return super.update(bundle);
//  }
//
//  /**
//   * Getter du DAO des comptes utilisateur
//   * @return Le DAO des comptes utilisateur
//   */
//  protected AccountDao getAccountDao()
//  {
//    return this.accountDao;
//  }
//}