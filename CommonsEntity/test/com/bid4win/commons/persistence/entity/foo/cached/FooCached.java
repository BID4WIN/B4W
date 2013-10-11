package com.bid4win.commons.persistence.entity.foo.cached;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Cacheable;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.persistence.entity.foo.FooAbstract;

/**
 * Classe de test simple<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Entity
@Access(AccessType.FIELD)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", discriminatorType = DiscriminatorType.STRING, length = 10)
@DiscriminatorValue("SIMPLE")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class FooCached<CLASS extends FooCached<CLASS>> extends FooAbstract<CLASS>
{
  /**
   * Constructeur
   */
  protected FooCached()
  {
    super();
  }
  /**
   * Constructeur
   * @param value Valeur de l'objet
   * @param date Date de l'objet
   */
  public FooCached(String value, Bid4WinDate date)
  {
    super(value, date);
  }
}
