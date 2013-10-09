package com.bid4win.commons.core.collection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.Bid4WinCoreTester;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR><BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class Bid4WinSetTest extends Bid4WinCoreTester
{
  /**
   * Test of equals(Object), of class Bid4WinSet.
   */
  @Test
  public void testEquals_Object()
  {
    HashSet<String> set = new HashSet<String>();
    set.add("1");
    set.add("2");
    set.add("3");
    Bid4WinSet<String> bid4WinSet1 = new Bid4WinSet<String>(set);
    Bid4WinSet<String> bid4WinSet2 = new Bid4WinSet<String>(set);
    assertTrue("Should be equal", bid4WinSet1.equals(set));
    assertTrue("Should be equal", set.equals(bid4WinSet1));
    assertTrue("Should be equal", bid4WinSet1.equals(bid4WinSet2));
    assertTrue("Should be equal", bid4WinSet2.equals(bid4WinSet1));
    bid4WinSet1.add("4");
    assertFalse("Should not be equal", bid4WinSet1.equals(set));
    assertFalse("Should not be equal", set.equals(bid4WinSet1));
    assertFalse("Should not be equal", bid4WinSet1.equals(bid4WinSet2));
    assertFalse("Should not be equal", bid4WinSet2.equals(bid4WinSet1));
  }
  /**
   * Test of remove(Object), of class Bid4WinSet.
   */
  @Test
  public void testRemove_Object()
  {
    Bid4WinSet<String> set = new Bid4WinSet<String>();
    String string = "value";
    set.add(string);
    assertTrue("Should have been removed", set.remove(string));
  }
  /**
   * Test of clone(), of class Bid4WinSet.
   */
  @Test
  public void testClone_0args()
  {
    Bid4WinSet<String> set = new Bid4WinSet<String>();
    Bid4WinSet<String> clone = set.clone();
    assertTrue("Clone should be the same", set.equals(clone));
    set.add("a");
    set.add("b");
    set.add("c");
    set.add("d");
    clone = set.clone();
    assertTrue("Clone should be the same", set.equals(clone));
  }

  /**
   * Test of "for each", of class Bid4WinSet.
   */
  @Test
  public void testForEach_0args()
  {
    System.out.println("### \"for each\"");
    Bid4WinSet<String> set = new Bid4WinSet<String>();
    set.add("1");
    set.add("2");
    set.add("3");
    set.add("4");

    for(String string : new Bid4WinSet<String>(set))
    {
      System.out.println(string);
    }
    System.out.println("### \"for each\"");
  }
}
