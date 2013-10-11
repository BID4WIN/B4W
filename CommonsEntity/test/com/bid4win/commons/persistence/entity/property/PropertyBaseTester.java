package com.bid4win.commons.persistence.entity.property;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Properties;

import org.junit.Test;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.Bid4WinEntityTester;
import com.bid4win.commons.persistence.entity.EntityGenerator;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;

/**
 * Classe de test d'une propriété de base<BR>
 * <BR>
 * @param <BASE> TODO A COMMENTER<BR>
 * @param <ROOT> TODO A COMMENTER<BR>
 * @param <PROPERTY> TODO A COMMENTER<BR>
 * @param <ACCOUNT> TODO A COMMENTER<BR>
 * @param <GENERATOR> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class PropertyBaseTester<BASE extends PropertyBase<BASE, ROOT, PROPERTY>,
                                         ROOT extends PropertyRootAbstract<ROOT, PROPERTY>,
                                         PROPERTY extends PropertyAbstract<PROPERTY, ROOT>,
                                         ACCOUNT extends AccountAbstract<ACCOUNT>,
                                         GENERATOR extends EntityGenerator<ACCOUNT>>
       extends Bid4WinEntityTester<ACCOUNT, GENERATOR>
{
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected abstract BASE createBase();
  /**
   *
   * TODO A COMMENTER
   * @param key TODO A COMMENTER
   * @param value TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  protected abstract PROPERTY createProperty(String key, String value) throws UserException;
  /**
   *
   * TODO A COMMENTER
   * @param index TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected String getBaseKey(int index)
  {
    return "a_" + index;
  }

  /**
   * Test of PropertyBase(...) method, of class PropertyBase.
   * @throws Bid4WinException TODO A COMMENTER
   */
  @SuppressWarnings("unused")
  @Test
  public void testConstructor_etc() throws Bid4WinException
  {
    // Pas de test spécifique de constructeur
  }
  /**
   * Test of toProperties(), of class PropertyBase.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testToProperties() throws Bid4WinException
  {
    BASE base = this.createBase();
    String key1 = this.getBaseKey(0);
    String key2 = this.getBaseKey(1);
    base.addProperty(UtilProperty.computeKey(key1, "b"), "1");
    base.addProperty(key2, "2");
    base.addProperty(UtilProperty.computeKey(key2, "b"), "3");
    Properties properties = base.toProperties();
    assertEquals("Bad properties nb", 4, properties.size());
    assertEquals("Bad properties value", base.getProperty(key1).getValue(), properties.get(key1));
    assertEquals("Bad properties value", "1", properties.get(UtilProperty.computeKey(key1, "b")));
    assertEquals("Bad properties value", "2", properties.get(key2));
    assertEquals("Bad properties value", "3", properties.get(UtilProperty.computeKey(key2, "b")));
  }
  /**
   * Test of sameInternal(PropertyBase, boolean), of class PropertyBase.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testSameInternal_CLASS_boolean() throws Bid4WinException
  {
    BASE base1 = this.createBase();
    BASE base2 = this.createBase();
    assertTrue(base1.same(base2));
    assertTrue(base2.same(base1));
    assertTrue(base1.identical(base2));
    assertTrue(base2.identical(base1));

    String key1 = this.getBaseKey(1);
    base1.addProperty(key1, "value1");
    assertFalse(base1.same(base2));
    assertFalse(base2.same(base1));
    assertFalse(base1.identical(base2));
    assertFalse(base2.identical(base1));

    String key2 = this.getBaseKey(2);
    base1.addProperty(key2, "value2");
    base2.addProperty(key2, "value2");
    assertFalse(base1.same(base2));
    assertFalse(base2.same(base1));
    assertFalse(base1.identical(base2));
    assertFalse(base2.identical(base1));

    base2.addProperty(key1, "value1");
    assertTrue(base1.same(base2));
    assertTrue(base2.same(base1));
    assertTrue(base1.identical(base2));
    assertTrue(base2.identical(base1));
  }
  /**
   * Test of getProperty(String), of class PropertyBase.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testGetProperty_String() throws Bid4WinException
  {
    BASE base = this.createBase();

    PROPERTY a = base.addProperty(this.getBaseKey(1), "1");
    PROPERTY b = a.addProperty("b", "2");
    PROPERTY c = b.addProperty("c", "3");
    String sep = UtilProperty.KEY_SEPARATOR;
    assertTrue("Wrong property", a == base.getProperty(a.getKey()));
    assertTrue("Wrong property", b == base.getProperty(UtilProperty.computeKey(a.getKey(), b.getKey())));
    assertTrue("Wrong property", c == base.getProperty(UtilProperty.computeKey(a.getKey(), b.getKey(), c.getKey())));
    assertNull("Wrong property", base.getProperty(b.getKey()));
    assertNull("Wrong property", base.getProperty(a.getKey() + sep));
    assertNull("Wrong property", base.getProperty(sep + b.getKey()));
    assertNull("Wrong property", base.getProperty(null));
  }

  /**
   * Test of addProperty(String, String), of class PropertyBase.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testAddProperty_String_String() throws Bid4WinException
  {
    BASE base = this.createBase();
    // Ajout d'une propriété simple
    String key = this.getBaseKey(1);
    PROPERTY result = base.addProperty(key, "value a");
    assertNotNull("Property was not added", base.getProperty(key));
    assertEquals("Property was badly added", "value a", base.getProperty(key).getValue());
    assertNotNull("Result should not be null", result);
    assertTrue("Wrong result", base.getProperty(key) == result);
    // Ajout d'une propriété multiple
    result = base.addProperty(UtilProperty.computeKey(key,"b", "c", "d"), "value d");
    assertNotNull("Property was not added", base.getProperty(key).getProperty("b"));
    assertEquals("Property was badly added", "", base.getProperty(key).getProperty("b").getValue());
    assertNotNull("Result should not be null", result);
    assertTrue("Wrong result", base.getProperty(key).getProperty("b") == result);
    assertNotNull("Property was not added",
                  base.getProperty(key).getProperty("b").getProperty("c"));
    assertEquals("Property was badly added", "",
                 base.getProperty(key).getProperty("b").getProperty("c").getValue());
    assertNotNull("Property was not added",
                  base.getProperty(key).getProperty("b").getProperty("c").getProperty("d"));
    assertEquals("Property was badly added", "value d",
                 base.getProperty(key).getProperty("b").getProperty("c").getProperty("d").getValue());
    try
    {
      base.addProperty(UtilProperty.computeKey(key, "b", "c"), "value c");
      fail("Existing property should not be added again");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      base.addProperty(".b.c.d.e", "value e");
      fail("Property with wrong key should not be added");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      base.addProperty("b.c.d.", "value e");
      fail("Property with wrong key should not be added");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of addProperty(Property), of class PropertyBase.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testAddProperty_CLASS() throws Bid4WinException
  {
    BASE base = this.createBase();
    try
    {
      base.addProperty(null);
      fail("Should fail with null property");
    }
    catch(ModelArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
    PROPERTY property1 = this.createProperty(this.getBaseKey(1), "b");
    base.addProperty(property1);
    assertTrue("Should be added", property1 == base.getProperty(property1.getKey()));
    PROPERTY property2 = this.createProperty(property1.getKey(), property1.getValue());
    try
    {
      base.addProperty(property2);
      fail("Should fail with already referenced property");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertTrue("Should be added", property1 == base.getProperty(property1.getKey()));
    }
  }
  /**
   * Test of removeProperty(Property), of class PropertyBase.
   * @throws Bid4WinException Issue not expected during this test
   */
/*  @Test
  public void testRemoveProperty_Property() throws Bid4WinException
  {
    System.out.println("### removeProperty(Property)");
    PropertyBaseStub base = new PropertyBaseStub(1);
    try
    {
      base.removeProperty(null);
      fail("Should fail with null property");
    }
    catch(ModelArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
    PropertyAbstractStub stub1 = new PropertyAbstractStub("a", "b");
    try
    {
      base.removeProperty(stub1);
      fail("Should fail with not referenced property");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    base.putProperty(stub1);
    assertTrue("Should be added", stub1 == base.getProperty(stub1.getKey()));
    base.removeProperty(stub1);
    assertNull("Should be removed", base.getProperty(stub1.getKey()));
    System.out.println("### removeProperty(Property)");
  }*/
}
