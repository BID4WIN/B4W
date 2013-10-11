package com.bid4win.persistence.dao.account.credit;

import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.dao.Bid4WinDao_;
import com.bid4win.persistence.entity.account.credit.CreditBundleAbstract;
import com.bid4win.persistence.entity.account.credit.CreditInvolvement;
import com.bid4win.persistence.entity.account.credit.CreditUsageAbstract;

/**
 * DAO pour les entités de la classe CreditUsageAbstract<BR>
 * <BR>
 * @param <USAGE> Définition du type d'utilisation de crédits gérée par le DAO<BR>
 * @param <BUNDLE> Définition du type de lot de crédits des utilisations gérées
 * par le DAO<BR>
 * @param <INVOLVEMENT> Définition du type d'implication des utilisations de crédits
 * gérées par le DAO<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class CreditUsageAbstractDao_<USAGE extends CreditUsageAbstract<USAGE, BUNDLE, INVOLVEMENT>,
                                     BUNDLE extends CreditBundleAbstract<BUNDLE>,
                                     INVOLVEMENT extends CreditInvolvement<INVOLVEMENT, USAGE, BUNDLE, ?>>
       extends Bid4WinDao_<USAGE, Long>
{
  /**
   * Constructeur
   * @param usageClass Classe de l'utilisation de crédits gérée par le DAO
   */
  protected CreditUsageAbstractDao_(Class<USAGE> usageClass)
  {
    super(usageClass);
  }

  /**
   * Cette fonction permet d'ajouter l'utilisation de crédits en argument
   * @param usage {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#add(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  protected USAGE add(USAGE usage) throws PersistenceException
  {
    return super.add(usage);
  }
  /**
   * Cette fonction permet de modifier l'utilisation de crédits en argument
   * @param usage {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#update(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  protected USAGE update(USAGE usage) throws PersistenceException
  {
    return super.update(usage);
  }
  /**
   * Cette fonction permet de supprimer l'utilisation de crédits en argument
   * @param usage {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#remove(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  protected USAGE remove(USAGE usage) throws PersistenceException
  {
    return super.remove(usage);
  }
}
