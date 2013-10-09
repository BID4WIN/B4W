package com.bid4win.commons.core.io.resource;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;

/**
 * Cette interface défini la base des ressources divisées en portion utilisées
 * sous forme de fichier par les magasins de stockage correspondant<BR>
 * <BR>
 * @param <TYPE> Doit définir la classe des types de ressource<BR>
 * @param <PART_TYPE> Doit définir la classe des types de portion de ressource<BR>
 * @param <PART> Doit définir la classe des portions de ressource<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public interface Bid4WinFileResourceMultiPart<TYPE, PART_TYPE extends Bid4WinObjectType<PART_TYPE>,
                                              PART extends Bid4WinFileResourcePart<PART, TYPE, PART_TYPE>>
       extends Bid4WinResourceMultiPart<TYPE, PART_TYPE, PART>, Bid4WinFileResource<TYPE>
{
  // Pas de définition spécifique
}
