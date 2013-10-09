package com.bid4win.commons.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 * TODO COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class UtilBooleanTest extends Bid4WinCoreTester
{
  /**
   * Test of checkTrue(String, boolean) method, of class UtilBoolean.
   */
  @Test
  public void testCheckTrue_String_boolean()
  {
    try
    {
      boolean result = UtilBoolean.checkTrue("boolean", true);
      assertTrue("Bad result", result);
    }
    catch(ModelArgumentException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      UtilBoolean.checkTrue("boolean", false);
      fail("Should fail");
    }
    catch(ModelArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of checkTrue(String, boolean, MessageRef) method, of class UtilBoolean.
   */
  @Test
  public void testCheckTrue_String_boolean_MessageRef()
  {
    try
    {
      boolean result = UtilBoolean.checkTrue("boolean", true, MessageRef.UNKNOWN_ERROR);
      assertTrue("Bad result", result);
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      UtilBoolean.checkTrue("boolean", false, MessageRef.UNKNOWN_ERROR);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of checkFalse(String, boolean) method, of class UtilBoolean.
   */
  @Test
  public void testCheckFalse_String_boolean()
  {
    try
    {
      boolean result = UtilBoolean.checkFalse("boolean", false);
      assertFalse("Bad result", result);
    }
    catch(ModelArgumentException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      UtilBoolean.checkFalse("boolean", true);
      fail("Should fail");
    }
    catch(ModelArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of checkFalse(String, boolean, MessageRef) method, of class UtilBoolean.
   */
  @Test
  public void testCheckFalse_String_boolean_MessageRef()
  {
    try
    {
      boolean result = UtilBoolean.checkFalse("boolean", false, MessageRef.UNKNOWN_ERROR);
      assertFalse("Bad result", result);
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      UtilBoolean.checkFalse("boolean", true, MessageRef.UNKNOWN_ERROR);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
}