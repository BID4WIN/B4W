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
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;

/**
 * Classe de test avec des objets enfants<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * @param <CHILD> Définition du type des enfants<BR>
 * <BR>
 * @author Emeric Fillâtre
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
  private Bid4WinMap<String, CHILD> childMapInternal = new Bid4WinMap<String, CHILD>(this.getProtection());

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
      return this.getChildMapInternal();
    }
    return null;
  }

  /**
   * Cette méthode permet de récupérer de la map l'enfant dont la valeur est passée
   * en argument
   * @param value Valeur de l'enfant à retirer dans la map
   * @return L'enfant retiré dans la map
   */
  public CHILD getChild(String value)
  {
    return this.getChildMapInternal().get(value);
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public int getChildNb()
  {
    return this.getChildMapInternal().size();
  }
  /**
   * Cette méthode permet d'ajouter l'enfant en argument à la map
   * @param child Enfant à ajouter à la map
   * @return CHILD Enfant dont l'enfant à ajouter a potentiellement pris la place
   */
  @SuppressWarnings("unchecked")
  public CHILD putChild(CHILD child)
  {
    CHILD previous = this.getChildMapInternal().put(child.getValue(), child);
    if(previous != null)
    {
      previous.setParent(null);
    }
    child.setParent((CLASS)this);
    return previous;
  }
  /**
   * Cette méthode permet de retirer l'enfant en argument de la map
   * @param child Enfant à retirer de la map
   * @return L'enfant retiré de la map
   */
  public CHILD removeChild(CHILD child)
  {
    return this.removeChild(child.getValue());
  }
  /**
   * Cette méthode permet de retirer de la map l'enfant dont la valeur est passée
   * en argument
   * @param value Valeur de l'enfant à retirer de la map
   * @return L'enfant retiré de la map
   */
  public CHILD removeChild(String value)
  {
    CHILD child = this.getChildMapInternal().remove(value);
    child.setParent(null);
    return child;
  }
  /**
   * Getter de la map d'enfants inclus dans l'objet parent
   * @return La map d'enfants inclus dans l'objet parent
   */
  private Bid4WinMap<String, CHILD> getChildMapInternal()
  {
    return this.childMapInternal;
  }
  /**
   * Setter de la map interne d'enfants inclus dans l'objet
   * @param childMap Map d'enfants inclus dans l'objet à positionner
   * @throws ProtectionException Si l'objet est protégé contre les modifications
   */
  protected void setChildMapInternal(Map<String, CHILD> childMap) throws ProtectionException
  {
    this.checkProtection();
    this.childMapInternal = new Bid4WinMap<String, CHILD>(childMap, true);
    this.childMapInternal.protectFromExisting();
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de la map d'enfants inclus dans l'objet pour la persistence
   * @return La  map d'enfants inclus dans l'objet pour la persistence
   */
  protected Map<String, CHILD> getChildMap()
  {
    return this.getChildMapInternal().getInternal();
  }
  /**
   * Setter de la map d'enfants inclus dans l'objet
   * @param childMap Map d'enfants inclus dans l'objet à positionner
   */
  @SuppressWarnings("unused")
  private void setChildMap(Map<String, CHILD> childMap)
  {
    this.setChildMapInternal(childMap);
  }
}
