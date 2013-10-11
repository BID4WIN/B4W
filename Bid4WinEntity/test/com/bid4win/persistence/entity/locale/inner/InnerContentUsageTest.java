package com.bid4win.persistence.entity.locale.inner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.resource.ResourceUsageMultiPartTester;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.entity.EntityGenerator;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.locale.Language;
import com.bid4win.persistence.entity.locale.resource.InnerContent;
import com.bid4win.persistence.entity.product.Product;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
public class InnerContentUsageTest
       extends ResourceUsageMultiPartTester<InnerContentUsage, InnerContentType, InnerContentStorage,
                                            Language, InnerContent, Account, EntityGenerator>
{
  /**
   * Test of  ImageUsage(...) method, of class ImageUsage.
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.account.AccountBasedEntityTester#testConstructor_etc()
   */
  @Override
  @Test
  public void testConstructor_etc() throws Bid4WinException
  {
    super.testConstructor_etc();

    String path = "path";
    String name = "name";
    InnerContentStorage storage = this.createStorage(this.getType());
    try
    {
      InnerContentUsage usage = new InnerContentUsage(path, name, storage);
      assertEquals("Bad resource path", path, usage.getPath());
      assertEquals("Bad resource name", name, usage.getName());
      assertEquals("Bad resource type", storage.getType(), usage.getType());
      assertTrue("Bad resource storage", storage == usage.getStorage());
      assertTrue("Bad usage type", UsageType.SIMPLE == usage.getUsageType());
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Instanciation should not fail: " + ex.getMessage());
    }

    Product product = this.getGenerator().createProduct();
    try
    {
      InnerContentUsage instance1 = new InnerContentUsage(product, storage);
      assertEquals("Bad resource path", product.getId(), instance1.getPath());
      assertEquals("Bad resource name", product.getName() + "_" + instance1.getPosition(),
                                        instance1.getName());
      assertEquals("Bad resource type", storage.getType(), instance1.getType());
      assertTrue("Bad resource storage", storage == instance1.getStorage());
      assertTrue("Bad usage type", UsageType.PRODUCT == instance1.getUsageType());
      assertTrue("Bad product", product == instance1.getProduct());
      assertTrue("Bad product link", product.getDescriptionList().contains(instance1));
      assertEquals("Bad position", 0, instance1.getPosition());

      InnerContentUsage instance2 = new InnerContentUsage(product, storage);
      assertEquals("Bad resource path", product.getId(), instance2.getPath());
      assertEquals("Bad resource name", product.getName() + "_" + instance2.getPosition(),
                                        instance2.getName());
      assertEquals("Bad resource type", storage.getType(), instance2.getType());
      assertTrue("Bad resource storage", storage == instance2.getStorage());
      assertTrue("Bad usage type", UsageType.PRODUCT == instance2.getUsageType());
      assertTrue("Bad product", product == instance2.getProduct());
      assertTrue("Bad product link", product.getDescriptionList().contains(instance1));
      assertTrue("Bad product link", product.getDescriptionList().contains(instance2));
      assertEquals("Bad position", 0, instance1.getPosition());
      assertEquals("Bad position", 1, instance2.getPosition());
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Instanciation should not fail: " + ex.getMessage());
    }
  }
  /**
   * Test of definePosition(int), of class InnerContentUsage.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testDefinePosition_int() throws Bid4WinException
  {
    InnerContentStorage storage = this.createStorage(this.getType());
    String path = "path";
    String name = "name";
    InnerContentUsage instance = new InnerContentUsage(path, name, storage);
    assertEquals("Bad position", 0, instance.getPosition());
    instance.definePosition(1);
    assertEquals("Bad position", 0, instance.getPosition());
    Product product = this.getGenerator().createProduct();
    instance = new InnerContentUsage(product, storage);
    assertEquals("Bad position", 0, instance.getPosition());
    assertEquals("Bad resource name", product.getName() + "_" + instance.getPosition(),
                                      instance.getName());
    instance.definePosition(1);
    assertEquals("Bad position", 1, instance.getPosition());
    assertEquals("Bad resource name", product.getName() + "_" + instance.getPosition(),
                                      instance.getName());
  }
  /**
   * Test of unlinkFromProduct(), of class InnerContentUsage.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testUnlinkFromProduct_0args() throws Bid4WinException
  {
    InnerContentStorage storage = this.createStorage(this.getType());
    Product product = this.getGenerator().createProduct();
    InnerContentUsage usage = new InnerContentUsage(product, storage);
    assertTrue("Wrong product", product == usage.getProduct());
    assertTrue("Wrong product link", product.getDescriptionList().contains(usage));

    Product result = usage.unlinkFromProduct();
    assertNull("Wrong product", usage.getProduct());
    assertFalse("Wrong product link", product.getDescriptionList().contains(usage));
    assertTrue("Wrong result", product == result);
    try
    {
      usage.unlinkFromProduct();
      fail("Second unlink should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   *
   * TODO A COMMENTER
   * @param path {@inheritDoc}
   * @param name {@inheritDoc}
   * @param storage {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @throws ModelArgumentException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourceUsageTester#createResource(java.lang.String, java.lang.String, com.bid4win.commons.persistence.entity.resource.ResourceStorage)
   */
  @Override
  protected InnerContentUsage createResource(String path, String name, InnerContentStorage storage) throws UserException, ModelArgumentException
  {
    return new InnerContentUsage(path, name, storage);
  }
  /**
   *
   * TODO A COMMENTER
   * @param type {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourceUsageTester#createStorage(com.bid4win.commons.persistence.entity.resource.ResourceType)
   */
  @Override
  protected InnerContentStorage createStorage(InnerContentType type) throws UserException
  {
    return new InnerContentStorage("1", "2", type);
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourceTester#getType()
   */
  @Override
  protected InnerContentType getType()
  {
    return InnerContentType.XML;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourceUsageMultiPartTester#getPartType()
   */
  @Override
  protected Language getPartType()
  {
    return Language.ITALIAN;
  }
}
