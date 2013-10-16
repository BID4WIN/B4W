package com.bid4win.commons.persistence.entity;

import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimple;

/**
 * D�finition des acc�s aux champs de la classe Bid4WinEntityAutoID<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinEntityAutoID_Fields extends Bid4WinEntity_Fields
{
  /** Definition du champ correspondant � l'identifiant de l'entit� */
  public static final Bid4WinFieldSimple<Bid4WinEntityAutoID<?>, Long> ID =
      new Bid4WinFieldSimple<Bid4WinEntityAutoID<?>, Long>(null, Bid4WinEntityAutoID_.id);
}
