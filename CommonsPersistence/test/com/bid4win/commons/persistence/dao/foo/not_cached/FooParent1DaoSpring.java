package com.bid4win.commons.persistence.dao.foo.not_cached;

import com.bid4win.commons.persistence.entity.foo.not_cached.FooParent1;

/**
 * DAO pour les entités de la classe FooParent1<BR>
 * <BR>
 * @author Emeric Fillâtre
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

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.foo.FooAbstractDaoSpring#getEmbeddedDateField()
   */
/*  @Override
  protected Bid4WinField2<FooParent1, EmbeddableDate> getEmbeddedDateField()
  {
    // TODO Auto-generated method stub
    return FooParent1_.EMBEDDABLE_DATE2;
  }*/
}
