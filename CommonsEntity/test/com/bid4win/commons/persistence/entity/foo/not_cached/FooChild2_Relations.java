package com.bid4win.commons.persistence.entity.foo.not_cached;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;

/**
 * D�finition des relations de la classe FooChild2 vers les autres entit�s<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class FooChild2_Relations extends FooChild_Relations
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
