package com.bid4win.commons.core.io.resource;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.exception.UserException;

/**
 * Cette interface d�fini la base des ressources divis�es en portion utilis�es par
 * les magasins de stockage correspondant<BR>
 * <BR>
 * @param <TYPE> Doit d�finir la classe des types de ressource<BR>
 * @param <PART_TYPE> Doit d�finir la classe des types de portion de ressource<BR>
 * @param <PART> Doit d�finir la classe des portions de ressource<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public interface Bid4WinResourceMultiPart<TYPE, PART_TYPE extends Bid4WinObjectType<PART_TYPE>,
                                          PART extends Bid4WinResourcePart<PART, TYPE, PART_TYPE>>
       extends Bid4WinResource<TYPE>
{
  /**
   * Getter de la portion de ressource correspondant au type en param�tre
   * @param partType Type de la portion de ressource � r�cup�rer
   * @return La portion de ressource correspondant au type en param�tre
   * @throws UserException Si un probl�me emp�che de r�cup�rer de la portion de
   * ressource
   */
  public PART getPart(PART_TYPE partType) throws UserException;
}
