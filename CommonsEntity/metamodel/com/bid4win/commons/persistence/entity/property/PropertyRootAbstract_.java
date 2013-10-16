package com.bid4win.commons.persistence.entity.property;

import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;

/**
 * Metamodel de la classe PropertyRootAbstract<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(PropertyRootAbstract.class)
public abstract class PropertyRootAbstract_ extends PropertyBase_
{
  /** Définition de l'identifiant de la propriété racine */
  public static volatile SingularAttribute<PropertyRootAbstract<?, ?>, Integer> id;
  /** Définition du champ permettant le forçage de la modification de la propriété racine */
  public static volatile SingularAttribute<PropertyRootAbstract<?, ?>, Integer> updateForce;
  /** Definition de la map de sous-propriétés de la propriété racine */
  public static volatile MapAttribute<PropertyRootAbstract<?, ?>, String, PropertyAbstract<?, ?>> propertyMapDatabase;

  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
