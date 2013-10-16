package com.bid4win.commons.persistence.entity.property;

import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldJoinedSimple;
import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldJoinedToSimple;
import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimple;

/**
 * D�finition des acc�s aux champs de la classe PropertyAbstract<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class PropertyAbstract_Fields extends PropertyBase_Fields
{
  /** Definition du champ correspondant � la propri�t� racine de la propri�t� */
  public static final Bid4WinFieldSimple<PropertyAbstract<?, ?>, String> KEY =
      new Bid4WinFieldSimple<PropertyAbstract<?, ?>, String>(null, PropertyAbstract_.key);
  /** Definition du champ correspondant � la propri�t� racine de la propri�t� */
  public static final Bid4WinFieldJoinedSimple<PropertyAbstract<?, ?>, PropertyRootAbstract<?, ?>> ROOT_JOINED =
      new Bid4WinFieldJoinedSimple<PropertyAbstract<?, ?>, PropertyRootAbstract<?, ?>>(
          null, PropertyAbstract_.root);
  /** Definition du champ correspondant � l'identifiant de la propri�t� racine de la propri�t� */
  public static final Bid4WinFieldJoinedToSimple<PropertyAbstract<?, ?>, PropertyRootAbstract<?, ?>, Integer> ROOT_ID_JOINED =
      new Bid4WinFieldJoinedToSimple<PropertyAbstract<?, ?>, PropertyRootAbstract<?, ?>, Integer>(ROOT_JOINED, PropertyRootAbstract_Fields.ID);
}
