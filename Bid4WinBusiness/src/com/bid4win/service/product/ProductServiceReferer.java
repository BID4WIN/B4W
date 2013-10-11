package com.bid4win.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.j2ee.Bid4WinSelfReferer;

/***
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("ProductServiceReferer")
@Scope("singleton")
public class ProductServiceReferer extends Bid4WinSelfReferer<ProductService>
{
  /**
   *
   * TODO A COMMENTER
   * @param bean {@inheritDoc}
   * @see com.bid4win.commons.j2ee.Bid4WinSelfReferer#setBean(com.bid4win.commons.j2ee.Bid4WinSelfReferencedBean)
   */
  @Override
  @Autowired
  @Qualifier("ProductService")
  public void setBean(ProductService bean)
  {
    super.setBean(bean);
  }
}
