package com.bid4win.persistence.usertype.image;

import com.bid4win.commons.persistence.usertype.core.Bid4WinObjectTypeUserType;
import com.bid4win.persistence.entity.image.UsageType;

/**
 * Cette classe d�fini le 'type utilisateur' g�rant les types d'utilisation d'image
 * en base de donn�es<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class UsageTypeUserType extends Bid4WinObjectTypeUserType<UsageType>
{
  /**
   * Constructeur
   */
  public UsageTypeUserType()
  {
    super(UsageType.class);
  }
}
