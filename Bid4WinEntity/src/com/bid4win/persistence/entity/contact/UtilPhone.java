package com.bid4win.persistence.entity.contact;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.AccountRef;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class UtilPhone
{
  /** Pattern des indicatifs t�l�phoniques */
  private final static String CODE_PATTERN ="[0-9]{1,3}";
  /** Pattern des num�ros de t�l�phone */
  private final static String NUMBER_PATTERN ="[1-9][0-9]{8}";

  /**
   * Cette m�thode permet de tester si l'indicatif t�l�phonique en param�tre est
   * valide
   * @param code Indicatif t�l�phonique � tester
   * @return L'indicatif t�l�phonique test�
   * @throws UserException Si l'indicatif t�l�phonique ne respecte pas le format
   * attendu
   */
  public static String checkCode(String code) throws UserException
  {
    return UtilString.checkPattern("code", code, UtilPhone.CODE_PATTERN,
                                   AccountRef.ACCOUNT_PHONE_INVALID_ERROR, 1);
  }
  /**
   * Cette m�thode permet de tester si le num�ro de t�l�phone en param�tre est
   * valide
   * @param number Num�ro de t�l�phone � tester
   * @return Le num�ro de t�l�phone test�
   * @throws UserException Si le num�ro de t�l�phone ne respecte pas le format
   * attendu
   */
  public static String checkNumber(String number) throws UserException
  {
    return UtilString.checkPattern("number", number, UtilPhone.NUMBER_PATTERN,
                                   AccountRef.ACCOUNT_PHONE_INVALID_ERROR, 1);
  }
}
