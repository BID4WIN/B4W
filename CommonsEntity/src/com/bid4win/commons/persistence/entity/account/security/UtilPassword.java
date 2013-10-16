package com.bid4win.commons.persistence.entity.account.security;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.ConnectionRef;

/**
 * Classe utilitaire sur les mots de passe<BR>
 * <BR>
 * @author Maxime Ollagnier
 */
public class UtilPassword
{
  /** Taille minimum d'un mot de passe */
  private final static int PASSWORD_MIN_SIZE = 5;
  /** Taille maximum d'un mot de passe */
  private final static int PASSWORD_MAX_SIZE = 30;
  /** Pattern des valeurs de mots de passe non crypt�s */
  private final static String CLEAR_PASSWORD_PATTERN = "[0-9a-zA-Z]{" + UtilPassword.PASSWORD_MIN_SIZE + "," + UtilPassword.PASSWORD_MAX_SIZE + "}";
  /** Pattern des valeurs de mots de passe crypt�s */
  private final static String HASHED_PASSWORD_PATTERN = "[0-9a-f]{" + UtilPassword.PASSWORD_MIN_SIZE + "," + 2 * UtilPassword.PASSWORD_MAX_SIZE + "}";

  /**
   * Cette m�thode permet de tester que la cha�ne de caract�res en param�tre est
   * un mot de passe non crypt� valide
   * @param password La cha�ne de caract�res � tester
   * @return La cha�ne de caract�res test�e
   * @throws UserException Si La cha�ne de caract�res ne respecte pas le format
   * attendu
   */
  public static String checkClearPassword(String password) throws UserException
  {
    return UtilString.checkPattern("password", password,
                                   UtilPassword.CLEAR_PASSWORD_PATTERN,
                                   ConnectionRef.PASSWORD_INVALID_ERROR, 1);
  }

  /**
   * Cette m�thode permet de tester que la cha�ne de caract�res en param�tre est
   * un mot de passe crypt� valide
   * @param password La cha�ne de caract�res � tester
   * @return La cha�ne de caract�res test�e
   * @throws UserException Si La cha�ne de caract�res ne respecte pas le format
   * attendu
   */
  public static String checkHashedPassword(String password) throws UserException
  {
    return UtilString.checkPattern("password", password,
                                   UtilPassword.HASHED_PASSWORD_PATTERN,
                                   ConnectionRef.PASSWORD_INVALID_ERROR, 1);
  }
}
