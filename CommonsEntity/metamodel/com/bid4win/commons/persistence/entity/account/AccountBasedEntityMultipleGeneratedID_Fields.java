package com.bid4win.commons.persistence.entity.account;

import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimple;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class AccountBasedEntityMultipleGeneratedID_Fields extends AccountBasedEntityMultiple_Fields
{
  /** Definition du champ correspondant à l'identifiant de l'entité */
  public static final Bid4WinFieldSimple<AccountBasedEntityMultipleGeneratedID<?, ?>, String> ID =
      new Bid4WinFieldSimple<AccountBasedEntityMultipleGeneratedID<?, ?>, String>(
          null, AccountBasedEntityMultipleGeneratedID_.id);
}
