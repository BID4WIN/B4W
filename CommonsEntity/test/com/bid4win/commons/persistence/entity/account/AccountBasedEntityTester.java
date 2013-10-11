package com.bid4win.commons.persistence.entity.account;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.UserException;
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
   * Test of sameInternal(AccountBasedEntity, boolean), of class AccountBasedEntity.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testSameInternal_CLASS_boolean() throws Bid4WinException
  {
    long id = 123;
/*    AccountBasedEntityMultipleStub key1 = new AccountBasedEntityMultipleStub(
        this.createAccount("" + id));
    AccountBasedEntityMultipleStub key2 = new AccountBasedEntityMultipleStub(
        this.createAccount("" + id + 1)); TODO FAIRE LE TEST DANS LA BONNE CLASSE
    this.checkSame(key1, key2);
    this.checkSame(key2, key1);
    this.checkNotIdentical(key1, key2);
    this.checkNotIdentical(key2, key1);
    key2 = new AccountBasedEntityMultipleStub(this.createAccount("" + id));
    this.checkSame(key1, key2);
    this.checkSame(key2, key1);
    this.checkIdentical(key1, key2);
    this.checkIdentical(key2, key1);*/

    ENTITY entity1 = this.createEntity(this.getGenerator().createAccount("" + id));
    ENTITY entity2 = this.createEntity(this.getGenerator().createAccount("" + id));
    this.checkSame(entity1, entity2);
    this.checkSame(entity2, entity1);
    if(this.isIdAuto())
    {
      this.checkIdentical(entity1, entity2);
      this.checkIdentical(entity2, entity1);
    }

    entity1 = this.createEntity(this.getGenerator().createAccount("" + id));
    entity2 = this.createEntity(this.getGenerator().createAccount("" + id + 1));
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
