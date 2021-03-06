package com.bid4win.commons.persistence.entity.foo.cached;

import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OptimisticLock;

import com.bid4win.commons.core.Bid4WinDate;

/**
 * Classe de test parent avec orphan removal=true et fetch type=lazy<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Entity
@Access(AccessType.FIELD)
@DiscriminatorValue("CACHED_PARENT1")
public class FooCachedParent3 extends FooCachedParent<FooCachedParent3, FooCachedChild3>
{
  /**
   * Constructeur
   */
  protected FooCachedParent3()
  {
    super();
  }

  /**
   * Constructeur
   * @param value Valeur de l'objet parent
   * @param date Date de l'objet parent
   */
  public FooCachedParent3(String value, Bid4WinDate date)
  {
    super(value, date);
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de la map interne d'objets inclus dans l'objet
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.foo.cached.FooCachedParent#getChildMapInternal()
   */
  @Override
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @MapKey(name = "value")
  @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
  // A partir d'Hibernate 4.1.1, l'entit� parent n'est pas mise � jour par d�faut
  @OptimisticLock(excluded = false)
  protected Map<String, FooCachedChild3> getChildMapInternal()
  {
    return super.getChildMapInternal();
  }
}
