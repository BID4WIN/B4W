package com.bid4win.persistence.dao.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.persistence.dao.Bid4winTestInitializer_;
import com.bid4win.persistence.entity.product.Product;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("ProductInitializer")
@Scope("singleton")
public class ProductInitializer extends Bid4winTestInitializer_<Product, String>
{
  /** Référence du DAO des produits */
  @Autowired
  @Qualifier("ProductDaoStub")
  private ProductDaoStub dao;

  /**
   *
   * TODO A COMMENTER
   * @param index {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4winTestInitializer_#createEntity(int)
   */
  @Override
  protected Product createEntity(int index) throws Bid4WinException
  {
    return this.getGenerator().createProduct(index);
  }
  /**
   * Getter du DAO des produits
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4winTestInitializer_#getDao()
   */
  @Override
  protected ProductDaoStub getDao()
  {
    return this.dao;
  }
}
