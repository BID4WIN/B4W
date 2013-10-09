package com.bid4win.commons.core;

import java.util.Calendar;
import java.util.Date;

import com.bid4win.commons.core.exception.CommunicationException;

/**
 * Cette classe �tend la classe Date de java afin d'y rajouter des fonctions utiles
 * et manquantes.<BR>
 * Automatiquement et pour �viter tout oubli lors de l'utilisation dans une application,
 * le fuseau horaire par d�faut sera d�fini � GMT lors de l'utilisation de cette
 * classe. Si un autre fuseau horaire par d�faut doit �tre utilis� � la place, il
 * faudra bien penser � le red�finir.<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinDate extends Date
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = 2975690765092854697L;
  /** TODO A COMMENTER */
  public final static Bid4WinDate FINAL_DATE = Bid4WinDate.getFinalDate();
  /**
   * Lors de l'utilisation de cette classe dans un projet, le fuseau horaire par
   * d�faut sera d�fini � GMT
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
   * Constructeur utilisant une distance par rapport � une date r�f�rence pour
   * cr�er la date
   * @param timeInMillis Temps en millisecondes par rapport � la date de r�f�rence
   */
  public Bid4WinDate(long timeInMillis)
  {
    super(timeInMillis);
  }
  /**
   * Constructeur de copie � partir de l'objet date de base
   * @param date Date � partir de laquelle copier la nouvelle
   */
  public Bid4WinDate(Date date)
  {
    this(date.getTime());
  }

  /**
   * Calcule le jour de la semaine (entier de 1 � 7) avec lundi comme premier jour
   * @return Le jour de la semaine
   */
  public int getFrenchDayOfWeek()
  {
    int weekDay = this.getEnglishDayOfWeek();
    // Le lundi est le premier jour de la semaine
    return (weekDay == 1) ? 7 : weekDay - 1;
  }
  /**
   * Calcule le jour de la semaine (entier de 1 � 7) avec dimanche comme premier
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
   * Calcule le nombre de jour qui s�pare la date en cours de celle en argument
   * en partant de la date en cours. Si la date � comparer est ant�rieure, le
   * r�sultat sera donc n�gatif
   * @param toBeCompared Date entre laquelle et celle en cours il faut rechercher
   * le nombre de jour
   * @return Le nombre de jours s�parant la date en cours de celle en argument
   */
  public int getDaysBetween(Bid4WinDate toBeCompared)
  {
    // R�cup�re les calendriers correspondants aux 2 dates � comparer
    Bid4WinCalendar begin = this.toGregorianCalendar();
    Bid4WinCalendar end = toBeCompared.toGregorianCalendar();
    // Calcul le nombre de jour entre les 2 dates
    int days = (new Long((end.getTimeInMillis() -
                          begin.getTimeInMillis()) / 86400000)).intValue();
    // D�cale la date de d�but du nombre de jour
    begin.add(Calendar.DATE, days);
    // On tombe le m�me jour, le reste de la division enti�re n'a pas interf�r�
    if(begin.get(Calendar.DATE) == end.get(Calendar.DATE))
    {
      return days;
    }
    // Sinon il faut d�caler d'un jour dans un sens ou dans l'autre
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
   * Calcule le temps en millisecondes qui s�pare la date en cours de celle pass�e
   * en argument. Si la date � comparer est ant�rieure, le  r�sultat sera donc n�gatif.
   * @param toBeCompared Date entre laquelle et celle en cours il faut rechercher
   * le nombre de millisecondes
   * @return Le temps en millisecondes s�parant les deux dates
   */
  public long getTimeBetween(Bid4WinDate toBeCompared)
  {
    // Calcule les millisecondes entre les 2 dates
    return toBeCompared.getTime() - this.getTime();
  }

  /**
   * Ajoute le nombre de jours en argument � la date en cours et retourne le r�sultat
   * @param nbDays Nombre de jours � ajouter, pouvant �tre positif ou n�gatif
   * @return La date apr�s l'ajout effectu�
   */
  public Bid4WinDate addDays(int nbDays)
  {
    Bid4WinCalendar calendar = this.toGregorianCalendar();
    calendar.add(Calendar.DATE, nbDays);
    return calendar.getDate();
  }
  /**
   * Ajoute le temps en argument � la date en cours et retourne le r�sultat
   * @param timeInMillis Temps en millisecondes � ajouter, pouvant �tre positif
   * ou n�gatif
   * @return La date apr�s l'ajout effectu�
   */
  public Bid4WinDate addTime(long timeInMillis)
  {
    return new Bid4WinDate(this.getTime() + timeInMillis);
  }

  /**
   * Fonction qui retire de la date courante toutes les informations qui ne
   * d�finissent pas la date (cad heure, minute, seconde) afin de retourner la
   * date nettoy�e
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
   * Cr�e un calendrier gr�gorien utilisant le fuseau horaire par d�faut et d�fini
   * sa date comme la m�me que l'objet courant
   * @return L'objet date converti en calendrier gr�gorien
   */
  public Bid4WinCalendar toGregorianCalendar()
  {
    return new Bid4WinCalendar(this);
  }

  /**
   * Indique si les deux dates correspondent au m�me jour.
   * @param dateToCompare Date � comparer avec la date courante
   * @return True si les dates correspondent au m�me jour, false sinon
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
   * Permet de formater la date en cours directement en cha�ne de caract�res du
   * type DD/MM/YYYY
   * @return La cha�ne de caract�res format�e � partir de la date
   */
  public String formatDDIMMIYYYY()
  {
    return Bid4WinDateFormat.formatDDIMMIYYYY(this);
  }
  /**
   * Permet de formater la date en cours directement en cha�ne de caract�res du
   * type YYYY/MM/DD
   * @return La cha�ne de caract�res format�e � partir de la date
   */
  public String formatYYYYIMMIDD()
  {
    return Bid4WinDateFormat.formatYYYYIMMIDD(this);
  }
  /**
   * Permet de formater la date en cours directement en cha�ne de caract�res du
   * type DD/MM/YYYY HH:mm:ss:SSS
   * @return La cha�ne de caract�res format�e � partir de la date
   */
  public String formatDDIMMIYYYY_HHIMMISSISSS()
  {
    return Bid4WinDateFormat.formatDDIMMIYYYY_HHIMMISSISSS(this);
  }
  /**
   * Permet de formater la date en cours directement en cha�ne de caract�res du
   * type YYYY/MM/DD HH:mm:ss:SSS
   * @return La cha�ne de caract�res format�e � partir de la date
   */
  public String formatYYYYIMMIDD_HHIMMISSISSS()
  {
    return Bid4WinDateFormat.formatYYYYIMMIDD_HHIMMISSISSS(this);
  }

  /**
   * Cette fonction permet de tester si l'objet en param�tre est diff�rent de la
   * date courante. Deux dates sont consid�r�es diff�rentes si elles ne sont pas
   * �gales
   * @param toBeCompared Objet dont la diff�rence avec la date courante doit �tre
   * test�
   * @return True si l'objet en param�tre est diff�rent de la date courante, false
   * sinon
   */
  public final boolean differs(Object toBeCompared)
  {
    return !this.equals(toBeCompared);
  }
}
