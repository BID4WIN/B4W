package com.bid4win.commons.persistence.entity.connection;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultiple_Relations;

/**
 * Définition des relations de la classe ConnectionAbstract vers les autres entités<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class ConnectionAbstract_Relations extends AccountBasedEntityMultiple_Relations
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
