package com.bid4win.commons.testing;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.Description;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinTestTraceListenerTest
{
  /** TODO A COMMENTER */
  private Bid4WinTestTraceListener listener = new Bid4WinTestTraceListener(
      Bid4WinTestTraceListenerTest.class.getName());
  /**
   * TODO A COMMENTER
   */
  @Test
  public void test()
  {
    Description description = Description.createTestDescription(
        Bid4WinTestTraceListenerTest.class, "");
    assertEquals("()", this.listener.getTestedMethod(description));

    description = Description.createTestDescription(
        Bid4WinTestTraceListenerTest.class, "_test");
    assertEquals("(test)", this.listener.getTestedMethod(description));
    description = Description.createTestDescription(
        Bid4WinTestTraceListenerTest.class, "test");
    assertEquals("()", this.listener.getTestedMethod(description));

    description = Description.createTestDescription(
        Bid4WinTestTraceListenerTest.class, "Bid4WinTestTraceListener");
    assertEquals("Bid4WinTestTraceListener()", this.listener.getTestedMethod(description));
    description = Description.createTestDescription(
        Bid4WinTestTraceListenerTest.class, "testBid4WinTestTraceListener");
    assertEquals("Bid4WinTestTraceListener()", this.listener.getTestedMethod(description));

    description = Description.createTestDescription(
        Bid4WinTestTraceListenerTest.class, "testToto");
    assertEquals("toto()", this.listener.getTestedMethod(description));
    description = Description.createTestDescription(
        Bid4WinTestTraceListenerTest.class, "testToto_");
    assertEquals("toto()", this.listener.getTestedMethod(description));

    description = Description.createTestDescription(
        Bid4WinTestTraceListenerTest.class, "testToto_0args");
    assertEquals("toto()", this.listener.getTestedMethod(description));

    description = Description.createTestDescription(
        Bid4WinTestTraceListenerTest.class, "testToto_int");
    assertEquals("toto(int)", this.listener.getTestedMethod(description));

    description = Description.createTestDescription(
        Bid4WinTestTraceListenerTest.class, "testToto_int_String");
    assertEquals("toto(int, String)", this.listener.getTestedMethod(description));

    description = Description.createTestDescription(
        Bid4WinTestTraceListenerTest.class, "testToto_int_ArrayOfArrayOfString_etc");
    assertEquals("toto(int, String[][]...)", this.listener.getTestedMethod(description));

    description = Description.createTestDescription(
        Bid4WinTestTraceListenerTest.class, "testToto_int_ArrayOfCollectionOfArrayOfint");
    assertEquals("toto(int, Collection<int[]>[])", this.listener.getTestedMethod(description));
  }
}
