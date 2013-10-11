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
 * Manager de gestion des produits et de leurs ressources associ�es incluant la
 * gestion m�tier compl�te<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Component("ProductManager")
@Scope("singleton")
public class ProductManager extends Bid4WinManager_<Account, ProductManager>
{
  /** R�f�rence du DAO des produits */
  @Autowired
  @Qualifier("ProductDao")
  private ProductDao productDao = null;
  /** R�f�rence du manager de gestion des images */
  @Autowired
  @Qualifier("ImageManager")
  private ImageManager imageManager = null;
  /** R�f�rence du manager de gestion des contenus */
  @Autowired
  @Qualifier("InnerContentManager")
  private InnerContentManager innerContentManager = null;

  /**
   * Cette m�thode permet de r�cup�rer le produit en fonction de son identifiant.
   * Le chargement dans le magasin appropri� des ses utilisations de ressource
   * sera v�rifi� et effectu� le cas �ch�ant
   * @param id Identifiant du produit � r�cup�rer
   * @return Le produit r�cup�r� li� � ses utilisations de ressource
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun produit n'a pu �tre r�cup�r� avec
   * l'identifiant en argument
   * @throws UserException Si l'emplacement d�fini par l'une des utilisations de
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
   * Cette m�thode permet de r�cup�rer des produits en fonction de leur identifiant.
   * Le chargement dans le magasin appropri� de leurs utilisations de ressource
   * sera v�rifi� et effectu� le cas �ch�ant
   * @param idSet Identifiants des produits � r�cup�rer
   * @return Les produits r�cup�r�s li� � leurs utilisations de ressource
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si l'emplacement d�fini par l'une des utilisations de
   * ressource est invalide
   */
  public Bid4WinMap<String, Product> loadProductList(Bid4WinSet<String> idSet)
         throws PersistenceException, UserException
  {
    // R�cup�re les produits � charger
    Bid4WinMap<String, Product> result = this.getProductDao().findById(idSet);
    // S'assure du chargement de toutes les ressources dans le magasin appropri�
    this.loadResourceList(result.values());
    // Retourne les produits charg�s
    return result;
  }
  /**
   * Cette m�thode permet de v�rifier et de charger dans le magasin appropri� le
   * cas �ch�ant les utilisations de ressource des produits en argument
   * @param products Produits dont le chargement dans le magasin appropri�
   * des utilisations de ressource sera v�rifi� et effectu� le cas �ch�ant
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si l'emplacement d�fini par l'une des utilisations de
   * ressource est invalide
   */
  public void loadResourceList(Collection<Product> products)
         throws PersistenceException, UserException
  {
    // S'assure du chargement de toutes les ressources dans le magasin appropri�.
    this.loadImageList(products);
    this.loadDescriptionList(products);
  }
  /**
   * Cette m�thode permet de v�rifier et de charger dans le magasin appropri� le
   * cas �ch�ant les images des produits en argument
   * @param products Produits dont le chargement dans le magasin appropri� des images
   * sera v�rifi� et effectu� le cas �ch�ant
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si l'emplacement d�fini par l'une des images est invalide
   */
  public void loadImageList(Collection<Product> products)
         throws PersistenceException, UserException
  {
    Bid4WinList<ImageUsage> imageList = new Bid4WinList<ImageUsage>();
    // R�cup�re toutes les images r�f�renc�es
    for(Product product : products)
    {
      imageList.addAll(product.getImageList());
    }
    // S'assure du chargement de toutes les images dans le magasin appropri�.
    // Celles-ci sont tri�es de mani�re � �viter tout dead-lock
    for(ImageUsage usage : imageList.sort())
    {
      this.getImageManager().loadUsageInStore(usage);
    }
  }
  /**
   * Cette m�thode permet de v�rifier et de charger dans le magasin appropri� le
   * cas �ch�ant les descriptions des produits en argument
   * @param products Produits dont le chargement dans le magasin appropri� des descriptions
   * sera v�rifi� et effectu� le cas �ch�ant
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si l'emplacement d�fini par l'une des descriptions est
   * invalide
   */
  public void loadDescriptionList(Collection<Product> products)
         throws PersistenceException, UserException
  {
    Bid4WinList<InnerContentUsage> descriptionList = new Bid4WinList<InnerContentUsage>();
    // R�cup�re toutes les descriptions r�f�renc�es
    for(Product product : products)
    {
      descriptionList.addAll(product.getDescriptionList());
    }
    // S'assure du chargement de toutes les descriptions dans le magasin appropri�.
    // Celles-ci sont tri�es de mani�re � �viter tout dead-lock
    for(InnerContentUsage usage : descriptionList.sort())
    {
      this.getInnerContentManager().loadUsageInStore(usage);
    }
  }

   /**
   * Cette fonction permet de r�cup�rer la liste des produits existants
   * @return La liste des produits existants
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  public Bid4WinList<Product> getProductList() throws PersistenceException
  {
    return this.getProductDao().findAll();
  }
  /**
   * Cette m�thode permet de cr�er le produit d�fini en argument
   * @param reference R�f�rence du produit
   * @param names Nom du produit dans diff�rentes langues dont celle d�finie par
   * d�faut
   * @param summaries R�sum� de la description du produit dans diff�rentes langues
   * dont celle d�finie par d�faut
   * @param price Prix du produit dans diff�rentes monnaies dont celle d�finie par
   * d�faut
   * @return Le produit cr��
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si les param�tres du produit sont invalides
   */
  public Product createProduct(String reference, I18nGroup names,
                               I18nGroup summaries, Price price)
         throws PersistenceException, UserException
  {
    return this.getProductDao().add(new Product(reference, names, summaries, price));
  }
  /**
   * Cette m�thode permet de modifier le produit d�fini par l'identifiant en argument
   * @param id Identifiant du produit � modifier
   * @param reference Nouvelle r�f�rence du produit
   * @param names Nouveau nom du produit dans diff�rentes langues dont celle d�finie
   * par d�faut
   * @param summaries R�sum� de la description du produit dans diff�rentes langues
   * dont celle d�finie par d�faut
   * @param price Nouveau prix du produit dans diff�rentes monnaies dont celle
   * d�finie par d�faut
   * @return Le produit modifi�
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun produit n'a pu �tre r�cup�r� avec
   * l'identifiant en argument
   * @throws UserException Si les param�tres du produit sont invalides
   */
  public Product updateProduct(String id, String reference, I18nGroup names,
                               I18nGroup summaries, Price price)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    // R�cup�re le produit
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
   * Cette m�thode permet de supprimer le produit d�fini par l'identifiant en argument
   * @param id Identifiant du produit � supprimer
   * @return Le produit supprim�
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun produit n'a pu �tre r�cup�r� avec
   * l'identifiant en argument
   */
  public Product deleteProduct(String id) throws PersistenceException, NotFoundEntityException
  {
    // R�cup�re le produit
    Product product = this.getProductDao().getById(id);
    // Supprime le produit
    return this.getProductDao().remove(product);
  }
  /**
   * Cette m�thode permet d'ajouter une image � un produit
   * @param productId Identifiant du produit sur lequel ajouter l'image
   * @param storageId Identifiant du stockage sur lequel se basera l'image du produit
   * @return Le produit modifi�
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun produit ou aucun stockage d'image
   * n'a pu �tre r�cup�r� avec les identifiants en argument
   * @throws UserException Si l'emplacement d�fini par l'image est invalide
   */
  public Product addImage(String productId, long storageId)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    // R�cup�re le produit
    Product product = this.getProductDao().getById(productId);
    // Cr�e et ajoute l'utilisation d'image au produit et le retourne
    return this.getImageManager().createUsage(product, storageId).getProduct();
  }
  /**
   * Modifie la position d'une image parmi celles d'un produit
   * @param productId Identifiant du produit de l'image � d�placer
   * @param oldPosition Ancienne position de l'image parmi celles du produit
   * @param newPosition Nouvelle position de l'image parmi celles du produit
   * @return Le produit modifi�
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun produit n'a pu �tre r�cup�r� avec
   * l'identifiants en argument
   * @throws UserException Si les positions en argument ne sont pas valides
   */
  public Product moveImage(String productId, int oldPosition, int newPosition)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    // R�cup�re le produit
    Product product = this.getProductDao().getById(productId);
    // La modification en elle m�me peut entra�ner une violation temporaire des
    // contraintes. Il faut donc passer par une �tape interm�diaire
    this.prepareImageOrdering(product, Math.min(oldPosition, newPosition));
    // �tabli l'ordre d�finitif
    product.orderImage(oldPosition, newPosition);
    // Modifie le produit et le retourne
    return this.getProductDao().forceUpdate(product);
  }
  /**
   * Cette m�thode permet de retirer une image d'un produit
   * @param productId Identifiant du produit duquel retirer l'image
   * @param usageId Identifiant de l'image � retirer
   * @return Le produit modifi�
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun produit n'a pu �tre r�cup�r� avec
   * l'identifiants en argument
   * @throws UserException Si l'identifiant de l'image � retirer n'est pas r�f�renc�
   * par le produit
   */
  public Product removeImage(String productId, long usageId)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    // R�cup�re le produit
    Product product = this.getProductDao().getById(productId);
    // R�cup�re l'utilisation d'image � supprimer
    ImageUsage usage = product.getImage(usageId);
    // La modification en elle m�me peut entra�ner une violation temporaire des
    // contraintes. Il faut donc passer par une �tape interm�diaire
    this.prepareImageOrdering(product, usage.getPosition());
    // Supprime l'utilisation d'image
    this.getImageManager().deleteUsage(usage);
    // Modifie le produit et le retourne
    return this.getProductDao().update(product);
  }
  /**
   * Cette m�thode permet d'ajouter une description � un produit
   * @param productId Identifiant du produit sur lequel ajouter la description
   * @param storageId Identifiant du stockage sur lequel se basera la description
   * du produit
   * @return Le produit modifi�
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun produit ou aucun stockage de contenu
   * n'a pu �tre r�cup�r� avec les identifiants en argument
   * @throws UserException Si l'emplacement d�fini par la description est invalide
   */
  public Product addDescription(String productId, long storageId)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    // R�cup�re le produit
    Product product = this.getProductDao().getById(productId);
    // Cr�e et ajoute l'utilisation de contenu au produit et le retourne
    return this.getInnerContentManager().createUsage(product, storageId).getProduct();
  }
  /**
   * Modifie la position d'une description parmi celles d'un produit
   * @param productId Identifiant du produit de la description � d�placer
   * @param oldPosition Ancienne position de la description parmi celles du produit
   * @param newPosition Nouvelle position de la description parmi celles du produit
   * @return Le produit modifi�
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun produit n'a pu �tre r�cup�r� avec
   * l'identifiants en argument
   * @throws UserException Si les positions en argument ne sont pas valides
   */
  public Product moveDescription(String productId, int oldPosition, int newPosition)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    // R�cup�re le produit
    Product product = this.getProductDao().getById(productId);
    // La modification en elle m�me peut entra�ner une violation temporaire des
    // contraintes. Il faut donc passer par une �tape interm�diaire
    this.prepareDescriptionOrdering(product, Math.min(oldPosition, newPosition));
    // �tabli l'ordre d�finitif
    product.orderDescription(oldPosition, newPosition);
    // Modifie le produit et le retourne
    return this.getProductDao().forceUpdate(product);
  }
  /**
   * Cette m�thode permet de retirer une description d'un produit
   * @param productId Identifiant du produit duquel retirer la description
   * @param usageId Identifiant de la description � retirer
   * @return Le produit modifi�
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun produit n'a pu �tre r�cup�r� avec
   * l'identifiants en argument
   * @throws UserException Si l'identifiant donn� pour la description � retirer
   * n'est pas r�f�renc� par le produit
   */
  public Product removeDescription(String productId, long usageId)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    // R�cup�re le produit
    Product product = this.getProductDao().getById(productId);
    // R�cup�re l'utilisation de contenu � supprimer
    InnerContentUsage usage = product.getDescription(usageId);
    // La modification en elle m�me peut entra�ner une violation temporaire des
    // contraintes. Il faut donc passer par une �tape interm�diaire
    this.prepareDescriptionOrdering(product, usage.getPosition());
    // Supprime l'utilisation de contenu
    this.getInnerContentManager().deleteUsage(usage);
    // Modifie le produit et le retourne
    return this.getProductDao().update(product);
  }

  /**
   * L'ordonnancement d'une liste d'images peut, lors de l'encha�nement des modifications
   * en base de donn�es entra�ner une violation des contraintes (image dont la
   * position passe de 0 � 1 et inversement). Cette m�thode permet donc de passer
   * par une �tape interm�diaire qui emp�chera cela d'arriver
   * @param product Produit dont on souhaite pr�parer l'ordonnancement de la liste
   * d'images
   * @param index Index � partir duquel l'ordonnancement des images a un impact
   * @return Le produit pr�par�
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
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
   * L'ordonnancement d'une liste de contenus peut, lors de l'encha�nement des
   * modifications en base de donn�es, entra�ner une violation des contraintes
   * (contenu dont la position passe de 0 � 1 et inversement). Cette m�thode permet
   * donc de passer par une �tape interm�diaire qui emp�chera cela d'arriver
   * @param product Produit dont on souhaite pr�parer l'ordonnancement de la liste
   * de contenus
   * @param index Index � partir duquel l'ordonnancement des contenus a un impact
   * @return Le produit pr�par�
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
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
   * Red�fini la recherche d'entit�s en fonction de leur identifiant afin d'
   * ajouter dans le cas des produits la r�cup�ration des ressources associ�es
   * � leurs utilisations d'image
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
   * Getter de la r�f�rence du DAO des produits
   * @return La r�f�rence du DAO des produits
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
