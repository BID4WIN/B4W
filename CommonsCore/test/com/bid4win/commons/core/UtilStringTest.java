package com.bid4win.commons.core;

import static org.junit.Assert.assertEquals;
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
public class UtilStringTest extends Bid4WinCoreTester
{
  /**
   * Test of trimNotNull(String) method, of class UtilString.
   */
  @Test
  public void testTrimNotNull_String()
  {
    assertEquals("Bad result", "", UtilString.trimNotNull(null));
    assertEquals("Bad result", "", UtilString.trimNotNull("    "));
    assertEquals("Bad result", "a b c", UtilString.trimNotNull("  a b c  "));
  }
  /**
   * Test of checkPattern(String, String, String) method, of class UtilString.
   */
  @Test
  public void testCheckPattern_String_String_String()
  {
    String test = "value";
    try
    {
      String result = UtilString.checkPattern("test", test, "[a-z]{5}");
      assertEquals("Bad result", test, result);
    }
    catch(ModelArgumentException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      UtilString.checkPattern("test", test, "[a-z]{4}");
      fail("Should fail");
    }
    catch(ModelArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of checkPattern(String, String, String, MessageRef) method, of class UtilString.
   */
  @Test
  public void testCheckPattern_String_String_String_MessageRef()
  {
    String test = "value";
    try
    {
      String result = UtilString.checkPattern("test", test, "[a-z]{5}", MessageRef.UNKNOWN_ERROR);
      assertEquals("Bad result", test, result);
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      UtilString.checkPattern("test", test, "[a-z]{4}", MessageRef.UNKNOWN_ERROR);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of checkEmpty(String, String) method, of class UtilString.
   */
  @Test
  public void testCheckEmpty_String_String()
  {
    try
    {
      String string = "";
      String result = UtilString.checkEmpty("string", string);
      assertTrue("Bad result", string == result);
    }
    catch(ModelArgumentException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      UtilString.checkEmpty("string", null);
      fail("Should fail");
    }
    catch(ModelArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilString.checkEmpty("string", "  ");
      fail("Should fail");
    }
    catch(ModelArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of checkEmpty(String, String, MessageRef) method, of class UtilString.
   */
  @Test
  public void testCheckEmpty_String_String_MessageRef()
  {
    try
    {
      String string = "";
      String result = UtilString.checkEmpty("string", string, MessageRef.UNKNOWN_ERROR);
      assertTrue("Bad result", string == result);
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      UtilString.checkEmpty("string", null, MessageRef.UNKNOWN_ERROR);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilString.checkEmpty("string", "  ", MessageRef.UNKNOWN_ERROR);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of checkNotEmpty(String, String) method, of class UtilString.
   */
  @Test
  public void testCheckNotEmpty_String_String()
  {
    try
    {
      String string = "  ";
      String result = UtilString.checkNotEmpty("string", string);
      assertTrue("Bad result", string == result);
      string = null;
      result = UtilString.checkNotEmpty("string", string);
      assertTrue("Bad result", string == result);
    }
    catch(ModelArgumentException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      UtilString.checkNotEmpty("string", "");
      fail("Should fail");
    }
    catch(ModelArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of checkNotEmpty(String, String, MessageRef) method, of class UtilString.
   */
  @Test
  public void testCheckNotEmpty_String_String_MessageRef()
  {
    try
    {
      String string = "  ";
      String result = UtilString.checkNotEmpty("string", string, MessageRef.UNKNOWN_ERROR);
      assertTrue("Bad result", string == result);
      string = null;
      result = UtilString.checkNotEmpty("string", string, MessageRef.UNKNOWN_ERROR);
      assertTrue("Bad result", string == result);
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      UtilString.checkNotEmpty("string", "", MessageRef.UNKNOWN_ERROR);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of startsWith(StringBuffer, String) method, of class UtilString.
   */
  @Test
  public void testStartsWith_StringBuffer_String()
  {
    assertTrue("Bad result", UtilString.startsWith(new StringBuffer("abcdef"), "abc"));
    assertFalse("Bad result", UtilString.startsWith(new StringBuffer(" abcdef"), "abc"));
  }
  /**
   * Test of endsWith(StringBuffer, String) method, of class UtilString.
   */
  @Test
  public void testEndsWith_StringBuffer_String()
  {
    assertTrue("Bad result", UtilString.endsWith(new StringBuffer("abcdef"), "def"));
    assertFalse("Bad result", UtilString.endsWith(new StringBuffer("abcdef "), "def"));
  }
  /**
   * Test of fillBefore(StringBuffer, char, int) method, of class UtilString.
   */
  @Test
  public void testFillBefore_StringBuffer_char_int()
  {
    assertEquals("Bad result", new StringBuffer("   12345").toString(),
                 UtilString.fillBefore(new StringBuffer("12345"), ' ', 8).toString());
    assertEquals("Bad result", new StringBuffer("12345").toString(),
                 UtilString.fillBefore(new StringBuffer("12345"), ' ', 5).toString());
  }
  /**
   * Test of fillAfter(StringBuffer, char, int) method, of class UtilString.
   */
  @Test
  public void testFillAfter_StringBuffer_char_int()
  {
    assertEquals("Bad result", new StringBuffer("12345   ").toString(),
                 UtilString.fillAfter(new StringBuffer("12345"), ' ', 8).toString());
    assertEquals("Bad result", new StringBuffer("12345").toString(),
                 UtilString.fillAfter(new StringBuffer("12345"), ' ', 5).toString());
  }
  /**
   * Test of trimEmptyLine(StringBuffer) method, of class UtilString.
   */
  @Test
  public void testTrimEmptyLine_StringBuffer()
  {
    assertEquals("Bad result", "", UtilString.trimEmptyLine(null).toString());
    assertEquals("Bad result", "", UtilString.trimEmptyLine(
        new StringBuffer(UtilString.SEPARATOR_NEW_LINE +
                         UtilString.SEPARATOR_NEW_LINE +
                         UtilString.SEPARATOR_NEW_LINE)).toString());
    assertEquals("Bad result", "a" + UtilString.SEPARATOR_NEW_LINE + "b" + UtilString.SEPARATOR_NEW_LINE + "c",
                 UtilString.trimEmptyLine(new StringBuffer(UtilString.SEPARATOR_NEW_LINE +
                                                           UtilString.SEPARATOR_NEW_LINE + "a" +
                                                           UtilString.SEPARATOR_NEW_LINE + "b" +
                                                           UtilString.SEPARATOR_NEW_LINE + "c" +
                                                           UtilString.SEPARATOR_NEW_LINE +
                                                           UtilString.SEPARATOR_NEW_LINE)).toString());
  }
  /**
   * Test of indent(StringBuffer, String, int, String) method, of class UtilString.
   */
  @Test
  public void testIndent_StringBuffer_String_int_String()
  {
    String sep = UtilString.SEPARATOR_NEW_LINE;

    StringBuffer text = new StringBuffer("");
    StringBuffer expected = new StringBuffer("------");
    StringBuffer result = UtilString.indent(text, sep, 3, "--");
    assertEquals("Wrong indentation", expected.toString(), result.toString());

    text = new StringBuffer("toto");
    expected = new StringBuffer("------toto");
    result = UtilString.indent(text, sep, 3, "--");
    assertEquals("Wrong indentation", expected.toString(), result.toString());
    expected = new StringBuffer("--------toto");
    result = UtilString.indent(result, sep, 1, "--");
    assertEquals("Wrong indentation", expected.toString(), result.toString());

    text = new StringBuffer(sep);
    expected = new StringBuffer("----"+sep+"----");
    result = UtilString.indent(text, sep, 2, "--");
    assertEquals("Wrong indentation", expected.toString(), result.toString());

    text = new StringBuffer(sep+sep+"--toto"+sep+"tata"+sep);
    expected = new StringBuffer("----"+sep+"----"+sep+"------toto"+sep+"----tata"+sep+"----");
    result = UtilString.indent(text, sep, 2, "--");
    assertEquals("Wrong indentation", expected.toString(), result.toString());

    text = new StringBuffer("titi"+sep+sep+"--toto"+sep+"tata"+sep+"tutu");
    expected = new StringBuffer("----titi"+sep+"----"+sep+"------toto"+sep+"----tata"+sep+"----tutu");
    result = UtilString.indent(text, sep, 2, "--");
    assertEquals("Wrong indentation", expected.toString(), result.toString());
  }
}