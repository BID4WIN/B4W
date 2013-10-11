//package com.bid4win.manager.locale.html;
//
//import static org.junit.Assert.assertEquals;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.test.context.ContextConfiguration;
//
//import com.bid4win.commons.core.collection.Bid4WinList;
//import com.bid4win.commons.core.exception.Bid4WinException;
//import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
//import com.bid4win.persistence.dao.product.ProductInitializer;
//import com.bid4win.persistence.entity.locale.html.HtmlPageUsage;
//import com.bid4win.persistence.entity.product.Product;
//
///**
// *
// * TODO A COMMENTER<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//@RunWith(Bid4WinJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
//public class HtmlPageManagerProductTest extends HtmlPageManagerTester
//{
//  /** TODO A COMMENTER */
//  @Autowired
//  @Qualifier("HtmlPageManagerProductStub")
//  private HtmlPageManagerProductStub manager;
//  /** Référence TODO */
//  @Autowired
//  @Qualifier("ProductInitializer")
//  private ProductInitializer productInitializer;
//
//  /**
//   *
//   * TODO A COMMENTER
//   * @throws Exception {@inheritDoc}
//   * @see com.bid4win.commons.manager.resource.ResourceRepositoryManagerTester#testCreateUsage_String_String_long()
//   */
//  @Override
//  @Test
//  public void testCreateUsage_String_String_long() throws Exception
//  {
//    try
//    {
//      super.testCreateUsage_String_String_long();
//    }
//    catch(AssertionError ex)
//    {
//      // Cette exception n'est plus possible avec les images de produits car
//      // elles sont créées de façon à ne jamais produire de doublon
//      if(!ex.getMessage().equals("Should fail with already referenced usage"))
//      {
//        throw ex;
//      }
//    }
//    this.validateProduct(3, 0);
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @throws Exception TODO A COMMENTER
//   */
//  @Override
//  @Test
//  public void test_update_of_storage_and_usage() throws Exception
//  {
//    super.test_update_of_storage_and_usage();
//    this.validateProduct(3, 3);
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @throws Exception {@inheritDoc}
//   * @see com.bid4win.commons.manager.resource.ResourceRepositoryManagerTester#testDeleteUsage_long()
//   */
//  @Override
//  @Test
//  public void testDeleteUsage_long() throws Exception
//  {
//    super.testDeleteUsage_long();
//    this.validateProduct(4, 0);
//  }
//
//  /**
//   *
//   * TODO A COMMENTER
//   * @throws Bid4WinException TODO A COMMENTER
//   */
//  protected void validateProduct(int version, int updateForce) throws Bid4WinException
//  {
//    Product product = this.getProduct(0);
//    assertEquals("Wrong product version", version, product.getVersion());
//    assertEquals("Wrong product update forcing", updateForce, product.getUpdateForce());
//    Bid4WinList<HtmlPageUsage> list = this.getUsageDao().findAll();
//    list.sort();
//    assertEquals("Wrong image lists", list, product.getDescriptionList());
//  }
//
//  /**
//   *
//   * TODO A COMMENTER
//   * @param index TODO A COMMENTER
//   * @return TODO A COMMENTER
//   * @throws Bid4WinException TODO A COMMENTER
//   */
//  protected Product getProduct(int index) throws Bid4WinException
//  {
//    return this.getProductInitializer().getEntity(index);
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @return TODO A COMMENTER
//   */
//  protected ProductInitializer getProductInitializer()
//  {
//    return this.productInitializer;
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.manager.resource.ResourceRepositoryManagerTester#getManager()
//   */
//  @Override
//  public HtmlPageManagerProductStub getManager()
//  {
//    return this.manager;
//  }
//
//  /**
//   *
//   * TODO A COMMENTER
//   * @return TODO A COMMENTER
//   */
//  protected Product getTestProduct()
//  {
//    return this.getManager().getInternal().getTestProduct();
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @param product TODO A COMMENTER
//   */
//  protected void setTestProduct(Product product)
//  {
//    this.getManager().getInternal().setTestProduct(product);
//  }
//  /**
//   * Test setup method
//   * @throws Exception {@inheritDoc}
//   * @see com.bid4win.manager.image.ImageManagerTester#setUp()
//   */
//  @Override
//  @Before
//  public void setUp() throws Exception
//  {
//    super.setUp();
//    this.getProductInitializer().setUp(1);
//    this.setTestProduct(this.getProductInitializer().getEntity(0));
//  }
//  /**
//   * Test setup method
//   * @throws Exception {@inheritDoc}
//   * @see com.bid4win.manager.image.ImageManagerTester#tearDown()
//   */
//  @Override
//  @After
//  public void tearDown() throws Exception
//  {
//    super.tearDown();
//    this.getProductInitializer().tearDown();
//  }
//}
