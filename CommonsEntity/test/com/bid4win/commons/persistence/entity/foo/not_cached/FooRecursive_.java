package com.bid4win.commons.persistence.entity.foo.not_cached;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;

/**
 * Metamodel de la class FooRecursive<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(FooRecursive.class)
public abstract class FooRecursive_ extends Foo_
{
  /** Définition du parent de l'objet */
  public static volatile SingularAttribute<FooRecursive, FooRecursive> parent;
  /** Définition du set d'enfants de l'objet*/
  public static volatile SetAttribute<FooRecursive, FooRecursive> childSetInternal;

  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    FooRecursive_Relations.NODE_PARENT.addNode(FooRecursive_Relations.NODE_PARENT);
    FooRecursive_Relations.NODE_CHILD.addNode(FooRecursive_Relations.NODE_PARENT);
    FooRecursive_Relations.NODE_CHILD.addNode(FooRecursive_Relations.NODE_CHILD);
    Bid4WinEntity_.stopProtection();
  }
}