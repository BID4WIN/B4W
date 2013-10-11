package com.bid4win.commons.persistence.entity.account;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;

/**
 * Metamodel de la classe AccountBasedEntityMultipleGeneratedID<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(AccountBasedEntityMultipleGeneratedID.class)
public abstract class AccountBasedEntityMultipleGeneratedID_
       extends AccountBasedEntityMultiple_
{
  /** D�finition de l'identifiant de l'entit�*/
  public static volatile SingularAttribute<AccountBasedEntityMultipleGeneratedID<?, ?>, String> id;

  // D�finition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
