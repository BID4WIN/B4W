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
 * m�tier. La validation d'une session doit �tre effectu�e au niveau du serveur
 * afin de se pr�munir de toute tentative de vol. Une session, et donc la potentielle
 * connexion correspondante sera alors li�e � un serveur unique<BR>
 * <BR>
 * @param <CONNECTION> D�finition du type de connexion utilis�e par le projet<BR>
 * @param <HISTORY> D�finition du type d'historique de connexion utilis�e par le
 * projet<BR>
 * @param <REINIT> D�finition du type de r�-initialisation de mot de passe de
 * connexion utilis�e par le projet<BR>
 * @param <SESSION> D�finition du type de conteneur de donn�es d'une session utilis�
 * par le projet<BR>
 * @param <ACCOUNT> D�finition de type de compte utilisateur utilis� par le projet<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class ConnectionAbstractManager_<CONNECTION extends ConnectionAbstract<CONNECTION, HISTORY, ACCOUNT>,
                                                 HISTORY extends ConnectionHistoryAbstract<HISTORY, ACCOUNT>,
                                                 REINIT extends PasswordReinitAbstract<REINIT, ACCOUNT>,
                                                 SESSION extends SessionDataAbstract<ACCOUNT, CONNECTION>,
                                                 ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends Bid4WinManager_<ACCOUNT, ConnectionAbstractManager_<CONNECTION, HISTORY, REINIT, SESSION, ACCOUNT>>
{
  /** R�f�rence du DAO de gestion des connexions */
  @Autowired
  @Qualifier("ConnectionDao")
  private ConnectionAbstractDao_<CONNECTION, HISTORY, ACCOUNT> connectionDao;
  /** R�f�rence du DAO de gestion des historiques de connexion */
  @Autowired
  @Qualifier("ConnectionHistoryDao")
  private ConnectionHistoryAbstractDao_<HISTORY, ACCOUNT> historyDao;
  /** R�f�rence du DAO de gestion des historiques de connexion*/
  @Autowired
  @Qualifier("PasswordReinitDao")
  private PasswordReinitAbstractDao_<REINIT, ACCOUNT> passwordReinitDao;

  /**
   * Cette m�thode permet d'identifier le compte utilisateur connect�
   * @param session Session contenant les information de connexion
   * @return La d�finition du compte utilisateur connect�
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws AuthenticationException Si aucun compte utilisateur n'est consid�r�
   * connect�
   */
  public CONNECTION authentify(SESSION session)
          throws PersistenceException, AuthenticationException
  {
    // R�cup�re la connexion li�e � l'identifiant de session courante
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
   * Cette m�thode permet de connecter un compte utilisateur au syst�me. On peut
   * y d�finir la r�manence de celle-ci, c'est � dire si l'appareil doit �tre
   * consid�r� comme perp�tuellement connect�. Toute demande de r�-initialisation
   * de mot de passe en attente sera annul�e. La connexion sera potentiellement
   * r�cup�r�e d'une connexion active si elles correspondent
   * @param session Session contenant les information de connexion
   * @param loginOrEmail Identifiant de connexion ou adresse email du compte utilisateur
   * � connecter
   * @param password Mot de passe de connexion du compte utilisateur � connecter
   * @param remanent Flag autorisant la r�manence de la connexion si � true
   * @return La connection effectu�e
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si un probl�me emp�che la cr�ation de la connexion
   * @throws SessionException Si une connexion existe d�j� avec le m�me identifiant
   * de session mais est d�sactiv�e ou provient d'un process diff�rent, la session
   * devrait alors �tre invalid�e et la tentative de connexion r�essay�e
   * @throws AuthenticationException Si le compte utilisateur n'a pu �tre trouv�
   * ou ne correspond pas � celui connect� ou si le mot de passe n'est pas celui
   * associ� au compte utilisateur
   */
  public CONNECTION connect(SESSION session, String loginOrEmail,
                            Password password, boolean remanent)
         throws PersistenceException, UserException,
                SessionException, AuthenticationException
  {

    // TODO v�rifier si l'IP est bloqu�e

    // Recherche si la connexion est d�j� activ�e
    CONNECTION connection = this.getConnectionDao().findById(session.getSessionId());
    if(connection == null)
    {
      // Aucune connexion n'est li�e � la session courante, on en cr�e une nouvelle
      connection = this.startConnection(session, loginOrEmail, password, remanent);
    }
    else
    {
      // On valide l'utilisation de la connexion trouv�e
      this.useConnection(connection, loginOrEmail, password);
    }

    // TODO bloquer l'ip si n�cessaire

    // Annule toute demande de r�-initialisation de mot de passe
    REINIT reinit = this.getPasswordReinitDao().findOneByAccount(connection.getAccount());
    if(reinit != null)
    {
      this.getPasswordReinitDao().remove(reinit);
    }
    // Retourne la connection
    return connection;
  }
  /**
   * Cette fonction permet de valider et de cr�er la connexion demand�e
   * @param session Session contenant les information de connexion
   * @param loginOrEmail Identifiant de connexion ou adresse email du compte utilisateur
   * � connecter
   * @param password Mot de passe de connexion du compte utilisateur � connecter
   * @param remanent Flag autorisant la r�manence de la connexion si � true
   * @return La nouvelle connexion
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si un probl�me emp�che la cr�ation de la connexion
   * @throws AuthenticationException Si le compte utilisateur n'a pu �tre trouv�
   * ou si le mot de passe n'est pas celui associ� au compte utilisateur
   */
  private CONNECTION startConnection(SESSION session, String loginOrEmail,
                                     Password password, boolean remanent)
          throws PersistenceException, UserException, AuthenticationException
  {
    // R�cup�re le compte utilisateur en fonction de son identifiant de connexion
    // ou de son email
    ACCOUNT account = this.getAccountManager().getAccount(loginOrEmail);
    // Valide le mot de passe
    if(!account.getCredential().getPassword().equals(password))
    {
      throw new AuthenticationException(ConnectionRef.PASSWORD_WRONG_ERROR,
                                        DisconnectionReason.NONE);
    }
    // Cr�e et d�marre la connexion
    return this.getConnectionDao().add(
        this.createConnectionEntity(session, account, remanent));
  }
  /**
   * Cette fonction permet de valider l'utilisation d'une connexion existante
   * @param connection Connexion dont l'utilisation doit �tre valid�e
   * @param loginOrEmail Identifiant de connexion ou adresse email du compte utilisateur
   * � valider
   * @param password Mot de passe de connexion du compte utilisateur � valider
   * @throws SessionException Si la connexion est d�sactiv�e ou provient d'un process
   * diff�rent
   * @throws AuthenticationException Si la connexion existant ne correspond pas
   * � la connexion demand�e
   */
  private void useConnection(CONNECTION connection, String loginOrEmail, Password password)
          throws SessionException, AuthenticationException
  {
    // Une ancienne connexion ou une connexion d'un autre process utilise le m�me
    // identifiant de session, il faut utiliser une nouvelle session
    if(!connection.isActive() || !connection.getProcessId().equals(IdProcess.ID))
    {
      throw new SessionException(ConnectionRef.SESSION_INVALID_ERROR);
    }
    // Si la connexion demand�e ne correspond pas � la connexion existante, une
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
   * Cette m�thode permet de re-connecter un compte utilisateur au syst�me � partir
   * d'un appareil sur lequel la r�manence d'une connexion a �t� activ�e
   * @param session Session contenant les information de connexion
   * @param fingerPrint Empreinte unique de la derni�re r�manence de connexion
   * @param accountId Identifiant du compte utilisateur � re-connecter
   * @return La connection effectu�e
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si un probl�me emp�che la cr�ation de la connexion
   * @throws SessionException Si l'identifiant de session ne peut pas �tre r�utilis�.
   * La session devrait alors �tre invalid�e et la tentative de connexion r�essay�e
   * @throws AuthenticationException Si la connexion a �t� invalid�, n'existe pas
   * ou ne correspond pas au compte utilisateur demand�.
   * Attention la transaction ne doit pas �tre annul�e dans ce cas !!!
   */
  public CONNECTION reconnect(SESSION session, String fingerPrint, String accountId)
         throws PersistenceException, UserException,
                SessionException, AuthenticationException
  {
    // TODO v�rifier si l'IP est bloqu�

    // Recherche la connexion demand�e
    CONNECTION connection = this.getConnectionDao().findById(fingerPrint);
    // On essaye de r�utiliser la connexion
    this.reuseConnection(session, connection, fingerPrint, accountId);
    // TODO bloquer l'IP si n�cessaire

    // On valide la r�utilisation de l'identifiant de session
    if(session.getSessionId().equals(fingerPrint) && connection.isActive())
    {
      // On ne peut pas r�utiliser cette identifiant de session
      if(!connection.getProcessId().equals(IdProcess.ID))
      {
        throw new SessionException(ConnectionRef.SESSION_DEFINED_ERROR);
      }
      // On r�utilise directement la connection courante
      return connection;
    }
    // On utilise la r�manence en invalidant la derni�re
    if(!connection.isActive())
    {
      this.getConnectionDao().update(connection.stopConnection(DisconnectionReason.NONE));
    }
    // Cr�e et d�marre la connexion
    return this.getConnectionDao().add(
        this.createConnectionEntity(session, connection.getAccount(), true));
  }
  /**
   * Cette fonction permet de valider la r�-utilisation d'une connexion r�manente
   * @param session Session contenant les information de connexion
   * @param connection Connexion dont la r�utilisation doit �tre valid�e
   * @param fingerPrint Empreinte unique utilis�e pour recherche la connexion
   * @param accountId Identifiant du compte utilisateur � re-connecter
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws AuthenticationException Si la connexion n'est pas r�manente ou a �t�
   * invalid�e, n'existe pas ou ne correspond pas au compte utilisateur demand�
   */
  private void reuseConnection(SESSION session, CONNECTION connection,
                               String fingerPrint, String accountId)
          throws PersistenceException, AuthenticationException
  {
    // Si la connexion est inexistante, aucune re-connexion n'est possible
    if(connection == null)
    {
      // On recherche parmi les historiques de re-connexion si elle avait exist�
      // � un moment donn�
      for(HISTORY history : this.getHistoryDao().findListBySessionId(fingerPrint))
      {
        // Une connexion r�manente avait bien exist� pour le compte utilisateur
        // avec cette empreinte mais a �t� invalid�. On peut �tre en pr�sence d'
        // une tentative de vol de connexion. La premi�re fois, on invalide toutes
        // les connexions de cet utilisateur pour s�curiser son compte et lui permettre
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
      // La connexion n'a jamais exist� ou a d�j� �t� invalid�e suite � une tentative
      // de re-connexion en �chec ou de modification de mot de passe
      throw new AuthenticationException(ConnectionRef.SESSION_UNDEFINED_ERROR,
                                        DisconnectionReason.NONE);
    }
    // La r�manence n'a pas �t� activ�e sur cette connexion
    if(!connection.getData().isRemanent())
    {
      // On parle bien d'une nouvelle connexion et non d'un rattachement � une
      // connexion existante
      if(!session.getSessionId().equals(fingerPrint))
      {
        throw new AuthenticationException(ConnectionRef.SESSION_UNDEFINED_ERROR,
                                          DisconnectionReason.NONE);
      }
    }
    // La connexion n'est pas li�e au compte utilisateur demand�
    if(!connection.getAccount().getId().equals(accountId))
    {
      throw new AuthenticationException(ConnectionRef.SESSION_UNDEFINED_ERROR,
                                        DisconnectionReason.NONE);
    }
  }

  /**
   * Cette m�thode permet de terminer la connexion d'un compte utilisateur au syst�me
   * tout en gardant sa r�manence si celle-ci avait �t� activ�e
   * @param session Session contenant les information de connexion
   * @return TODO A COMMENTER
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  public boolean endConnection(SESSION session) throws PersistenceException
  {
    // Recherche la connexion demand�e
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
   * Cette m�thode permet de d�connecter un compte utilisateur du syst�me et d'
   * arr�ter la r�manence de sa connexion si celle-ci avait �t� activ�e
   * @param session Session contenant les information de connexion
   * @return TODO A COMMENTER
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  public boolean disconnect(SESSION session) throws PersistenceException
  {
    // Recherche la connexion demand�e
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
   * Cette m�thode permet de modifier le mot de passe de connexion d'un compte
   * utilisateur en prenant en charge la suppression de la r�manence de toutes
   * les connexions le concernant except�e celle utilis�e pour la modification
   * ainsi que toute demande de r�-initialisation de mot de passe potentiellement
   * existante
   * @param session TODO A COMMENTER
   * @param accountId Identifiant du compte utilisateur � modifier
   * @param oldPassword Ancien mot de passe de connexion du compte utilisateur
   * @param newPassword Nouveau mot de passe de connexion du compte utilisateur
   * @return Le compte utilisateur modifi�
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si le compte utilisateur n'a pu �tre trouv�
   * @throws UserException Si l'ancien mot de passe de connexion en argument ne
   * correspond pas � celui du compte utilisateur
   * @throws SessionException Si aucune session n'est d�finie
   */
  public ACCOUNT updatePassword(SESSION session, String accountId,
                                Password oldPassword, Password newPassword)
         throws PersistenceException, NotFoundEntityException, UserException, SessionException
  {
    // Met � jour le mot de passe de connexion
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
    // Supprime toute demande de r�-initialisation de mot de passe potentiellement
    // existante
    REINIT reinit = this.getPasswordReinitDao().findOneByAccount(account);
    if(reinit != null)
    {
      this.getPasswordReinitDao().remove(reinit);
    }
    // Renvoie le compte utilisateur modifi�
    return account;
  }
  /**
   * Cette m�thode permet de demander la r�-initialisation du mot de passe de connexion
   * d'un compte utilisateur
   * @param loginOrEmail Identifiant de connexion ou adresse email du compte utilisateur
   * dont on demande la r�-initialisation de mot de passe de connexion
   * @return La r�f�rence de la demande de r�-initialisation de mot de passe qu'il
   * faudra utiliser
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si le compte utilisateur n'a pu �tre trouv�
   */
  public REINIT reinitPassword(String loginOrEmail)
         throws PersistenceException, UserException
  {
    // R�cup�re le compte utilisateur en fonction de son identifiant de connexion
    // ou de son email
    ACCOUNT account = this.getAccountManager().getAccount(loginOrEmail);
    // R�cup�re la r�-initialisation potentiellement d�j� existante
    REINIT reinit = this.getPasswordReinitDao().findOneByAccount(account);
    // Aucune r�-initialisation n'est en cours, il faut en cr�er une
    if(reinit == null)
    {
      reinit = this.getPasswordReinitDao().add(this.createReinitEntity(account));
    }
    // Retourne la r�-initialisation de mot de passe demand�e
    return reinit;
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
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucune r�f�rence de demande de r�-initialisation
   * de mot de passe n'a pu �tre r�cup�r�e avec la cl� et l'identifiant de compte
   * utilisateur en argument
   */
  @SuppressWarnings("unchecked")
  public REINIT getPasswordReinit(String reinitKey, String accountId)
         throws PersistenceException, NotFoundEntityException
  {
    // TODO bloquer l'ip si n�cessaire
    REINIT reinit = this.getPasswordReinitDao().getById(reinitKey);
    if(!reinit.getAccount().getId().equals(accountId))
    {
      throw new NotFoundEntityException(PasswordReinitAbstract.class);
    }
    return reinit;
  }
  /**
   * Cette m�thode permet d'effectuer la r�-initialisation du mot de passe de connexion
   * d'un compte utilisateur en prenant en charge la suppression de la r�manence
   * de toutes les connexions le concernant. Attention, le compte utilisateur ne
   * sera pas consid�r� automatiquement connect� au syst�me
   * @param session TODO A COMMENTER
   * @param reinitKey Cl� de la r�f�rence de la demande de r�-initialisation de
   * mot de passe
   * @param accountId Identifiant du compte utilisateur associ� � la demande de
   * r�-initialisation de mot de passe
   * @param password Nouveau mot de passe de connexion du compte utilisateur associ�
   * � la demande de r�-initialisation
   * @return Le compte utilisateur modifi�
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucune r�f�rence de demande de r�-initialisation
   * de mot de passe n'a pu �tre r�cup�r�e avec la cl� et l'identifiant de compte
   * utilisateur en argument
   * @throws ModelArgumentException Si les param�tres sont invalides
   */
  public ACCOUNT reinitPassword(SESSION session, String reinitKey,
                                String accountId, Password password)
         throws PersistenceException, NotFoundEntityException, ModelArgumentException
  {
 // TODO bloquer l'ip si n�cessaire
    // R�cup�re la r�-initialisation pr�alablement demand�e
    REINIT reinit = this.getPasswordReinit(reinitKey, accountId);
    // R�cup�re le compte utilisateur correspondant
    ACCOUNT account = reinit.getAccount();
    // Op�re la r�-initialisation du mot de passe (La suppression de la demande
    // de r�-initialisation est automatique � la modification du mot de passe)
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
   * Cette m�thode doit permettre de cr�er la nouvelle connexion � utiliser
   * @param session TODO A COMMENTER
   * @param account Compte utilisateur de la connexion � cr�er
   * @param remanent TODO A COMMENTER
   * @return La connexion cr��e
   * @throws UserException Si les param�tres sont invalides
   */
  protected abstract CONNECTION createConnectionEntity(SESSION session,
                                                       ACCOUNT account,
                                                       boolean remanent)
            throws UserException;
  /**
   * Cette m�thode doit permettre de cr�er la r�-initialisation de mot de passe
   * de connexion � utiliser
   * @param account Compte utilisateur de la r�-initialisation de mot de passe de
   * connexion
   * @return La r�-initialisation de mot de passe de connexion cr��e
   * @throws UserException Si les param�tres sont invalides
   */
  protected abstract REINIT createReinitEntity(ACCOUNT account) throws UserException;

  /**
   * Getter de la r�f�rence du DAO des connexions
   * @return La r�f�rence du DAO des connexions
   */
  protected ConnectionAbstractDao_<CONNECTION, HISTORY, ACCOUNT> getConnectionDao()
  {
    return this.connectionDao;
  }
  /**
   * Getter de la r�f�rence du DAO des historiques de connexion
   * @return La r�f�rence du DAO des historiques de connexion
   */
  protected ConnectionHistoryAbstractDao_<HISTORY, ACCOUNT> getHistoryDao()
  {
    return this.historyDao;
  }
  /**
   * Getter de la r�f�rence du DAO de la r�-initialisation des mots de passe
   * @return La r�f�rence du DAO de la r�-initialisation des mots de passe
   */
  protected PasswordReinitAbstractDao_<REINIT, ACCOUNT> getPasswordReinitDao()
  {
    return this.passwordReinitDao;
  }
}
