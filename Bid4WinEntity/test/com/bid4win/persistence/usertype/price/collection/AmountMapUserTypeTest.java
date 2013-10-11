package com.bid4win.persistence.usertype.price.collection;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.persistence.usertype.collection.Bid4WinMapUserTypeTester;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.entity.price.Amount;
import com.bid4win.persistence.entity.price.Currency;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
public class AmountMapUserTypeTest
       extends Bid4WinMapUserTypeTester<Currency, Amount, AmountMapUserType>
{
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.usertype.Bid4WinStringUserTypeTester#getUserType()
   */
  @Override
  protected AmountMapUserType getUserType()
  {
    return new AmountMapUserType();
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.usertype.Bid4WinStringUserTypeTester#getType()
   */
  @Override
  protected Bid4WinMap<Currency, Amount> getType() throws Bid4WinException
  {
    Bid4WinMap<Currency, Amount> map = new Bid4WinMap<Currency, Amount>();
    int i = 0;
    map.put(Currency.UK_POUND, new Amount(Currency.UK_POUND, 123.456f + i++));
    map.put(Currency.US_DOLLAR, new Amount(Currency.US_DOLLAR, 123.456f + i++));
    map.put(Currency.EURO, new Amount(Currency.EURO, 123.456f + i++));
    return map;
  }
}
