package com.bid4win.commons.persistence.dao.foo.not_cached;

import com.bid4win.commons.persistence.dao.foo.FooAbstractDaoSpring;
import com.bid4win.commons.persistence.entity.foo.not_cached.Foo;

/**
 * DAO pour les entités de la classe Foo<BR>
 * <BR>
 * @param <FOO> Entité gérée par le DAO<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class FooDaoSpring<FOO extends Foo<FOO>> extends FooAbstractDaoSpring<FOO>
{
  /**
   * Constructeur pour spécialisation
   * @param entityClass Class de l'entité gérée par le DAO
   */
  protected FooDaoSpring(Class<FOO> entityClass)
  {
    super(entityClass);
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.foo.FooAbstractDaoSpring#getEmbeddedDateField()
   */
/*  @Override
  protected final Bid4WinFieldSimple<? super FOO, EmbeddableDate> getEmbeddedDateField()
  {
    return Foo_Fields.EMBEDDED_DATE;
  }*/
}
