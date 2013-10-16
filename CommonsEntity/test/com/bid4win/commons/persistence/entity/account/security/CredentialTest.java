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
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.Bid4WinEntityTester;
import com.bid4win.commons.persistence.entity.EntityGeneratorStub;
import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 * Classe de test d'un certificat de connexion<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class CredentialTest extends Bid4WinEntityTester<AccountAbstractStub, EntityGeneratorStub>
{
  /**
   * Test of Credential(Login, Password, Role), of class Credential.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testCredential_Login_Password_Role() throws Bid4WinException
  {
    Login login = this.getGenerator().createLogin();
    Password password = this.getGenerator().createPassword();
    Role role = Role.ADMIN;
    try
    {
      Credential credential = new Credential(login, password, role);
      assertEquals("Wrong login", login, credential.getLogin());
      assertEquals("Wrong password", password, credential.getPassword());
      assertTrue("Wrong role", role == credential.getRole());
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Instanciation should not fail: " + ex.getMessage());
    }
    try
    {
      new Credential(null, password, role);
      fail("Instanciation with null login should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      new Credential(login, null, role);
      fail("Instanciation with null password should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      new Credential(login, password, null);
      fail("Instanciation with null role should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of Credential(Login, Password), of class Credential.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testCredential_Login_Password() throws Bid4WinException
  {
    Login login = this.getGenerator().createLogin();
    Password password = this.getGenerator().createPassword();
    try
    {
      Credential credential = new Credential(login, password);
      assertEquals("Wrong login", login, credential.getLogin());
      assertEquals("Wrong password", password, credential.getPassword());
      assertTrue("Wrong role", Role.NONE == credential.getRole());
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Instanciation should not fail: " + ex.getMessage());
    }
    try
    {
      new Credential(null, password);
      fail("Instanciation with null login should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      new Credential(login, null);
      fail("Instanciation with null password should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }

  /**
   * Test of equalsInternal(Credential), of class Credential.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testEqualsInternal_Credential() throws Bid4WinException
  {
    Login login = this.getGenerator().createLogin();
    Password password = this.getGenerator().createPassword();
    Role role = Role.ADMIN;
    Credential credential1 = new Credential(login, password, role);
    Credential credential2 = new Credential(this.getGenerator().createLogin(login.getValue() + "bis"),
                                            password, role);
    assertFalse(credential1.equalsInternal(credential2));
    assertFalse(credential2.equalsInternal(credential1));

    credential2 = new Credential(login, this.getGenerator().createPassword(password.getValue() + 1), role);
    assertFalse(credential1.equalsInternal(credential2));
    assertFalse(credential2.equalsInternal(credential1));

    credential2 = new Credential(login, password, Role.BASIC);
    assertFalse(credential1.equalsInternal(credential2));
    assertFalse(credential2.equalsInternal(credential1));

    assertTrue(credential1.equalsInternal(credential1));
  }

  /**
   * Test of definePassword(Password), of class Credential.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testDefinePassword_Password() throws Bid4WinException
  {
    Login login = this.getGenerator().createLogin();
    int i = 0;
    Password password = this.getGenerator().createPassword(i++);
    Credential credential = new Credential(login, password);
    password = this.getGenerator().createPassword(i++);
    credential.definePassword(password);
    assertEquals("Wrong password", password, credential.getPassword());
    try
    {
      credential.definePassword(null);
      fail("Should fail with null password");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertEquals("Wrong password", password, credential.getPassword());
    }
  }
  /**
   * Test of defineRole(Role), of class Credential.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testDefineRole_Role() throws Bid4WinException
  {
    Login login = this.getGenerator().createLogin();
    Password password = this.getGenerator().createPassword();
    Role role = Role.NONE;
    Credential credential = new Credential(login, password, role);
    role = Role.BASIC;
    credential.defineRole(role);
    assertEquals("Wrong role", role, credential.getRole());
    try
    {
      credential.defineRole(null);
      fail("Should fail with null role");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertEquals("Wrong role", role, credential.getRole());
    }
  }

  /**
   *
   * TODO A COMMENTER
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.core.security.ProtectableObjectTester#testCheckProtection()
   */
  @Override
  @Test
  public void testCheckProtection() throws Bid4WinException
  {
    this.startProtection();
    int i = 0;
    Login login = this.getGenerator().createLogin(i);
    Password password = this.getGenerator().createPassword(i);
    Role role = Role.BASIC;
    Credential credential = new Credential(login, password, role);
    this.stopProtection();
    i++;
    try
    {
      credential.definePassword(this.getGenerator().createPassword(i));
      fail("Should fail with protected object");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      credential.defineRole(Role.USER);
      fail("Should fail with protected object");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    assertEquals("Wrong password", password, credential.getPassword());
    assertEquals("Wrong role", role, credential.getRole());
  }
}
