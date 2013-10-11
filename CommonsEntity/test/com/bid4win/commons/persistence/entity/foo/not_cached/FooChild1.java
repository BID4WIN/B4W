package com.bid4win.commons.persistence.entity.foo.not_cached;

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
@DiscriminatorValue("CHILD1")
public class FooChild1 extends FooChild<FooChild1, FooParent1>
{
  /**
   * Constructeur
   */
  protected FooChild1()
  {
    super();
  }
  /**
   * Constructeur
   * @param id Identifiant de l'objet
   * @param value Valeur de l'objet
   */
  public FooChild1(String id, String value)
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
  @Override
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  @JoinColumn(name = "PARENT_ID", nullable = false, unique = false)
  public FooParent1 getParent()
  {
    return super.getParent();
  }
}
