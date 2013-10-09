package com.bid4win.commons.core.io.resource;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;

/**
 * Cette interface d�fini la base des ressources divis�es en portion utilis�es
 * sous forme de fichier par les magasins de stockage correspondant<BR>
 * <BR>
 * @param <TYPE> Doit d�finir la classe des types de ressource<BR>
 * @param <PART_TYPE> Doit d�finir la classe des types de portion de ressource<BR>
 * @param <PART> Doit d�finir la classe des portions de ressource<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public interface Bid4WinFileResourceMultiPart<TYPE, PART_TYPE extends Bid4WinObjectType<PART_TYPE>,
                                              PART extends Bid4WinFileResourcePart<PART, TYPE, PART_TYPE>>
       extends Bid4WinResourceMultiPart<TYPE, PART_TYPE, PART>, Bid4WinFileResource<TYPE>
{
  // Pas de d�finition sp�cifique
}
