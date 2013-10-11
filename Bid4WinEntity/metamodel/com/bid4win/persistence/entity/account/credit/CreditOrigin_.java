package com.bid4win.persistence.entity.account.credit;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEmbeddable_;
import com.bid4win.persistence.entity.price.Amount;

/**
 * Metamodel de la classe CreditOrigin<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(CreditOrigin.class)
public class CreditOrigin_ extends Bid4WinEmbeddable_
{
  /** D�finition du type de provenance des cr�dits */
  public static volatile SingularAttribute<CreditOrigin, Origin> type;
  /** D�finition de la r�f�rence de provenance des cr�dits */
  public static volatile SingularAttribute<CreditOrigin, String> reference;
  /** D�finition de la valeur r�elle totale des cr�dits */
  public static volatile SingularAttribute<CreditOrigin, Amount> totalValue;
}
