package com.bid4win.persistence.dao.locale.inner;

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
import com.bid4win.persistence.entity.locale.inner.InnerContentStorage;
import com.bid4win.persistence.entity.locale.inner.InnerContentType;
import com.bid4win.persistence.entity.locale.inner.InnerContentUsage;
import com.bid4win.persistence.entity.locale.inner.UsageType;

/**
 * DAO pour les entit�s de la classe HtmlPageUsage<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Component("InnerContentUsageDao")
@Scope("singleton")
public class InnerContentUsageDao extends ResourceUsageDao_<InnerContentUsage, InnerContentType, InnerContentStorage>
{
  /** R�f�rence du DAO des stockages de contenu */
  private InnerContentStorageDao storageDao;
  /** R�f�rence du DAO des produits */
  private ProductDao productDao;

  /**
   * Constructeur
   */
  protected InnerContentUsageDao()
  {
    super(InnerContentUsage.class);
  }

  /**
   * Red�fini cette m�thode afin d'ajouter la mise � jour de l'objet r�f�ren�ant
   * l'utilisation selon son type
   * @param usage {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.resource.ResourceUsageDao_#add(com.bid4win.commons.persistence.entity.resource.ResourceUsage)
   */
  @Override
  public InnerContentUsage add(InnerContentUsage usage) throws PersistenceException
  {
    // Ajoute l'utilisation de contenu
    usage = super.add(usage);
    // Met � jour son produit
    if(usage.getUsageType().belongsTo(UsageType.PRODUCT))
    {
      this.getProductDao().update(usage.getProduct());
    }
    // Retourne l'utilisation de contenu ajout�e
    return usage;
  }
  /**
   * Propage selon son type le for�age de modification de l'utilisation en param�tre
   * � l'objet la r�f�ren�ant pour qu'il prenne en compte que sa ressource associ�e
   * n'est plus � jour. Cette m�thode est utilis�e afin de noter que la ressource
   * associ�e � l'utilisation en argument a �t� modifi�e
   * @param usage {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.resource.ResourceDao_#forceUpdate(com.bid4win.commons.persistence.entity.resource.Resource)
   */
  @Override
  public InnerContentUsage forceUpdate(InnerContentUsage usage) throws PersistenceException
  {
    if(usage.getUsageType().belongsTo(UsageType.PRODUCT))
    {
      // Propage le for�age de modification au produit de l'utilisation de contenu
      this.getProductDao().forceUpdate(usage.getProduct());
    }
    // Force la modification de l'utilisation de contenu
    return super.forceUpdate(usage);
  }
  /**
   * Red�fini cette m�thode afin d'ajouter la mise � jour de l'objet r�f�ren�ant
   * l'utilisation selon son type
   * @param usage {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.resource.ResourceUsageDao_#remove(com.bid4win.commons.persistence.entity.resource.ResourceUsage)
   */
  @Override
  public InnerContentUsage remove(InnerContentUsage usage) throws PersistenceException
  {
    if(usage.getUsageType().belongsTo(UsageType.PRODUCT))
    {
      try
      {
        // Met � jour son produit
        this.getProductDao().update(usage.unlinkFromProduct());
      }
      catch(UserException ex)
      {
        throw new PersistenceException(ex);
      }
    }
    // Supprime l'utilisation de contenu en param�tre
    return super.remove(usage);
  }

  /**
   * Getter du DAO des stockages de contenu
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.resource.ResourceUsageDao_#getStorageDao()
   */
  @Override
  public InnerContentStorageDao getStorageDao()
  {
    return this.storageDao;
  }
  /**
   * Setter du DAO des stockages de contenu
   * @param storageDao DAO des stockages de contenu � positionner
   * @throws ModelArgumentException Si un DAO est d�j� positionn�
   */
  @Autowired
  @Qualifier("InnerContentStorageDao")
  public void setStorageDao(InnerContentStorageDao storageDao) throws ModelArgumentException
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
   * @param productDao DAO des produits � positionner
   * @throws ModelArgumentException Si un DAO est d�j� positionn�
   */
  @Autowired
  @Qualifier("ProductDao")
  public void setProductDao(ProductDao productDao) throws ModelArgumentException
  {
    UtilObject.checkNull("productDao", this.getProductDao());
    this.productDao = productDao;
  }
}
