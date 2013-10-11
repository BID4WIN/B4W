package com.bid4win.commons.persistence.entity.foo.not_cached;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Metamodel de la class FooEmbeddable<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(FooEmbeddable.class)
public abstract class FooEmbeddable_
{
  /** Définition de la clé de l'objet */
  public static volatile SingularAttribute<FooEmbeddable, String> key;
  /** Définition de la valeur de l'objet */
  public static volatile SingularAttribute<FooEmbeddable, String> value;
}
