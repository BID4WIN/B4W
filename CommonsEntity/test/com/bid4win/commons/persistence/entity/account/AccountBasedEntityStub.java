package com.bid4win.commons.persistence.entity.account;

import com.bid4win.commons.core.exception.UserException;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class AccountBasedEntityStub
       extends AccountBasedEntity<AccountBasedEntityStub, Long, AccountAbstractStub>
{
  /**
   * Constructeur pour création par introspection
   */
  protected AccountBasedEntityStub()
  {
    super();
  }
  /**
   * Constructeur sans précision de l'identifiant
   * @param account Compte utilisateur de l'entité
   * @throws UserException Si le compte utilisateur en argument est nul
   */
  protected AccountBasedEntityStub(AccountAbstractStub account) throws UserException
  {
    super(account);
  }
  /**
   * Constructeur avec précision de l'identifiant
   * @param id Identifiant de l'entité
   * @param account Compte utilisateur de l'entité
   * @throws UserException Si le compte utilisateur en argument est nul
   */
  protected AccountBasedEntityStub(Long id, AccountAbstractStub account)
            throws UserException
  {
    super(id, account);
  }
}
