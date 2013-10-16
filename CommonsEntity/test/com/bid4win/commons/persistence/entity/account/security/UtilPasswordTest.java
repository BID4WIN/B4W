package com.bid4win.commons.persistence.entity.account.security;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.Bid4WinCoreTester;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 * Classe de test de l'utilitaire de mot de passe de connexion<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class UtilPasswordTest extends Bid4WinCoreTester
{
  /** Mot de passe le plus complexe autorisé */
  public final static String HASHED_PASSWORD = "1234567890abcdef";

  /**
   * Test of checkHashedPassword(String), of class UtilPassword.
   */
  @Test
  public void testCheckHashedPassword_String()
  {
    try
    {
      UtilPassword.checkHashedPassword(UtilPasswordTest.HASHED_PASSWORD);
      UtilPassword.checkHashedPassword("123ab");
      UtilPassword.checkHashedPassword("012345678901234567890123456789012345678901234567890123456789");
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Check should not fail: " + ex.getMessage());
    }
    try
    {
      UtilPassword.checkHashedPassword("123abC");
      fail("Check with capital character should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilPassword.checkHashedPassword("123abcg");
      fail("Check with forbidden character should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilPassword.checkHashedPassword("123a");
      fail("Check with too short value should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilPassword.checkHashedPassword("0123456789012345678901234567890123456789012345678901234567890");
      fail("Check with too long value should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
}
