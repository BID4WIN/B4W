package com.bid4win.commons.persistence.entity.foo;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.comparator.Bid4WinComparator;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.core.EmbeddableDate;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <CLASS> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
//Annotation pour la persistence
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class FooAbstract<CLASS extends FooAbstract<CLASS>>
       extends Bid4WinEntity<CLASS, Integer>
{
  /** Valeur de l'objet */
  @Transient
  private String value = null;
  /** Date de l'objet */
  @Transient
  private Bid4WinDate date = null;
  /** Date de l'objet */
  @Transient
  private EmbeddableDate embeddedDate = null;

  /**
   * Constructeur
   */
  protected FooAbstract()
  {
    super();
  }
  /**
   * Constructeur
   * @param value Valeur de l'objet
   * @param date Date de l'objet
   * @throws IllegalArgumentException TODO A COMMENTER
   */
  public FooAbstract(String value, Bid4WinDate date) throws IllegalArgumentException
  {
    this();
    this.setValue(value);
    this.defineDate(date);
  }

  /**
   * Définition de l'équivalence de la classe de test simple
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
  protected StringBuffer renderRelationNone()
  {
    StringBuffer buffer = super.renderRelationNone();
    buffer.append(" Value=").append(this.getValue());
    buffer.append(" Date=").append(this.getDate().formatDDIMMIYYYY_HHIMMISSISSS());
    return buffer;
  }
  /**
   *
   * TODO A COMMENTER
   * @param date TODO A COMMENTER
   * @throws IllegalArgumentException TODO A COMMENTER
   */
  public void defineDate(Bid4WinDate date) throws IllegalArgumentException
  {
    // Vérifie la protection de l'objet courant
    this.checkProtection();
    this.setEmbeddedDate(new EmbeddableDate(date));
    this.setDate(this.getEmbeddedDate().getDate());
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
  @Column(name = "ID", length = 10, nullable = false, unique = true)
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Integer getId()
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
  private void setDate(Bid4WinDate date)
  {
    this.date= date;
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Embedded()
  public EmbeddableDate getEmbeddedDate()
  {
    return this.embeddedDate;
  }
  /**
   *
   * TODO A COMMENTER
   * @param embeddedDate TODO A COMMENTER
   */
  private void setEmbeddedDate(EmbeddableDate embeddedDate)
  {
    this.embeddedDate = embeddedDate;
  }
}
