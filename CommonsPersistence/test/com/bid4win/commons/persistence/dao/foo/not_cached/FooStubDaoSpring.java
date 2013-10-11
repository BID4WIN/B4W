package com.bid4win.commons.persistence.dao.foo.not_cached;

import com.bid4win.commons.persistence.entity.foo.not_cached.FooStub;

/**
 * DAO pour les entit�s de la classe FooStub<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class FooStubDaoSpring extends FooDaoSpring<FooStub>
{
  /**
   * Constructeur
   */
  protected FooStubDaoSpring()
  {
    super(FooStub.class);
  }
}
