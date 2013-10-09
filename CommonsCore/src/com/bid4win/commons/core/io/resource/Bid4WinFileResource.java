package com.bid4win.commons.core.io.resource;

import com.bid4win.commons.core.exception.UserException;

/**
 * Cette interface d�fini la base des ressources utilis�es sous forme de fichier
 * par les magasins de stockage<BR>
 * <BR>
 * @param <TYPE> Doit d�finir la classe des types de ressource<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public interface Bid4WinFileResource<TYPE> extends Bid4WinResource<TYPE>
{
  /**
   * Cette m�thode doit d�finir le chemin d'acc�s complet � la ressource
   * @return Le chemin d'acc�s complet � la ressource
   * @throws UserException Si le chemin d'acc�s complet � la ressource est invalide
   */
  public String getFullPath() throws UserException;
}
