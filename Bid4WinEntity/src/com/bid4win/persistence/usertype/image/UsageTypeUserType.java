package com.bid4win.persistence.usertype.image;

import com.bid4win.commons.persistence.usertype.core.Bid4WinObjectTypeUserType;
import com.bid4win.persistence.entity.image.UsageType;

/**
 * Cette classe défini le 'type utilisateur' gérant les types d'utilisation d'image
 * en base de données<BR>
 * <BR>
 * @author Emeric Fillâtre
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
