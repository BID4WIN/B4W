package com.bid4win.commons.core;

/**
 * Classe utilitaire sur les dates<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class UtilDate
{
  /**
   * Cette fonction permet de formater la date potentiellement nulle en param�tre
   * en cha�ne de caract�res du type DD/MM/YYYY 
   * @param date Date � formater
   * @return La cha�ne de caract�res format�e � partir de la date en param�tre
   */
  public static String formatDDIMMIYYYY(Bid4WinDate date)
  {
    return UtilDate.formatDDIMMIYYYY(date, "null");
  }
  /**
   * Cette fonction permet de formater la date potentiellement nulle en param�tre
   * en cha�ne de caract�res du type DD/MM/YYYY 
   * @param date Date � formater
   * @param stringForNull Cha�ne de caract�res � utiliser dans le cas o� la date
   * serait nulle
   * @return La cha�ne de caract�res format�e � partir de la date en param�tre
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
