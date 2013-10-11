package com.bid4win.commons.persistence.entity.resource;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.core.reference.MessageRef;

/**
 * Classe utilitaire sur les ressources<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class UtilResource
{
  /**
   * Cette m�thode permet de tester si le nom de resource en param�tre est valide
   * @param name Nom de resource � tester
   * @param base Base des r�f�rences de message portant sur les ressources
   * @return Le nom de resource test� et nettoy�
   * @throws UserException Si le nom de resource ne respecte pas le format attendu
   */
  public static String checkName(String name, MessageRef base) throws UserException
  {
    name = UtilString.trimNotNull(name).replaceAll("[ ]+", "_");
    name = name.replaceAll("[\\Q/.\\\\E]+", "-");
    return UtilFile.checkFilename(name, base);
  }
}
