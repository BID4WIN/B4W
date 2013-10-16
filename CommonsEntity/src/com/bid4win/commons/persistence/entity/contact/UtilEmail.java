package com.bid4win.commons.persistence.entity.contact;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.ConnectionRef;

/**
 * Classe utilitaire sur les emails<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class UtilEmail
{
  /** Liste des extensions de domaine sp�cifiques */
  private final static String DOMAINS = "com|org|net|edu|gov|mil|biz|info|mobi|name|aero|asia|jobs|museum";
  /** Pattern des adresses mail */
  private final static String EMAIL_PATTERN ="([a-z0-9]+[_\\Q-.+%\\E])*[a-z0-9]+[@]{1}([a-z0-9]+[\\Q-.\\E])+([a-z]{2}|" + UtilEmail.DOMAINS + ")";

  /**
   * Cette m�thode permet de tester si l'adresse email en param�tre est valide
   * @param address Adresse email � tester
   * @return L'adresse email test�e
   * @throws UserException Si l'adresse email ne respecte pas le format attendu
   */
  public static String checkAddress(String address) throws UserException
  {
    return UtilString.checkPattern("address", address, UtilEmail.EMAIL_PATTERN,
                                   ConnectionRef.EMAIL_INVALID_ERROR, 1);
  }
}
