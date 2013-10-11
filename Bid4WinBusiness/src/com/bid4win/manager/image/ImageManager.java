package com.bid4win.manager.image;

import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.core.exception.CommunicationException;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.manager.resource.ResourceRepositoryManager_;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.manager.image.store.ImageRepository;
import com.bid4win.manager.image.store.ImageUsageStore;
import com.bid4win.persistence.dao.image.ImageStorageDao;
import com.bid4win.persistence.dao.image.ImageUsageDao;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.image.ImageStorage;
import com.bid4win.persistence.entity.image.ImageType;
import com.bid4win.persistence.entity.image.ImageUsage;
import com.bid4win.persistence.entity.image.resource.Format;
import com.bid4win.persistence.entity.product.Product;

/**
 * Manager de gestion des images divisées en stockage/utilisation incluant leur
 * gestion métier<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("ImageManager")
@Scope("singleton")
public class ImageManager
       extends ResourceRepositoryManager_<ImageStorage, ImageUsage, ImageType, Account>
{
  /** Référence du DAO des références d'images */
  @Autowired
  @Qualifier("ImageStorageDao")
  private ImageStorageDao resourceDao = null;
  /** Référence du DAO des utilisations d'images */
  @Autowired
  @Qualifier("ImageUsageDao")
  private ImageUsageDao usageDao = null;
  /** Magasin de gestion des stockages d'image */
  @Autowired
  @Qualifier("ImageRepository")
  private ImageRepository repository = null;
  /** Magasin de gestion des utilisations d'image */
  @Autowired
  @Qualifier("ImageUsageStore")
  private ImageUsageStore store = null;

  /**
   * Cette méthode permet de charger une image en fonction de son identifiant.
   * Cette image sera bloquée le temps du chargement
   * @param id Identifiant de l'image à charger
   * @param format Format de l'image à charger
   * @param outputStream Flux de sortie dans lequel charger l'image
   * @return La référence de l'image chargée
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un problème intervient lors de l'ecriture
   * de l'image à fournir
   * @throws NotFoundEntityException Si aucune image n'a pu être récupérée avec
   * l'identifiant en argument
   * @throws UserException Si l'emplacement défini par la ressource est invalide
   */
  public ImageStorage loadImage(long id, Format format, OutputStream outputStream)
         throws PersistenceException, CommunicationException,
                NotFoundEntityException, UserException
  {
    // Bloque et récupère la référence de l'image à charger
    ImageStorage image = this.getResourceDao().lockById(id);
    // Charge l'image
    this.getRepository().retreive(outputStream, image.getPart(format));
    // Retourne la référence de l'image chargée
    return image;
  }
  /**
   * Cette méthode permet de charger une image en fonction de son identifiant.
   * Cette image sera bloquée le temps du chargement
   * @param id Identifiant de l'image à charger
   * @param format Format de l'image à charger
   * @return L'image chargée
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un problème intervient lors de la lecture
   * de l'image à fournir
   * @throws NotFoundEntityException Si aucune image n'a pu être récupérée avec
   * l'identifiant en argument
   * @throws UserException Si l'emplacement défini par la ressource est invalide
   */
  public InputStream loadImage(long id, Format format)
         throws PersistenceException, CommunicationException,
                NotFoundEntityException, UserException
  {
    // Bloque et récupère la référence de l'image à charger
    ImageStorage image = this.getResourceDao().lockById(id);
    // Charge et retourne l'image
    return this.getRepository().retreive(image.getPart(format));
  }
  /**
   * Cette méthode permet de créer l'utilisation de l'image correspondant à l'
   * identifiant en argument pour le produit défini en paramètre
   * @param product Produit utilisant l'image correspondant à l'identifiant donné
   * @param storageId Identifiant du stockage de l'image utilisée
   * @return L'utilisation d'image créée
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si l'emplacement défini par l'utilisation de ressource
   * est invalide
   */
  public ImageUsage createUsage(Product product, long storageId)
         throws PersistenceException, UserException
  {
    // Crée et ajoute l'utilisation de ressource en base de données
    ImageUsage usage = this.createUsageEntity(product,
                                              this.getResourceDao().getById(storageId));
    return this.createUsage(usage);
  }

  /**
   * Cette méthode permet de créer le stockage d'image définie en argument
   * @param path {@inheritDoc}
   * @param name {@inheritDoc}
   * @param type {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceManager_#createResourceEntity(java.lang.String, java.lang.String, com.bid4win.commons.persistence.entity.resource.ResourceType)
   */
  @Override
  protected ImageStorage createResourceEntity(String path, String name, ImageType type)
            throws UserException
  {
    return new ImageStorage(path, name, type);
  }
  /**
   * Cette méthode permet de créer une utilisation d'image simple
   * @param path {@inheritDoc}
   * @param name {@inheritDoc}
   * @param storage {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceRepositoryManager_#createUsageEntity(java.lang.String, java.lang.String, com.bid4win.commons.persistence.entity.resource.ResourceStorage)
   */
  @Override
  protected ImageUsage createUsageEntity(String path, String name, ImageStorage storage)
            throws PersistenceException, UserException
  {
    try
    {
      return new ImageUsage(path, name, storage);
    }
    catch(ModelArgumentException ex)
    {
      throw new PersistenceException(ex);
    }
  }
  /**
   * Cette méthode permet de créer une utilisation d'image pour un produit
   * @param product Produit utilisant l'image définie en argument
   * @param storage Image utilisée par le produit défini en argument
   * @return L'utilisation d'image créée
   * @throws PersistenceException Si un problème empêche la création du lien entre
   * l'utilisation d'image et son stockage ou son produit
   * @throws UserException Si le nom ou l'emplacement de stockage de l'utilisation
   * d'image est invalide
   */
  protected ImageUsage createUsageEntity(Product product, ImageStorage storage)
            throws PersistenceException, UserException
  {
    try
    {
      return new ImageUsage(product, storage);
    }
    catch(ModelArgumentException ex)
    {
      throw new PersistenceException(ex);
    }
  }

  /**
   * Getter de la référence du DAO des stockages d'image
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceRepositoryManager_#getResourceDao()
   */
  @Override
  protected ImageStorageDao getResourceDao()
  {
    return this.resourceDao;
  }
  /**
   * Getter de la référence du DAO des utilisations d'image
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceRepositoryManager_#getUsageDao()
   */
  @Override
  protected ImageUsageDao getUsageDao()
  {
    return this.usageDao;
  }
  /**
   * Getter du magasin de gestion du stockage des images
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceManager_#getRepository()
   */
  @Override
  protected ImageRepository getRepository()
  {
    return this.repository;
  }
  /**
   * Getter du magasin de gestion des utilisations d'image
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceRepositoryManager_#getStore()
   */
  @Override
  protected ImageUsageStore getStore()
  {
    return this.store;
  }
}
