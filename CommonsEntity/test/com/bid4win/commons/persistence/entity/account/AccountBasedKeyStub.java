package com.bid4win.commons.persistence.entity.account;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.IdPattern;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class AccountBasedKeyStub
       extends AccountBasedKey<AccountBasedKeyStub, AccountAbstractStub>
{
  /**
   * Constructeur pour création par introspection
   */
  protected AccountBasedKeyStub()
  {
    super();
  }
  /**
   * Constructeur
   * @param idPattern Pattern à utiliser pour la génération des clés
   * @param account Compte utilisateur à lier à la clé
   * @throws UserException Si le pattern à utiliser pour la génération des clés
   * ou le compte utilisateur en argument est nul
   */
  protected AccountBasedKeyStub(IdPattern idPattern, AccountAbstractStub account)
            throws UserException
  {
    super(idPattern, account);
  }
  /**
   * Constructeur
   * @param account Compte utilisateur à lier à la clé
   * @throws UserException Si le compte utilisateur en argument est nul
   */
  public AccountBasedKeyStub(AccountAbstractStub account) throws UserException
  {
    super(account);
  }
}
