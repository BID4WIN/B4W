package com.bid4win.commons.core.io.resource;

import com.bid4win.commons.core.exception.UserException;

/**
 * Cette interface défini la base des ressources utilisées sous forme de fichier
 * par les magasins de stockage<BR>
 * <BR>
 * @param <TYPE> Doit définir la classe des types de ressource<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public interface Bid4WinFileResource<TYPE> extends Bid4WinResource<TYPE>
{
  /**
   * Cette méthode doit définir le chemin d'accès complet à la ressource
   * @return Le chemin d'accès complet à la ressource
   * @throws UserException Si le chemin d'accès complet à la ressource est invalide
   */
  public String getFullPath() throws UserException;
}
