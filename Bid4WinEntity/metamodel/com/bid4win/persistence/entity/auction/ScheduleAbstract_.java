package com.bid4win.persistence.entity.auction;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.persistence.entity.Bid4WinEmbeddable_;

/**
 * Metamodel de la classe ScheduleAbstract<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(ScheduleAbstract.class)
public class ScheduleAbstract_ extends Bid4WinEmbeddable_
{
  /** Définition de la date de démarrage prévue de la vente aux enchères */
  public static volatile SingularAttribute<ScheduleAbstract<?>, Bid4WinDate> startDate;
  /** Définition de la date de clôture prévue de la vente aux enchères */
  public static volatile SingularAttribute<ScheduleAbstract<?>, Bid4WinDate> endDate;
}
