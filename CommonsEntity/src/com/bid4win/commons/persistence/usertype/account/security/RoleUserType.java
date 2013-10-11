package com.bid4win.commons.persistence.usertype.account.security;

import com.bid4win.commons.persistence.entity.account.security.Role;
import com.bid4win.commons.persistence.usertype.core.Bid4WinObjectTypeUserType;

/**
 * Cette classe d�fini le 'type utilisateur' g�rant les r�les en base de donn�es<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class RoleUserType extends Bid4WinObjectTypeUserType<Role>
{
  /**
   * Constructeur
   */
  public RoleUserType()
  {
    super(Role.class);
  }
}
