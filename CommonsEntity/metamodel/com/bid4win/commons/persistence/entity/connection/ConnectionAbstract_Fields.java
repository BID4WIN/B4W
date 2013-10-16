package com.bid4win.commons.persistence.entity.connection;

import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimple;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultipleGeneratedID_Fields;

/**
 * Définition des accès aux champs de la classe ConnectionAbstract<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class ConnectionAbstract_Fields extends AccountBasedEntityMultipleGeneratedID_Fields
{
  /** Definition du champ correspondant à TODO A COMMENTER */
  public static final Bid4WinFieldSimple<ConnectionAbstract<?, ?, ?>, ConnectionData> DATA =
      new Bid4WinFieldSimple<ConnectionAbstract<?, ?, ?>, ConnectionData>(null, ConnectionAbstract_.data);
}
