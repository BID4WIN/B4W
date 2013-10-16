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
 * Classe de test enfant avec cascade=merge et fetch type=eager<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Entity
@Access(AccessType.FIELD)
@DiscriminatorValue("CACHED_CHILD1")
public class FooCachedChild3 extends FooCachedChild<FooCachedChild3, FooCachedParent3>
{
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  @JoinColumn(name = "PARENT_ID", nullable = false, unique = false)
  private FooCachedParent3 parent;

  /**
   * Constructeur
   */
  protected FooCachedChild3()
  {
    super();
  }
  /**
   * Constructeur
   * @param id Identifiant de l'objet
   * @param value Valeur de l'objet
   */
  public FooCachedChild3(String id, String value)
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
  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  @JoinColumn(name = "PARENT_ID", nullable = false, unique = false)
  public FooCachedParent3 getParent()
  {
    return super.getParent();
  }*/
}
