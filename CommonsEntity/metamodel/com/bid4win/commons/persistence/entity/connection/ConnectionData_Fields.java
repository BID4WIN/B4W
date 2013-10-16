package com.bid4win.commons.persistence.entity.connection;

import com.bid4win.commons.persistence.entity.Bid4WinEmbeddable_Fields;
import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimple;

/**
 * D�finition des acc�s aux champs de la classe ConnectionData<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class ConnectionData_Fields extends  Bid4WinEmbeddable_Fields
{
  /** Definition du champ correspondant � TODO A COMMENTER */
  public static final Bid4WinFieldSimple<ConnectionData, String> SESSION_ID =
      new Bid4WinFieldSimple<ConnectionData, String>(null, ConnectionData_.sessionId);
}
