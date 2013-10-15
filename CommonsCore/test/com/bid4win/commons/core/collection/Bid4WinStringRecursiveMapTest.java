package com.bid4win.commons.core.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
public class Bid4WinStringRecursiveMapTest
{
  /**
   * Test of Bid4WinStringRecursiveMap(Bid4WinStringRecursiveMap, boolean),
   * of class Bid4WinStringRecursiveMap.
   */
  @Test
  public void testBid4WinStringRecursiveMap_Bid4WinStringRecursiveMap_boolean()
  {
    Bid4WinStringRecursiveMap map1 = new Bid4WinStringRecursiveMap();
    map1.put("1", null);
    map1.put("2", null);
    Bid4WinStringRecursiveMap map2 = new Bid4WinStringRecursiveMap();
    map2.put("1", null);
    Bid4WinStringRecursiveMap map2_1 = new Bid4WinStringRecursiveMap();
    map2.put("2", map2_1);
    map2_1.put("1", null);
    map2_1.put("2", new Bid4WinStringRecursiveMap());

    Bid4WinStringRecursiveMap map = new Bid4WinStringRecursiveMap("1", map1);
    map.put("2", map2);

    Bid4WinStringRecursiveMap result = new Bid4WinStringRecursiveMap(map, false);
    assertTrue("Wrong result size", map.size() == result.size());
    assertTrue("Wrong result size", map.get("1") == result.get("1"));
    assertTrue("Wrong result size", map.get("2") == result.get("2"));

    result = new Bid4WinStringRecursiveMap(map, true);
  }
  /**
   * Test of add(String, Bid4WinStringRecursiveMap),
   * of class Bid4WinStringRecursiveMap.
   */
  @Test
  public void testAdd_String_Bid4WinStringRecursiveMap()
  {
    Bid4WinStringRecursiveMap map = new Bid4WinStringRecursiveMap();

    map.add("1", null);
    assertNull("Wrong result", map.get("1"));

    Bid4WinStringRecursiveMap map1 = new Bid4WinStringRecursiveMap();
    map1.add("0", null);
    Bid4WinStringRecursiveMap map1_1 = new Bid4WinStringRecursiveMap();
    map1.add("1", map1_1);
    Bid4WinStringRecursiveMap map1_2 = new Bid4WinStringRecursiveMap();
    map1.add("2", map1_2);
    Bid4WinStringRecursiveMap map1_2_1 = new Bid4WinStringRecursiveMap();
    map1_2.add("1", map1_2_1);

    map.add("1", map1);
    assertTrue("Wrong result", map1 == map.get("1"));

    map.add("1", null);
    assertTrue("Wrong result", map1 == map.get("1"));

    Bid4WinStringRecursiveMap map2 = new Bid4WinStringRecursiveMap();
    map1.add("0", null);
    Bid4WinStringRecursiveMap map2_1 = new Bid4WinStringRecursiveMap();
    map2.add("1", map2_1);
    Bid4WinStringRecursiveMap map2_2 = new Bid4WinStringRecursiveMap();
    map2.add("2", map2_2);
    Bid4WinStringRecursiveMap map2_2_1 = new Bid4WinStringRecursiveMap();
    map2_2.add("1", map2_2_1);

    map.add("2", map2);
    assertTrue("Wrong result", map1 == map.get("1"));
    assertTrue("Wrong result", map2 == map.get("2"));

    Bid4WinStringRecursiveMap map3 = new Bid4WinStringRecursiveMap();
    map3.add("0", null);
    Bid4WinStringRecursiveMap map3_1 = new Bid4WinStringRecursiveMap();
    map3.add("1", map3_1);
    Bid4WinStringRecursiveMap map3_2 = new Bid4WinStringRecursiveMap();
    map3.add("2", map3_2);
    Bid4WinStringRecursiveMap map3_2_1 = new Bid4WinStringRecursiveMap();
    map3_2.add("1", map3_2_1);
    Bid4WinStringRecursiveMap map3_2_1_1 = new Bid4WinStringRecursiveMap();
    map3_2_1.add("1", map3_2_1_1);
    Bid4WinStringRecursiveMap map3_2_2 = new Bid4WinStringRecursiveMap();
    map3_2.add("2", map3_2_2);

    map.add("1", map3);
    assertTrue("Wrong result", map1 == map.get("1"));
    assertTrue("Wrong result", map2 == map.get("2"));
    assertTrue("Wrong result", map1_1 == map1.get("1"));
    assertEquals("Wrong size", 0, map1_1.size());
    assertTrue("Wrong result", map1_2 == map1.get("2"));
    assertTrue("Wrong result", map1_2_1 == map1_2.get("1"));
    assertTrue("Wrong result", map3_2_1_1 == map1_2_1.get("1"));
    assertTrue("Wrong result", map3_2_2 == map1_2.get("2"));

    map.add("1", map1);
    assertTrue("Wrong result", map1 == map.get("1"));
    assertTrue("Wrong result", map2 == map.get("2"));
    assertTrue("Wrong result", map1_1 == map1.get("1"));
    assertEquals("Wrong size", 0, map1_1.size());
    assertTrue("Wrong result", map1_2 == map1.get("2"));
    assertTrue("Wrong result", map1_2_1 == map1_2.get("1"));
    assertTrue("Wrong result", map3_2_1_1 == map1_2_1.get("1"));
    assertTrue("Wrong result", map3_2_2 == map1_2.get("2"));
  }
}
