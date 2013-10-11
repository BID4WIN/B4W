package com.bid4win.commons.persistence.dao.foo.cached;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.persistence.dao.foo.FooParentAbstractDaoSpringTester;
import com.bid4win.commons.persistence.entity.foo.cached.FooCachedChild2;
import com.bid4win.commons.persistence.entity.foo.cached.FooCachedParent2;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class FooCachedParent2DaoSpringTest extends FooParentAbstractDaoSpringTester<FooCachedParent2>
{
  /** Référence du DAO à tester */
  @Autowired
  @Qualifier("FooCachedParent2Dao")
  private FooCachedParent2DaoSpring dao;

  /**
   * Permet de créer l'entité à utiliser pour les tests
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.foo.FooAbstractDaoSpringTester#createFoo()
   */
  @Override
  protected FooCachedParent2 createFoo()
  {
    return new FooCachedParent2("value", new Bid4WinDate());
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
  public void addChild(FooCachedParent2 parent, int index) throws ModelArgumentException
  {
    parent.putChild(new FooCachedChild2("" + index, "value " + index));
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
  public void addChilds(FooCachedParent2 parent, int index, int nb) throws ModelArgumentException
  {
    for(int i = 0 ; i < nb ; i++)
    {
      parent.putChild(new FooCachedChild2("" + index + "_" + i, "value " + index + "_" + i));
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
  public void updateChild(FooCachedParent2 parent, int index)
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
  public void removeChild(FooCachedParent2 parent, int index)
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
  public int getChildNb(FooCachedParent2 parent)
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
  public int getChildVersion(FooCachedParent2 parent, int index)
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
  public FooCachedParent2DaoSpring getDao()
  {
    return this.dao;
  }
}
