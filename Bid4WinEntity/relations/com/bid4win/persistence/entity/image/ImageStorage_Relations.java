package com.bid4win.persistence.entity.image;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_Relations;
import com.bid4win.commons.persistence.entity.resource.FileResourceStorageMultiPart_Relations;

/**
 * D�finition des relations de la classe ImageStorage vers les autres entit�s<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class ImageStorage_Relations extends FileResourceStorageMultiPart_Relations
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
