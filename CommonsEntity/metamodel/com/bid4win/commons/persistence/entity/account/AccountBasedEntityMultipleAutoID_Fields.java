package com.bid4win.commons.persistence.entity.account;

import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimple;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class AccountBasedEntityMultipleAutoID_Fields extends AccountBasedEntityMultiple_Fields
{
  /** Definition du champ correspondant � l'identifiant de l'entit� */
  public static final Bid4WinFieldSimple<AccountBasedEntityMultipleAutoID<?, ?>, Long> ID =
      new Bid4WinFieldSimple<AccountBasedEntityMultipleAutoID<?, ?>, Long>(
          null, AccountBasedEntityMultipleAutoID_.id);
}
