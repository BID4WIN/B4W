package com.bid4win.manager.connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.ConnectionRef;
import com.bid4win.commons.manager.connection.ConnectionAbstractManager_;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.account.security.Credential;
import com.bid4win.commons.persistence.entity.account.security.Role;
import com.bid4win.commons.persistence.entity.contact.Email;
import com.bid4win.persistence.dao.connection.SubscriptionDao;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.connection.Connection;
import com.bid4win.persistence.entity.connection.ConnectionHistory;
import com.bid4win.persistence.entity.connection.PasswordReinit;
import com.bid4win.persistence.entity.connection.Subscription;
import com.bid4win.service.connection.SessionData;

/**
 * Manager du projet pour la gestion des connexions incluant leur gestion m�tier.<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Component("ConnectionManager")
@Scope("singleton")
public class ConnectionManager
       extends ConnectionAbstractManager_<Connection, ConnectionHistory,
                                          PasswordReinit, SessionData, Account>
{
  /** R�f�rence du DAO des inscriptions */
  @Autowired
  @Qualifier("SubscriptionDao")
  private SubscriptionDao subscriptionDao = null;

  /**
   * Cette m�thode permet d'effectuer l'inscription d'un compte utilisateur. Le
   * compte utilisateur sera consid�r� comme non valid� et n'aura donc pas acc�s
   * � toutes les fonctionnalit�s. L'inscription ne connectera pas automatiquement
   * le compte utilisateur
   * @param credential Certificat de connexion du compte utilisateur � cr�er
   * @param email Email du compte utilisateur � cr�er
   * @return La r�f�rence d'inscription qu'il faudra utiliser pour la validation
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante ou si l'identifiant de connexion ou l'email existe
   * d�j� en base de donn�es
   * @throws ModelArgumentException Si les param�tres sont invalides
   * @throws UserException Si l'identifiant de connexion ou l'email existe d�j�
   * en base de donn�es
   */
  public Subscription subscribe(Credential credential, Email email)
         throws PersistenceException, ModelArgumentException, UserException
  {
    // Le compte � cr�er est mis en attente de validation
    UtilObject.checkNotNull("creadential", credential).defineRole(Role.WAIT);
    // Cr�e le compte utilisateur
    Account account = this.getAccountManager().createAccount(credential, email);
    // TODO bloquer l'ip si tentative de cr�ation en masse ???
    // Cr�e la r�f�rence d'inscription et la retourne
    return this.getSubscriptionDao().add(new Subscription(account));
  }
  /*
   * Cette m�thode
   * @param loginOrEmail
   * @return
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException
   * @throws UserException
   */
 /* public Subscription getSubscriptionToValidate(String loginOrEmail)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    // R�cup�re le compte utilisateur en fonction de son identifiant de connexion
    // ou de son email
    Account account = this.getAccountManager().getAccount(loginOrEmail);
    // R�cup�re la r�f�rence d'inscription correspondante
    Subscription subscription = this.getSubscriptionDao().getOneByAccount(account);
    // L'inscription ne doit pas d�j� �tre valid�e
    if(subscription.isValidated())
    {
      throw new UserException(MessageRef.ERROR_SUBSCRIPTION_VALIDATED);
    }
    // Retourne la r�f�rence d'inscription
    return subscription;
  }*/
  /**
   * Cette m�thode permet de valider l'inscription d'un compte utilisateur. Celui-ci
   * aura donc acc�s aux diff�rentes fonctionnalit�s qui lui sont r�serv�es. La
   * validation ne connectera pas automatiquement le compte utilisateur
   * @param validationKey Cl� de validation du compte utilisateur
   * @param accountId Compte utilisateur � valider
   * @return Le compte utilisateur valid�
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si la cl� de validation n'a pu �tre trouv�e
   * ou si l'identifiant du compte utilisateur donn� ne lui correspond pas
   * @throws ModelArgumentException Si la validation de l'inscription �choue
   * @throws UserException Si l'inscription du compte utilisateur est d�j� valid�e
   */
  public Account validateSubscription(String validationKey, String accountId)
         throws PersistenceException, NotFoundEntityException, ModelArgumentException, UserException
  {
    // TODO bloquer l'ip si n�cessaire
    Subscription subscription = this.getSubscriptionDao().getById(validationKey);
    // L'inscription ne doit pas d�j� �tre valid�e
    if(subscription.isValidated())
    {
      throw new UserException(ConnectionRef.CONNECTION_SUBSCRIPTION_VALIDATED_ERROR);
    }
    Account account = subscription.getAccount();
    // L'identifiant du compte utilisateur doit correspondre � celui en argument
    if(!account.getId().equals(accountId))
    {
      throw new NotFoundEntityException(PasswordReinit.class);
    }
    subscription.defineValidationDate();
    this.getSubscriptionDao().update(subscription);
    return this.getAccountManager().updateRole(account.getId(), Role.USER);
  }

  /**
   *
   * TODO A COMMENTER
   * @param session {@inheritDoc}
   * @param account {@inheritDoc}
   * @param remanent {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.manager.connection.ConnectionAbstractManager_#createConnectionEntity(com.bid4win.commons.service.connection.SessionDataAbstract, com.bid4win.commons.persistence.entity.account.AccountAbstract, boolean)
   */
  @Override
  protected Connection createConnectionEntity(SessionData session,
                                              Account account,
                                              boolean remanent)
            throws UserException
  {
    return new Connection(session.getSessionId(), account,
                          session.getIpAddress(), remanent);
  }
  /**
   *
   * TODO A COMMENTER
   * @param account {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.manager.connection.ConnectionAbstractManager_#createReinitEntity(com.bid4win.commons.persistence.entity.account.AccountAbstract)
   */
  @Override
  protected PasswordReinit createReinitEntity(Account account) throws UserException
  {
    return new PasswordReinit(account);
  }

  /**
   * Getter de la r�f�rence du DAO des inscriptions
   * @return La r�f�rence du DAO des inscriptions
   */
  private SubscriptionDao getSubscriptionDao()
  {
    return this.subscriptionDao;
  }
}
