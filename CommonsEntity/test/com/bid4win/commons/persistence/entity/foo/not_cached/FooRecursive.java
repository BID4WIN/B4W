package com.bid4win.commons.persistence.entity.foo.not_cached;

import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.OptimisticLock;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;

/**
 * Classe de test avec récursivité<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Entity
@Access(AccessType.FIELD)
@DiscriminatorValue("RECURSIVE")
public class FooRecursive extends Foo<FooRecursive>
{
  /** Parent de l'objet */
  @Transient
  private FooRecursive parent = null;
  /** Set d'enfants inclus dans l'objet parent */
  @Transient
  private Bid4WinSet<FooRecursive> childSet = new Bid4WinSet<FooRecursive>();

  /**
   * Constructeur
   */
  protected FooRecursive()
  {
    super();
  }

  /**
   * Constructeur
   * @param value Valeur de l'objet parent
   * @param date Date de l'objet parent
   */
  public FooRecursive(String value, Bid4WinDate date)
  {
    super(value, date);
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getFullRelationNodeList()
   */
  @Override
  protected Bid4WinList<Bid4WinRelationNode> getFullRelationNodeList()
  {
    return new Bid4WinList<Bid4WinRelationNode>(FooRecursive_Relations.NODE_PARENT,
                                                FooRecursive_Relations.NODE_CHILD);
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
    if(relation.equals(FooRecursive_Relations.RELATION_PARENT))
    {
      return this.getParent();
    }
    return null;
  }
  /**
   *
   * TODO A COMMENTER
   * @param relation {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getRelationSet(com.bid4win.commons.persistence.entity.Bid4WinRelation)
   */
  @Override
  protected Bid4WinSet<FooRecursive> getRelationSet(Bid4WinRelation relation)
  {
    if(relation.equals(FooRecursive_Relations.RELATION_CHILD))
    {
      return this.getChildSet();
    }
    return null;
  }

  /**
   *
   * TODO A COMMENTER
   * @param child TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public boolean addChild(FooRecursive child)
  {
    if(this.getChildSet().add(child))
    {
      child.setParent(this);
      return true;
    }
    return false;
  }
  /**
   *
   * TODO A COMMENTER
   * @param child TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public boolean removeChild(FooRecursive child)
  {
    if(this.getChildSet().remove(child))
    {
      child.setParent(null);
      return true;
    }
    return false;
  }
  /**
   * Getter du set d'enfants inclus dans l'objet parent
   * @return Le set d'enfants inclus dans l'objet parent
   */
  public Bid4WinSet<FooRecursive> getChildSet()
  {
    return this.childSet;
  }
  /**
   * Setter du set d'enfants inclus dans l'objet parent
   * @param childSet Set d'enfants inclus dans l'objet parent à positionner
   */
  private void setChildSet(Bid4WinSet<FooRecursive> childSet)
  {
    this.childSet = childSet;
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/

  /**
   * Getter du parent de l'objet
   * @return Le parent de l'objet
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @ManyToOne(fetch = FetchType.EAGER,
//             cascade = {})
//             cascade = {CascadeType.PERSIST})
             cascade = {CascadeType.MERGE})
//             cascade = {CascadeType.REFRESH})
//             cascade = {CascadeType.ALL})
  @JoinColumn(name = "PARENT_ID", nullable = true, unique = false)
  public FooRecursive getParent()
  {
    return this.parent;
  }
  /**
   * Setter du parent de l'objet
   * @param parent Parent de l'objet à positionner
   */
  private void setParent(FooRecursive parent)
  {
    this.parent = parent;
  }
  /**
   * Getter du set interne d'objets inclus dans l'objet
   * @return Le set interne d'objets inclus dans l'objet
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER,
//             cascade = {})
//             cascade = {CascadeType.PERSIST})
//               cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.MERGE})
//               cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//             cascade = {CascadeType.MERGE})
//             cascade = {CascadeType.REFRESH})
             cascade = {CascadeType.ALL}, orphanRemoval = true)
  // A partir d'Hibernate 4.1.1, l'entité parent n'est pas mise à jour par défaut
  @OptimisticLock(excluded = false)
  @SuppressWarnings(value = "unused")
  private Set<FooRecursive> getChildSetInternal()
  {
    return this.getChildSet().getInternal();
  }
  /**
   * Setter du set interne d'objets inclus dans l'objet
   * @param internalChildSet Set interne d'objets inclus dans l'objet à positionner
   */
  @SuppressWarnings(value = "unused")
  private void setChildSetInternal(Set<FooRecursive> internalChildSet)
  {
    this.setChildSet(new Bid4WinSet<FooRecursive>(internalChildSet, true));
  }
}
