package com.bid4win.commons.service.resource;

import org.springframework.transaction.annotation.Transactional;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.manager.resource.ResourceRepositoryManager_;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthenticationException;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthorizationException;
import com.bid4win.commons.persistence.entity.account.security.exception.SessionException;
import com.bid4win.commons.persistence.entity.resource.ResourceStorage;
import com.bid4win.commons.persistence.entity.resource.ResourceType;
import com.bid4win.commons.persistence.entity.resource.ResourceUsage;
import com.bid4win.commons.service.connection.SessionDataAbstract;

/**
 * Service de gestion générique des ressources divisées en stockage/utilisation
 * incluant la gestion des transactions ainsi que celle des habilitations<BR>
 * <BR>
 * @param <STORAGE> Définition du type de stockages de ressources gérés par le service<BR>
 * @param <USAGE> Définition du type d'utilisations de ressources gérés par le service<BR>
 * @param <TYPE> Définition de la classe des types associés aux ressources gérées
 * par le service<BR>
 * @param <SESSION> Définition du type de conteneur de données d'une session utilisé
 * par le projet<BR>
 * @param <ACCOUNT> Definition de type de compte utilisateur utilisé par le projet<BR>
 * @param <SERVICE> Définition du service implémenté afin de pouvoir s'autoréférencer<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class ResourceRepositoryService_<STORAGE extends ResourceStorage<STORAGE, TYPE, USAGE>,
                                                 USAGE extends ResourceUsage<USAGE, TYPE, STORAGE>,
                                                 TYPE extends ResourceType<TYPE>,
                                                 SESSION extends SessionDataAbstract<ACCOUNT, ?>,
                                                 ACCOUNT extends AccountAbstract<ACCOUNT>,
                                                 SERVICE extends ResourceRepositoryService_<STORAGE, USAGE, TYPE, SESSION, ACCOUNT, SERVICE>>
       extends ResourceService_<STORAGE, TYPE, SESSION, ACCOUNT, SERVICE>
{
  /**
   * Cette méthode permet de charger une utilisation de ressource en fonction de
   * son chemin d'accès complet
   * @param fullPath Chemin d'accès complet de l'utilisation de ressource à charger
   * @param outputStream Flux de sortie dans lequel charger la ressource
   * @return L'utilisation de ressource chargée sans ses relations
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un problème intervient lors de l'ecriture
   * de la ressource à fournir
   * @throws NotFoundEntityException Si aucune utilisation de ressource n'a pu être
   * récupérée avec l'identifiant en argument
   * @throws UserException Si l'emplacement défini par l'utilisation de ressource
   * est invalide
   */
 /* @Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
  public USAGE loadUsage(String fullPath, OutputStream outputStream)
         throws PersistenceException, CommunicationException,
                NotFoundEntityException, UserException
  {
    return this.getManager().loadUsage(fullPath, outputStream);
  }
  /**
   * Cette méthode permet de charger dans son magasin une utilisation de ressource
   * en fonction de son chemin d'accès complet. Le chargement s'effectuera à partir
   * du stockage défini pour l'utilisation de ressource choisie
   * @param fullPath Chemin d'accès complet de l'utilisation de ressource à charger
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucune utilisation de ressource n'a pu être
   * récupérée avec le chemin d'accès complet en argument
   * @throws UserException Si l'emplacement défini par l'utilisation de ressource
   * est invalide
   */
/*  @Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
  public void loadUsageInStore(String fullPath)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    this.getManager().loadUsageInStore(fullPath);
  }*/
  /**
   * Cette méthode permet de créer l'utilisation de ressource définie en argument
   * @param path Emplacement de stockage de l'utilisation de ressource
   * @param name Nom de l'utilisation de ressource
   * @param storageId Identifiant du stockage de la ressource associée
   * @return L'utilisation de ressource créée
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si l'emplacement défini par l'utilisation de ressource
   * est invalide
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public USAGE createUsage(String path, String name, long storageId)
         throws PersistenceException, UserException, SessionException,
                AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    return this.getManager().createUsage(path, name, storageId);
  }
  /**
   * Cette méthode permet de déplacer l'utilisation de ressource définie en argument
   * @param id Identifiant de l'utilisation de ressource à déplacer
   * @param path Nouvel emplacement de stockage de l'utilisation de ressource
   * @param name Nouveau nom de l'utilisation de ressource
   * @return L'utilisation de ressource déplacée
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si l'utilisation de ressource à déplacer n'a
   * pu être récupérée
   * @throws UserException Si l'emplacement défini par la ressource est invalide
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public USAGE moveUsage(long id, String path, String name)
         throws PersistenceException, NotFoundEntityException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    return this.getManager().moveUsage(id, path, name);
  }
  /**
   * Cette méthode permet de supprimer l'utilisation de ressource définie en argument
   * @param id Identifiant de l'utilisation de ressource à supprimer
   * @return L'utilisation de ressource supprimée
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si l'utilisation de ressource à supprimer
   * n'a pu être récupérée
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public USAGE deleteUsage(long id)
         throws PersistenceException, NotFoundEntityException, SessionException,
                AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    return this.getManager().deleteUsage(id);
  }

  /**
   * Permet de préciser la référence du manager des ressources gérées par le service
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.resource.ResourceService_#getManager()
   */
  @Override
  protected abstract ResourceRepositoryManager_<STORAGE, USAGE, TYPE, ACCOUNT> getManager();
}
