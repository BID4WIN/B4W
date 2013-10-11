package com.bid4win.service.price;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.PropertyRef;
import com.bid4win.persistence.entity.price.Currency;
import com.bid4win.persistence.entity.price.ExchangeRates;
import com.bid4win.persistence.entity.property.Property;
import com.bid4win.persistence.entity.property.PropertyRoot;
import com.bid4win.service.property.PropertyBasedService_;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("CurrencyService")
@Scope("singleton")
public class CurrencyService extends PropertyBasedService_
{
  /** TODO A COMMENTER */
  private ExchangeRates exchangeRates = null;

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  public Bid4WinSet<Currency> getCurrencySet() throws PersistenceException
  {
    this.checkForPropertyRootChange();
    return this.getExchangeRatesProperty().getTypeSet();
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  public ExchangeRates getExchangeRates() throws PersistenceException
  {
    this.checkForPropertyRootChange();
    return this.getExchangeRatesProperty();
  }

  /**
   *
   * TODO A COMMENTER
   * @param propertyRoot {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.service.property.PropertyBasedService_#applyPropertyRootChange(com.bid4win.persistence.entity.property.PropertyRoot)
   */
  @Override
  protected void applyPropertyRootChange(PropertyRoot propertyRoot) throws UserException
  {
    Property currencies = propertyRoot.getProperty(PropertyRef.SERVER_DEFINITION_CURRENCIES.getCode());
    Bid4WinMap<Currency, Double> rateMap = null;
    if(currencies == null)
    {
      rateMap = new Bid4WinMap<Currency, Double>(0);
    }
    else
    {
      rateMap = new Bid4WinMap<Currency, Double>(currencies.getPropertyNb());
      for(Property property : currencies.getPropertySet())
      {
        try
        {
          rateMap.put(Currency.getCurrency(property.getKey()), property.getDoubleValue());
        }
        catch(Bid4WinException ex)
        {
          ex.printStackTrace();
        }
      }
    }
    this.setExchangeRatesProperty(new ExchangeRates(rateMap));
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  private ExchangeRates getExchangeRatesProperty()
  {
    return this.exchangeRates;
  }
  /**
   *
   * TODO A COMMENTER
   * @param exchangeRates TODO A COMMENTER
   */
  private void setExchangeRatesProperty(ExchangeRates exchangeRates)
  {
    this.exchangeRates = exchangeRates;
  }
}
