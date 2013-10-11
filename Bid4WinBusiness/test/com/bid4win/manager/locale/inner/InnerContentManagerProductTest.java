package com.bid4win.manager.locale.inner;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.dao.product.ProductInitializer;
import com.bid4win.persistence.entity.product.Product;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
public class InnerContentManagerProductTest extends InnerContentManagerTester
{
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("InnerContentManagerProductStub")
  private InnerContentManagerProductStub manager;
  /** Référence TODO */
  @Autowired
  @Qualifier("ProductInitializer")
  private ProductInitializer productInitializer;

  /**
   *
   * TODO A COMMENTER
   * @param index TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  protected Product getProduct(int index) throws Bid4WinException
  {
    return this.getProductInitializer().getEntity(index);
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected ProductInitializer getProductInitializer()
  {
    return this.productInitializer;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceRepositoryManagerTester#getManager()
   */
  @Override
  public InnerContentManagerProductStub getManager()
  {
    return this.manager;
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected Product getTestProduct()
  {
    return this.getManager().getTestProduct();
  }
  /**
   *
   * TODO A COMMENTER
   * @param product TODO A COMMENTER
   */
  protected void setTestProduct(Product product)
  {
    this.getManager().setTestProduct(product);
  }
  /**
   *
   * TODO A COMMENTER
   * @throws Exception {@inheritDoc}
   * @see com.bid4win.service.resource.ResourceRepositoryServiceTester#setUp()
   */
  @Override
  @Before
  public void setUp() throws Exception
  {
    super.setUp();
    this.getProductInitializer().setUp(1);
    this.setTestProduct(this.getProductInitializer().getEntity(0));
  }
  /**
   *
   * TODO A COMMENTER
   * @throws Exception {@inheritDoc}
   * @see com.bid4win.commons.service.resource.ResourceRepositoryServiceTester#tearDown()
   */
  @Override
  @After
  public void tearDown() throws Exception
  {
    super.tearDown();
    this.getProductInitializer().tearDown();
  }
}
