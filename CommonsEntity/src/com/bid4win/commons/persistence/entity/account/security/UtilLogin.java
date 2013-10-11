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
   * Cette m�thode permet de tester que la cha�ne de caract�res en param�tre est
   * un login valide
   * @param login La cha�ne de caract�res � tester
   * @return La cha�ne de caract�res test�e
   * @throws UserException Si La cha�ne de caract�res ne respecte pas le format
   * attendu
   */
  public static String checkLogin(String login) throws UserException
  {
    return UtilString.checkPattern("login", login, UtilLogin.LOGIN_PATTERN,
                                   ConnectionRef.CONNECTION_LOGIN_INVALID_ERROR, 1);
  }
}
