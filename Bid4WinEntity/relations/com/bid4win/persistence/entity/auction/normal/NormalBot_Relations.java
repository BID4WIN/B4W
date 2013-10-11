package com.bid4win.persistence.entity.auction.normal;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.persistence.entity.auction.Bot_Relations;

/**
 * Définition des relations de la classe NormalBot vers les autres entités<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class NormalBot_Relations extends Bot_Relations
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
