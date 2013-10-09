package com.bid4win.commons.core;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Cette classe �tend la classe GregorianCalendar de java afin d'y rajouter des
 * fonctions utiles et manquantes et surtout afin de produire la classe Date
 * elle aussi �tendue.<BR>
 * Automatiquement et pour �viter tout oubli lors de l'utilisation dans une application,
 * le fuseau horaire par d�faut sera d�fini � GMT lors de l'utilisation de cette
 * classe. Si un autre fuseau horaire par d�faut doit �tre utilis� � la place, il
 * faudra bien penser � le red�finir.<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinCalendar extends GregorianCalendar
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = -2702247326302588775L;
  /** Fuseau horaire de r�f�rence */
  private static final TimeZone GMT_TIME_ZONE = TimeZone.getTimeZone("GMT");
  /**
   * D�fini le fuseau horaire de r�f�rence comme celui � utiliser par d�faut
   */
  public static void setDefaultTimeZoneToGMT()
  {
     TimeZone.setDefault(GMT_TIME_ZONE);
  }
  /**
   * Lors de l'utilisation de cette classe dans un projet, le fuseau horaire par
   * d�faut sera d�fini � GMT
   */
  static
  {
    Bid4WinCalendar.setDefaultTimeZoneToGMT();
  }
  /**
   * Getter du fuseau horaire par d�faut
   * @return Le fuseau horaire par d�faut
   */
  public static TimeZone getDefaultTimeZone()
  {
    return TimeZone.getDefault();
  }

  /**
   * Constructeur � partir de la date courante
   */
  public Bid4WinCalendar()
  {
    super();
    this.setFirstDayOfWeek(Calendar.MONDAY);
  }
  /**
   * Constructeur de copie � partir de l'objet calendrier de base
   * @param calendar Calendrier � partir duquel cr�er le nouveau par copie
   */
  public Bid4WinCalendar(Calendar calendar)
  {
    this();
    this.setTime(calendar.getTime());
  }

  /**
   * Constructeur � partir de l'objet Date
   * @param date Date � partir de laquelle construire le calendrier
   */
  public Bid4WinCalendar(Date date)
  {
    this();
    this.setTime(date);
  }

  /**
   * Permet de r�cup�rer la date contenue dans le calendrier directement cast�e
   * Attention, du fait que la fonction getTime soit finale dans la classe m�re,
   * celle ci n'a pas pu �tre surcharg�e pour cr�er les bons objets date ou la
   * d�pr�cier
   * @return La date actuelle du calendrier
   */
  public Bid4WinDate getDate()
  {
    return new Bid4WinDate(super.getTime());
  }
}
