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
 * @param <CONNECTION> D�finition du type de connexion utilis�e par le projet<BR>
 * @param <HISTORY> D�finition du type d'historique de connexion utilis�e par le
 * projet<BR>
 * @param <REINIT> D�finition du type de r�-initialisation de mot de passe de
 * connexion utilis�e par le projet<BR>
 * @param <SESSION> D�finition du type de conteneur de donn�es d'une session utilis�
 * par le projet<BR>
 * @param <ACCOUNT> D�finition de type de compte utilisateur utilis� par le projet<BR>
 * @param <SERVICE> D�finition du service impl�ment� afin de pouvoir s'autor�f�rencer<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class ConnectionAbstractService_<CONNECTION extends ConnectionAbstract<CONNECTION, HISTORY, ACCOUNT>,
                                        HISTORY extends ConnectionHistoryAbstract<HISTORY, ACCOUNT>,
                                        REINIT extends PasswordReinitAbstract<REINIT, ACCOUNT>,
                                        SESSION extends SessionDataAbstract<ACCOUNT>,
                                        ACCOUNT extends AccountAbstract<ACCOUNT>,
                                        SERVICE extends ConnectionAbstractService_<CONNECTION, HISTORY, REINIT, SESSION, ACCOUNT, SERVICE>>
       extends Bid4WinService_<SESSION, ACCOUNT, SERVICE>
{
  /** R�f�rence du gestionnaire des donn�es de session */
  @Autowired
  @Qualifier("SessionHandler")
  private SessionHandlerAbstract<SESSION, ACCOUNT> sessionHandler;
  /** Manager de gestion des connexions */
  @Autowired
  @Qualifier("ConnectionManager")
  private ConnectionAbstractManager_<CONNECTION, HISTORY, REINIT, SESSION, ACCOUNT> manager;

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
   * Cette m�thode permet de d�finir le compte utilisateur connect� sur la session
   * courante
   * @param account Compte utilisateur connect� sur la session courante
   * @throws SessionException Si aucune session n'est d�finie
   * @throws AuthorizationException Si le compte utilisateur n'a pas les habilitations
   * suffisantes pour �tre consid�r� connect�
   */
  private void connectAccount(ACCOUNT account)
          throws SessionException, AuthenticationException, AuthorizationException
  {
    // @ V�rifie le niveau d'habilitation minimum de l'utilisateur
    UtilSecurity.checkRole(account, Role.BASIC);
    this.getSessionHandler().connect(account);
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
  private ACCOUNT authentify()
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
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public CONNECTION connect(String loginOrEmail, Password password, boolean remanent)
         throws PersistenceException, UserException, SessionException,
                AuthenticationException, AuthorizationException
  {
    // Connecte l'utilisateur
    CONNECTION connection = this.getManager().connect(
        this.getSession(), loginOrEmail, password, remanent);
    // D�fini le compte utilisateur connect� sur la session courante
    this.connectAccount(connection.getAccount());
    return connection.loadRelation();
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
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class},
                                   noRollbackFor = {AuthenticationException.class})
  public CONNECTION reconnect(String fingerPrint, String accountId)
         throws PersistenceException, UserException, SessionException,
                AuthenticationException, AuthorizationException
  {
    // Reconnecte l'utilisateur
    CONNECTION connection = this.getManager().reconnect(
        this.getSession(), fingerPrint, accountId);
    // D�fini le compte utilisateur connect� sur la session courante
    this.connectAccount(connection.getAccount());
    return connection.loadRelation();
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
   * Cette m�thode permet de d�connecter un compte utilisateur du syst�me et d'
   * arr�ter la r�manence de sa connexion si celle-ci avait �t� activ�e
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws SessionException Si aucune session n'est d�finie
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public void disconnect() throws PersistenceException, SessionException
  {
    // D�connecte l'utilisateur
    if(this.getManager().disconnect(this.getSession()))
    {
      this.disconnectAccount();
    }
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
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public ACCOUNT updatePassword(Password oldPassword, Password newPassword)
         throws PersistenceException, NotFoundEntityException, UserException, SessionException
  {
    return this.getManager().updatePassword(this.getSession(),
                                            this.getConnectedAccount().getId(),
                                            oldPassword, newPassword);
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
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public REINIT reinitPassword(String loginOrEmail) throws PersistenceException, UserException
  {
    return this.getManager().reinitPassword(loginOrEmail);
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
  @Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
  public REINIT getPasswordReinit(String reinitKey, String accountId)
         throws PersistenceException, NotFoundEntityException
  {
    return this.getManager().getPasswordReinit(reinitKey, accountId).loadRelation();
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
    // La m�thode est red�finie pour �tre d�bray�e
  }
}
