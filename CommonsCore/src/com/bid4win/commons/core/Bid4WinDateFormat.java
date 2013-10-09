package com.bid4win.commons.core;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.bid4win.commons.core.exception.CommunicationException;

/**
 * Classe de formatage des dates contenant des formats prédéfinis<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinDateFormat extends SimpleDateFormat
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = 6704488329928588378L;

  /**
   * Lors de l'utilisation de cette classe dans un projet, le fuseau horaire par
   * défaut sera défini à GMT. Cela doit être fait avant l'instanciation des
   * formats statiques internes à la classe
   */
  static
  {
    Bid4WinCalendar.setDefaultTimeZoneToGMT();
  }

  /** Formateur de date au format DD/MM/YYYY */
  private final static Bid4WinDateFormat FORMAT_DDIMMIYYYY =
      new Bid4WinDateFormat("dd/MM/yyyy");
  /** Formateur de date au format YYYY/MM/DD */
  private final static Bid4WinDateFormat FORMAT_YYYYIMMIDD =
      new Bid4WinDateFormat("yyyy/MM/dd");
  /** Formateur de date au format DD/MM/YYYY HH:mm:ss:SSS */
  private final static Bid4WinDateFormat FORMAT_DDIMMIYYYY_HHIMMISSISSS =
      new Bid4WinDateFormat("dd/MM/yyyy HH:mm:ss:SSS");
  /** Formateur de date au format YYYY/MM/DD HH:mm:ss:SSS */
  private final static Bid4WinDateFormat FORMAT_YYYYIMMIDD_HHIMMISSISSS =
      new Bid4WinDateFormat("yyyy/MM/dd HH:mm:ss:SSS");

  /**
   * Cette fonction est utile pour directement parser une chaîne de caractères et
   * en récupérer une date
   * @param text Chaîne de caractères à parser
   * @param pattern Pattern à utiliser pour le parsing
   * @return La date issue du parsing de la chaîne de caractères en argument
   * @throws CommunicationException Si le parsing a échoué
   */
  public static Bid4WinDate parse(String text, String pattern) throws CommunicationException
  {
    Bid4WinDateFormat dateFormat = new Bid4WinDateFormat(pattern);
    try
    {
      return dateFormat.parse(text);
    }
    catch(ParseException ex)
    {
      throw new CommunicationException(ex);
    }
  }
  
  /**
   * Parse directement la date en paramètre avec le pattern DD/MM/YYYY
   * @param date Chaîne de caractères à parser
   * @return La date issue du parsing de la chaîne de caractères en argument
   * @throws CommunicationException Si un problème intervient durant le parsing
   */
  public static Bid4WinDate parseDDIMMIYYYY(String date) throws CommunicationException
  {
    try
    {
      synchronized(FORMAT_DDIMMIYYYY)
      {
        return FORMAT_DDIMMIYYYY.parse(date);
      }
    }
    catch(ParseException ex)
    {
      throw new CommunicationException(ex);
    }
  }
  /**
   * Formate directement la date en paramètre au format DD/MM/YYYY
   * @param date Date à formater
   * @return La chaîne de caractères formatée à partir de la date
   */
  protected static String formatDDIMMIYYYY(Date date)
  {
    synchronized(FORMAT_DDIMMIYYYY)
    {
      return FORMAT_DDIMMIYYYY.format(date);
    }
  }
  
  /**
   * Parse directement la date en paramètre avec le pattern YYYY/MM/DD
   * @param date Chaîne de caractères à parser
   * @return La date issue du parsing de la chaîne de caractères en argument
   * @throws CommunicationException Si un problème intervient durant le parsing
   */
  public static Bid4WinDate parseYYYYIMMIDD(String date) throws CommunicationException
  {
    try
    {
      synchronized(FORMAT_YYYYIMMIDD)
      {
        return FORMAT_YYYYIMMIDD.parse(date);
      }
    }
    catch(ParseException ex)
    {
      throw new CommunicationException(ex);
    }
  }
  /**
   * Formate directement la date en paramètre au format YYYY/MM/DD
   * @param date Date à formater
   * @return La chaîne de caractères formatée à partir de la date
   */
  protected static String formatYYYYIMMIDD(Date date)
  {
    synchronized(FORMAT_YYYYIMMIDD)
    {
      return FORMAT_YYYYIMMIDD.format(date);
    }
  }
  
  /**
   * Parse directement la date en paramètre avec le pattern DD/MM/YYYY HH:mm:ss:SSS
   * @param date Chaîne de caractères à parser
   * @return La date issue du parsing de la chaîne de caractères en argument
   * @throws CommunicationException Si un problème intervient durant le parsing
   */
  public static Bid4WinDate parseDDIMMIYYYY_HHIMMISSISSS(String date) throws CommunicationException
  {
    try
    {
      synchronized(FORMAT_DDIMMIYYYY_HHIMMISSISSS)
      {
        return FORMAT_DDIMMIYYYY_HHIMMISSISSS.parse(date);
      }
    }
    catch(ParseException ex)
    {
      throw new CommunicationException(ex);
    }
  }
  /**
   * Formate directement la date en paramètre au format YYYY/MM/DD HH:mm:ss:SSS
   * @param date Date à formater
   * @return La chaîne de caractères formatée à partir de la date
   */
  protected static String formatDDIMMIYYYY_HHIMMISSISSS(Date date)
  {
    synchronized(FORMAT_DDIMMIYYYY_HHIMMISSISSS)
    {
      return FORMAT_DDIMMIYYYY_HHIMMISSISSS.format(date);
    }
  }
  
  /**
   * Parse directement la date en paramètre avec le pattern YYYY/MM/DD HH:mm:ss:SSS
   * @param date Chaîne de caractères à parser
   * @return La date issue du parsing de la chaîne de caractères en argument
   * @throws CommunicationException Si un problème intervient durant le parsing
   */
  public static Bid4WinDate parseYYYYIMMIDD_HHIMMISSISSS(String date) throws CommunicationException
  {
    try
    {
      synchronized(FORMAT_YYYYIMMIDD_HHIMMISSISSS)
      {
        return FORMAT_YYYYIMMIDD_HHIMMISSISSS.parse(date);
      }
    }
    catch(ParseException ex)
    {
      throw new CommunicationException(ex);
    }
  }
  /**
   * Formate directement la date en paramètre au format YYYY/MM/DD HH:mm:ss:SSS
   * @param date Date à formater
   * @return La chaîne de caractères formatée à partir de la date
   */
  protected static String formatYYYYIMMIDD_HHIMMISSISSS(Date date)
  {
    synchronized(FORMAT_YYYYIMMIDD_HHIMMISSISSS)
    {
      return FORMAT_YYYYIMMIDD_HHIMMISSISSS.format(date);
    }
  }

  /**
   * Constructeur
   */
  public Bid4WinDateFormat()
  {
    super();
  }

  /**
   * Constructeur avec pattern de parsing
   * @param pattern Pattern de parsing
   */
  public Bid4WinDateFormat(String pattern)
  {
    super(pattern);
  }
  
  /**
   * Constructeur avec pattern de parsing
   * @param locale Region/Pays
   * @param pattern Pattern de parsing
   */
  public Bid4WinDateFormat(String pattern, Locale locale)
  {
    super(pattern,locale);
  }
  
  /**
   * Redéfinition afin de retourner le bon objet de date
   * @return {@inheritDoc}
   * @see java.text.SimpleDateFormat#get2DigitYearStart()
   */
  @Override
  public Bid4WinDate get2DigitYearStart()
  {
    return new Bid4WinDate(super.get2DigitYearStart());
  }

  /**
   * Redéfinition afin de retourner le bon objet de date. Cette fonction utilise
   * la ParseException pour tout problème de parsing
   * @param text {@inheritDoc}
   * @return {@inheritDoc}
   * @throws ParseException {@inheritDoc}
   * @see java.text.DateFormat#parse(java.lang.String)
   */
  @Override
  public Bid4WinDate parse(String text) throws ParseException
  {
    // Récupère la date à partir de la chaîne parsée
    Date date = super.parse(text);
    if(date == null)
    {
      throw new ParseException("Unparseable date: \"" + text + "\"", 0);
    }
    // Construit la date si la date a pu être récupérée
    return new Bid4WinDate(date);
  }

  /**
   * Redéfinition afin de retourner le bon objet de date. Cette fonction n'utilise
   * pas les exception mais retourne null si le parsing a échoué
   * @param text {@inheritDoc}
   * @param posisition {@inheritDoc}
   * @return {@inheritDoc}
   * @see java.text.SimpleDateFormat#parse(java.lang.String, java.text.ParsePosition)
   */
  @Override
  public Bid4WinDate parse(String text, ParsePosition posisition)
  {
    // Récupère la date à partir de la chaîne parsée
    Date date = super.parse(text, posisition);
    if(date == null)
    {
      return null;
    }
    // Construit la date si la date a pu être récupérée
    return new Bid4WinDate(date);
  }

  /**
   * Redéfinition afin de retourner le bon objet de calendrier
   * @return {@inheritDoc}
   * @see java.text.DateFormat#getCalendar()
   */
  @Override
  public Bid4WinCalendar getCalendar()
  {
    return new Bid4WinCalendar(super.getCalendar());
  }
}
