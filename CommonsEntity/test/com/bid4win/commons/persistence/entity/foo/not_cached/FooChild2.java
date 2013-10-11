package com.bid4win.commons.persistence.entity.foo.not_cached;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Classe de test enfant avec cascade=none et fetch type=lazy<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Entity
@Access(AccessType.FIELD)
@DiscriminatorValue("CHILD2")
public class FooChild2 extends FooChild<FooChild2, FooParent2>
{
  /**
   * Constructeur
   */
  protected FooChild2()
  {
    super();
  }
  /**
   * Constructeur
   * @param id Identifiant de l'objet
   * @param value Valeur de l'objet
   */
  public FooChild2(String id, String value)
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
  @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
  @JoinColumn(name = "PARENT_ID", nullable = false, unique = false)
  public FooParent2 getParent()
  {
    return super.getParent();
  }
}
