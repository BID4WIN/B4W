package com.bid4win.persistence.usertype.price;

import com.bid4win.commons.persistence.usertype.core.Bid4WinObjectTypeUserType;
import com.bid4win.persistence.entity.price.Currency;

/**
 * Cette classe défini le 'type utilisateur' gérant les monnaies en base de données<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class CurrencyUserType extends Bid4WinObjectTypeUserType<Currency>
{
  /**
   * Constructeur
   */
  public CurrencyUserType()
  {
    super(Currency.class);
  }
}
