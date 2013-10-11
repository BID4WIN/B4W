package com.bid4win.persistence.entity.auction;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.persistence.entity.sale.Sale_Relations;

/**
 * Définition des relations de la classe AuctionAbstract vers les autres entités<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class AuctionAbstract_Relations extends Sale_Relations
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
