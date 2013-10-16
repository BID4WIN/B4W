package com.bid4win.commons.persistence.entity.property;

import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldJoinedSimple;
import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldJoinedToSimple;
import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimple;

/**
 * Définition des accès aux champs de la classe PropertyAbstract<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class PropertyAbstract_Fields extends PropertyBase_Fields
{
  /** Definition du champ correspondant à la propriété racine de la propriété */
  public static final Bid4WinFieldSimple<PropertyAbstract<?, ?>, String> KEY =
      new Bid4WinFieldSimple<PropertyAbstract<?, ?>, String>(null, PropertyAbstract_.key);
  /** Definition du champ correspondant à la propriété racine de la propriété */
  public static final Bid4WinFieldJoinedSimple<PropertyAbstract<?, ?>, PropertyRootAbstract<?, ?>> ROOT_JOINED =
      new Bid4WinFieldJoinedSimple<PropertyAbstract<?, ?>, PropertyRootAbstract<?, ?>>(
          null, PropertyAbstract_.root);
  /** Definition du champ correspondant à l'identifiant de la propriété racine de la propriété */
  public static final Bid4WinFieldJoinedToSimple<PropertyAbstract<?, ?>, PropertyRootAbstract<?, ?>, Integer> ROOT_ID_JOINED =
      new Bid4WinFieldJoinedToSimple<PropertyAbstract<?, ?>, PropertyRootAbstract<?, ?>, Integer>(ROOT_JOINED, PropertyRootAbstract_Fields.ID);
}
