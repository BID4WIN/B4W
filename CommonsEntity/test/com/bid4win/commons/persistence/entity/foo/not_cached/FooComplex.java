package com.bid4win.commons.persistence.entity.foo.not_cached;

import java.util.List;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OrderColumn;
import javax.persistence.Transient;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.comparator.Bid4WinComparator;
import com.bid4win.commons.core.renderer.Bid4WinObjectCollectionRenderer;

/**
 * Classe de test avec des objets inclus<BR>
 * <BR><BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Entity
@Access(AccessType.FIELD)
@DiscriminatorValue("COMPLEX")
public class FooComplex extends Foo<FooComplex>
{
  /** Objet inclus dans l'objet */
  @Transient
  private FooEmbeddable embedded = null;
  /** Set d'objets inclus dans l'objet */
  @Transient
  private Bid4WinSet<FooEmbeddable> embeddedSet = new Bid4WinSet<FooEmbeddable>();
  /** Liste d'objets inclus dans l'objet */
  @Transient
  private Bid4WinList<FooEmbeddable> embeddedList = new Bid4WinList<FooEmbeddable>();

  /**
   * Constructeur
   */
  protected FooComplex()
  {
    super();
  }

  /**
   * Constructeur
   * @param value Valeur de l'objet
   * @param date Date de l'objet
   * @param embedded Objet inclus dans l'objet
   */
  public FooComplex(String value, Bid4WinDate date, FooEmbeddable embedded)
  {
    super(value, date);
    this.setEmbedded(embedded);
  }

  /**
   * Définition de l'équivalence de la classe de test avec des objets inclus
   * @param toBeCompared {@inheritDoc}
   * @param identical {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.foo.FooAbstract#sameRelationNoneInternal(com.bid4win.commons.persistence.entity.foo.FooAbstract, boolean)
   */
  @Override
  protected boolean sameRelationNoneInternal(FooComplex toBeCompared, boolean identical)
  {
    return super.sameRelationNoneInternal(toBeCompared, identical) &&
           Bid4WinComparator.getInstance().equals(this.getEmbedded(), toBeCompared.getEmbedded()) &&
           Bid4WinComparator.getInstance().equals(this.getEmbeddedSet(), toBeCompared.getEmbeddedSet()) &&
           Bid4WinComparator.getInstance().equals(this.getEmbeddedList(), toBeCompared.getEmbeddedList());
  }
  /**
   * Permet de transformer l'objet en une chaîne de caractères lisible
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.foo.not_cached.Foo#renderRelationNone()
   */
  @Override
  public StringBuffer renderRelationNone()
  {
    StringBuffer buffer = super.renderRelationNone();
    buffer.append(" Embedded=[").append(this.getEmbedded().render()).append("]");
    buffer.append(" EmbeddedSet=").append(
        Bid4WinObjectCollectionRenderer.getInstanceObjectCollection().render(this.getEmbeddedSet()));
    buffer.append(" EmbeddedList=").append(
        Bid4WinObjectCollectionRenderer.getInstanceObjectCollection().render(this.getEmbeddedList()));
    return buffer;
  }

  /**
   * Getter du set d'objets inclus dans l'objet
   * @return Le set d'objets inclus dans l'objet
   */
  public Bid4WinSet<FooEmbeddable> getEmbeddedSet()
  {
    return this.embeddedSet;
  }
  /**
   * Setter du set d'objets inclus dans l'objet
   * @param embeddedSet Set d'objets inclus dans l'objet à positionner
   */
  private void setEmbeddedSet(Bid4WinSet<FooEmbeddable> embeddedSet)
  {
    this.embeddedSet = embeddedSet;
  }
  /**
   * Getter de la liste d'objets inclus dans l'objet
   * @return La liste d'objets inclus dans l'objet
   */
  public Bid4WinList<FooEmbeddable> getEmbeddedList()
  {
    return this.embeddedList;
  }
  /**
   * Setter de la liste d'objets inclus dans l'objet
   * @param embeddedList Liste d'objets inclus dans l'objet à positionner
   */
  private void setEmbeddedList(Bid4WinList<FooEmbeddable> embeddedList)
  {
    this.embeddedList = embeddedList;
  }
  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de l'objet inclus dans l'objet
   * @return L'objet inclus dans l'objet
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Embedded()
  @AttributeOverride(name = "value", column = @Column(name = "VALUE2"))
  public FooEmbeddable getEmbedded()
  {
    return this.embedded;
  }

  /**
   * Setter de l'objet inclus dans l'objet
   * @param embedded Objet inclus dans l'objet à positionner
   */
  private void setEmbedded(FooEmbeddable embedded)
  {
    this.embedded = embedded;
  }

  /**
   * Getter du set interne d'objets inclus dans l'objet
   * @return Le set interne d'objets inclus dans l'objet
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @ElementCollection(fetch=FetchType.EAGER)
  @CollectionTable(name = "FOO_EMBEDDED_SET", joinColumns = @JoinColumn(name="FOO_ID"))
  @SuppressWarnings(value = "unused")
  private Set<FooEmbeddable> getEmbeddedSetInternal()
  {
    return this.getEmbeddedSet().getInternal();
  }
  /**
   * Setter du set interne d'objets inclus dans l'objet
   * @param internalEmbeddedSet Set interne d'objets inclus dans l'objet à positionner
   */
  @SuppressWarnings(value = "unused")
  private void setEmbeddedSetInternal(Set<FooEmbeddable> internalEmbeddedSet)
  {
    this.setEmbeddedSet(new Bid4WinSet<FooEmbeddable>(internalEmbeddedSet, true));
  }

  /**
   * Getter de la liste interne d'objets inclus dans l'objet
   * @return La liste interne d'objets inclus dans l'objet
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @ElementCollection(fetch=FetchType.EAGER)
  @CollectionTable(name = "FOO_EMBEDDED_LIST", joinColumns = @JoinColumn(name="FOO_ID"))
  @OrderColumn(name="INDEX_")
  @SuppressWarnings(value = "unused")
  private List<FooEmbeddable> getEmbeddedListInternal()
  {
    return this.getEmbeddedList().getInternal();
  }
  /**
   * Setter de la liste interne d'objets inclus dans l'objet
   * @param internalEmbeddedList Liste interne d'objets inclus dans l'objet à positionner
   */
  @SuppressWarnings(value = "unused")
  private void setEmbeddedListInternal(List<FooEmbeddable> internalEmbeddedList)
  {
    this.setEmbeddedList(new Bid4WinList<FooEmbeddable>(internalEmbeddedList, true));
  }
}
