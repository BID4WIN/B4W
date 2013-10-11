package com.bid4win.persistence.entity.account.credit;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntityAutoID_;
import com.bid4win.commons.persistence.entity.Bid4WinEntity_;

/**
 * Metamodel de la classe CreditUsageAbstract<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(CreditUsageAbstract.class)
public class CreditUsageAbstract_ extends Bid4WinEntityAutoID_
{
  /** D�finition de l'identifiant du lot de provenance des cr�dits utilis�s */
  public static volatile SingularAttribute<CreditUsageAbstract<?, ?, ?>, Long> bundleId;
  /** D�finition du lot de provenance des cr�dits utilis�s */
  public static volatile SingularAttribute<CreditUsageAbstract<?, ?, ?>, CreditBundleAbstract<?>> bundle;
  /** D�finition de l'implication globale des cr�dits utilis�s */
  public static volatile SingularAttribute<CreditUsageAbstract<?, ?, ?>, CreditInvolvement<?, ?, ?, ?>> involvement;
  /** D�finition du nombre de cr�dits du lot */
  public static volatile SingularAttribute<CreditUsageAbstract<?, ?, ?>, Integer> usedNb;
  // D�finition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
