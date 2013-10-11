package com.bid4win.persistence.usertype.account.credit;

import com.bid4win.commons.persistence.usertype.core.Bid4WinObjectTypeUserType;
import com.bid4win.persistence.entity.account.credit.Origin;

/**
 * Cette classe d�fini le 'type utilisateur' g�rant les types de provenance de
 * cr�dits en base de donn�es<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class OriginUserType extends Bid4WinObjectTypeUserType<Origin>
{
  /**
   * Constructeur
   */
  public OriginUserType()
  {
    super(Origin.class);
  }
}
