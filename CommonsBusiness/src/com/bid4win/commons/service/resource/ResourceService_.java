package com.bid4win.commons.service.resource;

import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.transaction.annotation.Transactional;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinStringRecursiveMap;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.CommunicationException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.manager.resource.ResourceManager_;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthenticationException;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthorizationException;
import com.bid4win.commons.persistence.entity.account.security.exception.SessionException;
import com.bid4win.commons.persistence.entity.resource.Resource;
import com.bid4win.commons.persistence.entity.resource.ResourceType;
import com.bid4win.commons.service.Bid4WinService_;
import com.bid4win.commons.service.connection.SessionDataAbstract;

/**
 * Service de base du projet pour la gestion des ressources incluant la gestion
 * des transactions ainsi que celle des habilitations<BR>
 * <BR>
 * @param <RESOURCE> Définition du type de ressources gérées par le service<BR>
 * @param <TYPE> Définition de la classe des types associés aux ressources gérées
 * par le service<BR>
 * @param <SESSION> Définition du type de conteneur de données d'une session utilisé
 * par le projet<BR>
 * @param <ACCOUNT> Definition de type de compte utilisateur utilisé par le projet<BR>
 * @param <SERVICE> Définition du service implémenté afin de pouvoir s'autoréférencer<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class ResourceService_<RESOURCE extends Resource<RESOURCE, TYPE>,
                                       TYPE extends ResourceType<TYPE>,
                                       SESSION extends SessionDataAbstract<ACCOUNT>,
                                       ACCOUNT extends AccountAbstract<ACCOUNT>,
                                       SERVICE extends ResourceService_<RESOURCE, TYPE, SESSION, ACCOUNT, SERVICE>>
       extends Bid4WinService_<SESSION, ACCOUNT, SERVICE>
{
  /**
   * Cette méthode permet de charger une ressource en fonction de son chemin d'accès
   * complet
   * @param fullPath Chemin d'accès complet de la ressource à charger
   * @param outputStream Flux de sortie dans lequel charger la ressource
   * @return La référence de la ressource chargée sans ses relations
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un problème intervient lors de l'ecriture
   * de la ressource à fournir
   * @throws NotFoundEntityException Si aucune ressource n'a pu être récupérée avec
   * le chemin d'accès complet en argument
   * @throws UserException Si l'emplacement défini par la ressource est invalide
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  @Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
  public RESOURCE loadResource(String fullPath, OutputStream outputStream)
         throws PersistenceException, CommunicationException, NotFoundEntityException,
                UserException, SessionException, AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    return this.getManager().loadResource(fullPath, outputStream);
  }
  /**
   * Cette méthode permet de charger une ressource en fonction de son identifiant
   * @param id Identifiant de la ressource à charger
   * @param outputStream Flux de sortie dans lequel charger la ressource
   * @return La référence de la ressource chargée sans ses relations
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un problème intervient lors de l'ecriture
   * de la ressource à fournir
   * @throws NotFoundEntityException Si aucune ressource n'a pu être récupérée avec
   * l'identifiant en argument
   * @throws UserException Si l'emplacement défini par la ressource est invalide
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  @Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
  public RESOURCE loadResource(long id, OutputStream outputStream)
         throws PersistenceException, CommunicationException, NotFoundEntityException,
                UserException, SessionException, AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    return this.getManager().loadResource(id, outputStream);
  }
  /**
   * Cette méthode permet de charger une ressource en fonction de son identifiant.
   * Cette ressource sera bloquée le temps du chargement
   * @param id Identifiant de la ressource à charger
   * @return La ressource chargée
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un problème intervient lors de la lecture
   * de la ressource à fournir
   * @throws NotFoundEntityException Si aucune ressource n'a pu être récupérée avec
   * l'identifiant en argument
   * @throws UserException Si l'emplacement défini par la ressource est invalide
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  @Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
  public InputStream loadResource(long id)
         throws PersistenceException, CommunicationException, NotFoundEntityException,
                UserException, SessionException, AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    return this.getManager().loadResource(id);
  }

  /**
   * Cette méthode permet de récupérer les sous-répertoires de celui défini par
   * le chemin en paramètre. Une liste vide sera retournée si l'emplacement donné
   * ne référence pas un répertoire ou n'existe pas
   * @param parentPath Emplacement parent relatif à la racine du magasin des sous
   * répertoires à recherche
   * @return Les sous-répertoires de la ressource définie par le chemin en paramètre
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si l'emplacement de la ressource en argument est invalide
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  public Bid4WinStringRecursiveMap getSubdirectories(String parentPath)
      throws PersistenceException, UserException, SessionException,
             AuthenticationException, AuthorizationException
  {
    return UtilFile.computeSubdirectories(this.self().getSubdirectoryList(parentPath),
                                          parentPath);
  }
  /**
   * Cette méthode permet de récupérer les sous-répertoires de celui défini par
   * le chemin en paramètre. Une liste vide sera retournée si l'emplacement donné
   * ne référence pas un répertoire ou n'existe pas
   * @param parentPath Emplacement parent relatif à la racine du magasin des sous
   * répertoires à recherche
   * @return Les sous-répertoires de la ressource définie par le chemin en paramètre
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si l'emplacement de la ressource en argument est invalide
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  @Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
  public Bid4WinList<String> getSubdirectoryList(String parentPath)
         throws PersistenceException, UserException, SessionException,
                AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    return this.getManager().getSubdirectories(parentPath);
  }
  /**
   * Cette fonction permet de récupérer la liste des références de resources existantes
   * @return La liste des références de resources existantes sans leurs relations
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  @Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
  public Bid4WinList<RESOURCE> getResourceList()
         throws PersistenceException, SessionException,
                AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    return this.getManager().getResourceList();
  }
  /**
   * Cette méthode permet de récupérer la liste des ressources dont l'emplacement
   * de stockage correspond à celui en argument
   * @param path Emplacement de stockage des ressource à récupérer
   * @return La liste des ressources dont l'emplacement de stockage correspond à
   * celui en argument
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si l'emplacement de stockage en argument est invalide
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  @Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
  public Bid4WinList<RESOURCE> getResourceList(String path)
         throws PersistenceException, UserException, SessionException,
                AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    return this.getManager().getResourceList(path);
  }
  /**
   * Cette méthode permet de créer la ressource définie en argument
   * @param path Emplacement de stockage de la ressource
   * @param name Nom de la ressource
   * @param type Type de la ressource
   * @param inputStream Ressource à stocker
   * @return La référence de la ressource créée
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un problème intervient lors de la lecture
   * de la ressource à stocker
   * @throws UserException Si l'emplacement défini par la ressource est invalide
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public RESOURCE createResource(String path, String name, TYPE type, InputStream inputStream)
         throws PersistenceException, CommunicationException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    return this.getManager().createResource(path, name, type, inputStream);
  }
  /**
   * Cette méthode permet de copier une ressource
   * @param id Identifiant de la ressource à copier
   * @param path Emplacement de stockage de la ressource copiée
   * @param name Nom de la ressource copiée
   * @return La reference de la ressource copiée
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si la ressource à copier n'a pu être récupérée
   * @throws UserException Si l'emplacement défini par la ressource à copier est
   * invalide
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public RESOURCE copyResource(long id, String path, String name)
         throws PersistenceException, NotFoundEntityException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    return this.getManager().copyResource(id, path, name);
  }
  /**
   * Cette méthode permet de modifier une ressource
   * @param id Identifiant de la référence de la ressource à modifier
   * @param type Nouveau type de la ressource
   * @param inputStream Ressource à stocker
   * @return La référence de la ressource modifiée
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un problème intervient lors de la lecture
   * de la ressource à stocker
   * @throws NotFoundEntityException Si la ressource à modifier n'a pu être récupérée
   * @throws UserException Si l'emplacement défini par la ressource à modifier est
   * invalide
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public RESOURCE updateResource(long id, TYPE type, InputStream inputStream)
         throws PersistenceException, CommunicationException, NotFoundEntityException,
                UserException, SessionException, AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    return this.getManager().updateResource(id, type, inputStream);
  }
  /**
   * Cette méthode permet de déplacer la ressource définie en argument
   * @param id Identifiant de la ressource à déplacer
   * @param path Nouvel emplacement de stockage de la ressource
   * @param name Nouveau nom de la ressource
   * @return La référence de la ressource déplacée
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si la ressource à déplacer n'a pu être récupérée
   * @throws UserException Si l'emplacement défini par la ressource est invalide
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public RESOURCE moveResource(long id, String path, String name)
         throws PersistenceException, NotFoundEntityException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    return this.getManager().moveResource(id, path, name);
  }
  /**
   * Cette méthode permet de supprimer la ressource définie en argument
   * @param id Identifiant de la ressource à supprimer
   * @return La référence de la ressource supprimée
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si la ressource à supprimer n'a pu être récupérée
   * @throws UserException Si l'emplacement défini par la ressource est invalide
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public RESOURCE deleteResource(long id)
         throws PersistenceException, NotFoundEntityException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    return this.getManager().deleteResource(id);
  }

  /**
   * Permet de préciser la référence du manager des ressources gérées par le service
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinService_#getManager()
   */
  @Override
  protected abstract ResourceManager_<RESOURCE, TYPE, ACCOUNT> getManager();
}
