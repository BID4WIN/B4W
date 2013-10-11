package com.bid4win.persistence.entity.connection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.entity.Bid4WinEntityTester;
import com.bid4win.persistence.entity.account.Account;

/**
 * Classe de test d'une ré-initialisation de mot de passe<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
public class SubscriptionTest extends Bid4WinEntityTester
{
  /**
   * Test of Subscription(Account), of class Subscription.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testSubscription_Account()throws Bid4WinException
  {
    Account account = this.getGenerator().createAccount("123");
    try
    {
      Subscription instance = new Subscription(account);
      assertNotNull("Wrong ID", instance.getId());
      assertEquals("Wrong ID", 16, instance.getId().length());
      assertEquals("Bad account ID", account, instance.getAccount());
      assertNull("Bad validation date", instance.getValidationDate());
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Instanciation should not fail: " + ex.getMessage());
    }
    try
    {
      new Subscription(null);
      fail("Instanciation with null account should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }

  /**
   * Test of sameInternal(Subscription, boolean), of class Subscription.
   * @throws Bid4WinException Issue not expected during this test
   * @throws InterruptedException Issue not expected during this test
   */
  @Test
  public void testSameInternal_Subscription_boolean() throws Bid4WinException, InterruptedException
  {
    Account account = this.getGenerator().createAccount("123");
    Subscription subscription1 = new Subscription(account);
    Subscription subscription2 = new Subscription(account);
    assertTrue(subscription1.same(subscription2));
    assertTrue(subscription2.same(subscription1));
    assertFalse(subscription1.identical(subscription2));
    assertFalse(subscription2.identical(subscription1));

    subscription1.defineValidationDate();
    assertFalse(subscription1.same(subscription2));
    assertFalse(subscription2.same(subscription1));
    assertFalse(subscription1.identical(subscription2));
    assertFalse(subscription2.identical(subscription1));
    synchronized(account)
    {
      account.wait(10);
    }
    subscription2.defineValidationDate();
    assertFalse(subscription1.same(subscription2));
    assertFalse(subscription2.same(subscription1));
    assertFalse(subscription1.identical(subscription2));
    assertFalse(subscription2.identical(subscription1));
  }
}
