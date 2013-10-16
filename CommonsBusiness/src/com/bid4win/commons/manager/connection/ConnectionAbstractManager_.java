package com.bid4win.commons.manager.connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.ConnectionRef;
import com.bid4win.commons.core.security.IdProcess;
import com.bid4win.commons.manager.Bid4WinManager_;
import com.bid4win.commons.persistence.dao.connection.ConnectionAbstractDao_;
import com.bid4win.commons.persistence.dao.connection.ConnectionHistoryAbstractDao_;
import com.bid4win.commons.persistence.dao.connection.PasswordReinitAbstractDao_;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.security.Login;
import com.bid4win.commons.persistence.entity.account.security.Password;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthenticationException;
import com.bid4win.commons.persistence.entity.account.security.exception.SessionException;
import com.bid4win.commons.persistence.entity.connection.ConnectionAbstract;
import com.bid4win.commons.persistence.entity.connection.ConnectionHistoryAbstract;
import com.bid4win.commons.persistence.entity.connection.DisconnectionReason;
import com.bid4win.commons.persistence.entity.connection.PasswordReinitAbstract;
import com.bid4win.commons.persistence.entity.contact.Email;
import com.bid4win.commons.service.connection.SessionDataAbstract;

/**
 * Manager de base du projet pour la gestion des connexions incluant leur gestion
 * métier. La validation d'une session doit être effectuée au niveau du serveur
 * afin de se prémunir de toute tentative de vol. Une session, et donc la potentielle
 * connexion correspondante sera alors liée à un serveur unique<BR>
 * <BR>
 * @param <CONNECTION> Définition du type de connexion utilisée par le projet<BR>
 * @param <HISTORY> Définition du type d'historique de connexion utilisée par le
 * projet<BR>
 * @param <REINIT> Définition du type de ré-initialisation de mot de passe de
 * connexion utilisée par le projet<BR>
 * @param <SESSION> Définition du type de conteneur de données d'une session utilisé
 * par le projet<BR>
 * @param <ACCOUNT> Définition de type de compte utilisateur utilisé par le projet<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class ConnectionAbstractManager_<CONNECTION extends ConnectionAbstract<CONNECTION, HISTORY, ACCOUNT>,
                                                 HISTORY extends ConnectionHistoryAbstract<HISTORY, ACCOUNT>,
                                                 REINIT extends PasswordReinitAbstract<REINIT, ACCOUNT>,
                                                 SESSION extends SessionDataAbstract<ACCOUNT, CONNECTION>,
                                                 ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends Bid4WinManager_<ACCOUNT, ConnectionAbstractManager_<CONNECTION, HISTORY, REINIT, SESSION, ACCOUNT>>
{
  /** Référence du DAO de gestion des connexions */
  @Autowired
  @Qualifier("ConnectionDao")
  private ConnectionAbstractDao_<CONNECTION, HISTORY, ACCOUNT> connectionDao;
  /** Référence du DAO de gestion des historiques de connexion */
  @Autowired
  @Qualifier("ConnectionHistoryDao")
  private ConnectionHistoryAbstractDao_<HISTORY, ACCOUNT> historyDao;
  /** Référence du DAO de gestion des historiques de connexion*/
  @Autowired
  @Qualifier("PasswordReinitDao")
  private PasswordReinitAbstractDao_<REINIT, ACCOUNT> passwordReinitDao;

  /**
   * Cette méthode permet d'identifier le compte utilisateur connecté
   * @param session Session contenant les information de connexion
   * @return La définition du compte utilisateur connecté
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws AuthenticationException Si aucun compte utilisateur n'est considéré
   * connecté
   */
  public CONNECTION authentify(SESSION session)
          throws PersistenceException, AuthenticationException
  {
    // Récupère la connexion liée à l'identifiant de session courante
    CONNECTION connection = this.getConnectionDao().findById(session.getSessionId());
    if(connection == null || !connection.getProcessId().equals(IdProcess.ID))
    {
      throw new AuthenticationException(ConnectionRef.SESSION_UNDEFINED_ERROR,
                                        DisconnectionReason.NONE);
    }
    else if(!connection.isActive())
    {
      throw new AuthenticationException(ConnectionRef.SESSION_INVALID_ERROR,
                                        connection.getData().getDisconnectionReason());
    }
    return connection;
  }

  /**
   * Cette méthode permet de connecter un compte utilisateur au système. On peut
   * y définir la rémanence de celle-ci, c'est à dire si l'appareil doit être
   * considéré comme perpétuellement connecté. Toute demande de ré-initialisation
   * de mot de passe en attente sera annulée. La connexion sera potentiellement
   * récupérée d'une connexion active si elles correspondent
   * @param session Session contenant les information de connexion
   * @param loginOrEmail Identifiant de connexion ou adresse email du compte utilisateur
   * à connecter
   * @param password Mot de passe de connexion du compte utilisateur à connecter
   * @param remanent Flag autorisant la rémanence de la connexion si à true
   * @return La connection effectuée
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si un problème empêche la création de la connexion
   * @throws SessionException Si une connexion existe déjà avec le même identifiant
   * de session mais est désactivée ou provient d'un process différent, la session
   * devrait alors être invalidée et la tentative de connexion réessayée
   * @throws AuthenticationException Si le compte utilisateur n'a pu être trouvé
   * ou ne correspond pas à celui connecté ou si le mot de passe n'est pas celui
   * associé au compte utilisateur
   */
  public CONNECTION connect(SESSION session, String loginOrEmail,
                            Password password, boolean remanent)
         throws PersistenceException, UserException,
                SessionException, AuthenticationException
  {

    // TODO vérifier si l'IP est bloquée

    // Recherche si la connexion est déjà activée
    CONNECTION connection = this.getConnectionDao().findById(session.getSessionId());
    if(connection == null)
    {
      // Aucune connexion n'est liée à la session courante, on en crée une nouvelle
      connection = this.startConnection(session, loginOrEmail, password, remanent);
    }
    else
    {
      // On valide l'utilisation de la connexion trouvée
      this.useConnection(connection, loginOrEmail, password);
    }

    // TODO bloquer l'ip si nécessaire

    // Annule toute demande de ré-initialisation de mot de passe
    REINIT reinit = this.getPasswordReinitDao().findOneByAccount(connection.getAccount());
    if(reinit != null)
    {
      this.getPasswordReinitDao().remove(reinit);
    }
    // Retourne la connection
    return connection;
  }
  /**
   * Cette fonction permet de valider et de créer la connexion demandée
   * @param session Session contenant les information de connexion
   * @param loginOrEmail Identifiant de connexion ou adresse email du compte utilisateur
   * à connecter
   * @param password Mot de passe de connexion du compte utilisateur à connecter
   * @param remanent Flag autorisant la rémanence de la connexion si à true
   * @return La nouvelle connexion
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si un problème empêche la création de la connexion
   * @throws AuthenticationException Si le compte utilisateur n'a pu être trouvé
   * ou si le mot de passe n'est pas celui associé au compte utilisateur
   */
  private CONNECTION startConnection(SESSION session, String loginOrEmail,
                                     Password password, boolean remanent)
          throws PersistenceException, UserException, AuthenticationException
  {
    // Récupère le compte utilisateur en fonction de son identifiant de connexion
    // ou de son email
    ACCOUNT account = this.getAccountManager().getAccount(loginOrEmail);
    // Valide le mot de passe
    if(!account.getCredential().getPassword().equals(password))
    {
      throw new AuthenticationException(ConnectionRef.PASSWORD_WRONG_ERROR,
                                        DisconnectionReason.NONE);
    }
    // Crée et démarre la connexion
    return this.getConnectionDao().add(
        this.createConnectionEntity(session, account, remanent));
  }
  /**
   * Cette fonction permet de valider l'utilisation d'une connexion existante
   * @param connection Connexion dont l'utilisation doit être validée
   * @param loginOrEmail Identifiant de connexion ou adresse email du compte utilisateur
   * à valider
   * @param password Mot de passe de connexion du compte utilisateur à valider
   * @throws SessionException Si la connexion est désactivée ou provient d'un process
   * différent
   * @throws AuthenticationException Si la connexion existant ne correspond pas
   * à la connexion demandée
   */
  private void useConnection(CONNECTION connection, String loginOrEmail, Password password)
          throws SessionException, AuthenticationException
  {
    // Une ancienne connexion ou une connexion d'un autre process utilise le même
    // identifiant de session, il faut utiliser une nouvelle session
    if(!connection.isActive() || !connection.getProcessId().equals(IdProcess.ID))
    {
      throw new SessionException(ConnectionRef.SESSION_INVALID_ERROR);
    }
    // Si la connexion demandée ne correspond pas à la connexion existante, une
    // tentative de vol de connexion pourrait intervenir
    boolean loginOrEmailValid = false;
    ACCOUNT account = connection.getAccount();
    try
    {
      if(account.getCredential().getLogin().equals(new Login(loginOrEmail)))
      {
        loginOrEmailValid = true;
      }
    }
    catch(UserException ex1)
    {
      //
    }
    if(!loginOrEmailValid)
    {
      try
      {
        if(account.getEmail().equals(new Email(loginOrEmail)))
        {
          loginOrEmailValid = true;
        }
      }
      catch(UserException ex2)
      {
        //
      }
    }
    if(!loginOrEmailValid)
    {
      throw new AuthenticationException(ConnectionRef.LOGIN_OR_EMAIL_UNKNOWN_ERROR,
                                        DisconnectionReason.NONE);
    }
    if(!account.getCredential().getPassword().equals(password))
    {
      throw new AuthenticationException(ConnectionRef.PASSWORD_WRONG_ERROR,
                                        DisconnectionReason.NONE);
    }
  }

  /**
   * Cette méthode permet de re-connecter un compte utilisateur au système à partir
   * d'un appareil sur lequel la rémanence d'une connexion a été activée
   * @param session Session contenant les information de connexion
   * @param fingerPrint Empreinte unique de la dernière rémanence de connexion
   * @param accountId Identifiant du compte utilisateur à re-connecter
   * @return La connection effectuée
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si un problème empêche la création de la connexion
   * @throws SessionException Si l'identifiant de session ne peut pas être réutilisé.
   * La session devrait alors être invalidée et la tentative de connexion réessayée
   * @throws AuthenticationException Si la connexion a été invalidé, n'existe pas
   * ou ne correspond pas au compte utilisateur demandé.
   * Attention la transaction ne doit pas être annulée dans ce cas !!!
   */
  public CONNECTION reconnect(SESSION session, String fingerPrint, String accountId)
         throws PersistenceException, UserException,
                SessionException, AuthenticationException
  {
    // TODO vérifier si l'IP est bloqué

    // Recherche la connexion demandée
    CONNECTION connection = this.getConnectionDao().findById(fingerPrint);
    // On essaye de réutiliser la connexion
    this.reuseConnection(session, connection, fingerPrint, accountId);
    // TODO bloquer l'IP si nécessaire

    // On valide la réutilisation de l'identifiant de session
    if(session.getSessionId().equals(fingerPrint) && connection.isActive())
    {
      // On ne peut pas réutiliser cette identifiant de session
      if(!connection.getProcessId().equals(IdProcess.ID))
      {
        throw new SessionException(ConnectionRef.SESSION_DEFINED_ERROR);
      }
      // On réutilise directement la connection courante
      return connection;
    }
    // On utilise la rémanence en invalidant la dernière
    if(!connection.isActive())
    {
      this.getConnectionDao().update(connection.stopConnection(DisconnectionReason.NONE));
    }
    // Crée et démarre la connexion
    return this.getConnectionDao().add(
        this.createConnectionEntity(session, connection.getAccount(), true));
  }
  /**
   * Cette fonction permet de valider la ré-utilisation d'une connexion rémanente
   * @param session Session contenant les information de connexion
   * @param connection Connexion dont la réutilisation doit être validée
   * @param fingerPrint Empreinte unique utilisée pour recherche la connexion
   * @param accountId Identifiant du compte utilisateur à re-connecter
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws AuthenticationException Si la connexion n'est pas rémanente ou a été
   * invalidée, n'existe pas ou ne correspond pas au compte utilisateur demandé
   */
  private void reuseConnection(SESSION session, CONNECTION connection,
                               String fingerPrint, String accountId)
          throws PersistenceException, AuthenticationException
  {
    // Si la connexion est inexistante, aucune re-connexion n'est possible
    if(connection == null)
    {
      // On recherche parmi les historiques de re-connexion si elle avait existé
      // à un moment donné
      for(HISTORY history : this.getHistoryDao().findListBySessionId(fingerPrint))
      {
        // Une connexion rémanente avait bien existé pour le compte utilisateur
        // avec cette empreinte mais a été invalidé. On peut être en présence d'
        // une tentative de vol de connexion. La première fois, on invalide toutes
        // les connexions de cet utilisateur pour sécuriser son compte et lui permettre
        // de reprendre la main
        if(history.getData().isRemanent() && history.getAccount().getId().equals(accountId) &&
           (!history.getData().getDisconnectionReason().equals(DisconnectionReason.REMANENCE) &&
            !history.getData().getDisconnectionReason().equals(DisconnectionReason.PASSWORD)))
        {
          for(CONNECTION toUnvalidate : this.getConnectionDao().findListByAccountId(accountId))
          {
            this.getConnectionDao().update(toUnvalidate.stopConnection(DisconnectionReason.REMANENCE));
          }
          throw new AuthenticationException(ConnectionRef.SESSION_UNDEFINED_ERROR,
                                            DisconnectionReason.REMANENCE);
        }
      }
      // La connexion n'a jamais existé ou a déjà été invalidée suite à une tentative
      // de re-connexion en échec ou de modification de mot de passe
      throw new AuthenticationException(ConnectionRef.SESSION_UNDEFINED_ERROR,
                                        DisconnectionReason.NONE);
    }
    // La rémanence n'a pas été activée sur cette connexion
    if(!connection.getData().isRemanent())
    {
      // On parle bien d'une nouvelle connexion et non d'un rattachement à une
      // connexion existante
      if(!session.getSessionId().equals(fingerPrint))
      {
        throw new AuthenticationException(ConnectionRef.SESSION_UNDEFINED_ERROR,
                                          DisconnectionReason.NONE);
      }
    }
    // La connexion n'est pas liée au compte utilisateur demandé
    if(!connection.getAccount().getId().equals(accountId))
    {
      throw new AuthenticationException(ConnectionRef.SESSION_UNDEFINED_ERROR,
                                        DisconnectionReason.NONE);
    }
  }

  /**
   * Cette méthode permet de terminer la connexion d'un compte utilisateur au système
   * tout en gardant sa rémanence si celle-ci avait été activée
   * @param session Session contenant les information de connexion
   * @return TODO A COMMENTER
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  public boolean endConnection(SESSION session) throws PersistenceException
  {
    // Recherche la connexion demandée
    CONNECTION connection = this.getConnectionDao().findById(session.getSessionId());
    // On peut bien stopper la connexion
    if(connection != null && connection.getProcessId().equals(IdProcess.ID))
    {
      connection.endConnection(DisconnectionReason.AUTO);
      this.getConnectionDao().update(connection);
      return true;
    }
    return false;
  }
  /**
   * Cette méthode permet de déconnecter un compte utilisateur du système et d'
   * arrêter la rémanence de sa connexion si celle-ci avait été activée
   * @param session Session contenant les information de connexion
   * @return TODO A COMMENTER
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  public boolean disconnect(SESSION session) throws PersistenceException
  {
    // Recherche la connexion demandée
    CONNECTION connection = this.getConnectionDao().findById(session.getSessionId());
    // On peut bien stopper la connexion
    if(connection != null && connection.getProcessId().equals(IdProcess.ID))
    {
      connection.stopConnection(DisconnectionReason.MANUAL);
      this.getConnectionDao().update(connection);
      return true;
    }
    return false;
  }

  /**
   * Cette méthode permet de modifier le mot de passe de connexion d'un compte
   * utilisateur en prenant en charge la suppression de la rémanence de toutes
   * les connexions le concernant exceptée celle utilisée pour la modification
   * ainsi que toute demande de ré-initialisation de mot de passe potentiellement
   * existante
   * @param session TODO A COMMENTER
   * @param accountId Identifiant du compte utilisateur à modifier
   * @param oldPassword Ancien mot de passe de connexion du compte utilisateur
   * @param newPassword Nouveau mot de passe de connexion du compte utilisateur
   * @return Le compte utilisateur modifié
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si le compte utilisateur n'a pu être trouvé
   * @throws UserException Si l'ancien mot de passe de connexion en argument ne
   * correspond pas à celui du compte utilisateur
   * @throws SessionException Si aucune session n'est définie
   */
  public ACCOUNT updatePassword(SESSION session, String accountId,
                                Password oldPassword, Password newPassword)
         throws PersistenceException, NotFoundEntityException, UserException, SessionException
  {
    // Met à jour le mot de passe de connexion
    ACCOUNT account = this.getAccountManager().updatePassword(accountId,
                                                              oldPassword,
                                                              newPassword);
    // Invalide les autres connexions !!!
    for(CONNECTION connection : this.getConnectionDao().findListByAccount(account))
    {
      if(!connection.getFingerPrint().equals(session.getSessionId()))
      {
        connection.stopConnection(DisconnectionReason.PASSWORD);
        this.getConnectionDao().update(connection);
      }
    }
    // Supprime toute demande de ré-initialisation de mot de passe potentiellement
    // existante
    REINIT reinit = this.getPasswordReinitDao().findOneByAccount(account);
    if(reinit != null)
    {
      this.getPasswordReinitDao().remove(reinit);
    }
    // Renvoie le compte utilisateur modifié
    return account;
  }
  /**
   * Cette méthode permet de demander la ré-initialisation du mot de passe de connexion
   * d'un compte utilisateur
   * @param loginOrEmail Identifiant de connexion ou adresse email du compte utilisateur
   * dont on demande la ré-initialisation de mot de passe de connexion
   * @return La référence de la demande de ré-initialisation de mot de passe qu'il
   * faudra utiliser
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si le compte utilisateur n'a pu être trouvé
   */
  public REINIT reinitPassword(String loginOrEmail)
         throws PersistenceException, UserException
  {
    // Récupère le compte utilisateur en fonction de son identifiant de connexion
    // ou de son email
    ACCOUNT account = this.getAccountManager().getAccount(loginOrEmail);
    // Récupère la ré-initialisation potentiellement déjà existante
    REINIT reinit = this.getPasswordReinitDao().findOneByAccount(account);
    // Aucune ré-initialisation n'est en cours, il faut en créer une
    if(reinit == null)
    {
      reinit = this.getPasswordReinitDao().add(this.createReinitEntity(account));
    }
    // Retourne la ré-initialisation de mot de passe demandée
    return reinit;
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
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucune référence de demande de ré-initialisation
   * de mot de passe n'a pu être récupérée avec la clé et l'identifiant de compte
   * utilisateur en argument
   */
  @SuppressWarnings("unchecked")
  public REINIT getPasswordReinit(String reinitKey, String accountId)
         throws PersistenceException, NotFoundEntityException
  {
    // TODO bloquer l'ip si nécessaire
    REINIT reinit = this.getPasswordReinitDao().getById(reinitKey);
    if(!reinit.getAccount().getId().equals(accountId))
    {
      throw new NotFoundEntityException(PasswordReinitAbstract.class);
    }
    return reinit;
  }
  /**
   * Cette méthode permet d'effectuer la ré-initialisation du mot de passe de connexion
   * d'un compte utilisateur en prenant en charge la suppression de la rémanence
   * de toutes les connexions le concernant. Attention, le compte utilisateur ne
   * sera pas considéré automatiquement connecté au système
   * @param session TODO A COMMENTER
   * @param reinitKey Clé de la référence de la demande de ré-initialisation de
   * mot de passe
   * @param accountId Identifiant du compte utilisateur associé à la demande de
   * ré-initialisation de mot de passe
   * @param password Nouveau mot de passe de connexion du compte utilisateur associé
   * à la demande de ré-initialisation
   * @return Le compte utilisateur modifié
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucune référence de demande de ré-initialisation
   * de mot de passe n'a pu être récupérée avec la clé et l'identifiant de compte
   * utilisateur en argument
   * @throws ModelArgumentException Si les paramètres sont invalides
   */
  public ACCOUNT reinitPassword(SESSION session, String reinitKey,
                                String accountId, Password password)
         throws PersistenceException, NotFoundEntityException, ModelArgumentException
  {
 // TODO bloquer l'ip si nécessaire
    // Récupère la ré-initialisation préalablement demandée
    REINIT reinit = this.getPasswordReinit(reinitKey, accountId);
    // Récupère le compte utilisateur correspondant
    ACCOUNT account = reinit.getAccount();
    // Opère la ré-initialisation du mot de passe (La suppression de la demande
    // de ré-initialisation est automatique à la modification du mot de passe)
    try
    {
      account = this.updatePassword(session, account.getId(),
                                    account.getCredential().getPassword(), password);
    }
    catch(UserException ex)
    {
      throw new ModelArgumentException(ex);
    }
    // Retourne le compte utilisateur
    return account;
  }

  /**
   * Cette méthode doit permettre de créer la nouvelle connexion à utiliser
   * @param session TODO A COMMENTER
   * @param account Compte utilisateur de la connexion à créer
   * @param remanent TODO A COMMENTER
   * @return La connexion créée
   * @throws UserException Si les paramètres sont invalides
   */
  protected abstract CONNECTION createConnectionEntity(SESSION session,
                                                       ACCOUNT account,
                                                       boolean remanent)
            throws UserException;
  /**
   * Cette méthode doit permettre de créer la ré-initialisation de mot de passe
   * de connexion à utiliser
   * @param account Compte utilisateur de la ré-initialisation de mot de passe de
   * connexion
   * @return La ré-initialisation de mot de passe de connexion créée
   * @throws UserException Si les paramètres sont invalides
   */
  protected abstract REINIT createReinitEntity(ACCOUNT account) throws UserException;

  /**
   * Getter de la référence du DAO des connexions
   * @return La référence du DAO des connexions
   */
  protected ConnectionAbstractDao_<CONNECTION, HISTORY, ACCOUNT> getConnectionDao()
  {
    return this.connectionDao;
  }
  /**
   * Getter de la référence du DAO des historiques de connexion
   * @return La référence du DAO des historiques de connexion
   */
  protected ConnectionHistoryAbstractDao_<HISTORY, ACCOUNT> getHistoryDao()
  {
    return this.historyDao;
  }
  /**
   * Getter de la référence du DAO de la ré-initialisation des mots de passe
   * @return La référence du DAO de la ré-initialisation des mots de passe
   */
  protected PasswordReinitAbstractDao_<REINIT, ACCOUNT> getPasswordReinitDao()
  {
    return this.passwordReinitDao;
  }
}
