package com.bid4win.commons.persistence.entity.account;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.Bid4WinEntityTester;
import com.bid4win.commons.persistence.entity.EntityGenerator;

/**
 * Classe de test d'une entité liée à un compte utilisateur<BR>
 * <BR>
 * @param <ENTITY> TODO A COMMENTER<BR>
 * @param <ACCOUNT> TODO A COMMENTER<BR>
 * @param <GENERATOR> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class AccountBasedEntityTester<ENTITY extends AccountBasedEntity<ENTITY, ?, ACCOUNT>,
                                               ACCOUNT extends AccountAbstract<ACCOUNT>,
                                               GENERATOR extends EntityGenerator<ACCOUNT>>
       extends Bid4WinEntityTester<ACCOUNT, GENERATOR>
{
  /**
   *
   * TODO A COMMENTER
   * @param account TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  protected abstract ENTITY createEntity(ACCOUNT account) throws UserException;
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public boolean isIdAuto()
  {
    return true;
  }

  /**
   * Test of  AccountBasedEntity(...) method, of class AccountBasedEntity.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testConstructor_etc() throws Bid4WinException
  {
    ACCOUNT account = this.getGenerator().createAccount("123");
    try
    {
      ENTITY entity = this.createEntity(account);
      assertTrue("Bad account link", account == entity.getAccount());
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Instanciation should not fail: " + ex.getMessage());
    }
    try
    {
      this.createEntity(null);
      fail("Instanciation with null account should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }

  /**
   * Test of linkToAccount(ACCOUNT) method, of class AccountBasedEntity.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testLinkToAccount_ACCOUNT() throws Bid4WinException
  {
    ACCOUNT account1 = this.getGenerator().createAccount("123");
    ACCOUNT account2 = this.getGenerator().createAccount("123");
    ENTITY entity = this.createEntity(account1);
    try
    {
      entity.linkToAccount(account2);
      fail("Should fail with already linked entity");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertTrue("Link should not have been changed", account1  == entity.getAccount());
    }
    entity.unlinkFromAccount();
    try
    {
      entity.linkToAccount(account2);
      assertTrue("Link should not have been changed", account2  == entity.getAccount());
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Should not fail with unlinked entity");
    }
  }

  /**
   * Test of unlinkFromAccount() method, of class AccountBasedEntity.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testUnlinkFromAccount_0args() throws Bid4WinException
  {
    ACCOUNT account = this.getGenerator().createAccount("123");
    ENTITY entity = this.createEntity(account);
    try
    {
      entity.unlinkFromAccount();
      assertNull("Link should have been removed", entity.getAccount());
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Should not fail with linked entity");
    }
    try
    {
      entity.unlinkFromAccount();
      fail("Should fail with unlinked entity");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertNull("Link should not have been changed", entity.getAccount());
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
    String protectionId = this.startProtection();
    ACCOUNT account = this.getGenerator().createAccount("123");
    ENTITY entity = this.createEntity(account);
    this.stopProtection();
    try
    {
      entity.unlinkFromAccount();
      fail("Should fail with protected entity");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    this.startProtection(protectionId);
    entity.unlinkFromAccount();
    this.stopProtection();
    try
    {
      entity.linkToAccount(account);
      fail("Should fail with protected entity");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    assertNull("Link should still been removed", entity.getAccount());
  }
 /**
   * Test of sameInternal(AccountBasedEntity, boolean), of class AccountBasedEntity.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testSameInternal_CLASS_boolean() throws Bid4WinException
  {
    long id = 123;
    ENTITY entity1 = this.createEntity(this.getGenerator().createAccount(UtilString.EMPTY + id));
    ENTITY entity2 = this.createEntity(this.getGenerator().createAccount(UtilString.EMPTY + id));
    this.checkSame(entity1, entity2);
    this.checkSame(entity2, entity1);
    if(this.isIdAuto())
    {
      this.checkIdentical(entity1, entity2);
      this.checkIdentical(entity2, entity1);
    }

    entity1 = this.createEntity(this.getGenerator().createAccount(UtilString.EMPTY + id));
    entity2 = this.createEntity(this.getGenerator().createAccount(UtilString.EMPTY + id + 1));
    this.checkSame(entity1, entity2);
    this.checkSame(entity2, entity1);
    this.checkNotIdentical(entity1, entity2);
    this.checkNotIdentical(entity2, entity1);

    entity1 = this.createEntity(this.getGenerator().createAccount(0));
    entity2 = this.createEntity(this.getGenerator().createAccount(1));
    this.checkNotSame(entity1, entity2);
    this.checkNotSame(entity2, entity1);
    this.checkNotIdentical(entity1, entity2);
    this.checkNotIdentical(entity2, entity1);
  }
}
