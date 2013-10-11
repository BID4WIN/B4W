package com.bid4win.persistence.usertype.price;

import com.bid4win.commons.persistence.usertype.core.Bid4WinObjectTypeUserType;
import com.bid4win.persistence.entity.price.Currency;

/**
 * Cette classe d�fini le 'type utilisateur' g�rant les monnaies en base de donn�es<BR>
 * <BR>
 * @author Emeric Fill�tre
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
