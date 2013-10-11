package com.bid4win.commons.persistence.entity.account;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;

/**
 * Metamodel de la classe AccountBasedEntitySingleAutoID<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(AccountBasedEntitySingleAutoID.class)
public abstract class AccountBasedEntitySingleAutoID_ extends AccountBasedEntitySingle_
{
  /** D�finition de l'identifiant de l'entit�*/
  public static volatile SingularAttribute<AccountBasedEntitySingleAutoID<?, ?>, Long> id;

  // D�finition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
