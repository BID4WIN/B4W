package com.bid4win.commons.persistence.entity.foo.not_cached;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;

/**
 * Metamodel de la class FooComplex<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(FooComplex.class)
public abstract class FooComplex_ extends Foo_
{
  /** D�finition de l'objet inclus dans l'objet */
  public static volatile SingularAttribute<FooComplex, FooEmbeddable> embedded;
  /** D�finition du set d'objets inclus dans l'objet */
  public static volatile SetAttribute<FooComplex, FooEmbeddable> embeddedSetInternal;
  /** D�finition de la liste d'objets inclus dans l'objet */
  public static volatile ListAttribute<FooComplex, FooEmbeddable> embeddedListInternal;

  // D�finition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
