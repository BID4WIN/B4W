package com.bid4win.commons.persistence.dao.foo.cached;

import com.bid4win.commons.persistence.dao.foo.FooAbstractDaoSpring;
import com.bid4win.commons.persistence.entity.foo.cached.FooCached;

/**
 * DAO pour les entit�s de la classe Foo<BR>
 * <BR>
 * @param <FOO> Entit� g�r�e par le DAO<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class FooCachedDaoSpring<FOO extends FooCached<FOO>> extends FooAbstractDaoSpring<FOO>
{
  /**
   * Constructeur pour sp�cialisation
   * @param entityClass Class de l'entit� g�r�e par le DAO
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
