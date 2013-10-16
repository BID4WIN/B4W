package com.bid4win.commons.persistence.entity.account;

import com.bid4win.commons.persistence.entity.Bid4WinEntityGeneratedID;
import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimple;
import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimpleToSimpleParent;

/**
 * Definition des accès aux champs de la classe AccountBasedEntityMultiple<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class AccountBasedEntityMultiple_Fields extends AccountBasedEntity_Fields
{
  /** Définition du champ correspondant au compte utilisateur de l'entité */
  public static final Bid4WinFieldSimple<AccountBasedEntityMultiple<?, ?, ?>, AccountAbstract<?>> ACCOUNT =
      new Bid4WinFieldSimple<AccountBasedEntityMultiple<?, ?, ?>, AccountAbstract<?>>(
          null, AccountBasedEntityMultiple_.account);
  public static final Bid4WinFieldSimpleToSimpleParent<AccountBasedEntityMultiple<?, ?, ?>,
                                                       Bid4WinEntityGeneratedID<?>,
                                                       AccountAbstract<?>,
                                                       String> AUCTION_ID =
      new Bid4WinFieldSimpleToSimpleParent<AccountBasedEntityMultiple<?, ?, ?>,
                                           Bid4WinEntityGeneratedID<?>,
                                           AccountAbstract<?>,
                                           String>(
          ACCOUNT, AccountAbstract_Fields.ID);


  /*public static final Bid4WinFieldJoinedSingle<AccountBasedEntityMultiple<?, ?, ?>, AccountAbstract<?>> ACCOUNT_JOINED =
      new Bid4WinFieldJoinedSingle<AccountBasedEntityMultiple<?, ?, ?>, AccountAbstract<?>>(
          AccountBasedEntityMultiple_.account);*/
}
