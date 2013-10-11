package com.bid4win.persistence.usertype.image;

import com.bid4win.commons.persistence.usertype.core.Bid4WinObjectTypeUserType;
import com.bid4win.persistence.entity.image.ImageType;

/**
 * Cette classe d�fini le 'type utilisateur' g�rant les types d'image en base de
 * donn�es<BR>
 * <BR>
 * @author Emeric Fill�tre
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
