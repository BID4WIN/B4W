package com.bid4win.commons.persistence.entity.account;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.EntityGeneratorStub;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 * Classe de test d'une entité liée à un compte utilisateur<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class AccountBasedEntityTest
       extends AccountBasedEntityTester<AccountBasedEntityStub, AccountAbstractStub, EntityGeneratorStub>
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
  protected AccountBasedEntityStub createEntity(AccountAbstractStub account) throws UserException
  {
    return new AccountBasedEntityStub(account);
  }

  /**
   * Test of AccountBasedEntity(...) method, of class AccountBasedEntity.
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.account.AccountBasedEntityTester#testConstructor_etc()
   */
  @Override
  @Test
  public void testConstructor_etc() throws Bid4WinException
  {
    super.testConstructor_etc();

    AccountAbstractStub account = this.getGenerator().createAccount("123");
    try
    {
      AccountBasedEntityStub entity = new AccountBasedEntityStub(123L, account);
      assertTrue("Bad ID", 123 == entity.getId());
      assertTrue("Bad account link", account == entity.getAccount());
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Instanciation should not fail: " + ex.getMessage());
    }
    try
    {
      new AccountBasedEntityStub(123L, null);
      fail("Instanciation with null account should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }

  /**
   * Test of sameInternal(AccountBasedEntity, boolean), of class AccountBasedEntity.
   * @throws Bid4WinException {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.account.AccountBasedEntityTester#testSameInternal_CLASS_boolean()
   */
  @Override
  @Test
  public void testSameInternal_CLASS_boolean() throws Bid4WinException
  {
    super.testSameInternal_CLASS_boolean();

    long id = 123;

    AccountBasedEntityStub entity1 = new AccountBasedEntityStub(id, this.getGenerator().createAccount(UtilString.EMPTY + id));
    AccountBasedEntityStub entity2 = new AccountBasedEntityStub(id + 1, this.getGenerator().createAccount(UtilString.EMPTY + id));
    this.checkSame(entity1, entity2);
    this.checkSame(entity2, entity1);
    this.checkNotIdentical(entity1, entity2);
    this.checkNotIdentical(entity2, entity1);

    entity2 = new AccountBasedEntityStub(id, this.getGenerator().createAccount(UtilString.EMPTY + id));
    this.checkSame(entity1, entity2);
    this.checkSame(entity2, entity1);
    this.checkIdentical(entity1, entity2);
    this.checkIdentical(entity2, entity1);
  }
}
