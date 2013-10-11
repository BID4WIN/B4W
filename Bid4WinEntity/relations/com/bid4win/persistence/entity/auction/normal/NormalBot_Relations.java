package com.bid4win.persistence.entity.auction.normal;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.persistence.entity.auction.Bot_Relations;

/**
 * D�finition des relations de la classe NormalBot vers les autres entit�s<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class NormalBot_Relations extends Bot_Relations
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
