package com.bid4win.persistence.entity.account.credit;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntityAutoID_;
import com.bid4win.commons.persistence.entity.Bid4WinEntity_;

/**
 * Metamodel de la classe CreditUsageAbstract<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(CreditUsageAbstract.class)
public class CreditUsageAbstract_ extends Bid4WinEntityAutoID_
{
  /** Définition de l'identifiant du lot de provenance des crédits utilisés */
  public static volatile SingularAttribute<CreditUsageAbstract<?, ?, ?>, Long> bundleId;
  /** Définition du lot de provenance des crédits utilisés */
  public static volatile SingularAttribute<CreditUsageAbstract<?, ?, ?>, CreditBundleAbstract<?>> bundle;
  /** Définition de l'implication globale des crédits utilisés */
  public static volatile SingularAttribute<CreditUsageAbstract<?, ?, ?>, CreditInvolvement<?, ?, ?, ?>> involvement;
  /** Définition du nombre de crédits du lot */
  public static volatile SingularAttribute<CreditUsageAbstract<?, ?, ?>, Integer> usedNb;
  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
