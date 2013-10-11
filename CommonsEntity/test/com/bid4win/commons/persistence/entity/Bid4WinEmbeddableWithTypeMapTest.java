package com.bid4win.commons.persistence.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.Bid4WinObjectTypeStub1;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class Bid4WinEmbeddableWithTypeMapTest extends Bid4WinEntityTester<AccountAbstractStub, EntityGeneratorStub>
{
  /**
   * Test of Bid4WinEmbeddableWithTypeMap(EMBEDDED) method, of class Bid4WinEmbeddableWithTypeMap.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testBid4WinEmbeddableWithTypeMap_EMBEDDED() throws Bid4WinException
  {
    Bid4WinEmbeddableWithTypeStub embedded1 = new Bid4WinEmbeddableWithTypeStub(Bid4WinObjectTypeStub1.TYPE1);
    Bid4WinEmbeddableWithTypeStub embedded2 = new Bid4WinEmbeddableWithTypeStub(Bid4WinObjectTypeStub1.TYPE2);
    Bid4WinEmbeddableWithTypeStub embedded3 = new Bid4WinEmbeddableWithTypeStub(Bid4WinObjectTypeStub1.TYPE3);
    try
    {
      Bid4WinEmbeddableWithTypeMapStub map = new Bid4WinEmbeddableWithTypeMapStub(embedded1);
      assertEquals("Wrong objects", new Bid4WinSet<Bid4WinEmbeddableWithTypeStub>(embedded1),
                   map.getEmbeddedSet());
      map = new Bid4WinEmbeddableWithTypeMapStub(embedded3, embedded2, embedded1);
      assertEquals("Wrong objects", new Bid4WinSet<Bid4WinEmbeddableWithTypeStub>(embedded1, embedded2, embedded3),
                   map.getEmbeddedSet());
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Shoud not fail: " + ex.getMessage());
    }
    try
    {
      new Bid4WinEmbeddableWithTypeMapStub(embedded2);
      fail("Shoud fail without default type");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      new Bid4WinEmbeddableWithTypeMapStub(embedded2, embedded1, embedded2);
      fail("Shoud fail with twice the same type");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }

  /**
   * Test of equalsInternal(CLASS) method, of class Bid4WinEmbeddableWithTypeMap.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testEqualsInternal_CLASS() throws Bid4WinException
  {
    Bid4WinEmbeddableWithTypeStub embedded1 = new Bid4WinEmbeddableWithTypeStub(Bid4WinObjectTypeStub1.TYPE1);
    Bid4WinEmbeddableWithTypeStub embedded2 = new Bid4WinEmbeddableWithTypeStub(Bid4WinObjectTypeStub1.TYPE2);
    Bid4WinEmbeddableWithTypeStub embedded3 = new Bid4WinEmbeddableWithTypeStub(Bid4WinObjectTypeStub1.TYPE3);

    Bid4WinEmbeddableWithTypeMapStub map1 = new Bid4WinEmbeddableWithTypeMapStub(embedded1);
    Bid4WinEmbeddableWithTypeMapStub map2 = new Bid4WinEmbeddableWithTypeMapStub(embedded1);
    assertTrue("Wrong result", map1.equalsInternal(map2));
    assertTrue("Wrong result", map2.equalsInternal(map1));

    map1.addEmbedded(embedded2);
    assertFalse("Wrong result", map1.equalsInternal(map2));
    assertFalse("Wrong result", map2.equalsInternal(map1));

    map2.addEmbedded(embedded3);
    assertFalse("Wrong result", map1.equalsInternal(map2));
    assertFalse("Wrong result", map2.equalsInternal(map1));

    map1.addEmbedded(embedded3);
    assertFalse("Wrong result", map1.equalsInternal(map2));
    assertFalse("Wrong result", map2.equalsInternal(map1));

    map2.addEmbedded(embedded2);
    assertTrue("Wrong result", map1.equalsInternal(map2));
    assertTrue("Wrong result", map2.equalsInternal(map1));
  }
}
