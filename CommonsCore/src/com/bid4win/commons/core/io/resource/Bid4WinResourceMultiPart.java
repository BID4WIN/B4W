package com.bid4win.commons.core.io.resource;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.exception.UserException;

/**
 * Cette interface défini la base des ressources divisées en portion utilisées par
 * les magasins de stockage correspondant<BR>
 * <BR>
 * @param <TYPE> Doit définir la classe des types de ressource<BR>
 * @param <PART_TYPE> Doit définir la classe des types de portion de ressource<BR>
 * @param <PART> Doit définir la classe des portions de ressource<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public interface Bid4WinResourceMultiPart<TYPE, PART_TYPE extends Bid4WinObjectType<PART_TYPE>,
                                          PART extends Bid4WinResourcePart<PART, TYPE, PART_TYPE>>
       extends Bid4WinResource<TYPE>
{
  /**
   * Getter de la portion de ressource correspondant au type en paramètre
   * @param partType Type de la portion de ressource à récupérer
   * @return La portion de ressource correspondant au type en paramètre
   * @throws UserException Si un problème empêche de récupérer de la portion de
   * ressource
   */
  public PART getPart(PART_TYPE partType) throws UserException;
}
