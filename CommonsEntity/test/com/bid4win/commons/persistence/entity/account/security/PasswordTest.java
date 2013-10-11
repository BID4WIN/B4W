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
 * Classe de test d'un mot de passe<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class PasswordTest extends Bid4WinEntityTester<AccountAbstractStub, EntityGeneratorStub>
{
  /**
   * Test of Password(String), of class Password.
   */
  @Test
  public void testPassword_String()
  {
    try
    {
      String value = UtilPasswordTest.HASHED_PASSWORD;
      Password instance = new Password(value);
      assertEquals("Bad password value", value, instance.getValue());

      value = "   " + value.toUpperCase() + "   ";
      instance = new Password(value);
      assertEquals("Bad password value", UtilPasswordTest.HASHED_PASSWORD, instance.getValue());
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Instanciation should not fail: " + ex.getMessage());
    }
    try
    {
      new Password(UtilPasswordTest.HASHED_PASSWORD + "g");
      fail("Instanciation with forbidden character should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      new Password(null);
      fail("Instanciation with null value should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of equalsInternal(Password), of class Password.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testEqualsInternal_Password() throws Bid4WinException
  {
    Password password1 = new Password("123456");
    Password password2 = new Password("1234567");

    assertFalse(password1.equalsInternal(password2));
    assertFalse(password2.equalsInternal(password1));
    assertTrue(password1.equalsInternal(password1));
  }
}
