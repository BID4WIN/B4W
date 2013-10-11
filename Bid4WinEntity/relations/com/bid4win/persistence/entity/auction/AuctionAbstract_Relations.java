package com.bid4win.persistence.entity.auction;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.persistence.entity.sale.Sale_Relations;

/**
 * D�finition des relations de la classe AuctionAbstract vers les autres entit�s<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class AuctionAbstract_Relations extends Sale_Relations
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
