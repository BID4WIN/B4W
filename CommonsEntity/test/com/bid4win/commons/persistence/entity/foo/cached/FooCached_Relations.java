package com.bid4win.commons.persistence.entity.foo.cached;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.foo.FooAbstract_Relations;

/**
 * Définition des relations de la classe FooCached vers les autres entités<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class FooCached_Relations extends FooAbstract_Relations
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
