package com.bid4win.commons.persistence.dao.foo.not_cached;

import com.bid4win.commons.persistence.dao.foo.FooAbstractDaoSpring;
import com.bid4win.commons.persistence.entity.foo.not_cached.Foo;

/**
 * DAO pour les entit�s de la classe Foo<BR>
 * <BR>
 * @param <FOO> Entit� g�r�e par le DAO<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class FooDaoSpring<FOO extends Foo<FOO>> extends FooAbstractDaoSpring<FOO>
{
  /**
   * Constructeur pour sp�cialisation
   * @param entityClass Class de l'entit� g�r�e par le DAO
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
