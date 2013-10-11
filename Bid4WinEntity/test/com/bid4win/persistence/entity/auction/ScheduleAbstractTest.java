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
 * Classe de test éléments de planification de base d'une vente aux enchères<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
public class ScheduleAbstractTest
{
  /**
   * Test of ScheduleAbstract(Bid4WinDate) method, of class Bid.
   */
  @Test
  public void testScheduleAbstract_Bid4WinDate()
  {
    try
    {
      Bid4WinDate date = new Bid4WinDate();
      ScheduleAbstractStub schedule = new ScheduleAbstractStub(date);
      assertEquals("Bad start date", date.removeSecondInfo(), schedule.getStartDate());
      assertEquals("Bad end date", Bid4WinDate.FINAL_DATE.removeMilliSecondInfo(), schedule.getEndDate());
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail(ex.getMessage());
    }
    try
    {
      new ScheduleAbstractStub(null);
      fail("Should fail with null start date");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of ScheduleAbstract(Bid4WinDate, Bid4WinDate) method, of class Bid.
   */
  @Test
  public void testScheduleAbstract_Bid4WinDate_Bid4WinDate()
  {
    try
    {
      Bid4WinDate date = new Bid4WinDate();
      ScheduleAbstractStub schedule = new ScheduleAbstractStub(date, date);
      assertEquals("Bad start date", date.removeSecondInfo(), schedule.getStartDate());
      assertEquals("Bad end date", date.removeMilliSecondInfo(), schedule.getEndDate());
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail(ex.getMessage());
    }
    try
    {
      new ScheduleAbstractStub(null, new Bid4WinDate());
      fail("Should fail with null start date");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      new ScheduleAbstractStub(new Bid4WinDate(), null);
      fail("Should fail with null end date");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
}
