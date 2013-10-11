package com.bid4win.persistence.entity.property;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.property.PropertyAbstract_Relations;

/**
 * Définition des relations de la classe Property vers les autres entités<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Property_Relations extends PropertyAbstract_Relations
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
