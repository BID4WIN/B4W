package com.bid4win.commons.persistence.entity.foo.not_cached;

import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;

/**
 * Classe de test avec des objets enfants<BR>
 * <BR>
 * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
 * @param <CHILD> D�finition du type des enfants<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Entity
@Access(AccessType.FIELD)
@DiscriminatorValue("PARENT")
public abstract class FooParent<CLASS extends FooParent<CLASS, CHILD>,
                                CHILD extends FooChild<CHILD, CLASS>>
       extends Foo<CLASS>
{
  /** Map d'enfants inclus dans l'objet parent */
  @Transient
  private Bid4WinMap<String, CHILD> childMap = new Bid4WinMap<String, CHILD>();

  /**
   * Constructeur
   */
  protected FooParent()
  {
    super();
  }

  /**
   * Constructeur
   * @param value Valeur de l'objet parent
   * @param date Date de l'objet parent
   */
  protected FooParent(String value, Bid4WinDate date)
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
    return new Bid4WinList<Bid4WinRelationNode>(FooParent_Relations.NODE_CHILD);
  }
  /**
   *
   * TODO A COMMENTER
   * @param relation {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getRelationMap(com.bid4win.commons.persistence.entity.Bid4WinRelation)
   */
  @Override
  protected Bid4WinMap<String, CHILD> getRelationMap(Bid4WinRelation relation)
  {
    if(relation.equals(FooParent_Relations.RELATION_CHILD))
    {
      return this.getChildMap();
    }
    return null;
  }

  /**
   * Cette m�thode permet de r�cup�rer de la map l'enfant dont la valeur est pass�e
   * en argument
   * @param value Valeur de l'enfant � retirer dans la map
   * @return L'enfant retir� dans la map
   */
  public CHILD getChild(String value)
  {
    return this.getChildMap().get(value);
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public int getChildNb()
  {
    return this.getChildMap().size();
  }
  /**
   * Cette m�thode permet d'ajouter l'enfant en argument � la map
   * @param child Enfant � ajouter � la map
   * @return CHILD Enfant dont l'enfant � ajouter a potentiellement pris la place
   */
  @SuppressWarnings("unchecked")
  public CHILD putChild(CHILD child)
  {
    CHILD previous = this.getChildMap().put(child.getValue(), child);
    if(previous != null)
    {
      previous.setParent(null);
    }
    child.setParent((CLASS)this);
    return previous;
  }
  /**
   * Cette m�thode permet de retirer l'enfant en argument de la map
   * @param child Enfant � retirer de la map
   * @return L'enfant retir� de la map
   */
  public CHILD removeChild(CHILD child)
  {
    return this.removeChild(child.getValue());
  }
  /**
   * Cette m�thode permet de retirer de la map l'enfant dont la valeur est pass�e
   * en argument
   * @param value Valeur de l'enfant � retirer de la map
   * @return L'enfant retir� de la map
   */
  public CHILD removeChild(String value)
  {
    CHILD child = this.getChildMap().remove(value);
    child.setParent(null);
    return child;
  }
  /**
   * Getter de la map d'enfants inclus dans l'objet parent
   * @return La map d'enfants inclus dans l'objet parent
   */
  protected Bid4WinMap<String, CHILD> getChildMap()
  {
    return this.childMap;
  }
  /**
   * Setter de la map d'enfants inclus dans l'objet parent
   * @param childMap Map d'enfants inclus dans l'objet parent � positionner
   */
  private void setChildMap(Bid4WinMap<String, CHILD> childMap)
  {
    this.childMap = childMap;
  }

  /**
   * Getter de la map interne d'objets inclus dans l'objet
   * @return La map interne d'objets inclus dans l'objet
   */
  protected Map<String, CHILD> getChildMapInternal()
  {
    return this.getChildMap().getInternal();
  }
  /**
   * Setter de la map interne d'objets inclus dans l'objet
   * @param internalChildMap Map interne d'objets inclus dans l'objet � positionner
   */
  protected void setChildMapInternal(Map<String, CHILD> internalChildMap)
  {
    this.setChildMap(new Bid4WinMap<String, CHILD>(internalChildMap, true));
  }
}
