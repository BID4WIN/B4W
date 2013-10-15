package com.bid4win.commons.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 * Classe de test des objets de base du projet<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class Bid4WinObjectTest extends Bid4WinCoreTester
{
  /**
   * Test of equals(Object) method, of class Bid4WinObject.
   */
  @Test
  public void testEquals_Object()
  {
    Bid4WinObjectStub stub1 = new Bid4WinObjectStub("key", "value");
    Bid4WinObjectStub stub2 = new Bid4WinObjectStub("key", "value");
    assertTrue(stub1.equals(stub1));
    assertTrue(stub1.equals(stub2));
    assertTrue(stub2.equals(stub1));
    assertFalse(stub1.equals(null));
    assertFalse(stub1.equals("TOTO"));
    stub2 = new Bid4WinObjectStub("key1", "value");
    assertFalse(stub1.equals(stub2));
    assertFalse(stub2.equals(stub1));
  }
  /**
   * Test of render() method, of class Bid4WinObject.
   */
  @Test
  public void testRender_0args()
  {
    Bid4WinObjectStub stub = new Bid4WinObjectStub("key", "value");
    assertEquals("KEY=" + stub.getKey() + " VALUE=" + stub.getValue(),
                 stub.render().toString());
  }
}
