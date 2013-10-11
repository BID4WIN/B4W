package com.bid4win.commons.persistence.entity.foo;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.commons.persistence.entity.core.EmbeddableDate;

/**
 * Metamodel de la classe FooAbstract<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(FooAbstract.class)
public abstract class FooAbstract_ extends Bid4WinEntity_
{
  /** D�finition de l'identifiant de l'objet */
  public static volatile SingularAttribute<FooAbstract<?>, Integer> id;
  /** D�finition de la valeur de l'objet */
  public static volatile SingularAttribute<FooAbstract<?>, String> value;
  /** D�finition de la date de l'objet */
  public static volatile SingularAttribute<FooAbstract<?>, Bid4WinDate> date;
  /** D�finition de la date de l'objet */
  public static volatile SingularAttribute<FooAbstract<?>, EmbeddableDate> embeddedDate;

  // D�finition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
