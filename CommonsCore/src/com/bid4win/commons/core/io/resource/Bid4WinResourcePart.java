package com.bid4win.commons.core.io.resource;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.exception.UserException;

/**
 * Cette interface d�fini la base des portions de ressource utilis�es par les magasins
 * de stockage correspondant<BR>
 * <BR>
 * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
 * @param <TYPE> Doit d�finir la classe des types de ressource<BR>
 * @param <PART_TYPE> Doit d�finir la classe des types de portion de ressource<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public interface Bid4WinResourcePart<CLASS extends Bid4WinResourcePart<CLASS, TYPE, PART_TYPE>,
                                     TYPE, PART_TYPE extends Bid4WinObjectType<PART_TYPE>>
       extends Bid4WinResource<TYPE>
{
  /**
   * Getter du type de la portion de ressource
   * @return Le type de la portion de ressource
   */
  public PART_TYPE getPartType();
  /**
   * Getter de la portion de ressource du type donn� correspondant celle courante
   * @param partType Type de la portion de ressource � r�cup�rer
   * @return La portion de ressource du type donn� correspondant � celle courante
   * @throws UserException Si un probl�me emp�che de r�cup�rer de la portion de
   * ressource
   */
  public CLASS getPart(PART_TYPE partType) throws UserException;
}
