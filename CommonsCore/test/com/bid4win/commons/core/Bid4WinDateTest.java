package com.bid4win.commons.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 * Classe de test d'une date<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class Bid4WinDateTest extends Bid4WinCoreTester
{
  /**
   * Test of getDaysBetween(Date) method, of class Date.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testGetDaysBetween_Date() throws Bid4WinException
  {
    Bid4WinDate date1 = this.getGenerator().createDateTime("01/01/2000 00:00:01:000");
    Bid4WinDate date2 = this.getGenerator().createDateTime("31/12/1999 23:59:59:000");
    assertEquals("01/01/2000 00:00:01 -> 31/12/1999 23:59:59",
                 -1, date1.getDaysBetween(date2));
    assertEquals("31/12/1999 23:59:59 -> 01/01/2000 00:00:01",
                 1, date2.getDaysBetween(date1));
    date2 = this.getGenerator().createDateTime("31/01/2000 23:59:59:000");
    assertEquals("01/01/2000 00:00:01 -> 31/01/2000 23:59:59",
                 30, date1.getDaysBetween(date2));
    assertEquals("31/01/2000 23:59:59 -> 01/01/2000 00:00:01",
                 -30, date2.getDaysBetween(date1));
    date2 = this.getGenerator().createDateTime("01/01/2000 23:59:59:000");
    assertEquals("01/01/2000 00:00:01 -> 01/01/2000 23:59:59",
                 0, date1.getDaysBetween(date2));
    assertEquals("01/01/2000 23:59:59 -> 01/01/2000 00:00:01",
                 0, date2.getDaysBetween(date1));
    assertEquals("01/01/2000 00:00:01 -> 01/01/2000 00:00:01",
                 0, date1.getDaysBetween(date1));
  }
  /**
   * Test of getTimeBetween(Date) method, of class Date.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testGetTimeBetween_Date() throws Bid4WinException
  {
    Bid4WinDate date1 = this.getGenerator().createDateTime("01/01/2000 00:00:01:000");
    Bid4WinDate date2 = this.getGenerator().createDateTime("31/12/1999 23:59:59:000");
    assertEquals("01/01/2000 00:00:01 -> 31/12/1999 23:59:59",
                 -2*1000, date1.getTimeBetween(date2));
    assertEquals("31/12/1999 23:59:59 -> 01/01/2000 00:00:01",
                 2*1000, date2.getTimeBetween(date1));
    date2 = this.getGenerator().createDateTime("31/01/2000 23:59:59:000");
    assertEquals("01/01/2000 00:00:01 -> 31/01/2000 23:59:59",
                 31l*24l*3600l*1000l - 2l*1000l, date1.getTimeBetween(date2), 0l);
    assertEquals("31/01/2000 23:59:59 -> 01/01/2000 00:00:01",
                 -31l*24l*3600l*1000l + 2l*1000l, date2.getTimeBetween(date1), 0l);
    date2 = this.getGenerator().createDateTime("01/01/2000 23:59:59:000");
    assertEquals("01/01/2000 00:00:01 -> 01/01/2000 23:59:59",
                 (24*3600 - 2)*1000, date1.getTimeBetween(date2));
    assertEquals("01/01/2000 23:59:59 -> 01/01/2000 00:00:01",
                 -(24*3600 - 2)*1000, date2.getTimeBetween(date1));
    assertEquals("01/01/2000 00:00:01 -> 01/01/2000 00:00:01",
                 0, date1.getTimeBetween(date1));
  }
  /**
   * Test of addDays(int) method, of class Date.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testAddDays_int() throws Bid4WinException
  {
    Bid4WinDate date1 = this.getGenerator().createDateTime("01/01/2000 00:00:01:000");
    Bid4WinDate date2 = this.getGenerator().createDateTime("05/01/2000 00:00:01:000");
    assertEquals("0 day add did not work", date1, date1.addDays(0));
    assertEquals("+n day add did not work", date2, date1.addDays(date1.getDaysBetween(date2)));
    assertEquals("-n day add did not work", date1, date2.addDays(date2.getDaysBetween(date1)));
  }
  /**
   * Test of addTime(long) method, of class Date.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testAddTime_long() throws Bid4WinException
  {
    Bid4WinDate date1 = this.getGenerator().createDateTime("01/01/2000 12:34:56:789");
    Bid4WinDate date2 = this.getGenerator().createDateTime("01/01/2000 23:45:67:890");
    assertEquals("0 time add did not work", date1, date1.addTime(0));
    assertEquals("+n time add did not work", date2, date1.addTime(date1.getTimeBetween(date2)));
    assertEquals("-n time add did not work", date1, date2.addTime(date2.getTimeBetween(date1)));
  }
  /**
   * Test of removeSecondInfo() method, of class Date.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testRemoveSecondInfo_0args() throws Bid4WinException
  {
    Bid4WinDate date1 = this.getGenerator().createDateTime("31/12/1999 23:59:59:999");
    Bid4WinDate date2 = this.getGenerator().createDateTime("31/12/1999 23:59:00:000");
    assertTrue("Second information removal did not work",  date2.equals(date1.removeSecondInfo()));
  }
  /**
   * Test of removeTimeInfo() method, of class Date.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testRemoveTimeInfo_0args() throws Bid4WinException
  {
    Bid4WinDate date1 = this.getGenerator().createDateTime("31/12/1999 23:59:59:999");
    Bid4WinDate date2 = this.getGenerator().createDateTime("31/12/1999 00:00:00:000");
    assertTrue("Time information removal did not work",  date2.equals(date1.removeTimeInfo()));
  }
  /**
   * Test of isSameDay(Date) method, of class Date.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testIsSameDay_Date() throws Bid4WinException
  {
    Bid4WinDate date1 = this.getGenerator().createDateTime("31/12/1999 23:59:59:000");
    Bid4WinDate date2 = this.getGenerator().createDateTime("31/12/1999 00:00:00:000");
    assertTrue("Dates are about same day", date1.isSameDay(date2));
    assertTrue("Dates are about same day", date2.isSameDay(date1));
    date2 = this.getGenerator().createDateTime("01/01/2000 00:00:00:000");
    assertFalse("Dates are not about same day", date1.isSameDay(date2));
    assertFalse("Dates are not about same day", date2.isSameDay(date1));
  }
}
