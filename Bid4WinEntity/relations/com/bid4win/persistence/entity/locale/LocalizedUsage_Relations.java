package com.bid4win.persistence.entity.locale;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.resource.FileResourceUsageMultiPart_Relations;

/**
 * D�finition des relations de la classe LocalizedUsage vers les autres entit�s<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class LocalizedUsage_Relations extends FileResourceUsageMultiPart_Relations
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
