package com.bid4win.persistence.usertype.price;

import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.persistence.usertype.Bid4WinEmbeddableWithTypeUserType;
import com.bid4win.persistence.entity.price.Amount;
import com.bid4win.persistence.entity.price.Currency;

/**
 * Cette classe défini le 'type utilisateur' gérant les montants en base de données<BR>
 * <BR><BR>
 * <BR>
 * @author Emeric Fillâtre
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
   * Cette fonction est définie afin de construire le montant correspondant aux
   * chaînes de caractères en argument associé à la monnaie donnée
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
   * Cette fonction est définie afin de récupérer la liste des champs du montant
   * en paramètre autres que sa monnaie sous la forme de chaînes de caractères
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
