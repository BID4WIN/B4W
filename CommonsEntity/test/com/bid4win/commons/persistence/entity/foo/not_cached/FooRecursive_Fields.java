package com.bid4win.commons.persistence.entity.foo.not_cached;

import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldJoinedSimple;
import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimple;

/**
 * Metamodel de la class FooRecursive<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class FooRecursive_Fields extends Foo_Fields
{
  /** Definition du champ correspondant au parent de l'objet */
  public static final Bid4WinFieldSimple<FooRecursive, FooRecursive> PARENT =
      new Bid4WinFieldSimple<FooRecursive, FooRecursive>(null, FooRecursive_.parent);
  /** Definition du lien avec le champ correspondant au parent de l'objet */
  public static final Bid4WinFieldJoinedSimple<FooRecursive, FooRecursive> PARENT_JOINED =
      new Bid4WinFieldJoinedSimple<FooRecursive, FooRecursive>(null, FooRecursive_.parent);
}