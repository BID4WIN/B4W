package com.bid4win.commons.core.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.collection.Bid4WinMap.Bid4WinEntry;
import com.bid4win.commons.core.collection.Bid4WinMap.Bid4WinEntryIterator;
import com.bid4win.commons.core.collection.Bid4WinMap.Bid4WinEntrySet;
import com.bid4win.commons.core.security.ProtectableObjectTester;
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
public class Bid4WinMapTest extends ProtectableObjectTester
{
  /**
   *
   * TODO A COMMENTER {@inheritDoc}
   * @see com.bid4win.commons.core.security.ProtectableObjectTester#testCheckProtection()
   */
  @Override
  @Test
  public void testCheckProtection()
  {
    Bid4WinMap<String, String> map = new Bid4WinMap<String, String>();

    String key1 = "key1";
    String value1 = "value1";
    map.put(key1, value1);
    assertEquals("Element should have been added", value1, map.get(key1));

    String key2 = "key2";
    String value2 = "value2";
    map.protectFromNothing();

    try
    {
      map.clear();
      fail("Should fail with protected map");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      map.put(key2, value2);
      fail("Should fail with protected map");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      map.putAll(new Bid4WinMap<String, String>(key2, value2));
      fail("Should fail with protected map");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      map.remove(key1);
      fail("Should fail with protected map");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }

    Bid4WinEntrySet<String, String> entrySet = map.entrySet();
    assertTrue("Entry set should also be protected",
               entrySet.getProtection() == map.getProtection());
    Bid4WinEntryIterator<String, String> entryIterator = entrySet.iterator();
    assertTrue("Entry iterator should also be protected",
               entryIterator.getProtection() == entrySet.getProtection());
    Bid4WinEntry<String, String> entry = entryIterator.next();
    assertTrue("Entry should also be protected",
               entry.getProtection() == entryIterator.getProtection());
    try
    {
      entrySet.clear();
      fail("Should fail with protected map");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      entryIterator.remove();
      fail("Should fail with protected map");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      entry.setValue(value2);
      fail("Should fail with protected map");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }

    entrySet = map.entrySetProtected();
    assertNotNull("Entry set should also be protected", entrySet.getProtection());
    assertFalse("Entry set be protected on its own",
               entrySet.getProtection() == map.getProtection());
    entryIterator = entrySet.iterator();
    assertTrue("Entry iterator should also be protected",
               entryIterator.getProtection() == entrySet.getProtection());
    entry = entryIterator.next();
    assertTrue("Entry should also be protected",
               entry.getProtection() == entryIterator.getProtection());
    try
    {
      entrySet.clear();
      fail("Should fail with protected map");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      entryIterator.remove();
      fail("Should fail with protected map");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      entry.setValue(value2);
      fail("Should fail with protected map");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }

    Bid4WinSet<String> keySet = map.keySet();
    assertTrue("Key set should also be protected",
               keySet.getProtection() == map.getProtection());
    Bid4WinIterator<String> keyIterator = keySet.iterator();
    assertTrue("Key iterator should also be protected",
               entryIterator.getProtection() == entrySet.getProtection());
    keyIterator.next();
    try
    {
      keySet.clear();
      fail("Should fail with protected map");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      keyIterator.remove();
      fail("Should fail with protected map");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }

    Bid4WinCollection<String> values = map.values();
    assertTrue("Values should also be protected",
               values.getProtection() == map.getProtection());
    Bid4WinIterator<String> valueIterator = values.iterator();
    assertTrue("Value iterator should also be protected",
               valueIterator.getProtection() == values.getProtection());
    valueIterator.next();
    try
    {
      values.clear();
      fail("Should fail with protected map");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      valueIterator.remove();
      fail("Should fail with protected map");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }

    try
    {
      map.getInternal();
      fail("Should fail with protected map");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }

    assertEquals("Map should not have been modified", 1, map.size());
    assertEquals("Map should not have been modified", value1, map.get(key1));
  }

  /**
   * Test of clone(boolean), of class Bid4WinMap.
   */
  @Test
  public void testClone_boolean()
  {
    Bid4WinMap<String, String> map = new Bid4WinMap<String, String>();
    Bid4WinMap<String, String> clone1 = map.clone(false);
    assertTrue("Clone should be the same", map.equals(clone1));
    int i = 0;
    map.put("" + ++i, "" + i);
    map.put("" + ++i, "" + i);
    map.put("" + ++i, "" + i);
    map.put("" + ++i, "" + i);
    Bid4WinMap<String, String> clone2 = map.clone(false);
    assertEquals("Old clone should be the same", 0, clone1.size());
    assertFalse("Old clone should be the same", map.equals(clone1));
    assertEquals("Clone should be the same", i, clone2.size());
    assertTrue("Clone should be the same", map.equals(clone2));
    String element5 = "" + ++i;
    String element6 = "" + ++i;
    clone1.put(element5, element5);
    clone2.put(element6, element6);
    assertEquals("Collection should not have changed", i - 2, map.size());
    assertFalse("Collection should not have changed", map.containsKey(element5));
    assertFalse("Collection should not have changed", map.containsKey(element6));
    assertEquals("Clone should have changed", 1, clone1.size());
    assertTrue("Clone should have changed", clone1.containsKey(element5));
    assertFalse("Clone should have changed", clone1.containsKey(element6));
    assertEquals("Clone should have changed", i - 1, clone2.size());
    assertFalse("Clone should have changed", clone2.containsKey(element5));
    assertTrue("Clone should have changed", clone2.containsKey(element6));

    Bid4WinMap<String, String> clone3 = map.clone(true);
    String element7 = "" + ++i;
    map.put(element7, element7);
    assertEquals("Collection should have changed", i - 2, map.size());
    assertTrue("Collection should have changed", map.containsKey(element7));
    assertTrue("Clone should be the same", map.equals(clone3));

    String element8 = "" + ++i;
    try
    {
      clone3.put(element8, element8);
      fail("Collection should be protected");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    assertEquals("Collection not should have changed", i - 3, map.size());
    assertFalse("Collection not should have changed", map.containsKey(element8));
  }
}
