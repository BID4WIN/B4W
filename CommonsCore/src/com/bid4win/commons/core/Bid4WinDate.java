package com.bid4win.commons.core;

import java.util.Calendar;
import java.util.Date;

import com.bid4win.commons.core.exception.CommunicationException;

/**
 * Cette classe étend la classe Date de java afin d'y rajouter des fonctions utiles
 * et manquantes.<BR>
 * Automatiquement et pour éviter tout oubli lors de l'utilisation dans une application,
 * le fuseau horaire par défaut sera défini à GMT lors de l'utilisation de cette
 * classe. Si un autre fuseau horaire par défaut doit être utilisé à la place, il
 * faudra bien penser à le redéfinir.<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinDate extends Date
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = 2975690765092854697L;
  /** TODO A COMMENTER */
  public final static Bid4WinDate FINAL_DATE = Bid4WinDate.getFinalDate();
  /**
   * Lors de l'utilisation de cette classe dans un projet, le fuseau horaire par
   * défaut sera défini à GMT
   */
  static
  {
    Bid4WinCalendar.setDefaultTimeZoneToGMT();
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  private static Bid4WinDate getFinalDate()
  {
    try
    {
      return Bid4WinDateFormat.parseDDIMMIYYYY("31/12/2100");
    }
    catch(CommunicationException ex)
    {
      throw new IllegalArgumentException(ex);
    }
  }

  /**
   * Constructeur
   */
  public Bid4WinDate()
  {
    super();
  }
  /**
   * Constructeur utilisant une distance par rapport à une date référence pour
   * créer la date
   * @param timeInMillis Temps en millisecondes par rapport à la date de référence
   */
  public Bid4WinDate(long timeInMillis)
  {
    super(timeInMillis);
  }
  /**
   * Constructeur de copie à partir de l'objet date de base
   * @param date Date à partir de laquelle copier la nouvelle
   */
  public Bid4WinDate(Date date)
  {
    this(date.getTime());
  }

  /**
   * Calcule le jour de la semaine (entier de 1 à 7) avec lundi comme premier jour
   * @return Le jour de la semaine
   */
  public int getFrenchDayOfWeek()
  {
    int weekDay = this.getEnglishDayOfWeek();
    // Le lundi est le premier jour de la semaine
    return (weekDay == 1) ? 7 : weekDay - 1;
  }
  /**
   * Calcule le jour de la semaine (entier de 1 à 7) avec dimanche comme premier
   * jour
   * @return Le jour de la semaine
   */
  public int getEnglishDayOfWeek()
  {
    int dayOfWeek = this.toGregorianCalendar().get(Calendar.DAY_OF_WEEK);
    // Le dimanche est le premier jour de la semaine
    switch (dayOfWeek)
    {
      case Calendar.SUNDAY:
        return 1;
      case Calendar.MONDAY:
        return 2;
      case Calendar.TUESDAY:
        return 3;
      case Calendar.WEDNESDAY:
        return 4;
      case Calendar.THURSDAY:
        return 5;
      case Calendar.FRIDAY:
        return 6;
      default:
        return 7;
    }
  }

  /**
   * Calcule le nombre de jour qui sépare la date en cours de celle en argument
   * en partant de la date en cours. Si la date à comparer est antérieure, le
   * résultat sera donc négatif
   * @param toBeCompared Date entre laquelle et celle en cours il faut rechercher
   * le nombre de jour
   * @return Le nombre de jours séparant la date en cours de celle en argument
   */
  public int getDaysBetween(Bid4WinDate toBeCompared)
  {
    // Récupère les calendriers correspondants aux 2 dates à comparer
    Bid4WinCalendar begin = this.toGregorianCalendar();
    Bid4WinCalendar end = toBeCompared.toGregorianCalendar();
    // Calcul le nombre de jour entre les 2 dates
    int days = (new Long((end.getTimeInMillis() -
                          begin.getTimeInMillis()) / 86400000)).intValue();
    // Décale la date de début du nombre de jour
    begin.add(Calendar.DATE, days);
    // On tombe le même jour, le reste de la division entière n'a pas interféré
    if(begin.get(Calendar.DATE) == end.get(Calendar.DATE))
    {
      return days;
    }
    // Sinon il faut décaler d'un jour dans un sens ou dans l'autre
    else if(this.before(toBeCompared))
    {
      return days + 1;
    }
    else
    {
      return days - 1;
    }
  }
  /**
   * Calcule le temps en millisecondes qui sépare la date en cours de celle passée
   * en argument. Si la date à comparer est antérieure, le  résultat sera donc négatif.
   * @param toBeCompared Date entre laquelle et celle en cours il faut rechercher
   * le nombre de millisecondes
   * @return Le temps en millisecondes séparant les deux dates
   */
  public long getTimeBetween(Bid4WinDate toBeCompared)
  {
    // Calcule les millisecondes entre les 2 dates
    return toBeCompared.getTime() - this.getTime();
  }

  /**
   * Ajoute le nombre de jours en argument à la date en cours et retourne le résultat
   * @param nbDays Nombre de jours à ajouter, pouvant être positif ou négatif
   * @return La date après l'ajout effectué
   */
  public Bid4WinDate addDays(int nbDays)
  {
    Bid4WinCalendar calendar = this.toGregorianCalendar();
    calendar.add(Calendar.DATE, nbDays);
    return calendar.getDate();
  }
  /**
   * Ajoute le temps en argument à la date en cours et retourne le résultat
   * @param timeInMillis Temps en millisecondes à ajouter, pouvant être positif
   * ou négatif
   * @return La date après l'ajout effectué
   */
  public Bid4WinDate addTime(long timeInMillis)
  {
    return new Bid4WinDate(this.getTime() + timeInMillis);
  }

  /**
   * Fonction qui retire de la date courante toutes les informations qui ne
   * définissent pas la date (cad heure, minute, seconde) afin de retourner la
   * date nettoyée
   * @return Une copie de la date sans aucune information d'horaire
   */
  public Bid4WinDate removeTimeInfo()
  {
    Bid4WinCalendar calendar = this.toGregorianCalendar();
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    return calendar.getDate();
  }
  /**
   * Fonction qui retire de la date courante les informations de seconde
   * @return Une copie de la date sans aucune information de seconde
   */
  public Bid4WinDate removeSecondInfo()
  {
    Bid4WinCalendar calendar = this.toGregorianCalendar();
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    return calendar.getDate();
  }
  /**
   * Fonction qui retire de la date courante les informations de millisecondes
   * @return Une copie de la date sans aucune information de millisecondes
   */
  public Bid4WinDate removeMilliSecondInfo()
  {
    Bid4WinCalendar calendar = this.toGregorianCalendar();
    calendar.set(Calendar.MILLISECOND, 0);
    return calendar.getDate();
  }

  /**
   * Crée un calendrier grégorien utilisant le fuseau horaire par défaut et défini
   * sa date comme la même que l'objet courant
   * @return L'objet date converti en calendrier grégorien
   */
  public Bid4WinCalendar toGregorianCalendar()
  {
    return new Bid4WinCalendar(this);
  }

  /**
   * Indique si les deux dates correspondent au même jour.
   * @param dateToCompare Date à comparer avec la date courante
   * @return True si les dates correspondent au même jour, false sinon
   */
  public boolean isSameDay(Bid4WinDate dateToCompare)
  {
    if(this.getDaysBetween(dateToCompare) == 0)
    {
      return true;
    }
    return false;
  }

  /**
   * Permet de formater la date en cours directement en chaîne de caractères du
   * type DD/MM/YYYY
   * @return La chaîne de caractères formatée à partir de la date
   */
  public String formatDDIMMIYYYY()
  {
    return Bid4WinDateFormat.formatDDIMMIYYYY(this);
  }
  /**
   * Permet de formater la date en cours directement en chaîne de caractères du
   * type YYYY/MM/DD
   * @return La chaîne de caractères formatée à partir de la date
   */
  public String formatYYYYIMMIDD()
  {
    return Bid4WinDateFormat.formatYYYYIMMIDD(this);
  }
  /**
   * Permet de formater la date en cours directement en chaîne de caractères du
   * type DD/MM/YYYY HH:mm:ss:SSS
   * @return La chaîne de caractères formatée à partir de la date
   */
  public String formatDDIMMIYYYY_HHIMMISSISSS()
  {
    return Bid4WinDateFormat.formatDDIMMIYYYY_HHIMMISSISSS(this);
  }
  /**
   * Permet de formater la date en cours directement en chaîne de caractères du
   * type YYYY/MM/DD HH:mm:ss:SSS
   * @return La chaîne de caractères formatée à partir de la date
   */
  public String formatYYYYIMMIDD_HHIMMISSISSS()
  {
    return Bid4WinDateFormat.formatYYYYIMMIDD_HHIMMISSISSS(this);
  }

  /**
   * Cette fonction permet de tester si l'objet en paramètre est différent de la
   * date courante. Deux dates sont considérées différentes si elles ne sont pas
   * égales
   * @param toBeCompared Objet dont la différence avec la date courante doit être
   * testé
   * @return True si l'objet en paramètre est différent de la date courante, false
   * sinon
   */
  public final boolean differs(Object toBeCompared)
  {
    return !this.equals(toBeCompared);
  }
}
