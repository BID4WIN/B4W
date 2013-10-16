package com.bid4win.commons.persistence.entity.connection;

import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimple;
import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimpleToSimple;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultipleAutoID_Fields;

/**
 * Définition des accès aux champs de la classe ConnectionHistoryAbstract<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class ConnectionHistoryAbstract_Fields extends AccountBasedEntityMultipleAutoID_Fields
{
  /** Definition du champ correspondant à TODO A COMMENTER */
  public static final Bid4WinFieldSimple<ConnectionHistoryAbstract<?, ?>, ConnectionData> DATA =
      new Bid4WinFieldSimple<ConnectionHistoryAbstract<?, ?>, ConnectionData>(null, ConnectionHistoryAbstract_.data);
  /** Definition du champ correspondant à TODO A COMMENTER */
  public static final Bid4WinFieldSimpleToSimple<ConnectionHistoryAbstract<?, ?>, ConnectionData, String> SESSION_ID =
      new Bid4WinFieldSimpleToSimple<ConnectionHistoryAbstract<?, ?>, ConnectionData, String>(DATA, ConnectionData_Fields.SESSION_ID);
}
