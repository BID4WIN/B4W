package com.bid4win.commons.core.comparator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.Bid4WinCoreTester;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 * Classe de test du comparateur par défaut du projet<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class Bid4WinComparatorTest extends Bid4WinCoreTester
{
  /**
   * Test of equals(Object, Object) method, of class Bid4WinComparator.
   */
  @Test
  public void testEquals_Object_Object()
  {
    assertTrue(Bid4WinComparator.getInstance().equals("a", "a"));
    assertFalse(Bid4WinComparator.getInstance().equals(null, "a"));
    assertFalse(Bid4WinComparator.getInstance().equals("a", null));
    assertTrue(Bid4WinComparator.getInstance().equals(null, null));
    assertFalse(Bid4WinComparator.getInstance().equals("a", "b"));
    assertFalse(Bid4WinComparator.getInstance().equals("a", 1));
    assertFalse(Bid4WinComparator.getInstance().equals(1, "a"));
  }

  /**
   * Test of compare(Object, Object) method, of class Bid4WinComparator.
   */
  @Test
  public void testCompare_Object_Object()
  {
    assertEquals(0, Bid4WinComparator.getInstance().compare(null, null));
    assertEquals(-1, Bid4WinComparator.getInstance().compare(null, ""));
    assertEquals(1, Bid4WinComparator.getInstance().compare("", null));
    StringBuffer buffer1 = new StringBuffer("a");
    StringBuffer buffer2 = new StringBuffer(buffer1);
    assertEquals(buffer1.hashCode() - buffer2.hashCode(),
                 Bid4WinComparator.getInstance().compare(buffer1, buffer2));
    assertEquals(0, Bid4WinComparator.getInstance().compare(buffer1.toString(),
                                                            buffer2.toString()));
    assertEquals(-1, Bid4WinComparator.getInstance().compare("a", "b"));
    assertEquals(1, Bid4WinComparator.getInstance().compare("b", "a"));
  }
}
