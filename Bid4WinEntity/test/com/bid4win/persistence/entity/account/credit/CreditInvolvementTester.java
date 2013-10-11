package com.bid4win.persistence.entity.account.credit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntityTester;
import com.bid4win.persistence.entity.EntityGenerator;
import com.bid4win.persistence.entity.account.Account;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <INVOLVEMENT> Doit définir la classe réellement instanciée<BR>
 * @param <USAGE> Définition des utilisations de crédits en provenance de différents
 * lots<BR>
 * @param <BUNDLE> Définition des lots de provenance des utilisations de crédits<BR>
 * @param <HISTORY> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class CreditInvolvementTester<INVOLVEMENT extends CreditInvolvement<INVOLVEMENT, USAGE, BUNDLE, HISTORY>,
                                              USAGE extends CreditUsageAbstract<USAGE, BUNDLE, INVOLVEMENT>,
                                              BUNDLE extends CreditBundleAbstract<BUNDLE>,
                                              HISTORY extends CreditInvolvement<HISTORY, ?, CreditBundleHistory, ?>>
       extends AccountBasedEntityTester<INVOLVEMENT, Account, EntityGenerator>
{
  /**
   *
   * TODO A COMMENTER
   * @param account TODO A COMMENTER
   * @param origin TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  protected abstract BUNDLE createBundle(Account account, CreditOrigin origin) throws UserException;
  /**
   *
   * TODO A COMMENTER
   * @param bundle TODO A COMMENTER
   * @param involvement TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  protected abstract USAGE createUsage(BUNDLE bundle, INVOLVEMENT involvement) throws UserException;

  /**
   * Test of getUsage(BUNDLE) method, of class CreditInvolvement.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testGetUsage_BUNDLE() throws Bid4WinException
  {
    Account account = this.getGenerator().createAccount("123");
    INVOLVEMENT involvement = this.createEntity(account);
    CreditOrigin origin1 = new CreditOrigin(Origin.PURCHASE, "111111111111");
    CreditOrigin origin2 = new CreditOrigin(Origin.SPONSORSHIP, origin1.getReference());
    try
    {
      involvement.getUsage(null);
      fail("Should fail with null origin");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }

    BUNDLE bundle1 = this.createBundle(account, origin1);
    BUNDLE bundle2 = this.createBundle(account, origin2);

    assertNull("Wrong usage", involvement.getUsage(bundle1));
    assertNull("Wrong usage", involvement.getUsage(bundle2));

    USAGE usage1 = this.createUsage(bundle1, involvement);
    assertTrue("Wrong usage", usage1 == involvement.getUsage(bundle1));
    assertNull("Wrong usage", involvement.getUsage(bundle2));

    USAGE usage2 = this.createUsage(bundle2, involvement);
    assertTrue("Wrong usage", usage1 == involvement.getUsage(bundle1));
    assertTrue("Wrong usage", usage2 == involvement.getUsage(bundle2));
  }

  /**
   * Test of addUsage(BUNDLE, int) method, of class CreditInvolvement.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testAddUsage_BUNDLE_int() throws Bid4WinException
  {
    Account account = this.getGenerator().createAccount("123");
    INVOLVEMENT involvement = this.createEntity(account);
    CreditOrigin origin1 = new CreditOrigin(Origin.PURCHASE, "111111111111");
    CreditOrigin origin2 = new CreditOrigin(Origin.SPONSORSHIP, origin1.getReference());
    BUNDLE bundle1 = this.createBundle(account, origin1);
    BUNDLE bundle2 = this.createBundle(account, origin2);

    USAGE usage1 = involvement.addUsage(bundle1, 1);
    assertEquals("Wrong nb", 1, involvement.getUsedNb());
    assertEquals("Wrong usage nb", 1, usage1.getUsedNb());
    assertTrue("Wrong bundle", bundle1 == usage1.getBundle());

    USAGE usage2 = involvement.addUsage(bundle2, 2);
    assertEquals("Wrong nb", 3, involvement.getUsedNb());
    assertEquals("Wrong usage nb", 2, usage2.getUsedNb());
    assertTrue("Wrong bundle", bundle2 == usage2.getBundle());

    USAGE usage3 = involvement.addUsage(bundle1, 3);
    assertEquals("Wrong nb", 6, involvement.getUsedNb());
    assertEquals("Wrong usage nb", 4, usage1.getUsedNb());
    assertTrue("Wrong bundle", bundle1 == usage3.getBundle());
    assertTrue("Wrong usage", usage1 == usage3);


    BUNDLE bundle3 = this.createBundle(this.getGenerator().createAccount("124"), origin1);
    try
    {
      involvement.addUsage(bundle3, 3);
      fail("Should fail with bundle comming from different account");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertEquals("Wrong nb", 6, involvement.getUsedNb());
      assertEquals("Wrong usage nb", 4, usage1.getUsedNb());
      assertTrue("Wrong bundle", bundle1 == usage3.getBundle());
      assertTrue("Wrong usage", usage1 == usage3);
    }
  }
  /**
   * Test of historize() method, of class CreditInvolvement.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testHistorize_0args() throws Bid4WinException
  {
    Account account = this.getGenerator().createAccount("123");

    INVOLVEMENT involvement = this.createEntity(account);
    assertFalse("Should not be historized", involvement.isHistorized());

    HISTORY history = involvement.historize();
    assertTrue("Should be historized", involvement.isHistorized());
    assertFalse("Should not be historized", history.isHistorized());
    assertTrue("Wrong account", account == history.getAccount());

    try
    {
      involvement.historize();
      fail("Should fail with historized involvement");
    }
    catch(UserException ex)
    {
      System.out.println(ex);
    }
  }
}
