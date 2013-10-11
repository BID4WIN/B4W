package com.bid4win.commons.persistence.usertype.account.security;

import com.bid4win.commons.persistence.entity.account.security.Role;
import com.bid4win.commons.persistence.usertype.core.Bid4WinObjectTypeUserType;

/**
 * Cette classe défini le 'type utilisateur' gérant les rôles en base de données<BR>
 * <BR>
 * @author Emeric Fillâtre
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
