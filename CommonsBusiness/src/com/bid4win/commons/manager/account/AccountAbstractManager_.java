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
 * Manager de base du projet pour la gestion m�tier des comptes utilisateur<BR>
 * <BR>
 * @param <ACCOUNT> D�finition de type de compte utilisateur utilis� par le projet<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class AccountAbstractManager_<ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends Bid4WinManager_<ACCOUNT, AccountAbstractManager_<ACCOUNT>>
{
  /** R�f�rence du DAO de gestion des comptes utilisateurs */
  @Autowired
  @Qualifier("AccountDao")
  private AccountAbstractDao_<ACCOUNT, ?, ?> accountDao;

  /**
   * Cette m�thode permet de rechercher un compte utilisateur en fonction de son
   * identifiant de connexion ou de son email
   * @param loginOrEmail Identifiant de connexion ou email du compte utilisateur
   * � rechercher
   * @return Le compte utilisateur trouv� correspondant � l'identifiant de connexion
   * ou � l'email en argument ou null le cas �ch�ant
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
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
   * Cette m�thode permet de r�cup�rer un compte utilisateur en fonction de son
   * identifiant de connexion ou de son email
   * @param loginOrEmail Identifiant de connexion ou email du compte utilisateur
   * � r�cup�rer
   * @return Le compte utilisateur trouv� correspondant � l'identifiant de connexion
   * ou � l'email en argument
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws AuthenticationException Si le compte utilisateur n'a pu �tre trouv�
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
   * Cette m�thode permet de r�cup�rer la liste compl�te des comptes utilisateur
   * @return La liste compl�te des comptes utilisateur
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  public Bid4WinList<ACCOUNT> getAccountList() throws PersistenceException
  {
    // TODO ajouter des crit�res au manager !!!
    return this.getAccountDao().findList(new Bid4WinRequest<ACCOUNT>());
  }
  /**
   * Cette m�thode permet de r�cup�rer un compte utilisateur en fonction de son
   * identifiant
   * @param accountId Identifiant du compte utilisateur � r�cup�rer
   * @return Le compte utilisateur correspondant � l'identifiant en argument
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si le compte utilisateur n'a pu �tre trouv�
   */
  public ACCOUNT getById(String accountId) throws PersistenceException, NotFoundEntityException
  {
    return this.getAccountDao().getById(accountId);
  }
  /**
   * Cette m�thode permet de r�cup�rer un compte utilisateur en fonction de son
   * identifiant en bloquant sa modification pour toute autre transaction concurrente
   * @param accountId Identifiant du compte utilisateur � r�cup�rer et � bloquer
   * @return Le compte utilisateur bloqu� correspondant � l'identifiant en argument
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si le compte utilisateur n'a pu �tre trouv�
   */
  public ACCOUNT lockById(String accountId) throws PersistenceException, NotFoundEntityException
  {
    return this.getAccountDao().lockById(accountId);
  }

  /**
   * Cette m�thode permet de cr�er un compte utilisateur
   * @param credential Certificat de connexion du compte utilisateur � cr�er
   * @param email Adresse email du compte utilisateur � cr�er
   * @return Le compte utilisateur cr��
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante ou si l'identifiant de connexion ou l'email existe
   * d�j� en base de donn�es
   * @throws ModelArgumentException Si les param�tres sont invalides
   * @throws UserException Si l'identifiant de connexion ou l'email existe d�j�
   * en base de donn�es
   */
  public ACCOUNT createAccount(Credential credential, Email email)
         throws PersistenceException, ModelArgumentException, UserException
  {
    return this.createAccount(this.createAccountEntity(credential, email));
  }
  /**
   * Cette m�thode permet de cr�er un compte utilisateur
   * @param account Compte utilisateur � cr�er
   * @return Le compte utilisateur cr��
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante ou si l'identifiant de connexion ou l'email existe
   * d�j� en base de donn�es
   * @throws UserException  Si l'identifiant de connexion ou l'email existe d�j�
   * en base de donn�es
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
   * Cette m�thode permet de modifier le role d'un compte utilisateur
   * @param accountId Identifiant du compte utilisateur � modifier
   * @param role Nouveau role du compte utilisateur
   * @return Le compte utilisateur modifi�
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si le compte utilisateur n'a pu �tre trouv�
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
   * Cette m�thode permet de modifier le mot de passe de connexion d'un compte
   * utilisateur
   * @param accountId Identifiant du compte utilisateur � modifier
   * @param oldPassword Ancien mot de passe de connexion du compte utilisateur
   * @param newPassword Nouveau mot de passe de connexion du compte utilisateur
   * @return Le compte utilisateur modifi�
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si le compte utilisateur n'a pu �tre trouv�
   * @throws UserException Si l'ancien mot de passe de connexion en argument ne
   * correspond pas � celui du compte utilisateur
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
   * Cette m�thode permet de cr�er l'objet correspondant au compte utilisateur
   * d�fini en argument
   * @param credential Certificat de connexion du compte utilisateur � cr�er
   * @param email Adresse email du compte utilisateur � cr�er
   * @return L'objet correspondant au compte utilisateur d�fini en argument
   * @throws ModelArgumentException Si les param�tres sont invalides
   */
  protected abstract ACCOUNT createAccountEntity(Credential credential, Email email)
            throws ModelArgumentException;

  /**
   * Getter de la r�f�rence du DAO des comptes utilisateur
   * @return La r�f�rence du DAO des comptes utilisateur
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
    // La m�thode est red�finie pour �tre d�bray�e
  }
}
