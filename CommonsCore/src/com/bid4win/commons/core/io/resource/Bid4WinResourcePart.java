package com.bid4win.commons.core.io.resource;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.exception.UserException;

/**
 * Cette interface défini la base des portions de ressource utilisées par les magasins
 * de stockage correspondant<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * @param <TYPE> Doit définir la classe des types de ressource<BR>
 * @param <PART_TYPE> Doit définir la classe des types de portion de ressource<BR>
 * <BR>
 * @author Emeric Fillâtre
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
   * Getter de la portion de ressource du type donné correspondant celle courante
   * @param partType Type de la portion de ressource à récupérer
   * @return La portion de ressource du type donné correspondant à celle courante
   * @throws UserException Si un problème empêche de récupérer de la portion de
   * ressource
   */
  public CLASS getPart(PART_TYPE partType) throws UserException;
}
