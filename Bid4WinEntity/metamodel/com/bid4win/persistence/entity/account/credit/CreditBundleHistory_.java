package com.bid4win.persistence.entity.account.credit;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;

/**
 * Metamodel de la classe CreditBundle<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(CreditBundleHistory.class)
public abstract class CreditBundleHistory_ extends CreditBundleAbstract_
{
  /** Définition de la provenance des crédits du lot */
  // Suite à un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
  // en compte si défini dans le metamodel de la super class : bug HHH-5024
  // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
  public static volatile SingularAttribute<CreditBundleHistory, CreditOrigin> origin;

  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
