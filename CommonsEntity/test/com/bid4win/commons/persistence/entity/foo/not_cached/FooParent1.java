package com.bid4win.commons.persistence.entity.foo.not_cached;

import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OptimisticLock;

import com.bid4win.commons.core.Bid4WinDate;

/**
 * Classe de test parent avec orphan removal=true et fetch type=eager<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Entity
@Access(AccessType.FIELD)
@DiscriminatorValue("PARENT1")
public class FooParent1 extends FooParent<FooParent1, FooChild1>
{
  /**
   * Constructeur
   */
  protected FooParent1()
  {
    super();
  }

  /**
   * Constructeur
   * @param value Valeur de l'objet parent
   * @param date Date de l'objet parent
   */
  public FooParent1(String value, Bid4WinDate date)
  {
    super(value, date);
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de la map d'enfants inclus dans l'objet pour la persistence
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.foo.not_cached.FooParent#getChildMap()
   */
  @Override
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  @MapKey(name = "value")
  // A partir d'Hibernate 4.1.1, l'entité parent n'est pas mise à jour par défaut
  @OptimisticLock(excluded = false)
  protected Map<String, FooChild1> getChildMap()
  {
    return super.getChildMap();
  }
}
