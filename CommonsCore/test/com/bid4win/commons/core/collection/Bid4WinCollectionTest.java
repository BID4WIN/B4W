package com.bid4win.commons.core.collection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

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
public class Bid4WinCollectionTest extends Bid4WinCollectionAbstractTester<Bid4WinCollection<String>, String>
{
  /**
   * Test of equals(Object), of class Bid4WinSet.
   * @see com.bid4win.commons.core.collection.Bid4WinCollectionAbstractTester#testEquals_Object()
   */
  @Override
  @Test
  public void testEquals_Object()
  {
    super.testEquals_Object();
    HashSet<String> set = new HashSet<String>();
    set.add("1");
    set.add("2");
    set.add("3");
    Bid4WinSet<String> bid4WinSet1 = new Bid4WinSet<String>(set);
    assertTrue("Should be equal", bid4WinSet1.equals(set));
    assertTrue("Should be equal", set.equals(bid4WinSet1));
    bid4WinSet1.add("4");
    assertFalse("Should not be equal", bid4WinSet1.equals(set));
    assertFalse("Should not be equal", set.equals(bid4WinSet1));
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.collection.Bid4WinCollectionAbstractTester#createCollection()
   */
  @Override
  public Bid4WinCollection<String> createCollection()
  {
    return new Bid4WinCollection<String>();
  }
  /**
   *
   * TODO A COMMENTER
   * @param index {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.collection.Bid4WinCollectionAbstractTester#createElement(int)
   */
  @Override
  public String createElement(int index)
  {
    return "element " + index;
  }
}
