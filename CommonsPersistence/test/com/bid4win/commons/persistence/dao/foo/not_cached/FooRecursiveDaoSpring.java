package com.bid4win.commons.persistence.dao.foo.not_cached;

import org.springframework.transaction.annotation.Transactional;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.entity.foo.not_cached.FooRecursive;
import com.bid4win.commons.persistence.entity.foo.not_cached.FooRecursive_Fields;
import com.bid4win.commons.persistence.request.Bid4WinCriteria;
import com.bid4win.commons.persistence.request.data.Bid4WinDataNullable;

/**
 * DAO pour les entités de la classe FooRecursive<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class FooRecursiveDaoSpring extends FooDaoSpring<FooRecursive>
{
  /**
   * Constructeur
   */
  protected FooRecursiveDaoSpring()
  {
    super(FooRecursive.class);
  }

  /**
   *
   * TODO A COMMENTER
   * @param entity {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.foo.FooAbstractDaoSpring#add(com.bid4win.commons.persistence.entity.foo.FooAbstract)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public FooRecursive add(FooRecursive entity) throws PersistenceException
  {
    super.add(entity);
    if(entity.getParent() != null)
    {
      this.update(entity.getParent());
    }
//    return this.refresh(entity);
    return this.getById(entity.getId());
  }
  /**
   *
   * TODO A COMMENTER
   * @param entity {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.foo.FooAbstractDaoSpring#remove(com.bid4win.commons.persistence.entity.foo.FooAbstract)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public FooRecursive remove(FooRecursive entity) throws PersistenceException
  {
    FooRecursive result = super.remove(entity);
    if(entity.getParent() != null)
    {
      this.update(entity.getParent());
    }
    return result;
  }

  /**
   *
   * TODO A COMMENTER
   * @param child TODO A COMMENTER
   * @param newParent TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public void switchTo(FooRecursive child, FooRecursive newParent) throws PersistenceException
  {
    FooRecursive oldParent = child.getParent();
    this.update(oldParent);
    oldParent.removeChild(child);
    //oldParent.addChild(child);
    newParent.addChild(child);
    this.update(oldParent);
    this.update(newParent);
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#getCriteriaForAll()
   */
  @Override
  protected Bid4WinCriteria<FooRecursive> getCriteriaForAll()
  {
    return new Bid4WinDataNullable<FooRecursive, FooRecursive>(FooRecursive_Fields.PARENT);
  }

  /**
   * Cette fonction permet d'ajouter des conditions pour la récupération de la
   * liste complète des entités
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#getRequestForAll()
   */
/*  @SuppressWarnings("unchecked")
  @Override
  protected Bid4WinRequest<FooRecursive> getRequestForAll()
  {
/*    Bid4WinRequest<FooRecursive> request = super.getRequestForAll();
    Bid4WinCriteria<FooRecursive> data[] = new Bid4WinCriteria[request.getCriteriaSet().size() + 1];
    request.getCriteriaSet().toArray(data);
    data[data.length - 1] = new FooRecursiveParentNullData();*/
  /*  return Bid4WinRequest.copyRequest(super.getRequestForAll(),
                                      new Bid4WinData<FooRecursive, FooRecursive>(FooRecursive_.PARENT));
        //new FooRecursiveParentNullData());
  }*/
}
