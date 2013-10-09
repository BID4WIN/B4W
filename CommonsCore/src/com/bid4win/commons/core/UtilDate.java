package com.bid4win.commons.core;

/**
 * Classe utilitaire sur les dates<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class UtilDate
{
  /**
   * Cette fonction permet de formater la date potentiellement nulle en paramètre
   * en chaîne de caractères du type DD/MM/YYYY 
   * @param date Date à formater
   * @return La chaîne de caractères formatée à partir de la date en paramètre
   */
  public static String formatDDIMMIYYYY(Bid4WinDate date)
  {
    return UtilDate.formatDDIMMIYYYY(date, "null");
  }
  /**
   * Cette fonction permet de formater la date potentiellement nulle en paramètre
   * en chaîne de caractères du type DD/MM/YYYY 
   * @param date Date à formater
   * @param stringForNull Chaîne de caractères à utiliser dans le cas où la date
   * serait nulle
   * @return La chaîne de caractères formatée à partir de la date en paramètre
   */
  public static String formatDDIMMIYYYY(Bid4WinDate date, String stringForNull)
  {
    if(date == null)
    {
      return stringForNull;
    }
    return date.formatDDIMMIYYYY();
  }
}
