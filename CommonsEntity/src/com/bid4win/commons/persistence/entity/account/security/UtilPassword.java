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
  /** Pattern des valeurs de mots de passe non cryptés */
  private final static String CLEAR_PASSWORD_PATTERN = "[0-9a-zA-Z]{" + UtilPassword.PASSWORD_MIN_SIZE + "," + UtilPassword.PASSWORD_MAX_SIZE + "}";
  /** Pattern des valeurs de mots de passe cryptés */
  private final static String HASHED_PASSWORD_PATTERN = "[0-9a-f]{" + UtilPassword.PASSWORD_MIN_SIZE + "," + 2 * UtilPassword.PASSWORD_MAX_SIZE + "}";

  /**
   * Cette méthode permet de tester que la chaîne de caractères en paramètre est
   * un mot de passe non crypté valide
   * @param password La chaîne de caractères à tester
   * @return La chaîne de caractères testée
   * @throws UserException Si La chaîne de caractères ne respecte pas le format
   * attendu
   */
  public static String checkClearPassword(String password) throws UserException
  {
    return UtilString.checkPattern("password", password,
                                   UtilPassword.CLEAR_PASSWORD_PATTERN,
                                   ConnectionRef.PASSWORD_INVALID_ERROR, 1);
  }

  /**
   * Cette méthode permet de tester que la chaîne de caractères en paramètre est
   * un mot de passe crypté valide
   * @param password La chaîne de caractères à tester
   * @return La chaîne de caractères testée
   * @throws UserException Si La chaîne de caractères ne respecte pas le format
   * attendu
   */
  public static String checkHashedPassword(String password) throws UserException
  {
    return UtilString.checkPattern("password", password,
                                   UtilPassword.HASHED_PASSWORD_PATTERN,
                                   ConnectionRef.PASSWORD_INVALID_ERROR, 1);
  }
}
