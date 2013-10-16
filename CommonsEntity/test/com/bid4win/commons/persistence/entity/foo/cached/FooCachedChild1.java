package com.bid4win.commons.persistence.entity.foo.cached;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Classe de test enfant avec cascade=merge et fetch type=lazy<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Entity
@Access(AccessType.FIELD)
@DiscriminatorValue("CACHED_CHILD1")
public class FooCachedChild1 extends FooCachedChild<FooCachedChild1, FooCachedParent1>
{

  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  @JoinColumn(name = "PARENT_ID", nullable = false, unique = false)
  private FooCachedParent1 parent;


  /**
   * Constructeur
   */
  protected FooCachedChild1()
  {
    super();
  }
  /**
   * Constructeur
   * @param id Identifiant de l'objet
   * @param value Valeur de l'objet
   */
  public FooCachedChild1(String id, String value)
  {
    super(id, value);
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter du parent de l'objet
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.foo.not_cached.FooChild#getParent()
   */
/*  @Override
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  @JoinColumn(name = "PARENT_ID", nullable = false, unique = false)
  public FooCachedParent1 getParent()
  {
    return super.getParent();
  }*/
}
