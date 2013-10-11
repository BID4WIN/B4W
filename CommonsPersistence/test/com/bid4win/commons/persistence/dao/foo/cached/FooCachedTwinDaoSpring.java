package com.bid4win.commons.persistence.dao.foo.cached;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.entity.foo.cached.FooCachedTwin;
import com.bid4win.commons.persistence.entity.foo.cached.FooCachedTwin_;

/**
 * DAO pour les entités de la classe FooCachedTwin<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class FooCachedTwinDaoSpring extends FooCachedDaoSpring<FooCachedTwin>
{
  /** Défini la liste de noeuds correspondant aux relations des deux jumeaux */
/*  private final static Bid4WinList<Bid4WinRelationNode> NODE_LIST =
      new Bid4WinList<Bid4WinRelationNode>(FooCachedTwin_.NODE_TWIN);*/

  /**
   * Constructeur
   */
  protected FooCachedTwinDaoSpring()
  {
    super(FooCachedTwin.class);
  }

  /**
   * Cette fonction permet de récupérer l'unique entité en fonction de son identifiant
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#getById(java.lang.Object)
   */
  @Override
  public FooCachedTwin getById(Integer id) throws PersistenceException
  {
    FooCachedTwin twin = super.getById(id);
    return twin.loadRelation(/*NODE_LIST*/);
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
  public FooCachedTwin findOneByValue(String value) throws PersistenceException
  {
    FooCachedTwin twin = super.findOneByValue(value);
    return (twin != null ? twin.loadRelation(/*NODE_LIST*/) : null);
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
  public Bid4WinList<FooCachedTwin> findListByValue(String value) throws PersistenceException
  {
    Bid4WinList<FooCachedTwin> twinList = super.findListByValue(value);
    for(FooCachedTwin twin : twinList)
    {
      twin.loadRelation(/*NODE_LIST*/);
    }
    return twinList;
  }

  /**
   * Cette fonction permet de récupérer l'unique entité liée au jumeau dont l'
   * identifiant est en argument
   * @param twinId Identifiant du jumeau de l'entité unique à récupérer
   * @return Entité récupérée
   * @throws PersistenceException Si une exception non attendue est levée
   */
  public FooCachedTwin getByTwin(Integer twinId) throws PersistenceException
  {
    return super.getOne(this.getCriteriaForTwin(twinId)).loadRelation(/*NODE_LIST*/);
  }
  /**
   * Cette fonction permet de récupérer l'unique entité liée au jumeau dont l'
   * identifiant est en argument
   * @param twinId Identifiant du jumeau de l'entité unique à récupérer
   * @return Entité récupérée
   * @throws PersistenceException Si une exception non attendue est levée
   */
  public FooCachedTwin findOneByTwin(Integer twinId) throws PersistenceException
  {
    FooCachedTwin twin = super.findOne(this.getCriteriaForTwin(twinId));
    return (twin != null ? twin.loadRelation(/*NODE_LIST*/) : null);
  }
  /**
   * Cette méthode permet de construire les critères permettant de rechercher des
   * entités en fonction de l'identifiant de leur jumeau
   * @param twinId Identifiant du jumeau de l'entité unique à récupérer
   * @return Les critères permettant de rechercher des entités en fonction de l'
   * identifiant de leur jumeau
   */
  protected CriteriaQuery<FooCachedTwin> getCriteriaForTwin(Integer twinId)
  {
    CriteriaBuilder builder = this.getCriteriaBuilder();

    CriteriaQuery<FooCachedTwin> criteria = this.createCriteria();
    Root<FooCachedTwin> foo_ = criteria.from(this.getEntityClass());
    Path<FooCachedTwin> twin_ = foo_.get(FooCachedTwin_.twin);
    Path<Integer> twinId_ = twin_.get(FooCachedTwin_.id);

    Predicate condition = builder.equal(twinId_, twinId);
    return criteria.where(condition);
  }
}
