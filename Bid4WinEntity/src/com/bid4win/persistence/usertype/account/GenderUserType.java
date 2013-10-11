package com.bid4win.persistence.usertype.account;

import com.bid4win.commons.persistence.usertype.core.Bid4WinObjectTypeUserType;
import com.bid4win.persistence.entity.account.Gender;

/**
 * Cette classe d�fini le 'type utilisateur' g�rant les genres des utilisateurs
 * en base de donn�es<BR>
 * <BR>
 * @author Emeric Fill�tre
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
