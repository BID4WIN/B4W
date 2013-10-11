package com.bid4win.persistence.usertype.account.credit;

import com.bid4win.commons.persistence.usertype.core.Bid4WinObjectTypeUserType;
import com.bid4win.persistence.entity.account.credit.Origin;

/**
 * Cette classe défini le 'type utilisateur' gérant les types de provenance de
 * crédits en base de données<BR>
 * <BR>
 * @author Emeric Fillâtre
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
