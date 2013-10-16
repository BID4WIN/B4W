package com.bid4win.commons.persistence.entity.property;

import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimple;

/**
 * Définition des accès aux champs de la classe PropertyRootAbstract<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class PropertyRootAbstract_Fields extends PropertyBase_Fields
{
  /** Definition du champ correspondant à l'identifiant de la propriété racine */
  public static final Bid4WinFieldSimple<PropertyRootAbstract<?, ?>, Integer> ID =
      new Bid4WinFieldSimple<PropertyRootAbstract<?, ?>, Integer>(null, PropertyRootAbstract_.id);
}
