package com.bid4win.persistence.usertype.auction;

import com.bid4win.commons.persistence.usertype.core.Bid4WinObjectTypeUserType;
import com.bid4win.persistence.entity.auction.Status;

/**
 * Cette classe d�fini le 'type utilisateur' g�rant les status de vente aux ench�res
 * en base de donn�es<BR>
 * <BR>
 * @author Emeric Fill�tre
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
