package com.bid4win.commons.persistence.entity.account.security;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.ConnectionRef;

/**
 * Classe utilitaire sur les logins<BR>
 * <BR>
 * @author Maxime Ollagnier
 */
public class UtilLogin
{
  /** Pattern des valeurs d'identifiants */
  private final static String LOGIN_PATTERN = "[0-9a-z_\\Q-.$\\E]{5,30}";


  /**
   * Cette méthode permet de tester que la chaîne de caractères en paramètre est
   * un login valide
   * @param login La chaîne de caractères à tester
   * @return La chaîne de caractères testée
   * @throws UserException Si La chaîne de caractères ne respecte pas le format
   * attendu
   */
  public static String checkLogin(String login) throws UserException
  {
    return UtilString.checkPattern("login", login, UtilLogin.LOGIN_PATTERN,
                                   ConnectionRef.CONNECTION_LOGIN_INVALID_ERROR, 1);
  }
}
