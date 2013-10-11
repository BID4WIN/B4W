package com.bid4win.commons.persistence.entity.foo.not_cached;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Metamodel de la class FooComplex<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(FooComplex.class)
public abstract class FooComplex_ extends Foo_
{
  /** Définition de l'objet inclus dans l'objet */
  public static volatile SingularAttribute<FooComplex, FooEmbeddable> embedded;
  /** Définition du set d'objets inclus dans l'objet */
  public static volatile SetAttribute<FooComplex, FooEmbeddable> embeddedSetInternal;
  /** Définition de la liste d'objets inclus dans l'objet */
  public static volatile ListAttribute<FooComplex, FooEmbeddable> embeddedListInternal;
}
