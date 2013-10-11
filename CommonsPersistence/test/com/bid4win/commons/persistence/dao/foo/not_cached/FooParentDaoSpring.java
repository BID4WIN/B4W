package com.bid4win.commons.persistence.dao.foo.not_cached;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.entity.foo.not_cached.FooParent;

/**
 * DAO pour les entit�s de la classe FooParent<BR>
 * <BR>
 * @param <PARENT> Entit� parent g�r�e par le DAO<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class FooParentDaoSpring<PARENT extends FooParent<PARENT, ?>> extends FooDaoSpring<PARENT>
{
  /** D�fini le noeud de relation entre l'entit� parent et ses enfants */
/*  private final static Bid4WinRelationNode NODE_CHILD =
      new Bid4WinRelationNode(FooParent_.RELATION_CHILD);
  /** D�fini la liste de noeuds correspondant aux relations de l'entit� parent */
/*  private final static Bid4WinList<Bid4WinRelationNode> NODE_LIST =
      new Bid4WinList<Bid4WinRelationNode>(NODE_CHILD);*/

  /**
   * Cette fonction permet de r�cup�rer l'unique entit� en fonction de son identifiant
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#getById(java.lang.Object)
   */
  @Override
  public PARENT getById(Integer id) throws PersistenceException
  {
    PARENT parent = super.getById(id);
    return parent.loadRelation(/*NODE_LIST*/);
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
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.foo.FooAbstractDaoSpring#findListByValue(java.lang.String)
   */
  @Override
  public Bid4WinList<PARENT> findListByValue(String value) throws PersistenceException
  {
    Bid4WinList<PARENT> parentList = super.findListByValue(value);
    for(PARENT parent : parentList)
    {
      parent.loadRelation(/*NODE_LIST*/);
    }
    return parentList;
  }
  /**
   * Constructeur
   * @param entityClass Class de l'entit� parent g�r�e par le DAO
   */
  protected FooParentDaoSpring(Class<PARENT> entityClass)
  {
    super(entityClass);
  }
}
