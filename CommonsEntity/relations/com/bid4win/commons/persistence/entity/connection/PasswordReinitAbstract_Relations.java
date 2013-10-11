package com.bid4win.commons.persistence.entity.connection;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.account.AccountBasedKey_Relations;

/**
 * D�finition des relations de la classe PasswordReinitAbstract vers les autres entit�s<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class PasswordReinitAbstract_Relations extends AccountBasedKey_Relations
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
