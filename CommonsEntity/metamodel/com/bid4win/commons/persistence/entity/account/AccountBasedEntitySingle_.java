package com.bid4win.commons.persistence.entity.account;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;

/**
 * Metamodel de la classe AccountBasedEntitySingle<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(AccountBasedEntitySingle.class)
public abstract class AccountBasedEntitySingle_ extends AccountBasedEntity_
{
  /** D�finition du compte utilisateur de l'entit� */
  public static volatile SingularAttribute<AccountBasedEntitySingle<?, ?, ?>, AccountAbstract<?>> account;

  // D�finition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
