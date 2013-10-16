package com.bid4win.service.product;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.core.io.UtilFileTest;
import com.bid4win.commons.core.reference.MessageRef.ResourceRef;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.resource.UtilResource;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.manager.image.store.ImageRepository;
import com.bid4win.manager.image.store.ImageRepositoryValidator;
import com.bid4win.manager.image.store.ImageUsageLinkedValidator;
import com.bid4win.manager.image.store.ImageUsageStore;
import com.bid4win.manager.locale.inner.store.InnerContentRepository;
import com.bid4win.manager.locale.inner.store.InnerContentRepositoryValidator;
import com.bid4win.manager.locale.inner.store.InnerContentUsageLinkedValidator;
import com.bid4win.manager.locale.inner.store.InnerContentUsageStore;
import com.bid4win.persistence.dao.image.ImageStorageDaoStub;
import com.bid4win.persistence.dao.image.ImageUsageDaoStub;
import com.bid4win.persistence.dao.locale.inner.InnerContentStorageDaoStub;
import com.bid4win.persistence.dao.locale.inner.InnerContentUsageDaoStub;
import com.bid4win.persistence.dao.product.ProductDaoStub;
import com.bid4win.persistence.entity.image.ImageStorage;
import com.bid4win.persistence.entity.image.ImageType;
import com.bid4win.persistence.entity.image.ImageUsage;
import com.bid4win.persistence.entity.locale.I18nGroup;
import com.bid4win.persistence.entity.locale.inner.InnerContentStorage;
import com.bid4win.persistence.entity.locale.inner.InnerContentType;
import com.bid4win.persistence.entity.locale.inner.InnerContentUsage;
import com.bid4win.persistence.entity.price.Price;
import com.bid4win.persistence.entity.product.Product;
import com.bid4win.service.Bid4WinServiceTester;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner .class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
public class ProductServiceTest extends Bid4WinServiceTester<Product, String>
{
  /** Référence TODO */
  @Autowired
  @Qualifier("ProductDaoStub")
  private ProductDaoStub dao;
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("ImageStorageDaoStub")
  private ImageStorageDaoStub imageStorageDao;
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("ImageUsageDaoStub")
  private ImageUsageDaoStub imageUsageDao;
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("InnerContentStorageDaoStub")
  private InnerContentStorageDaoStub innerContentStorageDao;
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("InnerContentUsageDaoStub")
  private InnerContentUsageDaoStub innerContentUsageDao;
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("ProductService")
  private ProductService service;
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("ImageRepositoryValidator")
  private ImageRepositoryValidator imageStorageValidator;
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("ImageUsageLinkedValidator")
  private ImageUsageLinkedValidator imageUsageValidator;
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("InnerContentRepositoryValidator")
  private InnerContentRepositoryValidator innerContentStorageValidator;
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("InnerContentUsageLinkedValidator")
  private InnerContentUsageLinkedValidator innerContentUsageValidator;
  /** TODO A COMMENTER */
  private long imageStorageId;
  /** TODO A COMMENTER */
  private long innerContentStorageId;

  /**
   * Test of createProduct(String, I18nGroup, I18nGroup, Price), of class ProductManager.
   * @throws Exception Issue not expected during this test
   */
  @Test
  public void testCreateProduct_String_I18nGroup_I18nGroup_Price() throws Exception
  {
    // Validation de la vérification des droits administrateur
    this.checkAdminRestriction(
        "createProduct", String.class, I18nGroup.class, I18nGroup.class, Price.class);

    I18nGroup names = this.getGenerator().createI18nGroup(0);
    I18nGroup summaries = this.getGenerator().createI18nGroup(1);
    Price price = this.getGenerator().createPrice();
    Product expected = new Product("productReference", names, summaries, price);
    Product product1 = this.getService().createProduct(expected.getReference(),
                                                       expected.getNames(),
                                                       expected.getSummaries(),
                                                       expected.getPrice());
    this.checkVersion(0, product1);
    this.checkSame(expected, product1);
    this.checkNotIdentical(expected, product1);
    Product result = this.getDao().getById(product1.getId());
    this.checkIdentical(product1, result);

    // Test d'ajout d'un second produit
    Product product2 = this.getService().createProduct(expected.getReference(),
                                                       expected.getNames(),
                                                       expected.getSummaries(),
                                                       expected.getPrice());
    this.checkVersion(0, product2);
    this.checkSame(expected, product2);
    this.checkNotIdentical(expected, product2);
    result = this.getDao().getById(product2.getId());
    this.checkIdentical(product2, result);

    this.checkSame(product1, product2);
    this.checkNotIdentical(product1, product2);
  }
  /**
   * Test of updateProduct(String, String, I18nGroup, I18nGroup, Price), of class ProductManager.
   * @throws Exception Issue not expected during this test
   */
  @Test
  public void testUpdateProduct_String_String_I18nGroup_I18nGroup_Price() throws Exception
  {
    // Validation de la vérification des droits administrateur
    this.checkAdminRestriction("updateProduct", String.class, String.class,
                               I18nGroup.class, I18nGroup.class, Price.class);

    // Création de la situation de départ
    I18nGroup names = this.getGenerator().createI18nGroup(0);
    I18nGroup summaries = this.getGenerator().createI18nGroup(1);
    Price price = this.getGenerator().createPrice(0);
    Product expected = this.getService().createProduct(
        "productReference", names, summaries, price);
    this.getService().addImage(expected.getId(), this.getImageStorageId());
    this.getService().addImage(expected.getId(), this.getImageStorageId());
    this.getService().addImage(expected.getId(), this.getImageStorageId());
    this.getService().addDescription(expected.getId(), this.getInnerContentStorageId());
    this.getService().addDescription(expected.getId(), this.getInnerContentStorageId());
    this.getService().addDescription(expected.getId(), this.getInnerContentStorageId());
    expected = this.getService().addDescriptionWithResult(expected.getId(), this.getInnerContentStorageId());

    expected.defineReference(expected.getReference() + "2");
    expected.defineSummaries(this.getGenerator().createI18nGroup(2));
    expected.definePrice(this.getGenerator().createPrice(1));
    this.getService().updateProduct(
        expected.getId(), expected.getReference(), expected.getNames(),
        expected.getSummaries(), expected.getPrice());
    Product product1 = this.getById(expected.getId());

    this.checkSame(expected, product1);
    this.checkVersion(expected.getVersion() + 1, product1);
    Product result = this.getDao().getById(product1.getId());
    this.checkIdentical(product1, result);
    Bid4WinList<ImageUsage> imageList = this.getImageUsageDao().findAll();
    assertEquals("Wrong image nb", product1.getImageNb(), imageList.size());
    imageList.sort();
    assertEquals("Wrong image list", product1.getImageList(), imageList);
    for(int i = 0 ; i < expected.getImageNb() ; i++)
    {
      this.checkVersion("Wrong image version",
                        expected.getImageList().get(i).getVersion(),
                        imageList.get(i));
      assertEquals("Wrong image name", UtilResource.checkName(expected.getName(), ResourceRef.IMAGE) + "_" + i,
                   imageList.get(i).getName());
      this.checkVersion("Wrong storage version",
                        imageList.get(i).getStorage().getUsageList().size(),
                        imageList.get(i).getStorage());
    }
    Bid4WinList<InnerContentUsage> descriptionList = this.getInnerContentUsageDao().findAll();
    assertEquals("Wrong description nb", product1.getDescriptionNb(), descriptionList.size());
    descriptionList.sort();
    assertEquals("Wrong description list", product1.getDescriptionList(), descriptionList);
    for(int i = 0 ; i < expected.getImageNb() ; i++)
    {
      this.checkVersion("Wrong description version",
                        expected.getDescriptionList().get(i).getVersion(),
                        descriptionList.get(i));
      assertEquals("Wrong description name", UtilResource.checkName(expected.getName(), ResourceRef.RESOURCE) + "_" + i,
                   descriptionList.get(i).getName());
      this.checkVersion("Wrong storage version",
                        descriptionList.get(i).getStorage().getUsageList().size(),
                        descriptionList.get(i).getStorage());
    }
    // Test avec impact sur les utilisations
    expected.defineNames(this.getGenerator().createI18nGroup(2));
    this.getService().updateProduct(
        expected.getId(), expected.getReference(), expected.getNames(),
        expected.getSummaries(), expected.getPrice());
    Product product2 = this.getById(expected.getId());

    this.checkSame(expected, product2);
    this.checkVersion( expected.getVersion() + 2, product2);
    result = this.getDao().getById(product2.getId());
    this.checkIdentical(product2, result);
    imageList = this.getImageUsageDao().findAll();
    assertEquals("Wrong image nb", product2.getImageNb(), imageList.size());
    imageList.sort();
    assertEquals("Wrong image list", product2.getImageList(), imageList);
    for(int i = 0 ; i < expected.getImageNb() ; i++)
    {
      this.checkVersion("Wrong image version",
                        expected.getImageList().get(i).getVersion() + 1,
                        imageList.get(i));
      assertEquals("Wrong image name", UtilResource.checkName(expected.getName(), ResourceRef.IMAGE) + "_" + i,
                   imageList.get(i).getName());
      this.checkVersion("Wrong storage version",
                        imageList.get(i).getStorage().getUsageList().size(),
                        imageList.get(i).getStorage());
    }
    descriptionList = this.getInnerContentUsageDao().findAll();
    assertEquals("Wrong description nb", product2.getDescriptionNb(), descriptionList.size());
    imageList.sort();
    assertEquals("Wrong description list", product2.getDescriptionList(), descriptionList);
    for(int i = 0 ; i < expected.getImageNb() ; i++)
    {
      this.checkVersion("Wrong description version",
                        expected.getDescriptionList().get(i).getVersion() + 1,
                        descriptionList.get(i));
      assertEquals("Wrong description name", UtilResource.checkName(expected.getName(), ResourceRef.RESOURCE) + "_" + i,
                   descriptionList.get(i).getName());
      this.checkVersion("Wrong storage version",
                        descriptionList.get(i).getStorage().getUsageList().size(),
                        descriptionList.get(i).getStorage());
    }
  }
  /**
   * Test of deleteProduct(String), of class ProductManager.
   * @throws Exception Issue not expected during this test
   */
  @Test
  public void testDeleteProduct_String() throws Exception
  {
    // Validation de la vérification des droits administrateur
    this.checkAdminRestriction("deleteProduct", String.class);

    // Création de la situation de départ
    I18nGroup names = this.getGenerator().createI18nGroup(0);
    I18nGroup summaries = this.getGenerator().createI18nGroup(1);
    Price price = this.getGenerator().createPrice();
    Product expected = this.getService().createProduct(
        "productReference", names, summaries, price);
    expected = this.getService().addImageWithResult(expected.getId(), this.getImageStorageId());

    try
    {
      this.getService().deleteProduct(expected.getId());
      fail("Should fail with remaining image usage");
    }
    catch(RuntimeException ex)
    {
      System.out.println(ex.getMessage());
    }
    for(ImageUsage usage : expected.getImageList())
    {
      this.getImageUsageDao().removeById(usage.getId());
    }
    // Test avec succès
    this.getService().deleteProduct(expected.getId());
    assertNull("Should be removed", this.getDao().findById(expected.getId()));
    // Test avec un produit inexistant
    try
    {
      this.getService().deleteProduct(expected.getId());
      fail("Should fail with unexisting product");
    }
    catch(NotFoundEntityException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of addImage(String, long), of class ProductManager.
   * @throws Exception Issue not expected during this test
   */
  @Test
  public void testAddImage_String_long() throws Exception
  {
    // Validation de la vérification des droits administrateur
    this.checkAdminRestriction("addImage", String.class, long.class);

    // Création de la situation de départ
    I18nGroup names = this.getGenerator().createI18nGroup(0);
    I18nGroup summaries = this.getGenerator().createI18nGroup(1);
    Price price = this.getGenerator().createPrice();
    Product expected = this.getService().createProduct(
        "productReference", names, summaries, price);

    this.getService().addImage(expected.getId(), this.getImageStorageId());
    this.getService().addImage(expected.getId(), this.getImageStorageId());
    this.getService().addImage(expected.getId(), this.getImageStorageId());
    Product product1 = this.getById(expected.getId());

    this.checkVersion(expected.getVersion() + product1.getImageNb(), product1);
    assertEquals("Wrong image usage nb", 3, product1.getImageNb());
    Bid4WinList<ImageUsage> list1 = product1.getImageList();
    for(int i = 0 ; i < list1.size() ; i++)
    {
      ImageUsage usage = list1.get(i);
      this.checkVersion("Wrong image usage version", 1, usage);
      assertEquals("Wrong image usage path", product1.getId(), usage.getPath());
      assertEquals("Wrong image usage name", UtilResource.checkName(product1.getName(), ResourceRef.IMAGE) + "_" + i,
                   usage.getName());
      assertEquals("Wrong image usage position", i, usage.getPosition());
    }
    this.getService().addImage(expected.getId(), this.getImageStorageId());
    Product product2 = this.getById(expected.getId());
    this.checkVersion(expected.getVersion() + product2.getImageNb(), product2);
    assertEquals("Wrong image usage nb", 4, product2.getImageNb());
    Bid4WinList<ImageUsage> list2 = product1.getImageList();
    for(int i = 0 ; i < list1.size() ; i++)
    {
      assertEquals("Wrong image usage", list1.get(i), list2.get(i));
    }
    ImageUsage usage = list2.getLast();
    this.checkVersion("Wrong image usage version", 1, usage);
    assertEquals("Wrong image usage path", product2.getId(), usage.getPath());
    assertEquals("Wrong image usage name",
                 UtilResource.checkName(product2.getName(), ResourceRef.IMAGE) + "_" + (list2.size() - 1),
                 usage.getName());
    assertEquals("Wrong image usage position",
                 list2.size() - 1, usage.getPosition());
  }
  /**
   * Test of removeImage(String, long), of class ProductManager.
   * @throws Exception Issue not expected during this test
   */
  @Test
  public void testRemoveImage_String_long() throws Exception
  {
    // Validation de la vérification des droits administrateur
    this.checkAdminRestriction("removeImage", String.class, long.class);

    // Création de la situation de départ
    I18nGroup names = this.getGenerator().createI18nGroup(0);
    I18nGroup summaries = this.getGenerator().createI18nGroup(1);
    Price price = this.getGenerator().createPrice();
    Product expected = this.getService().createProduct(
        "productReference", names, summaries, price);
    this.getService().addImage(expected.getId(), this.getImageStorageId());
    this.getService().addImage(expected.getId(), this.getImageStorageId());
    this.getService().addImage(expected.getId(), this.getImageStorageId());
    expected = this.getService().addImageWithResult(expected.getId(), this.getImageStorageId());

    this.getService().removeImage(
        expected.getId(), expected.getImageList().get(1).getId());
    Product product = this.getById(expected.getId());

    this.checkVersion(expected.getVersion() + 1, product);
    Bid4WinList<ImageUsage> list = this.getImageUsageDao().findAll();
    assertEquals("Wrong usage nb", list.size(), expected.getImageNb() -1);
    assertEquals("Wrong usage list", list, product.getImageList());
    try
    {
      product.getImage(expected.getImageList().get(1).getId());
      fail("Should not be referenced anymore");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    for(int i = 0 ; i < product.getImageNb() ; i++)
    {
      ImageUsage usage = product.getImageList().get(i);
      assertEquals("Wrong image usage path", product.getId(), usage.getPath());
      assertEquals("Wrong image usage name", UtilResource.checkName(product.getName(), ResourceRef.IMAGE) + "_" + i,
                                             usage.getName());
      assertEquals("Wrong image usage position", i, usage.getPosition());
      if(usage.getPosition() == 0)
      {
        this.checkVersion("Wrong image usage version", 1, usage);
      }
      else
      {
        this.checkVersion("Wrong image usage version", 3, usage);
      }
    }
    try
    {
      this.getService().removeImage(
          expected.getId(), expected.getImageList().get(1).getId());
      fail("Should fail with unexisting image usage");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }

    for(int i = 0 ; i < product.getImageNb() ; i++)
    {
      ImageUsage usage = product.getImageList().getFirst();
      this.getService().removeImage(expected.getId(), usage.getId());
      product = this.getById(expected.getId());
      this.checkVersion(expected.getVersion() + 2 + i, product);
      list = this.getImageUsageDao().findAll();
      assertEquals("Wrong usage nb", list.size(), expected.getImageNb() - 2 - i);
      assertEquals("Wrong usage list", list, product.getImageList());
      int j = 0;
      for(ImageUsage usage2 : product.getImageList())
      {
        assertEquals("Wrong image usage path", product.getId(), usage2.getPath());
        assertEquals("Wrong image usage name", UtilResource.checkName(product.getName(), ResourceRef.IMAGE) + "_" + j,
                                               usage2.getName());
        assertEquals("Wrong image usage position", j, usage2.getPosition());
        this.checkVersion("Wrong image usage version", 3 + (i + 1)*2, usage2);
        j++;
      }
    }

    Product product2 = this.getService().createProduct(
        "productReference", names, summaries, price);
    product2 = this.getService().addImageWithResult(product2.getId(), this.getImageStorageId());
    try
    {
      this.getService().removeImage(
          product.getId(), product2.getImageList().getFirst().getId());
      fail("Should fail with unexisting image usage");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of addDescription(String, long), of class ProductManager.
   * @throws Exception Issue not expected during this test
   */
  @Test
  public void testAddDescriptionString_long() throws Exception
  {
    // Validation de la vérification des droits administrateur
    this.checkAdminRestriction("addDescription", String.class, long.class);

    // Création de la situation de départ
    I18nGroup names = this.getGenerator().createI18nGroup(0);
    I18nGroup summaries = this.getGenerator().createI18nGroup(1);
    Price price = this.getGenerator().createPrice();
    Product expected = this.getService().createProduct(
        "productReference", names, summaries, price);

    this.getService().addDescription(expected.getId(), this.getInnerContentStorageId());
    this.getService().addDescription(expected.getId(), this.getInnerContentStorageId());
    this.getService().addDescription(expected.getId(), this.getInnerContentStorageId());
    Product product1 = this.getById(expected.getId());

    this.checkVersion(expected.getVersion() + product1.getDescriptionNb(), product1);
    assertEquals("Wrong description nb", 3, product1.getDescriptionNb());
    Bid4WinList<InnerContentUsage> list1 = product1.getDescriptionList();
    for(int i = 0 ; i < list1.size() ; i++)
    {
      InnerContentUsage usage = list1.get(i);
      this.checkVersion("Wrong description version", 1, usage);
      assertEquals("Wrong description path", product1.getId(), usage.getPath());
      assertEquals("Wrong description name", UtilResource.checkName(product1.getName(), ResourceRef.RESOURCE) + "_" + i,
                   usage.getName());
      assertEquals("Wrong description position", i, usage.getPosition());
    }
    this.getService().addDescription(expected.getId(), this.getInnerContentStorageId());
    Product product2 = this.getById(expected.getId());

    this.checkVersion(expected.getVersion() + product2.getDescriptionNb(), product2);
    assertEquals("Wrong description nb", 4, product2.getDescriptionNb());
    Bid4WinList<InnerContentUsage> list2 = product1.getDescriptionList();
    for(int i = 0 ; i < list1.size() ; i++)
    {
      assertEquals("Wrong description", list1.get(i), list2.get(i));
    }
    InnerContentUsage usage = list2.getLast();
    this.checkVersion("Wrong description version", 1, usage);
    assertEquals("Wrong description path", product2.getId(), usage.getPath());
    assertEquals("Wrong description name",
                 UtilResource.checkName(product2.getName(), ResourceRef.RESOURCE) + "_" + (list2.size() - 1),
                 usage.getName());
    assertEquals("Wrong description position",
                 list2.size() - 1, usage.getPosition());
  }
  /**
   * Test of removeDescription(String, long), of class ProductManager.
   * @throws Exception Issue not expected during this test
   */
  @Test
  public void testRemoveDescription_String_long() throws Exception
  {
    // Validation de la vérification des droits administrateur
    this.checkAdminRestriction("removeDescription", String.class, long.class);

    // Création de la situation de départ
    I18nGroup names = this.getGenerator().createI18nGroup(0);
    I18nGroup summaries = this.getGenerator().createI18nGroup(1);
    Price price = this.getGenerator().createPrice();
    Product expected = this.getService().createProduct(
        "productReference", names, summaries, price);
    this.getService().addDescription(expected.getId(), this.getInnerContentStorageId());
    this.getService().addDescription(expected.getId(), this.getInnerContentStorageId());
    this.getService().addDescription(expected.getId(), this.getInnerContentStorageId());
    expected = this.getService().addDescriptionWithResult(expected.getId(), this.getInnerContentStorageId());

    this.getService().removeDescription(
        expected.getId(), expected.getDescriptionList().get(1).getId());
    Product product = this.getById(expected.getId());

    this.checkVersion(expected.getVersion() + 1, product);
    Bid4WinList<InnerContentUsage> list = this.getInnerContentUsageDao().findAll();
    assertEquals("Wrong usage nb", list.size(), expected.getDescriptionNb() -1);
    assertEquals("Wrong usage list", list, product.getDescriptionList());
    try
    {
      product.getDescription(expected.getDescriptionList().get(1).getId());
      fail("Should not be referenced anymore");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    for(int i = 0 ; i < product.getDescriptionNb() ; i++)
    {
      InnerContentUsage usage = product.getDescriptionList().get(i);
      assertEquals("Wrong description path", product.getId(), usage.getPath());
      assertEquals("Wrong description name", UtilResource.checkName(product.getName(), ResourceRef.RESOURCE) + "_" + i,
                                             usage.getName());
      assertEquals("Wrong description position", i, usage.getPosition());
      if(usage.getPosition() == 0)
      {
        this.checkVersion("Wrong image usage version", 1, usage);
      }
      else
      {
        this.checkVersion("Wrong image usage version", 3, usage);
      }
    }
    try
    {
      this.getService().removeDescription(
          expected.getId(), expected.getDescriptionList().get(1).getId());
      fail("Should fail with unexisting description");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }

    for(int i = 0 ; i < product.getDescriptionNb() ; i++)
    {
      InnerContentUsage usage = product.getDescriptionList().getFirst();
      this.getService().removeDescription(expected.getId(), usage.getId());
      product = this.getById(expected.getId());

      this.checkVersion(expected.getVersion() + 2 + i, product);
      list = this.getInnerContentUsageDao().findAll();
      assertEquals("Wrong usage nb", list.size(), expected.getDescriptionNb() - 2 - i);
      assertEquals("Wrong usage list", list, product.getDescriptionList());
      for(int j = 0 ; j < product.getDescriptionNb() ; j++)
      {
        InnerContentUsage usage2 = product.getDescriptionList().get(j);
        assertEquals("Wrong description path", product.getId(), usage2.getPath());
        assertEquals("Wrong description name", UtilResource.checkName(product.getName(), ResourceRef.RESOURCE) + "_" + j,
                                               usage2.getName());
        assertEquals("Wrong description position", j, usage2.getPosition());
        this.checkVersion("Wrong description version", 3 + (i + 1)*2, usage2);
      }
    }

    Product product2 = this.getService().createProduct(
        "productReference", names, summaries, price);
    product2 = this.getService().addDescriptionWithResult(product2.getId(), this.getInnerContentStorageId());
    try
    {
      this.getService().removeDescription(
          product.getId(), product2.getDescriptionList().getFirst().getId());
      fail("Should fail with unexisting description usage");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }

  /**
   *
   * TODO A COMMENTER
   * @throws Exception TODO A COMMENTER
   */
  @Test
  public void testGetProduct_String() throws Exception
  {
    fail("TO BO IMPLEMENTED");
    /*
    this.connectAccount(0);
    this.updateRole(0, this.getService().getAdminRole());
    File file1 = this.getTestFile(this.getFilename1());
    File file2 = this.getTestFile(this.getFilename2());
    InputStream inputStream = new FileInputStream(file1);
    // Création de la situation de départ
    Product product = this.getService().createProduct("reference", "name");
    ImageStorage storage1;
    try
    {
      storage1 = this.getService().createResource("storagePath", "storageName1", inputStream);
      product = this.getService().addImage(product.getId(), storage1.getId());
    }
    finally
    {
      inputStream.close();
    }
    // L'utilisation d'image ne doit pas encore exister dans le magasin
    ImageUsage usage1 = product.getImageList().getFirst();
    assertFalse("Usage should not exist in store", this.getStore().exists(usage1));
    // Récupère le produit
    product = this.getService().getProduct(product.getId());
    // L'utilisation d'image doit exister dans le magasin
    assertTrue("Usage should exist in store", this.getStore().exists(usage1));
    this.getUsageValidator().assertEquals(file1, usage1);

    // Ajout d'une utilisation d'image
    inputStream = new FileInputStream(file2);
    ImageStorage storage2;
    try
    {
      storage2 = this.getManager().createResource("storagePath", "storageName2", inputStream);
      product = this.getManager().addImage(product.getId(), storage2.getId());
    }
    finally
    {
      inputStream.close();
    }

    // L'ancienne utilisation d'image doit exister dans le magasin
    assertTrue("Usage should not be updated",
               usage1.identical(product.getImageList().getFirst(), new Bid4WinList<Bid4WinRelationNode>()));
    assertTrue("Usage should still exist in store", this.getStore().exists(usage1));
    this.getUsageValidator().assertEquals(file1, usage1);
    // La nouvelle utilisation d'image ne doit pas encore exister dans le magasin
    ImageUsage usage2 = product.getImageList().get(1);
    assertFalse("Usage should not exist in store", this.getStore().exists(usage2));
    // Récupère le produit
    product = this.getService().getProduct(product.getId());
    // La nouvelle utilisation d'image doit exister dans le magasin
    assertTrue("Usage should exist in store", this.getStore().exists(usage2));
    this.getUsageValidator().assertEquals(file2, usage2);

    // Modification du nom du produit
    product = this.getManager().updateProduct(product.getId(), product.getReference(), product.getName());

    // Les anciennes utilisations d'image doivent exister dans le magasin
    assertTrue("Usage should still exist in store", this.getStore().exists(usage1));
    this.getUsageValidator().assertEquals(file1, usage1);
    assertTrue("Usage should still exist in store", this.getStore().exists(usage2));
    this.getUsageValidator().assertEquals(file2, usage2);
    // Les nouvelles utilisations d'image ne doivent pas encore exister dans le magasin
    usage1 = product.getImageList().get(0);
    usage2 = product.getImageList().get(1);
    assertFalse("Usage should not exist in store", this.getStore().exists(usage1));
    assertFalse("Usage should not exist in store", this.getStore().exists(usage2));
    // Récupère le produit
    product = this.getService().getProduct(product.getId());
    // Les nouvelles utilisations d'image doivent exister dans le magasin
    assertTrue("Usage should exist in store", this.getStore().exists(usage1));
    this.getUsageValidator().assertEquals(file1, usage1);
    assertTrue("Usage should exist in store", this.getStore().exists(usage2));
    this.getUsageValidator().assertEquals(file2, usage2);

    // Modifie un stockage d'image
    inputStream = new FileInputStream(file2);
    try
    {
      storage1 = this.getManager().updateResource(storage1.getId(), inputStream);
      product = this.getProductDao().getById(product.getId());
    }
    finally
    {
      inputStream.close();
    }

    // Les anciennes utilisations d'image doivent exister dans le magasin
    assertTrue("Usage should not be updated",
               usage2.identical(product.getImageList().get(1), new Bid4WinList<Bid4WinRelationNode>()));
    assertTrue("Usage should still exist in store", this.getStore().exists(usage1));
    this.getUsageValidator().assertEquals(file1, usage1);
    assertTrue("Usage should still exist in store", this.getStore().exists(usage2));
    this.getUsageValidator().assertEquals(file2, usage2);
    // La nouvelle utilisation d'image ne doit pas encore exister dans le magasin
    usage1 = product.getImageList().getFirst();
    assertFalse("Usage should not exist in store", this.getStore().exists(usage1));
    // Récupère le produit
    product = this.getService().getProduct(product.getId());
    // La nouvelle utilisation d'image doit exister dans le magasin
    assertTrue("Usage should exist in store", this.getStore().exists(usage1));
    this.getUsageValidator().assertEquals(file2, usage1);*/
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  public String getTestResourcePath() throws UserException
  {
    return UtilFile.concatAbsolutePath(ResourceRef.RESOURCE,
                                       UtilFileTest.getProjectPath("Bid4WinManager"),
                                       "test" , "resources");
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  public String getTestPath() throws UserException
  {
    return UtilFile.concatAbsolutePath(
        ResourceRef.RESOURCE, this.getTestResourcePath(), "test");
  }
 /**
   *
   * TODO A COMMENTER
   * @param filename TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  public File getTestFile(String filename) throws UserException
  {
    return UtilFile.concatAbsolutePathToFile(
        ResourceRef.RESOURCE, this.getTestPath(), filename);
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDaoTester#getDao()
   */
  @Override
  protected ProductDaoStub getDao()
  {
    // TODO Auto-generated method stub
    return this.dao;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected ImageUsageDaoStub getImageUsageDao()
  {
    return this.imageUsageDao;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected ImageStorageDaoStub getImageStorageDao()
  {
    return this.imageStorageDao;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected InnerContentUsageDaoStub getInnerContentUsageDao()
  {
    return this.innerContentUsageDao;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected InnerContentStorageDaoStub getInnerContentStorageDao()
  {
    return this.innerContentStorageDao;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinServiceTester#getService()
   */
  @Override
  public ProductService getService()
  {
    return this.service;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected ImageRepository getImageRepository()
  {
    return this.getImageStorageValidator().getStore();
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected ImageUsageStore getImageStore()
  {
    return this.getImageUsageValidator().getStore();
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected ImageRepositoryValidator getImageStorageValidator()
  {
    return this.imageStorageValidator;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected ImageUsageLinkedValidator getImageUsageValidator()
  {
    return this.imageUsageValidator;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected InnerContentRepository getInnerContentRepository()
  {
    return this.getInnerContentStorageValidator().getStore();
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected InnerContentUsageStore getInnerContentStore()
  {
    return this.getInnerContentUsageValidator().getStore();
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected InnerContentRepositoryValidator getInnerContentStorageValidator()
  {
    return this.innerContentStorageValidator;
  }
 /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected InnerContentUsageLinkedValidator getInnerContentUsageValidator()
  {
    return this.innerContentUsageValidator;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  private long getImageStorageId()
  {
    return this.imageStorageId;
  }
  /**
   *
   * TODO A COMMENTER
   * @param storageId TODO A COMMENTER
   */
  private void setImageStorageId(long storageId)
  {
    this.imageStorageId = storageId;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  private long getInnerContentStorageId()
  {
    return this.innerContentStorageId;
  }
  /**
   *
   * TODO A COMMENTER
   * @param storageId TODO A COMMENTER
   */
  private void setInnerContentStorageId(long storageId)
  {
    this.innerContentStorageId = storageId;
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDaoTester#removeAll()
   */
  @Override
  public Bid4WinList<Product> removeAll() throws PersistenceException
  {
    try
    {
      this.getImageUsageValidator().cleanAll(this.getTestPath());
      this.getImageStorageValidator().cleanAll(this.getTestPath());
      this.getInnerContentUsageValidator().cleanAll(this.getTestPath());
      this.getInnerContentStorageValidator().cleanAll(this.getTestPath());
    }
    catch(PersistenceException ex)
    {
      throw ex;
    }
    catch(Bid4WinException ex)
    {
      throw new PersistenceException(ex);
    }
    this.getImageUsageDao().removeAll();
    this.getImageStorageDao().removeAll();
    this.getInnerContentUsageDao().removeAll();
    this.getInnerContentStorageDao().removeAll();
    return super.removeAll();
  }
  /**
   *
   * TODO A COMMENTER
   * @throws Exception {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinServiceTester#setUp()
   */
  @Override
  @Before
  public void setUp() throws Exception
  {
    this.getImageStorageValidator().setUpProperty();
    super.setUp();
    FileInputStream inputStream = new FileInputStream(this.getTestFile("image.jpg"));
    ImageStorage imageStorage = new ImageStorage("testPath", "testName", ImageType.JPG);
    this.setImageStorageId(this.getImageStorageDao().add(imageStorage).getId());
    this.getImageRepository().store(inputStream, imageStorage);
    inputStream = new FileInputStream(this.getTestFile("image.jpg"));
    InnerContentStorage innerContentStorage = new InnerContentStorage("testPath", "testName", InnerContentType.JSP);
    this.setInnerContentStorageId(this.getInnerContentStorageDao().add(innerContentStorage).getId());
    this.getInnerContentRepository().store(inputStream, innerContentStorage);
  }
}
