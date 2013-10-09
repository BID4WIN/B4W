package com.bid4win.commons.core;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Cette classe étend la classe GregorianCalendar de java afin d'y rajouter des
 * fonctions utiles et manquantes et surtout afin de produire la classe Date
 * elle aussi étendue.<BR>
 * Automatiquement et pour éviter tout oubli lors de l'utilisation dans une application,
 * le fuseau horaire par défaut sera défini à GMT lors de l'utilisation de cette
 * classe. Si un autre fuseau horaire par défaut doit être utilisé à la place, il
 * faudra bien penser à le redéfinir.<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinCalendar extends GregorianCalendar
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = -2702247326302588775L;
  /** Fuseau horaire de référence */
  private static final TimeZone GMT_TIME_ZONE = TimeZone.getTimeZone("GMT");
  /**
   * Défini le fuseau horaire de référence comme celui à utiliser par défaut
   */
  public static void setDefaultTimeZoneToGMT()
  {
     TimeZone.setDefault(GMT_TIME_ZONE);
  }
  /**
   * Lors de l'utilisation de cette classe dans un projet, le fuseau horaire par
   * défaut sera défini à GMT
   */
  static
  {
    Bid4WinCalendar.setDefaultTimeZoneToGMT();
  }
  /**
   * Getter du fuseau horaire par défaut
   * @return Le fuseau horaire par défaut
   */
  public static TimeZone getDefaultTimeZone()
  {
    return TimeZone.getDefault();
  }

  /**
   * Constructeur à partir de la date courante
   */
  public Bid4WinCalendar()
  {
    super();
    this.setFirstDayOfWeek(Calendar.MONDAY);
  }
  /**
   * Constructeur de copie à partir de l'objet calendrier de base
   * @param calendar Calendrier à partir duquel créer le nouveau par copie
   */
  public Bid4WinCalendar(Calendar calendar)
  {
    this();
    this.setTime(calendar.getTime());
  }

  /**
   * Constructeur à partir de l'objet Date
   * @param date Date à partir de laquelle construire le calendrier
   */
  public Bid4WinCalendar(Date date)
  {
    this();
    this.setTime(date);
  }

  /**
   * Permet de récupérer la date contenue dans le calendrier directement castée
   * Attention, du fait que la fonction getTime soit finale dans la classe mère,
   * celle ci n'a pas pu être surchargée pour créer les bons objets date ou la
   * déprécier
   * @return La date actuelle du calendrier
   */
  public Bid4WinDate getDate()
  {
    return new Bid4WinDate(super.getTime());
  }
}
