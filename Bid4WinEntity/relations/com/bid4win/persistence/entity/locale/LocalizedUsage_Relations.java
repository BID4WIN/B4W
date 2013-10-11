package com.bid4win.persistence.entity.locale;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.resource.FileResourceUsageMultiPart_Relations;

/**
 * Définition des relations de la classe LocalizedUsage vers les autres entités<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class LocalizedUsage_Relations extends FileResourceUsageMultiPart_Relations
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
