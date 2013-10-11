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
 * Manager de gestion g�n�rique des ressources incluant leur gestion m�tier<BR>
 * <BR>
 * @param <RESOURCE> D�finition du type de ressources g�r�es par le manager<BR>
 * @param <TYPE> D�finition de la classe des types associ�s aux ressources g�r�es
 * par le manager<BR>
 * @param <ACCOUNT> Definition de type de compte utilisateur utilis� par le projet<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class ResourceManager_<RESOURCE extends Resource<RESOURCE, TYPE>,
                                       TYPE extends ResourceType<TYPE>,
                                       ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends Bid4WinManager_<ACCOUNT, ResourceManager_<RESOURCE, TYPE, ACCOUNT>>
{
  /**
   * Cette m�thode permet de charger une ressource en fonction de son chemin d'acc�s
   * complet. Cette ressource sera bloqu�e le temps du chargement
   * @param fullPath Chemin d'acc�s complet de la ressource � charger
   * @param outputStream Flux de sortie dans lequel charger la ressource
   * @return La r�f�rence de la ressource charg�e
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un probl�me intervient lors de l'ecriture
   * de la ressource � fournir
   * @throws NotFoundEntityException Si aucune ressource n'a pu �tre r�cup�r�e avec
   * le chemin d'acc�s complet en argument
   * @throws UserException Si l'emplacement d�fini par la ressource est invalide
   */
  public RESOURCE loadResource(String fullPath, OutputStream outputStream)
         throws PersistenceException, CommunicationException,
                NotFoundEntityException, UserException
  {
    // R�cup�re la r�f�rence de la ressource � charger
    RESOURCE resource = this.getResourceDao().getOneByFullPath(fullPath);
    // Bloque la r�f�rence de la ressource � charger
    resource = this.getResourceDao().lock(resource);
    // Charge la ressource
    this.getRepository().retreive(outputStream, resource);
    // Retourne la r�f�rence de la ressource charg�e
    return resource;
  }
  /**
   * Cette m�thode permet de charger une ressource en fonction de son identifiant.
   * Cette ressource sera bloqu�e le temps du chargement
   * @param id Identifiant de la ressource � charger
   * @param outputStream Flux de sortie dans lequel charger la ressource
   * @return La r�f�rence de la ressource charg�e
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un probl�me intervient lors de l'ecriture
   * de la ressource � fournir
   * @throws NotFoundEntityException Si aucune ressource n'a pu �tre r�cup�r�e avec
   * l'identifiant en argument
   * @throws UserException Si l'emplacement d�fini par la ressource est invalide
   */
  public RESOURCE loadResource(long id, OutputStream outputStream)
         throws PersistenceException, CommunicationException,
                NotFoundEntityException, UserException
  {
    // Bloque et r�cup�re la r�f�rence de la ressource � charger
    RESOURCE resource = this.getResourceDao().lockById(id);
    // Charge la ressource
    this.getRepository().retreive(outputStream, resource);
    // Retourne la r�f�rence de la ressource charg�e
    return resource;
  }
  /**
   * Cette m�thode permet de charger une ressource en fonction de son identifiant.
   * Cette ressource sera bloqu�e le temps du chargement
   * @param id Identifiant de la ressource � charger
   * @return La ressource charg�e
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un probl�me intervient lors de la lecture
   * de la ressource � fournir
   * @throws NotFoundEntityException Si aucune ressource n'a pu �tre r�cup�r�e avec
   * l'identifiant en argument
   * @throws UserException Si l'emplacement d�fini par la ressource est invalide
   */
  public InputStream loadResource(long id)
         throws PersistenceException, CommunicationException,
                NotFoundEntityException, UserException
  {
    // Bloque et r�cup�re la r�f�rence de la ressource � charger
    RESOURCE resource = this.getResourceDao().lockById(id);
    // Charge et retourne la ressource
    return this.getRepository().retreive(resource);
  }
  /**
   * Cette m�thode permet de r�cup�rer les sous-r�pertoires de celui d�fini par
   * le chemin en param�tre. Une liste vide sera retourn�e si l'emplacement donn�
   * ne r�f�rence pas un r�pertoire ou n'existe pas
   * @param parentPath Emplacement parent relatif � la racine du magasin des sous
   * r�pertoires � recherche
   * @return Les sous-r�pertoires de la ressource d�finie par le chemin en param�tre
   * @throws UserException Si l'emplacement de la ressource en argument est invalide
   */
  public Bid4WinList<String> getSubdirectories(String parentPath)
         throws UserException
  {
    return this.getResourceDao().getPathList(parentPath);
  }
  /**
   * Cette fonction permet de r�cup�rer la liste des r�f�rences de resources existantes
   * @return La liste des r�f�rences de resources existantes
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  public Bid4WinList<RESOURCE> getResourceList() throws PersistenceException
  {
    return this.getResourceDao().findAll();
  }
  /**
   * Cette m�thode permet de r�cup�rer la liste des ressources dont l'emplacement
   * de stockage correspond � celui en argument
   * @param path Emplacement de stockage des ressource � r�cup�rer
   * @return La liste des ressources dont l'emplacement de stockage correspond �
   * celui en argument
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si l'emplacement de stockage en argument est invalide
   */
  public Bid4WinList<RESOURCE> getResourceList(String path)
         throws PersistenceException, UserException
  {
    return this.getResourceDao().findResourceList(path);
  }
  /**
   * Cette m�thode permet de cr�er la ressource d�finie en argument
   * @param path Emplacement de stockage de la ressource
   * @param name Nom de la ressource
   * @param type Type de la ressource
   * @param inputStream Ressource � stocker
   * @return La r�f�rence de la ressource cr��e
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un probl�me intervient lors de la lecture
   * de la ressource � stocker
   * @throws UserException Si l'emplacement d�fini par la ressource est invalide
   */
  public RESOURCE createResource(String path, String name, TYPE type, InputStream inputStream)
         throws PersistenceException, CommunicationException, UserException
  {
    // Cr�e et ajoute la r�f�rence de la ressource en base de donn�es
    return this.createResource(this.createResourceEntity(path, name, type), inputStream);
  }
  /**
   * Cette m�thode permet de cr�er la ressource d�finie en argument
   * @param resource R�f�rence de la ressource � cr�er
   * @param inputStream Ressource � stocker
   * @return La r�f�rence de la ressource cr��e
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un probl�me intervient lors de la lecture
   * de la ressource � stocker
   * @throws UserException Si l'emplacement d�fini par la ressource est invalide
   */
  protected RESOURCE createResource(RESOURCE resource, InputStream inputStream)
            throws PersistenceException, CommunicationException, UserException
  {
    // Ajoute la r�f�rence de la ressource en base de donn�es
    resource = this.getResourceDao().add(resource);
    this.getResourceDao().flush();
    // Stocke la ressource nouvellement r�f�renc�e
    this.getRepository().store(inputStream, resource);
    return resource;
  }
  /**
   * Cette m�thode permet de copier une ressource. Cette ressource sera bloqu�e
   * le temps de la copie
   * @param id Identifiant de la ressource � copier
   * @param path Emplacement de stockage de la ressource copi�e
   * @param name Nom de la ressource copi�e
   * @return La reference de la ressource copi�e
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si la ressource � copier n'a pu �tre r�cup�r�e
   * @throws UserException Si l'emplacement d�fini par la ressource � copier est
   * invalide
   */
  public RESOURCE copyResource(long id, String path, String name)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    // Bloque et r�cup�re la r�f�rence de la ressource � copier
    RESOURCE from = this.getResourceDao().lockById(id);
    // Ajoute la r�f�rence de la ressource cible en base de donn�es
    RESOURCE to = this.createResourceEntity(path, name, from.getType());
    to = this.getResourceDao().add(to);
    this.getResourceDao().flush();
    // Stocke la ressource nouvellement r�f�renc�e
    this.getRepository().copy(from, to);
    return to;
  }
  /**
   * Cette m�thode permet de modifier une ressource. Cette ressource sera bloqu�e
   * le temps de la modification via l'update en base de donn�es de l'entit�
   * @param id Identifiant de la r�f�rence de la ressource � modifier
   * @param type Nouveau type de la ressource
   * @param inputStream Ressource � stocker
   * @return La r�f�rence de la ressource modifi�e
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un probl�me intervient lors de la lecture
   * de la ressource � stocker
   * @throws NotFoundEntityException Si la ressource � modifier n'a pu �tre r�cup�r�e
   * @throws UserException Si l'emplacement d�fini par la ressource � modifier est
   * invalide
   */
  public RESOURCE updateResource(long id, TYPE type, InputStream inputStream)
         throws PersistenceException, CommunicationException,
                NotFoundEntityException, UserException
  {
    // R�cup�re la r�f�rence de la ressource � modifier
    RESOURCE resource = this.getResourceDao().getById(id);
    RESOURCE oldResource = null;
    // Si le type de ressource est modifi�, l'extension du fichier physique la
    // repr�sentant le sera aussi
    if(!resource.getType().equals(type))
    {
      oldResource = this.createResourceEntity(
          resource.getPath(), resource.getName(), resource.getType());
      resource.defineType(type);
    }
    // Modifie la r�f�rence de la ressource en base de donn�es
    this.getResourceDao().forceUpdate(resource);
    this.getResourceDao().flush();
    // Si l'extension du fichier physique a �t� modifi�, cela correspond � une
    // nouvelle ressource � cr�er � la place de l'ancienne
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
    // Sinon seulement son contenu doit �tre modifi�
    else
    {
      this.getRepository().replace(inputStream, resource);
    }
    return resource;
  }
  /**
   * Cette m�thode permet de d�placer la ressource d�finie en argument. Cette
   * ressource sera bloqu�e le temps du d�placement via l'update en base de donn�es
   * de l'entit�
   * @param id Identifiant de la ressource � d�placer
   * @param path Nouvel emplacement de stockage de la ressource
   * @param name Nouveau nom de la ressource
   * @return La r�f�rence de la ressource d�plac�e
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si la ressource � d�placer n'a pu �tre r�cup�r�e
   * @throws UserException Si l'emplacement d�fini par la ressource est invalide
   */
  public RESOURCE moveResource(long id, String path, String name)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    // R�cup�re la r�f�rence de la ressource � d�placer
    RESOURCE resource = this.getResourceDao().getById(id);
    // �vite le d�placement � l'identique
    if(!resource.getPath().equals(path) || !resource.getName().equals(name))
    {
      RESOURCE oldResource = this.createResourceEntity(
          resource.getPath(), resource.getName(), resource.getType());
      // D�place la r�f�rence de la ressource en base de donn�es
      resource.definePath(path);
      resource.defineName(name);
      resource = this.getResourceDao().update(resource);
      this.getResourceDao().flush();
      // D�place la ressource
      this.getRepository().move(oldResource, resource);
    }
    return resource;
  }
  /**
   * Cette m�thode permet de supprimer la ressource d�finie en argument. Cette
   * ressource sera bloqu�e le temps de la suppression via le delete en base de
   * donn�es de l'entit�
   * @param id Identifiant de la ressource � supprimer
   * @return La r�f�rence de la ressource supprim�e
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si la ressource � supprimer n'a pu �tre r�cup�r�e
   * @throws UserException Si l'emplacement d�fini par la ressource est invalide
   */
  public RESOURCE deleteResource(long id)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    // R�cup�re la r�f�rence de la ressource et la supprime
    RESOURCE resource = this.getResourceDao().remove(this.getResourceDao().getById(id));
    this.getResourceDao().flush();
    // Supprime la ressource
    this.getRepository().remove(resource);
    return resource;
  }

  /**
   * Cette m�thode permet de cr�er la r�f�rence de ressource d�finie en argument
   * @param path Emplacement de stockage de la ressource
   * @param name Nom de la ressource
   * @param type Type de la ressource
   * @return La r�f�rence de ressource cr��e
   * @throws UserException Si le nom ou l'emplacement de stockage de la ressource
   * est invalide
   */
  protected abstract RESOURCE createResourceEntity(String path, String name, TYPE type)
            throws UserException;
  /**
   * Getter de la r�f�rence du DAO des ressources
   * @return La r�f�rence du DAO des ressources
   */
  protected abstract ResourceDao_<RESOURCE, TYPE> getResourceDao();
  /**
   * Getter du magasin de gestion du stockage des ressources
   * @return Le magasin de gestion du stockage des ressources
   */
  protected abstract Bid4WinResourceStore<RESOURCE, TYPE> getRepository();
}