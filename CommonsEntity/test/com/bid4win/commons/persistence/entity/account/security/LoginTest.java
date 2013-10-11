package com.bid4win.commons.persistence.entity.account.security;

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
public class LoginTest extends Bid4WinEntityTester<AccountAbstractStub, EntityGeneratorStub>
{
  /**
   * Test of Login(String), of class Login.
   */
  @Test
  public void testLogin_String()
  {
    try
    {
      String value = UtilLoginTest.LOGIN;
      Login instance = new Login(value);
      assertEquals("Bad login value", value, instance.getValue());

      value = "   " + value.toUpperCase() + "   ";
      instance = new Login(value);
      assertEquals("Bad login value", UtilLoginTest.LOGIN, instance.getValue());
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Instanciation should not fail: " + ex.getMessage());
    }
    try
    {
      new Login(UtilLoginTest.LOGIN + "|");
      fail("Instanciation with forbidden character should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      new Login(null);
      fail("Instanciation with null value should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of equalsInternal(Login), of class Login.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testEqualsInternal_Login() throws Bid4WinException
  {
    Login login1 = new Login("123456");
    Login login2 = new Login("1234567");

    assertFalse(login1.equalsInternal(login2));
    assertFalse(login2.equalsInternal(login1));
    assertTrue(login1.equalsInternal(login1));
  }
}
