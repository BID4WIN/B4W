package com.bid4win.persistence.dao.account.credit;

import com.bid4win.commons.persistence.dao.account.AccountBasedEntityMultipleDao_;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.account.credit.CreditBundleAbstract;

/**
 * DAO pour les entités de la classe CreditBundleAbstract<BR>
 * <BR>
 * @param <BUNDLE> Définition du type de lot de crédits géré par le DAO<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class CreditBundleAbstractDao_<BUNDLE extends CreditBundleAbstract<BUNDLE>>
       extends AccountBasedEntityMultipleDao_<BUNDLE, Long, Account>
{
  /**
   * Constructeur
   * @param bundleClass Classe du lot de crédits géré par le DAO
   */
  protected CreditBundleAbstractDao_(Class<BUNDLE> bundleClass)
  {
    super(bundleClass);
  }
}
