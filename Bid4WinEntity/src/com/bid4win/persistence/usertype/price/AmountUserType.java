package com.bid4win.persistence.usertype.price;

import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.persistence.usertype.Bid4WinEmbeddableWithTypeUserType;
import com.bid4win.persistence.entity.price.Amount;
import com.bid4win.persistence.entity.price.Currency;

/**
 * Cette classe d�fini le 'type utilisateur' g�rant les montants en base de donn�es<BR>
 * <BR><BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class AmountUserType extends Bid4WinEmbeddableWithTypeUserType<Amount, Currency>
{
  /**
   * Constructeur
   */
  public AmountUserType()
  {
    super(Amount.class, Currency.class);
  }

  /**
   * Cette fonction est d�finie afin de construire le montant correspondant aux
   * cha�nes de caract�res en argument associ� � la monnaie donn�e
   * @param currency {@inheritDoc}
   * @param strings {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.usertype.Bid4WinEmbeddableWithTypeUserType#typeFromStrings(com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType, java.lang.String[])
   */
  @Override
  public Amount typeFromStrings(Currency currency, String ... strings) throws Bid4WinException
  {
    UtilObject.checkEquals("strings", strings.length, 1);
    return new Amount(currency, Float.valueOf(strings[0]));
  }
  /**
   * Cette fonction est d�finie afin de r�cup�rer la liste des champs du montant
   * en param�tre autres que sa monnaie sous la forme de cha�nes de caract�res
   * @param amount {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.usertype.Bid4WinEmbeddableWithTypeUserType#stringsFromType(com.bid4win.commons.persistence.entity.Bid4WinEmbeddableWithType)
   */
  @Override
  public Bid4WinList<String> stringsFromType(Amount amount)
  {
    return new Bid4WinList<String>("" + amount.getValue());
  }
}
