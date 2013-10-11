package com.bid4win.manager.product;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.manager.Bid4WinManager_;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.manager.image.ImageManager;
import com.bid4win.manager.locale.inner.InnerContentManager;
import com.bid4win.persistence.dao.product.ProductDao;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.image.ImageUsage;
import com.bid4win.persistence.entity.locale.I18nGroup;
import com.bid4win.persistence.entity.locale.inner.InnerContentUsage;
import com.bid4win.persistence.entity.price.Price;
import com.bid4win.persistence.entity.product.Product;

/**
 * Manager de gestion des produits et de leurs ressources associées incluant la
 * gestion métier complète<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("ProductManager")
@Scope("singleton")
public class ProductManager extends Bid4WinManager_<Account, ProductManager>
{
  /** Référence du DAO des produits */
  @Autowired
  @Qualifier("ProductDao")
  private ProductDao productDao = null;
  /** Référence du manager de gestion des images */
  @Autowired
  @Qualifier("ImageManager")
  private ImageManager imageManager = null;
  /** Référence du manager de gestion des contenus */
  @Autowired
  @Qualifier("InnerContentManager")
  private InnerContentManager innerContentManager = null;

  /**
   * Cette méthode permet de récupérer le produit en fonction de son identifiant.
   * Le chargement dans le magasin approprié des ses utilisations de ressource
   * sera vérifié et effectué le cas échéant
   * @param id Identifiant du produit à récupérer
   * @return Le produit récupéré lié à ses utilisations de ressource
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun produit n'a pu être récupéré avec
   * l'identifiant en argument
   * @throws UserException Si l'emplacement défini par l'une des utilisations de
   * ressource est invalide
   */
  public Product loadProduct(String id)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    Product product = this.getProductDao().getById(id);
    this.loadResourceList(new Bid4WinSet<Product>(product));
    return product;
  }
  /**
   * Cette méthode permet de récupérer des produits en fonction de leur identifiant.
   * Le chargement dans le magasin approprié de leurs utilisations de ressource
   * sera vérifié et effectué le cas échéant
   * @param idSet Identifiants des produits à récupérer
   * @return Les produits récupérés lié à leurs utilisations de ressource
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si l'emplacement défini par l'une des utilisations de
   * ressource est invalide
   */
  public Bid4WinMap<String, Product> loadProductList(Bid4WinSet<String> idSet)
         throws PersistenceException, UserException
  {
    // Récupère les produits à charger
    Bid4WinMap<String, Product> result = this.getProductDao().findById(idSet);
    // S'assure du chargement de toutes les ressources dans le magasin approprié
    this.loadResourceList(result.values());
    // Retourne les produits chargés
    return result;
  }
  /**
   * Cette méthode permet de vérifier et de charger dans le magasin approprié le
   * cas échéant les utilisations de ressource des produits en argument
   * @param products Produits dont le chargement dans le magasin approprié
   * des utilisations de ressource sera vérifié et effectué le cas échéant
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si l'emplacement défini par l'une des utilisations de
   * ressource est invalide
   */
  public void loadResourceList(Collection<Product> products)
         throws PersistenceException, UserException
  {
    // S'assure du chargement de toutes les ressources dans le magasin approprié.
    this.loadImageList(products);
    this.loadDescriptionList(products);
  }
  /**
   * Cette méthode permet de vérifier et de charger dans le magasin approprié le
   * cas échéant les images des produits en argument
   * @param products Produits dont le chargement dans le magasin approprié des images
   * sera vérifié et effectué le cas échéant
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si l'emplacement défini par l'une des images est invalide
   */
  public void loadImageList(Collection<Product> products)
         throws PersistenceException, UserException
  {
    Bid4WinList<ImageUsage> imageList = new Bid4WinList<ImageUsage>();
    // Récupère toutes les images référencées
    for(Product product : products)
    {
      imageList.addAll(product.getImageList());
    }
    // S'assure du chargement de toutes les images dans le magasin approprié.
    // Celles-ci sont triées de manière à éviter tout dead-lock
    for(ImageUsage usage : imageList.sort())
    {
      this.getImageManager().loadUsageInStore(usage);
    }
  }
  /**
   * Cette méthode permet de vérifier et de charger dans le magasin approprié le
   * cas échéant les descriptions des produits en argument
   * @param products Produits dont le chargement dans le magasin approprié des descriptions
   * sera vérifié et effectué le cas échéant
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si l'emplacement défini par l'une des descriptions est
   * invalide
   */
  public void loadDescriptionList(Collection<Product> products)
         throws PersistenceException, UserException
  {
    Bid4WinList<InnerContentUsage> descriptionList = new Bid4WinList<InnerContentUsage>();
    // Récupère toutes les descriptions référencées
    for(Product product : products)
    {
      descriptionList.addAll(product.getDescriptionList());
    }
    // S'assure du chargement de toutes les descriptions dans le magasin approprié.
    // Celles-ci sont triées de manière à éviter tout dead-lock
    for(InnerContentUsage usage : descriptionList.sort())
    {
      this.getInnerContentManager().loadUsageInStore(usage);
    }
  }

   /**
   * Cette fonction permet de récupérer la liste des produits existants
   * @return La liste des produits existants
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  public Bid4WinList<Product> getProductList() throws PersistenceException
  {
    return this.getProductDao().findAll();
  }
  /**
   * Cette méthode permet de créer le produit défini en argument
   * @param reference Référence du produit
   * @param names Nom du produit dans différentes langues dont celle définie par
   * défaut
   * @param summaries Résumé de la description du produit dans différentes langues
   * dont celle définie par défaut
   * @param price Prix du produit dans différentes monnaies dont celle définie par
   * défaut
   * @return Le produit créé
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si les paramètres du produit sont invalides
   */
  public Product createProduct(String reference, I18nGroup names,
                               I18nGroup summaries, Price price)
         throws PersistenceException, UserException
  {
    return this.getProductDao().add(new Product(reference, names, summaries, price));
  }
  /**
   * Cette méthode permet de modifier le produit défini par l'identifiant en argument
   * @param id Identifiant du produit à modifier
   * @param reference Nouvelle référence du produit
   * @param names Nouveau nom du produit dans différentes langues dont celle définie
   * par défaut
   * @param summaries Résumé de la description du produit dans différentes langues
   * dont celle définie par défaut
   * @param price Nouveau prix du produit dans différentes monnaies dont celle
   * définie par défaut
   * @return Le produit modifié
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun produit n'a pu être récupéré avec
   * l'identifiant en argument
   * @throws UserException Si les paramètres du produit sont invalides
   */
  public Product updateProduct(String id, String reference, I18nGroup names,
                               I18nGroup summaries, Price price)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    // Récupère le produit
    Product product = this.getProductDao().getById(id);
    // Modifie le produit
    product.defineReference(reference);
    product.defineNames(names);
    product.defineSummaries(summaries);
    product.definePrice(price);
    // Modifie le produit
    return this.getProductDao().update(product);
  }
  /**
   * Cette méthode permet de supprimer le produit défini par l'identifiant en argument
   * @param id Identifiant du produit à supprimer
   * @return Le produit supprimé
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun produit n'a pu être récupéré avec
   * l'identifiant en argument
   */
  public Product deleteProduct(String id) throws PersistenceException, NotFoundEntityException
  {
    // Récupère le produit
    Product product = this.getProductDao().getById(id);
    // Supprime le produit
    return this.getProductDao().remove(product);
  }
  /**
   * Cette méthode permet d'ajouter une image à un produit
   * @param productId Identifiant du produit sur lequel ajouter l'image
   * @param storageId Identifiant du stockage sur lequel se basera l'image du produit
   * @return Le produit modifié
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun produit ou aucun stockage d'image
   * n'a pu être récupéré avec les identifiants en argument
   * @throws UserException Si l'emplacement défini par l'image est invalide
   */
  public Product addImage(String productId, long storageId)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    // Récupère le produit
    Product product = this.getProductDao().getById(productId);
    // Crée et ajoute l'utilisation d'image au produit et le retourne
    return this.getImageManager().createUsage(product, storageId).getProduct();
  }
  /**
   * Modifie la position d'une image parmi celles d'un produit
   * @param productId Identifiant du produit de l'image à déplacer
   * @param oldPosition Ancienne position de l'image parmi celles du produit
   * @param newPosition Nouvelle position de l'image parmi celles du produit
   * @return Le produit modifié
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun produit n'a pu être récupéré avec
   * l'identifiants en argument
   * @throws UserException Si les positions en argument ne sont pas valides
   */
  public Product moveImage(String productId, int oldPosition, int newPosition)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    // Récupère le produit
    Product product = this.getProductDao().getById(productId);
    // La modification en elle même peut entraîner une violation temporaire des
    // contraintes. Il faut donc passer par une étape intermédiaire
    this.prepareImageOrdering(product, Math.min(oldPosition, newPosition));
    // Établi l'ordre définitif
    product.orderImage(oldPosition, newPosition);
    // Modifie le produit et le retourne
    return this.getProductDao().forceUpdate(product);
  }
  /**
   * Cette méthode permet de retirer une image d'un produit
   * @param productId Identifiant du produit duquel retirer l'image
   * @param usageId Identifiant de l'image à retirer
   * @return Le produit modifié
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun produit n'a pu être récupéré avec
   * l'identifiants en argument
   * @throws UserException Si l'identifiant de l'image à retirer n'est pas référencé
   * par le produit
   */
  public Product removeImage(String productId, long usageId)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    // Récupère le produit
    Product product = this.getProductDao().getById(productId);
    // Récupère l'utilisation d'image à supprimer
    ImageUsage usage = product.getImage(usageId);
    // La modification en elle même peut entraîner une violation temporaire des
    // contraintes. Il faut donc passer par une étape intermédiaire
    this.prepareImageOrdering(product, usage.getPosition());
    // Supprime l'utilisation d'image
    this.getImageManager().deleteUsage(usage);
    // Modifie le produit et le retourne
    return this.getProductDao().update(product);
  }
  /**
   * Cette méthode permet d'ajouter une description à un produit
   * @param productId Identifiant du produit sur lequel ajouter la description
   * @param storageId Identifiant du stockage sur lequel se basera la description
   * du produit
   * @return Le produit modifié
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun produit ou aucun stockage de contenu
   * n'a pu être récupéré avec les identifiants en argument
   * @throws UserException Si l'emplacement défini par la description est invalide
   */
  public Product addDescription(String productId, long storageId)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    // Récupère le produit
    Product product = this.getProductDao().getById(productId);
    // Crée et ajoute l'utilisation de contenu au produit et le retourne
    return this.getInnerContentManager().createUsage(product, storageId).getProduct();
  }
  /**
   * Modifie la position d'une description parmi celles d'un produit
   * @param productId Identifiant du produit de la description à déplacer
   * @param oldPosition Ancienne position de la description parmi celles du produit
   * @param newPosition Nouvelle position de la description parmi celles du produit
   * @return Le produit modifié
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun produit n'a pu être récupéré avec
   * l'identifiants en argument
   * @throws UserException Si les positions en argument ne sont pas valides
   */
  public Product moveDescription(String productId, int oldPosition, int newPosition)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    // Récupère le produit
    Product product = this.getProductDao().getById(productId);
    // La modification en elle même peut entraîner une violation temporaire des
    // contraintes. Il faut donc passer par une étape intermédiaire
    this.prepareDescriptionOrdering(product, Math.min(oldPosition, newPosition));
    // Établi l'ordre définitif
    product.orderDescription(oldPosition, newPosition);
    // Modifie le produit et le retourne
    return this.getProductDao().forceUpdate(product);
  }
  /**
   * Cette méthode permet de retirer une description d'un produit
   * @param productId Identifiant du produit duquel retirer la description
   * @param usageId Identifiant de la description à retirer
   * @return Le produit modifié
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun produit n'a pu être récupéré avec
   * l'identifiants en argument
   * @throws UserException Si l'identifiant donné pour la description à retirer
   * n'est pas référencé par le produit
   */
  public Product removeDescription(String productId, long usageId)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    // Récupère le produit
    Product product = this.getProductDao().getById(productId);
    // Récupère l'utilisation de contenu à supprimer
    InnerContentUsage usage = product.getDescription(usageId);
    // La modification en elle même peut entraîner une violation temporaire des
    // contraintes. Il faut donc passer par une étape intermédiaire
    this.prepareDescriptionOrdering(product, usage.getPosition());
    // Supprime l'utilisation de contenu
    this.getInnerContentManager().deleteUsage(usage);
    // Modifie le produit et le retourne
    return this.getProductDao().update(product);
  }

  /**
   * L'ordonnancement d'une liste d'images peut, lors de l'enchaînement des modifications
   * en base de données entraîner une violation des contraintes (image dont la
   * position passe de 0 à 1 et inversement). Cette méthode permet donc de passer
   * par une étape intermédiaire qui empêchera cela d'arriver
   * @param product Produit dont on souhaite préparer l'ordonnancement de la liste
   * d'images
   * @param index Index à partir duquel l'ordonnancement des images a un impact
   * @return Le produit préparé
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  protected Product prepareImageOrdering(Product product, int index) throws PersistenceException
  {
    for(ImageUsage usage : product.getImageList())
    {
      if(usage.getPosition() >= index)
      {
        usage.definePosition(usage.getPosition() + product.getImageNb());
      }
    }
    // Modifie le produit temporairement
    product = this.getProductDao().update(product);
    this.getProductDao().flush();
    return product;
  }
  /**
   * L'ordonnancement d'une liste de contenus peut, lors de l'enchaînement des
   * modifications en base de données, entraîner une violation des contraintes
   * (contenu dont la position passe de 0 à 1 et inversement). Cette méthode permet
   * donc de passer par une étape intermédiaire qui empêchera cela d'arriver
   * @param product Produit dont on souhaite préparer l'ordonnancement de la liste
   * de contenus
   * @param index Index à partir duquel l'ordonnancement des contenus a un impact
   * @return Le produit préparé
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  protected Product prepareDescriptionOrdering(Product product, int index) throws PersistenceException
  {
    for(InnerContentUsage usage : product.getDescriptionList())
    {
      if(usage.getPosition() >= index)
      {
        usage.definePosition(usage.getPosition() + product.getDescriptionNb());
      }
    }
    // Modifie le produit temporairement
    product = this.getProductDao().update(product);
    this.getProductDao().flush();
    return product;
  }

  /**
   * Redéfini la recherche d'entités en fonction de leur identifiant afin d'
   * ajouter dans le cas des produits la récupération des ressources associées
   * à leurs utilisations d'image
   * @param <ENTITY> {@inheritDoc}
   * @param <ID> {@inheritDoc}
   * @param entityClass {@inheritDoc}
   * @param idSet {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.manager.Bid4WinManager_#findById(java.lang.Class, com.bid4win.commons.core.collection.Bid4WinSet)
   */
  @SuppressWarnings("unchecked")
  @Override
  public <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
         Bid4WinMap<ID, ENTITY> findById(Class<ENTITY> entityClass, Bid4WinSet<ID> idSet)
         throws PersistenceException
  {
    if(entityClass.equals(this.getProductDao().getEntityClass()))
    {
      try
      {
        return (Bid4WinMap<ID, ENTITY>)this.loadProductList((Bid4WinSet<String>)idSet);
      }
      catch(UserException ex)
      {
        throw new PersistenceException(ex);
      }
    }
    return super.findById(entityClass, idSet);
  }

  /**
   * Getter de la référence du DAO des produits
   * @return La référence du DAO des produits
   */
  protected ProductDao getProductDao()
  {
    return this.productDao;
  }
  /**
   * Getter du manager de gestion des images
   * @return Le manager de gestion des images
   */
  protected ImageManager getImageManager()
  {
    return this.imageManager;
  }
  /**
   * Getter du manager de gestion des contenus
   * @return Le manager de gestion des contenus
   */
  protected InnerContentManager getInnerContentManager()
  {
    return this.innerContentManager;
  }
}
