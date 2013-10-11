package com.bid4win.commons.persistence.entity.account;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.EntityGenerator;

/**
 * Classe de test d'une clé à lier à un compte utilisateur<BR>
 * <BR>
 * @param <KEY> TODO A COMMENTER<BR>
 * @param <ACCOUNT> TODO A COMMENTER<BR>
 * @param <GENERATOR> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class AccountBasedKeyTester<KEY extends AccountBasedKey<KEY, ACCOUNT>,
                                            ACCOUNT extends AccountAbstract<ACCOUNT>,
                                            GENERATOR extends EntityGenerator<ACCOUNT>>
       extends AccountBasedEntityTester<KEY, ACCOUNT, GENERATOR>
{
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.account.AccountBasedEntityTester#isIdAuto()
   */
  @Override
  public boolean isIdAuto()
  {
    return false;
  }

  /**
   * Test of  AccountBasedKey(...) method, of class AccountBasedKey.
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.account.AccountBasedEntityTester#testConstructor_etc()
   */
  @Override
  @Test
  public void testConstructor_etc() throws Bid4WinException
  {
    super.testConstructor_etc();

    ACCOUNT account = this.getGenerator().createAccount("123");
    try
    {
      KEY key = this.createEntity(account);
      assertNotNull("Wrong ID", key.getId());
      try
      {
        UtilString.checkPattern("id", key.getId(), AccountBasedKey.KEY_PATTERN.getRegexp());
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
  }

  /**
   * Test of sameInternal(AccountBasedKey, boolean), of class AccountBasedKey.
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.account.AccountBasedEntityTester#testSameInternal_CLASS_boolean()
   */
  @Override
  @Test
  public void testSameInternal_CLASS_boolean() throws Bid4WinException
  {
    super.testSameInternal_CLASS_boolean();

    ACCOUNT account = this.getGenerator().createAccount("123");
    KEY key1 = this.createEntity(account);
    KEY key2 = this.createEntity(account);
    this.checkSame(key1, key2);
    this.checkSame(key2, key1);
    this.checkNotIdentical(key1, key2);
    this.checkNotIdentical(key2, key1);
  }
}
