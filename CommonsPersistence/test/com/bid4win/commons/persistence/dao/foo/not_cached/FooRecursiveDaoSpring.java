package com.bid4win.commons.persistence.dao.foo.not_cached;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.entity.foo.not_cached.FooRecursive;
import com.bid4win.commons.persistence.entity.foo.not_cached.FooRecursive_;

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
   * Cette fonction permet d'ajouter des conditions pour la récupération de la
   * liste complète des entités
   * @param criteria {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#addConditionForAll(javax.persistence.criteria.CriteriaQuery)
   */
  @Override
  protected Root<FooRecursive> addConditionForAll(CriteriaQuery<FooRecursive> criteria)
  {
    CriteriaBuilder builder = this.getCriteriaBuilder();

    Root<FooRecursive> foo_ = super.addConditionForAll(criteria);
    Predicate condition = builder.isNull(foo_.get(FooRecursive_.parent));
    criteria.where(condition);
    return foo_;
  }
}
