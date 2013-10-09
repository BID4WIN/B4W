package com.bid4win.commons.core.collection;

import static org.junit.Assert.assertEquals;

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
public class Bid4WinSameTypeMapTest extends Bid4WinCoreTester
{
  /**
   * Test of putAll(Object[][]), of class Bid4WinSameTypeMap.
   */
  @Test
  public void testPutAll_ArrayOfArrayOfObject()
  {
    String[][] keysAndValues = new String[3][2];
    keysAndValues[0][0] = "1";
    keysAndValues[0][1] = "a";
    keysAndValues[1][0] = "2";
    keysAndValues[1][1] = "b";
    keysAndValues[2][0] = "3";
    keysAndValues[2][1] = "c";
    Bid4WinSameTypeMap<String> map1 = new Bid4WinSameTypeMap<String>();
    map1.putAll(keysAndValues);
    assertEquals("Wrong value", "a", map1.get("1"));
    assertEquals("Wrong value", "b", map1.get("2"));
    assertEquals("Wrong value", "c", map1.get("3"));

    keysAndValues = new String[][] {{"1", "a"}, {"2", "b"}, {"3", "c"}};
    Bid4WinSameTypeMap<String> map2 = new Bid4WinSameTypeMap<String>(keysAndValues);
    assertEquals("Maps should be the same", map1, map2);
  }
}
