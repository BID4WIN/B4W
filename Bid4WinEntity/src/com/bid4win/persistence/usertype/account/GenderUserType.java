package com.bid4win.persistence.usertype.account;

import com.bid4win.commons.persistence.usertype.core.Bid4WinObjectTypeUserType;
import com.bid4win.persistence.entity.account.Gender;

/**
 * Cette classe défini le 'type utilisateur' gérant les genres des utilisateurs
 * en base de données<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class GenderUserType extends Bid4WinObjectTypeUserType<Gender>
{
  /**
   * Constructeur
   */
  public GenderUserType()
  {
    super(Gender.class);
  }
}
