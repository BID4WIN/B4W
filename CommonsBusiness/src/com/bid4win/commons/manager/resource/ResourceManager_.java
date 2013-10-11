package com.bid4win.commons.manager.resource;

import java.io.InputStream;
import java.io.OutputStream;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.CommunicationException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.resource.Bid4WinResourceStore;
import com.bid4win.commons.manager.Bid4WinManager_;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.dao.resource.ResourceDao_;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.resource.Resource;
import com.bid4win.commons.persistence.entity.resource.ResourceType;

/**
 * Manager de gestion générique des ressources incluant leur gestion métier<BR>
 * <BR>
 * @param <RESOURCE> Définition du type de ressources gérées par le manager<BR>
 * @param <TYPE> Définition de la classe des types associés aux ressources gérées
 * par le manager<BR>
 * @param <ACCOUNT> Definition de type de compte utilisateur utilisé par le projet<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class ResourceManager_<RESOURCE extends Resource<RESOURCE, TYPE>,
                                       TYPE extends ResourceType<TYPE>,
                                       ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends Bid4WinManager_<ACCOUNT, ResourceManager_<RESOURCE, TYPE, ACCOUNT>>
{
  /**
   * Cette méthode permet de charger une ressource en fonction de son chemin d'accès
   * complet. Cette ressource sera bloquée le temps du chargement
   * @param fullPath Chemin d'accès complet de la ressource à charger
   * @param outputStream Flux de sortie dans lequel charger la ressource
   * @return La référence de la ressource chargée
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un problème intervient lors de l'ecriture
   * de la ressource à fournir
   * @throws NotFoundEntityException Si aucune ressource n'a pu être récupérée avec
   * le chemin d'accès complet en argument
   * @throws UserException Si l'emplacement défini par la ressource est invalide
   */
  public RESOURCE loadResource(String fullPath, OutputStream outputStream)
         throws PersistenceException, CommunicationException,
                NotFoundEntityException, UserException
  {
    // Récupère la référence de la ressource à charger
    RESOURCE resource = this.getResourceDao().getOneByFullPath(fullPath);
    // Bloque la référence de la ressource à charger
    resource = this.getResourceDao().lock(resource);
    // Charge la ressource
    this.getRepository().retreive(outputStream, resource);
    // Retourne la référence de la ressource chargée
    return resource;
  }
  /**
   * Cette méthode permet de charger une ressource en fonction de son identifiant.
   * Cette ressource sera bloquée le temps du chargement
   * @param id Identifiant de la ressource à charger
   * @param outputStream Flux de sortie dans lequel charger la ressource
   * @return La référence de la ressource chargée
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un problème intervient lors de l'ecriture
   * de la ressource à fournir
   * @throws NotFoundEntityException Si aucune ressource n'a pu être récupérée avec
   * l'identifiant en argument
   * @throws UserException Si l'emplacement défini par la ressource est invalide
   */
  public RESOURCE loadResource(long id, OutputStream outputStream)
         throws PersistenceException, CommunicationException,
                NotFoundEntityException, UserException
  {
    // Bloque et récupère la référence de la ressource à charger
    RESOURCE resource = this.getResourceDao().lockById(id);
    // Charge la ressource
    this.getRepository().retreive(outputStream, resource);
    // Retourne la référence de la ressource chargée
    return resource;
  }
  /**
   * Cette méthode permet de charger une ressource en fonction de son identifiant.
   * Cette ressource sera bloquée le temps du chargement
   * @param id Identifiant de la ressource à charger
   * @return La ressource chargée
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un problème intervient lors de la lecture
   * de la ressource à fournir
   * @throws NotFoundEntityException Si aucune ressource n'a pu être récupérée avec
   * l'identifiant en argument
   * @throws UserException Si l'emplacement défini par la ressource est invalide
   */
  public InputStream loadResource(long id)
         throws PersistenceException, CommunicationException,
                NotFoundEntityException, UserException
  {
    // Bloque et récupère la référence de la ressource à charger
    RESOURCE resource = this.getResourceDao().lockById(id);
    // Charge et retourne la ressource
    return this.getRepository().retreive(resource);
  }
  /**
   * Cette méthode permet de récupérer les sous-répertoires de celui défini par
   * le chemin en paramètre. Une liste vide sera retournée si l'emplacement donné
   * ne référence pas un répertoire ou n'existe pas
   * @param parentPath Emplacement parent relatif à la racine du magasin des sous
   * répertoires à recherche
   * @return Les sous-répertoires de la ressource définie par le chemin en paramètre
   * @throws UserException Si l'emplacement de la ressource en argument est invalide
   */
  public Bid4WinList<String> getSubdirectories(String parentPath)
         throws UserException
  {
    return this.getResourceDao().getPathList(parentPath);
  }
  /**
   * Cette fonction permet de récupérer la liste des références de resources existantes
   * @return La liste des références de resources existantes
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  public Bid4WinList<RESOURCE> getResourceList() throws PersistenceException
  {
    return this.getResourceDao().findAll();
  }
  /**
   * Cette méthode permet de récupérer la liste des ressources dont l'emplacement
   * de stockage correspond à celui en argument
   * @param path Emplacement de stockage des ressource à récupérer
   * @return La liste des ressources dont l'emplacement de stockage correspond à
   * celui en argument
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si l'emplacement de stockage en argument est invalide
   */
  public Bid4WinList<RESOURCE> getResourceList(String path)
         throws PersistenceException, UserException
  {
    return this.getResourceDao().findResourceList(path);
  }
  /**
   * Cette méthode permet de créer la ressource définie en argument
   * @param path Emplacement de stockage de la ressource
   * @param name Nom de la ressource
   * @param type Type de la ressource
   * @param inputStream Ressource à stocker
   * @return La référence de la ressource créée
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un problème intervient lors de la lecture
   * de la ressource à stocker
   * @throws UserException Si l'emplacement défini par la ressource est invalide
   */
  public RESOURCE createResource(String path, String name, TYPE type, InputStream inputStream)
         throws PersistenceException, CommunicationException, UserException
  {
    // Crée et ajoute la référence de la ressource en base de données
    return this.createResource(this.createResourceEntity(path, name, type), inputStream);
  }
  /**
   * Cette méthode permet de créer la ressource définie en argument
   * @param resource Référence de la ressource à créer
   * @param inputStream Ressource à stocker
   * @return La référence de la ressource créée
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un problème intervient lors de la lecture
   * de la ressource à stocker
   * @throws UserException Si l'emplacement défini par la ressource est invalide
   */
  protected RESOURCE createResource(RESOURCE resource, InputStream inputStream)
            throws PersistenceException, CommunicationException, UserException
  {
    // Ajoute la référence de la ressource en base de données
    resource = this.getResourceDao().add(resource);
    this.getResourceDao().flush();
    // Stocke la ressource nouvellement référencée
    this.getRepository().store(inputStream, resource);
    return resource;
  }
  /**
   * Cette méthode permet de copier une ressource. Cette ressource sera bloquée
   * le temps de la copie
   * @param id Identifiant de la ressource à copier
   * @param path Emplacement de stockage de la ressource copiée
   * @param name Nom de la ressource copiée
   * @return La reference de la ressource copiée
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si la ressource à copier n'a pu être récupérée
   * @throws UserException Si l'emplacement défini par la ressource à copier est
   * invalide
   */
  public RESOURCE copyResource(long id, String path, String name)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    // Bloque et récupère la référence de la ressource à copier
    RESOURCE from = this.getResourceDao().lockById(id);
    // Ajoute la référence de la ressource cible en base de données
    RESOURCE to = this.createResourceEntity(path, name, from.getType());
    to = this.getResourceDao().add(to);
    this.getResourceDao().flush();
    // Stocke la ressource nouvellement référencée
    this.getRepository().copy(from, to);
    return to;
  }
  /**
   * Cette méthode permet de modifier une ressource. Cette ressource sera bloquée
   * le temps de la modification via l'update en base de données de l'entité
   * @param id Identifiant de la référence de la ressource à modifier
   * @param type Nouveau type de la ressource
   * @param inputStream Ressource à stocker
   * @return La référence de la ressource modifiée
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un problème intervient lors de la lecture
   * de la ressource à stocker
   * @throws NotFoundEntityException Si la ressource à modifier n'a pu être récupérée
   * @throws UserException Si l'emplacement défini par la ressource à modifier est
   * invalide
   */
  public RESOURCE updateResource(long id, TYPE type, InputStream inputStream)
         throws PersistenceException, CommunicationException,
                NotFoundEntityException, UserException
  {
    // Récupère la référence de la ressource à modifier
    RESOURCE resource = this.getResourceDao().getById(id);
    RESOURCE oldResource = null;
    // Si le type de ressource est modifié, l'extension du fichier physique la
    // représentant le sera aussi
    if(!resource.getType().equals(type))
    {
      oldResource = this.createResourceEntity(
          resource.getPath(), resource.getName(), resource.getType());
      resource.defineType(type);
    }
    // Modifie la référence de la ressource en base de données
    this.getResourceDao().forceUpdate(resource);
    this.getResourceDao().flush();
    // Si l'extension du fichier physique a été modifié, cela correspond à une
    // nouvelle ressource à créer à la place de l'ancienne
    if(oldResource != null)
    {
      this.getRepository().store(inputStream, resource);
      boolean success = false;
      try
      {
        this.getRepository().remove(oldResource);
        success = true;
      }
      finally
      {
        if(!success)
        {
          this.getRepository().remove(resource);
        }
      }
    }
    // Sinon seulement son contenu doit être modifié
    else
    {
      this.getRepository().replace(inputStream, resource);
    }
    return resource;
  }
  /**
   * Cette méthode permet de déplacer la ressource définie en argument. Cette
   * ressource sera bloquée le temps du déplacement via l'update en base de données
   * de l'entité
   * @param id Identifiant de la ressource à déplacer
   * @param path Nouvel emplacement de stockage de la ressource
   * @param name Nouveau nom de la ressource
   * @return La référence de la ressource déplacée
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si la ressource à déplacer n'a pu être récupérée
   * @throws UserException Si l'emplacement défini par la ressource est invalide
   */
  public RESOURCE moveResource(long id, String path, String name)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    // Récupère la référence de la ressource à déplacer
    RESOURCE resource = this.getResourceDao().getById(id);
    // Évite le déplacement à l'identique
    if(!resource.getPath().equals(path) || !resource.getName().equals(name))
    {
      RESOURCE oldResource = this.createResourceEntity(
          resource.getPath(), resource.getName(), resource.getType());
      // Déplace la référence de la ressource en base de données
      resource.definePath(path);
      resource.defineName(name);
      resource = this.getResourceDao().update(resource);
      this.getResourceDao().flush();
      // Déplace la ressource
      this.getRepository().move(oldResource, resource);
    }
    return resource;
  }
  /**
   * Cette méthode permet de supprimer la ressource définie en argument. Cette
   * ressource sera bloquée le temps de la suppression via le delete en base de
   * données de l'entité
   * @param id Identifiant de la ressource à supprimer
   * @return La référence de la ressource supprimée
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si la ressource à supprimer n'a pu être récupérée
   * @throws UserException Si l'emplacement défini par la ressource est invalide
   */
  public RESOURCE deleteResource(long id)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    // Récupère la référence de la ressource et la supprime
    RESOURCE resource = this.getResourceDao().remove(this.getResourceDao().getById(id));
    this.getResourceDao().flush();
    // Supprime la ressource
    this.getRepository().remove(resource);
    return resource;
  }

  /**
   * Cette méthode permet de créer la référence de ressource définie en argument
   * @param path Emplacement de stockage de la ressource
   * @param name Nom de la ressource
   * @param type Type de la ressource
   * @return La référence de ressource créée
   * @throws UserException Si le nom ou l'emplacement de stockage de la ressource
   * est invalide
   */
  protected abstract RESOURCE createResourceEntity(String path, String name, TYPE type)
            throws UserException;
  /**
   * Getter de la référence du DAO des ressources
   * @return La référence du DAO des ressources
   */
  protected abstract ResourceDao_<RESOURCE, TYPE> getResourceDao();
  /**
   * Getter du magasin de gestion du stockage des ressources
   * @return Le magasin de gestion du stockage des ressources
   */
  protected abstract Bid4WinResourceStore<RESOURCE, TYPE> getRepository();
}