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
 * @author Emeric Fillâtre
 */
@Component("AccountService")
@Scope("singleton")
public class AccountService extends AccountAbstractService_<SessionData, Account, AccountService>
{
  /**
   * Cette méthode renvoi la liste complète des comptes utilisateur comprenant,
   * en fonction des filtres appliqués, la chaîne de caractères à rechercher dans
   * un de leurs attributs
   * @param searchString la chaîne de caractères à rechercher
   * @param filterList la liste des filtres à appliquer
   * @return La liste filtrée des comptes utilisateur sans leurs relations
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws ModelArgumentException Si un problème intervient lors du filtrage
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
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
   * Cette méthode permet de modifier les informations sur l'utilisateur d'un compte
   * @param accountId Identifiant du compte utilisateur à modifier
   * @param user Informations sur l'utilisateur du compte à prendre en compte
   * @return Le compte utilisateur modifié sans ses relations
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si le compte utilisateur n'a pu être trouvé
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException  Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Account updateUser(String accountId, User user)
         throws PersistenceException, NotFoundEntityException, SessionException,
                AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    // Modifie les informations sur l'utilisateur de compte
    return this.getManager().updateUser(accountId, user);
  }
  /**
   * Cette méthode permet de modifier les informations sur l'utilisateur du compte
   * connecté
   * @param user Informations sur l'utilisateur du compte à prendre en compte
   * @return Le compte utilisateur modifié sans ses relations
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
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
   * Cette méthode permet de modifier la préférence de langue d'un compte utilisateur
   * @param accountId Identifiant du compte utilisateur à modifier
   * @param language Nouvelle préférence de langue du compte utilisateur
   * @return Le compte utilisateur modifié sans ses relations
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si le compte utilisateur n'a pu être trouvé
   * @throws UserException Si on défini une langue nulle
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException  Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Account updateLanguage(String accountId, Language language)
         throws PersistenceException, NotFoundEntityException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    // Modifie la préférence de langue du compte utilisateur
    return this.getManager().updateLanguage(accountId, language);
  }
  /**
   * Cette méthode permet de modifier la préférence de langue du compte utilisateur
   * connecté
   * @param language Nouvelle préférence de langue du compte utilisateur connecté
   * @return Le compte utilisateur modifié sans ses relations
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si on défini une langue nulle
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Account updateLanguage(Language language)
         throws PersistenceException, UserException, SessionException,
                AuthenticationException, AuthorizationException
  {
    // Modifie la préférence de langue du compte utilisateur
    return this.getManager().updateLanguage(this.getConnectedAccountId(), language);
  }

  /**
   * Getter de la référence du manager interne de gestion des comptes utilisateur
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.account.AccountAbstractService_#getManager()
   */
  @Override
  protected AccountManager getManager()
  {
    return (AccountManager)super.getManager();
  }
}
