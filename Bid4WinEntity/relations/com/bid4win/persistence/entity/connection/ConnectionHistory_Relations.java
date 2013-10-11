package com.bid4win.persistence.entity.connection;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.connection.ConnectionHistoryAbstract_Relations;

/**
 * Définition des relations de la classe ConnectionHistory vers les autres entités<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class ConnectionHistory_Relations extends ConnectionHistoryAbstract_Relations
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
