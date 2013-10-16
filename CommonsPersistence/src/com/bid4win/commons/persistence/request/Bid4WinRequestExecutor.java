package com.bid4win.commons.persistence.request;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <ENTITY> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinRequestExecutor<ENTITY extends Bid4WinEntity<?, ?>>
{
  /** TODO A COMMENTER */
  private EntityManager entityManager = null;
  /** TODO A COMMENTER */
  private Class<ENTITY> entityClass = null;
  /** TODO A COMMENTER */
  private CriteriaBuilder builder = null;
  /** TODO A COMMENTER */
  private Bid4WinPagination<ENTITY> pagination = null;
  /** TODO A COMMENTER */
  private CriteriaQuery<ENTITY> select = null;
  /** TODO A COMMENTER */
  private Root<ENTITY> from_ = null;
  /** TODO A COMMENTER */
  private Predicate condition = null;
  /** TODO A COMMENTER */
  private Expression<?>[] groups = null;

  /**
   *
   * TODO A COMMENTER
   * @param entityManager TODO A COMMENTER
   * @param entityClass TODO A COMMENTER
   * @param criteria TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  public Bid4WinRequestExecutor(EntityManager entityManager,
                                Class<ENTITY> entityClass,
                                Bid4WinCriteria<ENTITY> criteria)
         throws PersistenceException
  {
    try
    {
      this.entityManager = entityManager;
      this.entityClass = entityClass;
      this.builder = this.getEntityManager().getCriteriaBuilder();
      this.select = this.getBuilder().createQuery(this.getEntityClass());
      this.from_ = this.getSelect().from(this.getEntityClass());
      if(criteria != null)
      {
        this.condition = criteria.buildCondition(this.getBuilder(), this.getFrom_());
        this.groups = criteria.getGroups(this.getFrom_());
      }
    }
    catch(RuntimeException ex)
    {
      throw new PersistenceException(ex);
    }
  }
  /**
   *
   * TODO A COMMENTER
   * @param entityManager TODO A COMMENTER
   * @param entityClass TODO A COMMENTER
   * @param criteria TODO A COMMENTER
   * @param pagination TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  public Bid4WinRequestExecutor(EntityManager entityManager,
                                Class<ENTITY> entityClass,
                                Bid4WinCriteria<ENTITY> criteria,
                                Bid4WinPagination<ENTITY> pagination)
         throws PersistenceException
  {
    this(entityManager, entityClass, criteria);
    this.pagination = pagination;
  }

  /**
   *
   * TODO A COMMENTER
   * @param defaultOrders TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  public Bid4WinResult<ENTITY> execute(Bid4WinOrder<? super ENTITY, ?> ... defaultOrders)
         throws PersistenceException
  {
    try
    {
      // On crée la requête de selection
      this.createSelect();
      // On ordonne la requête de selection si nécessaire
      this.orderSelect(defaultOrders);
      // Groupe les tables demandées
      this.groupSelect();
      // On crée la requête à executer
      TypedQuery<ENTITY> query = this.getEntityManager().createQuery(this.getSelect());
      // On pagine la requête à executer si nécessaire
      this.paginateQuery(query);
      // On execute la requête
      List<ENTITY> result = query.getResultList();
      return new Bid4WinResult<ENTITY>(result, this.getTotalNb(result.size()));
    }
    catch(RuntimeException ex)
    {
      throw new PersistenceException(ex);
    }
  }

  /**
   *
   * TODO A COMMENTER
   */
  protected void createSelect()
  {
    // On récupère la requête de selection et on ajoute les conditions
    this.createSelect(this.getSelect());
  }
  /**
   *
   * TODO A COMMENTER
   * @param defaultOrders TODO A COMMENTER
   */
  protected void orderSelect(Bid4WinOrder<? super ENTITY, ?> ... defaultOrders)
  {
    // Impossible d'ordonner la requête de selection
    if(defaultOrders.length == 0 &&
       (this.getPagination() == null ||
        this.getPagination().getOrders().length == 0))
    {
      return;
    }
    // On ordonne la requête de selection
     Order[] orders;
     if(this.getPagination() != null && this.getPagination().getOrders().length != 0)
     {
       defaultOrders = this.getPagination().getOrders();
     }
     orders = new Order[defaultOrders.length];
     int index = 0;
     for(Bid4WinOrder<? super ENTITY, ?> order : defaultOrders)
     {
       orders[index++] = order.buildOrder(this.getBuilder(), this.getFrom_());
     }
    this.getSelect().orderBy(orders);
  }
  /**
   *
   * TODO A COMMENTER
   * @param query TODO A COMMENTER
   */
  protected void paginateQuery(TypedQuery<ENTITY> query)
  {
    // Impossible de paginer la requête
    if(this.getPagination() == null)
    {
      return;
    }
    Bid4WinRange range = this.getPagination().getRange();
    // Un départ de pagination est demandé
    if(range.hasIndex())
    {
      query.setFirstResult(range.getIndex());
    }
    // Une limite de pagination est demandée
    if(range.hasLimit())
    {
      query.setMaxResults(range.getLimit());
    }
  }
  /**
   *
   * TODO A COMMENTER
   */
  protected void groupSelect()
  {
    this.groupSelect(this.getSelect());
  }

  /**
   *
   * TODO A COMMENTER
   * @param partialNb TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public long getTotalNb(int partialNb)
  {
    if(this.getPagination() == null)
    {
      return partialNb;
    }
    Bid4WinRange range = this.getPagination().getRange();
    // Si l'index est positionné trop haut ou si est limité par le nombre, il faut
    // compter le nombre total
    if((range.hasIndex() && partialNb == 0) ||
       (range.hasLimit() && partialNb == range.getLimit()))
    {
      // On crée la requête de comptage
      CriteriaQuery<Long> count = this.createCount();
      // On crée la requête à executer
      TypedQuery<Long> query = this.getEntityManager().createQuery(count);
      // On execute la requête et retourne son résultat
      return query.getSingleResult();
    }
    // On a remonté moins que le nombre demandé, on a donc atteint la limite de la pagination
    return range.getIndex() + partialNb;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected CriteriaQuery<Long> createCount()
  {
    CriteriaQuery<Long> count = this.getBuilder().createQuery(Long.class);
    count.from(this.getEntityClass());
    count.select(this.getBuilder().count(this.getFrom_()));
    this.createSelect(count);
    this.groupSelect(count);
    return count;
  }

  /**
   *
   * TODO A COMMENTER
   * @param select TODO A COMMENTER
   */
  protected void createSelect(CriteriaQuery<?> select)
  {
    // On ajoute les conditions
    if(this.getCondition() != null)
    {
      select.where(this.getCondition());
    }
  }
  /**
   *
   * TODO A COMMENTER
   * @param select TODO A COMMENTER
   */
  protected void groupSelect(CriteriaQuery<?> select)
  {
    if(this.groups != null)
    {
      for(Expression<?> group : this.groups)
      {
        select.groupBy(group);
      }
    }
  }


  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected CriteriaBuilder getBuilder()
  {
    return this.builder;
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected EntityManager getEntityManager()
  {
    return this.entityManager;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  private Class<ENTITY> getEntityClass()
  {
    return this.entityClass;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  private Bid4WinPagination<ENTITY> getPagination()
  {
    return this.pagination;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  private CriteriaQuery<ENTITY> getSelect()
  {
    return this.select;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  private Root<ENTITY> getFrom_()
  {
    return this.from_;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  private Predicate getCondition()
  {
    return this.condition;
  }
}
