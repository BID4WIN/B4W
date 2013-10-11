package com.bid4win.persistence.dao.account.credit;

import com.bid4win.commons.persistence.dao.account.AccountBasedEntityMultipleDao_;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.account.credit.CreditBundleAbstract;

/**
 * DAO pour les entit�s de la classe CreditBundleAbstract<BR>
 * <BR>
 * @param <BUNDLE> D�finition du type de lot de cr�dits g�r� par le DAO<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class CreditBundleAbstractDao_<BUNDLE extends CreditBundleAbstract<BUNDLE>>
       extends AccountBasedEntityMultipleDao_<BUNDLE, Long, Account>
{
  /**
   * Constructeur
   * @param bundleClass Classe du lot de cr�dits g�r� par le DAO
   */
  protected CreditBundleAbstractDao_(Class<BUNDLE> bundleClass)
  {
    super(bundleClass);
  }
}
