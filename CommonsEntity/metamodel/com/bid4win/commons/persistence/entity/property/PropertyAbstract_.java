package com.bid4win.commons.persistence.entity.property;

import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;

/**
 * Metamodel de la classe PropertyAbstract<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(PropertyAbstract.class)
public abstract class PropertyAbstract_ extends PropertyBase_
{
  /** D�finition de l'identifiant de la propri�t� */
  public static volatile SingularAttribute<PropertyAbstract<?, ?>, Integer> id;
  /** Definition de la cl� de la propri�t� */
  public static volatile SingularAttribute<PropertyAbstract<?, ?>, String> key;
  /** Definition de la valeur de la propri�t� */
  public static volatile SingularAttribute<PropertyAbstract<?, ?>, String> value;
  /** Definition de la map de sous-propri�t�s de la propri�t� */
  public static volatile MapAttribute<PropertyAbstract<?, ?>, String, PropertyAbstract<?, ?>> propertyMapInternal;
  /** Definition de la propri�t� racine de la propri�t� */
  public static volatile SingularAttribute<PropertyAbstract<?, ?>, PropertyRootAbstract<?, ?>> root;
  /** Definition de la propri�t� parent de la propri�t� */
  public static volatile SingularAttribute<PropertyAbstract<?, ?>, PropertyAbstract<?, ?>> property;

  // D�finition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    /** D�fini la profondeur du noeud de relation existant avec la propri�t� parent */
    PropertyAbstract_Relations.NODE_PROPERTY.addNode(PropertyAbstract_Relations.NODE_ROOT);
    PropertyAbstract_Relations.NODE_PROPERTY.addNode(PropertyAbstract_Relations.NODE_PROPERTY);
    Bid4WinEntity_.stopProtection();
  }
}
