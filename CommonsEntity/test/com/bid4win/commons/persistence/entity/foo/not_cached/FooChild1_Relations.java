package com.bid4win.commons.persistence.entity.foo.not_cached;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;

/**
 * Définition des relations de la classe FooChild1 vers les autres entités<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class FooChild1_Relations extends FooChild_Relations
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
