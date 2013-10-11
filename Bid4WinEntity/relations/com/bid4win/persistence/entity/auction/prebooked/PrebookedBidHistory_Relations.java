package com.bid4win.persistence.entity.auction.prebooked;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.persistence.entity.auction.BidHistory_Relations;

/**
 * D�finition des relations de la classe PrebookedBidHistory vers les autres entit�s<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class PrebookedBidHistory_Relations extends BidHistory_Relations
{
  // D�marre la protection des relations
  static
  {
    Bid4WinEntity_Relations.startProtection();
  }
  // Arr�te la protection des relations
  static
  {
    Bid4WinEntity_Relations.stopProtection();
  }
}
