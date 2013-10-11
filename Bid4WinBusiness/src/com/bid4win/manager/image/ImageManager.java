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
 * Manager de gestion des images divis�es en stockage/utilisation incluant leur
 * gestion m�tier<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Component("ImageManager")
@Scope("singleton")
public class ImageManager
       extends ResourceRepositoryManager_<ImageStorage, ImageUsage, ImageType, Account>
{
  /** R�f�rence du DAO des r�f�rences d'images */
  @Autowired
  @Qualifier("ImageStorageDao")
  private ImageStorageDao resourceDao = null;
  /** R�f�rence du DAO des utilisations d'images */
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
   * Cette m�thode permet de charger une image en fonction de son identifiant.
   * Cette image sera bloqu�e le temps du chargement
   * @param id Identifiant de l'image � charger
   * @param format Format de l'image � charger
   * @param outputStream Flux de sortie dans lequel charger l'image
   * @return La r�f�rence de l'image charg�e
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un probl�me intervient lors de l'ecriture
   * de l'image � fournir
   * @throws NotFoundEntityException Si aucune image n'a pu �tre r�cup�r�e avec
   * l'identifiant en argument
   * @throws UserException Si l'emplacement d�fini par la ressource est invalide
   */
  public ImageStorage loadImage(long id, Format format, OutputStream outputStream)
         throws PersistenceException, CommunicationException,
                NotFoundEntityException, UserException
  {
    // Bloque et r�cup�re la r�f�rence de l'image � charger
    ImageStorage image = this.getResourceDao().lockById(id);
    // Charge l'image
    this.getRepository().retreive(outputStream, image.getPart(format));
    // Retourne la r�f�rence de l'image charg�e
    return image;
  }
  /**
   * Cette m�thode permet de charger une image en fonction de son identifiant.
   * Cette image sera bloqu�e le temps du chargement
   * @param id Identifiant de l'image � charger
   * @param format Format de l'image � charger
   * @return L'image charg�e
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un probl�me intervient lors de la lecture
   * de l'image � fournir
   * @throws NotFoundEntityException Si aucune image n'a pu �tre r�cup�r�e avec
   * l'identifiant en argument
   * @throws UserException Si l'emplacement d�fini par la ressource est invalide
   */
  public InputStream loadImage(long id, Format format)
         throws PersistenceException, CommunicationException,
                NotFoundEntityException, UserException
  {
    // Bloque et r�cup�re la r�f�rence de l'image � charger
    ImageStorage image = this.getResourceDao().lockById(id);
    // Charge et retourne l'image
    return this.getRepository().retreive(image.getPart(format));
  }
  /**
   * Cette m�thode permet de cr�er l'utilisation de l'image correspondant � l'
   * identifiant en argument pour le produit d�fini en param�tre
   * @param product Produit utilisant l'image correspondant � l'identifiant donn�
   * @param storageId Identifiant du stockage de l'image utilis�e
   * @return L'utilisation d'image cr��e
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si l'emplacement d�fini par l'utilisation de ressource
   * est invalide
   */
  public ImageUsage createUsage(Product product, long storageId)
         throws PersistenceException, UserException
  {
    // Cr�e et ajoute l'utilisation de ressource en base de donn�es
    ImageUsage usage = this.createUsageEntity(product,
                                              this.getResourceDao().getById(storageId));
    return this.createUsage(usage);
  }

  /**
   * Cette m�thode permet de cr�er le stockage d'image d�finie en argument
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
   * Cette m�thode permet de cr�er une utilisation d'image simple
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
   * Cette m�thode permet de cr�er une utilisation d'image pour un produit
   * @param product Produit utilisant l'image d�finie en argument
   * @param storage Image utilis�e par le produit d�fini en argument
   * @return L'utilisation d'image cr��e
   * @throws PersistenceException Si un probl�me emp�che la cr�ation du lien entre
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
   * Getter de la r�f�rence du DAO des stockages d'image
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceRepositoryManager_#getResourceDao()
   */
  @Override
  protected ImageStorageDao getResourceDao()
  {
    return this.resourceDao;
  }
  /**
   * Getter de la r�f�rence du DAO des utilisations d'image
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
