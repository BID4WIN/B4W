package com.bid4win.commons.persistence.entity.property;

import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;

/**
 * Metamodel de la classe PropertyRootAbstract<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(PropertyRootAbstract.class)
public abstract class PropertyRootAbstract_ extends PropertyBase_
{
  /** D�finition de l'identifiant de la propri�t� racine */
  public static volatile SingularAttribute<PropertyRootAbstract<?, ?>, Integer> id;
  /** D�finition du champ permettant le for�age de la modification de la propri�t� racine */
  public static volatile SingularAttribute<PropertyRootAbstract<?, ?>, Integer> updateForce;
  /** Definition de la map de sous-propri�t�s de la propri�t� racine */
  public static volatile MapAttribute<PropertyRootAbstract<?, ?>, String, PropertyAbstract<?, ?>> propertyMapDatabase;

  // D�finition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
