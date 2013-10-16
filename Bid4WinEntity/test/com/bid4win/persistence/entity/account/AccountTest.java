package com.bid4win.persistence.entity.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.account.AccountAbstractTester;
import com.bid4win.commons.persistence.entity.account.security.Credential;
import com.bid4win.commons.persistence.entity.contact.Email;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.entity.EntityGenerator;
import com.bid4win.persistence.entity.account.credit.CreditBundle;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementNormal;
import com.bid4win.persistence.entity.account.credit.collection.CreditMap;
import com.bid4win.persistence.entity.account.preference.PreferenceBundle;
import com.bid4win.persistence.entity.account.preference.PreferenceRoot;

/**
 * Classe de test d'un compte utilisateur<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
public class AccountTest extends AccountAbstractTester<Account, EntityGenerator>
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
  protected Account createAccount(Credential credential, Email email) throws ModelArgumentException
  {
    return new Account(credential, email);
  }

  /**
   * Test of Account(...) method, of class Account.
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
    User user = this.getGenerator().createUser();
    PreferenceBundle bundle = this.getGenerator().createPreferenceBundle();
    try
    {
      Account account = new Account(credential, email, user, bundle);
      assertEquals("Bad credential", credential, account.getCredential());
      assertEquals("Bad email", email, account.getEmail());
      assertEquals("Bad user", user, account.getUser());
      assertEquals("Bad preference bundle", bundle, account.getPreferenceBundle());

      account = new Account(credential, email, null, (PreferenceBundle)null);
      assertEquals("Bad credential", credential, account.getCredential());
      assertEquals("Bad email", email, account.getEmail());
      assertNull("Bad user", account.getUser());
      assertEquals("Bad preference bundle", new PreferenceBundle(), account.getPreferenceBundle());
    }
    catch(ModelArgumentException ex)
    {
      ex.printStackTrace();
      fail("Instanciation should not fail: " + ex.getMessage());
    }

    try
    {
      Account account = new Account(credential, email, user, bundle.exportPreferences());
      assertEquals("Bad credential", credential, account.getCredential());
      assertEquals("Bad email", email, account.getEmail());
      assertEquals("Bad user", user, account.getUser());
      assertEquals("Bad preference bundle", bundle, account.getPreferenceBundle());

      account = new Account(credential, email, null, (PreferenceRoot)null);
      assertEquals("Bad credential", credential, account.getCredential());
      assertEquals("Bad email", email, account.getEmail());
      assertNull("Bad user", account.getUser());
      assertEquals("Bad preference bundle", new PreferenceBundle(), account.getPreferenceBundle());
    }
    catch(ModelArgumentException ex)
    {
      ex.printStackTrace();
      fail("Instanciation should not fail: " + ex.getMessage());
    }
  }

  /**
   * Test of sameInternal(Account, boolean), of class Account.
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.account.AccountAbstractTester#testSameInternal_CLASS_boolean()
   */
  @Override
  @Test
  public void testSameInternal_CLASS_boolean() throws Bid4WinException
  {
    super.testSameInternal_CLASS_boolean();

    Account account1 = new Account(this.getGenerator().createCredential(0),
                                   this.getGenerator().createEmail(0),
                                   this.getGenerator().createUser(0),
                                   this.getGenerator().createPreferenceBundle(0));
    Account account2 = new Account(this.getGenerator().createCredential(0),
                                   this.getGenerator().createEmail(0),
                                   this.getGenerator().createUser(0),
                                   this.getGenerator().createPreferenceBundle(0));

    account2.defineUser(this.getGenerator().createUser(1));
    this.checkNotSame(account1, account2);
    this.checkNotSame(account2, account1);
    this.checkNotIdentical(account1, account2);
    this.checkNotIdentical(account2, account1);

    account2.defineUser(account1.getUser());
    account2.getPreferenceBundle().importPreferences(
        this.getGenerator().createPreferenceBundle(1).exportPreferences());
    this.checkNotSame(account1, account2);
    this.checkNotSame(account2, account1);
    this.checkNotIdentical(account1, account2);
    this.checkNotIdentical(account2, account1);

    account2.getPreferenceBundle().importPreferences(
        account1.getPreferenceBundle().exportPreferences());
    this.checkSame(account1, account2);
    this.checkSame(account2, account1);
    this.checkNotIdentical(account1, account2);
    this.checkNotIdentical(account2, account1);

    this.getGenerator().createCreditBundle(account1, 1, 2);
    this.checkNotSameRelationNone(account1, account2);
    this.checkNotSameRelationNone(account2, account1);
    this.checkNotIdenticalRelationNone(account1, account2);
    this.checkNotIdenticalRelationNone(account2, account1);

    this.getGenerator().createCreditBundle(account2, 2, 2);
    account1.useCredit();
    this.checkNotSameRelationNone(account1, account2);
    this.checkNotSameRelationNone(account2, account1);
    this.checkNotIdenticalRelationNone(account1, account2);
    this.checkNotIdenticalRelationNone(account2, account1);

    account2.useCredit();
    this.checkSameRelationNone(account1, account2);
    this.checkSameRelationNone(account2, account1);
    this.checkNotSame(account1, account2);
    this.checkNotSame(account2, account1);
    this.checkNotIdentical(account1, account2);
    this.checkNotIdentical(account2, account1);
  }

  /**
   * Test of useCredit(...) method, of class Account.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testUseCredit_etc() throws Bid4WinException
  {
    Credential credential = this.getGenerator().createCredential();
    Email email = this.getGenerator().createEmail();
    // Le compte utilisateur n'a pas de crédit
    Account account = new Account(credential, email, null, (PreferenceBundle)null);
    assertFalse("Bad result", account.hasCredit());
    assertEquals("Bad credit nb", 0, account.getCreditNb());
    assertEquals("Bad used credit nb", 0, account.getUsedCreditNb());
    // Le compte utilisateur a un crédit
    CreditBundle bundle1 = new CreditBundle(
        account, this.getGenerator().createCreditOrigin(), 1.23, 1);
    assertTrue("Bad result", account.hasCredit());
    assertEquals("Bad credit nb", 1, account.getCreditNb());
    assertEquals("Bad used credit nb", 0, account.getUsedCreditNb());
    // Utilise le crédit du compte utilisateur
    CreditBundle bundle = account.useCredit();
    assertFalse("Bad result", account.hasCredit());
    assertEquals("Bad credit nb", 0, account.getCreditNb());
    assertEquals("Bad used credit nb", 1, account.getUsedCreditNb());
    assertTrue("Bad bundle", bundle1 == bundle);
    assertFalse("Should not be linked", bundle1.isLinkedToAccount());
    assertEquals("Bad credit nb", 0, bundle1.getCurrentNb());

    CreditBundle bundle2 = new CreditBundle (
        account, this.getGenerator().createCreditOrigin(), 0, 2);
    CreditBundle bundle3 = new CreditBundle(
        account, this.getGenerator().createCreditOrigin(), 0, 3);

    // Utilise un crédit du compte utilisateur
    CreditMap result = account.useCredit(1);
    assertTrue("Bad result", account.hasCredit());
    assertEquals("Bad credit nb", 4, account.getCreditNb());
    assertEquals("Bad used credit nb", 2, account.getUsedCreditNb());
    assertTrue("Should be linked", bundle2.isLinkedToAccount());
    assertEquals("Bad credit nb", 1, bundle2.getCurrentNb());
    assertTrue("Should be linked", bundle3.isLinkedToAccount());
    assertEquals("Bad credit nb", 3, bundle3.getCurrentNb());
    assertEquals("Bad result", 1, result.size());
    assertEquals("Bad credit nb", result.get(bundle2).intValue(), 1);
    // Utilise moins d'un crédit
    result = account.useCredit(-1);
    assertTrue("Bad result", account.hasCredit());
    assertEquals("Bad credit nb", 4, account.getCreditNb());
    assertEquals("Bad used credit nb", 2, account.getUsedCreditNb());
    assertTrue("Should be linked", bundle2.isLinkedToAccount());
    assertEquals("Bad credit nb", 1, bundle2.getCurrentNb());
    assertTrue("Should be linked", bundle3.isLinkedToAccount());
    assertEquals("Bad credit nb", 3, bundle3.getCurrentNb());
    assertEquals("Bad result", 0, result.size());
    // Utilise plus de crédits que disponible
    try
    {
      account.useCredit(account.getCreditNb() + 1);
      fail("Should fail with not enought credits to use");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    // Utilise des crédits du compte utilisateur
    result = account.useCredit(3);
    assertTrue("Bad result", account.hasCredit());
    assertEquals("Bad credit nb", 1, account.getCreditNb());
    assertEquals("Bad used credit nb", 5, account.getUsedCreditNb());
    assertFalse("Should not be linked", bundle2.isLinkedToAccount());
    assertEquals("Bad credit nb", 0, bundle2.getCurrentNb());
    assertTrue("Should be linked", bundle3.isLinkedToAccount());
    assertEquals("Bad credit nb", 1, bundle3.getCurrentNb());
    assertEquals("Bad result", 2, result.size());
    assertEquals("Bad credit nb", result.get(bundle2).intValue(), 1);
    assertEquals("Bad credit nb", result.get(bundle3).intValue(), 2);
  }

  /**
   *
   * TODO A COMMENTER
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.account.AccountAbstractTester#testCheckProtection()
   */
  @Override
  @Test
  public void testCheckProtection() throws Bid4WinException
  {
    super.testCheckProtection();

    String idAccount = this.startProtection();
    Account account = this.createAccount(this.getGenerator().createCredential(),
                                         this.getGenerator().createEmail());
    User user = account.getUser();
    String idBundle = this.startProtection();
    CreditBundle bundle = new CreditBundle(account, this.getGenerator().createCreditOrigin(), 1, 10);
    this.stopProtection();
    this.stopProtection();

    try
    {
      account.defineUser(this.getGenerator().createUser(1));
      fail("Should fail with protected object");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      new CreditBundle(account, this.getGenerator().createCreditOrigin(), 1, 1);
      fail("Should fail with protected object");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      new CreditInvolvementNormal(account, this.getGenerator().createNormalAuction());
      fail("Should fail with protected object");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      account.useCredit(bundle.getCurrentNb());
      fail("Should fail with protected object");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    this.startProtection(idAccount);
    try
    {
      account.useCredit(bundle.getCurrentNb());
      fail("Should fail with protected object");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    this.stopProtection();
    this.startProtection(idBundle);
    try
    {
      account.useCredit(bundle.getCurrentNb());
      fail("Should fail with protected object");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    this.stopProtection();
    assertEquals("Wrong user", user, account.getUser());
    assertEquals("Wrong credit nb", bundle.getInitialNb(), account.getCreditNb());
    assertEquals("Wrong used credit nb", 0, account.getUsedCreditNb());
    assertEquals("Wrong credit bundle list", 1, account.getRelationList(Account_Relations.RELATION_CREDIT_BUNDLE_LIST).size());
    assertEquals("Wrong credit bundle", bundle.getInitialNb(),
                 ((CreditBundle)account.getRelationList(Account_Relations.RELATION_CREDIT_BUNDLE_LIST).get(0)).getCurrentNb());
    assertEquals("Wrong credit involevment normal map", 0, account.getRelationMap(Account_Relations.RELATION_INVOLVEMENT_NORMAL_MAP).size());
  }
}
