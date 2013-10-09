package com.bid4win.commons.core.io.resource;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;

/**
 * Cette interface défini la base des portions de ressource utilisées sous forme
 * de fichier par les magasins de stockage correspondant<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * @param <TYPE> Doit définir la classe des types de ressource<BR>
 * @param <PART_TYPE> Doit définir la classe des types de portion de ressource<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public interface Bid4WinFileResourcePart<CLASS extends Bid4WinFileResourcePart<CLASS, TYPE, PART_TYPE>,
                                         TYPE, PART_TYPE extends Bid4WinObjectType<PART_TYPE>>
       extends Bid4WinResourcePart<CLASS, TYPE, PART_TYPE>, Bid4WinFileResource<TYPE>
{
  // Pas de définition spécifique
}
