package com.bid4win.commons.persistence.entity.foo.not_cached;

import com.bid4win.commons.persistence.entity.Bid4WinEmbeddable_Fields;
import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimple;

/**
 * Metamodel de la class FooEmbeddable<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class FooEmbeddable_Fields extends Bid4WinEmbeddable_Fields
{
  /** Definition du champ correspondant � la cl� de l'objet */
  public static final Bid4WinFieldSimple<FooEmbeddable, String> KEY =
      new Bid4WinFieldSimple<FooEmbeddable, String>(null, FooEmbeddable_.key);
}
