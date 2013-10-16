package com.bid4win.commons.persistence.entity.foo.not_cached;

import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldJoinedSet;
import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldJoinedToSimple;
import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimple;
import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimpleToSimple;

/**
 * Metamodel de la class FooComplex<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class FooComplex_Fields extends Foo_Fields
{
  /** Definition du champ correspondant à l'objet inclus dans l'objet */
  public static final Bid4WinFieldSimple<FooComplex, FooEmbeddable> EMBEDDED =
      new Bid4WinFieldSimple<FooComplex, FooEmbeddable>(null, FooComplex_.embedded);
  /** Definition du champ correspondant à la clé de l'objet inclus dans l'objet */
  public static final Bid4WinFieldSimpleToSimple<FooComplex, FooEmbeddable, String> EMBEDDED_KEY =
      new Bid4WinFieldSimpleToSimple<FooComplex, FooEmbeddable, String>(EMBEDDED, FooEmbeddable_Fields.KEY);
  /** Definition du champ correspondant au set d'objets inclus dans l'objet */
  public static final Bid4WinFieldJoinedSet<FooComplex, FooEmbeddable, Long> EMBEDDED_SET_JOINED =
      new Bid4WinFieldJoinedSet<FooComplex, FooEmbeddable, Long>(
          null, FooComplex_.embeddedSetInternal, FooComplex_Fields.ID);
  /** Definition du champ correspondant aux clés des objets du set inclus dans l'objet */
  public static final Bid4WinFieldJoinedToSimple<FooComplex, FooEmbeddable, String> EMBEDDED_SET_KEY_JOINED =
      new Bid4WinFieldJoinedToSimple<FooComplex, FooEmbeddable, String>(EMBEDDED_SET_JOINED, FooEmbeddable_Fields.KEY);
}
