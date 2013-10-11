package com.bid4win.service.account;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthenticationException;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthorizationException;
import com.bid4win.commons.persistence.entity.account.security.exception.SessionException;
import com.bid4win.commons.service.account.AccountAbstractService_;
import com.bid4win.manager.account.AccountManager;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.account.User;
import com.bid4win.persistence.entity.account.UtilAccount;
import com.bid4win.persistence.entity.locale.Language;
import com.bid4win.service.connection.SessionData;

/**
 * Service de gestion des comptes utilisateur incluant la gestion des transactions
 * ainsi que celle des habilitations<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Component("AccountService")
@Scope("singleton")
public class AccountService extends AccountAbstractService_<SessionData, Account, AccountService>
{
  /**
   * Cette m�thode renvoi la liste compl�te des comptes utilisateur comprenant,
   * en fonction des filtres appliqu�s, la cha�ne de caract�res � rechercher dans
   * un de leurs attributs
   * @param searchString la cha�ne de caract�res � rechercher
   * @param filterList la liste des filtres � appliquer
   * @return La liste filtr�e des comptes utilisateur sans leurs relations
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws ModelArgumentException Si un probl�me intervient lors du filtrage
   * @throws SessionException Si aucune session n'est d�finie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
  public Bid4WinList<Account> getFilteredAccountList(String searchString,
                                                     Bid4WinList<UtilAccount.Filter> filterList)
         throws PersistenceException, ModelArgumentException, SessionException,
                AuthenticationException, AuthorizationException
  {
    Bid4WinList<Account> fullAccountList = this.self().getAccountList();
    return UtilAccount.getFilteredAccountList(fullAccountList, searchString, filterList);
  }

  /**
   * Cette m�thode permet de modifier les informations sur l'utilisateur d'un compte
   * @param accountId Identifiant du compte utilisateur � modifier
   * @param user Informations sur l'utilisateur du compte � prendre en compte
   * @return Le compte utilisateur modifi� sans ses relations
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si le compte utilisateur n'a pu �tre trouv�
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException  Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Account updateUser(String accountId, User user)
         throws PersistenceException, NotFoundEntityException, SessionException,
                AuthenticationException, AuthorizationException
  {
    // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
    this.checkAdminRole();
    // Modifie les informations sur l'utilisateur de compte
    return this.getManager().updateUser(accountId, user);
  }
  /**
   * Cette m�thode permet de modifier les informations sur l'utilisateur du compte
   * connect�
   * @param user Informations sur l'utilisateur du compte � prendre en compte
   * @return Le compte utilisateur modifi� sans ses relations
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Account updateUser(User user)
         throws PersistenceException, SessionException,
                AuthenticationException, AuthorizationException
  {
    // Modifie les informations sur l'utilisateur de compte
    return this.getManager().updateUser(this.getConnectedAccountId(), user);
  }
  /**
   * Cette m�thode permet de modifier la pr�f�rence de langue d'un compte utilisateur
   * @param accountId Identifiant du compte utilisateur � modifier
   * @param language Nouvelle pr�f�rence de langue du compte utilisateur
   * @return Le compte utilisateur modifi� sans ses relations
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si le compte utilisateur n'a pu �tre trouv�
   * @throws UserException Si on d�fini une langue nulle
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException  Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Account updateLanguage(String accountId, Language language)
         throws PersistenceException, NotFoundEntityException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
    this.checkAdminRole();
    // Modifie la pr�f�rence de langue du compte utilisateur
    return this.getManager().updateLanguage(accountId, language);
  }
  /**
   * Cette m�thode permet de modifier la pr�f�rence de langue du compte utilisateur
   * connect�
   * @param language Nouvelle pr�f�rence de langue du compte utilisateur connect�
   * @return Le compte utilisateur modifi� sans ses relations
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si on d�fini une langue nulle
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Account updateLanguage(Language language)
         throws PersistenceException, UserException, SessionException,
                AuthenticationException, AuthorizationException
  {
    // Modifie la pr�f�rence de langue du compte utilisateur
    return this.getManager().updateLanguage(this.getConnectedAccountId(), language);
  }

  /**
   * Getter de la r�f�rence du manager interne de gestion des comptes utilisateur
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.account.AccountAbstractService_#getManager()
   */
  @Override
  protected AccountManager getManager()
  {
    return (AccountManager)super.getManager();
  }
}
