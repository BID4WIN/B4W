package com.bid4win.commons.persistence.entity;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimple;

/**
 * D�finition des acc�s aux champs de la classe Bid4WinEntity<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinEntity_Fields
{
  /** Definition du champ correspondant � la date de cr�ation de l'entit� */
  public static final Bid4WinFieldSimple<Bid4WinEntity<?, ?>, Bid4WinDate> CREATE_DATE =
      new Bid4WinFieldSimple<Bid4WinEntity<?, ?>, Bid4WinDate>(null, Bid4WinEntity_.createDate);
  /** Definition du champ correspondant � la date de modification de l'entit� */
  public static final Bid4WinFieldSimple<Bid4WinEntity<?, ?>, Bid4WinDate> UPDATE_DATE =
      new Bid4WinFieldSimple<Bid4WinEntity<?, ?>, Bid4WinDate>(null, Bid4WinEntity_.updateDate);
}
