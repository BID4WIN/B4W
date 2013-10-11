package com.bid4win.persistence.entity.account.credit;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultipleAutoID_;
import com.bid4win.persistence.entity.price.Amount;

/**
 * Metamodel de la classe CreditBundleAbstract<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(CreditBundleAbstract.class)
public abstract class CreditBundleAbstract_ extends AccountBasedEntityMultipleAutoID_
{
  /** Définition de la provenance des crédits du lot */
  public static volatile SingularAttribute<CreditBundleAbstract<?>, CreditOrigin> origin;
  /** Définition de la valeur réelle unitaire des crédits du lot */
  public static volatile SingularAttribute<CreditBundleAbstract<?>, Amount> unitValue;
  /** Définition du nombre de crédits du lot */
  public static volatile SingularAttribute<CreditBundleAbstract<?>, Integer> nb;
  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
