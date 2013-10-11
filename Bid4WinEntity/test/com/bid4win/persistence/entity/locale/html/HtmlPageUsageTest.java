//package com.bid4win.persistence.entity.locale.html;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertNull;
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//
//import com.bid4win.commons.core.exception.Bid4WinException;
//import com.bid4win.commons.core.exception.UserException;
//import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
//import com.bid4win.persistence.entity.Bid4WinEntityTester;
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
//public class HtmlPageUsageTest extends Bid4WinEntityTester
//{
//  /**
//   * Test of HtmlPageUsage(String, String, HtmlPageStorage), of class HtmlPageUsage.
//   * @throws Bid4WinException Issue not expected during this test
//   */
//  @Test
//  public void testHtmlPageUsage_String_String_HtmlPageStorage() throws Bid4WinException
//  {
//    String path = "path";
//    String name = "name";
//    HtmlPageStorage storage = this.getGenerator().createHtmlPageStorage();
//    try
//    {
//      HtmlPageUsage instance = new HtmlPageUsage(path, name, storage);
//      assertEquals("Bad resource path", path, instance.getPath());
//      assertEquals("Bad resource name", name, instance.getName());
//      assertEquals("Bad resource type", storage.getType(), instance.getType());
//      assertTrue("Bad resource storage", storage == instance.getStorage());
//      assertTrue("Bad usage type", UsageType.SIMPLE == instance.getUsageType());
//    }
//    catch(UserException ex)
//    {
//      ex.printStackTrace();
//      fail("Instanciation should not fail: " + ex.getMessage());
//    }
//  }
//  /**
//   * Test of HtmlPageUsage(Product, HtmlPageStorage), of class HtmlPageUsage.
//   * @throws Bid4WinException Issue not expected during this test
//   */
//  @Test
//  public void testHtmlPageUsage_Product_HtmlPageStorage() throws Bid4WinException
//  {
//    HtmlPageStorage storage = this.getGenerator().createHtmlPageStorage();
//    Product product = this.getGenerator().createProduct();
//    try
//    {
//      HtmlPageUsage instance1 = new HtmlPageUsage(product, storage);
//      assertEquals("Bad resource path", product.getId(), instance1.getPath());
//      assertEquals("Bad resource name", product.getName() + "_" + instance1.getPosition(),
//                                        instance1.getName());
//      assertEquals("Bad resource type", storage.getType(), instance1.getType());
//      assertTrue("Bad resource storage", storage == instance1.getStorage());
//      assertTrue("Bad usage type", UsageType.PRODUCT == instance1.getUsageType());
//      assertTrue("Bad product", product == instance1.getProduct());
//      assertTrue("Bad product link", product.getDescriptionList().contains(instance1));
//      assertEquals("Bad position", 0, instance1.getPosition());
//
//      HtmlPageUsage instance2 = new HtmlPageUsage(product, storage);
//      assertEquals("Bad resource path", product.getId(), instance2.getPath());
//      assertEquals("Bad resource name", product.getName() + "_" + instance2.getPosition(),
//                                        instance2.getName());
//      assertEquals("Bad resource type", storage.getType(), instance2.getType());
//      assertTrue("Bad resource storage", storage == instance2.getStorage());
//      assertTrue("Bad usage type", UsageType.PRODUCT == instance2.getUsageType());
//      assertTrue("Bad product", product == instance2.getProduct());
//      assertTrue("Bad product link", product.getDescriptionList().contains(instance1));
//      assertTrue("Bad product link", product.getDescriptionList().contains(instance2));
//      assertEquals("Bad position", 0, instance1.getPosition());
//      assertEquals("Bad position", 1, instance2.getPosition());
//    }
//    catch(UserException ex)
//    {
//      ex.printStackTrace();
//      fail("Instanciation should not fail: " + ex.getMessage());
//    }
//  }
//  /**
//   * Test of definePosition(int), of class HtmlPageUsage.
//   * @throws Bid4WinException Issue not expected during this test
//   */
//  @Test
//  public void testDefinePosition_int() throws Bid4WinException
//  {
//    HtmlPageStorage storage = this.getGenerator().createHtmlPageStorage();
//    String path = "path";
//    String name = "name";
//    HtmlPageUsage instance = new HtmlPageUsage(path, name, storage);
//    assertEquals("Bad position", 0, instance.getPosition());
//    instance.definePosition(1);
//    assertEquals("Bad position", 0, instance.getPosition());
//    Product product = this.getGenerator().createProduct();
//    instance = new HtmlPageUsage(product, storage);
//    assertEquals("Bad position", 0, instance.getPosition());
//    assertEquals("Bad resource name", product.getName() + "_" + instance.getPosition(),
//                                      instance.getName());
//    instance.definePosition(1);
//    assertEquals("Bad position", 1, instance.getPosition());
//    assertEquals("Bad resource name", product.getName() + "_" + instance.getPosition(),
//                                      instance.getName());
//  }
//  /**
//   * Test of unlinkFromProduct(), of class HtmlPageUsage.
//   * @throws Bid4WinException Issue not expected during this test
//   */
//  @Test
//  public void testUnlinkFromProduct_0args() throws Bid4WinException
//  {
//    HtmlPageStorage storage = this.getGenerator().createHtmlPageStorage();
//    Product product = this.getGenerator().createProduct();
//    HtmlPageUsage usage = new HtmlPageUsage(product, storage);
//    assertTrue("Wrong product", product == usage.getProduct());
//    assertTrue("Wrong product link", product.getDescriptionList().contains(usage));
//
//    Product result = usage.unlinkFromProduct();
//    assertNull("Wrong product", usage.getProduct());
//    assertFalse("Wrong product link", product.getDescriptionList().contains(usage));
//    assertTrue("Wrong result", product == result);
//    try
//    {
//      usage.unlinkFromProduct();
//      fail("Second unlink should fail");
//    }
//    catch(UserException ex)
//    {
//      System.out.println(ex.getMessage());
//    }
//  }
//}
