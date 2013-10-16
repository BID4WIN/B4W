package com.bid4win.commons.persistence.entity.foo;

import com.bid4win.commons.persistence.entity.Bid4WinEntityAutoID_Relations;
import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;

/**
 * D�finition des relations de la classe FooAbstract vers les autres entit�s<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class FooAbstract_Relations extends Bid4WinEntityAutoID_Relations
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
