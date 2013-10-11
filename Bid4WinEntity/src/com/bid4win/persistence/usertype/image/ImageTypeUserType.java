package com.bid4win.persistence.usertype.image;

import com.bid4win.commons.persistence.usertype.core.Bid4WinObjectTypeUserType;
import com.bid4win.persistence.entity.image.ImageType;

/**
 * Cette classe défini le 'type utilisateur' gérant les types d'image en base de
 * données<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class ImageTypeUserType extends Bid4WinObjectTypeUserType<ImageType>
{
  /**
   * Constructeur
   */
  public ImageTypeUserType()
  {
    super(ImageType.class);
  }
}
