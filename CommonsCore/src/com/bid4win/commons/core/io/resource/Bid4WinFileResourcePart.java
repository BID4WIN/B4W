package com.bid4win.commons.core.io.resource;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;

/**
 * Cette interface d�fini la base des portions de ressource utilis�es sous forme
 * de fichier par les magasins de stockage correspondant<BR>
 * <BR>
 * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
 * @param <TYPE> Doit d�finir la classe des types de ressource<BR>
 * @param <PART_TYPE> Doit d�finir la classe des types de portion de ressource<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public interface Bid4WinFileResourcePart<CLASS extends Bid4WinFileResourcePart<CLASS, TYPE, PART_TYPE>,
                                         TYPE, PART_TYPE extends Bid4WinObjectType<PART_TYPE>>
       extends Bid4WinResourcePart<CLASS, TYPE, PART_TYPE>, Bid4WinFileResource<TYPE>
{
  // Pas de d�finition sp�cifique
}
