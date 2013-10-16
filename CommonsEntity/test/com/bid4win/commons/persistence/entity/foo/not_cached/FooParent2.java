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
 * Classe de test parent avec orphan removal=true et fetch type=lazy<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Entity
@Access(AccessType.FIELD)
@DiscriminatorValue("PARENT2")
public class FooParent2 extends FooParent<FooParent2, FooChild2>
{
  /**
   * Constructeur
   */
  protected FooParent2()
  {
    super();
  }

  /**
   * Constructeur
   * @param value Valeur de l'objet parent
   * @param date Date de l'objet parent
   */
  public FooParent2(String value, Bid4WinDate date)
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
  @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @MapKey(name = "value")
  // A partir d'Hibernate 4.1.1, l'entité parent n'est pas mise à jour par défaut
  @OptimisticLock(excluded = false)
  protected Map<String, FooChild2> getChildMap()
  {
    return super.getChildMap();
  }
}
