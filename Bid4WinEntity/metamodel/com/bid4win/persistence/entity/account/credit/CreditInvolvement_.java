package com.bid4win.persistence.entity.account.credit;

import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultipleAutoID_;

/**
 * Metamodel de la classe CreditInvolvement<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(CreditInvolvement.class)
public class CreditInvolvement_ extends AccountBasedEntityMultipleAutoID_
{
  /** Définition de la map des utilisation de crédits */
  public static volatile MapAttribute<CreditInvolvement<?, ?, ?, ?>, String, CreditUsageAbstract<?, ?, ?>> usageMapInternal;
  /** Définition du nombre de crédits utilisés */
  public static volatile SingularAttribute<CreditInvolvement<?, ?, ?, ?>, Integer> usedNb;

  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    /** Défini la profondeur du noeud de relation existant avec les utilisation de crédits */
    CreditInvolvement_Relations.NODE_USAGE_MAP.addNode(CreditUsageAbstract_Relations.NODE_INVOLVEMENT);
    CreditInvolvement_Relations.NODE_USAGE_MAP.addNode(CreditUsageAbstract_Relations.NODE_BUNDLE);
    Bid4WinEntity_.stopProtection();
  }
}
