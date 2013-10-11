package com.bid4win.commons.persistence.entity.foo.cached;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.commons.persistence.entity.core.EmbeddableDate;
import com.bid4win.commons.persistence.entity.foo.FooAbstract_;

/**
 * Metamodel de la class FooCached<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(FooCached.class)
public abstract class FooCached_ extends FooAbstract_
{
  /** Définition de la date de l'objet */
  // Suite à un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
  // en compte si défini dans le metamodel de la super class : bug HHH-5024
  // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
  public static volatile SingularAttribute<FooCached<?>, EmbeddableDate> embeddedDate;

  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
