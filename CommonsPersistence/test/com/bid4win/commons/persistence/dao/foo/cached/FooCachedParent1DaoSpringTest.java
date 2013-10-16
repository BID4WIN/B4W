package com.bid4win.commons.persistence.dao.foo.cached;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.persistence.dao.foo.FooParentAbstractDaoSpringTester;
import com.bid4win.commons.persistence.entity.foo.cached.FooCachedChild1;
import com.bid4win.commons.persistence.entity.foo.cached.FooCachedParent1;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class FooCachedParent1DaoSpringTest extends FooParentAbstractDaoSpringTester<FooCachedParent1>
{
  /** Référence du DAO à tester */
  @Autowired
  @Qualifier("FooCachedParent1Dao")
  private FooCachedParent1DaoSpring dao;

  /**
   * Permet de créer l'entité à utiliser pour les tests
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.foo.FooAbstractDaoSpringTester#createFoo()
   */
  @Override
  protected FooCachedParent1 createFoo()
  {
    return new FooCachedParent1("value", new Bid4WinDate());
  }

  /**
   *
   * TODO A COMMENTER
   * @param parent {@inheritDoc}
   * @param index {@inheritDoc}
   * @throws ModelArgumentException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.foo.FooParentAbstractDaoSpringTester#addChild(com.bid4win.commons.persistence.entity.foo.FooAbstract, int)
   */
  @Override
  public void addChild(FooCachedParent1 parent, int index) throws ModelArgumentException
  {
    parent.putChild(new FooCachedChild1(UtilString.EMPTY + index, "value " + index));
  }
  /**
   *
   * TODO A COMMENTER
   * @param parent {@inheritDoc}
   * @param index {@inheritDoc}
   * @param nb {@inheritDoc}
   * @throws ModelArgumentException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.foo.FooParentAbstractDaoSpringTester#addChilds(com.bid4win.commons.persistence.entity.foo.FooAbstract, int, int)
   */
  @Override
  public void addChilds(FooCachedParent1 parent, int index, int nb) throws ModelArgumentException
  {
    for(int i = 0 ; i < nb ; i++)
    {
      parent.putChild(new FooCachedChild1(UtilString.EMPTY + index + "_" + i, "value " + index + "_" + i));
    }
  }
  /**
   *
   * TODO A COMMENTER
   * @param parent {@inheritDoc}
   * @param index {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.foo.FooParentAbstractDaoSpringTester#updateChild(com.bid4win.commons.persistence.entity.foo.FooAbstract, int)
   */
  @Override
  public void updateChild(FooCachedParent1 parent, int index)
  {
    parent.getChild("value " + index).setDate(new Bid4WinDate());
  }
  /**
   *
   * TODO A COMMENTER
   * @param parent {@inheritDoc}
   * @param index {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.foo.FooParentAbstractDaoSpringTester#removeChild(com.bid4win.commons.persistence.entity.foo.FooAbstract, int)
   */
  @Override
  public void removeChild(FooCachedParent1 parent, int index)
  {
    parent.removeChild("value " + index);
  }
  /**
   *
   * TODO A COMMENTER
   * @param parent {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.foo.FooParentAbstractDaoSpringTester#getChildNb(com.bid4win.commons.persistence.entity.foo.FooAbstract)
   */
  @Override
  public int getChildNb(FooCachedParent1 parent)
  {
    return parent.getChildNb();
  }
  /**
   *
   * TODO A COMMENTER
   * @param parent {@inheritDoc}
   * @param index {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.foo.FooParentAbstractDaoSpringTester#getChildVersion(com.bid4win.commons.persistence.entity.foo.FooAbstract, int)
   */
  @Override
  public int getChildVersion(FooCachedParent1 parent, int index)
  {
    return parent.getChild("value " + index).getVersion();
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.foo.FooAbstractDaoSpringTester#getDao()
   */
  @Override
  public FooCachedParent1DaoSpring getDao()
  {
    return this.dao;
  }
}
