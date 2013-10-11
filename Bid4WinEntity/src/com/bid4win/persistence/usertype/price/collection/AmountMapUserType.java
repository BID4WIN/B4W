package com.bid4win.persistence.usertype.price.collection;

import com.bid4win.commons.persistence.usertype.collection.Bid4WinEmbeddableWithTypeMapUserType;
import com.bid4win.persistence.entity.price.Amount;
import com.bid4win.persistence.entity.price.Currency;
import com.bid4win.persistence.usertype.price.AmountUserType;

/**
 * Cette classe d�fini le 'type utilisateur' g�rant les maps de montants en base
 * de donn�es<BR>
 * <BR>
 * @author Emeric Fill�tre
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
