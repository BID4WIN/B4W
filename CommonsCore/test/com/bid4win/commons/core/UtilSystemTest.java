package com.bid4win.commons.core;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 * TODO COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class UtilSystemTest extends Bid4WinCoreTester
{
  /**
   * Test of getStackTraceElement(int) method, of class UtilSystem.
   */
  @Test
  public void testGetStackTraceElement_int()
  {
    StackTraceElement result = UtilSystem.getStackTraceElement(0);
    assertEquals("Wrong stack class", this.getClass().getName(), result.getClassName());
    assertEquals("Wrong stack method", "testGetStackTraceElement_int", result.getMethodName());
    result = UtilSystem.getStackTraceElement(-1);
    assertEquals("Wrong stack class", UtilSystem.class.getName(), result.getClassName());
    assertEquals("Wrong stack method", "getStackTraceElement", result.getMethodName());
  }

  /**
   * Test of getStackTraceElement(Throwable, int) method, of class UtilSystem.
   */
  @Test
  public void testGetStackTraceElement_Throwable_int()
  {
    StackTraceElement result = UtilSystem.getStackTraceElement(new Exception(), 0);
    assertEquals("Wrong stack class", this.getClass().getName(), result.getClassName());
    assertEquals("Wrong stack method", "testGetStackTraceElement_Throwable_int", result.getMethodName());
    result = UtilSystem.getStackTraceElement(-1);
    assertEquals("Wrong stack class", UtilSystem.class.getName(), result.getClassName());
    assertEquals("Wrong stack method", "getStackTraceElement", result.getMethodName());
  }
}