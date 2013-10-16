package com.bid4win.commons.persistence.entity.resource;

import com.bid4win.commons.persistence.entity.Bid4WinEntityAutoID_Fields;
import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimple;

/**
 * Définition des accès aux champs de la classe Resource<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Resource_Fields extends Bid4WinEntityAutoID_Fields
{
  /** Definition du champ correspondant à l'emplacement de stockage de la ressource */
  public static final Bid4WinFieldSimple<Resource<?, ?>, String> PATH =
      new Bid4WinFieldSimple<Resource<?, ?>, String>(null, Resource_.path);
  /** Definition du champ correspondant au chemin d'accès complet à la ressource */
  public static final Bid4WinFieldSimple<Resource<?, ?>, String> FULL_PATH =
      new Bid4WinFieldSimple<Resource<?, ?>, String>(null, Resource_.fullPath);
}
