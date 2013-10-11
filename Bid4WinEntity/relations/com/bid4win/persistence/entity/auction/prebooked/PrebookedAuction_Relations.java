package com.bid4win.persistence.entity.auction.prebooked;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.persistence.entity.auction.Auction_Relations;

/**
 * Définition des relations de la classe PrebookedAuction vers les autres entités<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class PrebookedAuction_Relations extends Auction_Relations
{
  // Démarre la protection des relations
  static
  {
    Bid4WinEntity_Relations.startProtection();
  }
  // Arrête la protection des relations
  static
  {
    Bid4WinEntity_Relations.stopProtection();
  }
}
