package com.bid4win.persistence.entity.account.credit;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultipleAutoID_;
import com.bid4win.persistence.entity.price.Amount;

/**
 * Metamodel de la classe CreditBundleAbstract<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(CreditBundleAbstract.class)
public abstract class CreditBundleAbstract_ extends AccountBasedEntityMultipleAutoID_
{
  /** D�finition de la provenance des cr�dits du lot */
  public static volatile SingularAttribute<CreditBundleAbstract<?>, CreditOrigin> origin;
  /** D�finition de la valeur r�elle unitaire des cr�dits du lot */
  public static volatile SingularAttribute<CreditBundleAbstract<?>, Amount> unitValue;
  /** D�finition du nombre de cr�dits du lot */
  public static volatile SingularAttribute<CreditBundleAbstract<?>, Integer> nb;
  // D�finition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
