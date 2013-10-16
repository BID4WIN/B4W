package com.bid4win.commons.persistence.entity.foo.not_cached;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEmbeddable_;

/**
 * Metamodel de la class FooEmbeddable<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(FooEmbeddable.class)
public abstract class FooEmbeddable_ extends Bid4WinEmbeddable_
{
  /** Définition de la clé de l'objet */
  public static volatile SingularAttribute<FooEmbeddable, String> key;
  /** Définition de la valeur de l'objet */
  public static volatile SingularAttribute<FooEmbeddable, String> value;
}
