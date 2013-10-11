package com.bid4win.persistence.entity.account.credit;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEmbeddable_;
import com.bid4win.persistence.entity.price.Amount;

/**
 * Metamodel de la classe CreditOrigin<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(CreditOrigin.class)
public class CreditOrigin_ extends Bid4WinEmbeddable_
{
  /** Définition du type de provenance des crédits */
  public static volatile SingularAttribute<CreditOrigin, Origin> type;
  /** Définition de la référence de provenance des crédits */
  public static volatile SingularAttribute<CreditOrigin, String> reference;
  /** Définition de la valeur réelle totale des crédits */
  public static volatile SingularAttribute<CreditOrigin, Amount> totalValue;
}
