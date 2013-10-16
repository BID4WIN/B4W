package com.bid4win.manager.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.core.UtilNumber;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.AccountRef;
import com.bid4win.commons.manager.account.AccountAbstractManager_;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.account.security.Credential;
import com.bid4win.commons.persistence.entity.contact.Email;
import com.bid4win.persistence.dao.account.credit.CreditBundleDao;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.account.User;
import com.bid4win.persistence.entity.account.credit.CreditBundle;
import com.bid4win.persistence.entity.account.credit.CreditOrigin;
import com.bid4win.persistence.entity.account.credit.collection.CreditMap;
import com.bid4win.persistence.entity.account.preference.PreferenceRoot;
import com.bid4win.persistence.entity.locale.Language;

/**
 * Manager de gestion des comptes utilisateur incluant leur gestion m�tier<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Component("AccountManager")
@Scope("singleton")
public class AccountManager extends AccountAbstractManager_<Account>
{
  /** R�f�rence du DAO des lots de cr�dits */
  @Autowired
  @Qualifier("CreditBundleDao")
  private CreditBundleDao creditBundleDao = null;

  /**
   * Cette m�thode permet de modifier les informations sur l'utilisateur du compte
   * @param accountId Identifiant du compte utilisateur � modifier
   * @param user Informations sur l'utilisateur du compte � prendre en compte
   * @return Le compte utilisateur modifi�
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si le compte utilisateur n'a pu �tre trouv�
   */
  public Account updateUser(String accountId, User user)
         throws PersistenceException, NotFoundEntityException
  {
    Account account = this.lockById(accountId);
    account.defineUser(user);
    return this.getAccountDao().update(account);
  }
  /**
   * Cette m�thode permet de modifier la pr�f�rence de langue d'un compte utilisateur
   * @param accountId Identifiant du compte utilisateur � modifier
   * @param language Nouvelle pr�f�rence de langue du compte utilisateur
   * @return Le compte utilisateur modifi�
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si le compte utilisateur n'a pu �tre trouv�
   * @throws UserException Si on d�fini une langue nulle
   */
  public Account updateLanguage(String accountId, Language language)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    Account account = this.lockById(accountId);
    account.getPreferenceBundle().defineLanguage(language);
    return this.getAccountDao().update(account);
  }
  /**
   * Cette m�thode permet de modifier une liste de pr�f�rences d'un compte utilisateur
   * @param accountId Identifiant du compte utilisateur � modifier
   * @param preferenceRoot Racine de la liste de nouvelles pr�f�rences du compte
   * utilisateur
   * @return Le compte utilisateur modifi�
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si le compte utilisateur n'a pu �tre trouv�
   * @throws UserException Si l'une des pr�f�rence est invalide
   */
  public Account updatePreferences(String accountId, PreferenceRoot preferenceRoot)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    Account account = this.lockById(accountId);
    account.getPreferenceBundle().importPreferences(preferenceRoot);
    return this.getAccountDao().update(account);
  }

  /**
   * Cette m�thode permet d'ajoute un lot de cr�dits � un compte utilisateur
   * @param accountId Identifiant du compte utilisateur du lot de cr�dits � ajouter
   * @param origin Provenance du lot de cr�dits � ajouter
   * @param totalValue Valeur r�elle totale des cr�dits du lot
   * @param nb Nombre de cr�dits du lot � ajouter
   * @return Le lot de cr�dit ajout�
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si le compte utilisateur n'a pu �tre trouv�
   * @throws UserException Si la provenance des cr�dits est nulle ou leur nombre
   * inf�rieur � un
   */
  public CreditBundle addCreditBundle(String accountId, CreditOrigin origin, double totalValue, int nb)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    Account account = this.lockById(accountId);
    // L'ajout du lot de cr�dit met forc�ment � jour le compte utilisateur associ�
    return this.getCreditBundleDao().add(new CreditBundle(account, origin, totalValue, nb));
  }
  /**
   * Cette m�thode permet d'utiliser des cr�dits du compte utilisateur
   * @param accountId Identifiant du compte utilisateur des cr�dits � utiliser
   * @param nb Le nombre de cr�dits � utiliser
   * @return Les lots de provenance des cr�dits utilis�s
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si le compte utilisateur n'a pu �tre trouv�
   * @throws UserException Si le compte utilisateur n'a pas assez de cr�dits ou
   * si le nombre de cr�dits � utiliser est inf�rieur � un
   */
  public UsedCredit useCredit(String accountId, int nb)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    UtilNumber.checkMinValue("nb", nb, 1, true, AccountRef.CREDIT_NB_INVALID_ERROR);
    // Block le compte utilisateur
    Account account = this.lockById(accountId);
    // Utilise les cr�dits
    CreditMap usedCreditMap = account.useCredit(nb);
    // La modification d'un lot de cr�dits met forc�ment � jour le compte utilisateur
    // associ�
    for(CreditBundle usedBundle : usedCreditMap.keySet())
    {
      this.getCreditBundleDao().update(usedBundle);
    }
    return new UsedCredit(account, usedCreditMap);
  }

  /**
   *
   * TODO A COMMENTER
   * @param credential {@inheritDoc}
   * @param email {@inheritDoc}
   * @return {@inheritDoc}
   * @throws ModelArgumentException {@inheritDoc}
   * @see com.bid4win.commons.manager.account.AccountAbstractManager_#createAccountEntity(com.bid4win.commons.persistence.entity.account.security.Credential, com.bid4win.commons.persistence.entity.contact.Email)
   */
  @Override
  protected Account createAccountEntity(Credential credential, Email email)
            throws ModelArgumentException
  {
    return new Account(credential, email);
  }

  /**
   * Getter de la r�f�rence du DAO des lots de cr�dits
   * @return La r�f�rence du DAO des lots de cr�dits
   */
  protected CreditBundleDao getCreditBundleDao()
  {
    return this.creditBundleDao;
  }
}
