package com.bid4win.commons.manager.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.ConnectionRef;
import com.bid4win.commons.manager.Bid4WinManager_;
import com.bid4win.commons.persistence.dao.account.AccountAbstractDao_;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.security.Credential;
import com.bid4win.commons.persistence.entity.account.security.Password;
import com.bid4win.commons.persistence.entity.account.security.Role;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthenticationException;
import com.bid4win.commons.persistence.entity.connection.DisconnectionReason;
import com.bid4win.commons.persistence.entity.contact.Email;
import com.bid4win.commons.persistence.request.Bid4WinRequest;

/**
 * Manager de base du projet pour la gestion métier des comptes utilisateur<BR>
 * <BR>
 * @param <ACCOUNT> Définition de type de compte utilisateur utilisé par le projet<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class AccountAbstractManager_<ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends Bid4WinManager_<ACCOUNT, AccountAbstractManager_<ACCOUNT>>
{
  /** Référence du DAO de gestion des comptes utilisateurs */
  @Autowired
  @Qualifier("AccountDao")
  private AccountAbstractDao_<ACCOUNT, ?, ?> accountDao;

  /**
   * Cette méthode permet de rechercher un compte utilisateur en fonction de son
   * identifiant de connexion ou de son email
   * @param loginOrEmail Identifiant de connexion ou email du compte utilisateur
   * à rechercher
   * @return Le compte utilisateur trouvé correspondant à l'identifiant de connexion
   * ou à l'email en argument ou null le cas échéant
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  public ACCOUNT findAccount(String loginOrEmail)
         throws PersistenceException//, AuthenticationException
  {
    /*try
    {*/
      loginOrEmail = UtilString.trimNotNull(loginOrEmail).toLowerCase();
      return this.getAccountDao().findOneByLoginOrEmail(loginOrEmail);
    /*}
    catch(UserException ex)
    {
      throw new AuthenticationException(ex.getMessageRef(), DisconnectionReason.NONE);
    }*/
  }
  /**
   * Cette méthode permet de récupérer un compte utilisateur en fonction de son
   * identifiant de connexion ou de son email
   * @param loginOrEmail Identifiant de connexion ou email du compte utilisateur
   * à récupérer
   * @return Le compte utilisateur trouvé correspondant à l'identifiant de connexion
   * ou à l'email en argument
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws AuthenticationException Si le compte utilisateur n'a pu être trouvé
   */
  public ACCOUNT getAccount(String loginOrEmail)
         throws PersistenceException, AuthenticationException
  {
    ACCOUNT account = this.findAccount(loginOrEmail);
    if(account == null)
    {
      throw new AuthenticationException(ConnectionRef.LOGIN_OR_EMAIL_UNKNOWN_ERROR,
                                        DisconnectionReason.NONE);
    }
    return account;
  }
  /**
   * Cette méthode permet de récupérer la liste complète des comptes utilisateur
   * @return La liste complète des comptes utilisateur
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  public Bid4WinList<ACCOUNT> getAccountList() throws PersistenceException
  {
    // TODO ajouter des critères au manager !!!
    return this.getAccountDao().findList(new Bid4WinRequest<ACCOUNT>());
  }
  /**
   * Cette méthode permet de récupérer un compte utilisateur en fonction de son
   * identifiant
   * @param accountId Identifiant du compte utilisateur à récupérer
   * @return Le compte utilisateur correspondant à l'identifiant en argument
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si le compte utilisateur n'a pu être trouvé
   */
  public ACCOUNT getById(String accountId) throws PersistenceException, NotFoundEntityException
  {
    return this.getAccountDao().getById(accountId);
  }
  /**
   * Cette méthode permet de récupérer un compte utilisateur en fonction de son
   * identifiant en bloquant sa modification pour toute autre transaction concurrente
   * @param accountId Identifiant du compte utilisateur à récupérer et à bloquer
   * @return Le compte utilisateur bloqué correspondant à l'identifiant en argument
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si le compte utilisateur n'a pu être trouvé
   */
  public ACCOUNT lockById(String accountId) throws PersistenceException, NotFoundEntityException
  {
    return this.getAccountDao().lockById(accountId);
  }

  /**
   * Cette méthode permet de créer un compte utilisateur
   * @param credential Certificat de connexion du compte utilisateur à créer
   * @param email Adresse email du compte utilisateur à créer
   * @return Le compte utilisateur créé
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante ou si l'identifiant de connexion ou l'email existe
   * déjà en base de données
   * @throws ModelArgumentException Si les paramètres sont invalides
   * @throws UserException Si l'identifiant de connexion ou l'email existe déjà
   * en base de données
   */
  public ACCOUNT createAccount(Credential credential, Email email)
         throws PersistenceException, ModelArgumentException, UserException
  {
    return this.createAccount(this.createAccountEntity(credential, email));
  }
  /**
   * Cette méthode permet de créer un compte utilisateur
   * @param account Compte utilisateur à créer
   * @return Le compte utilisateur créé
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante ou si l'identifiant de connexion ou l'email existe
   * déjà en base de données
   * @throws UserException  Si l'identifiant de connexion ou l'email existe déjà
   * en base de données
   */
  protected ACCOUNT createAccount(ACCOUNT account) throws PersistenceException, UserException
  {
    if(this.getAccountDao().findOneByLogin(account.getCredential().getLogin()) != null)
    {
      throw new UserException(ConnectionRef.LOGIN_EXISTING_ERROR);
    }
    if(this.getAccountDao().findOneByEmail(account.getEmail()) != null)
    {
      throw new UserException(ConnectionRef.EMAIL_EXISTING_ERROR);
    }
    return this.getAccountDao().add(account);
  }

  /**
   * Cette méthode permet de modifier le role d'un compte utilisateur
   * @param accountId Identifiant du compte utilisateur à modifier
   * @param role Nouveau role du compte utilisateur
   * @return Le compte utilisateur modifié
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si le compte utilisateur n'a pu être trouvé
   * @throws UserException Si les arguments sont invalides
   */
  public ACCOUNT updateRole(String accountId, Role role)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    ACCOUNT account = this.lockById(accountId);
    account.getCredential().defineRole(role);
    return this.getAccountDao().update(account);
  }
  /**
   * Cette méthode permet de modifier le mot de passe de connexion d'un compte
   * utilisateur
   * @param accountId Identifiant du compte utilisateur à modifier
   * @param oldPassword Ancien mot de passe de connexion du compte utilisateur
   * @param newPassword Nouveau mot de passe de connexion du compte utilisateur
   * @return Le compte utilisateur modifié
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si le compte utilisateur n'a pu être trouvé
   * @throws UserException Si l'ancien mot de passe de connexion en argument ne
   * correspond pas à celui du compte utilisateur
   */
  public ACCOUNT updatePassword(String accountId, Password oldPassword, Password newPassword)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    ACCOUNT account = this.lockById(accountId);
    UtilObject.checkEquals("oldPassword", oldPassword, account.getCredential().getPassword(),
                           ConnectionRef.PASSWORD_WRONG_ERROR);
    account.getCredential().definePassword(newPassword);
    return this.getAccountDao().update(account);
  }

  /**
   * Cette méthode permet de créer l'objet correspondant au compte utilisateur
   * défini en argument
   * @param credential Certificat de connexion du compte utilisateur à créer
   * @param email Adresse email du compte utilisateur à créer
   * @return L'objet correspondant au compte utilisateur défini en argument
   * @throws ModelArgumentException Si les paramètres sont invalides
   */
  protected abstract ACCOUNT createAccountEntity(Credential credential, Email email)
            throws ModelArgumentException;

  /**
   * Getter de la référence du DAO des comptes utilisateur
   * @return La référence du DAO des comptes utilisateur
   */
  protected AccountAbstractDao_<ACCOUNT, ?, ?> getAccountDao()
  {
    return this.accountDao;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.Bid4WinManager_#getAccountManager()
   */
  @Override
  protected AccountAbstractManager_<ACCOUNT> getAccountManager()
  {
    return this.self();
  }
  /**
   *
   * TODO A COMMENTER
   * @param accountManager {@inheritDoc}
   * @see com.bid4win.commons.manager.Bid4WinManager_#setAccountManager(com.bid4win.commons.manager.account.AccountAbstractManager_)
   */
  @Override
  protected void setAccountManager(AccountAbstractManager_<ACCOUNT> accountManager)
  {
    // La méthode est redéfinie pour être débrayée
  }
}
