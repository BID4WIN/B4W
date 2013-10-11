package com.bid4win.commons.persistence.entity;

import java.util.Set;

import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.comparator.Bid4WinComparator;

/**
 * Cette classe représente un stub des entités du projet<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinEntityStub extends Bid4WinEntity<Bid4WinEntityStub, String>
{
  /** Nom du stub */
  private String name = null;
  /** TODO A COMMENTER */
  private Bid4WinEntityStub parent = null;
  /** TODO A COMMENTER */
  private Bid4WinEntityStub son = null;
  /** TODO A COMMENTER */
  private Bid4WinEntityStub boss = null;
  /** TODO A COMMENTER */
  private Bid4WinSet<Bid4WinEntityStub> employeSet = new Bid4WinSet<Bid4WinEntityStub>();

  /**
   * Constructeur
   * @param id Identifiant du stub
   * @param name Nom du stub
   */
  public Bid4WinEntityStub(String id, String name)
  {
    super(id);
    this.name = name;
  }
  /**
   * Constructeur
   * @param name Nom du stub
   */
  public Bid4WinEntityStub(String name)
  {
    super();
    this.name = name;
  }

  /**
   * Getter du nom du stub
   * @return Le nom du stub
   */
  public String getName()
  {
    return this.name;
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public Bid4WinEntityStub getSon()
  {
    return this.son;
  }
  /**
   *
   * TODO A COMMENTER
   * @param son TODO A COMMENTER
   */
  protected void setSon(Bid4WinEntityStub son)
  {
    this.son = son;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public Bid4WinEntityStub getParent()
  {
    return this.parent;
  }
  /**
   *
   * TODO A COMMENTER
   * @param parent TODO A COMMENTER
   */
  protected void setParent(Bid4WinEntityStub parent)
  {
    this.parent = parent;
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public Bid4WinSet<Bid4WinEntityStub> getEmployeSet()
  {
    return this.employeSet;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public Bid4WinEntityStub getBoss()
  {
    return this.boss;
  }
  /**
   *
   * TODO A COMMENTER
   * @param boss TODO A COMMENTER
   */
  protected void setBoss(Bid4WinEntityStub boss)
  {
    this.boss = boss;
  }

  /**
   *
   * TODO A COMMENTER
   * @param toBeCompared {@inheritDoc}
   * @param identical {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#sameRelationNoneInternal(com.bid4win.commons.persistence.entity.Bid4WinEntity, boolean)
   */
  @Override
  protected boolean sameRelationNoneInternal(Bid4WinEntityStub toBeCompared, boolean identical)
  {
    return super.sameRelationNoneInternal(toBeCompared, identical) &&
           Bid4WinComparator.getInstance().equals(this.getName(), toBeCompared.getName());
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#renderRelationNone()
   */
  @Override
  public StringBuffer renderRelationNone()
  {
    StringBuffer buffer = super.renderRelationNone();
    return buffer.append(" NAME=" + this.getName());
  }

  /**
   *
   * TODO A COMMENTER
   * @param relation {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getRelationSimple(com.bid4win.commons.persistence.entity.Bid4WinRelation)
   */
  @Override
  protected Bid4WinEntity<?, ?> getRelationSimple(Bid4WinRelation relation)
  {
    if(relation.equals(Bid4WinEntityStub_Relations.RELATION_PARENT))
    {
      return this.getParent();
    }
    else if(relation.equals(Bid4WinEntityStub_Relations.RELATION_SON))
    {
      return this.getSon();
    }
    else if(relation.equals(Bid4WinEntityStub_Relations.RELATION_BOSS))
    {
      return this.getBoss();
    }
    return null;
  }
  /**
   *
   * TODO A COMMENTER
   * @param relation {@inheritDoc}
   * @param entity {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#setRelationSimple(com.bid4win.commons.persistence.entity.Bid4WinRelation, com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  protected void setRelationSimple(Bid4WinRelation relation, Bid4WinEntity<?, ?> entity)
  {
    if(relation.equals(Bid4WinEntityStub_Relations.RELATION_PARENT))
    {
      this.setParent((Bid4WinEntityStub)entity);
    }
    else if(relation.equals(Bid4WinEntityStub_Relations.RELATION_SON))
    {
      this.setSon((Bid4WinEntityStub)entity);
    }
    else if(relation.equals(Bid4WinEntityStub_Relations.RELATION_BOSS))
    {
      this.setBoss((Bid4WinEntityStub)entity);
    }
    else
    {
      super.setRelationSimple(relation, entity);
    }
  }

  /**
   *
   * TODO A COMMENTER
   * @param relation {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getRelationSet(com.bid4win.commons.persistence.entity.Bid4WinRelation)
   */
  @Override
  protected Set<? extends Bid4WinEntity<?, ?>> getRelationSet(Bid4WinRelation relation)
  {
    if(relation.equals(Bid4WinEntityStub_Relations.RELATION_EMPLOYE))
    {
      return this.getEmployeSet();
    }
    return super.getRelationSet(relation);
  }
}
