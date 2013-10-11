package com.bid4win.commons.persistence.entity.foo.not_cached;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.comparator.Bid4WinComparator;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;

/**
 * Classe de test enfant<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * @param <PARENT> Définition du type du parent<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Entity
@Access(AccessType.FIELD)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", discriminatorType = DiscriminatorType.STRING, length = 10)
@DiscriminatorValue("DEFAULT")
public class FooChild<CLASS extends FooChild<CLASS, PARENT>, PARENT extends FooParent<PARENT, CLASS>>
       extends Bid4WinEntity<CLASS, String>
{
  /** Valeur de l'objet */
  @Transient
  private String value = null;
  /** Date de l'objet */
  @Transient
  private Bid4WinDate date = null;
  /** Parent de l'objet */
  @Transient
  private PARENT parent = null;

  /**
   * Constructeur
   */
  protected FooChild()
  {
    super();
  }
  /**
   * Constructeur
   * @param id Identifiant de l'objet
   * @param value Valeur de l'objet
   */
  public FooChild(String id, String value)
  {
    super(id);
    this.setValue(value);
    this.setDate(new Bid4WinDate());
  }

  /**
   * Définition de l'équivalence de la classe de test enfant
   * @param toBeCompared {@inheritDoc}
   * @param identical {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#sameRelationNoneInternal(com.bid4win.commons.persistence.entity.Bid4WinEntity, boolean)
   */
  @Override
  protected boolean sameRelationNoneInternal(CLASS toBeCompared, boolean identical)
  {
    return super.sameRelationNoneInternal(toBeCompared, identical) &&
           Bid4WinComparator.getInstance().equals(this.getValue(), toBeCompared.getValue()) &&
           Bid4WinComparator.getInstance().equals(this.getDate(), toBeCompared.getDate());
  }
  /**
   * Permet de transformer l'objet en une chaîne de caractères lisible
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#renderRelationNone()
   */
  @Override
  public StringBuffer renderRelationNone()
  {
    StringBuffer buffer = super.renderRelationNone();
    buffer.append(" Value=").append(this.getValue());
    return buffer;
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
    return new Bid4WinList<Bid4WinRelationNode>(FooChild_Relations.NODE_PARENT);
  }
  /**
   *
   * TODO A COMMENTER
   * @param relation {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getRelationSimple(com.bid4win.commons.persistence.entity.Bid4WinRelation)
   */
  @Override
  protected PARENT getRelationSimple(Bid4WinRelation relation)
  {
    if(relation.equals(FooChild_Relations.RELATION_PARENT))
    {
      return this.getParent();
    }
    return null;
  }

  /**
   * Getter du parent de l'objet
   * @return Le parent de l'objet
   */
  public PARENT getParent()
  {
    return this.parent;
  }
  /**
   * Setter du parent de l'objet
   * @param parent Parent de l'objet à positionner
   */
  protected void setParent(PARENT parent)
  {
    this.parent = parent;
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de l'identifiant de l'objet
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getId()
   */
  @Override
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Id()
  @Column(name = "ID", length = 20, nullable = false, unique = true)
  //@GeneratedValue(strategy = GenerationType.AUTO)
  public String getId()
  {
    return super.getId();
  }

  /**
   * Getter de la valeur de l'objet
   * @return La valeur de l'objet
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "VALUE", length = 20, nullable = false, unique = false)
  public String getValue()
  {
    return this.value;
  }
  /**
   * Setter de la valeur de l'objet
   * @param value Valeur de l'objet à positionner
   */
  public void setValue(String value)
  {
    this.value = value;
  }

  /**
   * Getter de la date de l'objet
   * @return La date de l'objet
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "DATE_", nullable = false, unique = false)
  public Bid4WinDate getDate()
  {
    return this.date;
  }
  /**
   * Setter de la date de l'objet
   * @param date Date de l'objet à positionner
   */
  public void setDate(Bid4WinDate date)
  {
    this.date= date;
  }
}
