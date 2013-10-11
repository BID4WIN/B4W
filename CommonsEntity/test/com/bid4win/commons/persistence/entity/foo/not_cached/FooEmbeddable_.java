package com.bid4win.commons.persistence.entity.foo.not_cached;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Metamodel de la class FooEmbeddable<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(FooEmbeddable.class)
public abstract class FooEmbeddable_
{
  /** D�finition de la cl� de l'objet */
  public static volatile SingularAttribute<FooEmbeddable, String> key;
  /** D�finition de la valeur de l'objet */
  public static volatile SingularAttribute<FooEmbeddable, String> value;
}
