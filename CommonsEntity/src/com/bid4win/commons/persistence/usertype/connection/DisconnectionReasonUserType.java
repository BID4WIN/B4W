package com.bid4win.commons.persistence.usertype.connection;

import com.bid4win.commons.persistence.entity.connection.DisconnectionReason;
import com.bid4win.commons.persistence.usertype.core.Bid4WinObjectTypeUserType;

/**
 * Cette classe d�fini le 'type utilisateur' g�rant les raisons de fin de connexion
 * en base de donn�es<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class DisconnectionReasonUserType extends Bid4WinObjectTypeUserType<DisconnectionReason>
{
  /**
   * Constructeur
   */
  public DisconnectionReasonUserType()
  {
    super(DisconnectionReason.class);
  }
}
