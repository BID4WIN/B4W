package com.bid4win.persistence.entity.auction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 * Classe de test éléments de planification d'une vente aux enchères<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
public class ScheduleTest
{
  /**
   * Test of ScheduleAbstract(Bid4WinDate, int, int) method, of class Bid.
   */
  @Test
  public void testSchedule_Bid4WinDate_int_int()
  {
    try
    {
      Bid4WinDate date = new Bid4WinDate();
      ScheduleStub schedule = new ScheduleStub(date, 1, 2);
      assertEquals("Bad start date", date.removeSecondInfo(), schedule.getStartDate());
      assertEquals("Bad end date", date.removeSecondInfo().addTime(1000), schedule.getEndDate());
      assertEquals("Bad initial countdown", 1, schedule.getInitialCountdown());
      assertEquals("Bad additional countdown", 2, schedule.getAdditionalCountdown());
      schedule = new ScheduleStub(date, 2, 1);
      assertEquals("Bad start date", date.removeSecondInfo(), schedule.getStartDate());
      assertEquals("Bad end date", date.removeSecondInfo().addTime(2000), schedule.getEndDate());
      assertEquals("Bad initial countdown", 2, schedule.getInitialCountdown());
      assertEquals("Bad additional countdown", 1, schedule.getAdditionalCountdown());
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail(ex.getMessage());
    }
    try
    {
      new ScheduleStub(null, 1, 1);
      fail("Should fail with null start date");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      new ScheduleStub(new Bid4WinDate(), 0, 1);
      fail("Should fail with zero initial countdown");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      new ScheduleStub(new Bid4WinDate(), 1, 0);
      fail("Should fail with zero additional countdown");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
}
