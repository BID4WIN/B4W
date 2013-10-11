package com.bid4win.commons.persistence.entity.foo.not_cached;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.foo.FooAbstract_Relations;

/**
 * D�finition des relations de la classe Foo vers les autres entit�s<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Foo_Relations extends FooAbstract_Relations
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
