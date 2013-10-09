package com.bid4win.commons.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.collection.Bid4WinList;
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
public class UtilObjectTest extends Bid4WinCoreTester
{
  /**
   * Test of checkNotNull(String, TYPE) method, of class UtilObject.
   */
  @Test
  public void testCheckNotNull_String_TYPE()
  {
    try
    {
      String string = "123456";
      String result = UtilObject.checkNotNull("string", string);
      assertTrue("Bad result", result == string);
    }
    catch(ModelArgumentException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      UtilObject.checkNotNull("string", null);
      fail("Should fail");
    }
    catch(ModelArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of checkNotNull(String, TYPE, MessageRef) method, of class UtilObject.
   */
  @Test
  public void testCheckNotNull_String_TYPE_MessageRef()
  {
    try
    {
      String string = "123456";
      String result = UtilObject.checkNotNull("string", string, MessageRef.UNKNOWN_ERROR);
      assertTrue("Bad result", result == string);
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      UtilObject.checkNotNull("string", null, MessageRef.UNKNOWN_ERROR);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of checkNull(String, TYPE) method, of class UtilObject.
   */
  @Test
  public void testCheckNull_String_TYPE()
  {
    try
    {
      String result = UtilObject.checkNull("string", null);
      assertNull("Bad result", result);
    }
    catch(ModelArgumentException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      UtilObject.checkNull("string", "123456");
      fail("Should fail");
    }
    catch(ModelArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of checkNull(String, TYPE, MessageRef) method, of class UtilObject.
   */
  @Test
  public void testCheckNull_String_TYPE_MessageRef()
  {
    try
    {
      String result = UtilObject.checkNull("string", null, MessageRef.UNKNOWN_ERROR);
      assertNull("Bad result", result);
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      UtilObject.checkNull("string", "123456", MessageRef.UNKNOWN_ERROR);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of checkEquals(String, TYPE, TYPE) method, of class UtilObject.
   */
  @Test
  public void testCheckEquals_String_TYPE_TYPE()
  {
    try
    {
      String string1 = null;
      String string2 = null;
      String result = UtilObject.checkEquals("string", string1, string2);
      assertNull("Bad result", result);
      string1 = "123456";
      string2 = "123456";
      result = UtilObject.checkEquals("string", string1, string2);
      assertTrue("Bad result", string1 == result);
      assertTrue("Bad result", string2 == result);
    }
    catch(ModelArgumentException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      UtilObject.checkEquals("string", "123456", null);
      fail("Should fail");
    }
    catch(ModelArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilObject.checkEquals("string", null, "123456");
      fail("Should fail");
    }
    catch(ModelArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilObject.checkEquals("string", "123456", "12345");
      fail("Should fail");
    }
    catch(ModelArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of checkEquals(String, TYPE, TYPE, MessageRef) method, of class UtilObject.
   */
  @Test
  public void testCheckEquals_String_TYPE_TYPE_MessageRef()
  {
    try
    {
      String string1 = null;
      String string2 = null;
      String result = UtilObject.checkEquals("string", string1, string2, MessageRef.UNKNOWN_ERROR);
      assertNull("Bad result", result);
      string1 = "123456";
      string2 = "123456";
      result = UtilObject.checkEquals("string", string1, string2, MessageRef.UNKNOWN_ERROR);
      assertTrue("Bad result", string1 == result);
      assertTrue("Bad result", string2 == result);
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      UtilObject.checkEquals("string", "123456", null, MessageRef.UNKNOWN_ERROR);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilObject.checkEquals("string", null, "123456", MessageRef.UNKNOWN_ERROR);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilObject.checkEquals("string", "123456", "12345", MessageRef.UNKNOWN_ERROR);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of checkDiffers(String, TYPE, TYPE) method, of class UtilObject.
   */
  @Test
  public void testCheckDiffers_String_TYPE_TYPE()
  {
    try
    {
      String string1 = "123456";
      String string2 = "12345";
      String result = UtilObject.checkDiffers("string", string1, string2);
      assertTrue("Bad result", string1 == result);
      assertFalse("Bad result", string2.equals(result));
      string1 = "123456";
      string2 = null;
      result = UtilObject.checkDiffers("string", string1, string2);
      assertTrue("Bad result", string1 == result);
      assertFalse("Bad result", result.equals(string2));
      string1 = null;
      string2 = "12345";
      result = UtilObject.checkDiffers("string", string1, string2);
      assertTrue("Bad result", string1 == result);
      assertFalse("Bad result", string2.equals(result));
    }
    catch(ModelArgumentException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      UtilObject.checkDiffers("string", "123456", "123456");
      fail("Should fail");
    }
    catch(ModelArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilObject.checkDiffers("string", null, null);
      fail("Should fail");
    }
    catch(ModelArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of checkDiffers(String, TYPE, TYPE, MessageRef) method, of class UtilObject.
   */
  @Test
  public void testCheckDiffers_String_TYPE_TYPE_MessageRef()
  {
    try
    {
      String string1 = "123456";
      String string2 = "12345";
      String result = UtilObject.checkDiffers("string", string1, string2, MessageRef.UNKNOWN_ERROR);
      assertTrue("Bad result", string1 == result);
      assertFalse("Bad result", string2.equals(result));
      string1 = "123456";
      string2 = null;
      result = UtilObject.checkDiffers("string", string1, string2, MessageRef.UNKNOWN_ERROR);
      assertTrue("Bad result", string1 == result);
      assertFalse("Bad result", result.equals(string2));
      string1 = null;
      string2 = "12345";
      result = UtilObject.checkDiffers("string", string1, string2, MessageRef.UNKNOWN_ERROR);
      assertTrue("Bad result", string1 == result);
      assertFalse("Bad result", string2.equals(result));
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      UtilObject.checkDiffers("string", "123456", "123456", MessageRef.UNKNOWN_ERROR);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilObject.checkDiffers("string", null, null, MessageRef.UNKNOWN_ERROR);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of checkAmong(String, TYPE, Collection<TYPE>) method, of class UtilObject.
   */
  @Test
  public void testCheckAmong_String_TYPE_CollectionOfTYPE()
  {
    try
    {
      String string = "123456";
      Bid4WinList<String> list = new Bid4WinList<String>(string);
      String result = UtilObject.checkAmong("string", string, list);
      assertTrue("Bad result", string == result);
      list.add(0, string + "7");
      list.add(string + "78");
      result = UtilObject.checkAmong("string", string, list);
      assertTrue("Bad result", string == result);
    }
    catch(ModelArgumentException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      String string = "123456";
      Bid4WinList<String> list = new Bid4WinList<String>();
      UtilObject.checkAmong("string", string, list);
      fail("Should fail");
    }
    catch(ModelArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      String string = "123456";
      Bid4WinList<String> list = new Bid4WinList<String>();
      list.add(string + "7");
      list.add(string + "78");
      UtilObject.checkAmong("string", string, list);
      fail("Should fail");
    }
    catch(ModelArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of checkAmong(String, TYPE, Collection<TYPE>, MessageRef) method, of class UtilObject.
   */
  @Test
  public void testCheckAmong_String_TYPE_CollectionOfTYPE_MessageRef()
  {
    try
    {
      String string = "123456";
      Bid4WinList<String> list = new Bid4WinList<String>(string);
      String result = UtilObject.checkAmong("string", string, list, MessageRef.UNKNOWN_ERROR);
      assertTrue("Bad result", string == result);
      list.add(0, string + "7");
      list.add(string + "78");
      result = UtilObject.checkAmong("string", string, list, MessageRef.UNKNOWN_ERROR);
      assertTrue("Bad result", string == result);
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      String string = "123456";
      Bid4WinList<String> list = new Bid4WinList<String>();
      UtilObject.checkAmong("string", string, list, MessageRef.UNKNOWN_ERROR);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      String string = "123456";
      Bid4WinList<String> list = new Bid4WinList<String>();
      list.add(string + "7");
      list.add(string + "78");
      UtilObject.checkAmong("string", string, list, MessageRef.UNKNOWN_ERROR);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of checkExcept(String, TYPE, Collection<TYPE>) method, of class UtilObject.
   */
  @Test
  public void testCheckExcept_String_TYPE_CollectionOfTYPE()
  {
    try
    {
      String string = "123456";
      Bid4WinList<String> list = new Bid4WinList<String>();
      String result = UtilObject.checkExcept("string", string, list);
      assertTrue("Bad result", string == result);
      list.add(string + "7");
      list.add(string + "78");
      result = UtilObject.checkExcept("string", string, list);
      assertTrue("Bad result", string == result);
    }
    catch(ModelArgumentException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      String string = "123456";
      Bid4WinList<String> list = new Bid4WinList<String>(string);
      UtilObject.checkExcept("string", string, list);
      fail("Should fail");
    }
    catch(ModelArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      String string = "123456";
      Bid4WinList<String> list = new Bid4WinList<String>(string);
      list.add(0, string + "7");
      list.add(string + "78");
      UtilObject.checkExcept("string", string, list);
      fail("Should fail");
    }
    catch(ModelArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of checkExcept(String, TYPE, Collection<TYPE>, MessageRef) method, of class UtilObject.
   */
  @Test
  public void testCheckExcept_String_TYPE_CollectionOfTYPE_MessageRef()
  {
    try
    {
      String string = "123456";
      Bid4WinList<String> list = new Bid4WinList<String>();
      String result = UtilObject.checkExcept("string", string, list, MessageRef.UNKNOWN_ERROR);
      assertTrue("Bad result", string == result);
      list.add(string + "7");
      list.add(string + "78");
      result = UtilObject.checkExcept("string", string, list, MessageRef.UNKNOWN_ERROR);
      assertTrue("Bad result", string == result);
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      String string = "123456";
      Bid4WinList<String> list = new Bid4WinList<String>(string);
      UtilObject.checkExcept("string", string, list, MessageRef.UNKNOWN_ERROR);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      String string = "123456";
      Bid4WinList<String> list = new Bid4WinList<String>(string);
      list.add(0, string + "7");
      list.add(string + "78");
      UtilObject.checkExcept("string", string, list, MessageRef.UNKNOWN_ERROR);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
}