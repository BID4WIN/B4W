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
 * Manager de gestion des comptes utilisateur incluant leur gestion métier<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("AccountManager")
@Scope("singleton")
public class AccountManager extends AccountAbstractManager_<Account>
{
  /** Référence du DAO des lots de crédits */
  @Autowired
  @Qualifier("CreditBundleDao")
  private CreditBundleDao creditBundleDao = null;

  /**
   * Cette méthode permet de modifier les informations sur l'utilisateur du compte
   * @param accountId Identifiant du compte utilisateur à modifier
   * @param user Informations sur l'utilisateur du compte à prendre en compte
   * @return Le compte utilisateur modifié
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si le compte utilisateur n'a pu être trouvé
   */
  public Account updateUser(String accountId, User user)
         throws PersistenceException, NotFoundEntityException
  {
    Account account = this.lockById(accountId);
    account.defineUser(user);
    return this.getAccountDao().update(account);
  }
  /**
   * Cette méthode permet de modifier la préférence de langue d'un compte utilisateur
   * @param accountId Identifiant du compte utilisateur à modifier
   * @param language Nouvelle préférence de langue du compte utilisateur
   * @return Le compte utilisateur modifié
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si le compte utilisateur n'a pu être trouvé
   * @throws UserException Si on défini une langue nulle
   */
  public Account updateLanguage(String accountId, Language language)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    Account account = this.lockById(accountId);
    account.getPreferenceBundle().defineLanguage(language);
    return this.getAccountDao().update(account);
  }
  /**
   * Cette méthode permet de modifier une liste de préférences d'un compte utilisateur
   * @param accountId Identifiant du compte utilisateur à modifier
   * @param preferenceRoot Racine de la liste de nouvelles préférences du compte
   * utilisateur
   * @return Le compte utilisateur modifié
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si le compte utilisateur n'a pu être trouvé
   * @throws UserException Si l'une des préférence est invalide
   */
  public Account updatePreferences(String accountId, PreferenceRoot preferenceRoot)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    Account account = this.lockById(accountId);
    account.getPreferenceBundle().importPreferences(preferenceRoot);
    return this.getAccountDao().update(account);
  }

  /**
   * Cette méthode permet d'ajoute un lot de crédits à un compte utilisateur
   * @param accountId Identifiant du compte utilisateur du lot de crédits à ajouter
   * @param origin Provenance du lot de crédits à ajouter
   * @param totalValue Valeur réelle totale des crédits du lot
   * @param nb Nombre de crédits du lot à ajouter
   * @return Le lot de crédit ajouté
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si le compte utilisateur n'a pu être trouvé
   * @throws UserException Si la provenance des crédits est nulle ou leur nombre
   * inférieur à un
   */
  public CreditBundle addCreditBundle(String accountId, CreditOrigin origin, double totalValue, int nb)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    Account account = this.lockById(accountId);
    // L'ajout du lot de crédit met forcément à jour le compte utilisateur associé
    return this.getCreditBundleDao().add(new CreditBundle(account, origin, totalValue, nb));
  }
  /**
   * Cette méthode permet d'utiliser des crédits du compte utilisateur
   * @param accountId Identifiant du compte utilisateur des crédits à utiliser
   * @param nb Le nombre de crédits à utiliser
   * @return Les lots de provenance des crédits utilisés
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si le compte utilisateur n'a pu être trouvé
   * @throws UserException Si le compte utilisateur n'a pas assez de crédits ou
   * si le nombre de crédits à utiliser est inférieur à un
   */
  public UsedCredit useCredit(String accountId, int nb)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    UtilNumber.checkMinValue("nb", nb, 1, true, AccountRef.CREDIT_NB_INVALID_ERROR);
    // Block le compte utilisateur
    Account account = this.lockById(accountId);
    // Utilise les crédits
    CreditMap usedCreditMap = account.useCredit(nb);
    // La modification d'un lot de crédits met forcément à jour le compte utilisateur
    // associé
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
   * Getter de la référence du DAO des lots de crédits
   * @return La référence du DAO des lots de crédits
   */
  protected CreditBundleDao getCreditBundleDao()
  {
    return this.creditBundleDao;
  }
}
