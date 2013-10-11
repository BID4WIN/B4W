package com.bid4win.commons.persistence.entity.property;

import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;

/**
 * Metamodel de la classe PropertyAbstract<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(PropertyAbstract.class)
public abstract class PropertyAbstract_ extends PropertyBase_
{
  /** Définition de l'identifiant de la propriété */
  public static volatile SingularAttribute<PropertyAbstract<?, ?>, Integer> id;
  /** Definition de la clé de la propriété */
  public static volatile SingularAttribute<PropertyAbstract<?, ?>, String> key;
  /** Definition de la valeur de la propriété */
  public static volatile SingularAttribute<PropertyAbstract<?, ?>, String> value;
  /** Definition de la map de sous-propriétés de la propriété */
  public static volatile MapAttribute<PropertyAbstract<?, ?>, String, PropertyAbstract<?, ?>> propertyMapInternal;
  /** Definition de la propriété racine de la propriété */
  public static volatile SingularAttribute<PropertyAbstract<?, ?>, PropertyRootAbstract<?, ?>> root;
  /** Definition de la propriété parent de la propriété */
  public static volatile SingularAttribute<PropertyAbstract<?, ?>, PropertyAbstract<?, ?>> property;

  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    /** Défini la profondeur du noeud de relation existant avec la propriété parent */
    PropertyAbstract_Relations.NODE_PROPERTY.addNode(PropertyAbstract_Relations.NODE_ROOT);
    PropertyAbstract_Relations.NODE_PROPERTY.addNode(PropertyAbstract_Relations.NODE_PROPERTY);
    Bid4WinEntity_.stopProtection();
  }
}
