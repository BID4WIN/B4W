package com.bid4win.commons.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.collection.Bid4WinCollection;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.RuntimeInstantiationException;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 * Classe de test des types d'objets de base du projet<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class Bid4WinObjectTypeTest extends Bid4WinObjectTypeTester
{
  /** TODO A COMMENTER */
  private static int index = 0;
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  private int useIndex()
  {
    return Bid4WinObjectTypeTest.index++;
  }

  /**
   * Test of Bid4WinObjectType(String, CLASS) method, of class Bid4WinObjectType.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testBid4WinObjectType_String_CLASS() throws Bid4WinException
  {
    String code = "code" + this.useIndex();
    Bid4WinObjectTypeStub1 stub1_1 = new Bid4WinObjectTypeStub1(code, null);
    assertEquals("Wrong code", code, stub1_1.getCode());
    assertNull("Wrong parent", stub1_1.getParent());
    assertTrue("Wrong type",
               stub1_1 == Bid4WinObjectType.getType(Bid4WinObjectTypeStub1.class,
                                                    stub1_1.getCode()));
    try
    {
      new Bid4WinObjectTypeStub1(code, null);
      fail("Should fail");
    }
    catch(RuntimeInstantiationException ex)
    {
      System.out.println(ex.getMessage());
      assertTrue("Wrong type",
          stub1_1 == Bid4WinObjectType.getType(Bid4WinObjectTypeStub1.class,
                                               stub1_1.getCode()));
    }
    Bid4WinObjectTypeStub2 stub2_1 = new Bid4WinObjectTypeStub2(code, null);
    assertEquals("Wrong code", code, stub2_1.getCode());
    assertNull("Wrong parent", stub2_1.getParent());
    assertTrue("Wrong type",
               stub1_1 == Bid4WinObjectType.getType(Bid4WinObjectTypeStub1.class,
                                                    stub1_1.getCode()));
    assertTrue("Wrong type",
               stub2_1 == Bid4WinObjectType.getType(Bid4WinObjectTypeStub2.class,
                                                    stub2_1.getCode()));

    code = "code" + this.useIndex();
    Bid4WinObjectTypeStub2 stub2_2 = new Bid4WinObjectTypeStub2(code, null);
    assertEquals("Wrong code", code, stub2_2.getCode());
    assertNull("Wrong parent", stub2_2.getParent());
    assertTrue("Wrong type",
               stub1_1 == Bid4WinObjectType.getType(Bid4WinObjectTypeStub1.class,
                                                    stub1_1.getCode()));
    assertTrue("Wrong type",
               stub2_1 == Bid4WinObjectType.getType(Bid4WinObjectTypeStub2.class,
                                                    stub2_1.getCode()));
    assertTrue("Wrong type",
               stub2_2 == Bid4WinObjectType.getType(Bid4WinObjectTypeStub2.class,
                                                    stub2_2.getCode()));

    code = "code" + this.useIndex();
    Bid4WinObjectTypeStub2 stub2_1_1 = new Bid4WinObjectTypeStub2(code, stub2_1);
    assertEquals("Wrong code", code, stub2_1_1.getCode());
    assertTrue("Wrong parent", stub2_1 == stub2_1_1.getParent());
    assertEquals("Wrong subtype set", 1, stub2_1.getSubtypes().size());
    assertTrue("Wrong subtype set", stub2_1.getSubtypes().contains(stub2_1_1));

    code = "code" + this.useIndex();
    Bid4WinObjectTypeStub2 stub2_1_2 = new Bid4WinObjectTypeStub2(code, stub2_1);
    assertEquals("Wrong code", code, stub2_1_2.getCode());
    assertTrue("Wrong parent", stub2_1 == stub2_1_2.getParent());
    assertEquals("Wrong subtype set", 2, stub2_1.getSubtypes().size());
    assertTrue("Wrong subtype set", stub2_1.getSubtypes().contains(stub2_1_1));
    assertTrue("Wrong subtype set", stub2_1.getSubtypes().contains(stub2_1_2));

    try
    {
      new Bid4WinObjectTypeStub2(stub2_2.getCode(), stub2_1);
      fail("Should fail");
    }
    catch(RuntimeInstantiationException ex)
    {
      System.out.println(ex.getMessage());
      assertEquals("Wrong subtype set", 2, stub2_1.getSubtypes().size());
      assertTrue("Wrong subtype set", stub2_1.getSubtypes().contains(stub2_1_1));
      assertTrue("Wrong subtype set", stub2_1.getSubtypes().contains(stub2_1_2));
    }
  }
  /**
   * Test of getPrimitive() method, of class Bid4WinObjectType.
   */
  @Test
  public void testGetPrimitive_0args()
  {
    String code = "code" + this.useIndex();
    Bid4WinObjectTypeStub1 stub1_1 = new Bid4WinObjectTypeStub1(code);
    code = "code" + this.useIndex();
    Bid4WinObjectTypeStub1 stub1_2 = new Bid4WinObjectTypeStub1(code, stub1_1);
    assertTrue("Wrong primitive", stub1_1 == stub1_2.getPrimitive());
    code = "code" + this.useIndex();
    Bid4WinObjectTypeStub1 stub1_3 = new Bid4WinObjectTypeStub1(code, stub1_2);
    assertTrue("Wrong primitive", stub1_1 == stub1_3.getPrimitive());
  }

  /**
   * Test of getPrimiviteTypes(Class) method, of class Bid4WinObjectType.
   */
  @Test
  public void testGetPrimiviteTypes_Class()
  {
    Bid4WinCollection<Bid4WinObjectTypeStub1> collection =
        Bid4WinObjectType.getTypes(Bid4WinObjectTypeStub1.class);
    Bid4WinSet<Bid4WinObjectTypeStub1> expected = new Bid4WinSet<Bid4WinObjectTypeStub1>();
    for(Bid4WinObjectTypeStub1 type : collection)
    {
      if(!type.hasParent())
      {
        expected.add(type);
      }
    }
    assertEquals("Wrong result", expected,
                 new Bid4WinSet<Bid4WinObjectTypeStub1>(
                     Bid4WinObjectType.getPrimiviteTypes(Bid4WinObjectTypeStub1.class)));
  }
  /**
   *
   * TODO A COMMENTER
   */
  @Test
  public void testGetSubtypes_0args()
  {
    String code = "code" + this.useIndex();
    Bid4WinObjectTypeStub1 stub1 = new Bid4WinObjectTypeStub1(code);
    code = "code" + this.useIndex();
    Bid4WinObjectTypeStub1 stub1_2 = new Bid4WinObjectTypeStub1(code, stub1);
    code = "code" + this.useIndex();
    Bid4WinObjectTypeStub1 stub1_3 = new Bid4WinObjectTypeStub1(code, stub1);

    Bid4WinSet<Bid4WinObjectTypeStub1> expected = new Bid4WinSet<Bid4WinObjectTypeStub1>(stub1_2, stub1_3);
    Bid4WinCollection<Bid4WinObjectTypeStub1> result = stub1.getSubtypes();
    assertEquals("Wrong sub types", expected, new Bid4WinSet<Bid4WinObjectTypeStub1>(result));
    try
    {
      result.clear();
      fail("Should fail");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   *
   * TODO A COMMENTER
   */
  @Test
  public void testAddSubtype_Bid4WinObjectType()
  {
    String code = "code" + this.useIndex();
    Bid4WinObjectTypeStub1 stub1 = new Bid4WinObjectTypeStub1(code);
    try
    {
      stub1.addSubtype(null);
      fail("Should fail");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   *
   * TODO A COMMENTER {@inheritDoc}
   * @see com.bid4win.commons.core.Bid4WinObjectTypeTester#testGetDefaultType_Class()
   */
  @Override
  @Test
  public void testGetDefaultType_Class()
  {
    // TODO Auto-generated method stub
  }
}
