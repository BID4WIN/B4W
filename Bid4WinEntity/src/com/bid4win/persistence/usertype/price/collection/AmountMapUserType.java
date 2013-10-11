package com.bid4win.persistence.usertype.price.collection;

import com.bid4win.commons.persistence.usertype.collection.Bid4WinEmbeddableWithTypeMapUserType;
import com.bid4win.persistence.entity.price.Amount;
import com.bid4win.persistence.entity.price.Currency;
import com.bid4win.persistence.usertype.price.AmountUserType;

/**
 * Cette classe défini le 'type utilisateur' gérant les maps de montants en base
 * de données<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class AmountMapUserType extends Bid4WinEmbeddableWithTypeMapUserType<Amount, Currency>
{
  /**
   * Constructeur
   */
  public AmountMapUserType()
  {
    super(new AmountUserType());
  }
}
