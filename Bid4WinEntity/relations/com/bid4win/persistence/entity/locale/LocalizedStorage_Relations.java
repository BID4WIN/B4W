package com.bid4win.persistence.entity.locale;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.resource.FileResourceStorageMultiPart_Relations;

/**
 * Définition des relations de la classe LocalizedStorage vers les autres entités<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class LocalizedStorage_Relations extends FileResourceStorageMultiPart_Relations
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
