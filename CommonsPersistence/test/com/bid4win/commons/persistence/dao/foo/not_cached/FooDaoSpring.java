package com.bid4win.commons.persistence.dao.foo.not_cached;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.bid4win.commons.persistence.dao.foo.FooAbstractDaoSpring;
import com.bid4win.commons.persistence.entity.core.EmbeddableDate;
import com.bid4win.commons.persistence.entity.foo.not_cached.Foo;
import com.bid4win.commons.persistence.entity.foo.not_cached.Foo_;

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
   * @param root {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.foo.FooAbstractDaoSpring#getEmbeddedDatePath(javax.persistence.criteria.Root)
   */
  @Override
  protected Path<EmbeddableDate> getEmbeddedDatePath(Root<FOO> root)
  {
    // TODO Auto-generated method stub
    return root.get(Foo_.embeddedDate);
  }
}
