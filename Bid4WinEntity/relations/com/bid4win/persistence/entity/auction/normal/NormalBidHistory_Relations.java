package com.bid4win.persistence.entity.auction.normal;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.persistence.entity.auction.BidHistory_Relations;

/**
 * Définition des relations de la classe NormalBidHistory vers les autres entités<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class NormalBidHistory_Relations extends BidHistory_Relations
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
