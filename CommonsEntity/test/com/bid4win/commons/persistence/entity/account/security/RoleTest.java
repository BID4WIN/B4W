package com.bid4win.commons.persistence.entity.account.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.Bid4WinObjectTypeTester;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 * Classe de test d'un rôle<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class RoleTest extends Bid4WinObjectTypeTester
{
  /**
   * Test of getDefaultType(Class), of class Role.
   * @see com.bid4win.commons.core.Bid4WinObjectTypeTester#testGetDefaultType_Class()
   */
  @Override
  @Test
  public void testGetDefaultType_Class()
  {
    assertEquals("Wrong default", Role.NONE, Bid4WinObjectType.getDefaultType(Role.class));
    assertEquals("Wrong default", Role.NONE, Role.DEFAULT);
  }
  /**
   * Test of belongTo(Role), of class Role.
   */
  @Test
  public void testBelongTo_Role()
  {
    assertTrue("Wrong result", Role.NONE.belongsTo(Role.NONE));
    assertFalse("Wrong result", Role.NONE.belongsTo(Role.BASIC));
    assertFalse("Wrong result", Role.NONE.belongsTo(Role.USER));
    assertFalse("Wrong result", Role.NONE.belongsTo(Role.ADMIN));
    assertFalse("Wrong result", Role.NONE.belongsTo(Role.SUPER));
    assertFalse("Wrong result", Role.NONE.belongsTo(Role.WAIT));

    assertFalse("Wrong result", Role.BASIC.belongsTo(Role.NONE));
    assertTrue("Wrong result", Role.BASIC.belongsTo(Role.BASIC));
    assertFalse("Wrong result", Role.BASIC.belongsTo(Role.USER));
    assertFalse("Wrong result", Role.BASIC.belongsTo(Role.ADMIN));
    assertFalse("Wrong result", Role.BASIC.belongsTo(Role.SUPER));
    assertFalse("Wrong result", Role.BASIC.belongsTo(Role.WAIT));

    assertFalse("Wrong result", Role.USER.belongsTo(Role.NONE));
    assertTrue("Wrong result", Role.USER.belongsTo(Role.BASIC));
    assertTrue("Wrong result", Role.USER.belongsTo(Role.USER));
    assertFalse("Wrong result", Role.USER.belongsTo(Role.ADMIN));
    assertFalse("Wrong result", Role.USER.belongsTo(Role.SUPER));
    assertFalse("Wrong result", Role.USER.belongsTo(Role.WAIT));

    assertFalse("Wrong result", Role.ADMIN.belongsTo(Role.NONE));
    assertTrue("Wrong result", Role.ADMIN.belongsTo(Role.BASIC));
    assertFalse("Wrong result", Role.ADMIN.belongsTo(Role.USER));
    assertTrue("Wrong result", Role.ADMIN.belongsTo(Role.ADMIN));
    assertFalse("Wrong result", Role.ADMIN.belongsTo(Role.SUPER));
    assertFalse("Wrong result", Role.ADMIN.belongsTo(Role.WAIT));

    assertFalse("Wrong result", Role.SUPER.belongsTo(Role.NONE));
    assertTrue("Wrong result", Role.SUPER.belongsTo(Role.BASIC));
    assertTrue("Wrong result", Role.SUPER.belongsTo(Role.USER));
    assertTrue("Wrong result", Role.SUPER.belongsTo(Role.ADMIN));
    assertTrue("Wrong result", Role.SUPER.belongsTo(Role.SUPER));
    assertFalse("Wrong result", Role.SUPER.belongsTo(Role.WAIT));

    assertFalse("Wrong result", Role.WAIT.belongsTo(Role.NONE));
    assertTrue("Wrong result", Role.WAIT.belongsTo(Role.BASIC));
    assertFalse("Wrong result", Role.WAIT.belongsTo(Role.USER));
    assertFalse("Wrong result", Role.WAIT.belongsTo(Role.ADMIN));
    assertFalse("Wrong result", Role.WAIT.belongsTo(Role.SUPER));
    assertTrue("Wrong result", Role.WAIT.belongsTo(Role.WAIT));
  }
}
