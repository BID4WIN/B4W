package com.bid4win.persistence.entity.product;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.entity.Bid4WinEntityTester;
import com.bid4win.persistence.entity.image.ImageStorage;
import com.bid4win.persistence.entity.image.ImageUsage;
import com.bid4win.persistence.entity.locale.I18nGroup;
import com.bid4win.persistence.entity.locale.inner.InnerContentStorage;
import com.bid4win.persistence.entity.locale.inner.InnerContentUsage;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
public class ProductTest extends Bid4WinEntityTester
{
  /**
   * Test of defineNames(I18nGroup), of class Product.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testDefineNames_I18nGroup() throws Bid4WinException
  {
    Product product = this.getGenerator().createProduct(0);
    ImageStorage image = this.getGenerator().createImageStorage();
    InnerContentStorage description = this.getGenerator().createInnerContentStorage();
    Bid4WinSet<ImageUsage> imageSet = new Bid4WinSet<ImageUsage>();
    for(int i = 0 ; i < 5 ; i++)
    {
      imageSet.add(new ImageUsage(product, image));
    }
    for(ImageUsage usage : imageSet)
    {
      assertEquals("Bad resource name", product.getName() + "_" + usage.getPosition(),
                   usage.getName());
    }
    Bid4WinSet<InnerContentUsage> descriptionSet = new Bid4WinSet<InnerContentUsage>();
    for(int i = 0 ; i < 5 ; i++)
    {
      descriptionSet.add(new InnerContentUsage(product, description));
    }
    for(InnerContentUsage usage : descriptionSet)
    {
      assertEquals("Bad resource name", product.getName() + "_" + usage.getPosition(),
                   usage.getName());
    }
    I18nGroup i18nGroup = new I18nGroup("newName");
    product.defineNames(i18nGroup);
    assertEquals("Bad product names", i18nGroup, product.getNames());
    for(ImageUsage usage : imageSet)
    {
      assertEquals("Bad resource name", product.getName() + "_" + usage.getPosition(),
                   usage.getName());
    }
    for(InnerContentUsage usage : descriptionSet)
    {
      assertEquals("Bad resource name", product.getName() + "_" + usage.getPosition(),
                   usage.getName());
    }
  }
}
