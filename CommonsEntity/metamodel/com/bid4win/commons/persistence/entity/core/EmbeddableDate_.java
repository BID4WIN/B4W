package com.bid4win.commons.persistence.entity.core;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.persistence.entity.Bid4WinEmbeddable_;

/**
 * Metamodel de la classe EmbeddedDate<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(EmbeddableDate.class)
public class EmbeddableDate_ extends Bid4WinEmbeddable_
{
  /** D�finition de la date embarqu�e */
  public static volatile SingularAttribute<EmbeddableDate, Bid4WinDate> date;
}
