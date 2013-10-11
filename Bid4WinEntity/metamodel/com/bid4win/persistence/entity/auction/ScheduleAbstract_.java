package com.bid4win.persistence.entity.auction;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.persistence.entity.Bid4WinEmbeddable_;

/**
 * Metamodel de la classe ScheduleAbstract<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(ScheduleAbstract.class)
public class ScheduleAbstract_ extends Bid4WinEmbeddable_
{
  /** D�finition de la date de d�marrage pr�vue de la vente aux ench�res */
  public static volatile SingularAttribute<ScheduleAbstract<?>, Bid4WinDate> startDate;
  /** D�finition de la date de cl�ture pr�vue de la vente aux ench�res */
  public static volatile SingularAttribute<ScheduleAbstract<?>, Bid4WinDate> endDate;
}
