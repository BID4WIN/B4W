package com.bid4win.commons.persistence.request;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <ENTITY> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinCriteriaList<ENTITY extends Bid4WinEntity<?, ?>>
       extends Bid4WinCriteria<ENTITY>
{
  /** Critères à utiliser pour la requête */
  private Bid4WinCriteria<ENTITY>[] criterias = null;

  /**
   * Constructeur sans pagination et dont les critères sont exhaustifs
   * @param criterias Critères à utiliser pour la requête
   */
  public Bid4WinCriteriaList(Bid4WinCriteria<ENTITY> ... criterias)
  {
    this(true, criterias);
  }
  /**
   * Constructeur
   * @param exhaustive Boolean indiquant si les critères sont exhaustifs (AND) ou
   * non (OR)
   * @param criterias Critères à utiliser pour la requête
   */
  public Bid4WinCriteriaList(boolean exhaustive, Bid4WinCriteria<ENTITY> ... criterias)
  {
    super(exhaustive);
    this.setCriterias(criterias);
  }

  /**
   *
   * TODO A COMMENTER
   * @param builder {@inheritDoc}
   * @param from_ {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.request.Bid4WinCriteria#buildCondition(javax.persistence.criteria.CriteriaBuilder, javax.persistence.criteria.From)
   */
  @Override
  protected final Predicate buildCondition(CriteriaBuilder builder, From<?, ? extends ENTITY> from_)
  {
    Predicate[] conditions = new Predicate[this.getCriterias().length];
    int index = 0;
    for(Bid4WinCriteria<ENTITY> criteria : this.getCriterias())
    {
      conditions[index++] = criteria.buildCondition(builder, from_);
    }
    return this.combineConditions(builder, conditions);
  }

  /**
   *
   * TODO A COMMENTER
   * @param from_ {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.request.Bid4WinCriteria#getGroups(javax.persistence.criteria.From)
   */
  @Override
  protected Expression<?>[] getGroups(From<?, ? extends ENTITY> from_)
  {
    if(this.getCriterias().length == 0)
    {
      return null;
    }
    Bid4WinList<Expression<?>[]> groupsList = new Bid4WinList<Expression<?>[]>(
        this.getCriterias().length);
    int groupsNb = 0;
    for(Bid4WinCriteria<ENTITY> criteria : this.getCriterias())
    {
      Expression<?>[] group = criteria.getGroups(from_);
      if(group != null && group.length != 0)
      {
        groupsList.add(group);
        groupsNb += group.length;
      }
    }
    if(groupsNb == 0)
    {
      return null;
    }
    Expression<?>[] result = new Expression<?>[groupsNb];
    int index = 0;
    for(Expression<?>[] groups : groupsList)
    {
      for(Expression<?> group : groups)
      {
        result[index] = group;
      }
    }
    return result;
  }

  /**
   * Getter des critères à utiliser pour la requête
   * @return Les critères à utiliser pour la requête
   */
  private Bid4WinCriteria<ENTITY>[] getCriterias()
  {
    return this.criterias;
  }
  /**
   * Setter des critères à utiliser pour la requête
   * @param criterias Critères à utiliser pour la requête à positionner
   */
  @SuppressWarnings("unchecked")
  private void setCriterias(Bid4WinCriteria<ENTITY> ... criterias)
  {
    if(criterias == null)
    {
      criterias = new Bid4WinCriteria[0];
    }
    this.criterias = criterias;
  }
}
