package com.bid4win.commons.persistence.entity.account;

import com.bid4win.commons.core.exception.UserException;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class AccountBasedEntityStub
       extends AccountBasedEntity<AccountBasedEntityStub, Long, AccountAbstractStub>
{
  /**
   * Constructeur pour cr�ation par introspection
   */
  protected AccountBasedEntityStub()
  {
    super();
  }
  /**
   * Constructeur sans pr�cision de l'identifiant
   * @param account Compte utilisateur de l'entit�
   * @throws UserException Si le compte utilisateur en argument est nul
   */
  protected AccountBasedEntityStub(AccountAbstractStub account) throws UserException
  {
    super(account);
  }
  /**
   * Constructeur avec pr�cision de l'identifiant
   * @param id Identifiant de l'entit�
   * @param account Compte utilisateur de l'entit�
   * @throws UserException Si le compte utilisateur en argument est nul
   */
  protected AccountBasedEntityStub(Long id, AccountAbstractStub account)
            throws UserException
  {
    super(id, account);
  }
}
