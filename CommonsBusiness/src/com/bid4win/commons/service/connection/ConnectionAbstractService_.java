package com.bid4win.commons.service.connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.manager.connection.ConnectionAbstractManager_;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.security.Password;
import com.bid4win.commons.persistence.entity.account.security.Role;
import com.bid4win.commons.persistence.entity.account.security.UtilSecurity;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthenticationException;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthorizationException;
import com.bid4win.commons.persistence.entity.account.security.exception.SessionException;
import com.bid4win.commons.persistence.entity.connection.ConnectionAbstract;
import com.bid4win.commons.persistence.entity.connection.ConnectionHistoryAbstract;
import com.bid4win.commons.persistence.entity.connection.PasswordReinitAbstract;
import com.bid4win.commons.service.Bid4WinService_;

/**
 * Service  de base du projet pour la gestion des connexions incluant la gestion
 * des transactions ainsi que celle des habilitations<BR>
 * <BR>
 * @param <CONNECTION> Définition du type de connexion utilisée par le projet<BR>
 * @param <HISTORY> Définition du type d'historique de connexion utilisée par le
 * projet<BR>
 * @param <REINIT> Définition du type de ré-initialisation de mot de passe de
 * connexion utilisée par le projet<BR>
 * @param <SESSION> Définition du type de conteneur de données d'une session utilisé
 * par le projet<BR>
 * @param <ACCOUNT> Définition de type de compte utilisateur utilisé par le projet<BR>
 * @param <SERVICE> Définition du service implémenté afin de pouvoir s'autoréférencer<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class ConnectionAbstractService_<CONNECTION extends ConnectionAbstract<CONNECTION, HISTORY, ACCOUNT>,
                                        HISTORY extends ConnectionHistoryAbstract<HISTORY, ACCOUNT>,
                                        REINIT extends PasswordReinitAbstract<REINIT, ACCOUNT>,
                                        SESSION extends SessionDataAbstract<ACCOUNT>,
                                        ACCOUNT extends AccountAbstract<ACCOUNT>,
                                        SERVICE extends ConnectionAbstractService_<CONNECTION, HISTORY, REINIT, SESSION, ACCOUNT, SERVICE>>
       extends Bid4WinService_<SESSION, ACCOUNT, SERVICE>
{
  /** Référence du gestionnaire des données de session */
  @Autowired
  @Qualifier("SessionHandler")
  private SessionHandlerAbstract<SESSION, ACCOUNT> sessionHandler;
  /** Manager de gestion des connexions */
  @Autowired
  @Qualifier("ConnectionManager")
  private ConnectionAbstractManager_<CONNECTION, HISTORY, REINIT, SESSION, ACCOUNT> manager;

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
  @Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
  public ACCOUNT getConnectedAccount()
         throws PersistenceException, SessionException,
                AuthenticationException, AuthorizationException
  {
    ACCOUNT account = this.getSessionHandler().getConnectedAccount();
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
  private void connectAccount(ACCOUNT account)
          throws SessionException, AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation minimum de l'utilisateur
    UtilSecurity.checkRole(account, Role.BASIC);
    this.getSessionHandler().connect(account);
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
  private ACCOUNT authentify()
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
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public CONNECTION connect(String loginOrEmail, Password password, boolean remanent)
         throws PersistenceException, UserException, SessionException,
                AuthenticationException, AuthorizationException
  {
    // Connecte l'utilisateur
    CONNECTION connection = this.getManager().connect(
        this.getSession(), loginOrEmail, password, remanent);
    // Défini le compte utilisateur connecté sur la session courante
    this.connectAccount(connection.getAccount());
    return connection.loadRelation();
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
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class},
                                   noRollbackFor = {AuthenticationException.class})
  public CONNECTION reconnect(String fingerPrint, String accountId)
         throws PersistenceException, UserException, SessionException,
                AuthenticationException, AuthorizationException
  {
    // Reconnecte l'utilisateur
    CONNECTION connection = this.getManager().reconnect(
        this.getSession(), fingerPrint, accountId);
    // Défini le compte utilisateur connecté sur la session courante
    this.connectAccount(connection.getAccount());
    return connection.loadRelation();
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
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public void endConnection() throws PersistenceException, SessionException
  {
    // Termine la connexion de l'utilisateur
    if(this.getManager().endConnection(this.getSession()))
    {
      this.disconnectAccount();
    }
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
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public void disconnect() throws PersistenceException, SessionException
  {
    // Déconnecte l'utilisateur
    if(this.getManager().disconnect(this.getSession()))
    {
      this.disconnectAccount();
    }
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
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public ACCOUNT updatePassword(Password oldPassword, Password newPassword)
         throws PersistenceException, NotFoundEntityException, UserException, SessionException
  {
    return this.getManager().updatePassword(this.getSession(),
                                            this.getConnectedAccount().getId(),
                                            oldPassword, newPassword);
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
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public REINIT reinitPassword(String loginOrEmail) throws PersistenceException, UserException
  {
    return this.getManager().reinitPassword(loginOrEmail);
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
  @Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
  public REINIT getPasswordReinit(String reinitKey, String accountId)
         throws PersistenceException, NotFoundEntityException
  {
    return this.getManager().getPasswordReinit(reinitKey, accountId).loadRelation();
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
  @Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
  public ACCOUNT reinitPassword(String reinitKey, String accountId, Password password)
         throws PersistenceException, NotFoundEntityException,
                ModelArgumentException, SessionException
  {
    return this.getManager().reinitPassword(this.getSession(), reinitKey,
                                            accountId, password);
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinService_#getAdminRole()
   */
  @Override
  public Role getAdminRole()
  {
    return Role.ADMIN;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws SessionException TODO A COMMENTER
   */
  public SESSION getSession() throws SessionException
  {
    return this.getSessionHandler().getSessionData();
  }

  /**
   * Getter du gestionnaire des sessions
   * @return Le gestionnaire des sessions
   */
  public SessionHandlerAbstract<SESSION, ACCOUNT> getSessionHandler()
  {
    return this.sessionHandler;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinService_#getManager()
   */
  @Override
  protected ConnectionAbstractManager_<CONNECTION, HISTORY, REINIT, SESSION, ACCOUNT> getManager()
  {
    return this.manager;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinService_#getConnectionService()
   */
  @Override
  protected SERVICE getConnectionService()
  {
    return this.self();
  }
  /**
   *
   * TODO A COMMENTER
   * @param connectionService {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinService_#setConnectionService(com.bid4win.commons.service.connection.ConnectionAbstractService_)
   */
  @Override
  protected void setConnectionService(ConnectionAbstractService_<?, ?, ?, SESSION, ACCOUNT, ?> connectionService)
  {
    // La méthode est redéfinie pour être débrayée
  }
}
