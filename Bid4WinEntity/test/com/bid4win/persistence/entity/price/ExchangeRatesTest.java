package com.bid4win.persistence.entity.price;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 * Classe de test d'un jeu de taux de changes<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
public class ExchangeRatesTest
{
  /**
   * Test of ExchangeRates(Bid4WinMap<Currency, Double>), of class ExchangeRates.
   */
  @Test
  public void testExchangeRates_Bid4WinMapOfCurrencyAndDouble()
  {
    Bid4WinMap<Currency, Double> rateMap = new Bid4WinMap<Currency, Double>();
    try
    {
      ExchangeRates rates = new ExchangeRates(rateMap);
      assertEquals("Wrong default rate", new Amount(1), rates.getEmbedded(Currency.DEFAULT));
      assertEquals("Wrong rate set", 1, rates.getEmbeddedSet().size());

      rateMap.put(Currency.UK_POUND, 1.23456);
      rates = new ExchangeRates(rateMap);
      assertEquals("Wrong default rate", new Amount(1), rates.getEmbedded(Currency.DEFAULT));
      assertEquals("Wrong rate set", 2, rates.getEmbeddedSet().size());
      assertTrue("Wrong rate", 1.2346 == rates.getValue(Currency.UK_POUND));

      rateMap.put(Currency.DEFAULT, 1.23);
      rates = new ExchangeRates(rateMap);
      assertEquals("Wrong default rate", new Amount(1), rates.getEmbedded(Currency.DEFAULT));
      assertEquals("Wrong rate set", 2, rates.getEmbeddedSet().size());
      assertTrue("Wrong rate", 1.2346 == rates.getValue(Currency.UK_POUND));
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Instanciation should not fail: " + ex.getMessage());
    }
    try
    {
      rateMap.put(Currency.UK_POUND, -0.000000);
      new ExchangeRates(rateMap);
      fail("Instanciation should fail with zero value");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }

}
