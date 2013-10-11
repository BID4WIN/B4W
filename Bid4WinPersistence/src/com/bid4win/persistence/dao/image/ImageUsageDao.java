package com.bid4win.persistence.dao.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.dao.resource.ResourceUsageDao_;
import com.bid4win.persistence.dao.product.ProductDao;
import com.bid4win.persistence.entity.image.ImageStorage;
import com.bid4win.persistence.entity.image.ImageType;
import com.bid4win.persistence.entity.image.ImageUsage;
import com.bid4win.persistence.entity.image.UsageType;

/**
 * DAO pour les entités de la classe ImageUsage<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("ImageUsageDao")
@Scope("singleton")
public class ImageUsageDao extends ResourceUsageDao_<ImageUsage, ImageType, ImageStorage>
{
  /** Référence du DAO des stockages d'image */
  private ImageStorageDao storageDao;
  /** Référence du DAO des produits */
  private ProductDao productDao;

  /**
   * Constructeur
   */
  protected ImageUsageDao()
  {
    super(ImageUsage.class);
  }

  /**
   * Redéfini cette méthode afin d'ajouter la mise à jour de l'objet référençant
   * l'utilisation selon son type
   * @param usage {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.resource.ResourceUsageDao_#add(com.bid4win.commons.persistence.entity.resource.ResourceUsage)
   */
  @Override
  public ImageUsage add(ImageUsage usage) throws PersistenceException
  {
    // Ajoute l'utilisation d'image
    usage = super.add(usage);
    // Met à jour son produit
    if(usage.getUsageType().belongsTo(UsageType.PRODUCT))
    {
      this.getProductDao().update(usage.getProduct());
    }
    // Retourne l'utilisation d'image ajoutée
    return usage;
  }
  /**
   * Propage selon son type le forçage de modification de l'utilisation en paramètre
   * à l'objet la référençant pour qu'il prenne en compte que sa ressource associée
   * n'est plus à jour. Cette méthode est utilisée afin de noter que la ressource
   * associée à l'utilisation en argument a été modifiée
   * @param usage {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.resource.ResourceDao_#forceUpdate(com.bid4win.commons.persistence.entity.resource.Resource)
   */
  @Override
  public ImageUsage forceUpdate(ImageUsage usage) throws PersistenceException
  {
    if(usage.getUsageType().belongsTo(UsageType.PRODUCT))
    {
      // Propage le forçage de modification au produit de l'utilisation d'image
      this.getProductDao().forceUpdate(usage.getProduct());
    }
    // Force la modification de l'utilisation d'image
    return super.forceUpdate(usage);
  }
  /**
   * Redéfini cette méthode afin d'ajouter la mise à jour de l'objet référençant
   * l'utilisation selon son type
   * @param usage {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.resource.ResourceUsageDao_#remove(com.bid4win.commons.persistence.entity.resource.ResourceUsage)
   */
  @Override
  public ImageUsage remove(ImageUsage usage) throws PersistenceException
  {
    if(usage.getUsageType().belongsTo(UsageType.PRODUCT))
    {
      try
      {
        // Met à jour son produit
        this.getProductDao().update(usage.unlinkFromProduct());
      }
      catch(UserException ex)
      {
        throw new PersistenceException(ex);
      }
    }
    // Supprime l'utilisation d'image en paramètre
    return super.remove(usage);
  }

  /**
   * Getter du DAO des stockages d'image
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.resource.ResourceUsageDao_#getStorageDao()
   */
  @Override
  public ImageStorageDao getStorageDao()
  {
    return this.storageDao;
  }
  /**
   * Setter du DAO des stockages d'image
   * @param storageDao DAO des stockages d'image à positionner
   * @throws ModelArgumentException Si un DAO est déjà positionné
   */
  @Autowired
  @Qualifier("ImageStorageDao")
  public void setStorageDao(ImageStorageDao storageDao) throws ModelArgumentException
  {
    UtilObject.checkNull("storageDao", this.getStorageDao());
    this.storageDao = storageDao;
  }
  /**
   * Getter du DAO des produits
   * @return Le DAO des produits
   */
  public ProductDao getProductDao()
  {
    return this.productDao;
  }
  /**
   * Setter du DAO des produits
   * @param productDao DAO des produits à positionner
   * @throws ModelArgumentException Si un DAO est déjà positionné
   */
  @Autowired
  @Qualifier("ProductDao")
  public void setProductDao(ProductDao productDao) throws ModelArgumentException
  {
    UtilObject.checkNull("productDao", this.getProductDao());
    this.productDao = productDao;
  }
}
