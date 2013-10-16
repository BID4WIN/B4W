package com.bid4win.service.connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.account.security.Credential;
import com.bid4win.commons.persistence.entity.account.security.Login;
import com.bid4win.commons.persistence.entity.account.security.Password;
import com.bid4win.commons.persistence.entity.account.security.Role;
import com.bid4win.commons.persistence.entity.account.security.UtilSecurity;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthenticationException;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthorizationException;
import com.bid4win.commons.persistence.entity.account.security.exception.SessionException;
import com.bid4win.commons.persistence.entity.contact.Email;
import com.bid4win.commons.service.connection.ConnectionAbstractService_;
import com.bid4win.persistence.entity.EntityGenerator;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.connection.Connection;
import com.bid4win.persistence.entity.connection.ConnectionHistory;
import com.bid4win.persistence.entity.connection.PasswordReinit;
import com.bid4win.persistence.entity.connection.Subscription;

/**
 * Service du projet pour la gestion des connexions incluant la gestion des transactions
 * ainsi que celle des habilitations<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("ConnectionService")
@Scope("singleton")
public class ConnectionService
       extends ConnectionAbstractService_<Connection, ConnectionHistory, PasswordReinit,
                                          SessionData, Account, ConnectionService>
{
  @Autowired
  @Qualifier("EntityGenerator")
  EntityGenerator generator = null;

  private static ConnectionService instance = null;
  public static ConnectionService getInstance()
  {
    return ConnectionService.instance;
  }

  protected ConnectionService()
  {
    ConnectionService.instance = this;
  }  /**
   * Getter du gestionnaire des sessions
   * @return Le gestionnaire des sessions
   */
  @Override
  public SessionHandler getSessionHandler()
  {
    return (SessionHandler)super.getSessionHandler();
  }


  /**
   * Cette méthode permet de récupérer le compte utilisateur connecté sur la session
   * courante
   * @return Le compte utilisateur connecté sur la session courante sans ses relations
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthenticationException Si aucun compte utilisateur n'est considéré
   * connecté
   * @throws AuthorizationException Si le compte utilisateur n'a pas les habilitations
   * suffisantes pour être considéré connecté
   */
  @Override
  public Account getConnectedAccount()
         throws PersistenceException, SessionException,
                AuthenticationException, AuthorizationException
  {
    Account account = this.getSessionHandler().getConnectedAccount();
    if(account == null)
    {
      this.connectAccount(this.authentify());
    }
    return this.getSessionHandler().getConnectedAccount();
  }
  /**
   * Cette méthode permet de définir le compte utilisateur connecté sur la session
   * courante
   * @param account Compte utilisateur connecté sur la session courante
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthorizationException Si le compte utilisateur n'a pas les habilitations
   * suffisantes pour être considéré connecté
   */
  private void connectAccount(Connection connection)
          throws SessionException, AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation minimum de l'utilisateur
    UtilSecurity.checkRole(connection.getAccount(), Role.BASIC);
    this.getSessionHandler().connect(connection);
  }
  /**
   * Cette méthode permet de retirer le compte utilisateur connecté de la session
   * courante
   * @throws SessionException Si aucune session n'est définie
   */
  private void disconnectAccount() throws SessionException
  {
    this.getSessionHandler().disconnect();
  }
  /**
   * Cette méthode permet d'identifier le compte utilisateur connecté
   * @return La définition du compte utilisateur connecté sans ses relations
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthenticationException Si aucun compte utilisateur n'est considéré
   * connecté
   */
  private Connection authentify()
          throws PersistenceException, SessionException, AuthenticationException
  {
    return this.getManager().authentify(this.getSession());
  }

  /**
   * Cette méthode permet de connecter un compte utilisateur au système. On peut
   * y définir la rémanence de celle-ci, c'est à dire si l'appareil doit être
   * considéré comme perpétuellement connecté. Toute demande de ré-initialisation
   * de mot de passe en attente sera annulée. La connexion sera potentiellement
   * récupérée d'une connexion active si elles correspondent
   * @param loginOrEmail Identifiant de connexion ou adresse email du compte utilisateur
   * à connecter
   * @param password Mot de passe de connexion du compte utilisateur à connecter
   * @param remanent Flag autorisant la rémanence de la connexion si à true
   * @return La connection effectuée
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si un problème empêche la création de la connexion
   * @throws SessionException Si une connexion existe déjà avec le même identifiant
   * de session mais est désactivée, provient d'une adresse IP ou d'un process
   * différent ou ne correspond pas à la connexion demandée, la session devrait
   * alors être invalidée et la tentative de connexion réessayée
   * @throws AuthenticationException Si le compte utilisateur n'a pu être trouvé
   * ou si le mot de passe n'est pas celui associé au compte utilisateur
   * @throws AuthorizationException Si le compte utilisateur n'a pas les habilitations
   * suffisantes pour être considéré connecté
   */
  @Override
  public Connection connect(String loginOrEmail, Password password, boolean remanent)
         throws PersistenceException, UserException, SessionException,
                AuthenticationException, AuthorizationException
  {
    if(!loginOrEmail.equals("tazous"))
    {
      throw new AuthorizationException();
    }
    // Connecte l'utilisateur
    try
    {
      Connection connection = new Connection(this.getSession().getSessionId(),
                                             new Account(new Credential(new Login(loginOrEmail), new Password("123456")),
                                             new Email("emeric.fillatre@gmail.com")),
                                             this.getSession().getIpAddress(), remanent);
      connection.getAccount().getCredential().defineRole(Role.SUPER);
      // Défini le compte utilisateur connecté sur la session courante
      this.connectAccount(connection);
      return connection.loadRelation();
    }
    catch(ModelArgumentException ex)
    {
      throw new UserException(MessageRef.UNKNOWN_ERROR, ex);
    }
  }
  /**
   * Cette méthode permet de re-connecter un compte utilisateur au système à partir
   * d'un appareil sur lequel la rémanence d'une connexion a été activée
   * @param fingerPrint Empreinte unique de la dernière rémanence de connexion
   * @param accountId Identifiant du compte utilisateur à re-connecter
   * @return La connection effectuée
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si un problème empêche la création de la connexion
   * @throws SessionException Si l'identifiant de session ne peut pas être réutilisé.
   * La session devrait alors être invalidée et la tentative de connexion réessayée
   * @throws AuthenticationException Si la connexion a été invalidé, n'existe pas
   * ou ne correspond pas au compte utilisateur demandé.
   * Attention la transaction ne doit pas être annulée dans ce cas !!!
   * @throws AuthorizationException Si le compte utilisateur n'a pas les habilitations
   * suffisantes pour être considéré connecté
   */
  @Override
  public Connection reconnect(String fingerPrint, String accountId)
         throws PersistenceException, UserException, SessionException,
                AuthenticationException, AuthorizationException
  {
    // Reconnecte l'utilisateur
    try
    {
      Connection connection = new Connection(this.getSession().getSessionId(),
          new Account(new Credential(new Login("tazous"), new Password("123456")),
          new Email("emeric.fillatre@gmail.com")),
          this.getSession().getIpAddress(), true);
      // Défini le compte utilisateur connecté sur la session courante
      this.connectAccount(connection);
      return connection.loadRelation();
    }
    catch(ModelArgumentException ex)
    {
      throw new UserException(MessageRef.UNKNOWN_ERROR, ex);
    }
  }

  /**
   * Cette méthode permet de terminer la connexion d'un compte utilisateur au système
   * tout en gardant sa rémanence si celle-ci avait été activée
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws SessionException Si aucune session n'est définie
   */
  @Override
  public void endConnection() throws PersistenceException, SessionException
  {
    this.disconnectAccount();
  }
  /**
   * Cette méthode permet de déconnecter un compte utilisateur du système et d'
   * arrêter la rémanence de sa connexion si celle-ci avait été activée
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws SessionException Si aucune session n'est définie
   */
  @Override
  public void disconnect() throws PersistenceException, SessionException
  {
    this.disconnectAccount();
  }

  /**
   * Cette méthode permet de modifier le mot de passe de connexion d'un compte
   * utilisateur en prenant en charge la suppression de la rémanence de toutes
   * les connexions le concernant exceptée celle utilisée pour la modification
   * ainsi que toute demande de ré-initialisation de mot de passe potentiellement
   * existante
   * @param oldPassword Ancien mot de passe de connexion du compte utilisateur
   * @param newPassword Nouveau mot de passe de connexion du compte utilisateur
   * @return Le compte utilisateur modifié sans ses relations
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si le compte utilisateur n'a pu être trouvé
   * @throws UserException Si l'ancien mot de passe de connexion en argument ne
   * correspond pas à celui du compte utilisateur
   * @throws SessionException Si aucune session n'est définie
   */
  @Override
  public Account updatePassword(Password oldPassword, Password newPassword)
         throws PersistenceException, NotFoundEntityException, UserException, SessionException
  {
    try
    {
      return new Account(new Credential(new Login("tazous"), new Password("123456")),
          new Email("emeric.fillatre@gmail.com"));
    }
    catch(ModelArgumentException ex)
    {
      throw new UserException(MessageRef.UNKNOWN_ERROR, ex);
    }
  }
  /**
   * Cette méthode permet de demander la ré-initialisation du mot de passe de connexion
   * d'un compte utilisateur
   * @param loginOrEmail Identifiant de connexion ou adresse email du compte utilisateur
   * dont on demande la ré-initialisation de mot de passe de connexion
   * @return La référence de la demande de ré-initialisation de mot de passe qu'il
   * faudra utiliser
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si le compte utilisateur n'a pu être trouvé
   */
  @Override
  public PasswordReinit reinitPassword(String loginOrEmail) throws PersistenceException, UserException
  {
    try
    {
      return new PasswordReinit(new Account(new Credential(new Login("tazous"), new Password("123456")),
            new Email("emeric.fillatre@gmail.com")));
    }
    catch(ModelArgumentException ex)
    {
      throw new UserException(MessageRef.UNKNOWN_ERROR, ex);
    }
  }
  /**
   * Cette méthode permet de récupérer la référence de la demande de ré-initialisation
   * de mot de passe dont la clé est fournie en argument
   * @param reinitKey Clé de la référence de la demande de ré-initialisation de
   * mot de passe à récupérer
   * @param accountId Identifiant du compte utilisateur associé à la demande de
   * ré-initialisation de mot de passe à récupérer
   * @return La référence de la demande de ré-initialisation de mot de passe dont
   * la clé est fournie en argument
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucune référence de demande de ré-initialisation
   * de mot de passe n'a pu être récupérée avec la clé et l'identifiant de compte
   * utilisateur en argument
   */
  @Override
  public PasswordReinit getPasswordReinit(String reinitKey, String accountId)
         throws PersistenceException, NotFoundEntityException
  {
    try
    {
      return new PasswordReinit(new Account(new Credential(new Login("tazous"), new Password("123456")),
            new Email("emeric.fillatre@gmail.com")));
    }
    catch(ModelArgumentException ex)
    {
      throw new NotFoundEntityException(Account.class, ex.getMessage());
    }
    catch(UserException ex)
    {
      throw new NotFoundEntityException(Account.class, ex.getMessage());
    }
  }
  /**
   * Cette méthode permet d'effectuer la ré-initialisation du mot de passe de connexion
   * d'un compte utilisateur en prenant en charge la suppression de la rémanence
   * de toutes les connexions le concernant. Attention, le compte utilisateur ne
   * sera pas considéré automatiquement connecté au système
   * @param reinitKey Clé de la référence de la demande de ré-initialisation de
   * mot de passe
   * @param accountId Identifiant du compte utilisateur associé à la demande de
   * ré-initialisation de mot de passe
   * @param password Nouveau mot de passe de connexion du compte utilisateur associé
   * à la demande de ré-initialisation
   * @return Le compte utilisateur modifié sans ses relations
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucune référence de demande de ré-initialisation
   * de mot de passe n'a pu être récupérée avec la clé et l'identifiant de compte
   * utilisateur en argument
   * @throws ModelArgumentException Si les paramètres sont invalides
   */
  @Override
  public Account reinitPassword(String reinitKey, String accountId, Password password)
         throws PersistenceException, NotFoundEntityException,
                ModelArgumentException, SessionException
  {
    try
    {
    return new Account(new Credential(new Login("tazous"), new Password("123456")),
        new Email("emeric.fillatre@gmail.com"));
    }
    catch(UserException ex)
    {
      throw new ModelArgumentException(ex);
    }
  }

  /**
   * Cette méthode permet d'effectuer l'inscription d'un compte utilisateur. Le
   * compte utilisateur sera considéré comme non validé et n'aura donc pas accès
   * à toutes les fonctionnalités. L'inscription ne connectera pas automatiquement
   * le compte utilisateur
   * @param credential Certificat de connexion du compte utilisateur à créer
   * @param email Email du compte utilisateur à créer
   * @return La référence d'inscription qu'il faudra utiliser pour la validation
   * liée à son compte utilisateur sans ses relations
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante ou si l'identifiant de connexion ou l'email existe
   * déjà en base de données
   * @throws ModelArgumentException Si les paramètres sont invalides
   * @throws UserException Si l'identifiant de connexion ou l'email existe déjà
   * en base de données
   * @throws AuthenticationException Si un compte utilisateur est déjà connecté
   */
  public Subscription subscribe(Credential credential, Email email)
         throws PersistenceException, ModelArgumentException, UserException, AuthenticationException
  {
    // @ Vérifie qu'aucun compte utilisateur n'est connecté
    //this.checkNotConnected(); TODO voir si besoin de le tester
    return new Subscription(new Account(credential, email));
  }
  /**
   * Cette méthode permet de valider l'inscription d'un compte utilisateur. Celui-ci
   * aura donc accès aux différentes fonctionnalités qui lui sont réservées. La
   * validation ne connectera pas automatiquement le compte utilisateur
   * @param validationKey Clé de validation du compte utilisateur
   * @param accountId Compte utilisateur à valider
   * @return Le compte utilisateur validé sans ses relations
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si la clé de validation n'a pu être trouvée
   * ou si l'identifiant du compte utilisateur donné ne lui correspond pas
   * @throws ModelArgumentException Si la validation de l'inscription échoue
   * @throws UserException Si l'inscription du compte utilisateur est déjà validée
   */
  public Account validateSubscription(String validationKey, String accountId)
         throws PersistenceException, NotFoundEntityException, ModelArgumentException, UserException
  {
    try
    {
      return this.generator.createAccount();
    }
    catch(Bid4WinException ex)
    {
      throw new UserException(MessageRef.UNKNOWN_ERROR, ex);
    }
  }
}
