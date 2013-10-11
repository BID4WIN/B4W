package com.bid4win.commons.persistence.entity.account.security;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.Bid4WinEntityTester;
import com.bid4win.commons.persistence.entity.EntityGeneratorStub;
import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 * Classe de test de l'utilitaire d'identifiant de connexion<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class UtilLoginTest extends Bid4WinEntityTester<AccountAbstractStub, EntityGeneratorStub>
{
  /** Identifiant le plus complexe autorisé */
  public final static String LOGIN = "123abc_-.$cba321";

  /**
   * Test of checkLogin(String), of class UtilLogin.
   */
  @Test
  public void testCheckLogin_String()
  {
    try
    {
      UtilLogin.checkLogin(UtilLoginTest.LOGIN);
      UtilLogin.checkLogin("123ab");
      UtilLogin.checkLogin("012345678901234567890123456789");
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Check should not fail: " + ex.getMessage());
    }
    try
    {
      UtilLogin.checkLogin("123abC__bca321");
      fail("Check with capital character should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilLogin.checkLogin("123abc_-$.|cba321");
      fail("Check with forbidden character should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilLogin.checkLogin("123a");
      fail("Check with too short value should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilLogin.checkLogin("0123456789012345678901234567890");
      fail("Check with too long value should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
}
