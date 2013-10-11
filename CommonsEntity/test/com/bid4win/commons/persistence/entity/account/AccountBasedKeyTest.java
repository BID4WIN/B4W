package com.bid4win.commons.persistence.entity.account;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.IdPattern;
import com.bid4win.commons.persistence.entity.EntityGeneratorStub;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 * Classe de test d'une clé à lier à un compte utilisateur<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class AccountBasedKeyTest extends AccountBasedKeyTester<AccountBasedKeyStub, AccountAbstractStub, EntityGeneratorStub>
{
  /**
   *
   * TODO A COMMENTER
   * @param account {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.account.AccountBasedEntityTester#createEntity(com.bid4win.commons.persistence.entity.account.AccountAbstract)
   */
  @Override
  protected AccountBasedKeyStub createEntity(AccountAbstractStub account) throws UserException
  {
    return new AccountBasedKeyStub(account);
  }

  /**
   * Test of  AccountBasedKey(...) method, of class AccountBasedKey.
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.account.AccountBasedKeyTester#testConstructor_etc()
   */
  @Override
  @Test
  public void testConstructor_etc() throws Bid4WinException
  {
    super.testConstructor_etc();

    AccountAbstractStub account = this.getGenerator().createAccount("123");
    IdPattern idPattern = new IdPattern("NBA-ABN");
    try
    {
      AccountBasedKeyStub key = new AccountBasedKeyStub(idPattern, account);
      assertNotNull("Wrong ID", key.getId());
      try
      {
        UtilString.checkPattern("id", key.getId(), idPattern.getRegexp());
      }
      catch(ModelArgumentException ex)
      {
        ex.printStackTrace();
        fail(ex.getMessage());
      }
      assertTrue("Bad account link", account == key.getAccount());
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Instanciation should not fail: " + ex.getMessage());
    }
    try
    {
      new AccountBasedKeyStub(null, account);
      fail("Instanciation with null ID pattern should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
}
