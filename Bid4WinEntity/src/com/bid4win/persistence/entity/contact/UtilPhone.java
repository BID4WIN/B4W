package com.bid4win.persistence.entity.contact;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.AccountRef;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class UtilPhone
{
  /** Pattern des indicatifs téléphoniques */
  private final static String CODE_PATTERN ="[0-9]{1,3}";
  /** Pattern des numéros de téléphone */
  private final static String NUMBER_PATTERN ="[1-9][0-9]{8}";

  /**
   * Cette méthode permet de tester si l'indicatif téléphonique en paramètre est
   * valide
   * @param code Indicatif téléphonique à tester
   * @return L'indicatif téléphonique testé
   * @throws UserException Si l'indicatif téléphonique ne respecte pas le format
   * attendu
   */
  public static String checkCode(String code) throws UserException
  {
    return UtilString.checkPattern("code", code, UtilPhone.CODE_PATTERN,
                                   AccountRef.ACCOUNT_PHONE_INVALID_ERROR, 1);
  }
  /**
   * Cette méthode permet de tester si le numéro de téléphone en paramètre est
   * valide
   * @param number Numéro de téléphone à tester
   * @return Le numéro de téléphone testé
   * @throws UserException Si le numéro de téléphone ne respecte pas le format
   * attendu
   */
  public static String checkNumber(String number) throws UserException
  {
    return UtilString.checkPattern("number", number, UtilPhone.NUMBER_PATTERN,
                                   AccountRef.ACCOUNT_PHONE_INVALID_ERROR, 1);
  }
}
