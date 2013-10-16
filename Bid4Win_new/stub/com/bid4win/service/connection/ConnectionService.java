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
 * @author Emeric Fill�tre
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
   * Cette m�thode permet de r�cup�rer le compte utilisateur connect� sur la session
   * courante
   * @return Le compte utilisateur connect� sur la session courante sans ses relations
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws SessionException Si aucune session n'est d�finie
   * @throws AuthenticationException Si aucun compte utilisateur n'est consid�r�
   * connect�
   * @throws AuthorizationException Si le compte utilisateur n'a pas les habilitations
   * suffisantes pour �tre consid�r� connect�
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
   * Cette m�thode permet de d�finir le compte utilisateur connect� sur la session
   * courante
   * @param account Compte utilisateur connect� sur la session courante
   * @throws SessionException Si aucune session n'est d�finie
   * @throws AuthorizationException Si le compte utilisateur n'a pas les habilitations
   * suffisantes pour �tre consid�r� connect�
   */
  private void connectAccount(Connection connection)
          throws SessionException, AuthenticationException, AuthorizationException
  {
    // @ V�rifie le niveau d'habilitation minimum de l'utilisateur
    UtilSecurity.checkRole(connection.getAccount(), Role.BASIC);
    this.getSessionHandler().connect(connection);
  }
  /**
   * Cette m�thode permet de retirer le compte utilisateur connect� de la session
   * courante
   * @throws SessionException Si aucune session n'est d�finie
   */
  private void disconnectAccount() throws SessionException
  {
    this.getSessionHandler().disconnect();
  }
  /**
   * Cette m�thode permet d'identifier le compte utilisateur connect�
   * @return La d�finition du compte utilisateur connect� sans ses relations
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws SessionException Si aucune session n'est d�finie
   * @throws AuthenticationException Si aucun compte utilisateur n'est consid�r�
   * connect�
   */
  private Connection authentify()
          throws PersistenceException, SessionException, AuthenticationException
  {
    return this.getManager().authentify(this.getSession());
  }

  /**
   * Cette m�thode permet de connecter un compte utilisateur au syst�me. On peut
   * y d�finir la r�manence de celle-ci, c'est � dire si l'appareil doit �tre
   * consid�r� comme perp�tuellement connect�. Toute demande de r�-initialisation
   * de mot de passe en attente sera annul�e. La connexion sera potentiellement
   * r�cup�r�e d'une connexion active si elles correspondent
   * @param loginOrEmail Identifiant de connexion ou adresse email du compte utilisateur
   * � connecter
   * @param password Mot de passe de connexion du compte utilisateur � connecter
   * @param remanent Flag autorisant la r�manence de la connexion si � true
   * @return La connection effectu�e
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si un probl�me emp�che la cr�ation de la connexion
   * @throws SessionException Si une connexion existe d�j� avec le m�me identifiant
   * de session mais est d�sactiv�e, provient d'une adresse IP ou d'un process
   * diff�rent ou ne correspond pas � la connexion demand�e, la session devrait
   * alors �tre invalid�e et la tentative de connexion r�essay�e
   * @throws AuthenticationException Si le compte utilisateur n'a pu �tre trouv�
   * ou si le mot de passe n'est pas celui associ� au compte utilisateur
   * @throws AuthorizationException Si le compte utilisateur n'a pas les habilitations
   * suffisantes pour �tre consid�r� connect�
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
      // D�fini le compte utilisateur connect� sur la session courante
      this.connectAccount(connection);
      return connection.loadRelation();
    }
    catch(ModelArgumentException ex)
    {
      throw new UserException(MessageRef.UNKNOWN_ERROR, ex);
    }
  }
  /**
   * Cette m�thode permet de re-connecter un compte utilisateur au syst�me � partir
   * d'un appareil sur lequel la r�manence d'une connexion a �t� activ�e
   * @param fingerPrint Empreinte unique de la derni�re r�manence de connexion
   * @param accountId Identifiant du compte utilisateur � re-connecter
   * @return La connection effectu�e
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si un probl�me emp�che la cr�ation de la connexion
   * @throws SessionException Si l'identifiant de session ne peut pas �tre r�utilis�.
   * La session devrait alors �tre invalid�e et la tentative de connexion r�essay�e
   * @throws AuthenticationException Si la connexion a �t� invalid�, n'existe pas
   * ou ne correspond pas au compte utilisateur demand�.
   * Attention la transaction ne doit pas �tre annul�e dans ce cas !!!
   * @throws AuthorizationException Si le compte utilisateur n'a pas les habilitations
   * suffisantes pour �tre consid�r� connect�
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
      // D�fini le compte utilisateur connect� sur la session courante
      this.connectAccount(connection);
      return connection.loadRelation();
    }
    catch(ModelArgumentException ex)
    {
      throw new UserException(MessageRef.UNKNOWN_ERROR, ex);
    }
  }

  /**
   * Cette m�thode permet de terminer la connexion d'un compte utilisateur au syst�me
   * tout en gardant sa r�manence si celle-ci avait �t� activ�e
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws SessionException Si aucune session n'est d�finie
   */
  @Override
  public void endConnection() throws PersistenceException, SessionException
  {
    this.disconnectAccount();
  }
  /**
   * Cette m�thode permet de d�connecter un compte utilisateur du syst�me et d'
   * arr�ter la r�manence de sa connexion si celle-ci avait �t� activ�e
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws SessionException Si aucune session n'est d�finie
   */
  @Override
  public void disconnect() throws PersistenceException, SessionException
  {
    this.disconnectAccount();
  }

  /**
   * Cette m�thode permet de modifier le mot de passe de connexion d'un compte
   * utilisateur en prenant en charge la suppression de la r�manence de toutes
   * les connexions le concernant except�e celle utilis�e pour la modification
   * ainsi que toute demande de r�-initialisation de mot de passe potentiellement
   * existante
   * @param oldPassword Ancien mot de passe de connexion du compte utilisateur
   * @param newPassword Nouveau mot de passe de connexion du compte utilisateur
   * @return Le compte utilisateur modifi� sans ses relations
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si le compte utilisateur n'a pu �tre trouv�
   * @throws UserException Si l'ancien mot de passe de connexion en argument ne
   * correspond pas � celui du compte utilisateur
   * @throws SessionException Si aucune session n'est d�finie
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
   * Cette m�thode permet de demander la r�-initialisation du mot de passe de connexion
   * d'un compte utilisateur
   * @param loginOrEmail Identifiant de connexion ou adresse email du compte utilisateur
   * dont on demande la r�-initialisation de mot de passe de connexion
   * @return La r�f�rence de la demande de r�-initialisation de mot de passe qu'il
   * faudra utiliser
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si le compte utilisateur n'a pu �tre trouv�
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
   * Cette m�thode permet de r�cup�rer la r�f�rence de la demande de r�-initialisation
   * de mot de passe dont la cl� est fournie en argument
   * @param reinitKey Cl� de la r�f�rence de la demande de r�-initialisation de
   * mot de passe � r�cup�rer
   * @param accountId Identifiant du compte utilisateur associ� � la demande de
   * r�-initialisation de mot de passe � r�cup�rer
   * @return La r�f�rence de la demande de r�-initialisation de mot de passe dont
   * la cl� est fournie en argument
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucune r�f�rence de demande de r�-initialisation
   * de mot de passe n'a pu �tre r�cup�r�e avec la cl� et l'identifiant de compte
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
   * Cette m�thode permet d'effectuer la r�-initialisation du mot de passe de connexion
   * d'un compte utilisateur en prenant en charge la suppression de la r�manence
   * de toutes les connexions le concernant. Attention, le compte utilisateur ne
   * sera pas consid�r� automatiquement connect� au syst�me
   * @param reinitKey Cl� de la r�f�rence de la demande de r�-initialisation de
   * mot de passe
   * @param accountId Identifiant du compte utilisateur associ� � la demande de
   * r�-initialisation de mot de passe
   * @param password Nouveau mot de passe de connexion du compte utilisateur associ�
   * � la demande de r�-initialisation
   * @return Le compte utilisateur modifi� sans ses relations
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucune r�f�rence de demande de r�-initialisation
   * de mot de passe n'a pu �tre r�cup�r�e avec la cl� et l'identifiant de compte
   * utilisateur en argument
   * @throws ModelArgumentException Si les param�tres sont invalides
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
   * Cette m�thode permet d'effectuer l'inscription d'un compte utilisateur. Le
   * compte utilisateur sera consid�r� comme non valid� et n'aura donc pas acc�s
   * � toutes les fonctionnalit�s. L'inscription ne connectera pas automatiquement
   * le compte utilisateur
   * @param credential Certificat de connexion du compte utilisateur � cr�er
   * @param email Email du compte utilisateur � cr�er
   * @return La r�f�rence d'inscription qu'il faudra utiliser pour la validation
   * li�e � son compte utilisateur sans ses relations
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante ou si l'identifiant de connexion ou l'email existe
   * d�j� en base de donn�es
   * @throws ModelArgumentException Si les param�tres sont invalides
   * @throws UserException Si l'identifiant de connexion ou l'email existe d�j�
   * en base de donn�es
   * @throws AuthenticationException Si un compte utilisateur est d�j� connect�
   */
  public Subscription subscribe(Credential credential, Email email)
         throws PersistenceException, ModelArgumentException, UserException, AuthenticationException
  {
    // @ V�rifie qu'aucun compte utilisateur n'est connect�
    //this.checkNotConnected(); TODO voir si besoin de le tester
    return new Subscription(new Account(credential, email));
  }
  /**
   * Cette m�thode permet de valider l'inscription d'un compte utilisateur. Celui-ci
   * aura donc acc�s aux diff�rentes fonctionnalit�s qui lui sont r�serv�es. La
   * validation ne connectera pas automatiquement le compte utilisateur
   * @param validationKey Cl� de validation du compte utilisateur
   * @param accountId Compte utilisateur � valider
   * @return Le compte utilisateur valid� sans ses relations
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
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
