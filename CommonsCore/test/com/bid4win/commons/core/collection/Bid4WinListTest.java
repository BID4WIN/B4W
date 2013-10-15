package com.bid4win.commons.core.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.comparator.Bid4WinComparator;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class Bid4WinListTest extends Bid4WinCollectionAbstractTester<Bid4WinList<String>, String>
{
  /**
   *
   * TODO A COMMENTER {@inheritDoc}
   * @see com.bid4win.commons.core.collection.Bid4WinCollectionAbstractTester#testCheckProtection()
   */
  @Override
  @Test
  public void testCheckProtection()
  {
    super.testCheckProtection();
    Bid4WinList<String> list = this.createCollection();
    int i = 0;
    String element1 = this.createElement(i++);
    assertTrue("Element should have been added", list.add(element1));
    assertTrue("Element should have been added", list.contains(element1));

    String element2 = this.createElement(i++);
    list.protectFromNothing();

    try
    {
      list.add(0, element2);
      fail("Should fail with protected collection");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      list.addAll(0, new Bid4WinList<String>(element2));
      fail("Should fail with protected collection");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      list.remove(0);
      fail("Should fail with protected collection");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      list.removeFirst();
      fail("Should fail with protected collection");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      list.removeLast();
      fail("Should fail with protected collection");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      list.set(0, element2);
      fail("Should fail with protected collection");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      list.sort();
      fail("Should fail with protected collection");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      list.sort(Bid4WinComparator.getInstance());
      fail("Should fail with protected collection");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }

    Bid4WinListIterator<String> iterator = list.listIterator();
    assertTrue("Iterator should also be protected",
               iterator.getProtection() == list.getProtection());
    iterator.next();
    try
    {
      iterator.remove();
      fail("Should fail with protected collection");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    iterator = list.listIterator(0);
    assertTrue("Iterator should also be protected",
               iterator.getProtection() == list.getProtection());
    iterator.next();
    try
    {
      iterator.remove();
      fail("Should fail with protected collection");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    Bid4WinList<String> subList = list.subList(0);
    assertTrue("Sub list should also be protected",
               subList.getProtection() == list.getProtection());
    try
    {
      subList.remove(0);
      fail("Should fail with protected collection");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    subList = list.subList(0, 1);
    assertTrue("Sub list should also be protected",
               subList.getProtection() == list.getProtection());
    try
    {
      subList.remove(0);
      fail("Should fail with protected collection");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }

    assertEquals("Collection should not have been modified", 1, list.size());
    assertTrue("Collection should not have been modified", list.contains(element1));
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.collection.Bid4WinCollectionAbstractTester#createCollection()
   */
  @Override
  public Bid4WinList<String> createCollection()
  {
    return new Bid4WinList<String>();
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
