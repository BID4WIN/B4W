package com.bid4win.commons.persistence.entity.property;

import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimple;

/**
 * D�finition des acc�s aux champs de la classe PropertyRootAbstract<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class PropertyRootAbstract_Fields extends PropertyBase_Fields
{
  /** Definition du champ correspondant � l'identifiant de la propri�t� racine */
  public static final Bid4WinFieldSimple<PropertyRootAbstract<?, ?>, Integer> ID =
      new Bid4WinFieldSimple<PropertyRootAbstract<?, ?>, Integer>(null, PropertyRootAbstract_.id);
}
