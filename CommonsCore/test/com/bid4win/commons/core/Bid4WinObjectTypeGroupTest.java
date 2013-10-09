package com.bid4win.commons.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.RuntimeArgumentException;
import com.bid4win.commons.core.exception.RuntimeInstantiationException;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 * Classe de test des groupes de types d'objets de base du projet<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class Bid4WinObjectTypeGroupTest extends Bid4WinCoreTester
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
    return Bid4WinObjectTypeGroupTest.index++;
  }

  /**
   * Test of Bid4WinObjectTypeGroup(String, CLASS...) method, of class Bid4WinObjectTypeGroup.
   * @throws Bid4WinException Issue not expected during this test
   */
  @SuppressWarnings("deprecation")
  @Test
  public void testBid4WinObjectType_String_CLASS_etc() throws Bid4WinException
  {
    String code = "code" + this.useIndex();
    Bid4WinObjectTypeGroupStub stub1 = new Bid4WinObjectTypeGroupStub(code);
    assertEquals("Wrong code", code, stub1.getCode());
    try
    {
      stub1.getParent();
      fail("Should fail");
    }
    catch(RuntimeArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
    assertEquals("Wrong parent set", 0, stub1.getParentSet().size());
    assertTrue("Wrong type",
               stub1 == Bid4WinObjectType.getType(Bid4WinObjectTypeGroupStub.class,
                                                    stub1.getCode()));
    try
    {
      new Bid4WinObjectTypeGroupStub(code);
    }
    catch(RuntimeInstantiationException ex)
    {
      System.out.println(ex.getMessage());
      assertTrue("Wrong type",
                 stub1 == Bid4WinObjectType.getType(Bid4WinObjectTypeGroupStub.class,
                                                    stub1.getCode()));
    }

    code = "code" + this.useIndex();
    Bid4WinObjectTypeGroupStub stub2 = new Bid4WinObjectTypeGroupStub(code);
    assertEquals("Wrong code", code, stub2.getCode());
    try
    {
      stub2.getParent();
      fail("Should fail");
    }
    catch(RuntimeArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
    assertEquals("Wrong parent set", 0, stub2.getParentSet().size());
    assertTrue("Wrong type",
               stub1 == Bid4WinObjectType.getType(Bid4WinObjectTypeGroupStub.class,
                                                  stub1.getCode()));
    assertTrue("Wrong type",
               stub2 == Bid4WinObjectType.getType(Bid4WinObjectTypeGroupStub.class,
                                                  stub2.getCode()));

    code = "code" + this.useIndex();
    Bid4WinObjectTypeGroupStub stub3 = new Bid4WinObjectTypeGroupStub(code, stub1, stub2, stub2, stub1);
    assertEquals("Wrong code", code, stub3.getCode());
    try
    {
      stub3.getParent();
      fail("Should fail");
    }
    catch(RuntimeArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
    assertEquals("Wrong parent set", 2, stub3.getParentSet().size());
    assertTrue("Wrong parent", stub3.getParentSet().contains(stub1));
    assertTrue("Wrong parent", stub3.getParentSet().contains(stub2));
    assertTrue("Wrong type",
               stub1 == Bid4WinObjectType.getType(Bid4WinObjectTypeGroupStub.class,
                                                  stub1.getCode()));
    assertTrue("Wrong type",
               stub2 == Bid4WinObjectType.getType(Bid4WinObjectTypeGroupStub.class,
                                                  stub2.getCode()));
    assertTrue("Wrong type",
               stub3 == Bid4WinObjectType.getType(Bid4WinObjectTypeGroupStub.class,
                                                  stub3.getCode()));
    assertEquals("Wrong subtype set", 1, stub1.getSubtypeSet().size());
    assertTrue("Wrong subtype set", stub1.getSubtypeSet().contains(stub3));
    assertEquals("Wrong subtype set", 1, stub2.getSubtypeSet().size());
    assertTrue("Wrong subtype set", stub2.getSubtypeSet().contains(stub3));

    try
    {
      new Bid4WinObjectTypeGroupStub(code, stub1);
    }
    catch(RuntimeInstantiationException ex)
    {
      System.out.println(ex.getMessage());
      assertEquals("Wrong subtype set", 1, stub1.getSubtypeSet().size());
      assertTrue("Wrong subtype set", stub1.getSubtypeSet().contains(stub3));
    }
  }
  /**
   * Test of getPrimitiveSet() method, of class Bid4WinObjectType.
   */
  @SuppressWarnings("deprecation")
  @Test
  public void testGetPrimitiveSet_0args()
  {
    String code = "code" + this.useIndex();
    Bid4WinObjectTypeGroupStub stub1 = new Bid4WinObjectTypeGroupStub(code);
    code = "code" + this.useIndex();
    Bid4WinObjectTypeGroupStub stub2 = new Bid4WinObjectTypeGroupStub(code);
    code = "code" + this.useIndex();
    Bid4WinObjectTypeGroupStub stub3 = new Bid4WinObjectTypeGroupStub(
        code, stub1, stub2);
    code = "code" + this.useIndex();
    Bid4WinObjectTypeGroupStub stub4 = new Bid4WinObjectTypeGroupStub(
        code, stub1, stub3, stub2);
    try
    {
      stub1.getPrimitive();
      fail("Should fail");
    }
    catch(RuntimeArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      stub2.getPrimitive();
      fail("Should fail");
    }
    catch(RuntimeArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      stub3.getPrimitive();
      fail("Should fail");
    }
    catch(RuntimeArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      stub4.getPrimitive();
      fail("Should fail");
    }
    catch(RuntimeArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
    assertEquals("Wrong primitive", new Bid4WinSet<Bid4WinObjectTypeGroupStub>(stub1),
                 stub1.getPrimitiveSet());
    assertEquals("Wrong primitive", new Bid4WinSet<Bid4WinObjectTypeGroupStub>(stub2),
                 stub2.getPrimitiveSet());
    assertEquals("Wrong primitive", new Bid4WinSet<Bid4WinObjectTypeGroupStub>(stub1, stub2),
                 stub3.getPrimitiveSet());
    assertEquals("Wrong primitive", new Bid4WinSet<Bid4WinObjectTypeGroupStub>(stub1, stub2),
                 stub4.getPrimitiveSet());
  }
}
