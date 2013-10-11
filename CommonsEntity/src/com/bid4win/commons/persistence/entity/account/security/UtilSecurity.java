package com.bid4win.commons.persistence.entity.account.security;

import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthorizationException;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class UtilSecurity
{
  /**
   * Cette méthode permet de valider que le compte utilisateur en argument possède
   * bien les rôles attendus
   * @param account Compte utilisateur à valider
   * @param roles Rôles attendu pour le compte utilisateur à valider
   * @return Le rôle du compte utilisateur en argument
   * @throws AuthorizationException Si le compte utilisateur en argument n'a pas
   * les rôles attendus
   */
  public static Role checkRole(AccountAbstract<?> account, Role ... roles) throws AuthorizationException
  {
    return UtilSecurity.checkRole(account.getCredential().getRole(), roles);
  }
  /**
   * Cette méthode permet de valider que le rôle en argument possède bien les rôles
   * attendus
   * @param role Rôle à valider
   * @param roles Rôles attendu pour le rôle à valider
   * @return Le rôle validé
   * @throws AuthorizationException Si le rôle en argument n'a pas les rôles attendus
   */
  public static Role checkRole(Role role, Role ... roles) throws AuthorizationException
  {
    for(Role expected : roles)
    {
      if(!role.belongsTo(expected))
      {
        throw new AuthorizationException();
      }
    }
    return role;
  }
  /**
   * Cette méthode permet de valider que le compte utilisateur en argument possède
   * bien l'un des rôles attendus
   * @param account Compte utilisateur à valider
   * @param roles Rôles attendu pour le compte utilisateur à valider
   * @return Le rôle du compte utilisateur en argument
   * @throws AuthorizationException Si le compte utilisateur en argument n'a pas
   * l'un des rôles attendus
   */
  public static Role checkOneRole(AccountAbstract<?> account, Role ... roles) throws AuthorizationException
  {
    return UtilSecurity.checkOneRole(account.getCredential().getRole(), roles);
  }
  /**
   * Cette méthode permet de valider que le rôle en argument possède bien l'un des
   * rôles attendus
   * @param role Rôle à valider
   * @param roles Rôles attendu pour le rôle à valider
   * @return Le rôle validé
   * @throws AuthorizationException Si le rôle en argument n'a pas l'un des rôles
   * attendus
   */
  public static Role checkOneRole(Role role, Role ... roles) throws AuthorizationException
  {
    for(Role expected : roles)
    {
      if(role.belongsTo(expected))
      {
        return role;
      }
    }
    throw new AuthorizationException();
  }
  /**
   * Cette méthode permet de valider que le compte utilisateur en argument ne possède
   * aucun des rôles donnés
   * @param account Compte utilisateur à valider
   * @param roles Rôles interdits pour le compte utilisateur à valider
   * @return Le rôle du compte utilisateur en argument
   * @throws AuthorizationException Si le compte utilisateur en argument a l'un
   * des rôles donnés
   */
  public static Role checkNotRole(AccountAbstract<?> account, Role ... roles) throws AuthorizationException
  {
    return UtilSecurity.checkNotRole(account.getCredential().getRole(), roles);
  }
  /**
   * Cette méthode permet de valider que le rôle en argument ne possède aucun des
   * rôles donnés
   * @param role Rôle à valider
   * @param roles Rôles interdits pour le rôle à valider
   * @return Le rôle validé
   * @throws AuthorizationException Si le rôle en argument a l'un des rôles donnés
   */
  public static Role checkNotRole(Role role, Role ... roles) throws AuthorizationException
  {
    for(Role expected : roles)
    {
      if(role.belongsTo(expected))
      {
        throw new AuthorizationException();
      }
    }
    return role;
  }
}
