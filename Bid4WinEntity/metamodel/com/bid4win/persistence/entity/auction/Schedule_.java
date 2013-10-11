package com.bid4win.persistence.entity.auction;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Metamodel de la classe Schedule<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(Schedule.class)
public class Schedule_ extends ScheduleAbstract_
{
  /** D�finition du compte � rebours initial de fermeture d'une vente aux ench�res en secondes */
  public static volatile SingularAttribute<Schedule<?>, Integer> initialCountdown;
  /** D�finition du compte � rebours additionnel de fermeture d'une vente aux ench�res en secondes */
  public static volatile SingularAttribute<Schedule<?>, Integer> additionalCountdown;

}
