package com.bid4win.commons.persistence.entity.foo.not_cached;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;

/**
 * Définition des relations de la classe FooParent1 vers les autres entités<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class FooParent1_Relations extends FooParent_Relations
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
