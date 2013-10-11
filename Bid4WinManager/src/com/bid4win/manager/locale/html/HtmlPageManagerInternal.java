//package com.bid4win.manager.locale.html;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.commons.core.exception.PersistenceException;
//import com.bid4win.commons.core.exception.UserException;
//import com.bid4win.commons.manager.account.AccountAbstractManagerInternal_;
//import com.bid4win.commons.manager.resource.ResourceRepositoryMultiPartManagerInternal_;
//import com.bid4win.manager.account.AccountManagerInternal;
//import com.bid4win.manager.locale.html.store.HtmlPageRepository;
//import com.bid4win.manager.locale.html.store.HtmlPageUsageStore;
//import com.bid4win.persistence.dao.locale.html.HtmlPageStorageDao;
//import com.bid4win.persistence.dao.locale.html.HtmlPageUsageDao;
//import com.bid4win.persistence.entity.account.Account;
//import com.bid4win.persistence.entity.locale.Language;
//import com.bid4win.persistence.entity.locale.html.HtmlPageStorage;
//import com.bid4win.persistence.entity.locale.html.HtmlPageType;
//import com.bid4win.persistence.entity.locale.html.HtmlPageUsage;
//import com.bid4win.persistence.entity.locale.resource.HtmlPage;
//import com.bid4win.persistence.entity.product.Product;
//
///**
// * Manager interne de gestion des pages HTML divis�es en stockage/utilisation
// * incluant leur gestion m�tier<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
//@Component("HtmlPageManagerInternal")
//@Scope("singleton")
//public class HtmlPageManagerInternal
//       extends ResourceRepositoryMultiPartManagerInternal_<HtmlPageStorage, HtmlPageUsage, HtmlPageType, Language, HtmlPage, Account>
//{
//  /** R�f�rence du DAO des r�f�rences d'images */
//  @Autowired
//  @Qualifier("HtmlPageStorageDao")
//  private HtmlPageStorageDao resourceDao = null;
//  /** R�f�rence du DAO des utilisations d'images */
//  @Autowired
//  @Qualifier("HtmlPageUsageDao")
//  private HtmlPageUsageDao usageDao = null;
//  /** Magasin de gestion des stockages d'image */
//  @Autowired
//  @Qualifier("HtmlPageRepository")
//  private HtmlPageRepository repository = null;
//  /** Magasin de gestion des utilisations d'image */
//  @Autowired
//  @Qualifier("HtmlPageUsageStore")
//  private HtmlPageUsageStore store = null;
//  /** R�f�rence du manager interne de gestion des comptes utilisateur */
//  @Autowired
//  @Qualifier("AccountManagerInternal")
//  private AccountManagerInternal accountManager = null;
//
//  /**
//   * Cette m�thode permet de cr�er l'utilisation de la page HTML correspondant �
//   * l'identifiant en argument pour le produit d�fini en param�tre
//   * @param product Produit utilisant la page HTML correspondant � l'identifiant
//   * donn�
//   * @param storageId Identifiant du stockage de la page HTML utilis�e
//   * @return L'utilisation de page HTML cr��e
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   * @throws UserException Si l'emplacement d�fini par l'utilisation de page HTML
//   * est invalide
//   */
//  public HtmlPageUsage createUsage(Product product, long storageId)
//         throws PersistenceException, UserException
//  {
//    // Cr�e et ajoute l'utilisation de ressource en base de donn�es
//    HtmlPageUsage usage = this.createUsageEntity(product,
//                                              this.getResourceDao().getById(storageId));
//    return this.createUsage(usage);
//  }
//
//  /**
//   * Cette m�thode permet de cr�er le stockage de page HTML d�finie en argument
//   * @param path {@inheritDoc}
//   * @param name {@inheritDoc}
//   * @param type {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws UserException {@inheritDoc}
//   * @see com.bid4win.commons.manager.resource.ResourceManagerInternal_#createResourceEntity(java.lang.String, java.lang.String, com.bid4win.commons.persistence.entity.resource.ResourceType)
//   */
//  @Override
//  protected HtmlPageStorage createResourceEntity(String path, String name, HtmlPageType type)
//            throws UserException
//  {
//    return new HtmlPageStorage(path, name, type);
//  }
//  /**
//   * Cette m�thode permet de cr�er une utilisation de page HTML simple
//   * @param path {@inheritDoc}
//   * @param name {@inheritDoc}
//   * @param storage {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws UserException {@inheritDoc}
//   * @see com.bid4win.commons.manager.resource.ResourceRepositoryManagerInternal_#createUsageEntity(java.lang.String, java.lang.String, com.bid4win.commons.persistence.entity.resource.ResourceStorage)
//   */
//  @Override
//  protected HtmlPageUsage createUsageEntity(String path, String name, HtmlPageStorage storage)
//            throws PersistenceException, UserException
//  {
//    try
//    {
//      return new HtmlPageUsage(path, name, storage);
//    }
//    catch(ModelArgumentException ex)
//    {
//      throw new PersistenceException(ex);
//    }
//  }
//  /**
//   * Cette m�thode permet de cr�er une utilisation de page HTML pour un produit
//   * @param product Produit utilisant la page HTML d�finie en argument
//   * @param storage Page HTML utilis�e par le produit d�fini en argument
//   * @return L'utilisation de page HTML cr��e
//   * @throws PersistenceException Si un probl�me emp�che la cr�ation du lien entre
//   * l'utilisation de page HTML et son stockage ou son produit
//   * @throws UserException Si le nom ou l'emplacement de stockage de l'utilisation
//   * de page HTML est invalide
//   */
//  protected HtmlPageUsage createUsageEntity(Product product, HtmlPageStorage storage)
//            throws PersistenceException, UserException
//  {
//    try
//    {
//      return new HtmlPageUsage(product, storage);
//    }
//    catch(ModelArgumentException ex)
//    {
//      throw new PersistenceException(ex);
//    }
//  }
//
//  /**
//   * Getter de la r�f�rence du DAO des stockages d'image
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.manager.resource.ResourceRepositoryManagerInternal_#getResourceDao()
//   */
//  @Override
//  protected HtmlPageStorageDao getResourceDao()
//  {
//    return this.resourceDao;
//  }
//  /**
//   * Getter de la r�f�rence du DAO des utilisations d'image
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.manager.resource.ResourceRepositoryManagerInternal_#getUsageDao()
//   */
//  @Override
//  protected HtmlPageUsageDao getUsageDao()
//  {
//    return this.usageDao;
//  }
//  /**
//   * Getter du magasin de gestion du stockage des images
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.manager.resource.ResourceManagerInternal_#getRepository()
//   */
//  @Override
//  protected HtmlPageRepository getRepository()
//  {
//    return this.repository;
//  }
//  /**
//   * Getter du magasin de gestion des utilisations d'image
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.manager.resource.ResourceRepositoryManagerInternal_#getStore()
//   */
//  @Override
//  protected HtmlPageUsageStore getStore()
//  {
//    return this.store;
//  }
//  /**
//   * Getter du manager interne de gestion des comptes utilisateur
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.manager.Bid4WinManagerInternal_#getAccountManager()
//   */
//  @Override
//  protected AccountAbstractManagerInternal_<Account> getAccountManager()
//  {
//    return this.accountManager;
//  }
//}
