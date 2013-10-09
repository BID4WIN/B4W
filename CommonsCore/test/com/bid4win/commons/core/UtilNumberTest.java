package com.bid4win.commons.core;

import static org.junit.Assert.assertEquals;
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
public class UtilNumberTest extends Bid4WinCoreTester
{
  /**
   * Test of checkMin(String, int, int, boolean) method, of class UtilNumber.
   */
  @Test
  public void testCheckMin_String_int_int_boolean()
  {
    try
    {
      int min = 1234567890;
      int value = min;
      int result = UtilNumber.checkMinValue("int", value, min, true);
      assertTrue("Bad result", result == value);
      value++;
      result = UtilNumber.checkMinValue("int", value, min, false);
      assertTrue("Bad result", result == value);
    }
    catch(ModelArgumentException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      int min = 1234567890;
      UtilNumber.checkMinValue("int", min, min + 1, true);
      fail("Should fail");
    }
    catch(ModelArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      int min = 1234567890;
      UtilNumber.checkMinValue("int", min, min, false);
      fail("Should fail");
    }
    catch(ModelArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of checkMin(String, int, int, boolean, MessageRef) method, of class UtilNumber.
   */
  @Test
  public void testCheckMin_String_int_int_boolean_MessageRef()
  {
    try
    {
      int min = 1234567890;
      int value = min;
      int result = UtilNumber.checkMinValue("int", value, min, true, MessageRef.UNKNOWN_ERROR);
      assertTrue("Bad result", result == value);
      value++;
      result = UtilNumber.checkMinValue("int", value, min, false, MessageRef.UNKNOWN_ERROR);
      assertTrue("Bad result", result == value);
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      int min = 1234567890;
      UtilNumber.checkMinValue("int", min, min + 1, true, MessageRef.UNKNOWN_ERROR);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      int min = 1234567890;
      UtilNumber.checkMinValue("int", min, min, false, MessageRef.UNKNOWN_ERROR);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of checkMin(String, long, long, boolean) method, of class UtilNumber.
   */
  @Test
  public void testCheckMin_String_long_long_boolean()
  {
    try
    {
      long min = 12345678901l;
      long value = min;
      long result = UtilNumber.checkMinValue("long", value, min, true);
      assertTrue("Bad result", result == value);
      value++;
      result = UtilNumber.checkMinValue("long", value, min, false);
      assertTrue("Bad result", result == value);
    }
    catch(ModelArgumentException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      long min = 12345678901l;
      UtilNumber.checkMinValue("long", min, min + 1, true);
      fail("Should fail");
    }
    catch(ModelArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      long min = 12345678901l;
      UtilNumber.checkMinValue("long", min, min, false);
      fail("Should fail");
    }
    catch(ModelArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of checkMin(String, long, long, boolean, MessageRef) method, of class UtilNumber.
   */
  @Test
  public void testCheckMin_String_long_long_boolean_MessageRef()
  {
    try
    {
      long min = 12345678901l;
      long value = min;
      long result = UtilNumber.checkMinValue("long", value, min, true, MessageRef.UNKNOWN_ERROR);
      assertTrue("Bad result", result == value);
      value++;
      result = UtilNumber.checkMinValue("long", value, min, false, MessageRef.UNKNOWN_ERROR);
      assertTrue("Bad result", result == value);
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      long min = 12345678901l;
      UtilNumber.checkMinValue("long", min, min + 1, true, MessageRef.UNKNOWN_ERROR);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      long min = 12345678901l;
      UtilNumber.checkMinValue("long", min, min, false, MessageRef.UNKNOWN_ERROR);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of checkMin(String, float, float, boolean) method, of class UtilNumber.
   */
  @Test
  public void testCheckMin_String_float_float_boolean()
  {
    try
    {
      float min = 123456.78f;
      float value = min;
      float result = UtilNumber.checkMinValue("float", value, min, true);
      assertTrue("Bad result", result == value);
      value += 0.01f;
      result = UtilNumber.checkMinValue("float", value, min, false);
      assertTrue("Bad result", result == value);
    }
    catch(ModelArgumentException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      float min = 123456.78f;
      UtilNumber.checkMinValue("float", min, min + 0.01f, true);
      fail("Should fail");
    }
    catch(ModelArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      float min = 123456.78f;
      UtilNumber.checkMinValue("float", min, min, false);
      fail("Should fail");
    }
    catch(ModelArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of checkMin(String, float, float, boolean, MessageRef) method, of class UtilNumber.
   */
  @Test
  public void testCheckMin_String_float_float_boolean_MessageRef()
  {
    try
    {
      float min = 123456.78f;
      float value = min;
      float result = UtilNumber.checkMinValue("float", value, min, true, MessageRef.UNKNOWN_ERROR);
      assertTrue("Bad result", result == value);
      value += 0.01f;
      result = UtilNumber.checkMinValue("float", value, min, false, MessageRef.UNKNOWN_ERROR);
      assertTrue("Bad result", result == value);
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      float min = 123456.78f;
      UtilNumber.checkMinValue("float", min, min + 0.01f, true, MessageRef.UNKNOWN_ERROR);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      float min = 123456.78f;
      UtilNumber.checkMinValue("float", min, min, false, MessageRef.UNKNOWN_ERROR);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of checkMin(String, double, double, boolean) method, of class UtilNumber.
   */
  @Test
  public void testCheckMin_String_double_double_boolean()
  {
    try
    {
      double min = 123456.78912;
      double value = min;
      double result = UtilNumber.checkMinValue("double", value, min, true);
      assertTrue("Bad result", result == value);
      value += 0.00001;
      result = UtilNumber.checkMinValue("double", value, min, false);
      assertTrue("Bad result", result == value);
    }
    catch(ModelArgumentException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      double min = 123456.78912;
      UtilNumber.checkMinValue("double", min, min + 0.00001, true);
      fail("Should fail");
    }
    catch(ModelArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      double min = 123456.78f;
      UtilNumber.checkMinValue("double", min, min, false);
      fail("Should fail");
    }
    catch(ModelArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of checkMin(String, double, double, boolean, MessageRef) method, of class UtilNumber.
   */
  @Test
  public void testCheckMin_String_double_double_boolean_MessageRef()
  {
    try
    {
      double min = 123456.78912;
      double value = min;
      double result = UtilNumber.checkMinValue("double", value, min, true, MessageRef.UNKNOWN_ERROR);
      assertTrue("Bad result", result == value);
      value += 0.00001;
      result = UtilNumber.checkMinValue("double", value, min, false, MessageRef.UNKNOWN_ERROR);
      assertTrue("Bad result", result == value);
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      double min = 123456.78912;
      UtilNumber.checkMinValue("double", min, min + 0.00001, true, MessageRef.UNKNOWN_ERROR);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      double min = 123456.78f;
      UtilNumber.checkMinValue("double", min, min, false, MessageRef.UNKNOWN_ERROR);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of checkMax(String, int, int, boolean) method, of class UtilNumber.
   */
  @Test
  public void testCheckMax_String_int_int_boolean()
  {
    try
    {
      int Max = 1234567890;
      int value = Max;
      int result = UtilNumber.checkMaxValue("int", value, Max, true);
      assertTrue("Bad result", result == value);
      value--;
      result = UtilNumber.checkMaxValue("int", value, Max, false);
      assertTrue("Bad result", result == value);
    }
    catch(ModelArgumentException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      int Max = 1234567890;
      UtilNumber.checkMaxValue("int", Max, Max - 1, true);
      fail("Should fail");
    }
    catch(ModelArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      int Max = 1234567890;
      UtilNumber.checkMaxValue("int", Max, Max, false);
      fail("Should fail");
    }
    catch(ModelArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of checkMax(String, int, int, boolean, MessageRef) method, of class UtilNumber.
   */
  @Test
  public void testCheckMax_String_int_int_boolean_MessageRef()
  {
    try
    {
      int Max = 1234567890;
      int value = Max;
      int result = UtilNumber.checkMaxValue("int", value, Max, true, MessageRef.UNKNOWN_ERROR);
      assertTrue("Bad result", result == value);
      value--;
      result = UtilNumber.checkMaxValue("int", value, Max, false, MessageRef.UNKNOWN_ERROR);
      assertTrue("Bad result", result == value);
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      int Max = 1234567890;
      UtilNumber.checkMaxValue("int", Max, Max - 1, true, MessageRef.UNKNOWN_ERROR);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      int Max = 1234567890;
      UtilNumber.checkMaxValue("int", Max, Max, false, MessageRef.UNKNOWN_ERROR);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of checkMax(String, long, long, boolean) method, of class UtilNumber.
   */
  @Test
  public void testCheckMax_String_long_long_boolean()
  {
    try
    {
      long Max = 12345678901l;
      long value = Max;
      long result = UtilNumber.checkMaxValue("long", value, Max, true);
      assertTrue("Bad result", result == value);
      value--;
      result = UtilNumber.checkMaxValue("long", value, Max, false);
      assertTrue("Bad result", result == value);
    }
    catch(ModelArgumentException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      long Max = 12345678901l;
      UtilNumber.checkMaxValue("long", Max, Max - 1, true);
      fail("Should fail");
    }
    catch(ModelArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      long Max = 12345678901l;
      UtilNumber.checkMaxValue("long", Max, Max, false);
      fail("Should fail");
    }
    catch(ModelArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of checkMax(String, long, long, boolean, MessageRef) method, of class UtilNumber.
   */
  @Test
  public void testCheckMax_String_long_long_boolean_MessageRef()
  {
    try
    {
      long Max = 12345678901l;
      long value = Max;
      long result = UtilNumber.checkMaxValue("long", value, Max, true, MessageRef.UNKNOWN_ERROR);
      assertTrue("Bad result", result == value);
      value--;
      result = UtilNumber.checkMaxValue("long", value, Max, false, MessageRef.UNKNOWN_ERROR);
      assertTrue("Bad result", result == value);
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      long Max = 12345678901l;
      UtilNumber.checkMaxValue("long", Max, Max - 1, true, MessageRef.UNKNOWN_ERROR);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      long Max = 12345678901l;
      UtilNumber.checkMaxValue("long", Max, Max, false, MessageRef.UNKNOWN_ERROR);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of checkMax(String, float, float, boolean) method, of class UtilNumber.
   */
  @Test
  public void testCheckMax_String_float_float_boolean()
  {
    try
    {
      float Max = 123456.78f;
      float value = Max;
      float result = UtilNumber.checkMaxValue("float", value, Max, true);
      assertTrue("Bad result", result == value);
      value -= 0.01f;
      result = UtilNumber.checkMaxValue("float", value, Max, false);
      assertTrue("Bad result", result == value);
    }
    catch(ModelArgumentException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      float Max = 123456.78f;
      UtilNumber.checkMaxValue("float", Max, Max - 0.01f, true);
      fail("Should fail");
    }
    catch(ModelArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      float Max = 123456.78f;
      UtilNumber.checkMaxValue("float", Max, Max, false);
      fail("Should fail");
    }
    catch(ModelArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of checkMax(String, float, float, boolean, MessageRef) method, of class UtilNumber.
   */
  @Test
  public void testCheckMax_String_float_float_boolean_MessageRef()
  {
    try
    {
      float Max = 123456.78f;
      float value = Max;
      float result = UtilNumber.checkMaxValue("float", value, Max, true, MessageRef.UNKNOWN_ERROR);
      assertTrue("Bad result", result == value);
      value -= 0.01f;
      result = UtilNumber.checkMaxValue("float", value, Max, false, MessageRef.UNKNOWN_ERROR);
      assertTrue("Bad result", result == value);
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      float Max = 123456.78f;
      UtilNumber.checkMaxValue("float", Max, Max - 0.01f, true, MessageRef.UNKNOWN_ERROR);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      float Max = 123456.78f;
      UtilNumber.checkMaxValue("float", Max, Max, false, MessageRef.UNKNOWN_ERROR);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of checkMax(String, double, double, boolean) method, of class UtilNumber.
   */
  @Test
  public void testCheckMax_String_double_double_boolean()
  {
    try
    {
      double Max = 123456.78912;
      double value = Max;
      double result = UtilNumber.checkMaxValue("double", value, Max, true);
      assertTrue("Bad result", result == value);
      value -= 0.00001;
      result = UtilNumber.checkMaxValue("double", value, Max, false);
      assertTrue("Bad result", result == value);
    }
    catch(ModelArgumentException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      double Max = 123456.78912;
      UtilNumber.checkMaxValue("double", Max, Max - 0.00001, true);
      fail("Should fail");
    }
    catch(ModelArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      double Max = 123456.78f;
      UtilNumber.checkMaxValue("double", Max, Max, false);
      fail("Should fail");
    }
    catch(ModelArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of checkMax(String, double, double, boolean, MessageRef) method, of class UtilNumber.
   */
  @Test
  public void testCheckMax_String_double_double_boolean_MessageRef()
  {
    try
    {
      double Max = 123456.78912;
      double value = Max;
      double result = UtilNumber.checkMaxValue("double", value, Max, true, MessageRef.UNKNOWN_ERROR);
      assertTrue("Bad result", result == value);
      value -= 0.00001;
      result = UtilNumber.checkMaxValue("double", value, Max, false, MessageRef.UNKNOWN_ERROR);
      assertTrue("Bad result", result == value);
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      double Max = 123456.78912;
      UtilNumber.checkMaxValue("double", Max, Max - 0.00001, true, MessageRef.UNKNOWN_ERROR);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      double Max = 123456.78;
      UtilNumber.checkMaxValue("double", Max, Max, false, MessageRef.UNKNOWN_ERROR);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of getDecimalNb(double) method, of class UtilNumber.
   */
  @Test
  public void testGetDecimalNb()
  {
    assertEquals("Wrong result", 0, UtilNumber.getDecimalNb(0));
    assertEquals("Wrong result", 0, UtilNumber.getDecimalNb(123));
    assertEquals("Wrong result", 0, UtilNumber.getDecimalNb(123.0));
    assertEquals("Wrong result", 1, UtilNumber.getDecimalNb(0.1));
    assertEquals("Wrong result", 1, UtilNumber.getDecimalNb(.1));
    assertEquals("Wrong result", 6, UtilNumber.getDecimalNb(.123456));
  }
}