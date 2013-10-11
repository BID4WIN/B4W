package com.bid4win.commons.persistence.entity.resource;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.core.reference.MessageRef;

/**
 * Classe utilitaire sur les ressources<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class UtilResource
{
  /**
   * Cette méthode permet de tester si le nom de resource en paramètre est valide
   * @param name Nom de resource à tester
   * @param base Base des références de message portant sur les ressources
   * @return Le nom de resource testé et nettoyé
   * @throws UserException Si le nom de resource ne respecte pas le format attendu
   */
  public static String checkName(String name, MessageRef base) throws UserException
  {
    name = UtilString.trimNotNull(name).replaceAll("[ ]+", "_");
    name = name.replaceAll("[\\Q/.\\\\E]+", "-");
    return UtilFile.checkFilename(name, base);
  }
}
