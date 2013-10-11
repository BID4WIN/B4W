package com.bid4win.service.property;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.dao.property.IPropertyAbstractDaoStub;
import com.bid4win.commons.service.property.PropertyAbstractServiceTester;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.dao.property.PropertyDaoStub;
import com.bid4win.persistence.dao.property.PropertyRootDaoStub;
import com.bid4win.persistence.entity.EntityGenerator;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.property.Property;
import com.bid4win.persistence.entity.property.PropertyRoot;
import com.bid4win.service.connection.SessionData;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
public class PropertyServiceTest
       extends PropertyAbstractServiceTester<Property, PropertyRoot, SessionData, Account, EntityGenerator>
{
  /** Référence du service à tester */
  @Autowired
  @Qualifier("PropertyService")
  private PropertyService service;
  /** Référence du DAO de test des propriétés racines */
  @Autowired
  @Qualifier("PropertyRootDaoStub")
  private PropertyRootDaoStub dao;
  /** Référence du DAO de test des propriétés */
  @Autowired
  @Qualifier("PropertyDaoStub")
  private PropertyDaoStub propertyDao;

  /**
   * Test of addProperty(String, String), of class PropertyManager.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testAddProperty_String_String() throws Bid4WinException
  {
    // Validation de la vérification des droits administrateur
    this.checkAdminRestriction("addProperty", String.class, String.class);

    // Création de la situation de départ
    PropertyRoot root = this.getDao().getRoot();
    assertEquals("Wrong root version", 0, root.getVersion());
    assertEquals("Wrong root property set", 0, root.getPropertyNb());


    Property a = this.getService().addProperty("a", "a value");
    root = this.getDao().getRoot();
    assertEquals("Wrong root version", 1, root.getVersion());
    assertEquals("Wrong root property set", 1, root.getPropertyNb());
    Property result_a = root.getProperty(a.getKey());
    assertNotNull("Wrong root property", result_a);
    assertEquals("Wrong result id", a.getId(), result_a.getId());
    assertEquals("Wrong result version", 0, result_a.getVersion());
    assertEquals("Wrong result key", "a", result_a.getKey());
    assertEquals("Wrong result value", "a value", result_a.getValue());

    Property b = this.getService().addProperty("a.b.c", "c value");
    root = this.getDao().getRoot();
    assertEquals("Wrong root version", 2, root.getVersion());
    assertEquals("Wrong root property set", 1, root.getPropertyNb());
    result_a = root.getProperty(a.getKey());
    assertNotNull("Wrong root property", result_a);
    assertEquals("Wrong result id", a.getId(), result_a.getId());
    assertEquals("Wrong result version", 1, result_a.getVersion());
    assertEquals("Wrong result key", "a", result_a.getKey());
    assertEquals("Wrong result value", "a value", result_a.getValue());
    Property result_b = result_a.getProperty(b.getKey());
    assertNotNull("Wrong root property", result_b);
    assertEquals("Wrong result id", b.getId(), result_b.getId());
    assertEquals("Wrong result version", 0, result_b.getVersion());
    assertEquals("Wrong result key", "b", result_b.getKey());
    assertEquals("Wrong result value", "", result_b.getValue());
    Property result_c = result_b.getProperty("c");
    assertNotNull("Wrong root property", result_c);
    assertTrue("Wrong result id", 0 < result_c.getId());
    assertEquals("Wrong result version", 0, result_c.getVersion());
    assertEquals("Wrong result key", "c", result_c.getKey());
    assertEquals("Wrong result value", "c value", result_c.getValue());
    // Test d'ajout d'une propriété déjà référencée
    try
    {
      this.getService().addProperty("a.b", "b value");
      fail("Addition of already existing key should have failed");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      root = this.getDao().getRoot();
      assertEquals("Wrong root version", 2, root.getVersion());
      assertEquals("Wrong root property set", 1, root.getPropertyNb());
    }
  }
  /**
   * Test of updateKey(String, String), of class PropertyManager.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testUpdateKey_String_String() throws Bid4WinException
  {
    // Validation de la vérification des droits administrateur
    this.checkAdminRestriction("updateKey", String.class, String.class);

    // Création de la situation de départ
    PropertyRoot root = this.getDao().getRoot();
    assertEquals("Wrong root version", 0, root.getVersion());
    assertEquals("Wrong root property set", 0, root.getPropertyNb());
    this.addProperty("a.b.c", "c value");
    this.addProperty("a.b.c.d", "d value");
    root = this.getDao().getRoot();
    assertEquals("Wrong root version", 2, root.getVersion());
    assertEquals("Wrong root property set", 1, root.getPropertyNb());

    Property result_c = this.getService().updateKey("a.b.c", "a.b.d");
    assertEquals("Wrong result version", 2, result_c.getVersion());
    assertEquals("Wrong property size", 1, result_c.getPropertyNb());
    assertEquals("Wrong result key", "d", result_c.getKey());
    Property result_b = this.getProperty("a.b");
    assertEquals("Wrong result version", 1, result_b.getVersion());
    assertEquals("Wrong property size", 1, result_b.getPropertyNb());
    root = this.getDao().getRoot();
    assertEquals("Wrong result version", 3, root.getVersion());
    assertEquals("Wrong property size", 1, root.getPropertyNb());

    result_c = this.getService().updateKey("a.b.d", "d");
    assertEquals("Wrong result version", 3, result_c.getVersion());
    assertEquals("Wrong property size", 1, result_c.getPropertyNb());
    assertEquals("Wrong result key", "d", result_c.getKey());
    result_b = this.getProperty("a.b");
    assertEquals("Wrong result version", 2, result_b.getVersion());
    assertEquals("Wrong property size", 0, result_b.getPropertyNb());
    root = this.getDao().getRoot();
    assertEquals("Wrong result version", 4, root.getVersion());
    assertEquals("Wrong property size", 2, root.getPropertyNb());

    result_c = this.getService().updateKey("d", "a.b.c.d");
    assertEquals("Wrong result version", 4, result_c.getVersion());
    assertEquals("Wrong property size", 1, result_c.getPropertyNb());
    assertEquals("Wrong result key", "d", result_c.getKey());
    root = this.getDao().getRoot();
    assertEquals("Wrong result version", 5, root.getVersion());
    assertEquals("Wrong property size", 1, root.getPropertyNb());
    result_b = this.getProperty("a.b");
    assertEquals("Wrong result version", 3, result_b.getVersion());
    assertEquals("Wrong property size", 1, result_b.getPropertyNb());
    result_b = this.getProperty("a.b.c");
    assertEquals("Wrong result version", 0, result_b.getVersion());
    assertEquals("Wrong property size", 1, result_b.getPropertyNb());

    try
    {
      this.getService().updateKey("a.b.c.d", "a.b");
      fail("Should fail with existing key");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }

  /**
   *
   * TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  @Test
  public void test_performance_test() throws Bid4WinException
  {
    // Création de la situation de départ
    this.connectAccount(0);
    this.updateRole(0, this.getService().getAdminRole());

    this.getService().addProperty("a.b.c1.d1", "d value");
    this.getService().addProperty("a.b.c1.d2", "d value");
    this.getService().addProperty("a.b.c1.d3", "d value");
    this.getService().addProperty("a.b.c1.d4", "d value");
    this.getService().addProperty("a.b.c2.d1", "d value");
    this.getService().addProperty("a.b.c2.d2", "d value");
    this.getService().addProperty("a.b.c2.d3", "d value");
    this.getService().addProperty("a.b.c2.d4", "d value");
    this.getService().addProperty("a.b.c3.d1", "d value");
    this.getService().addProperty("a.b.c3.d2", "d value");
    this.getService().addProperty("a.b.c3.d3", "d value");
    this.getService().addProperty("a.b.c3.d4", "d value");
    try
    {
      this.getService().getProperty("a").defineValue("");
      fail("Should fail with protected property");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }

    Bid4WinDate date1 = new Bid4WinDate();
    for(int i = 0 ; i < 500 ; i++)
    {
      this.getService().getProperty("a");
    }
    Bid4WinDate date2 = new Bid4WinDate();
    System.out.println(date2.getTime() - date1.getTime());
    for(int i = 0 ; i < 500 ; i++)
    {
      try
      {
        UtilObject.checkNotNull("property", this.getDao().getRoot().getProperty("a"));
      }
      catch(RuntimeException ex)
      {
        throw new PersistenceException(ex);
      }
    }
    Bid4WinDate date3 = new Bid4WinDate();
    System.out.println(date3.getTime() - date2.getTime());
    if(date2.getTime() - date1.getTime() >= date3.getTime() - date2.getTime())
    {
      fail("Performance test failed");
    }
    System.out.println(this.getDao().getRoot());
  }

  /**
   * Getter du DAO de test des propriétés racines
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDaoTester#getDao()
   */
  @Override
  public PropertyRootDaoStub getDao()
  {
    return this.dao;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.property.PropertyAbstractServiceTester#getPropertyDao()
   */
  @Override
  protected IPropertyAbstractDaoStub<Property, PropertyRoot> getPropertyDao()
  {
    return this.propertyDao;
  }
  /**
   * Getter du manager des propriétés
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.property.PropertyAbstractServiceTester#getService()
   */
  @Override
  public PropertyService getService()
  {
    return this.service;
  }
}
