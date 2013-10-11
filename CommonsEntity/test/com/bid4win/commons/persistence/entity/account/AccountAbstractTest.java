package com.bid4win.commons.persistence.entity.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.persistence.entity.EntityGeneratorStub;
import com.bid4win.commons.persistence.entity.account.security.Credential;
import com.bid4win.commons.persistence.entity.contact.Email;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 * Classe de test d'un compte utilisateur<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class AccountAbstractTest extends AccountAbstractTester<AccountAbstractStub, EntityGeneratorStub>
{
  /**
   *
   * TODO A COMMENTER
   * @param credential {@inheritDoc}
   * @param email {@inheritDoc}
   * @return {@inheritDoc}
   * @throws ModelArgumentException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.account.AccountAbstractTester#createAccount(com.bid4win.commons.persistence.entity.account.security.Credential, com.bid4win.commons.persistence.entity.contact.Email)
   */
  @Override
  protected AccountAbstractStub createAccount(Credential credential, Email email) throws ModelArgumentException
  {
    return new AccountAbstractStub(credential, email);
  }

  /**
   * Test of AccountAbstract(...) method, of class AccountAbstract.
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.account.AccountAbstractTester#testConstructor_etc()
   */
  @Override
  @Test
  public void testConstructor_etc() throws Bid4WinException
  {
    super.testConstructor_etc();

    Credential credential = this.getGenerator().createCredential();
    Email email = this.getGenerator().createEmail();
    String id = "123456abcdef";
    try
    {
      AccountAbstract<?> account = new AccountAbstractStub("   " + id + "   ", credential, email);
      assertEquals("Bad ID", id, account.getId());
      assertEquals("Bad credential", credential, account.getCredential());
      assertEquals("Bad email", email, account.getEmail());
      account = new AccountAbstractStub(null, credential, email);
      assertEquals("Bad ID", null, account.getId());
      assertEquals("Bad credential", credential, account.getCredential());
      assertEquals("Bad email", email, account.getEmail());
      account = new AccountAbstractStub("   ", credential, email);
      assertEquals("Bad ID", null, account.getId());
      assertEquals("Bad credential", credential, account.getCredential());
      assertEquals("Bad email", email, account.getEmail());
    }
    catch(ModelArgumentException ex)
    {
      fail("Instanciation should not fail: " + ex.getMessage());
    }
    try
    {
      new AccountAbstractStub(id, null, email);
      fail("Instanciation with null credential should fail");
    }
    catch(ModelArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      new AccountAbstractStub(id, credential, null);
      fail("Instanciation with null email should fail");
    }
    catch(ModelArgumentException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of sameInternal(AccountAbstract, boolean), of class AccountAbstract.
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.account.AccountAbstractTester#testSameInternal_CLASS_boolean()
   */
  @Override
  @Test
  public void testSameInternal_CLASS_boolean() throws Bid4WinException
  {
    super.testSameInternal_CLASS_boolean();

    AccountAbstractStub account1 = new AccountAbstractStub(
        "1", this.getGenerator().createCredential(0), this.getGenerator().createEmail(0));
    AccountAbstractStub account2 = new AccountAbstractStub(
        "2", account1.getCredential(), account1.getEmail());

    this.checkSame(account1, account2);
    this.checkSame(account2, account1);
    this.checkNotIdentical(account1, account2);
    this.checkNotIdentical(account2, account1);

    account2 = new AccountAbstractStub(account1.getId(), account1.getCredential(), account1.getEmail());

    this.checkSame(account1, account2);
    this.checkSame(account2, account1);
    this.checkIdentical(account1, account2);
    this.checkIdentical(account2, account1);
  }
}
