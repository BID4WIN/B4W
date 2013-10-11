package com.bid4win.persistence.entity.auction;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Metamodel de la classe Schedule<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(Schedule.class)
public class Schedule_ extends ScheduleAbstract_
{
  /** Définition du compte à rebours initial de fermeture d'une vente aux enchères en secondes */
  public static volatile SingularAttribute<Schedule<?>, Integer> initialCountdown;
  /** Définition du compte à rebours additionnel de fermeture d'une vente aux enchères en secondes */
  public static volatile SingularAttribute<Schedule<?>, Integer> additionalCountdown;

}
