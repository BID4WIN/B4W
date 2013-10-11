package com.bid4win.persistence.dao.account.credit;

import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.dao.Bid4WinDao_;
import com.bid4win.persistence.entity.account.credit.CreditBundleAbstract;
import com.bid4win.persistence.entity.account.credit.CreditInvolvement;
import com.bid4win.persistence.entity.account.credit.CreditUsageAbstract;

/**
 * DAO pour les entit�s de la classe CreditUsageAbstract<BR>
 * <BR>
 * @param <USAGE> D�finition du type d'utilisation de cr�dits g�r�e par le DAO<BR>
 * @param <BUNDLE> D�finition du type de lot de cr�dits des utilisations g�r�es
 * par le DAO<BR>
 * @param <INVOLVEMENT> D�finition du type d'implication des utilisations de cr�dits
 * g�r�es par le DAO<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class CreditUsageAbstractDao_<USAGE extends CreditUsageAbstract<USAGE, BUNDLE, INVOLVEMENT>,
                                     BUNDLE extends CreditBundleAbstract<BUNDLE>,
                                     INVOLVEMENT extends CreditInvolvement<INVOLVEMENT, USAGE, BUNDLE, ?>>
       extends Bid4WinDao_<USAGE, Long>
{
  /**
   * Constructeur
   * @param usageClass Classe de l'utilisation de cr�dits g�r�e par le DAO
   */
  protected CreditUsageAbstractDao_(Class<USAGE> usageClass)
  {
    super(usageClass);
  }

  /**
   * Cette fonction permet d'ajouter l'utilisation de cr�dits en argument
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
   * Cette fonction permet de modifier l'utilisation de cr�dits en argument
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
   * Cette fonction permet de supprimer l'utilisation de cr�dits en argument
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
