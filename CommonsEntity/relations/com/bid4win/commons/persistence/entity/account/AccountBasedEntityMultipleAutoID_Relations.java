package com.bid4win.commons.persistence.entity.account;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;

/**
 * D�finition des relations de la classe AccountBasedEntityMultipleAutoID vers les
 * autres entit�s<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class AccountBasedEntityMultipleAutoID_Relations
       extends AccountBasedEntityMultiple_Relations
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
