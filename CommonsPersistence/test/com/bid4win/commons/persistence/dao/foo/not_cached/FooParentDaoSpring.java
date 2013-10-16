package com.bid4win.commons.persistence.dao.foo.not_cached;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.entity.account.security.Role;
import com.bid4win.commons.persistence.entity.foo.not_cached.FooParent;
import com.bid4win.commons.persistence.request.Bid4WinCriteria;
import com.bid4win.commons.persistence.request.Bid4WinPagination;
import com.bid4win.commons.persistence.request.Bid4WinResult;

/**
 * DAO pour les entités de la classe FooParent<BR>
 * <BR>
 * @param <PARENT> Entité parent gérée par le DAO<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class FooParentDaoSpring<PARENT extends FooParent<PARENT, ?>> extends FooDaoSpring<PARENT>
{
  /**
   * Constructeur
   * @param entityClass Class de l'entité parent gérée par le DAO
   */
  protected FooParentDaoSpring(Class<PARENT> entityClass)
  {
    super(entityClass);
  }
  /** Défini le noeud de relation entre l'entité parent et ses enfants */
/*  private final static Bid4WinRelationNode NODE_CHILD =
      new Bid4WinRelationNode(FooParent_.RELATION_CHILD);
  /** Défini la liste de noeuds correspondant aux relations de l'entité parent */
/*  private final static Bid4WinList<Bid4WinRelationNode> NODE_LIST =
      new Bid4WinList<Bid4WinRelationNode>(NODE_CHILD);*/

  /**
   * Cette fonction permet de récupérer l'unique entité en fonction de son identifiant
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#getById(java.lang.Object)
   */
  @Override
  public PARENT getById(Long id) throws PersistenceException
  {
    PARENT parent = super.getById(id);
    return parent.loadRelation(/*NODE_LIST*/);
  }

  /**
   *
   * TODO A COMMENTER
   * @param criteria {@inheritDoc}
   * @param pagination {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.foo.FooAbstractDaoSpring#findList(com.bid4win.commons.persistence.request.Bid4WinCriteria, com.bid4win.commons.persistence.request.Bid4WinPagination)
   */
  @Override
  public Bid4WinResult<PARENT> findList(Bid4WinCriteria<PARENT> criteria, Bid4WinPagination<PARENT> pagination) throws PersistenceException
  {
    Bid4WinResult<PARENT> parentList = super.findList(criteria, pagination);
    for(PARENT parent : parentList)
    {
      parent.loadRelation(/*NODE_LIST*/);
    }
    return parentList;
  }

  /**
   *
   * TODO A COMMENTER
   * @param value {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.foo.FooAbstractDaoSpring#findOneByValue(java.lang.String)
   */
  @Override
  public PARENT findOneByValue(String value) throws PersistenceException
  {
    PARENT parent = super.findOneByValue(value);
    return (parent != null ? parent.loadRelation(/*NODE_LIST*/) : null);
  }
  /**
   *
   * TODO A COMMENTER
   * @param value {@inheritDoc}
   * @param pagination {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.foo.FooAbstractDaoSpring#findListByValue(java.lang.String, com.bid4win.commons.persistence.request.Bid4WinPagination)
   */
  @Override
  public Bid4WinResult<PARENT> findListByValue(String value, Bid4WinPagination<PARENT> pagination) throws PersistenceException
  {
    Bid4WinResult<PARENT> parentList = super.findListByValue(value, pagination);
    for(PARENT parent : parentList)
    {
      parent.loadRelation(/*NODE_LIST*/);
    }
    return parentList;
  }
  /**
   *
   * TODO A COMMENTER
   * @param role {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.foo.FooAbstractDaoSpring#findListByRole(com.bid4win.commons.persistence.entity.account.security.Role)
   */
  @Override
  public Bid4WinResult<PARENT> findListByRole(Role role) throws PersistenceException
  {
    Bid4WinResult<PARENT> parentList = super.findListByRole(role);
    for(PARENT parent : parentList)
    {
      parent.loadRelation(/*NODE_LIST*/);
    }
    return parentList;
  }
  /**
   *
   * TODO A COMMENTER
   * @param date {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.foo.FooAbstractDaoSpring#findListByDate(com.bid4win.commons.core.Bid4WinDate)
   */
  @Override
  public Bid4WinResult<PARENT> findListByDate(Bid4WinDate date) throws PersistenceException
  {
    Bid4WinResult<PARENT> parentList = super.findListByDate(date);
    for(PARENT parent : parentList)
    {
      parent.loadRelation(/*NODE_LIST*/);
    }
    return parentList;
  }
}
