package com.bid4win.commons.persistence.entity.account;

import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimple;

/**
 * Definition des acc�s aux champs de la classe AccountBasedEntitySingle<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class AccountBasedEntitySingle_Fields extends AccountBasedEntity_Fields
{
  /** D�finition du champ correspondant au compte utilisateur de l'entit� */
  public static Bid4WinFieldSimple<AccountBasedEntitySingle<?, ?, ?>, AccountAbstract<?>> ACCOUNT =
      new Bid4WinFieldSimple<AccountBasedEntitySingle<?, ?, ?>, AccountAbstract<?>>(
          null, AccountBasedEntitySingle_.account);
/*  public static final Bid4WinFieldJoinedSingle<AccountBasedEntitySingle<?, ?, ?>, AccountAbstract<?>> ACCOUNT_JOINED =
      new Bid4WinFieldJoinedSingle<AccountBasedEntitySingle<?, ?, ?>, AccountAbstract<?>>(AccountBasedEntitySingle_.account);*/
}
