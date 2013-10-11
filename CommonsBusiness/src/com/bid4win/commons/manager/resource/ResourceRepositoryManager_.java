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
 * Manager de gestion g�n�rique des ressources divis�es en stockage/utilisation
 * incluant leur gestion m�tier<BR>
 * <BR>
 * @param <STORAGE> D�finition du type de stockages de ressources g�r�s par le manager<BR>
 * @param <USAGE> D�finition du type d'utilisations de ressources g�r�s par le manager<BR>
 * @param <TYPE> D�finition de la classe des types associ�s aux ressources g�r�es
 * par le manager<BR>
 * @param <ACCOUNT> Definition de type de compte utilisateur utilis� par le projet<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class ResourceRepositoryManager_<STORAGE extends ResourceStorage<STORAGE, TYPE, USAGE>,
                                                 USAGE extends ResourceUsage<USAGE, TYPE, STORAGE>,
                                                 TYPE extends ResourceType<TYPE>,
                                                 ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends ResourceManager_<STORAGE, TYPE, ACCOUNT>
{
  /**
   * Cette m�thode permet de r�cup�rer une utilisation de ressource en fonction
   * de son identifiant
   * @param id Identifiant de l'utilisation de ressource � r�cup�rer
   * @return L'utilisation de ressource r�cup�r�e en fonction de son identifiant
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucune utilisation de ressource n'a pu �tre
   * r�cup�r�e avec l'identifiant en argument
   */
  public USAGE getUsage(long id) throws PersistenceException, NotFoundEntityException
  {
    return this.getUsageDao().getById(id);
  }
  /**
   * Cette m�thode permet de r�cup�rer une utilisation de ressource en fonction
   * de son chemin d'acc�s complet
   * @param fullPath Chemin d'acc�s complet de l'utilisation de ressource � r�cup�rer
   * @return L'utilisation de ressource r�cup�r�e en fonction de son identifiant
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucune utilisation de ressource n'a pu �tre
   * r�cup�r�e avec le chemin d'acc�s complet en argument
   * @throws UserException Si le chemin d'acc�s complet en argument est invalide
   */
  public USAGE getUsage(String fullPath)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    return this.getUsageDao().getOneByFullPath(fullPath);
  }
  /**
   * Cette m�thode permet de charger une utilisation de ressource en fonction de
   * son chemin d'acc�s complet. Cette utilisation de ressource sera bloqu�e le
   * temps du chargement
   * @param fullPath Chemin d'acc�s complet de l'utilisation de ressource � charger
   * @param outputStream Flux de sortie dans lequel charger la ressource
   * @return L'utilisation de ressource charg�e
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un probl�me intervient lors de l'ecriture
   * de la ressource � fournir
   * @throws NotFoundEntityException Si aucune utilisation de ressource n'a pu �tre
   * r�cup�r�e avec l'identifiant en argument
   * @throws UserException Si l'emplacement d�fini par l'utilisation de ressource
   * est invalide
   */
  public USAGE loadUsage(String fullPath, OutputStream outputStream)
         throws PersistenceException, CommunicationException,
                NotFoundEntityException, UserException
  {
    return this.loadUsage(this.getUsage(fullPath), outputStream);
  }
  /**
   * Cette m�thode permet de charger une utilisation de ressource en fonction de
   * sa r�f�rence. Cette utilisation de ressource sera bloqu�e le temps du chargement
   * @param usage R�f�rence de l'utilisation de ressource � charger
   * @param outputStream Flux de sortie dans lequel charger la ressource
   * @return L'utilisation de ressource charg�e
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un probl�me intervient lors de l'ecriture
   * de la ressource � fournir
   * @throws UserException Si l'emplacement d�fini par l'utilisation de ressource
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
   * Cette m�thode permet de charger dans son magasin une utilisation de ressource
   * en fonction de son chemin d'acc�s complet. Le chargement s'effectuera � partir
   * du stockage d�fini pour l'utilisation de ressource choisie. Ce stockage sera
   * bloqu� le temps du chargement
   * @param fullPath Chemin d'acc�s complet de l'utilisation de ressource � charger
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucune utilisation de ressource n'a pu �tre
   * r�cup�r�e avec le chemin d'acc�s complet en argument
   * @throws UserException Si l'emplacement d�fini par l'utilisation de ressource
   * est invalide
   */
  public void loadUsageInStore(String fullPath)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    this.loadUsageInStore(this.getUsage(fullPath));
  }
  /**
   * Cette m�thode permet de charger dans son magasin l'utilisation de ressource.
   * Le chargement s'effectuera � partir du stockage d�fini pour l'utilisation de
   * ressource choisie. Ce stockage sera bloqu� le temps du chargement
   * @param usage Utilisation de ressource � charger
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si l'emplacement d�fini par l'utilisation de ressource
   * est invalide
   */
  public void loadUsageInStore(USAGE usage) throws PersistenceException, UserException
  {
    // V�rifie l'existence de la ressource
    if(!this.getStore().exists(usage))
    {
      synchronized(this.getStore())
      {
        if(!this.getStore().exists(usage))
        {
          // Bloque et r�cup�re la r�f�rence du stockage de ressource
          STORAGE storage = this.getResourceDao().lock(usage.getStorage());
          // Charge la ressource stock�e dans le magasin des utilisations
          this.getStore().copy(this.getRepository(), storage, usage);
        }
      }
    }
  }
  /**
   * Cette m�thode permet de cr�er l'utilisation de ressource d�finie en argument
   * @param path Emplacement de stockage de l'utilisation de ressource
   * @param name Nom de l'utilisation de ressource
   * @param storageId Identifiant du stockage de la ressource associ�e
   * @return L'utilisation de ressource cr��e
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si l'emplacement d�fini par l'utilisation de ressource
   * est invalide
   */
  public USAGE createUsage(String path, String name, long storageId)
         throws PersistenceException, UserException
  {
    // Cr�e et ajoute l'utilisation de ressource en base de donn�es
    USAGE usage = this.createUsageEntity(path, name,
                                         this.getResourceDao().getById(storageId));
    return this.createUsage(usage);
  }
  /**
   * Cette m�thode permet de cr�er l'utilisation de ressource d�finie en argument
   * @param usage Utilisation de ressource � cr�er
   * @return L'utilisation de ressource cr��e
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  protected USAGE createUsage(USAGE usage) throws PersistenceException
  {
    // Ajoute l'utilisation de ressource en base de donn�es
    return this.getUsageDao().add(usage);
  }
  /**
   * Cette m�thode permet de d�placer l'utilisation de ressource d�finie en argument
   * @param id Identifiant de l'utilisation de ressource � d�placer
   * @param path Nouvel emplacement de stockage de l'utilisation de ressource
   * @param name Nouveau nom de l'utilisation de ressource
   * @return L'utilisation de ressource d�plac�e
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si l'utilisation de ressource � d�placer n'a
   * pu �tre r�cup�r�e
   * @throws UserException Si l'emplacement d�fini par la ressource est invalide
   */
  public USAGE moveUsage(long id, String path, String name)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    // R�cup�re l'utilisation de ressource � d�placer
    USAGE usage = this.getUsageDao().getById(id);
    // D�place l'utilisation de ressource en base de donn�es
    usage.definePath(path);
    usage.defineName(name);
    return this.getUsageDao().update(usage);
  }
  /**
   * Cette m�thode permet de supprimer l'utilisation de ressource d�finie en argument
   * @param id Identifiant de l'utilisation de ressource � supprimer
   * @return L'utilisation de ressource supprim�e
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si l'utilisation de ressource � supprimer
   * n'a pu �tre r�cup�r�e
   */
  public USAGE deleteUsage(long id) throws PersistenceException, NotFoundEntityException
  {
    // R�cup�re l'utilisation de ressource � supprimer
    USAGE usage = this.getUsageDao().getById(id);
    // Supprime l'utilisation de ressource
    return this.deleteUsage(usage);
  }
  /**
   * Cette m�thode permet de supprimer l'utilisation de ressource d�finie en argument
   * @param usage Utilisation de ressource � supprimer
   * @return L'utilisation de ressource supprim�e
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  public USAGE deleteUsage(USAGE usage) throws PersistenceException
  {
    // Supprime l'utilisation de ressource
    return this.getUsageDao().remove(usage);
  }

  /**
   * Cette m�thode permet de cr�er l'utilisation de ressource d�finie en argument
   * @param path Emplacement de stockage de l'utilisation de ressource
   * @param name Nom de l'utilisation de ressource
   * @return L'utilisation de ressource cr��e
   * @throws PersistenceException Si un probl�me emp�che la cr�ation du lien entre
   * l'utilisation de ressource et son stockage
   * @throws UserException Si le nom ou l'emplacement de stockage de la ressource
   * est invalide
   */
  protected abstract USAGE createUsageEntity(String path, String name, STORAGE storage)
            throws PersistenceException, UserException;
  /**
   * Pr�cise la r�f�rence du DAO des stockages de ressource
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceManager_#getResourceDao()
   */
  @Override
  protected abstract ResourceStorageDao_<STORAGE, TYPE, USAGE> getResourceDao();
  /**
   * Getter de la r�f�rence du DAO des utilisations de ressource
   * @return La r�f�rence du DAO des utilisations de ressource
   */
  protected abstract ResourceUsageDao_<USAGE, TYPE, STORAGE> getUsageDao();
  /**
   * Getter du magasin de gestion des utilisations de ressource
   * @return Le magasin de gestion des utilisations de ressource
   */
  protected abstract Bid4WinResourceStore<USAGE, TYPE> getStore();
}
