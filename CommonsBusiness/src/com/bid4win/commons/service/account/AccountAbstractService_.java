package com.bid4win.commons.service.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.manager.account.AccountAbstractManager_;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.security.Credential;
import com.bid4win.commons.persistence.entity.account.security.Role;
import com.bid4win.commons.persistence.entity.account.security.UtilSecurity;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthenticationException;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthorizationException;
import com.bid4win.commons.persistence.entity.account.security.exception.SessionException;
import com.bid4win.commons.persistence.entity.contact.Email;
import com.bid4win.commons.service.Bid4WinService_;
import com.bid4win.commons.service.connection.SessionDataAbstract;

/**
 * Service de base du projet pour la gestion des comptes utilisateur incluant la
 * gestion des transactions ainsi que celle des habilitations<BR>
 * <BR>
 * @param <SESSION> Définition du type de conteneur de données d'une session utilisé
 * par le projet<BR>
 * @param <ACCOUNT> Définition de type de compte utilisateur utilisé par le projet<BR>
 * @param <SERVICE> Définition du service implémenté afin de pouvoir s'autoréférencer<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class AccountAbstractService_<SESSION extends SessionDataAbstract<ACCOUNT, ?>,
                                     ACCOUNT extends AccountAbstract<ACCOUNT>,
                                     SERVICE extends AccountAbstractService_<SESSION, ACCOUNT, SERVICE>>
       extends Bid4WinService_<SESSION, ACCOUNT, SERVICE>
{
  /** Manager de gestion des comptes utilisateur */
  @Autowired
  @Qualifier("AccountManager")
  private AccountAbstractManager_<ACCOUNT> manager;

  /**
   * Cette méthode permet de récupérer la liste complète des comptes utilisateur
   * @return La liste complète des comptes utilisateur sans leurs relations
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  @Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
  public Bid4WinList<ACCOUNT> getAccountList()
         throws PersistenceException, SessionException,
                AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    // TODO ajouter un filtre sur les roles !!!!
    // Retourne la liste des comptes utilisateur
    return this.getManager().getAccountList();
  }
  /**
   * Cette méthode permet de créer un compte utilisateur
   * @param credential Certificat de connexion du compte utilisateur à créer
   * @param email Adresse email du compte utilisateur à créer
   * @return Le compte utilisateur créé
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante ou si l'identifiant de connexion ou l'email existe
   * déjà en base de données
   * @throws ModelArgumentException Si les paramètres sont invalides
   * @throws UserException Si l'identifiant de connexion ou l'email existe déjà
   * en base de données
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public ACCOUNT createAccount(Credential credential, Email email)
         throws PersistenceException, ModelArgumentException, UserException, AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    Role role = (credential != null ? credential.getRole() : null);
    this.checkRoleUpdate(role, role);
    // Crée et retourne le compte utilisateur
    return this.getManager().createAccount(credential, email);
  }
  /**
   * Cette méthode permet de modifier le role d'un compte utilisateur
   * @param accountId Identifiant du compte utilisateur à modifier
   * @param newRole Nouveau role du compte utilisateur
   * @return Le compte utilisateur modifié sans ses relations
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si le compte utilisateur n'a pu être trouvé
   * @throws UserException Si les arguments sont invalides
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public ACCOUNT updateRole(String accountId, Role newRole)
         throws PersistenceException, NotFoundEntityException, UserException,
                AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkRoleUpdate(this.getManager().getById(accountId).getCredential().getRole(),
                         newRole);
    // Modifie le rôle du compte utilisateur
    return this.getManager().updateRole(accountId, newRole);
  }

  /**
   *
   * TODO A COMMENTER
   * @param currentRole TODO A COMMENTER
   * @param newRole TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   * @throws SessionException TODO A COMMENTER
   * @throws AuthenticationException TODO A COMMENTER
   * @throws AuthorizationException TODO A COMMENTER
   */
  protected void checkRoleUpdate(Role currentRole, Role newRole)
            throws PersistenceException, SessionException,
                   AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    Role role = this.checkAdminRole();
    // Il n'y a que les super utilisateurs qui sont habilités à modifier n'importe
    // quel type d'utilisateur et à donner n'importe quel type de rôle
    if(!role.belongsTo(Role.SUPER))
    {
      // Un gestionnaire ne peut modifier qu'un autre gestionnaire
      UtilSecurity.checkRole(currentRole, this.getAdminRole());
      // Un gestionnaire doit au moins avoir le même rôle que celui à modifier
      UtilSecurity.checkRole(role, currentRole);
      // Un gestionnaire doit au moins avoir le même rôle que celui à donner
      UtilSecurity.checkRole(role, newRole);
    }
  }

  /**
   * L'oscar du rôle d'administration des comptes utilisateurs est attribué aux
   * gestionnaires
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinService_#getAdminRole()
   */
  @Override
  public Role getAdminRole()
  {
    return Role.MANAGER;
  }
  /**
   * Getter du manager de gestion des comptes utilisateur
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinService_#getManager()
   */
  @Override
  protected AccountAbstractManager_<ACCOUNT> getManager()
  {
    return this.manager;
  }
}
