package com.bid4win.persistence.dao.account.credit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.core.UtilBoolean;
import com.bid4win.commons.core.UtilNumber;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.AccountRef;
import com.bid4win.persistence.dao.account.AccountDao;
import com.bid4win.persistence.entity.account.credit.CreditBundle;

/**
 * DAO pour les entités de la classe CreditBundle<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("CreditBundleDao")
@Scope("singleton")
public class CreditBundleDao extends CreditBundleAbstractDao_<CreditBundle>
{
  /** Référence du DAO des comptes utilisateur */
  @Autowired
  @Qualifier("AccountDao")
  private AccountDao accountDao;
  /** Référence du DAO des historiques de lots de crédits */
  @Autowired
  @Qualifier("CreditBundleHistoryDao")
  private CreditBundleHistoryDao historyDao;

  /**
   * Constructeur
   */
  protected CreditBundleDao()
  {
    super(CreditBundle.class);
  }

  /**
   * Cette fonction permet d'ajouter le lot de crédits en argument et de mettre
   * à jour le compte utilisateur correspondant
   * @param bundle {@inheritDoc}
   * @throws PersistenceException {@inheritDoc} ou si le compte utilisateur du lot
   * de crédits est nul ou n'a jamais été persisté
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityDao_#add(com.bid4win.commons.persistence.entity.account.AccountBasedEntity)
   */
  @Override
  public CreditBundle add(CreditBundle bundle) throws PersistenceException
  {
    // Si le lot est déjà à historiser, il faut le faire
    if(bundle.isHistorized())
    {
      // Crée l'historique du lot de crédits
      this.getHistoryDao().add(bundle.getHistory());
    }
    // Ajoute le lot de crédits en paramètre
    bundle = super.add(bundle);
    // Met à jour le compte utilisateur lié (il doit y en avoir forcément un)
    this.getAccountDao().update(bundle.getAccountLink());
    return bundle;
  }
  /**
   * Cette fonction permet de modifier le lot de crédits en argument et de mettre
   * à jour le compte utilisateur correspondant et d'en supprimer toute référence
   * si le lot est vide
   * @param bundle {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#update(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  public CreditBundle update(CreditBundle bundle) throws PersistenceException
  {
    // Si le lot de crédits est vide et lié à un compte utilisateur il faut retirer
    // le lien
    if(bundle.getCurrentNb() == 0)
    {
      if(bundle.isLinkedToAccount())
      {
        try
        {
          bundle.unlinkFromAccount();
        }
        catch(UserException ex)
        {
          throw new PersistenceException(ex);
        }
      }
    }
    // Si le lot de crédits n'est pas vide et pas lié à un compte utilisateur, il
    // faut ajouter le lien (cas des crédits remboursés)
    else
    {
      if(!bundle.isLinkedToAccount())
      {
        try
        {
          bundle.linkToAccount();
        }
        catch(UserException ex)
        {
          throw new PersistenceException(ex);
        }
      }
    }
    // Met à jour le compte utilisateur du lot de crédits (pour les nombre de crédits)
    this.getAccountDao().update(bundle.getAccount());
    // Modifie le lot de crédits en paramètre
    return super.update(bundle);
  }
  /**
   * Cette fonction permet d'historiser le lot de crédits en argument et d'en créer
   * son historique
   * @param bundle Lot de crédits à historiser
   * @return Le lot de crédits historisé
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si lot de crédits a déjà été historisé
   */
  public CreditBundle historize(CreditBundle bundle) throws PersistenceException, UserException
  {
    if((bundle.getCurrentNb() == 0 && bundle.isLinkedToAccount()) ||
        bundle.getCurrentNb() != 0 && !bundle.isLinkedToAccount())
    {
      throw new UserException(AccountRef.ACCOUNT_CREDIT_NOT_HISTORIZED_ERROR);
    }
    // Crée l'historique du lot de crédits
    this.getHistoryDao().add(bundle.historize());
    // Historise le lot de crédits
    return super.update(bundle.defineHistoryId());
  }
  /**
   * Cette méthode permet de nettoyer le lot de crédits en argument, c'est à dire
   * de le supprimer si celui-ci est historisé
   * @param bundle Lot de crédits à nettoyer
   * @return Le lot de crédits nettoyé
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si le lot de crédits à nettoyer n'est pas historisé
   */
  public CreditBundle clean(CreditBundle bundle) throws PersistenceException, UserException
  {
    UtilNumber.checkMaxValue("creditNb", bundle.getCurrentNb(), 0, true,
                             AccountRef.ACCOUNT_CREDIT_NB_INVALID_ERROR);
    UtilBoolean.checkTrue("historized", bundle.isHistorized(),
                          AccountRef.ACCOUNT_CREDIT_NOT_HISTORIZED_ERROR);
    UtilObject.checkNotNull("historized", bundle.getHistory().getId(),
                            AccountRef.ACCOUNT_CREDIT_NOT_HISTORIZED_ERROR);
    return this.remove(bundle);
  }

  /**
   * Getter du DAO des comptes utilisateur
   * @return Le DAO des comptes utilisateur
   */
  protected AccountDao getAccountDao()
  {
    return this.accountDao;
  }
  /**
   * Getter du DAO des historiques de lots de crédits
   * @return Le DAO des historiques de lots de crédits
   */
  protected CreditBundleHistoryDao getHistoryDao()
  {
    return this.historyDao;
  }
}