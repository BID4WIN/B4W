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
 * DAO pour les entit�s de la classe CreditBundle<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Component("CreditBundleDao")
@Scope("singleton")
public class CreditBundleDao extends CreditBundleAbstractDao_<CreditBundle>
{
  /** R�f�rence du DAO des comptes utilisateur */
  @Autowired
  @Qualifier("AccountDao")
  private AccountDao accountDao;
  /** R�f�rence du DAO des historiques de lots de cr�dits */
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
   * Cette fonction permet d'ajouter le lot de cr�dits en argument et de mettre
   * � jour le compte utilisateur correspondant
   * @param bundle {@inheritDoc}
   * @throws PersistenceException {@inheritDoc} ou si le compte utilisateur du lot
   * de cr�dits est nul ou n'a jamais �t� persist�
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityDao_#add(com.bid4win.commons.persistence.entity.account.AccountBasedEntity)
   */
  @Override
  public CreditBundle add(CreditBundle bundle) throws PersistenceException
  {
    // Si le lot est d�j� � historiser, il faut le faire
    if(bundle.isHistorized())
    {
      // Cr�e l'historique du lot de cr�dits
      this.getHistoryDao().add(bundle.getHistory());
    }
    // Ajoute le lot de cr�dits en param�tre
    bundle = super.add(bundle);
    // Met � jour le compte utilisateur li� (il doit y en avoir forc�ment un)
    this.getAccountDao().update(bundle.getAccountLink());
    return bundle;
  }
  /**
   * Cette fonction permet de modifier le lot de cr�dits en argument et de mettre
   * � jour le compte utilisateur correspondant et d'en supprimer toute r�f�rence
   * si le lot est vide
   * @param bundle {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#update(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  public CreditBundle update(CreditBundle bundle) throws PersistenceException
  {
    // Si le lot de cr�dits est vide et li� � un compte utilisateur il faut retirer
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
    // Si le lot de cr�dits n'est pas vide et pas li� � un compte utilisateur, il
    // faut ajouter le lien (cas des cr�dits rembours�s)
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
    // Met � jour le compte utilisateur du lot de cr�dits (pour les nombre de cr�dits)
    this.getAccountDao().update(bundle.getAccount());
    // Modifie le lot de cr�dits en param�tre
    return super.update(bundle);
  }
  /**
   * Cette fonction permet d'historiser le lot de cr�dits en argument et d'en cr�er
   * son historique
   * @param bundle Lot de cr�dits � historiser
   * @return Le lot de cr�dits historis�
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si lot de cr�dits a d�j� �t� historis�
   */
  public CreditBundle historize(CreditBundle bundle) throws PersistenceException, UserException
  {
    if((bundle.getCurrentNb() == 0 && bundle.isLinkedToAccount()) ||
        bundle.getCurrentNb() != 0 && !bundle.isLinkedToAccount())
    {
      throw new UserException(AccountRef.ACCOUNT_CREDIT_NOT_HISTORIZED_ERROR);
    }
    // Cr�e l'historique du lot de cr�dits
    this.getHistoryDao().add(bundle.historize());
    // Historise le lot de cr�dits
    return super.update(bundle.defineHistoryId());
  }
  /**
   * Cette m�thode permet de nettoyer le lot de cr�dits en argument, c'est � dire
   * de le supprimer si celui-ci est historis�
   * @param bundle Lot de cr�dits � nettoyer
   * @return Le lot de cr�dits nettoy�
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si le lot de cr�dits � nettoyer n'est pas historis�
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
   * Getter du DAO des historiques de lots de cr�dits
   * @return Le DAO des historiques de lots de cr�dits
   */
  protected CreditBundleHistoryDao getHistoryDao()
  {
    return this.historyDao;
  }
}