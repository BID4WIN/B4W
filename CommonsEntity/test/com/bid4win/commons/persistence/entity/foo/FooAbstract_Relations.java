package com.bid4win.commons.persistence.entity.foo;

import com.bid4win.commons.persistence.entity.Bid4WinEntityAutoID_Relations;
import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;

/**
 * Définition des relations de la classe FooAbstract vers les autres entités<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class FooAbstract_Relations extends Bid4WinEntityAutoID_Relations
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
