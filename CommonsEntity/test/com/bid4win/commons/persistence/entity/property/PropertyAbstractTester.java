package com.bid4win.commons.persistence.entity.property;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Properties;

import org.junit.Test;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.EntityGenerator;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;

/**
 * Classe de test d'une propriété<BR>
 * <BR>
 * @param <PROPERTY> TODO A COMMENTER<BR>
 * @param <ROOT> TODO A COMMENTER<BR>
 * @param <ACCOUNT> TODO A COMMENTER<BR>
 * @param <GENERATOR> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class PropertyAbstractTester<PROPERTY extends PropertyAbstract<PROPERTY, ROOT>,
                                             ROOT extends PropertyRootAbstract<ROOT, PROPERTY>,
                                             ACCOUNT extends AccountAbstract<ACCOUNT>,
                                             GENERATOR extends EntityGenerator<ACCOUNT>>
       extends PropertyBaseTester<PROPERTY, ROOT, PROPERTY, ACCOUNT, GENERATOR>
{
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.property.PropertyBaseTester#createBase()
   */
  @Override
  public PROPERTY createBase()
  {
    try
    {
      return this.createProperty("key", "value");
    }
    catch(UserException ex)
    {
      return null;
    }
  }
  /**
   *
   * TODO A COMMENTER
   * @param key TODO A COMMENTER
   * @param value TODO A COMMENTER
   * @param parent TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  public abstract PROPERTY createProperty(String key, String value, PROPERTY parent) throws UserException;
  /**
   *
   * TODO A COMMENTER
   * @param key TODO A COMMENTER
   * @param value TODO A COMMENTER
   * @param root TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  public abstract PROPERTY createProperty(String key, String value, ROOT root) throws UserException;
  /**
   *
   * TODO A COMMENTER
   * @param toBeCopied TODO A COMMENTER
   * @param property TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   * @throws ModelArgumentException TODO A COMMENTER
   */
  public abstract PROPERTY createProperty(PROPERTY toBeCopied, PROPERTY property) throws UserException, ModelArgumentException;
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public abstract ROOT createRoot();

  /**
   * Test of PropertyAbstract(...) method, of class PropertyAbstract.
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.property.PropertyBaseTester#testConstructor_etc()
   */
  @Override
  @Test
  public void testConstructor_etc() throws Bid4WinException
  {
    super.testConstructor_etc();

    String key = "1_a";
    String value = "azerty12345@:";
    try
    {
      PROPERTY instance = this.createProperty(key, value);
      assertEquals("Bad property key", key, instance.getKey());
      assertEquals("Bad property value", value, instance.getValue());
      assertEquals("Bad sub property nb", 0, instance.getPropertyNb());
      assertNull("Bad parent property", instance.getProperty());
      assertNull("Bad root property", instance.getRoot());
      key = "A";
      value = null;
      instance = this.createProperty(key, value);
      assertEquals("Bad property key", key.toLowerCase(), instance.getKey());
      assertEquals("Bad property value", UtilString.EMPTY, instance.getValue());
      assertEquals("Bad sub property nb", 0, instance.getPropertyNb());
      key = "1_a";
      value = "azerty12345@:";
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Instanciation should not fail: " + ex.getMessage());
    }
    try
    {
      this.createProperty("1.2", value);
      fail("Instanciation with point character key should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      this.createProperty(null, value);
      fail("Instanciation with null key should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }

    PROPERTY parent = this.createProperty("1", "2");
    try
    {
      PROPERTY instance = this.createProperty(key, value, parent);
      assertEquals("Bad property key", key, instance.getKey());
      assertEquals("Bad property value", value, instance.getValue());
      assertEquals("Bad sub property nb", 0, instance.getPropertyNb());
      assertTrue("Bad parent property", parent == instance.getProperty());
      assertNull("Bad root property", instance.getRoot());
      assertTrue("Bad parent sub property", instance == parent.getProperty(instance.getKey()));
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Instanciation should not fail: " + ex.getMessage());
    }
    parent = null;
    try
    {
      this.createProperty(key, value, parent);
      fail("Instanciation with null parent property should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }

    ROOT root = this.createRoot();
    key = this.getBaseKey(1);
    try
    {
      PROPERTY instance = this.createProperty(key, value, root);
      assertEquals("Bad property key", key, instance.getKey());
      assertEquals("Bad property value", value, instance.getValue());
      assertEquals("Bad sub property nb", 0, instance.getPropertyNb());
      assertNull("Bad parent property", instance.getProperty());
      assertTrue("Bad root property", root == instance.getRoot());
      assertTrue("Bad parent sub property", instance == root.getProperty(instance.getKey()));
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Instanciation should not fail: " + ex.getMessage());
    }
    root = null;
    try
    {
      this.createProperty(key, value, root);
      fail("Instanciation with null root property should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }

    key = this.getBaseKey(0);
    root = this.createRoot();
    PROPERTY a1 = root.addProperty(UtilProperty.computeKey(key, "b", "c", "d", "e"), "e");
    root.addProperty(UtilProperty.computeKey(key, "b", "c", "d", "e", "f1"), "f1");
    root.addProperty(UtilProperty.computeKey(key, "b", "c", "d", "e", "f2"), "f2");
    root.addProperty(UtilProperty.computeKey(key, "b", "c", "d", "e", "f3"), "f3");
    PROPERTY a2 = root.addProperty(this.getBaseKey(2), UtilString.EMPTY);

    PROPERTY property = this.createProperty(a1, a2);
    assertTrue("Wrong parent property", a2 == property.getProperty());
    assertEquals("Wrong result", a1.toProperties(a1.getFullKey()),
                                 property.toProperties(property.getFullKey()));

    try
    {
    	this.createProperty(a1, a2);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of defineKey(), of class PropertyAbstract.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testDefineKey_String() throws Bid4WinException
  {
    PROPERTY property1 = this.createBase();
    String key1 = property1.getKey();
    String key2 = this.getBaseKey(2);
    property1.defineKey(key2);
    assertEquals("Wrong key", key2, property1.getKey());
    property1.linkTo(this.createBase());
    try
    {
      property1.defineKey(key1);
      fail("Should fail with linked property");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    property1.unlinkFromProperty();
    property1.linkTo(this.createRoot());
    try
    {
      property1.defineKey(key1);
      fail("Should fail with linked property");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    assertEquals("Wrong key", key2, property1.getKey());
  }

  /**
   * Test of sameInternal(PropertyAbstract, boolean), of class PropertyAbstract.
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.property.PropertyBaseTester#testSameInternal_CLASS_boolean()
   */
  @Override
  @Test
  public void testSameInternal_CLASS_boolean() throws Bid4WinException
  {
    super.testSameInternal_CLASS_boolean();

    String key1 = this.getBaseKey(1);
    PROPERTY property1 = this.createProperty(key1, "value");
    PROPERTY property2 = this.createProperty(property1.getKey(), property1.getValue());
    assertTrue(property1.same(property2));
    assertTrue(property2.same(property1));
    assertTrue(property1.identical(property2));
    assertTrue(property2.identical(property1));

    String key2 = this.getBaseKey(2);
    property2 = this.createProperty(key2, property1.getValue());
    assertFalse(property1.same(property2));
    assertFalse(property2.same(property1));
    assertFalse(property1.identical(property2));
    assertFalse(property2.identical(property1));

    property2 = this.createProperty(property1.getKey(), property1.getValue() + "BIS");
    assertFalse(property1.same(property2));
    assertFalse(property2.same(property1));
    assertFalse(property1.identical(property2));
    assertFalse(property2.identical(property1));

    property2 = this.createProperty(property1.getKey(), property1.getValue());
    ROOT root1 = this.createRoot();
    ROOT root2 = this.createRoot();

    property1.linkTo(root1);
    assertFalse(property1.same(property2));
    assertFalse(property2.same(property1));
    assertFalse(property1.identical(property2));
    assertFalse(property2.identical(property1));

    property2.linkTo(root2);
    assertTrue(property1.same(property2));
    assertTrue(property2.same(property1));
    assertTrue(property1.identical(property2));
    assertTrue(property2.identical(property1));
  }
  /**
   * Test of render(), of class PropertyAbstract.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testRender_0args() throws Bid4WinException
  {
    ROOT root = this.createRoot();
    PROPERTY a1 = this.createProperty(this.getBaseKey(1), "value a1", root);
    PROPERTY b1 = this.createProperty("b1", "value b1", a1);
    PROPERTY c1 = this.createProperty("c1", "value c1", b1);
    PROPERTY c2 = this.createProperty("c2", "value c2", b1);
    PROPERTY b2 = this.createProperty("b2", "value b2", a1);
    PROPERTY b3 = this.createProperty("b3", "value b3", a1);
    PROPERTY c3 = this.createProperty("c3", "value c3", b3);
    PROPERTY a2 = this.createProperty(this.getBaseKey(2), "value a2", root);
    PROPERTY b4 = this.createProperty("b4", "value b4", a2);
    PROPERTY b5 = this.createProperty("b5", "value b5", a2);
    PROPERTY c4 = this.createProperty("c4", "value c4", b5);
    PROPERTY c5 = this.createProperty("c5", "value c5", b5);
    PROPERTY d1 = this.createProperty("d1", "value d1", c5);
    PROPERTY c6 = this.createProperty("c6", "value c6", b5);

    try
    {
      Class.forName("com.bid4win.commons.persistence.entity.property.PropertyAbstract_");
    }
    catch(ClassNotFoundException ex)
    {
      System.out.println(ex.getMessage());
    }
    System.out.println(a1.render());
    System.out.println(a2.render());
    System.out.println(b1.render());
    System.out.println(b2.render());
    System.out.println(b3.render());
    System.out.println(b4.render());
    System.out.println(b5.render());
    System.out.println(c1.render());
    System.out.println(c2.render());
    System.out.println(c3.render());
    System.out.println(c4.render());
    System.out.println(c5.render());
    System.out.println(c6.render());
    System.out.println(d1.render());
  }
  /**
   * Test of toProperties(), of class PropertyAbstract.
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.property.PropertyBaseTester#testToProperties()
   */
  @Override
  @Test
  public void testToProperties() throws Bid4WinException
  {
    PROPERTY base = this.createBase();
    base.addProperty(UtilProperty.computeKey("a", "b"), "1");
    base.addProperty("c", "2");
    base.addProperty(UtilProperty.computeKey("c", "b"), "3");
    Properties properties = base.toProperties();
    assertEquals("Bad properties nb", 5, properties.size());
    assertEquals("Bad properties value", base.getValue(), properties.get(base.getKey()));
    assertEquals("Bad properties value", UtilString.EMPTY,  properties.get(UtilProperty.computeKey(base.getKey(), "a")));
    assertEquals("Bad properties value", "1", properties.get(UtilProperty.computeKey(base.getKey(), "a", "b")));
    assertEquals("Bad properties value", "2", properties.get(UtilProperty.computeKey(base.getKey(), "c")));
    assertEquals("Bad properties value", "3", properties.get(UtilProperty.computeKey(base.getKey(), "c", "b")));

    base.defineValue("toto");
    properties = base.toProperties();
    assertEquals("Bad properties nb", 5, properties.size());
    assertEquals("Bad properties value", base.getValue(), properties.get(base.getKey()));
    assertEquals("Bad properties value", UtilString.EMPTY,  properties.get(UtilProperty.computeKey(base.getKey(), "a")));
    assertEquals("Bad properties value", "1", properties.get(UtilProperty.computeKey(base.getKey(), "a", "b")));
    assertEquals("Bad properties value", "2", properties.get(UtilProperty.computeKey(base.getKey(), "c")));
    assertEquals("Bad properties value", "3", properties.get(UtilProperty.computeKey(base.getKey(), "c" , "b")));
  }
  /**
   * Test of toProperties(String), of class PropertyAbstract.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testToProperties_String() throws Bid4WinException
  {
    ROOT root = this.createRoot();
    String keyA = this.getBaseKey(1);
    PROPERTY a = this.createProperty(keyA, "1", root);
    String keyB = "b";
    PROPERTY b = this.createProperty(keyB, "2", a);
    String keyC = "c";
    PROPERTY c = this.createProperty(keyC, "3", b);
    Properties properties1 = new Properties();
    properties1.put(keyA, a.getValue());
    properties1.put(UtilProperty.computeKey(keyA, keyB), b.getValue());
    properties1.put(UtilProperty.computeKey(keyA, keyB, keyC), c.getValue());
    assertEquals("Wrong properties", properties1, a.toProperties(keyA));
    assertEquals("Wrong properties", properties1, a.toProperties(UtilProperty.computeKey(keyA, keyB)));
    assertEquals("Wrong properties", properties1, a.toProperties(UtilProperty.computeKey(keyA, keyB, keyC)));
    assertEquals("Wrong properties", properties1, a.toProperties(null));

    properties1.remove(keyA);
    Properties properties2 = new Properties();
    properties2.put(keyB, b.getValue());
    properties2.put(UtilProperty.computeKey(keyB, keyC), c.getValue());
    assertEquals("Wrong properties", properties1, b.toProperties(keyA));
    assertEquals("Wrong properties", properties2, b.toProperties(UtilProperty.computeKey(keyA, keyB)));
    assertEquals("Wrong properties", properties1, b.toProperties(UtilProperty.computeKey(keyA, keyB, keyC)));
    assertEquals("Wrong properties", properties1, b.toProperties(null));

    properties1.remove(UtilProperty.computeKey(a.getKey(), b.getKey()));
    properties2.remove(b.getKey());
    Properties properties3 = new Properties();
    properties3.put(keyC, c.getValue());
    assertEquals("Wrong properties", properties1, c.toProperties(keyA));
    assertEquals("Wrong properties", properties2, c.toProperties(UtilProperty.computeKey(keyA, keyB)));
    assertEquals("Wrong properties", properties3, c.toProperties(UtilProperty.computeKey(keyA, keyB, keyC)));
    assertEquals("Wrong properties", properties1, c.toProperties(null));
  }
  /**
   * Test of getKey(String), of class PropertyAbstract.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testGetKey_String() throws Bid4WinException
  {
    ROOT root = this.createRoot();
    String keyA = this.getBaseKey(1);
    PROPERTY a = this.createProperty(keyA, "1", root);
    String keyB = "b";
    PROPERTY b = this.createProperty(keyB, "2", a);
    String keyC = "c";
    PROPERTY c = this.createProperty(keyC, "3", b);
    assertEquals("Wrong key", keyA, a.getKey(keyA));
    assertEquals("Wrong key", keyA, a.getKey(keyB));
    assertEquals("Wrong key", keyA, a.getKey(UtilProperty.computeKey(keyA, keyB)));
    assertEquals("Wrong key", keyA, a.getKey(UtilProperty.computeKey(keyB, keyA)));
    assertEquals("Wrong key", keyA, a.getKey(keyC));
    assertEquals("Wrong key", keyA, a.getKey(UtilProperty.computeKey(keyA, keyC)));
    assertEquals("Wrong key", keyA, a.getKey(UtilProperty.computeKey(keyC, keyA)));
    assertEquals("Wrong key", keyA, a.getKey(UtilProperty.computeKey(keyB, keyC)));
    assertEquals("Wrong key", keyA, a.getKey(UtilProperty.computeKey(keyC, keyB)));
    assertEquals("Wrong key", keyA, a.getKey(UtilProperty.computeKey(keyA, keyB, keyC)));
    assertEquals("Wrong key", keyA, a.getKey(null));

    assertEquals("Wrong key", UtilProperty.computeKey(keyA, keyB), b.getKey(keyA));
    assertEquals("Wrong key", UtilProperty.computeKey(keyA, keyB), b.getKey(keyB));
    assertEquals("Wrong key", keyB, b.getKey(UtilProperty.computeKey(keyA, keyB)));
    assertEquals("Wrong key", UtilProperty.computeKey(keyA, keyB), b.getKey(UtilProperty.computeKey(keyB, keyA)));
    assertEquals("Wrong key", UtilProperty.computeKey(keyA, keyB), b.getKey(keyC));
    assertEquals("Wrong key", UtilProperty.computeKey(keyA, keyB), b.getKey(UtilProperty.computeKey(keyA, keyC)));
    assertEquals("Wrong key", UtilProperty.computeKey(keyA, keyB), b.getKey(UtilProperty.computeKey(keyC, keyA)));
    assertEquals("Wrong key", UtilProperty.computeKey(keyA, keyB), b.getKey(UtilProperty.computeKey(keyB, keyC)));
    assertEquals("Wrong key", UtilProperty.computeKey(keyA, keyB), b.getKey(UtilProperty.computeKey(keyC, keyB)));
    assertEquals("Wrong key", UtilProperty.computeKey(keyA, keyB), b.getKey(UtilProperty.computeKey(keyA, keyB, keyC)));
    assertEquals("Wrong key", UtilProperty.computeKey(keyA, keyB), b.getKey(null));

    assertEquals("Wrong key", UtilProperty.computeKey(keyA, keyB, keyC), c.getKey(keyA));
    assertEquals("Wrong key", UtilProperty.computeKey(keyA, keyB, keyC), c.getKey(keyB));
    assertEquals("Wrong key", UtilProperty.computeKey(keyB, keyC), c.getKey(UtilProperty.computeKey(keyA, keyB)));
    assertEquals("Wrong key", UtilProperty.computeKey(keyA, keyB, keyC), c.getKey(UtilProperty.computeKey(keyB, keyA)));
    assertEquals("Wrong key", UtilProperty.computeKey(keyA, keyB, keyC), c.getKey(keyC));
    assertEquals("Wrong key", UtilProperty.computeKey(keyA, keyB, keyC), c.getKey(UtilProperty.computeKey(keyA, keyC)));
    assertEquals("Wrong key", UtilProperty.computeKey(keyA, keyB, keyC), c.getKey(UtilProperty.computeKey(keyC, keyA)));
    assertEquals("Wrong key", UtilProperty.computeKey(keyA, keyB, keyC), c.getKey(UtilProperty.computeKey(keyB, keyC)));
    assertEquals("Wrong key", UtilProperty.computeKey(keyA, keyB, keyC), c.getKey(UtilProperty.computeKey(keyC, keyB)));
    assertEquals("Wrong key", keyC, c.getKey(UtilProperty.computeKey(keyA, keyB, keyC)));
    assertEquals("Wrong key", UtilProperty.computeKey(keyA, keyB, keyC), c.getKey(null));
  }
  /**
   * Test of getFullKey(), of class PropertyAbstract.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testGetFullKey_0args() throws Bid4WinException
  {
    ROOT root = this.createRoot();
    String keyA = this.getBaseKey(1);
    PROPERTY a = this.createProperty(keyA, "1", root);
    String keyB = "b";
    PROPERTY b = this.createProperty(keyB, "2", a);
    String keyC = "c";
    PROPERTY c = this.createProperty(keyC, "3", b);
    assertEquals("Wrong key", UtilProperty.computeKey(keyA), a.getFullKey());
    assertEquals("Wrong key", UtilProperty.computeKey(keyA, keyB), b.getFullKey());
    assertEquals("Wrong key", UtilProperty.computeKey(keyA, keyB, keyC), c.getFullKey());
  }
  /**
   * Test of hasProperty(), of class PropertyAbstract.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testHasProperty_0args() throws Bid4WinException
  {
    ROOT root = this.createRoot();
    PROPERTY a = this.createProperty(this.getBaseKey(1), "1", root);
    PROPERTY b = this.createProperty("b", "2");
    assertFalse("Should not have parent property", a.hasProperty());
    assertFalse("Should not have parent property", b.hasProperty());
    b.linkTo(a);
    assertFalse("Should not have parent property", a.hasProperty());
    assertTrue("Should have parent property", b.hasProperty());
  }
  /**
   * Test of hasRoot(), of class PropertyAbstract.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testHasRoot_0args() throws Bid4WinException
  {
    PROPERTY a = this.createProperty(this.getBaseKey(1), "1");
    assertFalse("Should not have root property", a.hasRoot());
    ROOT root = this.createRoot();
    a.linkTo(root);
    assertTrue("Should have root property", a.hasRoot());
    a = this.createProperty("a", "1", this.createProperty("b", "2"));
    assertFalse("Should not have root property", a.hasRoot());
  }
  /**
   * Test of getProperty(), of class PropertyAbstract.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testGetProperty_0args() throws Bid4WinException
  {
    ROOT root = this.createRoot();
    PROPERTY a = this.createProperty(this.getBaseKey(1), "1", root);
    PROPERTY b = this.createProperty("b", "2");
    assertNull("Should not have parent property", a.getProperty());
    assertNull("Should not have parent property", b.getProperty());
    b.linkTo(a);
    assertNull("Should not have parent property", a.getProperty());
    assertTrue("Should have parent property", a == b.getProperty());
    assertTrue("Should have parent property", b.hasProperty());
  }
  /**
   * Test of getRoot(), of class PropertyAbstract.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testGetRoot_0args() throws Bid4WinException
  {
    PROPERTY a = this.createProperty(this.getBaseKey(1), "1");
    assertNull("Should not have root property", a.getRoot());
    ROOT root = this.createRoot();
    a.linkTo(root);
    assertTrue("Should have root property", root == a.getRoot());
    a = this.createProperty("a", "1", this.createProperty("b", "2"));
    assertNull("Should not have root property", a.getRoot());
  }
  /**
   * Test of linkTo(CLASS), of class PropertyAbstract.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testLinkTo_CLASS() throws Bid4WinException
  {
    PROPERTY a = this.createProperty("a", "1");
    PROPERTY b = this.createProperty("b", "2");
    assertNull("Should not have root property", a.getRoot());
    assertNull("Should not have parent property", a.getProperty());
    assertNull("Should not contain property", a.getProperty(b.getKey()));
    assertNull("Should not have root property", b.getRoot());
    assertNull("Should not have parent property", b.getProperty());
    b.linkTo(a);
    assertNull("Should not have root property", a.getRoot());
    assertNull("Should not have parent property", a.getProperty());
    assertTrue("Should contain property", b == a.getProperty(b.getKey()));
    assertNull("Should not have root property", b.getRoot());
    assertTrue("Should have parent property", a == b.getProperty());
    try
    {
      b.linkTo(this.createProperty("c", "3"));
      fail("Could not be linked again");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    b = this.createProperty(this.getBaseKey(1), "2", this.createRoot());
    try
    {
      b.linkTo(this.createProperty("c", "3"));
      fail("Could not be linked again");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of linkTo(PropertyRootAbstract), of class PropertyAbstract.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testLinkTo_PropertyRootAbstract() throws Bid4WinException
  {
    ROOT root = this.createRoot();
    PROPERTY property = this.createProperty(this.getBaseKey(1), "2");
    assertNull("Should not contain property", root.getProperty(property.getKey()));
    assertNull("Should not have root property", property.getRoot());
    assertNull("Should not have parent property", property.getProperty());
    property.linkTo(root);
    assertTrue("Should contain property", property == root.getProperty(property.getKey()));
    assertTrue("Should have root property", root == property.getRoot());
    assertNull("Should not have parent property", property.getProperty());
    try
    {
      property.linkTo(this.createRoot());
      fail("Could not be linked again");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    property = this.createProperty("b", "2", this.createProperty("a", "1"));
    try
    {
      property.linkTo(this.createRoot());
      fail("Could not be linked again");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of unlinkFromProperty(), of class PropertyAbstract.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testUnlinkFromProperty_0args() throws Bid4WinException
  {
    ROOT root = this.createRoot();
    PROPERTY a = this.createProperty("a", "1");
    PROPERTY b = this.createProperty(this.getBaseKey(1), "2");
    try
    {
      b.unlinkFromProperty();
      fail("Could not be unlinked if not linked to parent property");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    b.linkTo(a);
    try
    {
      b.unlinkFromProperty();
      assertNull("Should not contain property anymore", a.getProperty(b.getKey()));
      assertNull("Should not have root property", b.getRoot());
      assertNull("Should not have parent property anymore", b.getProperty());
    }
    catch(UserException ex)
    {
      fail("Should be unlinked if linked to parent property");
    }
    b.linkTo(root);
    try
    {
      b.unlinkFromProperty();
      fail("Could not be unlinked if linked to root property");
    }
    catch(UserException ex)
    {
      assertTrue("Should still contain property", b == root.getProperty(b.getKey()));
      assertTrue("Should still have root property", root == b.getRoot());
      assertNull("Should not have parent property", b.getProperty());
    }
  }
  /**
   * Test of unlinkFromRoot(), of class PropertyAbstract.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testUnlinkFromRoot_0args() throws Bid4WinException
  {
    ROOT root = this.createRoot();
    PROPERTY a = this.createProperty("a", "1");
    PROPERTY b = this.createProperty(this.getBaseKey(1), "2");
    try
    {
      b.unlinkFromRoot();
      fail("Could not be unlinked if not linked to root property");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    b.linkTo(root);
    try
    {
      b.unlinkFromRoot();
      assertNull("Should not contain property anymore", root.getProperty(b.getKey()));
      assertNull("Should not have root property anymore", b.getRoot());
      assertNull("Should not have parent property", b.getProperty());
    }
    catch(UserException ex)
    {
      fail("Should be unlinked if linked to root property");
    }
    b.linkTo(a);
    try
    {
      b.unlinkFromRoot();
      fail("Could not be unlinked if linked to parent property");
    }
    catch(UserException ex)
    {
      assertTrue("Should still contain property", b == a.getProperty(b.getKey()));
      assertNull("Should not have root property", b.getRoot());
      assertTrue("Should still have parent property", a == b.getProperty());
    }
  }
  /**
   *
   * TODO A COMMENTER
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.property.PropertyBaseTester#testCheckProtection()
   */
  @Override
  @Test
  public void testCheckProtection() throws Bid4WinException
  {
    super.testCheckProtection();

    String id1 = this.startProtection();
    PROPERTY property1 = this.createBase();
    String key = property1.getKey();
    String value = property1.getValue();
    String id2 = this.startProtection();
    PROPERTY property2 = property1.addProperty("a", "b");
    this.stopProtection();
    this.stopProtection();

    try
    {
      property1.defineKey(this.getBaseKey(2));
      fail("Should fail with protected object");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
      assertEquals("Wrong property", key, property1.getKey());
    }
    try
    {
      property1.defineValue(value + "1");
      fail("Should fail with protected object");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
      assertEquals("Wrong property", value, property1.getValue());
    }

    try
    {
      property2.unlinkFromProperty();
      fail("Should fail with protected object");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }

    this.startProtection(id1);
    try
    {
      property2.unlinkFromProperty();
      fail("Should fail with protected object");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }

    this.stopProtection();
    this.startProtection(id2);
    try
    {
      property2.unlinkFromProperty();
      fail("Should fail with protected object");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    assertTrue("Wrong property", property1 == property2.getProperty());
    assertTrue("Wrong property", property2 == property1.getProperty(property2.getKey()));

    this.startProtection(id1);
    property2.unlinkFromProperty();
    this.stopProtection();
    this.stopProtection();
    try
    {
      property2.linkTo(property1);
      fail("Should fail with protected object");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }

    this.startProtection(id1);
    try
    {
      property2.linkTo(property1);
      fail("Should fail with protected object");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }

    this.stopProtection();
    this.startProtection(id2);
    try
    {
      property2.linkTo(property1);
      fail("Should fail with protected object");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    assertNull("Wrong property", property2.getProperty());
    assertNull("Wrong property", property1.getProperty(property2.getKey()));

    this.startProtection(id1);
    ROOT root = this.createRoot();
    this.stopProtection();
    this.stopProtection();
    try
    {
      property2.linkTo(root);
      fail("Should fail with protected object");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }

    this.startProtection(id1);
    try
    {
      property2.linkTo(root);
      fail("Should fail with protected object");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }

    this.stopProtection();
    this.startProtection(id2);
    try
    {
      property2.linkTo(root);
      fail("Should fail with protected object");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    assertNull("Wrong property", property2.getRoot());
    assertNull("Wrong property", root.getProperty(property2.getKey()));

    this.startProtection(id1);
    property2.linkTo(root);
    this.stopProtection();
    this.stopProtection();

    try
    {
      property2.unlinkFromRoot();
      fail("Should fail with protected object");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }

    this.startProtection(id1);
    try
    {
      property2.unlinkFromRoot();
      fail("Should fail with protected object");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }

    this.stopProtection();
    this.startProtection(id2);
    try
    {
      property2.unlinkFromRoot();
      fail("Should fail with protected object");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    assertTrue("Wrong property", root == property2.getRoot());
    assertTrue("Wrong property", property2 == root.getProperty(property2.getKey()));
  }
}
