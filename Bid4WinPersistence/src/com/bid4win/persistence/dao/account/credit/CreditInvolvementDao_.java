package com.bid4win.persistence.dao.account.credit;

import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.dao.account.AccountBasedEntityMultipleDao_;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.account.credit.CreditBundleAbstract;
import com.bid4win.persistence.entity.account.credit.CreditBundleHistory;
import com.bid4win.persistence.entity.account.credit.CreditInvolvement;
import com.bid4win.persistence.entity.account.credit.CreditUsageAbstract;

/**
 * DAO pour les entités de la classe CreditInvolvement<BR>
 * <BR>
 * @param <INVOLVEMENT> Définition du type d'implication gérée par le DAO<BR>
 * @param <USAGE> Définition du type d'utilisation de crédits des implications
 * gérées par le DAO<BR>
 * @param <BUNDLE> Definition du type de lot de crédits utilisés par les implications
 * gérées par le DAO<BR>
 * @param <HISTORY> Définition du type d'historique des implications gérées par
 * le DAO<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class CreditInvolvementDao_<INVOLVEMENT extends CreditInvolvement<INVOLVEMENT, USAGE, BUNDLE, HISTORY>,
                                            USAGE extends CreditUsageAbstract<USAGE, BUNDLE, INVOLVEMENT>,
                                            BUNDLE extends CreditBundleAbstract<BUNDLE>,
                                            HISTORY extends CreditInvolvement<HISTORY, ?, CreditBundleHistory, ?>>
       extends AccountBasedEntityMultipleDao_<INVOLVEMENT, Long, Account>
{
  /**
   * Constructeur
   * @param involvementClass Classe de l'implication de crédits gérée par le DAO
   */
  protected CreditInvolvementDao_(Class<INVOLVEMENT> involvementClass)
  {
    super(involvementClass);
  }

  /**
   * Cette fonction permet d'ajouter l'implication en argument ainsi que toutes
   * ses utilisations de crédits
   * @param involvement {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityDao_#add(com.bid4win.commons.persistence.entity.account.AccountBasedEntity)
   */
  @Override
  public INVOLVEMENT add(INVOLVEMENT involvement) throws PersistenceException
  {
    involvement = super.add(involvement);
    for(USAGE usage : involvement.getUsageSet())
    {
      this.getUsageDao().add(usage);
    }
    return involvement;
  }
  /**
   * Cette fonction permet de modifier l'implication en argument ainsi que toutes
   * ses utilisations de crédits (les nouvelles seront elles ajoutées)
   * @param involvement {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#update(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  protected INVOLVEMENT update(INVOLVEMENT involvement) throws PersistenceException
  {
    for(USAGE usage : involvement.getUsageSet())
    {
      if(usage.isNewEntity())
      {
        this.getUsageDao().add(usage);
      }
      else
      {
        this.getUsageDao().update(usage);
      }
    }
    return super.update(involvement);
  }
  /**
   * Cette fonction permet de supprimer l'implication en argument ainsi que toutes
   * ses utilisations de crédits
   * @param involvement {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#remove(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  protected INVOLVEMENT remove(INVOLVEMENT involvement) throws PersistenceException
  {
    for(USAGE usage : involvement.getUsageSet())
    {
      this.getUsageDao().remove(usage);
    }
    return super.remove(involvement);
  }

  /**
   * Cette méthode permet d'historiser l'implication en argument
   * @param involvement Implication à historiser
   * @return L'implication historisée
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si la creation de l'historique échoue
   */
  protected INVOLVEMENT historize(INVOLVEMENT involvement) throws PersistenceException, UserException
  {
    this.getHistoryDao().add(involvement.historize());
    return this.update(involvement.defineHistoryId());
  }

  /**
   * Getter du DAO de gestion des utilisations de crédits des implications
   * @return Le DAO de gestion des utilisations de crédits des implications
   */
  protected abstract CreditUsageAbstractDao_<USAGE, BUNDLE, INVOLVEMENT> getUsageDao();
  /**
   * Getter du DAO de gestion des historisations d'implication de crédits
   * @return Le DAO de gestion des historisations d'implication de crédits
   */
  protected abstract CreditInvolvementDao_<HISTORY, ?, CreditBundleHistory, ?> getHistoryDao();
}
