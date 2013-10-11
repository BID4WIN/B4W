package com.bid4win.manager.locale.inner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.manager.resource.ResourceRepositoryMultiPartManager_;
import com.bid4win.manager.locale.inner.store.InnerContentRepository;
import com.bid4win.manager.locale.inner.store.InnerContentUsageStore;
import com.bid4win.persistence.dao.locale.inner.InnerContentStorageDao;
import com.bid4win.persistence.dao.locale.inner.InnerContentUsageDao;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.locale.Language;
import com.bid4win.persistence.entity.locale.inner.InnerContentStorage;
import com.bid4win.persistence.entity.locale.inner.InnerContentType;
import com.bid4win.persistence.entity.locale.inner.InnerContentUsage;
import com.bid4win.persistence.entity.locale.resource.InnerContent;
import com.bid4win.persistence.entity.product.Product;

/**
 * Manager de gestion des contenus internationalis�s internes � l'application divis�s
 * en stockage/utilisation incluant leur gestion m�tier<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Component("InnerContentManager")
@Scope("singleton")
public class InnerContentManager
       extends ResourceRepositoryMultiPartManager_<InnerContentStorage, InnerContentUsage,
                                                   InnerContentType, Language, InnerContent, Account>
{
  /** R�f�rence du DAO des r�f�rences de contenus */
  @Autowired
  @Qualifier("InnerContentStorageDao")
  private InnerContentStorageDao resourceDao = null;
  /** R�f�rence du DAO des utilisations de contenus */
  @Autowired
  @Qualifier("InnerContentUsageDao")
  private InnerContentUsageDao usageDao = null;
  /** Magasin de gestion des stockages de contenus */
  @Autowired
  @Qualifier("InnerContentRepository")
  private InnerContentRepository repository = null;
  /** Magasin de gestion des utilisations de contenus */
  @Autowired
  @Qualifier("InnerContentUsageStore")
  private InnerContentUsageStore store = null;

  /**
   * Cette m�thode permet de cr�er l'utilisation de contenu correspondant � l'
   * identifiant en argument pour le produit d�fini en param�tre
   * @param product Produit utilisant le contenu correspondant � l'identifiant
   * donn�
   * @param storageId Identifiant du stockage du contenu utilis�
   * @return L'utilisation de contenu cr��e
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si l'emplacement d�fini par l'utilisation de contenu
   * est invalide
   */
  public InnerContentUsage createUsage(Product product, long storageId)
         throws PersistenceException, UserException
  {
    // Cr�e et ajoute l'utilisation de ressource en base de donn�es
    InnerContentUsage usage = this.createUsageEntity(product,
                                              this.getResourceDao().getById(storageId));
    return this.createUsage(usage);
  }

  /**
   * Cette m�thode permet de cr�er le stockage de contenu d�fini en argument
   * @param path {@inheritDoc}
   * @param name {@inheritDoc}
   * @param type {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceManager_#createResourceEntity(java.lang.String, java.lang.String, com.bid4win.commons.persistence.entity.resource.ResourceType)
   */
  @Override
  protected InnerContentStorage createResourceEntity(String path, String name,
                                                     InnerContentType type)
            throws UserException
  {
    return new InnerContentStorage(path, name, type);
  }
  /**
   * Cette m�thode permet de cr�er une utilisation de contenu simple
   * @param path {@inheritDoc}
   * @param name {@inheritDoc}
   * @param storage {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceRepositoryManager_#createUsageEntity(java.lang.String, java.lang.String, com.bid4win.commons.persistence.entity.resource.ResourceStorage)
   */
  @Override
  protected InnerContentUsage createUsageEntity(String path, String name,
                                                InnerContentStorage storage)
            throws PersistenceException, UserException
  {
    try
    {
      return new InnerContentUsage(path, name, storage);
    }
    catch(ModelArgumentException ex)
    {
      throw new PersistenceException(ex);
    }
  }
  /**
   * Cette m�thode permet de cr�er une utilisation de contenu pour un produit
   * @param product Produit utilisant le contenu d�fini en argument
   * @param storage Contenu utilis� par le produit d�fini en argument
   * @return L'utilisation de contenu cr��e
   * @throws PersistenceException Si un probl�me emp�che la cr�ation du lien entre
   * l'utilisation de contenu et son stockage ou son produit
   * @throws UserException Si le nom ou l'emplacement de stockage de l'utilisation
   * de contenu est invalide
   */
  protected InnerContentUsage createUsageEntity(Product product, InnerContentStorage storage)
            throws PersistenceException, UserException
  {
    try
    {
      return new InnerContentUsage(product, storage);
    }
    catch(ModelArgumentException ex)
    {
      throw new PersistenceException(ex);
    }
  }

  /**
   * Getter de la r�f�rence du DAO des stockages de contenu
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceManager_#getResourceDao()
   */
  @Override
  protected InnerContentStorageDao getResourceDao()
  {
    return this.resourceDao;
  }
  /**
   * Getter de la r�f�rence du DAO des utilisations de contenu
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceRepositoryManager_#getUsageDao()
   */
  @Override
  protected InnerContentUsageDao getUsageDao()
  {
    return this.usageDao;
  }
  /**
   * Getter du magasin de gestion du stockage des contenus
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceManager_#getRepository()
   */
  @Override
  protected InnerContentRepository getRepository()
  {
    return this.repository;
  }
  /**
   * Getter du magasin de gestion des utilisations de contenus
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceRepositoryMultiPartManager_#getStore()
   */
  @Override
  protected InnerContentUsageStore getStore()
  {
    return this.store;
  }
}
