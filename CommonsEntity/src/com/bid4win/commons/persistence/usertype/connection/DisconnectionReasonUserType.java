package com.bid4win.commons.persistence.usertype.connection;

import com.bid4win.commons.persistence.entity.connection.DisconnectionReason;
import com.bid4win.commons.persistence.usertype.core.Bid4WinObjectTypeUserType;

/**
 * Cette classe défini le 'type utilisateur' gérant les raisons de fin de connexion
 * en base de données<BR>
 * <BR>
 * @author Emeric Fillâtre
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
