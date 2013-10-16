package com.bid4win.commons.persistence.dao.foo.not_cached;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.persistence.dao.foo.FooParentAbstractDaoSpringTester;
import com.bid4win.commons.persistence.entity.foo.not_cached.FooChild2;
import com.bid4win.commons.persistence.entity.foo.not_cached.FooParent2;

/**
 * Test du DAO de la classe FooParent2<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class FooParent2DaoSpringTest extends FooParentAbstractDaoSpringTester<FooParent2>
{
  /** R�f�rence du DAO � tester */
  @Autowired
  @Qualifier("FooParent2Dao")
  private FooParent2DaoSpring dao;

  /**
   * Permet de cr�er l'entit� � utiliser pour les tests
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.foo.FooAbstractDaoSpringTester#createFoo()
   */
  @Override
  protected FooParent2 createFoo()
  {
    return new FooParent2("VALUE", new Bid4WinDate());
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
  public void addChild(FooParent2 parent, int index) throws ModelArgumentException
  {
    parent.putChild(new FooChild2(UtilString.EMPTY + index, "value " + index));
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
  public void addChilds(FooParent2 parent, int index, int nb) throws ModelArgumentException
  {
    for(int i = 0 ; i < nb ; i++)
    {
      parent.putChild(new FooChild2(UtilString.EMPTY + index + "_" + i, "value " + index + "_" + i));
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
  public void updateChild(FooParent2 parent, int index)
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
  public void removeChild(FooParent2 parent, int index)
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
  public int getChildNb(FooParent2 parent)
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
  public int getChildVersion(FooParent2 parent, int index)
  {
    return parent.getChild("value " + index).getVersion();
  }
  /**
   * Getter du DAO � tester
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.foo.FooAbstractDaoSpringTester#getDao()
   */
  @Override
  public FooParent2DaoSpring getDao()
  {
    return this.dao;
  }
}
