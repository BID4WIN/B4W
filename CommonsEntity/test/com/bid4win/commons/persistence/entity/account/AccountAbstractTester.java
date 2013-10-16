package com.bid4win.commons.persistence.entity.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.persistence.entity.Bid4WinEntityGeneratedID;
import com.bid4win.commons.persistence.entity.Bid4WinEntityTester;
import com.bid4win.commons.persistence.entity.EntityGenerator;
import com.bid4win.commons.persistence.entity.account.security.Credential;
import com.bid4win.commons.persistence.entity.account.security.Login;
import com.bid4win.commons.persistence.entity.account.security.Password;
import com.bid4win.commons.persistence.entity.account.security.Role;
import com.bid4win.commons.persistence.entity.contact.Email;

/**
 * Classe de test d'un compte utilisateur<BR>
 * <BR>
 * @param <ACCOUNT> TODO A COMMENTER<BR>
 * @param <GENERATOR> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class AccountAbstractTester<ACCOUNT extends AccountAbstract<ACCOUNT>,
                                            GENERATOR extends EntityGenerator<ACCOUNT>>
       extends Bid4WinEntityTester<ACCOUNT, GENERATOR>
{
  /**
   *
   * TODO A COMMENTER
   * @param credential TODO A COMMENTER
   * @param email TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws ModelArgumentException TODO A COMMENTER
   */
  protected abstract ACCOUNT createAccount(Credential credential, Email email) throws ModelArgumentException;

  /**
   * Test of AccountAbstract(...) method, of class AccountAbstract.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testConstructor_etc() throws Bid4WinException
  {
    Credential credential = this.getGenerator().createCredential();
    Email email = this.getGenerator().createEmail();
    try
    {
      ACCOUNT account = this.createAccount(credential, email);
      try
      {
        UtilString.checkPattern("id", account.getId(),
                                Bid4WinEntityGeneratedID.ID_PATTERN.getRegexp());
      }
      catch(ModelArgumentException ex)
      {
        ex.printStackTrace();
        fail(ex.getMessage());
      }
      assertEquals("Bad credential", credential, account.getCredential());
      assertEquals("Bad email", email, account.getEmail());
    }
    catch(ModelArgumentException ex)
    {
      ex.printStackTrace();
      fail("Instanciation should not fail: " + ex.getMessage());
    }
    try
    {
      this.createAccount(null, email);
      fail("Instanciation with null credential should fail");
    }
    catch(ModelArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      this.createAccount(credential, null);
      fail("Instanciation with null email should fail");
    }
    catch(ModelArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
  }

  /**
   * Test of hasRole(Role), of class AccountAbstract.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testHasRole_Role() throws Bid4WinException
  {
    Login login = this.getGenerator().createLogin();
    Password password = this.getGenerator().createPassword();
    Credential credential = new Credential(login, password, Role.ADMIN);
    ACCOUNT account = this.createAccount(credential, this.getGenerator().createEmail());
    assertFalse("Bad result", account.hasRole(Role.NONE));
    assertTrue("Bad result", account.hasRole(Role.BASIC));
    assertFalse("Bad result", account.hasRole(Role.WAIT));
    assertTrue("Bad result", account.hasRole(Role.ADMIN));
    assertFalse("Bad result", account.hasRole(Role.SUPER));

    credential = new Credential(login, password, Role.SUPER);
    account = this.createAccount(credential, account.getEmail());
    assertFalse("Bad result", account.hasRole(Role.NONE));
    assertTrue("Bad result", account.hasRole(Role.BASIC));
    assertFalse("Bad result", account.hasRole(Role.WAIT));
    assertTrue("Bad result", account.hasRole(Role.ADMIN));
    assertTrue("Bad result", account.hasRole(Role.SUPER));
  }

  /**
   * Test of sameInternal(AccountAbstract, boolean), of class AccountAbstract.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testSameInternal_CLASS_boolean() throws Bid4WinException
  {
    ACCOUNT account1 = this.createAccount(this.getGenerator().createCredential(0),
                                          this.getGenerator().createEmail(0));
    ACCOUNT account2 = this.createAccount(this.getGenerator().createCredential(1),
                                          account1.getEmail());

    this.checkNotSame(account1, account2);
    this.checkNotSame(account2, account1);
    this.checkNotIdentical(account1, account2);
    this.checkNotIdentical(account2, account1);

    account2 = this.createAccount(account1.getCredential(), this.getGenerator().createEmail(1));

    this.checkNotSame(account1, account2);
    this.checkNotSame(account2, account1);
    this.checkNotIdentical(account1, account2);
    this.checkNotIdentical(account2, account1);

    account2 = this.createAccount(account1.getCredential(), account1.getEmail());

    this.checkSame(account1, account2);
    this.checkSame(account2, account1);
    this.checkNotIdentical(account1, account2);
    this.checkNotIdentical(account2, account1);
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
    // Pas de méthode protégée à tester
  }
}
