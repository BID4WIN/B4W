package com.bid4win.persistence.entity.account.credit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntityTester;
import com.bid4win.persistence.entity.EntityGenerator;
import com.bid4win.persistence.entity.account.Account;

/**
 * Classe de test d'un lot de crédit<BR>
 * <BR>
 * @param <BUNDLE> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class CreditBundleAbstractTester<BUNDLE extends CreditBundleAbstract<BUNDLE>>
       extends AccountBasedEntityTester<BUNDLE, Account, EntityGenerator>
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
  protected BUNDLE createEntity(Account account) throws UserException
  {
    try
    {
      return this.createEntity(account, this.getGenerator().createCreditOrigin(Origin.SPONSORSHIP, "012345678912"), 0, 1);
    }
    catch(Bid4WinException ex)
    {
      throw new UserException(MessageRef.UNKNOWN_ERROR, ex);
    }
  }
  /**
   *
   * TODO A COMMENTER
   * @param account TODO A COMMENTER
   * @param origin TODO A COMMENTER
   * @param totalValue TODO A COMMENTER
   * @param nb TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  protected abstract BUNDLE createEntity(Account account, CreditOrigin origin, double totalValue, int nb) throws UserException;

  /**
   * Test of  CreditBundleAbstract(...) method, of class CreditBundleAbstract.
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.account.AccountBasedEntityTester#testConstructor_etc()
   */
  @Override
  @Test
  public void testConstructor_etc() throws Bid4WinException
  {
    super.testConstructor_etc();

    Account account = this.getGenerator().createAccount("123");
    CreditOrigin origin = this.getGenerator().createCreditOrigin();
    try
    {
      BUNDLE bundle = this.createEntity(account, origin, 1.23456, 1);
      assertEquals("Bad origin", origin, bundle.getOrigin());
      assertTrue("Bad unit value", 1.2346 == bundle.getUnitValue().getValue());
      assertEquals("Bad credit nb", 1, bundle.getNb());
      assertTrue("Bad account", account == bundle.getAccount());

      bundle = this.createEntity(account, origin, 1.23456, 10);
      assertEquals("Bad origin", origin, bundle.getOrigin());
      assertTrue("Bad unit value", 0.1235 == bundle.getUnitValue().getValue());
      assertEquals("Bad credit nb", 10, bundle.getNb());
      assertTrue("Bad account", account == bundle.getAccount());
    }
    catch(Bid4WinException ex)
    {
      ex.printStackTrace();
      fail("Instanciation should not fail: " + ex.getMessage());
    }
    try
    {
      this.createEntity(account, null, 1.23456, 1);
      fail("Instanciation with null origin should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      this.createEntity(account, origin, 1.23456, 0);
      fail("Instanciation with zero initial credit nb should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of sameInternal(CreditBundleAbstract, boolean), of class CreditBundleAbstract.
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.account.AccountBasedEntityTester#testSameInternal_CLASS_boolean()
   */
  @Override
  @Test
  public void testSameInternal_CLASS_boolean() throws Bid4WinException
  {
    super.testSameInternal_CLASS_boolean();

    Long id = 123l;
    CreditOrigin origin1 = this.getGenerator().createCreditOrigin();
    CreditOrigin origin2 = this.getGenerator().createCreditOrigin();
    Account account1 = this.getGenerator().createAccount("" + id);
    Account account2 = this.getGenerator().createAccount(account1.getId());

    BUNDLE bundle1 = this.createEntity(account1, origin1, 1.23456, 2);
    BUNDLE bundle2 = this.createEntity(
        account2, origin2, bundle1.getUnitValue().getValue(), bundle1.getNb());
    assertFalse(bundle1.same(bundle2));
    assertFalse(bundle2.same(bundle1));
    assertFalse(bundle1.identical(bundle2));
    assertFalse(bundle2.identical(bundle1));

    account2 = this.getGenerator().createAccount(account1.getId());
    bundle2 = this.createEntity(
        account2, origin1, bundle1.getUnitValue().getValue() + 0.1, bundle1.getNb());
    assertFalse(bundle1.same(bundle2));
    assertFalse(bundle2.same(bundle1));
    assertFalse(bundle1.identical(bundle2));
    assertFalse(bundle2.identical(bundle1));

    account2 = this.getGenerator().createAccount(account1.getId());
    bundle2 = this.createEntity(
        account2, bundle1.getOrigin(), bundle1.getUnitValue().getValue(), bundle1.getNb() + 1);
    assertFalse(bundle1.same(bundle2));
    assertFalse(bundle2.same(bundle1));
    assertFalse(bundle1.identical(bundle2));
    assertFalse(bundle2.identical(bundle1));

    account2 = this.getGenerator().createAccount(account1.getId());
    bundle2 = this.createEntity(account2, origin1, bundle1.getUnitValue().getValue() * bundle1.getNb(), bundle1.getNb());
    assertTrue(bundle1.same(bundle2));
    assertTrue(bundle2.same(bundle1));
    assertTrue(bundle1.identical(bundle2));
    assertTrue(bundle2.identical(bundle1));
  }
}
