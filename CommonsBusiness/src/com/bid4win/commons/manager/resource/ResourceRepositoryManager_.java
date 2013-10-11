package com.bid4win.commons.manager.resource;

import java.io.OutputStream;

import com.bid4win.commons.core.exception.CommunicationException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.resource.Bid4WinResourceStore;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.dao.resource.ResourceStorageDao_;
import com.bid4win.commons.persistence.dao.resource.ResourceUsageDao_;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.resource.ResourceStorage;
import com.bid4win.commons.persistence.entity.resource.ResourceType;
import com.bid4win.commons.persistence.entity.resource.ResourceUsage;

/**
 * Manager de gestion générique des ressources divisées en stockage/utilisation
 * incluant leur gestion métier<BR>
 * <BR>
 * @param <STORAGE> Définition du type de stockages de ressources gérés par le manager<BR>
 * @param <USAGE> Définition du type d'utilisations de ressources gérés par le manager<BR>
 * @param <TYPE> Définition de la classe des types associés aux ressources gérées
 * par le manager<BR>
 * @param <ACCOUNT> Definition de type de compte utilisateur utilisé par le projet<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class ResourceRepositoryManager_<STORAGE extends ResourceStorage<STORAGE, TYPE, USAGE>,
                                                 USAGE extends ResourceUsage<USAGE, TYPE, STORAGE>,
                                                 TYPE extends ResourceType<TYPE>,
                                                 ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends ResourceManager_<STORAGE, TYPE, ACCOUNT>
{
  /**
   * Cette méthode permet de récupérer une utilisation de ressource en fonction
   * de son identifiant
   * @param id Identifiant de l'utilisation de ressource à récupérer
   * @return L'utilisation de ressource récupérée en fonction de son identifiant
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucune utilisation de ressource n'a pu être
   * récupérée avec l'identifiant en argument
   */
  public USAGE getUsage(long id) throws PersistenceException, NotFoundEntityException
  {
    return this.getUsageDao().getById(id);
  }
  /**
   * Cette méthode permet de récupérer une utilisation de ressource en fonction
   * de son chemin d'accès complet
   * @param fullPath Chemin d'accès complet de l'utilisation de ressource à récupérer
   * @return L'utilisation de ressource récupérée en fonction de son identifiant
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucune utilisation de ressource n'a pu être
   * récupérée avec le chemin d'accès complet en argument
   * @throws UserException Si le chemin d'accès complet en argument est invalide
   */
  public USAGE getUsage(String fullPath)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    return this.getUsageDao().getOneByFullPath(fullPath);
  }
  /**
   * Cette méthode permet de charger une utilisation de ressource en fonction de
   * son chemin d'accès complet. Cette utilisation de ressource sera bloquée le
   * temps du chargement
   * @param fullPath Chemin d'accès complet de l'utilisation de ressource à charger
   * @param outputStream Flux de sortie dans lequel charger la ressource
   * @return L'utilisation de ressource chargée
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un problème intervient lors de l'ecriture
   * de la ressource à fournir
   * @throws NotFoundEntityException Si aucune utilisation de ressource n'a pu être
   * récupérée avec l'identifiant en argument
   * @throws UserException Si l'emplacement défini par l'utilisation de ressource
   * est invalide
   */
  public USAGE loadUsage(String fullPath, OutputStream outputStream)
         throws PersistenceException, CommunicationException,
                NotFoundEntityException, UserException
  {
    return this.loadUsage(this.getUsage(fullPath), outputStream);
  }
  /**
   * Cette méthode permet de charger une utilisation de ressource en fonction de
   * sa référence. Cette utilisation de ressource sera bloquée le temps du chargement
   * @param usage Référence de l'utilisation de ressource à charger
   * @param outputStream Flux de sortie dans lequel charger la ressource
   * @return L'utilisation de ressource chargée
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un problème intervient lors de l'ecriture
   * de la ressource à fournir
   * @throws UserException Si l'emplacement défini par l'utilisation de ressource
   * est invalide
   */
  protected USAGE loadUsage(USAGE usage, OutputStream outputStream)
            throws PersistenceException, CommunicationException, UserException
  {
    // Bloque l'utilisation de ressource le temps du chargement
    usage = this.getUsageDao().lock(usage);
    // Met a jour le magasin correspondant si besoin
    this.loadUsageInStore(usage);
    // Charge et retourne l'utilisation de ressource
    this.getStore().retreive(outputStream, usage);
    return usage;
  }
  /**
   * Cette méthode permet de charger dans son magasin une utilisation de ressource
   * en fonction de son chemin d'accès complet. Le chargement s'effectuera à partir
   * du stockage défini pour l'utilisation de ressource choisie. Ce stockage sera
   * bloqué le temps du chargement
   * @param fullPath Chemin d'accès complet de l'utilisation de ressource à charger
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucune utilisation de ressource n'a pu être
   * récupérée avec le chemin d'accès complet en argument
   * @throws UserException Si l'emplacement défini par l'utilisation de ressource
   * est invalide
   */
  public void loadUsageInStore(String fullPath)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    this.loadUsageInStore(this.getUsage(fullPath));
  }
  /**
   * Cette méthode permet de charger dans son magasin l'utilisation de ressource.
   * Le chargement s'effectuera à partir du stockage défini pour l'utilisation de
   * ressource choisie. Ce stockage sera bloqué le temps du chargement
   * @param usage Utilisation de ressource à charger
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si l'emplacement défini par l'utilisation de ressource
   * est invalide
   */
  public void loadUsageInStore(USAGE usage) throws PersistenceException, UserException
  {
    // Vérifie l'existence de la ressource
    if(!this.getStore().exists(usage))
    {
      synchronized(this.getStore())
      {
        if(!this.getStore().exists(usage))
        {
          // Bloque et récupère la référence du stockage de ressource
          STORAGE storage = this.getResourceDao().lock(usage.getStorage());
          // Charge la ressource stockée dans le magasin des utilisations
          this.getStore().copy(this.getRepository(), storage, usage);
        }
      }
    }
  }
  /**
   * Cette méthode permet de créer l'utilisation de ressource définie en argument
   * @param path Emplacement de stockage de l'utilisation de ressource
   * @param name Nom de l'utilisation de ressource
   * @param storageId Identifiant du stockage de la ressource associée
   * @return L'utilisation de ressource créée
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si l'emplacement défini par l'utilisation de ressource
   * est invalide
   */
  public USAGE createUsage(String path, String name, long storageId)
         throws PersistenceException, UserException
  {
    // Crée et ajoute l'utilisation de ressource en base de données
    USAGE usage = this.createUsageEntity(path, name,
                                         this.getResourceDao().getById(storageId));
    return this.createUsage(usage);
  }
  /**
   * Cette méthode permet de créer l'utilisation de ressource définie en argument
   * @param usage Utilisation de ressource à créer
   * @return L'utilisation de ressource créée
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  protected USAGE createUsage(USAGE usage) throws PersistenceException
  {
    // Ajoute l'utilisation de ressource en base de données
    return this.getUsageDao().add(usage);
  }
  /**
   * Cette méthode permet de déplacer l'utilisation de ressource définie en argument
   * @param id Identifiant de l'utilisation de ressource à déplacer
   * @param path Nouvel emplacement de stockage de l'utilisation de ressource
   * @param name Nouveau nom de l'utilisation de ressource
   * @return L'utilisation de ressource déplacée
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si l'utilisation de ressource à déplacer n'a
   * pu être récupérée
   * @throws UserException Si l'emplacement défini par la ressource est invalide
   */
  public USAGE moveUsage(long id, String path, String name)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    // Récupère l'utilisation de ressource à déplacer
    USAGE usage = this.getUsageDao().getById(id);
    // Déplace l'utilisation de ressource en base de données
    usage.definePath(path);
    usage.defineName(name);
    return this.getUsageDao().update(usage);
  }
  /**
   * Cette méthode permet de supprimer l'utilisation de ressource définie en argument
   * @param id Identifiant de l'utilisation de ressource à supprimer
   * @return L'utilisation de ressource supprimée
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si l'utilisation de ressource à supprimer
   * n'a pu être récupérée
   */
  public USAGE deleteUsage(long id) throws PersistenceException, NotFoundEntityException
  {
    // Récupère l'utilisation de ressource à supprimer
    USAGE usage = this.getUsageDao().getById(id);
    // Supprime l'utilisation de ressource
    return this.deleteUsage(usage);
  }
  /**
   * Cette méthode permet de supprimer l'utilisation de ressource définie en argument
   * @param usage Utilisation de ressource à supprimer
   * @return L'utilisation de ressource supprimée
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  public USAGE deleteUsage(USAGE usage) throws PersistenceException
  {
    // Supprime l'utilisation de ressource
    return this.getUsageDao().remove(usage);
  }

  /**
   * Cette méthode permet de créer l'utilisation de ressource définie en argument
   * @param path Emplacement de stockage de l'utilisation de ressource
   * @param name Nom de l'utilisation de ressource
   * @return L'utilisation de ressource créée
   * @throws PersistenceException Si un problème empêche la création du lien entre
   * l'utilisation de ressource et son stockage
   * @throws UserException Si le nom ou l'emplacement de stockage de la ressource
   * est invalide
   */
  protected abstract USAGE createUsageEntity(String path, String name, STORAGE storage)
            throws PersistenceException, UserException;
  /**
   * Précise la référence du DAO des stockages de ressource
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceManager_#getResourceDao()
   */
  @Override
  protected abstract ResourceStorageDao_<STORAGE, TYPE, USAGE> getResourceDao();
  /**
   * Getter de la référence du DAO des utilisations de ressource
   * @return La référence du DAO des utilisations de ressource
   */
  protected abstract ResourceUsageDao_<USAGE, TYPE, STORAGE> getUsageDao();
  /**
   * Getter du magasin de gestion des utilisations de ressource
   * @return Le magasin de gestion des utilisations de ressource
   */
  protected abstract Bid4WinResourceStore<USAGE, TYPE> getStore();
}
