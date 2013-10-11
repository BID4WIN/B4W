package com.bid4win.persistence.entity.connection;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.connection.ConnectionAbstract_Relations;

/**
 * Définition des relations de la classe Connection vers les autres entités<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Connection_Relations extends ConnectionAbstract_Relations
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
