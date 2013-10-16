package com.bid4win.commons.persistence.dao.foo.cached;

import com.bid4win.commons.persistence.dao.foo.FooAbstractDaoSpring;
import com.bid4win.commons.persistence.entity.foo.cached.FooCached;

/**
 * DAO pour les entités de la classe Foo<BR>
 * <BR>
 * @param <FOO> Entité gérée par le DAO<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class FooCachedDaoSpring<FOO extends FooCached<FOO>> extends FooAbstractDaoSpring<FOO>
{
  /**
   * Constructeur pour spécialisation
   * @param entityClass Class de l'entité gérée par le DAO
   */
  protected FooCachedDaoSpring(Class<FOO> entityClass)
  {
    super(entityClass);
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.foo.FooAbstractDaoSpring#getEmbeddedDateField()
   */
 /* @Override
  protected final Bid4WinFieldSimple<? super FOO, EmbeddableDate> getEmbeddedDateField()
  {
    return FooCached_Fields.EMBEDDED_DATE;
  }
  /**
   *
   * TODO A COMMENTER
   * @param root {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.foo.FooAbstractDaoSpring#getEmbeddedDatePath(javax.persistence.criteria.Root)
   */
/*  @Override
  protected Path<EmbeddableDate> getEmbeddedDatePath(Root<FOO> root)
  {
    // TODO Auto-generated method stub
    return root.get(FooCached_.embeddedDate);
  }*/
}
