package com.bid4win.commons.core.security;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.Bid4WinCoreTester;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class ObjectProtectionTest extends Bid4WinCoreTester
{
  /**
   * Test of ObjectProtection(OBJECT), of class ObjectProtection.
   */
  @Test
  public void testObjectProtection_OBJECT()
  {

    ObjectProtection<String> protection1 = new ObjectProtection<String>("");
    assertFalse("Protection should not be enabled", protection1.isEnabled());

    ObjectProtector.startProtection();
    ObjectProtection<String> protection2 = new ObjectProtection<String>("");
    assertFalse("Protection should not be enabled", protection1.isEnabled());
    assertTrue("Protection be enabled", protection2.isEnabled());

    ObjectProtector.startProtection();
    ObjectProtection<String> protection3 = new ObjectProtection<String>("");
    assertFalse("Protection should not be enabled", protection1.isEnabled());
    assertTrue("Protection be enabled", protection2.isEnabled());
    assertTrue("Protection be enabled", protection3.isEnabled());

    ObjectProtector.stopProtection(ObjectProtector.getProtectionId());
    ObjectProtection<String> protection4 = new ObjectProtection<String>("");
    assertFalse("Protection should not be enabled", protection1.isEnabled());
    assertTrue("Protection be enabled", protection2.isEnabled());
    assertTrue("Protection be enabled", protection3.isEnabled());
    assertTrue("Protection be enabled", protection4.isEnabled());

    ObjectProtector.stopProtection(ObjectProtector.getProtectionId());
    ObjectProtection<String> protection5 = new ObjectProtection<String>("");
    assertFalse("Protection should not be enabled", protection1.isEnabled());
    assertTrue("Protection be enabled", protection2.isEnabled());
    assertTrue("Protection be enabled", protection3.isEnabled());
    assertTrue("Protection be enabled", protection4.isEnabled());
    assertFalse("Protection should not be enabled", protection5.isEnabled());
  }

  /**
   * Test of check(), of class ObjectProtection.
   */
  @Test
  public void testCheck_0args()
  {
    ObjectProtection<String> protection1 = new ObjectProtection<String>("");
    try
    {
      protection1.check();
    }
    catch(ProtectionException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    ObjectProtector.startProtection();
    ObjectProtection<String> protection2 = new ObjectProtection<String>("");
    try
    {
      protection1.check();
      protection2.check();
    }
    catch(ProtectionException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }

    ObjectProtector.startProtection();
    ObjectProtection<String> protection3 = new ObjectProtection<String>("");
    try
    {
      protection1.check();
      protection2.check();
      protection3.check();
    }
    catch(ProtectionException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }

    ObjectProtector.stopProtection(ObjectProtector.getProtectionId());
    ObjectProtection<String> protection4 = new ObjectProtection<String>("");
    try
    {
      protection1.check();
      protection2.check();
      protection4.check();
    }
    catch(ProtectionException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      protection3.check();
      fail("Should fail");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }

    ObjectProtector.stopProtection(ObjectProtector.getProtectionId());
    ObjectProtection<String> protection5 = new ObjectProtection<String>("");
    try
    {
      protection1.check();
      protection5.check();
    }
    catch(ProtectionException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      protection2.check();
      fail("Should fail");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      protection3.check();
      fail("Should fail");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      protection4.check();
      fail("Should fail");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
  }

  /**
   * Test setup method
   * @throws Exception {@inheritDoc}
   * @see com.bid4win.commons.testing.Bid4WinTester#setUp()
   */
  @Override
  @Before
  public void setUp() throws Exception
  {
    while(ObjectProtector.isProtectionStarted())
    {
      ObjectProtector.stopProtection(ObjectProtector.getProtectionId());
    }
  }
  /**
   * Test teardown method
   * @throws Exception {@inheritDoc}
   * @see com.bid4win.commons.testing.Bid4WinTester#tearDown()
   */
  @Override
  @After
  public void tearDown() throws Exception
  {
    while(ObjectProtector.isProtectionStarted())
    {
      ObjectProtector.stopProtection(ObjectProtector.getProtectionId());
    }
  }
}
