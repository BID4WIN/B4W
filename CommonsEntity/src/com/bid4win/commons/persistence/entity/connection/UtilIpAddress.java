package com.bid4win.commons.persistence.entity.connection;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.ConnectionRef;

/**
 * Classe utilitaire sur les adresses IP<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class UtilIpAddress
{
  /** Nombre de suite de parties d'une adresse IPv4 */
  private final static int IPv4_PART_NB = 4;
  /** Taille maximum d'une partie d'une adresse IPv4 */
  private final static int IPv4_PART_SIZE = 3;
  /** Pattern d'une partie d'une adresse IPv4 */
  private final static String IPv4_PART_PATTERN = "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
  /** Format des adresses IPv4 */
  private final static String IPv4_PATTERN = "(" + IPv4_PART_PATTERN+ "\\.){" +
                                             (IPv4_PART_NB - 1) + "}" +
                                             IPv4_PART_PATTERN;
  /** Nombre de suite de parties d'une adresse IPv6 */
  private final static int IPv6_PART_NB = 8;
  /** Taille maximum d'une partie d'une adresse IPv6 */
  private final static int IPv6_PART_SIZE = 4;
  /** Pattern d'une partie d'une adresse IPv6 */
  private final static String IPv6_PART_PATTERN = "([0-9A-F]{1,4})";
  /** Format des adresses IPv6 */
  private final static String IPv6_PATTERN = "(" + IPv6_PART_PATTERN+ "\\:){" +
                                             (IPv6_PART_NB - 1) + "}" +
                                             IPv6_PART_PATTERN;

  /**
   * Cette méthode permet de tester que l'adresse IPv4 en paramètre est valide
   * @param ip Adresse IPv4 à tester
   * @return L'adresse IPv4 testée et formatée afin d'avoir toujours la même taille
   * @throws UserException Si l'adresse IPv4 ne respecte pas le format attendu
   */
  public static String checkAddressV4(String ip) throws UserException
  {
    return UtilIpAddress.checkAddress(
        ip, UtilIpAddress.IPv4_PATTERN, UtilIpAddress.IPv4_PART_SIZE, ".");
  }

  /**
   * Cette méthode permet de tester que l'adresse IPv6 en paramètre est valide
   * @param ip Adresse IPv6 à tester
   * @return L'adresse IPv6 testée et formatée afin d'avoir toujours la même taille
   * @throws UserException Si l'adresse IPv6 ne respecte pas le format attendu
   */
  public static String checkAddressV6(String ip) throws UserException
  {
    return UtilIpAddress.checkAddress(
        ip, UtilIpAddress.IPv6_PATTERN, UtilIpAddress.IPv6_PART_SIZE, ":");
  }

  /**
   * Cette méthode permet de tester que l'adresse IPv4 ou IPv6 en paramètre est
   * valide
   * @param ip Adresse IPv4 ou IPv6 à tester
   * @return L'adresse IPv4 ou IPv6 testée et formatée afin d'avoir toujours la
   * même taille
   * @throws UserException Si l'adresse ne respecte ni le format IPv4 ni IPv6
   */
  public static String checkAddress(String ip) throws UserException
  {
    try
    {
      return UtilIpAddress.checkAddressV4(ip);
    }
    catch(UserException ex)
    {
      return UtilIpAddress.checkAddressV6(ip);
    }
  }
  /**
   * Cette méthode permet de tester que l'adresse IP en paramètre est valide
   * @param ip Adresse IP à tester
   * @param pattern Pattern attendu pour l'adresse IP
   * @param partSize Taille maximum d'une partie de l'adresse
   * @param separator Séparateur utilisé entre les parties de l'adresse
   * @return L'adresse IP testée et formatée afin d'avoir toujours la même taille
   * @throws UserException Si l'adresse ne respecte pas le format attendu
   */
  public static String checkAddress(String ip, String pattern, int partSize, String separator)
         throws UserException
  {
    UtilString.checkPattern("ip", ip, pattern, ConnectionRef.CONNECTION_IP_INVALID_ERROR, 1);
    String[] string = ip.split("[" + separator + "]");
    StringBuffer result = new StringBuffer();
    for(int i = 0 ; i < string.length ; i++)
    {
      result.append(UtilString.fillBefore(new StringBuffer(string[i]), '0', partSize));
      if(i != string.length - 1)
      {
        result.append(separator);
      }
    }
    return result.toString();
  }
}
