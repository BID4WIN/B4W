package com.bid4win.persistence.entity.price;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.entity.Bid4WinEntityTester;

/**
 * Classe de test d'un montant<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
public class AmountTest extends Bid4WinEntityTester
{
  /**
   * Test of Amount(double), of class Amount.
   */
  @Test
  public void testAmount_double()
  {
    try
    {
      Amount amount = new Amount(0);
      assertTrue("Wrong value", 0.0 == amount.getValue());
      assertEquals("Wrong currency", Currency.DEFAULT, amount.getCurrency());

      amount = new Amount(0.123);
      assertTrue("Wrong value", 0.12 == amount.getValue());
      assertEquals("Wrong currency", Currency.DEFAULT, amount.getCurrency());

      amount = new Amount(123.456);
      assertTrue("Wrong value", 123.46 == amount.getValue());
      assertEquals("Wrong currency", Currency.DEFAULT, amount.getCurrency());
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Instanciation should not fail: " + ex.getMessage());
    }
    try
    {
      new Amount(-0.0000001);
      fail("Instanciation should fail with negative value");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }

  /**
   * Test of Amount(Currency, double), of class Amount.
   */
  @Test
  public void testAmount_Currency_double()
  {
    try
    {
      Amount amount = new Amount(Currency.UK_POUND, 0);
      assertTrue("Wrong value", 0.0 == amount.getValue());
      assertEquals("Wrong currency", Currency.UK_POUND, amount.getCurrency());
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Instanciation should not fail: " + ex.getMessage());
    }
    try
    {
      new Amount(null, 0);
      fail("Instanciation should fail with negative value");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of Amount(double, int), of class Amount.
   */
  @Test
  public void testAmount_double_int()
  {
    try
    {
      Amount amount = new Amount(0, 5);
      assertTrue("Wrong value", 0.0 == amount.getValue());
      assertEquals("Wrong currency", Currency.DEFAULT, amount.getCurrency());

      amount = new Amount(0.123, 5);
      assertTrue("Wrong value", 0.123 == amount.getValue());
      assertEquals("Wrong currency", Currency.DEFAULT, amount.getCurrency());

      amount = new Amount(123.456, 5);
      assertTrue("Wrong value", 123.456 == amount.getValue());
      assertEquals("Wrong currency", Currency.DEFAULT, amount.getCurrency());

      amount = new Amount(123.456789, 5);
      assertTrue("Wrong value", 123.45679 == amount.getValue());
      assertEquals("Wrong currency", Currency.DEFAULT, amount.getCurrency());

      amount = new Amount(123.456789, -1);
      assertTrue("Wrong value", 123 == amount.getValue());
      assertEquals("Wrong currency", Currency.DEFAULT, amount.getCurrency());
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Instanciation should not fail: " + ex.getMessage());
    }
  }
  /**
   * Test of add(Amount), of class Amount.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testAdd_Amount() throws Bid4WinException
  {
    Amount amount1 = new Amount(Currency.UK_POUND, 123.456, 3);
    Amount amount2 = null;
    Amount result = amount1;
    assertEquals("Wrong result", result, amount1.add(amount2));

    amount2 = new Amount(Currency.UK_POUND, 1234.5678, 4);
    result = new Amount(Currency.UK_POUND, 1358.0238, 4);
    assertEquals("Wrong result", result, amount1.add(amount2));
    assertEquals("Wrong result", result, amount2.add(amount1));
  }
}
