package com.bid4win.persistence.entity.account.credit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.entity.account.Account;

/**
 * Classe de test d'un lot de crédit<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
public class CreditBundleTest extends CreditBundleAbstractTester<CreditBundle>
{
  /**
   *
   * TODO A COMMENTER
   * @param account {@inheritDoc}
   * @param origin {@inheritDoc}
   * @param totalValue {@inheritDoc}
   * @param nb {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.persistence.entity.account.credit.CreditBundleAbstractTester#createEntity(com.bid4win.persistence.entity.account.Account, com.bid4win.persistence.entity.account.credit.CreditOrigin, double, int)
   */
  @Override
  protected CreditBundle createEntity(Account account, CreditOrigin origin, double totalValue, int nb) throws UserException
  {
    return new CreditBundle(account, origin, totalValue, nb);
  }

 /**
   * Test of  CreditBundle(...) method, of class CreditBundle.
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
      CreditBundle bundle = new CreditBundle(account, origin, 1.23456, 1);
      assertEquals("Bad origin", origin, bundle.getOrigin());
      assertTrue("Bad unit value", 1.2346 == bundle.getUnitValue().getValue());
      assertEquals("Bad initial credit nb", 1, bundle.getInitialNb());
      assertEquals("Bad current credit nb", 1, bundle.getCurrentNb());
      assertTrue("Bad account", account == bundle.getAccount());
      assertTrue("Bad account link", account == bundle.getAccountLink());
    }
    catch(Bid4WinException ex)
    {
      ex.printStackTrace();
      fail("Instanciation should not fail: " + ex.getMessage());
    }
  }
  /**
   * Test of sameInternal(CreditBundle, boolean), of class CreditBundle.
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

    CreditBundle bundle1 = new CreditBundle(account1, origin1, 1.23456, 2);
    CreditBundle bundle2 = new CreditBundle(account2, origin1, 1.23456, 3);
    assertFalse(bundle1.same(bundle2));
    assertFalse(bundle2.same(bundle1));
    assertFalse(bundle1.identical(bundle2));
    assertFalse(bundle2.identical(bundle1));

    bundle2.useInternal(1);
    assertFalse(bundle1.same(bundle2));
    assertFalse(bundle2.same(bundle1));
    assertFalse(bundle1.identical(bundle2));
    assertFalse(bundle2.identical(bundle1));

    account2 = this.getGenerator().createAccount(account1.getId());
    bundle2 = new CreditBundle(account2, origin2, 1.23456, 2);
    assertFalse(bundle1.same(bundle2));
    assertFalse(bundle2.same(bundle1));
    assertFalse(bundle1.identical(bundle2));
    assertFalse(bundle2.identical(bundle1));

    account2 = this.getGenerator().createAccount(account1.getId());
    bundle2 = new CreditBundle(account2, origin1, 1.23456, 2);
    assertTrue(bundle1.same(bundle2));
    assertTrue(bundle2.same(bundle1));
    assertTrue(bundle1.identical(bundle2));
    assertTrue(bundle2.identical(bundle1));

    bundle1.useInternal(1);
    assertFalse(bundle1.same(bundle2));
    assertFalse(bundle2.same(bundle1));
    assertFalse(bundle1.identical(bundle2));
    assertFalse(bundle2.identical(bundle1));

    bundle2.useInternal(1);
    assertTrue(bundle1.same(bundle2));
    assertTrue(bundle2.same(bundle1));
    assertTrue(bundle1.identical(bundle2));
    assertTrue(bundle2.identical(bundle1));

    bundle1.historize();
    assertFalse(bundle1.same(bundle2));
    assertFalse(bundle2.same(bundle1));
    assertFalse(bundle1.identical(bundle2));
    assertFalse(bundle2.identical(bundle1));

    bundle2.historize();
    assertTrue(bundle1.same(bundle2));
    assertTrue(bundle2.same(bundle1));
    assertTrue(bundle1.identical(bundle2));
    assertTrue(bundle2.identical(bundle1));
  }
  /**
   * Test of isLinkedToAccount(), of class CreditBundle.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testIsLinkedToAccount_0args() throws Bid4WinException
  {
    Account account = this.getGenerator().createAccount("123");
    CreditBundle bundle = new CreditBundle(
        account, this.getGenerator().createCreditOrigin(), 1.23456, 1);
    assertTrue("Should be linked to account", bundle.isLinkedToAccount());
    account.useCredit(1);
    assertFalse("Should not be linked to account", bundle.isLinkedToAccount());
  }
  /**
   * Test of unlinkFromAccount(), of class CreditBundle.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testUnlinkFromAccount_0args() throws Bid4WinException
  {
    Account account = this.getGenerator().createAccount("123");
    CreditBundle bundle = new CreditBundle(
        account, this.getGenerator().createCreditOrigin(), 1.23456, 2);
    assertEquals("Bad initial bid right nb", 2, bundle.getInitialNb());
    assertEquals("Bad current bid right nb", 2, bundle.getCurrentNb());
    assertTrue("Bad account", account == bundle.getAccount());
    assertTrue("Bad account link", account == bundle.getAccountLink());
    try
    {
      bundle.unlinkFromAccount();
      fail("Should fail with not empty bundle");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertEquals("Bad initial bid right nb", 2, bundle.getInitialNb());
      assertEquals("Bad current bid right nb", 2, bundle.getCurrentNb());
      assertTrue("Bad account", account == bundle.getAccount());
      assertTrue("Bad account link", account == bundle.getAccountLink());
    }

    account.useCredit();
    try
    {
      bundle.unlinkFromAccount();
      fail("Should fail with not empty bundle");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertEquals("Bad initial bid right nb", 2, bundle.getInitialNb());
      assertEquals("Bad current bid right nb", 1, bundle.getCurrentNb());
      assertTrue("Bad account", account == bundle.getAccount());
      assertTrue("Bad account link", account == bundle.getAccountLink());
    }

    bundle.useInternal(1);
    assertEquals("Bad initial bid right nb", 2, bundle.getInitialNb());
    assertEquals("Bad current bid right nb", 0, bundle.getCurrentNb());
    assertTrue("Bad account", account == bundle.getAccount());
    assertNull("Bad account link", bundle.getAccountLink());

    try
    {
      bundle.unlinkFromAccount();
      fail("Second time unlink should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of useInternal(int), of class CreditBundle.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testUseInternal_int() throws Bid4WinException
  {
    Account account = this.getGenerator().createAccount("123");
    CreditBundle bundle = new CreditBundle(
        account, this.getGenerator().createCreditOrigin(), 1.23456, 5);
    assertEquals("Bad initial bid right nb", 5, bundle.getInitialNb());
    assertEquals("Bad current bid right nb", 5, bundle.getCurrentNb());
    assertTrue("Bad account", account == bundle.getAccount());
    assertTrue("Bad account link", account == bundle.getAccountLink());
    // Utilisation de moins d'un crédit
    try
    {
      bundle.useInternal(0);
      fail("Should fail with no credit to use");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    // Utilisation de moins de crédits que disponible
    bundle.useInternal(2);
    assertEquals("Bad initial bid right nb", 5, bundle.getInitialNb());
    assertEquals("Bad current bid right nb", 3, bundle.getCurrentNb());
    assertTrue("Bad account", account == bundle.getAccount());
    assertTrue("Bad account link", account == bundle.getAccountLink());
    // Utilisation de plus de crédits que disponible
    try
    {
      bundle.useInternal(bundle.getCurrentNb() + 1);
      fail("Should fail with no enought credit");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    // Utilisation de tous le crédits disponibles avec un lot historisé
    bundle.historize();
    bundle.useInternal(bundle.getCurrentNb());
    assertEquals("Bad initial bid right nb", 5, bundle.getInitialNb());
    assertEquals("Bad current bid right nb", 0, bundle.getCurrentNb());
    assertTrue("Bad account", account == bundle.getAccount());
    assertNull("Bad account link", bundle.getAccountLink());
    // Utilisation de crédits non disponibles
    try
    {
      bundle.useInternal(1);
      fail("Should fail with no more credit");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of historize(), of class CreditBundle.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testHistorize_0args() throws Bid4WinException
  {
    Account account = this.getGenerator().createAccount("123");
    CreditBundle bundle = new CreditBundle(
        account, this.getGenerator().createCreditOrigin(), 1.23456, 5);
    assertFalse("Should not be historized", bundle.isHistorized());

    CreditBundleHistory history = bundle.historize();
    assertTrue("Should be historized", bundle.isHistorized());
    assertTrue("Wrong history", history == bundle.getHistory());

    try
    {
      bundle.historize();
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertTrue("Should be historized", bundle.isHistorized());
      assertTrue("Wrong history", history == bundle.getHistory());
    }
  }
  /**
   * Test of defineTotalValue(double), of class CreditBundle.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testDefineTotalValue_double() throws Bid4WinException
  {
    Account account = this.getGenerator().createAccount("123");
    double unitValue = 1.2345;
    CreditBundle bundle = new CreditBundle(
        account, this.getGenerator().createCreditOrigin(), 12.3454, 10);
    assertTrue("Wrong unit value", unitValue == bundle.getUnitValue().getValue());

    unitValue = 0;
    bundle.defineTotalValue(unitValue);
    assertTrue("Wrong unit value", unitValue == bundle.getUnitValue().getValue());

    unitValue = 2.3456;
    bundle.defineTotalValue(unitValue * 10);
    assertTrue("Wrong unit value", unitValue == bundle.getUnitValue().getValue());

    try
    {
      bundle.defineTotalValue(-1);
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertTrue("Wrong unit value", unitValue == bundle.getUnitValue().getValue());
    }

    bundle.historize();
    try
    {
      bundle.defineTotalValue(1);
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertTrue("Wrong unit value", unitValue == bundle.getUnitValue().getValue());
    }
  }
}
