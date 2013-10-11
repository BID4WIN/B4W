package com.bid4win.commons.persistence.entity.connection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.Bid4WinEntityTester;
import com.bid4win.commons.persistence.entity.EntityGeneratorStub;
import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 * Classe de test d'un identifiant de connexion<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class IpAddressTest extends Bid4WinEntityTester<AccountAbstractStub, EntityGeneratorStub>
{
  /**
   * Test of IpAddress(String), of class IpAddress.
   */
  @Test
  public void testIpAddress_String()
  {
    String valueV4 = "1.2.3.4";
    String valueV6 = "1:2:3:4:5:6:7:8";
    try
    {
      IpAddress instance = new IpAddress(valueV4);
      assertEquals("Bad IP address", UtilIpAddress.checkAddressV4(valueV4), instance.getValue());
      instance = new IpAddress(valueV6);
      assertEquals("Bad IP address", UtilIpAddress.checkAddressV6(valueV6), instance.getValue());
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Instanciation should not fail: " + ex.getMessage());
    }
    try
    {
      new IpAddress(valueV4 + "|");
      fail("Instanciation with wrong address pattern should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      new IpAddress(null);
      fail("Instanciation with null value should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of equalsInternal(IpAddress), of class IpAddress.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testEqualsInternal_IpAddress() throws Bid4WinException
  {
    IpAddress ipAddress1 = new IpAddress("1.2.3.4");
    IpAddress ipAddress2 = new IpAddress("1:2:3:4:5:6:7:8");

    assertFalse(ipAddress1.equalsInternal(ipAddress2));
    assertFalse(ipAddress2.equalsInternal(ipAddress1));
    assertTrue(ipAddress1.equalsInternal(ipAddress1));
  }
}
