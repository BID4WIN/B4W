package com.bid4win.service.connection;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.account.security.Credential;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthenticationException;
import com.bid4win.commons.persistence.entity.contact.Email;
import com.bid4win.commons.service.connection.ConnectionAbstractService_;
import com.bid4win.manager.connection.ConnectionManager;
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
  private static ConnectionService instance = null;
  public static ConnectionService getInstance()
  {
    return ConnectionService.instance;
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
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Subscription subscribe(Credential credential, Email email)
         throws PersistenceException, ModelArgumentException, UserException, AuthenticationException
  {
    // @ V�rifie qu'aucun compte utilisateur n'est connect�
    //this.checkNotConnected(); TODO voir si besoin de le tester
    return this.getManager().subscribe(credential, email);
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
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Account validateSubscription(String validationKey, String accountId)
         throws PersistenceException, NotFoundEntityException, ModelArgumentException, UserException
  {
    return this.getManager().validateSubscription(validationKey, accountId);
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.connection.ConnectionAbstractService_#getManager()
   */
  @Override
  protected ConnectionManager getManager()
  {
    return (ConnectionManager)super.getManager();
  }
  /**
   *
   * TODO A COMMENTER
   * @param self {@inheritDoc}
   * @see com.bid4win.commons.j2ee.Bid4WinSelfReferencedBean#setSelf(com.bid4win.commons.j2ee.Bid4WinSelfReferencedBean)
   */
  @Override
  protected void setSelf(ConnectionService self)
  {
    super.setSelf(self);
    ConnectionService.instance = this.self();
  }
}
