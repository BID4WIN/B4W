package com.bid4win.commons.persistence.dao.foo.not_cached;

import com.bid4win.commons.persistence.entity.foo.not_cached.FooParent1;

/**
 * DAO pour les entit�s de la classe FooParent1<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class FooParent1DaoSpring extends FooParentDaoSpring<FooParent1>
{
  /**
   * Constructeur
   */
  public FooParent1DaoSpring()
  {
    super(FooParent1.class);
  }
}
