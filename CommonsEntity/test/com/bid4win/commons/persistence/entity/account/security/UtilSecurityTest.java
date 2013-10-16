package com.bid4win.commons.persistence.entity.account.security;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.Bid4WinCoreTester;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthorizationException;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */

@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class UtilSecurityTest extends Bid4WinCoreTester
{
  /**
   * Test of checkRole(Role, Role ...), of class Role.
   */
  @Test
  public void testCheckRole_Role_Role_etc()
  {
    this.testCheckRole(Role.NONE, true);
    this.testCheckRole(Role.NONE, true, Role.NONE, Role.NONE);
    this.testCheckRole(Role.NONE, false, Role.BASIC, Role.NONE);
    this.testCheckRole(Role.NONE, false, Role.NONE, Role.BASIC);
    this.testCheckRole(Role.NONE, false, Role.WAIT);
    this.testCheckRole(Role.NONE, false, Role.USER);
    this.testCheckRole(Role.NONE, false, Role.MANAGER);
    this.testCheckRole(Role.NONE, false, Role.I18N);
    this.testCheckRole(Role.NONE, false, Role.BIZ);
    this.testCheckRole(Role.NONE, false, Role.TECH);
    this.testCheckRole(Role.NONE, false, Role.ADMIN);
    this.testCheckRole(Role.NONE, false, Role.SUPER);

    this.testCheckRole(Role.BASIC, true);
    this.testCheckRole(Role.BASIC, false, Role.NONE);
    this.testCheckRole(Role.BASIC, true, Role.BASIC);
    this.testCheckRole(Role.BASIC, false, Role.WAIT);
    this.testCheckRole(Role.BASIC, false, Role.USER);
    this.testCheckRole(Role.BASIC, false, Role.MANAGER);
    this.testCheckRole(Role.BASIC, false, Role.I18N);
    this.testCheckRole(Role.BASIC, false, Role.BIZ);
    this.testCheckRole(Role.BASIC, false, Role.TECH);
    this.testCheckRole(Role.BASIC, false, Role.ADMIN);
    this.testCheckRole(Role.BASIC, false, Role.SUPER);

    this.testCheckRole(Role.WAIT, true);
    this.testCheckRole(Role.WAIT, false, Role.NONE);
    this.testCheckRole(Role.WAIT, true, Role.BASIC);
    this.testCheckRole(Role.WAIT, true, Role.WAIT);
    this.testCheckRole(Role.WAIT, false, Role.USER);
    this.testCheckRole(Role.WAIT, false, Role.MANAGER);
    this.testCheckRole(Role.WAIT, false, Role.I18N);
    this.testCheckRole(Role.WAIT, false, Role.BIZ);
    this.testCheckRole(Role.WAIT, false, Role.TECH);
    this.testCheckRole(Role.WAIT, false, Role.ADMIN);
    this.testCheckRole(Role.WAIT, false, Role.SUPER);

    this.testCheckRole(Role.USER, true);
    this.testCheckRole(Role.USER, false, Role.NONE);
    this.testCheckRole(Role.USER, true, Role.BASIC);
    this.testCheckRole(Role.USER, false, Role.WAIT);
    this.testCheckRole(Role.USER, true, Role.USER);
    this.testCheckRole(Role.USER, false, Role.MANAGER);
    this.testCheckRole(Role.USER, false, Role.I18N);
    this.testCheckRole(Role.USER, false, Role.BIZ);
    this.testCheckRole(Role.USER, false, Role.TECH);
    this.testCheckRole(Role.USER, false, Role.ADMIN);
    this.testCheckRole(Role.USER, false, Role.SUPER);

    this.testCheckRole(Role.MANAGER, true);
    this.testCheckRole(Role.MANAGER, false, Role.NONE);
    this.testCheckRole(Role.MANAGER, true, Role.BASIC);
    this.testCheckRole(Role.MANAGER, false, Role.WAIT);
    this.testCheckRole(Role.MANAGER, false, Role.USER);
    this.testCheckRole(Role.MANAGER, true, Role.MANAGER);
    this.testCheckRole(Role.MANAGER, false, Role.I18N);
    this.testCheckRole(Role.MANAGER, false, Role.BIZ);
    this.testCheckRole(Role.MANAGER, false, Role.TECH);
    this.testCheckRole(Role.MANAGER, false, Role.ADMIN);
    this.testCheckRole(Role.MANAGER, false, Role.SUPER);

    this.testCheckRole(Role.I18N, true);
    this.testCheckRole(Role.I18N, false, Role.NONE);
    this.testCheckRole(Role.I18N, true, Role.BASIC);
    this.testCheckRole(Role.I18N, false, Role.WAIT);
    this.testCheckRole(Role.I18N, false, Role.USER);
    this.testCheckRole(Role.I18N, true, Role.MANAGER);
    this.testCheckRole(Role.I18N, true, Role.I18N);
    this.testCheckRole(Role.I18N, false, Role.BIZ);
    this.testCheckRole(Role.I18N, false, Role.TECH);
    this.testCheckRole(Role.I18N, false, Role.ADMIN);
    this.testCheckRole(Role.I18N, false, Role.SUPER);

    this.testCheckRole(Role.BIZ, true);
    this.testCheckRole(Role.BIZ, false, Role.NONE);
    this.testCheckRole(Role.BIZ, true, Role.BASIC);
    this.testCheckRole(Role.BIZ, false, Role.WAIT);
    this.testCheckRole(Role.BIZ, false, Role.USER);
    this.testCheckRole(Role.BIZ, true, Role.MANAGER);
    this.testCheckRole(Role.BIZ, true, Role.I18N);
    this.testCheckRole(Role.BIZ, true, Role.BIZ);
    this.testCheckRole(Role.BIZ, false, Role.TECH);
    this.testCheckRole(Role.BIZ, false, Role.ADMIN);
    this.testCheckRole(Role.BIZ, false, Role.SUPER);

    this.testCheckRole(Role.TECH, true);
    this.testCheckRole(Role.TECH, false, Role.NONE);
    this.testCheckRole(Role.TECH, true, Role.BASIC);
    this.testCheckRole(Role.TECH, false, Role.WAIT);
    this.testCheckRole(Role.TECH, false, Role.USER);
    this.testCheckRole(Role.TECH, true, Role.MANAGER);
    this.testCheckRole(Role.TECH, false, Role.I18N);
    this.testCheckRole(Role.TECH, false, Role.BIZ);
    this.testCheckRole(Role.TECH, true, Role.TECH);
    this.testCheckRole(Role.TECH, false, Role.ADMIN);
    this.testCheckRole(Role.TECH, false, Role.SUPER);

    this.testCheckRole(Role.ADMIN, true);
    this.testCheckRole(Role.ADMIN, false, Role.NONE);
    this.testCheckRole(Role.ADMIN, true, Role.BASIC);
    this.testCheckRole(Role.ADMIN, false, Role.WAIT);
    this.testCheckRole(Role.ADMIN, false, Role.USER);
    this.testCheckRole(Role.ADMIN, true, Role.MANAGER);
    this.testCheckRole(Role.ADMIN, true, Role.I18N);
    this.testCheckRole(Role.ADMIN, true, Role.BIZ);
    this.testCheckRole(Role.ADMIN, true, Role.TECH);
    this.testCheckRole(Role.ADMIN, true, Role.ADMIN);
    this.testCheckRole(Role.ADMIN, false, Role.SUPER);

    this.testCheckRole(Role.SUPER, true);
    this.testCheckRole(Role.SUPER, false, Role.NONE);
    this.testCheckRole(Role.SUPER, true, Role.BASIC);
    this.testCheckRole(Role.SUPER, false, Role.WAIT);
    this.testCheckRole(Role.SUPER, true, Role.USER);
    this.testCheckRole(Role.SUPER, true, Role.MANAGER);
    this.testCheckRole(Role.SUPER, true, Role.I18N);
    this.testCheckRole(Role.SUPER, true, Role.BIZ);
    this.testCheckRole(Role.SUPER, true, Role.TECH);
    this.testCheckRole(Role.SUPER, true, Role.ADMIN);
    this.testCheckRole(Role.SUPER, true, Role.SUPER);
  }

  /**
   *
   * TODO A COMMENTER
   * @param role TODO A COMMENTER
   * @param ok TODO A COMMENTER
   * @param roles TODO A COMMENTER
   */
  protected void testCheckRole(Role role, boolean ok, Role ... roles)
  {
    try
    {
      UtilSecurity.checkRole(role, roles);
      if(!ok)
      {
        fail("checkRole should fail");
      }
    }
    catch(AuthorizationException ex)
    {
      if(ok)
      {
        fail("checkRole should fail");
      }
    }
  }
}
