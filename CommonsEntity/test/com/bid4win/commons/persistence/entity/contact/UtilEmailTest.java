package com.bid4win.commons.persistence.entity.contact;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.Bid4WinCoreTester;
import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.account.security.UtilLogin;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 * Classe de test de l'utilitaire d'emails<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class UtilEmailTest extends Bid4WinCoreTester
{
  /** Email le plus complexe autorisé */
  public final static String EMAIL = "a1_2b-3c4.d5e+f%g@h1-2i.3j4-k5l.mn";

  /**
   * Test of checkAddress(String), of class UtilEmail.
   */
  @Test
  public void testCheckAddress_String()
  {
    try
    {
      UtilEmail.checkAddress("a@b.cd");
      UtilEmail.checkAddress("a@b.com");
      UtilEmail.checkAddress("a@b.info");
      UtilEmail.checkAddress("a@b.museum");
      UtilEmail.checkAddress("a1@b2.cd");
      UtilEmail.checkAddress("a_b@c.de");
      UtilEmail.checkAddress("a-b@c.de");
      UtilEmail.checkAddress("a.b@c.de");
      UtilEmail.checkAddress("a+b@c.de");
      UtilEmail.checkAddress("a%b@c.de");
      UtilEmail.checkAddress("a@b.c.de");
      UtilEmail.checkAddress("a@b-c.de");
      UtilEmail.checkAddress("a@b.c-d.ef");
      UtilEmail.checkAddress(UtilEmailTest.EMAIL);
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Check should not have failed with valid address: " + ex.getMessage());
    }
    try
    {
      UtilEmail.checkAddress("A@b.cd");
      fail("Check with capital character should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilLogin.checkLogin("a|@b.cd");
      fail("Check with forbidden character should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilLogin.checkLogin("a@b_c.cd");
      fail("Check with forbidden character should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilEmail.checkAddress(UtilString.EMPTY);
      fail("Check should have failed with invalid address");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilEmail.checkAddress("a@");
      fail("Check should have failed with invalid address");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilEmail.checkAddress("a@b");
      fail("Check should have failed with invalid address");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilEmail.checkAddress("a@b.");
      fail("Check should have failed with invalid address");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilEmail.checkAddress("a@b.c");
      fail("Check should have failed with invalid address");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilEmail.checkAddress("a@b.cde");
      fail("Check should have failed with invalid address");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilEmail.checkAddress("a.@b.c");
      fail("Check should have failed with invalid address");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilEmail.checkAddress(".a@b.c");
      fail("Check should have failed with invalid address");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilEmail.checkAddress("a@.b.c");
      fail("Check should have failed with invalid address");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilEmail.checkAddress("a@b..c");
      fail("Check should have failed with invalid address");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilEmail.checkAddress("a.@b.c.");
      fail("Check should have failed with invalid address");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
}
