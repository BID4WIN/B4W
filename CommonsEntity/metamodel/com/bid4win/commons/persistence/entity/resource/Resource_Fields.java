package com.bid4win.commons.persistence.entity.resource;

import com.bid4win.commons.persistence.entity.Bid4WinEntityAutoID_Fields;
import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimple;

/**
 * D�finition des acc�s aux champs de la classe Resource<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Resource_Fields extends Bid4WinEntityAutoID_Fields
{
  /** Definition du champ correspondant � l'emplacement de stockage de la ressource */
  public static final Bid4WinFieldSimple<Resource<?, ?>, String> PATH =
      new Bid4WinFieldSimple<Resource<?, ?>, String>(null, Resource_.path);
  /** Definition du champ correspondant au chemin d'acc�s complet � la ressource */
  public static final Bid4WinFieldSimple<Resource<?, ?>, String> FULL_PATH =
      new Bid4WinFieldSimple<Resource<?, ?>, String>(null, Resource_.fullPath);
}
