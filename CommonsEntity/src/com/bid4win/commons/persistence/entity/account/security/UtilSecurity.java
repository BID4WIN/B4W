package com.bid4win.commons.persistence.entity.account.security;

import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthorizationException;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class UtilSecurity
{
  /**
   * Cette m�thode permet de valider que le compte utilisateur en argument poss�de
   * bien les r�les attendus
   * @param account Compte utilisateur � valider
   * @param roles R�les attendu pour le compte utilisateur � valider
   * @return Le r�le du compte utilisateur en argument
   * @throws AuthorizationException Si le compte utilisateur en argument n'a pas
   * les r�les attendus
   */
  public static Role checkRole(AccountAbstract<?> account, Role ... roles) throws AuthorizationException
  {
    return UtilSecurity.checkRole(account.getCredential().getRole(), roles);
  }
  /**
   * Cette m�thode permet de valider que le r�le en argument poss�de bien les r�les
   * attendus
   * @param role R�le � valider
   * @param roles R�les attendu pour le r�le � valider
   * @return Le r�le valid�
   * @throws AuthorizationException Si le r�le en argument n'a pas les r�les attendus
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
   * Cette m�thode permet de valider que le compte utilisateur en argument poss�de
   * bien l'un des r�les attendus
   * @param account Compte utilisateur � valider
   * @param roles R�les attendu pour le compte utilisateur � valider
   * @return Le r�le du compte utilisateur en argument
   * @throws AuthorizationException Si le compte utilisateur en argument n'a pas
   * l'un des r�les attendus
   */
  public static Role checkOneRole(AccountAbstract<?> account, Role ... roles) throws AuthorizationException
  {
    return UtilSecurity.checkOneRole(account.getCredential().getRole(), roles);
  }
  /**
   * Cette m�thode permet de valider que le r�le en argument poss�de bien l'un des
   * r�les attendus
   * @param role R�le � valider
   * @param roles R�les attendu pour le r�le � valider
   * @return Le r�le valid�
   * @throws AuthorizationException Si le r�le en argument n'a pas l'un des r�les
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
   * Cette m�thode permet de valider que le compte utilisateur en argument ne poss�de
   * aucun des r�les donn�s
   * @param account Compte utilisateur � valider
   * @param roles R�les interdits pour le compte utilisateur � valider
   * @return Le r�le du compte utilisateur en argument
   * @throws AuthorizationException Si le compte utilisateur en argument a l'un
   * des r�les donn�s
   */
  public static Role checkNotRole(AccountAbstract<?> account, Role ... roles) throws AuthorizationException
  {
    return UtilSecurity.checkNotRole(account.getCredential().getRole(), roles);
  }
  /**
   * Cette m�thode permet de valider que le r�le en argument ne poss�de aucun des
   * r�les donn�s
   * @param role R�le � valider
   * @param roles R�les interdits pour le r�le � valider
   * @return Le r�le valid�
   * @throws AuthorizationException Si le r�le en argument a l'un des r�les donn�s
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
