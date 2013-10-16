package com.bid4win.commons.persistence.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.Bid4WinCoreTester;
import com.bid4win.commons.core.Bid4WinObjectTypeStub1;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class Bid4WinEmbeddableWithTypeTest extends Bid4WinCoreTester
{
  /**
   * Test of Bid4WinEmbeddableWithType(TYPE) method, of class Bid4WinEmbeddableWithType.
   */
  @Test
  public void testBid4WinEmbeddableWithType_TYPE()
  {
    Bid4WinObjectTypeStub1 type = Bid4WinObjectTypeStub1.TYPE2;
    try
    {
      Bid4WinEmbeddableWithTypeStub object = new Bid4WinEmbeddableWithTypeStub(type);
      assertEquals("Wrong type", type, object.getType());
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Shoud not fail: " + ex.getMessage());
    }
    try
    {
      new Bid4WinEmbeddableWithTypeStub(null);
      fail("Shoud fail with null type");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }

  /**
   * Test of equalsInternal(CLASS) method, of class Bid4WinEmbeddableWithType.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testEqualsInternal_CLASS() throws Bid4WinException
  {
    Bid4WinEmbeddableWithTypeStub object1 = new Bid4WinEmbeddableWithTypeStub(Bid4WinObjectTypeStub1.TYPE1);
    Bid4WinEmbeddableWithTypeStub object2 = new Bid4WinEmbeddableWithTypeStub(Bid4WinObjectTypeStub1.TYPE2);
    assertFalse("Wrong result", object1.equalsInternal(object2));
    assertFalse("Wrong result", object2.equalsInternal(object1));
    assertTrue("Wrong result", object1.equalsInternal(object1));
  }
}
