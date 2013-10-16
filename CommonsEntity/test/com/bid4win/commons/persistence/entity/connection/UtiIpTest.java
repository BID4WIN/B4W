package com.bid4win.commons.persistence.entity.connection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.Bid4WinCoreTester;
import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 * Classe de test de l'utilitaire d'emails<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class UtiIpTest extends Bid4WinCoreTester
{
  /**
   * Test of checkAddressV4(String), of class UtilIp.
   */
  @Test
  public void testCheckAddressV4_String()
  {
    try
    {
      assertEquals("000.000.000.000", UtilIpAddress.checkAddressV4("0.0.0.0"));
      assertEquals("000.001.002.003", UtilIpAddress.checkAddressV4("0.1.2.3"));
      assertEquals("255.255.255.255", UtilIpAddress.checkAddressV4("255.255.255.255"));
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Check should not have failed with valid address: " + ex.getMessage());
    }
    try
    {
      UtilIpAddress.checkAddressV4("0..2.3");
      fail("Check should have failed with invalid address");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilIpAddress.checkAddressV4(UtilString.EMPTY);
      fail("Check should have failed with invalid address");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilIpAddress.checkAddressV4("0");
      fail("Check should have failed with invalid address");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilIpAddress.checkAddressV4("0.");
      fail("Check should have failed with invalid address");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilIpAddress.checkAddressV4("0.1");
      fail("Check should have failed with invalid address");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilIpAddress.checkAddressV4("0.1.");
      fail("Check should have failed with invalid address");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilIpAddress.checkAddressV4("0.1.2");
      fail("Check should have failed with invalid address");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilIpAddress.checkAddressV4("0.1.2.");
      fail("Check should have failed with invalid address");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilIpAddress.checkAddressV4(".1.2.3");
      fail("Check should have failed with invalid address");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilIpAddress.checkAddressV4(".0.1.2.3");
      fail("Check should have failed with invalid address");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilIpAddress.checkAddressV4("0.1.2.3.");
      fail("Check should have failed with invalid address");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilIpAddress.checkAddressV4("256.1.2.3");
      fail("Check should have failed with invalid address");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilIpAddress.checkAddressV4("1.256.2.3");
      fail("Check should have failed with invalid address");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilIpAddress.checkAddressV4("1.2.256.3");
      fail("Check should have failed with invalid address");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilIpAddress.checkAddressV4("1.2.3.256");
      fail("Check should have failed with invalid address");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }

  /**
   * Test of checkAddressV6(String), of class UtilIp.
   */
  @Test
  public void testCheckAddressV6_String()
  {
    try
    {
      assertEquals("0000:0000:0000:0000:0000:0000:0000:0000", UtilIpAddress.checkAddressV6("0:0:0:0:0:0:0:0"));
      assertEquals("0012:0345:0678:0009:2468:0ABC:0DEF:0000", UtilIpAddress.checkAddressV6("012:345:678:9:2468:ABC:DEF:0"));
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Check should not have failed with valid address: " + ex.getMessage());
    }
    try
    {
      UtilIpAddress.checkAddressV6(UtilString.EMPTY);
      fail("Check should have failed with invalid address");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
}
