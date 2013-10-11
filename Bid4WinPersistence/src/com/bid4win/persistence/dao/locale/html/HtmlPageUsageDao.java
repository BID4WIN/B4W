//package com.bid4win.persistence.dao.locale.html;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//
//import com.bid4win.commons.core.UtilObject;
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.commons.core.exception.PersistenceException;
//import com.bid4win.commons.core.exception.UserException;
//import com.bid4win.commons.persistence.dao.resource.ResourceUsageDao_;
//import com.bid4win.persistence.dao.product.ProductDao;
//import com.bid4win.persistence.entity.locale.html.HtmlPageStorage;
//import com.bid4win.persistence.entity.locale.html.HtmlPageType;
//import com.bid4win.persistence.entity.locale.html.HtmlPageUsage;
//import com.bid4win.persistence.entity.locale.html.UsageType;
//
///**
// * DAO pour les entit�s de la classe HtmlPageUsage<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
//@Component("HtmlPageUsageDao")
//@Scope("singleton")
//public class HtmlPageUsageDao extends ResourceUsageDao_<HtmlPageUsage, HtmlPageType, HtmlPageStorage>
//{
//  /** R�f�rence du DAO des stockages de page HTML */
//  private HtmlPageStorageDao storageDao;
//  /** R�f�rence du DAO des produits */
//  private ProductDao productDao;
//
//  /**
//   * Constructeur
//   */
//  protected HtmlPageUsageDao()
//  {
//    super(HtmlPageUsage.class);
//  }
//
//  /**
//   * Red�fini cette m�thode afin d'ajouter la mise � jour de l'objet r�f�ren�ant
//   * l'utilisation selon son type
//   * @param usage {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.resource.ResourceUsageDao_#add(com.bid4win.commons.persistence.entity.resource.ResourceUsage)
//   */
//  @Override
//  public HtmlPageUsage add(HtmlPageUsage usage) throws PersistenceException
//  {
//    // Ajoute l'utilisation de page HTML
//    usage = super.add(usage);
//    // Met � jour son produit
//    if(usage.getUsageType().belongsTo(UsageType.PRODUCT))
//    {
//      this.getProductDao().update(usage.getProduct());
//    }
//    // Retourne l'utilisation d'image ajout�e
//    return usage;
//  }
//  /**
//   * Propage selon son type le for�age de modification de l'utilisation en param�tre
//   * � l'objet la r�f�ren�ant pour qu'il prenne en compte que sa ressource associ�e
//   * n'est plus � jour. Cette m�thode est utilis�e afin de noter que la ressource
//   * associ�e � l'utilisation en argument a �t� modifi�e
//   * @param usage {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.resource.ResourceDao_#forceUpdate(com.bid4win.commons.persistence.entity.resource.Resource)
//   */
//  @Override
//  public HtmlPageUsage forceUpdate(HtmlPageUsage usage) throws PersistenceException
//  {
//    if(usage.getUsageType().belongsTo(UsageType.PRODUCT))
//    {
//      // Propage le for�age de modification au produit de l'utilisation d'image
//      this.getProductDao().forceUpdate(usage.getProduct());
//    }
//    // Force la modification de l'utilisation de page HTML
//    return super.forceUpdate(usage);
//  }
//  /**
//   * Red�fini cette m�thode afin d'ajouter la mise � jour de l'objet r�f�ren�ant
//   * l'utilisation selon son type
//   * @param usage {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.resource.ResourceUsageDao_#remove(com.bid4win.commons.persistence.entity.resource.ResourceUsage)
//   */
//  @Override
//  public HtmlPageUsage remove(HtmlPageUsage usage) throws PersistenceException
//  {
//    if(usage.getUsageType().belongsTo(UsageType.PRODUCT))
//    {
//      try
//      {
//        // Met � jour son produit
//        this.getProductDao().update(usage.unlinkFromProduct());
//      }
//      catch(UserException ex)
//      {
//        throw new PersistenceException(ex);
//      }
//    }
//    // Supprime l'utilisation de page HTML en param�tre
//    return super.remove(usage);
//  }
//
//  /**
//   * Getter du DAO des stockages de page HTML
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.resource.ResourceUsageDao_#getStorageDao()
//   */
//  @Override
//  public HtmlPageStorageDao getStorageDao()
//  {
//    return this.storageDao;
//  }
//  /**
//   * Setter du DAO des stockages de page HTML
//   * @param storageDao DAO des stockages de page HTML � positionner
//   * @throws ModelArgumentException Si un DAO est d�j� positionn�
//   */
//  @Autowired
//  @Qualifier("HtmlPageStorageDao")
//  public void setStorageDao(HtmlPageStorageDao storageDao) throws ModelArgumentException
//  {
//    UtilObject.checkNull("storageDao", this.getStorageDao());
//    this.storageDao = storageDao;
//  }
//  /**
//   * Getter du DAO des produits
//   * @return Le DAO des produits
//   */
//  public ProductDao getProductDao()
//  {
//    return this.productDao;
//  }
//  /**
//   * Setter du DAO des produits
//   * @param productDao DAO des produits � positionner
//   * @throws ModelArgumentException Si un DAO est d�j� positionn�
//   */
//  @Autowired
//  @Qualifier("ProductDao")
//  public void setProductDao(ProductDao productDao) throws ModelArgumentException
//  {
//    UtilObject.checkNull("productDao", this.getProductDao());
//    this.productDao = productDao;
//  }
//}
