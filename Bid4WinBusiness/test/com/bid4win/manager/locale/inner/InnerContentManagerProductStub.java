package com.bid4win.manager.locale.inner;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.persistence.dao.product.ProductDaoStub;
import com.bid4win.persistence.entity.locale.inner.InnerContentStorage;
import com.bid4win.persistence.entity.locale.inner.InnerContentUsage;
import com.bid4win.persistence.entity.product.Product;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("InnerContentManagerProductStub")
@Scope("singleton")
public class InnerContentManagerProductStub extends InnerContentManagerStub
{
  /** Produit à utiliser pour les tests */
  private Product testProduct = null;
  /** Référence TODO */
  @Autowired
  @Qualifier("ProductDaoStub")
  private ProductDaoStub productDao;

  /**
   *
   * TODO A COMMENTER
   * @param id {@inheritDoc}
   * @param path {@inheritDoc}
   * @param name {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws NotFoundEntityException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceRepositoryManager_#moveUsage(long, java.lang.String, java.lang.String)
   */
  @Override
  public InnerContentUsage moveUsage(long id, String path, String name)
      throws PersistenceException, NotFoundEntityException, UserException
  {
    InnerContentUsage usage = this.getUsage(id);
    usage.definePosition(usage.getProduct().getDescriptionNb() + new Random(new Date().getTime()).nextInt());
    return this.getUsageDao().update(usage);
  }

  /**
   *
   * TODO A COMMENTER
   * @param path {@inheritDoc}
   * @param name {@inheritDoc}
   * @param storage {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.manager.locale.inner.InnerContentManager#createUsageEntity(java.lang.String, java.lang.String, com.bid4win.persistence.entity.locale.inner.InnerContentStorage)
   */
  @Override
  protected InnerContentUsage createUsageEntity(String path, String name, InnerContentStorage storage)
            throws PersistenceException, UserException
  {
    try
    {
      this.setTestProduct(this.getProductDao().getById(this.getTestProduct().getId()));
      return new InnerContentUsage(this.getTestProduct(), storage);
    }
    catch(ModelArgumentException ex)
    {
      throw new PersistenceException(ex);
    }
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public Product getTestProduct()
  {
    return this.testProduct;
  }
  /**
   *
   * TODO A COMMENTER
   * @param testProduct TODO A COMMENTER
   */
  public void setTestProduct(Product testProduct)
  {
    this.testProduct = testProduct;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public ProductDaoStub getProductDao()
  {
    return this.productDao;
  }
}
