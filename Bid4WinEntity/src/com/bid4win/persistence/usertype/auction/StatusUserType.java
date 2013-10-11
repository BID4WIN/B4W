package com.bid4win.persistence.usertype.auction;

import com.bid4win.commons.persistence.usertype.core.Bid4WinObjectTypeUserType;
import com.bid4win.persistence.entity.auction.Status;

/**
 * Cette classe défini le 'type utilisateur' gérant les status de vente aux enchères
 * en base de données<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class StatusUserType extends Bid4WinObjectTypeUserType<Status>
{
  /**
   * Constructeur
   */
  public StatusUserType()
  {
    super(Status.class);
  }
}
